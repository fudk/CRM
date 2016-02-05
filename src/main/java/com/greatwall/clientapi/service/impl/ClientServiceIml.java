package com.greatwall.clientapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.greatwall.api.service.CallbackNotifyService;
import com.greatwall.clientapi.service.ClientService;
import com.greatwall.clientapi.service.JinPiaoService;
import com.greatwall.clientapi.service.LiulService;
import com.greatwall.clientapi.service.QiutongService;
import com.greatwall.clientapi.service.ShService;
import com.greatwall.clientapi.service.ShunpayService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;
import com.greatwall.recharge.dto.Recharge;
import com.greatwall.recharge.service.RechargeConsumeService;
import com.greatwall.util.RMSConstant;

@Service("clientService")
public class ClientServiceIml implements ClientService {

	Logger logger = Logger.getLogger(ClientServiceIml.class);
	
	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
	
	@Autowired
	private CallbackNotifyService callbackNotifyService;

	@Autowired
	private LiulService liulService;
	@Autowired
	private ShunpayService shunpanService;
	@Autowired
	private JinPiaoService jinPiaoService;
	@Autowired
	private QiutongService qiutongService;
	@Autowired
	private ShService shService;

	@Autowired
	private RechargeConsumeService rechargeConsumeService;

	public String searchState(ConsumeConditions consume) throws Exception{
		String status = "";//查询状态
		String statuslist = "";//金飘接口查询状态列表
		String shStatuslist = "";//sh接口查询状态列表
		String finalstatus = "";//转换成的内部状态
		String searchstatus = "";//为空时查询错误，其他查询成功
		try {
			if(RMSConstant.INTERFACE_NAME_LIUL.equals(consume.getInterfaceName())){
				status = liulService.searchState(consume);
			}else if(RMSConstant.INTERFACE_NAME_SHUNPAY.equals(consume.getInterfaceName())){
				status = shunpanService.searchState(consume);
			}else if(RMSConstant.INTERFACE_NAME_QIUTONG.equals(consume.getInterfaceName())){
				status = qiutongService.searchState(consume);
			}else if(RMSConstant.INTERFACE_NAME_JINPIAO.equals(consume.getInterfaceName())){
				statuslist = jinPiaoService.searchState();
			}else if(RMSConstant.INTERFACE_NAME_SH.equals(consume.getInterfaceName())){
				shStatuslist = shService.searchState();
			}
		} catch (Exception e) {
			logger.error("调研查询接口错误", e);
			status = "";
		}
		if(StringUtils.isEmpty(status)){
			System.out.println("接口查询状态："+statuslist);
		}else{
			System.out.println("接口查询状态："+status);
		}
		//		String status = RMSConstant.CONSUME_STATE_SENDED_FAIL;
		if(StringUtils.isNotBlank(status)){
			finalstatus = updateStatus(consume,status);
			System.out.println("finalstatus："+finalstatus);
			if(finalstatus.contains(RMSConstant.CONSUME_STATE_FAIL)){
				run(fixedThreadPool,consume,"00");
			}else if(finalstatus.contains(RMSConstant.CONSUME_STATE_SUC)){
				run(fixedThreadPool,consume,"01");
			}else{
				logger.info(consume.getInterfaceName() +" 查询中间状态 ："+status);
			}
			searchstatus = finalstatus;
		}else if(StringUtils.isNotBlank(statuslist)){
			searchstatus = updateStatusJinpiao(statuslist,finalstatus);
		}else if(StringUtils.isNotBlank(shStatuslist)){
			searchstatus = updateStatusSh(shStatuslist,finalstatus);
		}else{
			System.out.println("返回值不匹配 ");
		}
		
		return searchstatus;
	}
	
	/**
	 * 金飘接口状态更新
	 * @param statuslist
	 * @param finalstatus
	 * @return
	 * @throws Exception
	 */
	private String updateStatusJinpiao(String statuslist,String finalstatus) throws Exception{
		Gson gson = new Gson();
		Map map = gson.fromJson(statuslist, Map.class);
		
		List list = (List) map.get("Reports");
		for(int i=0;i<list.size();i++){
			Map m = (Map)list.get(i);
//			System.out.println(String.format("%.0f ", m.get("TaskID")).trim());
			Consume consu = new Consume();
			consu.setInterfaceName(RMSConstant.INTERFACE_NAME_JINPIAO);
			consu.setRemark(String.format("%.0f ", m.get("TaskID")).trim());
			
//			Consume cons = rechargeConsumeService.getConsumeBy(consu);
			ConsumeConditions cons = rechargeConsumeService.getConsumeConditions(consu);
			if(cons!=null){
				if("success".equals(cons.getState())){
					continue;
				}
				String rest = String.format("%.0f ", m.get("Status")).trim();
//				System.out.println(rest);
				if("5".equals(rest)){
					finalstatus = RMSConstant.CONSUME_STATE_FAIL;
					rechargeReturn(cons,finalstatus);
					run(fixedThreadPool,cons,"00");
				}else if("4".equals(rest)){
					finalstatus = RMSConstant.CONSUME_STATE_SUC;
					rechargeConsumeService.confirmConsume(cons.getConsumeId(), finalstatus);
					run(fixedThreadPool,cons,"01");
				}else{
					logger.info(RMSConstant.INTERFACE_NAME_JINPIAO +" 查询中间状态 ："+rest);
				}
			}
		}
		
		if("0".equals(map.get("Code"))){
			return "search";
		}else{
			return "error";
		}
	}
	/**
	 * sh接口状态更新
	 * @param statuslist
	 * @param finalstatus
	 * @return
	 * @throws Exception
	 */
	private String updateStatusSh(String statuslist,String finalstatus) throws Exception{
		Gson gson = new Gson();
		Map map = gson.fromJson(statuslist, Map.class);
		
		List list = (List) map.get("Reports");
		for(int i=0;i<list.size();i++){
			Map m = (Map)list.get(i);
//			System.out.println(String.format("%.0f ", m.get("TaskID")).trim());
			Consume consu = new Consume();
			consu.setInterfaceName(RMSConstant.INTERFACE_NAME_SH);
			consu.setRemark(String.format("%.0f ", m.get("TaskID")).trim());
			
//			Consume cons = rechargeConsumeService.getConsumeBy(consu);
			ConsumeConditions cons = rechargeConsumeService.getConsumeConditions(consu);
			if(cons!=null){
				if("success".equals(cons.getState())){
					continue;
				}
				String rest = String.format("%.0f ", m.get("Status")).trim();
//				System.out.println(rest);
				if("5".equals(rest)){
					finalstatus = RMSConstant.CONSUME_STATE_FAIL;
					rechargeReturn(cons,finalstatus);
					run(fixedThreadPool,cons,"00");
				}else if("4".equals(rest)){
					finalstatus = RMSConstant.CONSUME_STATE_SUC;
					rechargeConsumeService.confirmConsume(cons.getConsumeId(), finalstatus);
					run(fixedThreadPool,cons,"01");
				}else{
					logger.info(RMSConstant.INTERFACE_NAME_SH +" 查询中间状态 ："+rest);
				}
			}
		}
		
		if("0".equals(map.get("Code"))){
			return "search";
		}else{
			return "error";
		}
	}

	public String updateStatus(Consume consume,String status) throws Exception{
		if(RMSConstant.CONSUME_STATE_SENDED_FAIL.equals(status)){
			//如果充值失败需要冲抵余额
			Consume cons =rechargeConsumeService.getConsume(consume.getConsumeId());
			status = RMSConstant.CONSUME_STATE_FAIL;
			if(cons==null){
				return status;
			}
			rechargeReturn(cons,status);
			
		}else{
			rechargeConsumeService.confirmConsume(consume.getConsumeId(), status);
		}

		return status;
	}
	
	public Boolean rechargeReturn(Consume cons,String status) throws Exception{
		Recharge recharge = new Recharge();
		recharge.setConsumeId(cons.getConsumeId());
		Recharge rc =rechargeConsumeService.getRecharge(recharge);

		if(rc==null){
			Recharge rec = new Recharge();
			rec.setCreateTime(new Date());
			rec.setRemark("充值失败，系统自动退款");
			rec.setOperator(cons.getUserId());
			rec.setUserId(cons.getUserId());
			rec.setType(cons.getConsumeType());
			rec.setRechargeAmount(cons.getPayment());
			rec.setConsumeId(cons.getConsumeId());
			rechargeConsumeService.saveRecharge(rec, null);

			cons.setState(status);
			cons.setBalance(cons.getBalance()+cons.getPayment());
			//cons.setRemark("");
			rechargeConsumeService.updateState(cons);
			return true;
		}
		return false;
	}
	
	private void run(ExecutorService threadPool,final ConsumeConditions consumeConditions,final String opstatus) {
		threadPool.execute(new Runnable() {  
			@Override
			public void run() {  
				try {  
					callbackNotifyService.callbackNotify(consumeConditions, opstatus);
				} catch (Exception e) {  
					logger.error("接口名： "+consumeConditions.getInterfaceName()+"充值状态回调错误", e);
				}finally{
					
				}


			}  
		});  
		//threadPool.shutdown();// 任务执行完毕，关闭线程池  
	}
}

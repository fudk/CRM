package com.greatwall.clientapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.greatwall.clientapi.service.ClientService;
import com.greatwall.clientapi.service.JinPiaoService;
import com.greatwall.clientapi.service.LiulService;
import com.greatwall.clientapi.service.ShunpayService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.Recharge;
import com.greatwall.recharge.service.RechargeConsumeService;
import com.greatwall.util.RMSConstant;

@Service("clientService")
public class ClientServiceIml implements ClientService {

	Logger logger = Logger.getLogger(ClientServiceIml.class);

	@Autowired
	private LiulService liulService;
	@Autowired
	private ShunpayService shunpanService;
	@Autowired
	private JinPiaoService jinPiaoService;

	@Autowired
	private RechargeConsumeService rechargeConsumeService;

	public String searchState(Consume consume) throws Exception{
		String status = "";
		String statuslist = "";
		try {
			if(RMSConstant.INTERFACE_NAME_LIUL.equals(consume.getInterfaceName())){
				status = liulService.searchState(consume);
			}else if(RMSConstant.INTERFACE_NAME_SHUNPAY.equals(consume.getInterfaceName())){
				status = shunpanService.searchState(consume);
			}else if(RMSConstant.INTERFACE_NAME_JINPIAO.equals(consume.getInterfaceName())){
				statuslist = jinPiaoService.searchState();
			}
		} catch (Exception e) {
			logger.error("调研查询接口错误", e);
			status = RMSConstant.CONSUME_STATE_ERROR;
		}
		System.out.println("接口查询状态："+status);
		//		String status = RMSConstant.CONSUME_STATE_SENDED_FAIL;
		if(StringUtils.isNotBlank(status)){
			status = updateStatus(consume,status);
		}else if(StringUtils.isNotBlank(statuslist)){
			
			Gson gson = new Gson();
			Map map = gson.fromJson(statuslist, Map.class);
			
			if("0".equals(map.get("Code"))){
				status = RMSConstant.CONSUME_STATE_SUC;
			}

			List list = (List) map.get("Reports");
			for(int i=0;i<list.size();i++){
				Map m = (Map)list.get(i);
				System.out.println(String.format("%.0f ", m.get("TaskID")).trim());
				Consume consu = new Consume();
				consu.setInterfaceName(RMSConstant.INTERFACE_NAME_JINPIAO);
				consu.setRemark(String.format("%.0f ", m.get("TaskID")).trim());
				
				Consume cons = rechargeConsumeService.getConsumeBy(consu);
				if(cons!=null){
					String rest = String.format("%.0f ", m.get("Status")).trim();
					System.out.println(rest);
					if("5".equals(rest)){
						status = RMSConstant.CONSUME_STATE_FAIL;
						rechargeReturn(cons,status);
					}else if("4".equals(rest)){
						status = RMSConstant.CONSUME_STATE_SUC;
						rechargeConsumeService.confirmConsume(cons.getConsumeId(), status);
					}
				}
			}
		}

		return status;
	}

	private String updateStatus(Consume consume,String status) throws Exception{
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
	
	private Boolean rechargeReturn(Consume cons,String status) throws Exception{
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
}

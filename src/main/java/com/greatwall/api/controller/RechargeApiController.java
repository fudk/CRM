package com.greatwall.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.greatwall.api.domain.LiulNotify;
import com.greatwall.api.domain.RechargeCondition;
import com.greatwall.clientapi.service.LiulService;
import com.greatwall.clientapi.service.ShunpayService;
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.UserService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.Product;
import com.greatwall.recharge.dto.UserChannel;
import com.greatwall.recharge.service.ProductService;
import com.greatwall.recharge.service.RechargeConsumeService;
import com.greatwall.recharge.service.UserChannelService;
import com.greatwall.util.MathUtil;
import com.greatwall.util.NetworkUtil;
import com.greatwall.util.PhoneUtil;
import com.greatwall.util.RMSConstant;
import com.greatwall.util.StringUtil;

@Controller
@RequestMapping("/rechargeapi")
public class RechargeApiController {

	Logger logger = Logger.getLogger(RechargeApiController.class);
			
	@Autowired
	private ShunpayService shunpanService;
	@Autowired
	private RechargeConsumeService rechargeConsumeService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PhoneUtil phoneUtil;
	@Autowired
	private LiulService liulService;
	@Autowired
	private UserChannelService userChannelService;
	
	@RequestMapping("/resultQuery")
	public@ResponseBody Map<String,String> resultQuery(String platId,String timestamp,String orderId,String sign){
		
		Map<String,String> remap = new HashMap<String,String>();
		if(StringUtils.isBlank(platId)||StringUtils.isBlank(timestamp)
				||StringUtils.isBlank(orderId)||StringUtils.isBlank(sign)){
			remap.put("retcode", "05");
			remap.put("retmsg", "参数不能为空");
			return remap;
		}
		
		User u = userService.getUser(platId);
		if(u==null){
			remap.put("retcode", "03");
			remap.put("retmsg", "用户不存在");
			return remap;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("platId=");
		sb.append(platId);
		sb.append("&timestamp=");
		sb.append(timestamp);
		sb.append("&orderId=");
		sb.append(orderId);
		sb.append("&");
		sb.append(u.getSessionKey());
		
		if(!DigestUtils.md5Hex(sb.toString()).equals(sign)){
			remap.put("retcode", "06");
			remap.put("retmsg", "校验失败");
			return remap;
		}
		
		Consume co = new Consume();
		co.setOrderId(platId+"_"+orderId);
		Consume consume = rechargeConsumeService.getConsumeBy(co);
		if(consume==null){
			remap.put("retcode", "04");
			remap.put("retmsg", "单号不存在");
			return remap;
		}
		
		if(RMSConstant.CONSUME_STATE_SUC.equals(consume.getState())){
			remap.put("retcode", "01");
			remap.put("retmsg", "充值成功");
			return remap;
		} else if(consume.getState().contains("fail")||consume.getState().contains("error")){
			remap.put("retcode", "00");
			remap.put("retmsg", "充值失败");
			return remap;
		}else{
			remap.put("retcode", "02");
			remap.put("retmsg", "充值中");
			return remap;
		}
	}
	
	
	
	@RequestMapping("/flowCallbackNotify")
	public@ResponseBody String flowCallbackNotify(LiulNotify liulNotify){
		if(liulNotify!=null && liulNotify.getReq_id()!=null){
			StringBuffer signData = new StringBuffer();
			signData.append(liulNotify.getChar_set());
			signData.append(liulNotify.getMerc_id());
			signData.append(liulNotify.getReq_id());
			signData.append(liulNotify.getReq_dt());
			signData.append(liulNotify.getSign_typ());
			signData.append(liulNotify.getItf_code());
			signData.append(liulNotify.getMbl_no());
			signData.append(liulNotify.getFlx_typ());
			signData.append(liulNotify.getFlx_num());
			signData.append(liulNotify.getChg_sts());
			signData.append(liulNotify.getDebit_amt());
			
			String hmac = liulService.getHmac(signData.toString());
			if(!hmac.equals(liulNotify.getHmac())){
				System.out.println(hmac);
				return "FAIL";
			}
			String status = "";
			if("S".equals(liulNotify.getChg_sts().toUpperCase())){
				status = RMSConstant.CONSUME_STATE_SUC;
			}else if("F".equals(liulNotify.getChg_sts().toUpperCase())){
				status = RMSConstant.CONSUME_STATE_SENDED_FAIL;
			}
			if(!"".equals(status)){
//				Consume consume = rechargeConsumeService.getConsume(liulNotify.getReq_id());
//				if(consume!=null){
//					consume.setState(status);
//					rechargeConsumeService.updateState(consume);
//				}
//				User u = userService.getUser(consume.getUserId());
				rechargeConsumeService.confirmConsume(liulNotify.getReq_id(), status);
			}
			return "SUCCESS";
			
		}
		 
		return "FAIL";
	}
	/** 
	* @Title: callbackNotify 
	* @Description: 舜联通信充值回调接口 
	* @param streameid
	* @param thirdstreamid
	* @param opstatus
	* @param sign
	* @return    设定文件
	* @return Map<String,String>    返回类型 
	* @throws 
	*/
	@RequestMapping("/callbacknotify")
	public@ResponseBody Map<String,String> callbackNotify(String streameid,
			String thirdstreamid,String opstatus,String sign){
		
		StringBuffer sb = new StringBuffer();
		sb.append("streameid=");
		sb.append(streameid);
		sb.append("&thirdstreamid=");
		sb.append(thirdstreamid);
		sb.append("&opstatus=");
		sb.append(opstatus);
		sb.append("&");
		sb.append(shunpanService.getKey());
		
		//TODO 失败后需要还回余额
		Map<String,String> remap = new HashMap<String,String>();
		if(DigestUtils.md5Hex(sb.toString()).equals(sign)){
			String status = "";
			if("1".equals(opstatus)){
				status = RMSConstant.CONSUME_STATE_SUC;
			}else if("0".equals(opstatus)){
				status = RMSConstant.CONSUME_STATE_SENDED_FAIL;
			}
			if(StringUtils.isNotBlank(status)){
				rechargeConsumeService.confirmConsume(thirdstreamid, status);
				remap.put("retcode", "10000000");
				remap.put("retmsg", "交易成功");
			}
			
		}else{
			remap.put("retcode", "10000001");
			remap.put("retmsg", "交易失败");
		}
		
		return remap;
	}
	
	
    
	@RequestMapping(value = "/wt/recharge",method = { RequestMethod.GET, RequestMethod.POST })
	public void recharge(RechargeCondition rechargeCondition,
			HttpServletRequest request,HttpServletResponse response){
		
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragrma", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
        	
        	Map m = this.rechargeApi(rechargeCondition, request);
        	if(!"01".equals(m.get("resCode"))){
        		response.setHeader("paycut", "-1");
        	}
        	Gson gson = new Gson();
			response.getWriter().write(gson.toJson(m));
			response.getWriter().flush();  
	        response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	/** 
	* @Title: recharge 
	* @Description: 对外提供的充值接口 
	* @param rechargeCondition
	* @param request
	* @return    设定文件
	* @return Map<String,String>    返回类型 
	* @throws 
	*/
	@RequestMapping(value = "/recharge",method = { RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody Map<String,String> recharge(RechargeCondition rechargeCondition,HttpServletRequest request){
		
		Map<String,String> remap = new HashMap<String,String>();
		if(rechargeCondition==null){
			remap.put("resCode", "02");
			remap.put("resMsg", "校验失败");
			return remap;
		}
		User u = null;
		if(rechargeCondition.getPlatId()!=null){
			u = userService.getUser(rechargeCondition.getPlatId());
		}else{
			remap.put("resCode", "06");
			remap.put("resMsg", "platId不能为空");
			return remap;
		}
		
		if(u == null || StringUtils.isBlank(u.getLoginName())){
			remap.put("rescode", "04");
			remap.put("resMsg", "用户不存在");
			return remap;
		}
		
		String ip = NetworkUtil.getIpAddress(request);
		if(!"127.0.0.1".equals(u.getPermitIp())){
			if(StringUtils.isBlank(ip)||!ip.equals(u.getPermitIp())){
				remap.put("resCode", "03");
				remap.put("resMsg", "IP地址未授权");
				return remap;
			}
		}
		
		if(!StringUtil.authRechargeCondition(rechargeCondition, u.getSessionKey())){
			remap.put("resCode", "02");
			remap.put("resMsg", "校验失败");
			return remap;
		}
		
		String[] phones = StringUtil.getRepStrings(rechargeCondition.getCustPhone());
		
		Product product = new Product();
		if("phone".equals(rechargeCondition.getOpType())){
			product.setProductPrice(new Double(rechargeCondition.getOpPrice()));
		}else{
			product.setProductValue(rechargeCondition.getOpPrice());
		}
		String isp = phoneUtil.isPhoneNum(phones[0]);
		product.setState("enable");
		product.setProductValidity(rechargeCondition.getFlxTyp());
		product.setIsp(isp);
		product.setProductType(rechargeCondition.getOpType());
		product = productService.getProduct(product);
		
		if(product==null){
			remap.put("resCode", "07");
			remap.put("resMsg", "充值产品不存在");
			return remap;
		}
		
		Consume consume = new Consume();
		consume.setDiscount(RMSConstant.DEFAULT_DISCOUNT);//默认折扣
		
		UserChannel userChannel = new UserChannel();
		userChannel.setUserId(u.getUserId());
		userChannel.setIsp(isp);
		userChannel.setType(rechargeCondition.getOpType());
		List<UserChannel> uclist = userChannelService.getUserChannel(userChannel);
		String interfaceName = "";
		if(uclist!=null&&uclist.size()>0){
			for(UserChannel uc:uclist){
				if(isp.equals(uc.getIsp())){
					interfaceName = uc.getInterfaceName();
					consume.setDiscount(uc.getDiscount());
				}
			}
		}
		if("".equals(interfaceName)){
			remap.put("resCode", "08");
			remap.put("resMsg", "用户未配置通道");
			return remap;
		}
		
		
		
		consume.setProductId(product.getProductId());
		consume.setProductName(product.getProductName());
		consume.setProductValue(product.getProductValue());
		consume.setConsumePrice(product.getProductPrice());
		consume.setProductValidity(product.getProductValidity());
		consume.setConsumeNum(rechargeCondition.getOpNum());//默认消费数量为1个
		consume.setConsumeAmount(MathUtil.mul(product.getProductPrice(), new Double(1), 2));
		consume.setConsumeType(product.getProductType());
		consume.setIsp(product.getIsp());
		consume.setState(RMSConstant.CONSUME_STATE_PROCESSING);//处理中
		consume.setUserId(u.getUserId());
		consume.setInterfaceName(interfaceName);
		consume.setNotifyUrl(rechargeCondition.getNotifyUrl());
		consume.setOrderId(rechargeCondition.getPlatId()+"_"+rechargeCondition.getOrderId());
		consume.setProductParam(product.getQtProductId());
		
		List<String> errorMsgs = new ArrayList<String>();
		//获取相应产品信息
		
		for(String phone : phones){
			try {
				//如果电话号码与产品运营商不匹配
				if(!consume.getIsp().toUpperCase().equals(phoneUtil.isPhoneNum(phone))){
					throw new ClassCastException("手机号码与运营商不匹配");
				}
				consume.setConsumePhone(phone);
				
				rechargeConsumeService.addConsume(consume);
			}catch (Exception e) {
				logger.error(phone,e);
				errorMsgs.add(phone+" "+e.getMessage());
			}
		}

		if(errorMsgs!=null&&errorMsgs.size()>0){
			Gson gson = new Gson();
			
			String emsg = gson.toJson(errorMsgs);
			remap.put("resCode", "09");
			if(emsg.contains("code102")){
				remap.put("resCode", "102");
			}else if(emsg.contains("code104")){
				remap.put("resCode", "104");
			}
			remap.put("resMsg", emsg );
			remap.put("msg", "共"+(phones.length-errorMsgs.size())+"条保存成功，共"+errorMsgs.size()+"条保存失败。");
		}else{
			remap.put("resCode", "01");
			remap.put("resMsg", "提交成功");
		}
		
		return remap;
	}
	
	
	/**
	 * 测试用
	 * @param consumeId
	 * @param orderId
	 * @param opstatus
	 * @param sign
	 * @return
	 */
	@RequestMapping("/callback")
	public@ResponseBody Map<String,String> callback(String consumeId,
			String orderId,String opstatus,String sign){
		
		StringBuffer sb = new StringBuffer();
		sb.append("consumeId=");
		sb.append(consumeId);
		sb.append("&orderId=");
		sb.append(orderId);
		sb.append("&opstatus=");
		sb.append(opstatus);
		sb.append("&");
		sb.append("C6914624EB90000116D71D90141B3FC0");
		
		Map<String,String> remap = new HashMap<String,String>();
		if(DigestUtils.md5Hex(sb.toString()).equals(sign)){
			String status = "";
			if("1".equals(opstatus)){
				status = RMSConstant.CONSUME_STATE_SUC;
			}else if("0".equals(opstatus)){
				status = RMSConstant.CONSUME_STATE_SENDED_FAIL;
			}
			if(StringUtils.isNotBlank(status)){
				//TODO 保存充值状态
				remap.put("retcode", "01");
				remap.put("retmsg", "测试成功");
			}
			
		}else{
			remap.put("retcode", "00");
			remap.put("retmsg", "测试失败");
		}
		return remap;
	}
	
	private Map<String,String> rechargeApi(RechargeCondition rechargeCondition,HttpServletRequest request){
		Map<String,String> remap = new HashMap<String,String>();
		if(rechargeCondition==null){
			remap.put("resCode", "02");
			remap.put("resMsg", "校验失败");
			return remap;
		}
		User u = null;
		if(rechargeCondition.getPlatId()!=null){
			u = userService.getUser(rechargeCondition.getPlatId());
		}else{
			remap.put("resCode", "06");
			remap.put("resMsg", "platId不能为空");
			return remap;
		}
		
		if(u == null || StringUtils.isBlank(u.getLoginName())){
			remap.put("rescode", "04");
			remap.put("resMsg", "用户不存在");
			return remap;
		}
		
		String ip = NetworkUtil.getIpAddress(request);
		if(!"127.0.0.1".equals(u.getPermitIp())){
			if(StringUtils.isBlank(ip)||!ip.equals(u.getPermitIp())){
				remap.put("resCode", "03");
				remap.put("resMsg", "IP地址未授权");
				return remap;
			}
		}
		
		if(!StringUtil.authRechargeCondition(rechargeCondition, u.getSessionKey())){
			remap.put("resCode", "02");
			remap.put("resMsg", "校验失败");
			return remap;
		}
		
		String[] phones = StringUtil.getRepStrings(rechargeCondition.getCustPhone());
		
		Product product = new Product();
		if("phone".equals(rechargeCondition.getOpType())){
			product.setProductPrice(new Double(rechargeCondition.getOpPrice()));
		}else{
			product.setProductValue(rechargeCondition.getOpPrice());
		}
		String isp = phoneUtil.isPhoneNum(phones[0]);
		product.setState("enable");
		product.setProductValidity(rechargeCondition.getFlxTyp());
		product.setIsp(isp);
		product.setProductType(rechargeCondition.getOpType());
		product = productService.getProduct(product);
		
		if(product==null){
			remap.put("resCode", "07");
			remap.put("resMsg", "充值产品不存在");
			return remap;
		}
		
		Consume consume = new Consume();
		consume.setDiscount(RMSConstant.DEFAULT_DISCOUNT);//默认折扣
		
		UserChannel userChannel = new UserChannel();
		userChannel.setUserId(u.getUserId());
		userChannel.setIsp(isp);
		userChannel.setType(rechargeCondition.getOpType());
		List<UserChannel> uclist = userChannelService.getUserChannel(userChannel);
		String interfaceName = "";
		if(uclist!=null&&uclist.size()>0){
			for(UserChannel uc:uclist){
				if(isp.equals(uc.getIsp())){
					interfaceName = uc.getInterfaceName();
					consume.setDiscount(uc.getDiscount());
				}
			}
		}
		if("".equals(interfaceName)){
			remap.put("resCode", "08");
			remap.put("resMsg", "用户未配置通道");
			return remap;
		}
		
		
		
		consume.setProductId(product.getProductId());
		consume.setProductName(product.getProductName());
		consume.setProductValue(product.getProductValue());
		consume.setConsumePrice(product.getProductPrice());
		consume.setProductValidity(product.getProductValidity());
		consume.setConsumeNum(rechargeCondition.getOpNum());//默认消费数量为1个
		consume.setConsumeAmount(MathUtil.mul(product.getProductPrice(), new Double(1), 2));
		consume.setConsumeType(product.getProductType());
		consume.setIsp(product.getIsp());
		consume.setState(RMSConstant.CONSUME_STATE_PROCESSING);//处理中
		consume.setUserId(u.getUserId());
		consume.setInterfaceName(interfaceName);
		consume.setNotifyUrl(rechargeCondition.getNotifyUrl());
		consume.setOrderId(rechargeCondition.getPlatId()+"_"+rechargeCondition.getOrderId());
		
		List<String> errorMsgs = new ArrayList<String>();
		//获取相应产品信息
		
		for(String phone : phones){
			try {
				//如果电话号码与产品运营商不匹配
				if(!consume.getIsp().toUpperCase().equals(phoneUtil.isPhoneNum(phone))){
					throw new ClassCastException("手机号码与运营商不匹配");
				}
				consume.setConsumePhone(phone);
				
				rechargeConsumeService.addConsume(consume);
			}catch (Exception e) {
				logger.error(phone,e);
				errorMsgs.add(phone+" "+e.getMessage());
			}
		}

		if(errorMsgs!=null&&errorMsgs.size()>0){
			Gson gson = new Gson();
			
			String emsg = gson.toJson(errorMsgs);
			remap.put("resCode", "09");
			if(emsg.contains("code102")){
				remap.put("resCode", "102");
			}else if(emsg.contains("code104")){
				remap.put("resCode", "104");
			}
			remap.put("resMsg", emsg );
			remap.put("msg", "共"+(phones.length-errorMsgs.size())+"条保存成功，共"+errorMsgs.size()+"条保存失败。");
		}else{
			remap.put("resCode", "01");
			remap.put("resMsg", "提交成功");
		}
		return remap;
	}
	
}

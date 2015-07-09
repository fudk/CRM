package com.greatwall.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.UserService;
import com.greatwall.recharge.client.LiulService;
import com.greatwall.recharge.client.ShunpanService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.Product;
import com.greatwall.recharge.service.ProductService;
import com.greatwall.recharge.service.RechargeConsumeService;
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
	private ShunpanService shunpanService;
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
	
	@RequestMapping("/callbacknotify")
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
			if(hmac.equals(liulNotify.getHmac())){
				System.out.println(hmac);
			}
			String status = "";
			if("S".equals(liulNotify.getChg_sts().toUpperCase())){
				status = RMSConstant.CONSUME_STATE_SUC;
			}else if("S".equals(liulNotify.getChg_sts().toUpperCase())){
				status = RMSConstant.CONSUME_STATE_SENDED_FAIL;
			}
			if(!"".equals(status)){
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
		
		Map<String,String> remap = new HashMap<String,String>();
		if(DigestUtils.md5Hex(sb.toString()).equals(sign)){
			String status = "1".equals(opstatus)?RMSConstant.CONSUME_STATE_SUC:RMSConstant.CONSUME_STATE_FAIL;
			rechargeConsumeService.confirmConsume(thirdstreamid, status);
			remap.put("retcode", "10000000");
			remap.put("retmsg", "交易成功");
		}else{
			remap.put("retcode", "10000001");
			remap.put("retmsg", "交易失败");
		}
		
		return remap;
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
		
		product.setIsp(phoneUtil.isPhoneNum(phones[0]));
		product.setProductType(rechargeCondition.getOpType());
		product = productService.getProduct(product);
		
		Consume consume = new Consume();
		
		consume.setProductId(product.getProductId());
		consume.setProductName(product.getProductName());
		consume.setProductValue(product.getProductValue());
		consume.setConsumePrice(product.getProductPrice());
		consume.setConsumeNum(rechargeCondition.getOpNum());//默认消费数量为1个
		consume.setConsumeAmount(MathUtil.mul(product.getProductPrice(), new Double(1), 2));
		consume.setConsumeType(product.getProductType());
		consume.setIsp(product.getIsp());
		consume.setState("processing");//处理中
		consume.setUserId(u.getUserId());
		
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
					
			}catch (ClassCastException e) {
				logger.error(phone+" "+e.getMessage());
				errorMsgs.add(phone+" "+e.getMessage());
			}catch (Exception e) {
				logger.error(phone+" 消费错误 ",e);
				errorMsgs.add(phone+" "+e.getMessage());
			}
		}

		if(errorMsgs!=null&&errorMsgs.size()>0){
			Gson gson = new Gson();
			remap.put("resCode", "05");
			remap.put("resMsg", gson.toJson(errorMsgs) );
			remap.put("msg", phones.length-errorMsgs.size()+"保存成功，"+errorMsgs.size()+"保存失败。");
		}else{
			remap.put("resCode", "01");
			remap.put("resMsg", "充值成功");
		}
		
		return remap;
	}
}

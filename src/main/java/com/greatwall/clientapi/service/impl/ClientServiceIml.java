package com.greatwall.clientapi.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.clientapi.service.ClientService;
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
	private RechargeConsumeService rechargeConsumeService;

	public String searchState(Consume consume) throws Exception{
		String status = "";
		try {
			if(RMSConstant.INTERFACE_NAME_LIUL.equals(consume.getInterfaceName())){
				status = liulService.searchState(consume);
			}else if(RMSConstant.INTERFACE_NAME_SHUNPAY.equals(consume.getInterfaceName())){
				status = shunpanService.searchState(consume);
			}
		} catch (Exception e) {
			logger.error("调研查询接口错误", e);
			status = RMSConstant.CONSUME_STATE_ERROR;
		}
		System.out.println("接口查询状态："+status);
		//		String status = RMSConstant.CONSUME_STATE_SENDED_FAIL;
		if(StringUtils.isNotBlank(status)){
			if(RMSConstant.CONSUME_STATE_SENDED_FAIL.equals(status)){
				//如果充值失败需要冲抵余额
				Consume cons =rechargeConsumeService.getConsume(consume.getConsumeId());

				if(cons.getState().contains(RMSConstant.CONSUME_STATE_SENDED)){//如果接口已经调用，而且状态为失败才能冲抵，并且只能冲抵1次
					Recharge rec = new Recharge();
					rec.setCreateTime(new Date());
					rec.setRemark("充值失败，系统自动余额冲抵");
					rec.setOperator(cons.getUserId());
					rec.setUserId(cons.getUserId());
					rec.setType(cons.getConsumeType());
					rec.setRechargeAmount(cons.getPayment());

					rechargeConsumeService.saveRecharge(rec, null);
					status = RMSConstant.CONSUME_STATE_FAIL;
				}
			}
			rechargeConsumeService.confirmConsume(consume.getConsumeId(), status);
		}

		return status;
	}
}

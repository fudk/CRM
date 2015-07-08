package com.greatwall.recharge.service.impl;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.greatwall.api.service.RechargeService;


@WebService(endpointInterface="com.greatwall.api.service.RechargeService")
@SOAPBinding(style = Style.RPC)
public class DefaultRechargeService implements RechargeService {


	@Override  
	public int add(int num1, int num2)  
	{  
		System.out.println("123213123");
		return num1 + num2;  
	}
}

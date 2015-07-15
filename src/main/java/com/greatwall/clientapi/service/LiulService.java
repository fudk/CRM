package com.greatwall.clientapi.service;

import com.greatwall.recharge.dto.Consume;

public interface LiulService {

	public Boolean sendMsg(Consume consume) throws Exception;
	
	public String searchState(Consume consume) throws Exception;
	
	public String getHmac(String signData);
	
}

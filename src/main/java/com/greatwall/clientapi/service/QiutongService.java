package com.greatwall.clientapi.service;

import com.greatwall.recharge.dto.Consume;

public interface QiutongService {

	public Boolean sendMsg(Consume consume) throws Exception;

	String searchState(Consume consume) throws Exception;
	
	
}

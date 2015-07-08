package com.greatwall.recharge.client;

import com.greatwall.recharge.dto.Consume;

public interface ShunpanService {

	public Boolean sendMsg(Consume consume) throws Exception;
	
	public String getKey();
}

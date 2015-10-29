package com.greatwall.clientapi.service;

import com.greatwall.recharge.dto.Consume;

public interface ClientService {

	public String searchState(Consume consume) throws Exception;
	
	public Boolean rechargeReturn(Consume cons,String status) throws Exception;
}

package com.greatwall.clientapi.service;

import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;

public interface ClientService {

	public String searchState(ConsumeConditions consume) throws Exception;
	
	public Boolean rechargeReturn(Consume cons,String status) throws Exception;
}

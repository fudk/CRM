package com.greatwall.api.service;

import com.greatwall.recharge.dto.ConsumeConditions;

public interface CallbackNotifyService {

	public Boolean callbackNotify(ConsumeConditions consumeConditions,String opstatus) throws Exception;
}

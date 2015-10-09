package com.greatwall.api.service;

import com.greatwall.recharge.dto.ConsumeConditions;

public interface CallbackNotifyService {

	public Boolean callbackNotify(ConsumeConditions consume,String opstatus) throws Exception;
}

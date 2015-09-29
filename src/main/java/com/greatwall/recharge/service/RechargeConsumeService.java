package com.greatwall.recharge.service;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;
import com.greatwall.recharge.dto.Recharge;
import com.greatwall.recharge.dto.RechargeConditions;


public interface RechargeConsumeService {

	public Boolean saveRecharge(Recharge recharge ,Integer agentId) throws Exception;
	
	public List<RechargeConditions> getRechargesPage(RechargeConditions rechargeConditions,PageParameter page) throws DaoException;

	public Boolean addConsume(Consume consume) throws Exception;

	public List<ConsumeConditions> getConsumesPage(ConsumeConditions consume, PageParameter page)
			throws DaoException;
	
	public Consume getConsume(String consumeId);
	
	public Boolean confirmConsume(String consumeId,String opstatus);
	
	public List<ConsumeConditions> getConsumesByStatePage(ConsumeConditions consume,PageParameter page) throws DaoException;
	
	public Boolean updateState(Consume consume);
	
	public Consume getConsumeBy(Consume consume);
	
	public Recharge getRecharge(Recharge recharge);
}

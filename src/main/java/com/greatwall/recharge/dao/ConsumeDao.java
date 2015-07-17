package com.greatwall.recharge.dao;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;

public interface ConsumeDao {

	public int insert(Consume consume);
	
	public int update(Consume consume);
	
	public int updateState(Consume consume);
	
	public List<ConsumeConditions> getConsumes(ConsumeConditions consume,PageParameter page) throws DaoException;
	
	public List<ConsumeConditions> getConsumesByStatePage(ConsumeConditions consume, PageParameter page)
			throws DaoException ;
	
	public Consume getConsume(String consumeId);
	
	public Consume getConsumeBy(Consume consume);
}

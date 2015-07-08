package com.greatwall.recharge.dao;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Recharge;
import com.greatwall.recharge.dto.RechargeConditions;


public interface RechargeDao {
	
    public int insert(Recharge record);
    
    public List<RechargeConditions> getRechargesPage(RechargeConditions rechargeConditions,PageParameter page) throws DaoException;

}
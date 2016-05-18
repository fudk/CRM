package com.greatwall.recharge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dao.RechargeDao;
import com.greatwall.recharge.dto.Recharge;
import com.greatwall.recharge.dto.RechargeConditions;


@Repository
public class RechargeDaoImpl extends MyBatisDao implements RechargeDao {

	public int insert(Recharge recharge) {
		return this.save("RechargeMapper.insert",recharge);
	}
	
	public Recharge getRecharge(Recharge recharge){
		return this.get("RechargeMapper.selectRecharge", recharge);
	}

	public List<RechargeConditions> getRechargesPage(RechargeConditions rechargeConditions,PageParameter page) throws DaoException{
		return this.getListPage("RechargeMapper.selectByQueryPage", rechargeConditions, page);
	}
	
	public List<RechargeConditions> getUserRechargePage(RechargeConditions rechargeConditions,PageParameter page) throws DaoException{
		return this.getListPage("RechargeMapper.selectRechargePage", rechargeConditions, page);
	}
}

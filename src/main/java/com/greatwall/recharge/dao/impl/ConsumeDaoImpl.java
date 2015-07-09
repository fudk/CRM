package com.greatwall.recharge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dao.ConsumeDao;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;

@Repository
public class ConsumeDaoImpl extends MyBatisDao implements ConsumeDao {

	@Override
	public int insert(Consume consume) {
		return this.save("ConsumeMapper.insert", consume);
	}

	@Override
	public int update(Consume consume) {
		return this.update("ConsumeMapper.updateByPrimaryKeySelective", consume);
	}
	public int updateState(Consume consume) {
		return this.update("ConsumeMapper.updateState", consume);
	}

	@Override
	public List<ConsumeConditions> getConsumes(ConsumeConditions consume, PageParameter page)
			throws DaoException {
		return this.getListPage("ConsumeMapper.selectByQueryPage",consume,page);
	}
	
	public Consume getConsume(String consumeId){
		return this.get("ConsumeMapper.selectByPrimaryKey", consumeId);
	}

}

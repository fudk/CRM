package com.greatwall.recharge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.recharge.dao.UserChannelDao;
import com.greatwall.recharge.dto.UserChannel;

@Repository
public class UserChannelDaoImpl extends MyBatisDao implements UserChannelDao {

	public int insert(UserChannel userChannel) {
		return this.save("UserChannelMapper.insert",userChannel);
	}
	
	public List<UserChannel> getUserChannel(UserChannel userChannel){
		return this.getList("UserChannelMapper.getUserChannel", userChannel);
	}
	
	public int delUserChannel(UserChannel userChannel) throws DaoException {
		if(userChannel.getChannelId() ==null && userChannel.getUserId() == null){
			throw new DaoException("channelId and userId is null");
		}
		return this.delete("UserChannelMapper.delUserChannel",userChannel);
	}
	

}

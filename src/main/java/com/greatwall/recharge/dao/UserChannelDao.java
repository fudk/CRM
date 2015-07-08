package com.greatwall.recharge.dao;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.recharge.dto.UserChannel;


public interface UserChannelDao {
	
    public int insert(UserChannel userChannel);
    
    public List<UserChannel> getUserChannel(UserChannel userChannel);

    public int delUserChannel(UserChannel userChannel) throws DaoException;
//    public int insertSelective(UserChannel record);
}
package com.greatwall.recharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.recharge.dao.ChannelDao;
import com.greatwall.recharge.dto.Channel;
import com.greatwall.recharge.service.ChannelService;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService  {

	@Autowired
	private ChannelDao channelDao;
	
	public List<Channel> getChannelsIsp(String isp,String type){
		return channelDao.getChannelsIsp(isp,type);
	}
}

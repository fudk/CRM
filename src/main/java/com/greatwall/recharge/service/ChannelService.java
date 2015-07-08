package com.greatwall.recharge.service;

import java.util.List;

import com.greatwall.recharge.dto.Channel;


public interface ChannelService {

	public List<Channel> getChannelsIsp(String isp,String type);
}

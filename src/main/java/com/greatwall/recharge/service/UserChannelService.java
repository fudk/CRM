package com.greatwall.recharge.service;

import java.util.List;

import com.greatwall.recharge.dto.ChannelCondition;
import com.greatwall.recharge.dto.UserChannel;

public interface UserChannelService {

	public List<UserChannel> getUserChannel(UserChannel userChannel);
	
	public void saveUserChannels(Integer userId,ChannelCondition channelCondition);
	
}

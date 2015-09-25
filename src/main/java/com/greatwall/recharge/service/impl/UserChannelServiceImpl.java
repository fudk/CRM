package com.greatwall.recharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.recharge.dao.UserChannelDao;
import com.greatwall.recharge.dto.ChannelCondition;
import com.greatwall.recharge.dto.UserChannel;
import com.greatwall.recharge.service.UserChannelService;
import com.greatwall.util.RMSConstant;

@Service("userChannelService")
public class UserChannelServiceImpl implements UserChannelService {
	
	@Autowired
	private UserChannelDao userChannelDao;

	public List<UserChannel> getUserChannel(UserChannel userChannel){
		return userChannelDao.getUserChannel(userChannel);
	}
	
	public void saveUserChannels(Integer userId,List<UserChannel> userChannels){
		for(UserChannel userChannel:userChannels){
			userChannel.setUserId(userId);
			userChannelDao.insert(userChannel);
		}
	}
	
	public void saveUserChannels(Integer userId,ChannelCondition channelCondition){
		UserChannel userChannelCM = new UserChannel();
		userChannelCM.setIsp(RMSConstant.ISP_CM);
		userChannelCM.setChannelId(channelCondition.getCm());
		userChannelCM.setUserId(userId);
		userChannelCM.setType(RMSConstant.PRODUCT_TYPE_PHONE);
		userChannelDao.insert(userChannelCM);
		
		UserChannel userChannelCU = new UserChannel();
		userChannelCU.setIsp(RMSConstant.ISP_CU);
		userChannelCU.setChannelId(channelCondition.getCu());
		userChannelCU.setUserId(userId);
		userChannelCU.setType(RMSConstant.PRODUCT_TYPE_PHONE);
		userChannelDao.insert(userChannelCU);
		
		UserChannel userChannelCT = new UserChannel();
		userChannelCT.setIsp(RMSConstant.ISP_CT);
		userChannelCT.setChannelId(channelCondition.getCt());
		userChannelCT.setUserId(userId);
		userChannelCT.setType(RMSConstant.PRODUCT_TYPE_PHONE);
		userChannelDao.insert(userChannelCT);
		
		UserChannel userChannelCMFlow = new UserChannel();
		userChannelCMFlow.setIsp(RMSConstant.ISP_CM);
		userChannelCMFlow.setChannelId(channelCondition.getCmflow());
		userChannelCMFlow.setUserId(userId);
		userChannelCMFlow.setType(RMSConstant.PRODUCT_TYPE_FLOW);
		userChannelDao.insert(userChannelCMFlow);
		
		UserChannel userChannelCUFlow = new UserChannel();
		userChannelCUFlow.setIsp(RMSConstant.ISP_CU);
		userChannelCUFlow.setChannelId(channelCondition.getCuflow());
		userChannelCUFlow.setUserId(userId);
		userChannelCUFlow.setType(RMSConstant.PRODUCT_TYPE_FLOW);
		userChannelDao.insert(userChannelCUFlow);
		
		UserChannel userChannelCTFlow = new UserChannel();
		userChannelCTFlow.setIsp(RMSConstant.ISP_CT);
		userChannelCTFlow.setChannelId(channelCondition.getCtflow());
		userChannelCTFlow.setUserId(userId);
		userChannelCTFlow.setType(RMSConstant.PRODUCT_TYPE_FLOW);
		userChannelDao.insert(userChannelCTFlow);
	}
}

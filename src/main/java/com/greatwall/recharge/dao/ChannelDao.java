package com.greatwall.recharge.dao;

import java.util.List;

import com.greatwall.recharge.dto.Channel;


public interface ChannelDao {
	
	
	/** 
	* @Title: insert 
	* @Description: 保存频道 
	* @param channel
	* @return int    返回类型 
	* @throws 
	*/
	public int insert(Channel channel);
	
	/** 
	* @Title: getChannelsIsp 
	* @Description: 获取支持某运营商的通道 
	* @param isp CM中国移动，CU中国联通，CT中国电信
	* @return List<Channel>    返回类型 
	* @throws 
	*/
	public List<Channel> getChannelsIsp(String isp,String type);
	
//	int deleteByPrimaryKey(Integer channelId);
//
//	int insertSelective(Channel record);
//
//	Channel selectByPrimaryKey(Integer channelId);
//
//	int updateByPrimaryKeySelective(Channel record);
//
//	int updateByPrimaryKey(Channel record);
}
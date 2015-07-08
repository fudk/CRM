package com.greatwall.recharge.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.recharge.dao.ChannelDao;
import com.greatwall.recharge.dto.Channel;

@Repository
public class ChannelDaoImpl extends MyBatisDao implements ChannelDao {

			
	public int insert(Channel channel) {
		return this.save("ChannelMapper.insert",channel);
	}
	
	public List<Channel> getChannelsIsp(String isp,String type){
		Map<String,String> params = new HashMap<String,String>();
		params.put("isp", "%"+isp+"%");
		params.put("type", type);
		return this.getList("ChannelMapper.getChannelsIsp", params);
	}

}

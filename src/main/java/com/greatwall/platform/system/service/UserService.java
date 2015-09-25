package com.greatwall.platform.system.service;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.User;
import com.greatwall.recharge.dto.ChannelCondition;
import com.greatwall.recharge.dto.UserChannel;


public interface UserService {

	
	public List<User> getUsers(User user,PageParameter page) throws DaoException;
	
	/** 
	* @Title: searchUsers 
	* @Description: 模糊查询用户
	* @param user
	* @param page
	* @return
	* @throws DaoException    设定文件
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<User> searchUsers(User user,PageParameter page) throws DaoException;
	
	public void saveUserAndChannel(User user,List<UserChannel> userChannels,Integer roleId);
//	public void saveUserAndChannel(User user,ChannelCondition channelCondition,Integer roleId);
	
	public void updateUserAndChannel(User user,List<UserChannel> userChannels)throws DaoException;
	
	/** 
	* @Title: hasUser 
	* @Description: 判断用户是否存在，一版根据userId、 platId或loginName
	* @param @param user
	* @param @return    设定文件 
	* @return Boolean    返回类型 
	* @throws 
	*/
	public Boolean hasUser(User user);
	
	public User getUser(Integer userId);
	
	public User getUser(String platId);
	
	public Boolean updatePassowrd(Integer userId,String oldPassword,String newPassword);
	
	public Boolean disableUser(Integer userId);
}

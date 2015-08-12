package com.greatwall.platform.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dao.UserDao;
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.RoleService;
import com.greatwall.platform.system.service.UserService;
import com.greatwall.recharge.dao.UserChannelDao;
import com.greatwall.recharge.dto.ChannelCondition;
import com.greatwall.recharge.dto.UserChannel;
import com.greatwall.recharge.service.UserChannelService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserChannelDao userChannelDao;
	@Autowired
	private UserChannelService userChannelService;
	@Autowired
	private RoleService roleService;
	
	
	public List<User> getUsers(User user,PageParameter page) throws DaoException{
		return userDao.getUsers(user,page);
	}
	
	public List<User> searchUsers(User user,PageParameter page) throws DaoException{
		return userDao.searchUsers(user,page);
	}
	
	public User getUser(Integer userId){
		User user = new User();
		user.setUserId(userId);
		return userDao.getUser(user);
	}
	
	public User getUser(String platId){
		User user = new User();
		user.setPlatId(platId);
		user.setUserType(1);//有效用户
		return userDao.getUser(user);
	}
	
	public Boolean updatePassowrd(Integer userId,String oldPassword,String newPassword){
		User newPasUser = new User();
		newPasUser.setUserId(userId);
		User u = this.getUser(userId);
		if(u.getUserPas().equals(oldPassword)){
			newPasUser.setUserPas(newPassword);
		}else{
			return false;
		}
		
		int num = userDao.updateUser(newPasUser);
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
	public Boolean disableUser(Integer userId){
		User disUser = new User();
		disUser.setUserId(userId);
		disUser.setUserType(2);
		int num = userDao.updateUser(disUser);
		if(num==1){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	@Transactional
	public void updateUserAndChannel(User user,ChannelCondition channelCondition) throws DaoException{
		userDao.updateUser(user);
		
		if(channelCondition!=null){
			UserChannel userChannel = new UserChannel();
			userChannel.setUserId(user.getUserId());
			userChannelDao.delUserChannel(userChannel);
			userChannelService.saveUserChannels(user.getUserId(), channelCondition);
		}
		
	}
	
	@Transactional
	public void saveUserAndChannel(User user,ChannelCondition channelCondition,Integer roleId){
		user.setCreateTime(new Date());
		user.setUserType(1);
		if(user.getBalance()==null){
			user.setBalance(new Double(0));
		}
		if(user.getFlowBalance()==null){
			user.setFlowBalance(new Double(0));
		}
		if(user.getVersion()==null){
			user.setVersion(1);
		}
		userDao.saveUser(user);
		
		//获取新增用户id
		User u = userDao.getUser(user);
		
		//保存用户通道
		userChannelService.saveUserChannels(u.getUserId(), channelCondition);
		
		//保存用户角色
		roleService.saveUserRole(u.getUserId(), roleId);
		
	}
	
	
	public Boolean hasUser(User user){
		User u = userDao.getUser(user);
		if(u!=null){
			return true;
		}else{
			return false;
		}
	}
	
}

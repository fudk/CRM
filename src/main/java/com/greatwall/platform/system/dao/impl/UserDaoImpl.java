package com.greatwall.platform.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.base.dao.MyBatisDao;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dao.UserDao;
import com.greatwall.platform.system.dto.User;

@Repository
public class UserDaoImpl extends MyBatisDao implements UserDao {

	public User getUser(User user){
		return this.get("userMapper.getUser",user);
	}
	
	public void saveUser(User user){
		this.save("userMapper.insert", user);
	}
	
	public List<User> getUsers(User user,PageParameter page) throws DaoException{
		return this.getListPage("userMapper.selectByQueryPage",user,page);
	}
	
	public List<User> searchUsers(User user,PageParameter page) throws DaoException{
		user.setLoginName("%"+user.getLoginName()+"%");
		return this.getListPage("userMapper.searchUsersPage",user,page);
	}
	
	public int updateUser(User user){
		return this.update("userMapper.updateByPrimaryKeySelective", user);
	}
	
	public int updateBalance(Double amount,Integer version,Integer userId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("amount", amount);
		params.put("version", version);
		params.put("userId", userId);
		return this.update("userMapper.updateBalance", params);
	}
	public int updateFlowBalance(Double amount,Integer version,Integer userId){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("amount", amount);
		params.put("version", version);
		params.put("userId", userId);
		return this.update("userMapper.updateFlowBalance", params);
	}
	
	
}

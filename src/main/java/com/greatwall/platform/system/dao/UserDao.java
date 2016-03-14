package com.greatwall.platform.system.dao;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.User;



public interface UserDao {

	/** 
	* @Title: getUser 
	* @Description: 查询单个用户 
	* @param @param user
	* @param @return    设定文件 
	* @return User    返回类型 
	* @throws 
	*/
	public User getUser(User user);
	
	/** 
	* @Title: saveUser 
	* @Description: 新增用户 
	* @param @param user    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void saveUser(User user);
	
	
	/** 
	* @Title: getUsers 
	* @Description: 获取用户列表 
	* @param @param user
	* @param @param page
	* @param @return    设定文件 
	* @return List<User>    返回类型 
	* @throws 
	*/
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
	/** 
	* @Title: updateUser 
	* @Description: 更新用户
	* @param user
	* @return    设定文件
	* @return int    返回类型 
	* @throws 
	*/
	public int updateUser(User user);
	
	
	/** 
	* @Title: updateBalance 
	* @Description: 更新用户剩余金额，充值或冲销 
	* @param amount
	* @param version
	* @param userId
	* @return int    返回为大于0表示成功，
	* @throws 
	*/
	public int updateBalance(Double amount,Integer version,Integer userId);
	
	
	public int updateFlowBalance(Double amount,Integer version,Integer userId);
	
	public void delUser(Integer userId);
}

package com.greatwall.platform.login.service.impl;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.login.service.LoginService;
import com.greatwall.platform.system.dao.ResDao;
import com.greatwall.platform.system.dao.RoleDao;
import com.greatwall.platform.system.dao.UserDao;
import com.greatwall.platform.system.dto.Association;
import com.greatwall.platform.system.dto.User;
import com.greatwall.platform.system.service.AssociationService;




/**
 * 登录
 * @author fudk_k@sina.com
 * @update 2013-1-1
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

//	private static ExecutorService es = Executors.newCachedThreadPool();

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AssociationService associationService;

	/**
	 * 用户名密码登录
	 * @param loginName
	 * @param pwd
	 * @return
	 */
	public Boolean checkLogin(String loginName,String pwd,HttpSession httpSession){
		Boolean loginflag = false;
			User user = new User();
			user.setLoginName(loginName);
			User u = (User)userDao.getUser(user);
			if(u!=null){
				if(StringUtils.isNotBlank(u.getUserPas())&&pwd.equals(u.getUserPas())){
					u.setUserPas("");
					httpSession.setAttribute("roleIds", this.getRoleIds(u.getUserId()));
					httpSession.setAttribute("user",u);
					
					User loginTimeUser = new User();
					loginTimeUser.setUserId(u.getUserId());
					loginTimeUser.setLastLoginTime(new Date());
					userDao.updateUser(loginTimeUser);
					
					loginflag = true;
				}
			}
		return loginflag;
	}
	
	/** 
	* @Title: getRoleIds 
	* @Description: 获取登录用户角色 
	* @param userId
	* @return String  返回格式  roleId,roleId,
	* @throws 
	*/
	private String getRoleIds(Integer userId){
		Association association = new Association();
		association.setSourceType("role");
		association.setDestinationType("user");
		association.setDestinationValue(userId);
		List<Association> associations =associationService.getAssociations(association);
		StringBuffer roleIds = new StringBuffer();
		if(associations!=null){
			for(Association ass : associations){
				roleIds.append(ass.getSourceValue());
				roleIds.append(",");
			}
		}
		return roleIds.toString();
	}

}

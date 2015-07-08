package com.greatwall.platform.login.service;

import javax.servlet.http.HttpSession;


public interface LoginService {

	/**
	 * 本地用户校验
	 * @param loginName
	 * @param pwd
	 * @param httpSession
	 * @return
	 */
	public Boolean checkLogin(String loginName,String pwd,HttpSession httpSession);
	
}

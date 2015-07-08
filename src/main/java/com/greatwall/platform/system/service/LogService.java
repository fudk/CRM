package com.greatwall.platform.system.service;

import com.greatwall.platform.system.dto.Log;


public interface LogService {

	/** 
	* @Title: saveLog 
	* @Description: 保存日志 
	* @param log    设定文件
	* @return void    返回类型 
	* @throws 
	*/
	public void saveLog(Log log);
}

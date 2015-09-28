package com.greatwall.platform.system.service;

import java.util.List;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
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

	List<Log> getLogs(Log log, PageParameter page) throws DaoException;

	void execLog(String logType, String logName, Long startTimeMillis,
			String remark);
}

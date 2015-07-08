package com.greatwall.platform.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.system.dao.LogDao;
import com.greatwall.platform.system.dto.Log;
import com.greatwall.platform.system.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService{
	
	@Autowired
	private LogDao logDao;
	
	public void saveLog(Log log){
		logDao.saveLog(log);
	}
	
}

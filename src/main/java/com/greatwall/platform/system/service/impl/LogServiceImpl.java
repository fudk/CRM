package com.greatwall.platform.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dao.LogDao;
import com.greatwall.platform.system.dto.Log;
import com.greatwall.platform.system.service.LogService;

@Service("logService")
public class LogServiceImpl implements LogService{
	
	Logger logger = Logger.getLogger(LogServiceImpl.class);
	
	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
	
	@Autowired
	private LogDao logDao;
	
	public void saveLog(Log log){
		logDao.saveLog(log);
	}
	
	@Override
	public List<Log> getLogs(Log log,PageParameter page) throws DaoException{
		return logDao.getLogs(log, page);
	}
	
	@Override
	public void execLog(String logType,String logName,Long startTimeMillis,String remark){
		run(fixedThreadPool,logType,logName,startTimeMillis,remark);
	}
	
	private void run(ExecutorService threadPool,final String logType,final String logName,final Long startTimeMillis
			,final String remark) {
		threadPool.execute(new Runnable() {  
			@Override
			public void run() {  
				try {  
					Log log = new Log();
					log.setLogType("call");
					log.setLogName(logName);
					log.setLogTime(new Date(startTimeMillis));
					log.setRemark(remark);
					log.setTimeConsuming(System.currentTimeMillis() - startTimeMillis);
					
					logDao.saveLog(log);
				} catch (Exception e) {  
					logger.error("", e);
				}finally{
					
				}


			}  
		});  
		//threadPool.shutdown();// 任务执行完毕，关闭线程池  
	}
}

package com.greatwall.quartz;


import org.apache.log4j.Logger;

public class SmsJob {

	private static Logger logger = Logger.getLogger(SmsJob.class);

	public void synMembers(){
		System.out.println("会员增量更新提醒任务");
		try {
			
		} catch (Exception e) {
			logger.error(e);
		} 
	}
}

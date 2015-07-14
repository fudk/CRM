package com.greatwall.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greatwall.util.GlobalParamsUtil;

@Component
public class JobTaskImpl implements JobTask {

	//@Scheduled(cron="0/2 * *  * * ? ")   //每5秒执行一次  
    @Override  
    public void synState(){  
		System.out.println("进入测试");
		if(GlobalParamsUtil.getSearchLock()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		try {
			System.out.println("业务处理");
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GlobalParamsUtil.unSearchLock();
    }  
}

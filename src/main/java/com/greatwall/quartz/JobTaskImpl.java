package com.greatwall.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greatwall.clientapi.service.ClientService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.util.GlobalParamsUtil;

@Component
public class JobTaskImpl implements JobTask {

	@Autowired
	private ClientService clientService;
	
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
			
			
			Consume consume = new Consume();
			clientService.searchState(consume);
			
			System.out.println("业务处理");
		} catch (Exception e) {
			e.printStackTrace();
		}
		GlobalParamsUtil.unSearchLock();
    }  
}

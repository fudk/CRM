package com.greatwall.platform.base.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.greatwall.recharge.service.RechargeStateSynService;

@Component("instantiationTracing")
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private RechargeStateSynService rechargeStateSynService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		System.out.println("启动");
		//event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是老大. 
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。 
			System.out.println("过滤启动");
			rechargeStateSynService.synState();
		}  

	}

}

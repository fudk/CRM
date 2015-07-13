package com.greatwall.recharge.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.greatwall.recharge.service.RechargeStateSynService;

@Service("rechargeStateSynService")
public class RechargeStateSynImpl implements RechargeStateSynService {

	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);

	ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

	public void synState(){
		run(fixedThreadPool,"");
	}

	private void run(ExecutorService threadPool,final String consume) {
		threadPool.execute(new Runnable() {  
			@Override
			public void run() {  

				try {
					System.out.println("单线程处理");
					//Thread.sleep(3000);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					// 关闭线程池
					//threadPool.shutdown();
				}

			}  
		});  
		//threadPool.shutdown();// 任务执行完毕，关闭线程池  
	}
}

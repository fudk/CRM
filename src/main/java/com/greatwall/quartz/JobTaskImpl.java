package com.greatwall.quartz;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greatwall.api.service.CallbackNotifyService;
import com.greatwall.clientapi.service.ClientService;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;
import com.greatwall.recharge.service.RechargeConsumeService;
import com.greatwall.util.GlobalParamsUtil;
import com.greatwall.util.RMSConstant;

@Component
public class JobTaskImpl implements JobTask {

	Logger logger = Logger.getLogger(JobTaskImpl.class);

	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);
	
	@Autowired
	private ClientService clientService;

	@Autowired
	private RechargeConsumeService rechargeConsumeService;
	
	@Autowired
	private CallbackNotifyService callbackNotifyService;

	@Scheduled(cron="0 0/1 *  * * ? ")   //每5秒执行一次  
	@Override  
	public void synState(){  
		if(GlobalParamsUtil.getSearchLock()){
			//System.out.println(new Date() +"获取定时任务锁成功");
			try {
				searchState();
				//Thread.sleep(40000);
			} catch (Exception e) {
				logger.error("充值状态查询定时任务错误 ", e);
			}

		}else{
			System.out.println("获取定时任务锁失败");
		}
		GlobalParamsUtil.unSearchLock();
	}  

	private void searchState() throws Exception{
		Boolean flag = true;
		int maxWhile = 10;//最多循环10次
		int initWhile = 1;
		ConsumeConditions jinpiao = null;
		ConsumeConditions cc =new ConsumeConditions();
		cc.setState("%sended%");

		PageParameter page = new PageParameter();
		page.setPageSize(20);
		//page.setCurrentPage(1);

		String status = "";
		String opstatus = "";
		
		while(flag){
			//System.out.println("业务处理"+page.getCurrentPage()+" maxpage="+page.getTotalPage());
			List<ConsumeConditions> consumeList = rechargeConsumeService.getConsumesByStatePage(cc, page);
			if(consumeList==null){
				break;
			}

			for(ConsumeConditions ccd:consumeList){
				if(RMSConstant.INTERFACE_NAME_JINPIAO.equals(ccd.getInterfaceName())){
					if(jinpiao==null){
						jinpiao = ccd;
					}
					continue;
				}
				status = clientService.searchState(ccd);
				if(status.contains("fail")){
					opstatus = "00";
				}else if("success".equals(status)){
					opstatus = "01";
				}else{
					continue;
				}
				run(fixedThreadPool,ccd,opstatus);
			}

			if(page.getCurrentPage()>page.getTotalPage()){
				break;
			}
			page.setCurrentPage(page.getCurrentPage()+1);

			initWhile++;
			if(initWhile>=maxWhile){
				break;
			}
		}
		
		if(jinpiao!=null){
			status = clientService.searchState(jinpiao);
			if(status.contains("fail")){
				opstatus = "00";
			}else if("success".equals(status)){
				opstatus = "01";
			}
			run(fixedThreadPool,jinpiao,opstatus);
		}
	}
	
	
	private void run(ExecutorService threadPool,final ConsumeConditions consumeConditions,final String opstatus) {
		threadPool.execute(new Runnable() {  
			@Override
			public void run() {  
				try {  
					callbackNotifyService.callbackNotify(consumeConditions, opstatus);
				} catch (Exception e) {  
					logger.error("充值状态回调错误", e);
				}finally{
					
				}


			}  
		});  
		//threadPool.shutdown();// 任务执行完毕，关闭线程池  
	}
}

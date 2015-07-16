package com.greatwall.quartz;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.greatwall.clientapi.service.ClientService;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.ConsumeConditions;
import com.greatwall.recharge.service.RechargeConsumeService;
import com.greatwall.util.GlobalParamsUtil;

@Component
public class JobTaskImpl implements JobTask {

	Logger logger = Logger.getLogger(JobTaskImpl.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private RechargeConsumeService rechargeConsumeService;

	@Scheduled(cron="0/30 * *  * * ? ")   //每5秒执行一次  
	@Override  
	public void synState(){  
		if(GlobalParamsUtil.getSearchLock()){
			System.out.println(new Date() +"获取定时任务锁成功");
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
		int maxWhile = 50;//最多循环50次
		int initWhile = 1;
		ConsumeConditions cc =new ConsumeConditions();
		cc.setState("%sended%");

		PageParameter page = new PageParameter();
		page.setPageSize(2);
		//page.setCurrentPage(1);

		while(flag){
			//System.out.println("业务处理"+page.getCurrentPage()+" maxpage="+page.getTotalPage());
			List<ConsumeConditions> consumeList = rechargeConsumeService.getConsumesByStatePage(cc, page);
			if(consumeList==null){
				break;
			}

			for(ConsumeConditions ccd:consumeList){
				clientService.searchState(ccd);
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

	}
}

package com.greatwall.recharge.client;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.service.RechargeConsumeService;

@Controller
@RequestMapping("/client")
public class ClientController {

	Logger logger = Logger.getLogger(ClientController.class);
	
	@Autowired
	private LiulService liulService;
	
	@Autowired
	private RechargeConsumeService rechargeConsumeService;
	
	@RequestMapping("/searchState")
	public String searchState(Consume consume){
		try {
			String status = liulService.searchState(consume);
			
			rechargeConsumeService.confirmConsume(consume.getConsumeId(), status);
			return "success";
		} catch (Exception e) {
			logger.error("",e);
			return "fail";
		}
	}
}

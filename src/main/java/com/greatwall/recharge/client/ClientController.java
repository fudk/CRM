package com.greatwall.recharge.client;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.service.RechargeConsumeService;

@Controller
@RequestMapping("/client")
public class ClientController {

	Logger logger = Logger.getLogger(ClientController.class);
	
	@Autowired
	private LiulService liulService;
	@Autowired
	private ShunpanService shunpanService;
	
	@Autowired
	private RechargeConsumeService rechargeConsumeService;
	
	@RequestMapping("/searchState")
	public@ResponseBody String searchState(Consume consume,String interfaceName){
		try {
			String status = "";
			if("liul".equals(interfaceName)){
				status = liulService.searchState(consume);
			}else if("shunpan".equals(interfaceName)){
				status = shunpanService.searchState(consume);
			}
			if(StringUtils.isNotBlank(status)){
				rechargeConsumeService.confirmConsume(consume.getConsumeId(), status);
			}
			return "success";
		} catch (Exception e) {
			logger.error("",e);
			return "fail";
		}
	}
}

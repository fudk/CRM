package com.greatwall.clientapi.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greatwall.clientapi.service.ClientService;
import com.greatwall.recharge.dto.Consume;
import com.greatwall.recharge.dto.ConsumeConditions;
import com.greatwall.recharge.service.RechargeConsumeService;

@Controller
@RequestMapping("/client")
public class ClientController {

	Logger logger = Logger.getLogger(ClientController.class);

	/*@Autowired
	private LiulService liulService;
	@Autowired
	private ShunpayService shunpanService;*/

	@Autowired
	private RechargeConsumeService rechargeConsumeService;

	@Autowired
	private ClientService clientService;

	@RequestMapping("/searchState")
	public@ResponseBody String searchState(Consume consume){
		try {

			ConsumeConditions cons = rechargeConsumeService.getConsumeConditions(consume);
			return clientService.searchState(cons);
		} catch (Exception e) {
			logger.error("",e);
			return "error";
		}
	}
}

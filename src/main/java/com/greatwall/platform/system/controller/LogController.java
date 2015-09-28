package com.greatwall.platform.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.base.dao.DaoException;
import com.greatwall.platform.domain.PageParameter;
import com.greatwall.platform.system.dto.Log;
import com.greatwall.platform.system.dto.Role;
import com.greatwall.platform.system.service.LogService;
import com.greatwall.platform.system.service.ResourceService;
import com.greatwall.platform.system.service.RoleService;

@Controller
@RequestMapping("/system/log")
public class LogController {

	Logger logger = Logger.getLogger(LogController.class);
	@Autowired
	private LogService logService;
	
	@RequestMapping("/logManage")
	public ModelAndView logManage(Log log,ModelMap model){
		return new ModelAndView("/sysmanage/log/logmanage.jsp");
	}
	
	@RequestMapping("/getLogs")
	public ModelAndView getLogs(Log log,PageParameter page,ModelMap model){

		try {
			model.addAttribute("logs", logService.getLogs(log, page));
		} catch (DaoException e) {
			logger.error("", e);
		}
		model.addAttribute("page", page);
		return new ModelAndView("/sysmanage/log/logs.jsp");
	}
	
	
}

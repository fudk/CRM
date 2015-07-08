package com.greatwall.platform.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



/**
 * 首页
 * @author fudk_k@sina.com
 * @update 2014-7-6
 */
//@Controller
//@RequestMapping("/mana/index")
public class IndexController {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView main(Model model) {
		return new ModelAndView("index.jsp");
	}

}
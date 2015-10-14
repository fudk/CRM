package com.greatwall.platform.common.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/** 
* @ClassName: FileController 
* @Description: 文件控制 
* @author fudk fudk_k@sina.com 
* @date 2015年7月4日 下午4:56:13 
*  
*/
@Controller
@RequestMapping("/error")
public class ErrorController {

	Logger logger = Logger.getLogger(ErrorController.class);
	
	@RequestMapping("/404")
	public ModelAndView error404() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/404.jsp");
		return mav;
	}
	@RequestMapping("/500")
	public ModelAndView error500() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/error.jsp");
		return mav;
	}
	
}

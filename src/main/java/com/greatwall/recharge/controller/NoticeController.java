package com.greatwall.recharge.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Notice;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	Logger logger = Logger.getLogger(NoticeController.class);
	
	@RequestMapping("/noticeManage")
	public ModelAndView noticeManage(ModelMap model){
		return new ModelAndView("/notice/noticemanage.jsp");
	}
	
	@RequestMapping("/getNotices")
	public ModelAndView getProducts(Notice notice,PageParameter page,ModelMap model){
		try {
//			model.addAttribute("products",productService.getProductsPage(product, page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("查询产品列表错误",e);
			return new ModelAndView("/common/error.jsp");
		}
		return new ModelAndView("/notice/notices.jsp");
	}
	
	@RequestMapping("/noticeInit")
	public ModelAndView noticeInit(ModelMap model){
		return new ModelAndView("/notice/addNotice.jsp");
	}
	
}

package com.greatwall.recharge.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.domain.PageParameter;
import com.greatwall.recharge.dto.Notice;
import com.greatwall.recharge.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	Logger logger = Logger.getLogger(NoticeController.class);
	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/noticeManage")
	public ModelAndView noticeManage(ModelMap model){
		return new ModelAndView("/notice/noticemanage.jsp");
	}
	
	@RequestMapping("/getNotices")
	public ModelAndView getProducts(Notice notice,PageParameter page,ModelMap model){
		try {
			model.addAttribute("notices",noticeService.getNotices(notice, page));
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
	
	@RequestMapping("/addNotice")
	public@ResponseBody String addNotice(Notice notice){
		if(notice!=null){
			if(StringUtils.isBlank(notice.getTitle())||StringUtils.isBlank(notice.getContent())){
				return "标题或内容不能为空！";
			}
			notice.setCreateTime(new Date());
			noticeService.saveNotice(notice);
			return "success";
		}
		return "失败";
	}
	
	@RequestMapping("/updateNoticeInit")
	public ModelAndView updateNoticeInit(Integer id,ModelMap model){
		model.addAttribute("notice",noticeService.getNotice(id));
		return new ModelAndView("/notice/addNotice.jsp");
	}
	@RequestMapping("/showNotice")
	public ModelAndView showNotice(Integer id,ModelMap model){
		model.addAttribute("notice",noticeService.getNotice(id));
		return new ModelAndView("/notice/notice.jsp");
	}
	
	@RequestMapping("/updateNotice")
	public@ResponseBody String updateNotice(Notice notice){
		if(notice!=null){
			if(notice.getId()==null||StringUtils.isBlank(notice.getTitle())
					||StringUtils.isBlank(notice.getContent())){
				return "标题或内容不能为空！";
			}
			noticeService.updateNotice(notice);
			return "success";
		}
		return "失败";
	}
}

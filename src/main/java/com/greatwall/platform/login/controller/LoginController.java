package com.greatwall.platform.login.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.greatwall.platform.login.service.LoginService;



/**
 * 登录
 * @author fudk_k@sina.com
 * @update 2014-7-5
 */
@Controller
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class);  
	
	@Autowired
	private LoginService loginService;

	@RequestMapping("/login")
	public ModelAndView login() {

		ModelAndView mav = new ModelAndView();
		//		mav.setViewName("login.ftl");
		mav.setViewName("login.jsp");

		//		String kaptchaExpected = (String)httpSession
		//				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//System.out.println(kaptchaExpected);

		return mav;
	}

	@RequestMapping(value = "/loginmain", method = RequestMethod.POST)
	public ModelAndView loginmain(@RequestParam(value="loginName") String loginName,
			@RequestParam(value="password") String password,HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login.jsp");

		try {
			if(StringUtils.isBlank(loginName)||StringUtils.isBlank(password)){
				mav.addObject("reflag", 1);//用户名或密码错误
				mav.addObject("loginName", loginName);
				mav.addObject("msg", "用户名或密码为空！");
				return mav;
			}

			if(loginService.checkLogin(loginName, password,httpSession)){
				//				mav.setViewName("redirect:mana/index");
				mav.setViewName("redirect:/main");
			}else{
				mav.addObject("reflag", 1);//用户名或密码错误
				mav.addObject("loginName", loginName);
				mav.addObject("msg", "用户名或密码错误！");
			}
		} catch (Exception e) {
			logger.error("登录错误",e);
			mav.addObject("reflag", 1);//用户名或密码错误
			mav.addObject("loginName", loginName);
			mav.addObject("msg", "登录错误！");
		}
		return mav;
	}


	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession httpSession) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login.jsp");
		if(httpSession.getAttribute("user")!=null){
			httpSession.setAttribute("user", null);
		}

		return mav;
	}


}
package com.greatwall.platform.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



public class PermissionsInteceptor implements HandlerInterceptor  {
//	private String loginUrl; //登录
//	private String registUrl; //注册
	private String redirectUrl; //重定向登录页
	private List<String> excludeUrl; //重定向登录页

//	public void setRegistUrl(String registUrl) {
//		this.registUrl = registUrl;
//	}
//	public void setLoginUrl(String loginUrl) {
//		this.loginUrl = loginUrl;
//	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public List<String> getExcludeUrl() {
		return excludeUrl;
	}
	public void setExcludeUrl(List<String> excludeUrl) {
		this.excludeUrl = excludeUrl;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		//1、请求到登录页面 放行  
		String urlPath = request.getServletPath();
		if(excludeUrl!=null&&excludeUrl.size()>0){
			for(String eurl : excludeUrl){
				if(urlPath.startsWith(eurl)){
					return true;
				}
			}
		}
//		if(request.getServletPath().startsWith(loginUrl)
//				||request.getServletPath().startsWith(registUrl)) {  
//			return true;  
//		}  
		
		//2、比如退出、首页等页面无需登录，即此处要放行 允许游客的请求  

		//3、如果用户已经登录 放行    
		if(request.getSession().getAttribute("user") != null) {  
			return true; 
		}  

		//4、非法请求 即这些请求需要登录后才能访问  
		//重定向到登录页面  
//		response.sendRedirect(request.getContextPath() + loginUrl);  
		response.sendRedirect(redirectUrl);  
		return false;

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
					throws Exception {
	}





}

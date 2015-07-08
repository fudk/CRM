package com.greatwall.platform.common.aop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.After;  
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;  
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.greatwall.platform.system.dto.Log;
import com.greatwall.platform.system.service.LogService;
import com.greatwall.util.NetworkUtil;

@Aspect 
@Component
public class ServiceApiAop {

	private static Logger logger = Logger.getLogger(ServiceApiAop.class);
	
	@Autowired
	private LogService logService;
	
	@Pointcut("execution(* com.greatwall.api.controller.*.*(..))")  
	private void pointCutLogMethod() {  
	}  

	/*//声明前置通知  
	@Before("pointCutLogMethod()")  
	public void doBefore() {  
		System.out.println("前置通知");  
		startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间  
	}  

	//声明后置通知  
	@AfterReturning(pointcut = "pointCutLogMethod()", returning = "result")  
	public void doAfterReturning(String result) {  
		System.out.println("后置通知");  
		System.out.println("---" + result + "---");  
	}  

	//声明例外通知  
	@AfterThrowing(pointcut = "pointCutLogMethod()", throwing = "e")  
	public void doAfterThrowing(Exception e) {  
		System.out.println("例外通知");  
		System.out.println(e.getMessage());  
	}  

	//声明最终通知  
	@After("pointCutLogMethod()")  
	public void doAfter() {  
		System.out.println("最终通知");  
		endTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间  
//		this.printOptLog();  
	}  
	 */
	//声明环绕通知  
	@Around("pointCutLogMethod()")  
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
//		System.out.println("进入方法---环绕通知");  
		long startTimeMillis = System.currentTimeMillis(); // 开始时间  

		/** 
		 * 1.获取request信息 
		 * 2.根据request获取session 
		 * 3.从session中取出登录用户信息 
		 */
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
		ServletRequestAttributes sra = (ServletRequestAttributes)ra;  
		HttpServletRequest request = sra.getRequest();

		String ip = NetworkUtil.getIpAddress(request);

		String className =  pjp.getTarget().getClass().getName();

		String methodName = pjp.getSignature().getName();

		// 获取输入参数
		Object[] inputParam = pjp.getArgs();
		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行  
		Object outputParam = pjp.proceed();// result的值就是被拦截方法的返回值  // 存放输出结果

		long endTimeMillis = System.currentTimeMillis();// 结束时间  
		this.printOptLog(className,inputParam,methodName,outputParam,ip,startTimeMillis,endTimeMillis);  
//		System.out.println("退出方法---环绕通知");  
		return outputParam;  
	}  

	/** 
	 *  
	 * @Title：printOptLog 
	 * @Description: 输出日志  
	 * @author shaojian.yu  
	 * @date 2014年11月2日 下午4:47:09 
	 */  
	private void printOptLog(String className,Object[] inputParam,String methodName,Object outputParam,
			String ip,long startTimeMillis,long endTimeMillis) {  
		Gson gson = new Gson(); 
		String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);  
		
		if(inputParam!=null&&inputParam.length>0){
			for(int i=0;i<inputParam.length;i++){
				if(inputParam[i] instanceof HttpServletRequest){
					inputParam = ArrayUtils.remove(inputParam, i);
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("param：");
		sb.append(gson.toJson(inputParam));
		sb.append(" result：");
		sb.append(gson.toJson(outputParam));
		
		sb.append(" ip：");
		sb.append(ip);
		sb.append(" className：");
		sb.append(className);
		sb.append(" methodName:");
		sb.append(methodName);
		sb.append(" op_time:");
		sb.append(optTime);
		sb.append(" pro_time:");
		sb.append(endTimeMillis - startTimeMillis);
		sb.append(" ms");
		
		logger.info(sb.toString());
		
		Log log = new Log();
		log.setLogType(methodName);
		log.setLogTime(new Date(startTimeMillis));
		log.setRemark(sb.toString());
		log.setFromIp(ip);
		log.setTimeConsuming(endTimeMillis - startTimeMillis);
		
		logService.saveLog(log);
		
//		logger.info("\n className："+className 
//				+"  methodName："+methodName+"; op_time：" + optTime + " pro_time：" + (endTimeMillis - startTimeMillis) + "ms ;"  
//				+" ip:"+ip+" param："+gson.toJson(inputParam)+";"+"\n result："+gson.toJson(outputParam));  
	}  

	/** 
	 *  
	 * @Title：doBeforeInServiceLayer 
	 * @Description: 方法调用前触发  
	 *  记录开始时间  
	 * @author shaojian.yu  
	 * @date 2014年11月2日 下午4:45:53 
	 * @param joinPoint 
	 *//*  
	@Before("execution(* com.greatwall.platform.login.controller..*.*(..))")  
	public void doBeforeInServiceLayer(JoinPoint joinPoint) {  
		startTimeMillis = System.currentTimeMillis(); // 记录方法开始执行的时间  
	}  */
	/*
	 *//** 
	 *  
	 * @Title：doAfterInServiceLayer 
	 * @Description: 方法调用后触发  
	 *  记录结束时间 
	 * @author shaojian.yu  
	 * @date 2014年11月2日 下午4:46:21 
	 * @param joinPoint 
	 *//*  
	@After("execution(* com.yusj.controller..*.*(..))")  
	public void doAfterInServiceLayer(JoinPoint joinPoint) {  
		endTimeMillis = System.currentTimeMillis(); // 记录方法执行完成的时间  
		this.printOptLog();  
	}  

	  *//** 
	  *  
	  * @Title：doAround 
	  * @Description: 环绕触发  
	  * @author shaojian.yu  
	  * @date 2014年11月3日 下午1:58:45 
	  * @param pjp 
	  * @return 
	  * @throws Throwable 
	  *//*  
	@Around("execution(* com.yusj.controller..*.*(..))")  
	public Object doArounds(ProceedingJoinPoint pjp) throws Throwable {  
	   *//** 
	   * 1.获取request信息 
	   * 2.根据request获取session 
	   * 3.从session中取出登录用户信息 
	   *//*  
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
		ServletRequestAttributes sra = (ServletRequestAttributes)ra;  
		HttpServletRequest request = sra.getRequest();  
		// 从session中获取用户信息  
		//        String loginInfo = (String) session.getAttribute("username");  
		//        if(loginInfo != null && !"".equals(loginInfo)){  
		//            userName = operLoginModel.getLogin_Name();  
		//        }else{  
		//            userName = "用户未登录" ;  
		//        }  
		// 获取输入参数  
		inputParamMap = request.getParameterMap();  
		// 获取请求地址  
		requestPath = request.getRequestURI();  

		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行  
		outputParamMap = new HashMap<String, Object>();  
		Object result = pjp.proceed();// result的值就是被拦截方法的返回值  
		outputParamMap.put("result", result);  

		return result;  
	} 

	private void printOptLog() {  
		Gson gson = new Gson(); // 需要用到google的gson解析包  
		String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);  
		logger.info("\n className："+className  + " IP:"+ip
				+"  url："+requestPath+"; op_time：" + optTime + " pro_time：" + (endTimeMillis - startTimeMillis) + "ms ;"  
				+" param："+gson.toJson(inputParamMap)+";"+"\n result："+gson.toJson(outputParamMap));  
	}  
	    */


}  

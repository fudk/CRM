package com.greatwall.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PhoneUtil {

	/** 
	* @Fields ctNums : 中国电信号码段 
	*/ 
	private String ctNums;
	/** 
	* @Fields cuNums : 中国联通号码段 
	*/ 
	private String cuNums;
	
	/** 
	* @Fields cmNums : 中国移动号码段 
	*/ 
	public String cmNums;
	
	private static final String telcomPattern = "[0-9]{11}";
	
	public String getCtNums() {
		return ctNums;
	}
	@Value("#{propertiesReader['Phone.ctNums']}") 
	public void setCtNums(String ctNums) {
		this.ctNums = ctNums;
	}
	public String getCuNums() {
		return cuNums;
	}
	@Value("#{propertiesReader['Phone.cuNums']}") 
	public void setCuNums(String cuNums) {
		this.cuNums = cuNums;
	}
	public String getCmNums() {
		return cmNums;
	}
	@Value("#{propertiesReader['Phone.cmNums']}") 
	public void setCmNums(String cmNums) {
		this.cmNums = cmNums;
	}
	
	/** 
	* @Title: isPhoneNum 
	* @Description: 判断手机号码是否正确，运营商 
	* @param phone
	* @return String   CM移动号段， CU联通号段，CT电信号段
	* @throws 
	*/
	public String isPhoneNum(String phone){
		if(!phone.matches(telcomPattern)){
			throw new ClassCastException("错误的电话号码");
		}
		String phoneStart = phone.substring(0, 3);
		if(ArrayUtils.contains(this.cmNums.split(","), phoneStart))
			return "CM";
		if(ArrayUtils.contains(this.cuNums.split(","), phoneStart))
			return "CU";
		if(ArrayUtils.contains(this.ctNums.split(","), phoneStart))
			return "CT";
		
		throw new ClassCastException("错误的电话号码或号段未维护");
	}
	
	public String getIspName(String isp){
		if("CM".equals(isp.toUpperCase())){
			return "移动";
		}else if("CU".equals(isp.toUpperCase())){
			return "联通";
		}else if("CUT".equals(isp.toUpperCase())){
			return "电信";
		}else{
			return "运营商错误";
		}
	}
}

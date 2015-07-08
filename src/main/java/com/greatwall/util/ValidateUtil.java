package com.greatwall.util;

public class ValidateUtil {

	/** 
	* @Title: isAgent 
	* @Description: 判断是否是代理商角色 
	* @param roleIds	从session中取出的roleId
	* @return Boolean    返回类型 
	* @throws 
	*/
	public static Boolean isAgent(String roleIds){
		if(!roleIds.contains("1,")&&roleIds.contains("2,")){
			return true;
		}else{
			return false;
		}
	}
	
	
	private static void main(String[] args) {

	}

}

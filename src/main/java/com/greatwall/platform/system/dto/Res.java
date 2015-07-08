package com.greatwall.platform.system.dto;



/**   
 * @Description: 系统字典资源POJO 
 * @author fudk fudk_k@sina.com   
 * @date 2014年7月13日 下午3:47:14    
 */
public class Res {

	/**
	 * @Fields resId : 资源字典id
	 */
	private Integer resId; 
	
	/**
	 * @Fields resName : 资源名称
	 */
	private String resName; 
	
	/**
	 * @Fields resType : 资源类型
	 */
	private String resType;
	
	/**
	 * @Fields resVal : 资源值
	 */
	private String resVal;

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getResVal() {
		return resVal;
	}

	public void setResVal(String resVal) {
		this.resVal = resVal;
	}
	
	
}

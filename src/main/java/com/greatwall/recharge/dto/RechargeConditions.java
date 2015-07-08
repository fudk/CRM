package com.greatwall.recharge.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class RechargeConditions extends Recharge {

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;
	private String recType;
	private String operatorName;
	private String userName;
	private String loginName;
	
	private Integer operator;
	private Integer userId;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRecType() {
		return recType;
	}
	public void setRecType(String recType) {
		this.recType = recType;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	


}

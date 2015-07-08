package com.greatwall.api.domain;

public class RechargeCondition {
	
	private String platId;
	private String timestamp;
	private String orderId;
	private String opType;
	private String custPhone;
	private String opPrice;
	private Integer opNum;
	private String notifyUrl;
	private String sign;
	
	public String getPlatId() {
		return platId;
	}
	public void setPlatId(String platId) {
		this.platId = platId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getOpPrice() {
		return opPrice;
	}
	public void setOpPrice(String opPrice) {
		this.opPrice = opPrice;
	}
	public Integer getOpNum() {
		return opNum;
	}
	public void setOpNum(Integer opNum) {
		this.opNum = opNum;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	

}

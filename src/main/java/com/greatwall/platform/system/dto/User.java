package com.greatwall.platform.system.dto;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	
	private static final long serialVersionUID = -7207048858640201537L;

	private Integer userId;

    private String platId;

    private Integer parentId;

    private String loginName;

    private String userName;

    private String userPas;

    private String userEmail;

    private String userAddress;

    private String userQq;

    private Double balance;
    
    private Double flowBalance;

    private Date createTime;

    private Date effectiveTime;

    private Date lastLoginTime;

    private Integer userType;

    private String sessionKey;

    private String permitIp;

    private String remark;

    private Integer version;

    private Integer discountPhone;

    private Integer discountFlow;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId == null ? null : platId.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPas() {
        return userPas;
    }

    public void setUserPas(String userPas) {
        this.userPas = userPas == null ? null : userPas.trim();
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }

    public String getUserQq() {
        return userQq;
    }

    public void setUserQq(String userQq) {
        this.userQq = userQq == null ? null : userQq.trim();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
    }

    public String getPermitIp() {
        return permitIp;
    }

    public void setPermitIp(String permitIp) {
        this.permitIp = permitIp == null ? null : permitIp.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDiscountPhone() {
        return discountPhone;
    }

    public void setDiscountPhone(Integer discountPhone) {
        this.discountPhone = discountPhone;
    }

    public Integer getDiscountFlow() {
        return discountFlow;
    }

    public void setDiscountFlow(Integer discountFlow) {
        this.discountFlow = discountFlow;
    }

	public Double getFlowBalance() {
		return flowBalance;
	}

	public void setFlowBalance(Double flowBalance) {
		this.flowBalance = flowBalance;
	}
    
}
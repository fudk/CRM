package com.greatwall.recharge.dto;

import java.util.Date;

public class Product {
    private Integer productId;

    private String productName;

    private String productImg;

    private String productValue;

    private String isp;

    private String productType;

    private Double productPrice;

    private String productValidity;

    private Date createTime;

    private String state;

    private String remark;

    private String qtProductId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg == null ? null : productImg.trim();
    }

    public String getProductValue() {
        return productValue;
    }

    public void setProductValue(String productValue) {
        this.productValue = productValue == null ? null : productValue.trim();
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp == null ? null : isp.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductValidity() {
        return productValidity;
    }

    public void setProductValidity(String productValidity) {
        this.productValidity = productValidity == null ? null : productValidity.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getQtProductId() {
        return qtProductId;
    }

    public void setQtProductId(String qtProductId) {
        this.qtProductId = qtProductId == null ? null : qtProductId.trim();
    }
}
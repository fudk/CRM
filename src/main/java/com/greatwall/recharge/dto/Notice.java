package com.greatwall.recharge.dto;

import java.util.Date;

public class Notice {
    private Integer id;

    private String title;

    private Date createTime;

    private String needShow;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNeedShow() {
        return needShow;
    }

    public void setNeedShow(String needShow) {
        this.needShow = needShow == null ? null : needShow.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
package com.greatwall.recharge.dto;

public class Channel {
    private Integer channelId;

    private String channelName;

    private String channelProperty;

    private String isp;
    
    private String type;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelProperty() {
        return channelProperty;
    }

    public void setChannelProperty(String channelProperty) {
        this.channelProperty = channelProperty == null ? null : channelProperty.trim();
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp == null ? null : isp.trim();
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}
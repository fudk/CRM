package com.greatwall.platform.system.dto;

public class Association {
    private Integer associationId;

    private String destinationType;

    private Integer destinationValue;

    private String sourceType;

    private Integer sourceValue;

    public Integer getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Integer associationId) {
        this.associationId = associationId;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType == null ? null : destinationType.trim();
    }

    public Integer getDestinationValue() {
        return destinationValue;
    }

    public void setDestinationValue(Integer destinationValue) {
        this.destinationValue = destinationValue;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public Integer getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(Integer sourceValue) {
        this.sourceValue = sourceValue;
    }
}
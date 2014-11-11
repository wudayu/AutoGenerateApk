package com.movitech.grande.model;

public class XcfcIntegral {

	String id;
	String sourceType;
	String brokerId;
	String integral;
	String createTime;
	String sourceId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	@Override
	public String toString() {
		return "XcfcIntegral [id=" + id + ", sourceType=" + sourceType
				+ ", brokerId=" + brokerId + ", integral=" + integral
				+ ", createTime=" + createTime + ", sourceId=" + sourceId + "]";
	}
	
}

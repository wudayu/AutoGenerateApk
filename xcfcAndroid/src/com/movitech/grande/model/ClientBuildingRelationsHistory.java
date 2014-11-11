package com.movitech.grande.model;

public class ClientBuildingRelationsHistory {

	String id;
	String clientId;
	String buildingId;
	String brokerId;
	String clientStatus;
	String remark;
	String creater;
	String createTime;
	String clientBuildingId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	public String getClientStatus() {
		return clientStatus;
	}
	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getClientBuildingId() {
		return clientBuildingId;
	}
	public void setClientBuildingId(String clientBuildingId) {
		this.clientBuildingId = clientBuildingId;
	}
	@Override
	public String toString() {
		return "ClientBuildingRelationsHistory [id=" + id + ", clientId="
				+ clientId + ", buildingId=" + buildingId + ", brokerId="
				+ brokerId + ", clientStatus=" + clientStatus + ", remark="
				+ remark + ", creater=" + creater + ", createTime="
				+ createTime + ", clientBuildingId=" + clientBuildingId + "]";
	}
	
	
}

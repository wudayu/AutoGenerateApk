package com.movitech.grande.model;

import java.util.Arrays;

public class ClientBuildingRelations {

	String id;
	String clientId;
	String cityId;
	String buildingId;
	String brokerId;
	String clientStatus;
	String isVisited;
	String buildingName;
	String remark;
	String creater;
	String createTime;
	String isLocked;
	String lockedAt;
	String unlockedAt;
	String isSigned;
	String protectTime;
	String buildingCity;
	String buildingRatio;
	ClientBuildingRelationsHistory[] historys;
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
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
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
	public String getIsVisited() {
		return isVisited;
	}
	public void setIsVisited(String isVisited) {
		this.isVisited = isVisited;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
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
	public String getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}
	public String getLockedAt() {
		return lockedAt;
	}
	public void setLockedAt(String lockedAt) {
		this.lockedAt = lockedAt;
	}
	public String getUnlockedAt() {
		return unlockedAt;
	}
	public void setUnlockedAt(String unlockedAt) {
		this.unlockedAt = unlockedAt;
	}
	public String getIsSigned() {
		return isSigned;
	}
	public void setIsSigned(String isSigned) {
		this.isSigned = isSigned;
	}
	public String getProtectTime() {
		return protectTime;
	}
	public void setProtectTime(String protectTime) {
		this.protectTime = protectTime;
	}
	public String getBuildingCity() {
		return buildingCity;
	}
	public void setBuildingCity(String buildingCity) {
		this.buildingCity = buildingCity;
	}
	public String getBuildingRatio() {
		return buildingRatio;
	}
	public void setBuildingRatio(String buildingRatio) {
		this.buildingRatio = buildingRatio;
	}
	public ClientBuildingRelationsHistory[] getHistorys() {
		return historys;
	}
	public void setHistorys(ClientBuildingRelationsHistory[] historys) {
		this.historys = historys;
	}
	@Override
	public String toString() {
		return "ClientBuildingRelations [id=" + id + ", clientId=" + clientId
				+ ", cityId=" + cityId + ", buildingId=" + buildingId
				+ ", brokerId=" + brokerId + ", clientStatus=" + clientStatus
				+ ", isVisited=" + isVisited + ", buildingName=" + buildingName
				+ ", remark=" + remark + ", creater=" + creater
				+ ", createTime=" + createTime + ", isLocked=" + isLocked
				+ ", lockedAt=" + lockedAt + ", unlockedAt=" + unlockedAt
				+ ", isSigned=" + isSigned + ", protectTime=" + protectTime
				+ ", buildingCity=" + buildingCity + ", buildingRatio="
				+ buildingRatio + ", historys=" + Arrays.toString(historys)
				+ "]";
	}
	
	
}

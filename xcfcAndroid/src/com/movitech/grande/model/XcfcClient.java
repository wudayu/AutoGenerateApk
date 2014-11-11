package com.movitech.grande.model;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月15日 下午2:37:09 
 * 类说明
 */
public class XcfcClient {
	private String name;
	private String id;
	private String phone;
	private String brokerId;
	private String isVip;// 是否重要客户 0，是，1 否
	private String buildingNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	

	public String getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}

	@Override
	public String toString() {
		return "XcfcClient [name=" + name + ", id=" + id + ", phone=" + phone
				+ ", brokerId=" + brokerId + ", isVip=" + isVip
				+ ", buildingNum=" + buildingNum + "]";
	}

}

package com.movitech.grande.model;

import java.io.Serializable;

public class XcfcMyCustomer implements Serializable{

	private static final long serialVersionUID = 609165843427387121L;

	private String id;
	private String name;
	private String phone;
	private String gender;
	private String picsrc;
	private String buildingNum;
	private String buildingName;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPicsrc() {
		return picsrc;
	}
	public void setPicsrc(String picsrc) {
		this.picsrc = picsrc;
	}
	public String getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	@Override
	public String toString() {
		return "XcfcMyCustomer [id=" + id + ", name=" + name + ", phone="
				+ phone + ", gender=" + gender + ", picsrc=" + picsrc
				+ ", buildingNum=" + buildingNum + ", buildingName="
				+ buildingName + "]";
	}
	
}

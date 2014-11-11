package com.movitech.grande.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 26, 2014 8:47:04 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcInfoDetail {

	private String id;
	private String title;
	private String context;
	private String createTime;
	private String catagoryId;
	private String creater;
	private String address;
	private String addressX;
	private String addressY;
	private String isDisable;
	private String isHot;
	private String isPublish;
	private String isPush;
	private String picsrc;
	/*
	items is complicated but for now it is useless
	*/

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCatagoryId() {
		return catagoryId;
	}
	public void setCatagoryId(String catagoryId) {
		this.catagoryId = catagoryId;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddressX() {
		return addressX;
	}
	public void setAddressX(String addressX) {
		this.addressX = addressX;
	}
	public String getAddressY() {
		return addressY;
	}
	public void setAddressY(String addressY) {
		this.addressY = addressY;
	}
	public String getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	public String getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}
	public String getIsPush() {
		return isPush;
	}
	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}
	public String getPicsrc() {
		return picsrc;
	}
	public void setPicsrc(String picsrc) {
		this.picsrc = picsrc;
	}

	@Override
	public String toString() {
		return "XcfcInfoDetail [id=" + id + ", title=" + title + ", context="
				+ context + ", createTime=" + createTime + ", catagoryId="
				+ catagoryId + ", creater=" + creater + ", address=" + address
				+ ", addressX=" + addressX + ", addressY=" + addressY
				+ ", isDisable=" + isDisable + ", isHot=" + isHot
				+ ", isPublish=" + isPublish + ", isPush=" + isPush
				+ ", picsrc=" + picsrc + "]";
	}

}

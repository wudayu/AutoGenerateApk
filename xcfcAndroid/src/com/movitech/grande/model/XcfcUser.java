package com.movitech.grande.model;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 7:12:47 PM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcUser {

	String id;
	String name;
	String screenName;
	String mphone;
	String photosrc;
	String approveState; // Constant.APPROVE_STATE
	String brokerType;
	String superiorId;
	String city;

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
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
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getPhotosrc() {
		return photosrc;
	}
	public void setPhotosrc(String photosrc) {
		this.photosrc = photosrc;
	}
	public String getApproveState() {
		return approveState;
	}
	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}

	public String getBrokerType() {
		return brokerType;
	}
	public void setBrokerType(String brokerType) {
		this.brokerType = brokerType;
	}
	public String getSuperiorId() {
		return superiorId;
	}
	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}
	@Override
	public String toString() {
		return "XcfcUser [id=" + id + ", name=" + name + ", screenName="
				+ screenName + ", mphone=" + mphone + ", photosrc=" + photosrc
				+ ", approveState=" + approveState + "]";
	}
	
}

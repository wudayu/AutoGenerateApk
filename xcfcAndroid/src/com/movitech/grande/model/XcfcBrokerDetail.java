package com.movitech.grande.model;

import java.util.Arrays;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 3, 2014 5:11:40 PM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcBrokerDetail {

	private String id;
	private String name;
	private String mphone;
	private String level;	// 用户等级
	private String photosrc;
	private String ranking;	// 积分排名
	private String screenName;
	private String approveState;//认证状态
	private int integral;
	private int[] userNum;
	private double[] brokerageNum;
	private int isDisabled;

	public int getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(int isDisabled) {
		this.isDisabled = isDisabled;
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
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPhotosrc() {
		return photosrc;
	}
	public void setPhotosrc(String photosrc) {
		this.photosrc = photosrc;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int[] getUserNum() {
		return userNum;
	}
	public void setUserNum(int[] userNum) {
		this.userNum = userNum;
	}
	public double[] getBrokerageNum() {
		return brokerageNum;
	}
	public void setBrokerageNum(double[] brokerageNum) {
		this.brokerageNum = brokerageNum;
	}

	public String getApproveState() {
		return approveState;
	}
	public void setApproveState(String approveState) {
		this.approveState = approveState;
	}
	@Override
	public String toString() {
		return "XcfcBrokerDetail [id=" + id + ", name=" + name + ", mphone="
				+ mphone + ", level=" + level + ", photosrc=" + photosrc
				+ ", ranking=" + ranking + ", screenName=" + screenName
				+ ", approveState=" + approveState + ", integral=" + integral
				+ ", userNum=" + Arrays.toString(userNum) + ", brokerageNum="
				+ Arrays.toString(brokerageNum) + "]";
	}
}

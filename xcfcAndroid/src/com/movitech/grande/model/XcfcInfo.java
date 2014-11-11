package com.movitech.grande.model;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 23, 2014 10:14:35 AM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcInfo {

	private String id;
	private String title;
	private String picsrc;
	private String createTime;
	private String cityId;

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
	public String getPicsrc() {
		return picsrc;
	}
	public void setPicsrc(String picsrc) {
		this.picsrc = picsrc;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "XcfcInfo [id=" + id + ", title=" + title + ", picsrc=" + picsrc
				+ ", createTime=" + createTime + ", cityId=" + cityId + "]";
	}
}

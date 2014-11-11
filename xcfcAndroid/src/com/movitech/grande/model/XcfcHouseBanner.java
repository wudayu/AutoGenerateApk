package com.movitech.grande.model;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月17日 下午4:21:22 类说明
 */
public class XcfcHouseBanner {
	private String id;
	private String sourceType;
	private String title;
	private String newsId;
	private String picsrc;
	private String cityName;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getPicsrc() {
		return picsrc;
	}

	public void setPicsrc(String picsrc) {
		this.picsrc = picsrc;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return "XcfcHouseBanner [id=" + id + ", sourceType=" + sourceType
				+ ", title=" + title + ", newsId=" + newsId + ", picsrc="
				+ picsrc + ", cityName=" + cityName + "]";
	}
}

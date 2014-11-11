package com.movitech.grande.model;
/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月23日 下午5:57:54
 * 类说明
 */
public class XcfcInfoBanner {
   
	private String id;
	private String title;
	private String bannerSrc;
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
	public String getBannerSrc() {
		return bannerSrc;
	}
	public void setBannerSrc(String bannerSrc) {
		this.bannerSrc = bannerSrc;
	}
	@Override
	public String toString() {
		return "XcfcInfoBanner [id=" + id + ", title=" + title + ", bannerSrc="
				+ bannerSrc + "]";
	}
}

package com.movitech.grande.model;
/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月10日 下午2:59:49
 * 类说明
 */
public class XcfcServiceTerms {

	private String id;
	private String content;
	private String creater;
	private String sourceType;
	private String title;
	private String attachId;
	private String uname;
	private String isDisabled;
	private String createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
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
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "XcfcServiceTerms [id=" + id + ", content=" + content
				+ ", creater=" + creater + ", sourceType=" + sourceType
				+ ", title=" + title + ", attachId=" + attachId + ", uname="
				+ uname + ", isDisabled=" + isDisabled + ", createTime="
				+ createTime + "]";
	}
}

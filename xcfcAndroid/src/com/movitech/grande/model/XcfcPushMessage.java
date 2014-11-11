package com.movitech.grande.model;



/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 21, 2014 6:56:42 PM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcPushMessage {

	private String id;
	private String keyCode; //use PushMessageKeyCode
	private String content;
	private String brokerId;
	private String createTime;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBrokerId() {
		return brokerId;
	}
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "XcfcPushMessage [id=" + id + ", keyCode=" + keyCode
				+ ", content=" + content + ", brokerId=" + brokerId
				+ ", createTime=" + createTime + "]";
	}
}

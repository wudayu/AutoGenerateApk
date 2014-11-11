package com.movitech.grande.model;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 15, 2014 4:04:43 PM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcMyMessage {

	private String id;
	private String content;
	private String creater;
	private String createTime;
	private String flag;
	private String receiverId;
	private String receiverType;
	private String sourceId;
	private String sourceType;

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


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createrTime) {
		this.createTime = createrTime;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getReceiverId() {
		return receiverId;
	}


	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}


	public String getReceiverType() {
		return receiverType;
	}


	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}


	public String getSourceId() {
		return sourceId;
	}


	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}


	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Override
	public String toString() {
		return "Xcfc\bMyMessage [id=" + id + ", content=" + content
				+ ", creater=" + creater + ", createrTime=" + createTime
				+ ", flag=" + flag + ", receiverId=" + receiverId
				+ ", receiverType=" + receiverType + ", sourceId=" + sourceId
				+ ", sourceType=" + sourceType + "]";
	}
}

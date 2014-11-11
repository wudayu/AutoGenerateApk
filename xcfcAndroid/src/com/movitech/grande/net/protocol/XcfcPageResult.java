package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 20, 2014 3:25:01 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcPageResult {

	@JsonProperty(value = "curPageNo")
	private int curPageNo;
	@JsonProperty(value = "pageNo")
	private int pageNo;
	@JsonProperty(value = "pageSize")
	private int pageSize;
	@JsonProperty(value = "showFlag")
	private boolean showFlag;
	@JsonProperty(value = "totalSize")
	private int totalSize;

	public int getCurPageNo() {
		return curPageNo;
	}
	public void setCurPageNo(int curPageNo) {
		this.curPageNo = curPageNo;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isShowFlag() {
		return showFlag;
	}
	public void setShowFlag(boolean showFlag) {
		this.showFlag = showFlag;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public String toString() {
		return "XcfcPageBaseResult [curPageNo=" + curPageNo + ", pageNo="
				+ pageNo + ", pageSize=" + pageSize + ", showFlag=" + showFlag
				+ ", totalSize=" + totalSize + "]";
	}
}

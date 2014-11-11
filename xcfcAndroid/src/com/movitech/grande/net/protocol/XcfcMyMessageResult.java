package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 15, 2014 4:21:50 PM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcMyMessageResult extends BaseResult {
	@JsonProperty(value = "objValue")
	XcfcMyMessagePageResult pageResult;

	public XcfcMyMessagePageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(XcfcMyMessagePageResult pageResult) {
		this.pageResult = pageResult;
	}

	@Override
	public String toString() {
		return "XcfcMyMessageResult [pageResult=" + pageResult + "]";
	}

}

package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcMyCustomerResult extends BaseResult {

	@JsonProperty(value = "objValue")
	XcfcMyCustomerPageResult pageResult;

	public XcfcMyCustomerPageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(XcfcMyCustomerPageResult pageResult) {
		this.pageResult = pageResult;
	}

	@Override
	public String toString() {
		return "XcfcMyCustomerResult [pageResult=" + pageResult + "]";
	}
}

package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 20, 2014 5:44:43 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcHousesResult extends BaseResult {

	@JsonProperty(value = "objValue")
	XcfcHousesPageResult pageResult;

	public XcfcHousesPageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(XcfcHousesPageResult pageResult) {
		this.pageResult = pageResult;
	}

	@Override
	public String toString() {
		return "XcfcHouseResult [pageResult=" + pageResult + "]";
	}
}

package com.movitech.grande.net.protocol;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Warkey.Song
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcCommissionResult extends BaseResult {
	@JsonProperty(value = "objValue")
	XcfcCommissionPageResult pageResult;
	
	public XcfcCommissionPageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(XcfcCommissionPageResult pageResult) {
		this.pageResult = pageResult;
	}
	
	@Override
	public String toString() {
		return "XcfcCommissionPageResult [pageResult=" + pageResult + "]";
	}
}

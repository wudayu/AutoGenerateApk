package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月15日 下午2:47:23 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcClientResult extends BaseResult {
	@JsonProperty(value = "objValue")
	XcfcClientPageResult pageResult;

	public XcfcClientPageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(XcfcClientPageResult pageResult) {
		this.pageResult = pageResult;
	}

	@Override
	public String toString() {
		return "XcfcClientResult [pageResult=" + pageResult
				+ ", getPageResult()=" + getPageResult()
				+ ", getResultSuccess()=" + getResultSuccess()
				+ ", getResultMsg()=" + getResultMsg() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

}

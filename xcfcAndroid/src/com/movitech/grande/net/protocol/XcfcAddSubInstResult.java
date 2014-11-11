package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月28日 下午5:40:13
 * 类说明
 */
public class XcfcAddSubInstResult extends BaseResult{

	@JsonProperty(value = "objValue")
	private String objValue;
	
	@JsonProperty(value = "ok")
	private Boolean ok;

	public String getObjValue() {
		return objValue;
	}

	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}
}

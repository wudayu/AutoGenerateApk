package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月16日 下午6:05:49 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcCommissionConfirmResult extends BaseResult {
	@JsonProperty(value = "objValue")
	String objValue;
	@JsonProperty(value = "ok")
	boolean ok;

	public String getObjValue() {
		return objValue;
	}

	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
}

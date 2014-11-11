package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月10日 上午9:51:36 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcForgetPassWordResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private String objValue;

	@JsonProperty(value = "ok")
	private boolean ok;

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

	@Override
	public String toString() {
		return "XcfcForgetPassWordResult [objValue=" + objValue + ", ok=" + ok
				+ "]";
	}
}

package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcEarnIntegral;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月17日 上午11:17:26 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcEarnIntegralResult extends BaseResult {
	@JsonProperty(value = "objValue")
	XcfcEarnIntegral objValue;
	@JsonProperty(value = "ok")
	boolean ok;

	public XcfcEarnIntegral getObjValue() {
		return objValue;
	}

	public void setObjValue(XcfcEarnIntegral objValue) {
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
		return "XcfcEarnIntegralResult [objValue=" + objValue + ", ok=" + ok
				+ "]";
	}
}

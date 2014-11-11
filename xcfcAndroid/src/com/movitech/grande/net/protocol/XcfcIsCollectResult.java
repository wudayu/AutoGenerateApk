package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月7日 下午2:42:30 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcIsCollectResult extends BaseResult {
	@JsonProperty(value = "objValue")
	boolean objValue;
	@JsonProperty(value = "ok")
	boolean ok;

	public boolean isObjValue() {
		return objValue;
	}

	public void setObjValue(boolean objValue) {
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
		return "XcfcCommissionApplyResult [objValue=" + objValue + ", ok=" + ok
				+ "]";
	}
}

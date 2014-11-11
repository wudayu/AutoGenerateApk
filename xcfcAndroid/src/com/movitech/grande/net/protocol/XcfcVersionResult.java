package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月10日 下午3:52:13 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcVersionResult extends BaseResult {
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

	@Override
	public String toString() {
		return "XcfcVersionResult [objValue=" + objValue + ", ok=" + ok + "]";
	}
}

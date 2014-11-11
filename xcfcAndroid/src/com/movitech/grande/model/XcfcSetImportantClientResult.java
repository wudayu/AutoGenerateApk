package com.movitech.grande.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.net.protocol.BaseResult;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月15日 下午5:58:53
 * 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcSetImportantClientResult extends BaseResult{
	@JsonProperty(value = "objValue")
	String objValue;
	@JsonProperty(value = "ok")
	boolean ok;

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getObjValue() {
		return objValue;
	}

	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}

	@Override
	public String toString() {
		return "XcfcSetImportantClientResult [objValue=" + objValue + ", ok="
				+ ok + "]";
	}
}

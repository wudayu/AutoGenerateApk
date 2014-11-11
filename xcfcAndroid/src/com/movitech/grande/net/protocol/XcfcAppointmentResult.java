package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月2日 下午1:02:08
 *  类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcAppointmentResult extends BaseResult {
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
		return "XcfcRecommendedResult [objValue=" + objValue + ", ok=" + ok + "]";
	}

}

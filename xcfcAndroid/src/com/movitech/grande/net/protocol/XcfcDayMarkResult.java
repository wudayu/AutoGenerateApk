package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月15日 上午9:27:52
 * 类说明
 */
public class XcfcDayMarkResult extends BaseResult{
  
	@JsonProperty(value="objValue")
	String objValue;
	@JsonProperty(value="ok")
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
		return "XcfcDayMark [objValue=" + objValue + ", ok=" + ok + "]";
	}
}

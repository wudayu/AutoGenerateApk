package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 8, 2014 4:03:23 PM
 * @Description: This is David Wu's property.
 * 
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcGuestIdResult extends BaseResult {
	@JsonProperty(value = "objValue")
	String objValue;
	
	@JsonProperty(value = "ok")
	boolean ok;

	public String getObjValue() {
		return objValue;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public void setObjValue(String objValue) {
		this.objValue = objValue;
	}

	public String getGuestId() {
		return objValue;
	}

	@Override
	public String toString() {
		return "XcfcGuestIdResult [" + super.toString() + ", objValue="
				+ objValue + "]";
	}
}

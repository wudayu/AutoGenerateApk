package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 9, 2014 10:14:20 AM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcVerificationCodeResult extends BaseResult {

	@JsonProperty(value = "objValue")
	int objValue;

	public int getObjValue() {
		return objValue;
	}

	public void setObjValue(int objValue) {
		this.objValue = objValue;
	}

	@Override
	public String toString() {
		return "XcfcVerificationCodeResult [objValue=" + objValue + "]";
	}
}

package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcPushMessage;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 22, 2014 10:35:50 AM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcPushMessageResult extends BaseResult {

	@JsonProperty(value = "objValue")
	XcfcPushMessage[] objValue;

	public XcfcPushMessage[] getObjValue() {
		return objValue;
	}

	public void setObjValue(XcfcPushMessage[] objValue) {
		this.objValue = objValue;
	}

	@Override
	public String toString() {
		return "XcfcPushMessageResult [objValue=" + objValue + "]";
	}

}
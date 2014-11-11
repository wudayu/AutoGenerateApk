package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 4:08:27 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResult{

	@JsonProperty(value = "result")
	private boolean result;

	@JsonProperty(value = "value")
	private String value;

	public boolean getResultSuccess() {
		return result;
	}

	public String getResultMsg() {
		return value;
	}

	@Override
	public String toString() {
		return "result = " + result + ", value = " + value;
	}
}
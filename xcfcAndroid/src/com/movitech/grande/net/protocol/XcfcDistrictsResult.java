package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcDistrict;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 20, 2014 9:36:11 AM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcDistrictsResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private XcfcDistrict[] districts;

	public XcfcDistrict[] getDistricts() {
		return districts;
	}

	public void setDistricts(XcfcDistrict[] districts) {
		this.districts = districts;
	}

	@Override
	public String toString() {
		return "XcfcDistrictsResult [" + super.toString() + "districts=" + Arrays.toString(districts)
				+ "]";
	}
}

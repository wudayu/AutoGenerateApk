package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcCity;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 8:26:48 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcCitiesResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private XcfcCity[] cities;

	public XcfcCity[] getCities() {
		return cities;
	}

	public void setCities(XcfcCity[] cities) {
		this.cities = cities;
	}

	@Override
	public String toString() {
		return "XcfcCityResult [" + super.toString() + ", cities=" + Arrays.toString(cities) + "]";
	}
}

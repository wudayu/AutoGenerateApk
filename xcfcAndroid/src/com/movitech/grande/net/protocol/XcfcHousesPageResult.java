package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcHouse;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 20, 2014 3:32:57 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcHousesPageResult extends XcfcPageResult {
	
	
	@JsonProperty(value = "resultList")
	XcfcHouse[] houses;

	public XcfcHouse[] getHouses() {
		return houses;
	}

	public void setHouses(XcfcHouse[] houses) {
		this.houses = houses;
	}

	@Override
	public String toString() {
		return "XcfcHousesPageResult [" + super.toString() + ", houses=" + Arrays.toString(houses) + "]";
	}
}

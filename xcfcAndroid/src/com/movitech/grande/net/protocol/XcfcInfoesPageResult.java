package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcInfo;


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
public class XcfcInfoesPageResult extends XcfcPageResult {

	@JsonProperty(value = "resultList")
	XcfcInfo[] infoes;

	public XcfcInfo[] getInfoes() {
		return infoes;
	}

	public void setInfoes(XcfcInfo[] infoes) {
		this.infoes = infoes;
	}

	@Override
	public String toString() {
		return "XcfcInfoesPageResult [" + super.toString() + ", infoes=" + Arrays.toString(infoes) + "]";
	}
}

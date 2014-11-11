package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcImageReturn;

/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @Created Time: Nov 7, 2014, 1:52:55 PM
 * @Description: David Wu created this file.
 *
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcImageReturnResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private XcfcImageReturn image;

	public XcfcImageReturn getImage() {
		return image;
	}

	public void setImage(XcfcImageReturn image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "XcfcImageReturnResult [image=" + image + ", " + super.toString() + "]";
	}

}
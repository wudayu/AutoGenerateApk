package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcInfoDetail;


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
public class XcfcInfoDetailResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private XcfcInfoDetail infoDetail;

	public XcfcInfoDetail getInfoDetail() {
		return infoDetail;
	}

	public void setInfoDetail(XcfcInfoDetail infoDetail) {
		this.infoDetail = infoDetail;
	}

	@Override
	public String toString() {
		return "XcfcInfoDetailResult [" + super.toString() + ", infoDetail=" + infoDetail + "]";
	}

}

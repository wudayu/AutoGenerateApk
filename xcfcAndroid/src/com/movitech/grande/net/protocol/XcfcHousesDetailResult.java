package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcHouseDetail;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcHousesDetailResult extends BaseResult {

	@JsonProperty(value = "objValue")
	XcfcHouseDetail houseDetail;

	public XcfcHouseDetail getHouseDetail() {
		return houseDetail;
	}

	public void setHouseDetail(XcfcHouseDetail houseDetail) {
		this.houseDetail = houseDetail;
	}

	@Override
	public String toString() {
		return "XcfcHousesDetailResult [houses=" + houseDetail + "]";
	}

}

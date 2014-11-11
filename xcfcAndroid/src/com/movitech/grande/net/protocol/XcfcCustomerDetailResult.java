package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcCustomerDetail;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcCustomerDetailResult extends BaseResult {

	@JsonProperty(value = "objValue")
	XcfcCustomerDetail customerDetail;

	public XcfcCustomerDetail getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(XcfcCustomerDetail customerDetail) {
		this.customerDetail = customerDetail;
	}

	@Override
	public String toString() {
		return "XcfcCustomerDetailResult [customerDetail=" + customerDetail
				+ "]";
	}

}

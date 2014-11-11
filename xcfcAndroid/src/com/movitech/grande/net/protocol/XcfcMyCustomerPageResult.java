package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcMyCustomer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcMyCustomerPageResult extends XcfcPageResult {

	@JsonProperty(value = "resultList")
	XcfcMyCustomer[] customers;

	public XcfcMyCustomer[] getCustomers() {
		return customers;
	}

	public void setCustomers(XcfcMyCustomer[] customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "XcfcMyCustomerPageResult [customers="
				+ Arrays.toString(customers) + "]";
	}

}

package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcIntegral;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcIntegralResult extends BaseResult{
	
	@JsonProperty(value = "objValue")
	XcfcIntegral[] integrals;

	public XcfcIntegral[] getIntegrals() {
		return integrals;
	}

	public void setIntegrals(XcfcIntegral[] integrals) {
		this.integrals = integrals;
	}

	@Override
	public String toString() {
		return "XcfcIntegralResult [integrals=" + Arrays.toString(integrals)
				+ "]";
	}
	
}

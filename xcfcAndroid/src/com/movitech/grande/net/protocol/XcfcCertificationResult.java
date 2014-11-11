package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcCertification;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcCertificationResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private XcfcCertification certification;

	public XcfcCertification getCertification() {
		return certification;
	}

	public void setCertification(XcfcCertification certification) {
		this.certification = certification;
	}

	@Override
	public String toString() {
		return "XcfcCertificationResult [certification=" + certification + "]";
	}

}

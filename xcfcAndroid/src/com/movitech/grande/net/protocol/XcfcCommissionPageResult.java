package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcCommission;

/**
 * 
 * @author Warkey.Song
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcCommissionPageResult extends XcfcPageResult {
	@JsonProperty(value = "resultList")
	private XcfcCommission[] commissions;
	@JsonProperty(value = "amounts")
	private String amounts;
	public String getAmounts() {
		return amounts;
	}
	public void setAmounts(String amounts) {
		this.amounts = amounts;
	}
	public XcfcCommission[] getCommissions() {
		return commissions;
	}

	public void setCommissions(XcfcCommission[] commissions) {
		this.commissions = commissions;
	}

	@Override
	public String toString() {
		return "XcfcCommissionResult [commissions="
				+ Arrays.toString(commissions) + "]";
	}

}

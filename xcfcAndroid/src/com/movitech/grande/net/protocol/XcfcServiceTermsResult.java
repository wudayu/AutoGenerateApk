package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcServiceTerms;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月10日 下午3:04:31 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcServiceTermsResult extends BaseResult {
   
	@JsonProperty(value = "objValue")
	XcfcServiceTerms serviceTerms;
	
	@JsonProperty(value = "ok")
	boolean ok;

	public XcfcServiceTerms getServiceTerms() {
		return serviceTerms;
	}

	public void setServiceTerms(XcfcServiceTerms serviceTerms) {
		this.serviceTerms = serviceTerms;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	@Override
	public String toString() {
		return "XcfcServiceTerms [serviceTerms=" + serviceTerms + ", ok=" + ok
				+ "]";
	}
	
}

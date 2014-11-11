package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcBrokerDetail;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 3, 2014 5:20:14 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcBrokerDetailResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private XcfcBrokerDetail brokerDetail;

	public XcfcBrokerDetail getBrokerDetail() {
		return brokerDetail;
	}

	public void setBrokerDetail(XcfcBrokerDetail brokerDetail) {
		this.brokerDetail = brokerDetail;
	}

	@Override
	public String toString() {
		return "XcfcBrokerDetailResult [" + super.toString() + ", brokerDetail=" + brokerDetail + "]";
	}
}

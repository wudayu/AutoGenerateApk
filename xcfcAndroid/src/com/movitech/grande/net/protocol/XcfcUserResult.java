package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcUser;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 7:43:51 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcUserResult extends BaseResult {

	@JsonProperty(value = "objValue")
	private XcfcUser user;

	public XcfcUser getUser() {
		return user;
	}

	public void setUser(XcfcUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "XcfcUserResult [" + super.toString() + ", user=" + user + "]";
	}
}

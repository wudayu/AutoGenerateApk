package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcMyMessage;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 15, 2014 4:12:43 PM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcMyMessagePageResult extends XcfcPageResult {

	@JsonProperty(value = "resultList")
	private XcfcMyMessage[] messages;

	public XcfcMyMessage[] getMessages() {
		return messages;
	}

	public void setMessages(XcfcMyMessage[] messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return "XcfcMyMessagePageResult [" + super.toString() + ", messages=" + Arrays.toString(messages)
				+ "]";
	}
}

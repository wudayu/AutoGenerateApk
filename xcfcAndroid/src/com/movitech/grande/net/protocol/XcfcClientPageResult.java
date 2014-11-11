package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcClient;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月15日 下午2:41:11 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcClientPageResult extends XcfcPageResult {

	@JsonProperty(value = "resultList")
	XcfcClient[] clients;

	public XcfcClient[] getClients() {
		return clients;
	}

	public void setClients(XcfcClient[] clients) {
		this.clients = clients;
	}
}

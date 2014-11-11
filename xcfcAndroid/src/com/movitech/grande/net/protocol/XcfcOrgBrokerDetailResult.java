package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcOrgBrokerDetail;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年9月2日 下午3:29:23
 * 类说明
 */
public class XcfcOrgBrokerDetailResult extends BaseResult{
	
	@JsonProperty(value = "objValue")
	XcfcOrgBrokerDetail orgBrokerDetail;

	public XcfcOrgBrokerDetail getOrgBrokerDetail() {
		return orgBrokerDetail;
	}

	public void setOrgBrokerDetail(XcfcOrgBrokerDetail orgBrokerDetail) {
		this.orgBrokerDetail = orgBrokerDetail;
	}
  
}

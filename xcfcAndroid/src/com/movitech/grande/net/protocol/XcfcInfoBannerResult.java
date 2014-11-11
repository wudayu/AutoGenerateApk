package com.movitech.grande.net.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcInfoBanner;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月23日 下午5:59:23
 * 类说明
 */
public class XcfcInfoBannerResult extends BaseResult{
	@JsonProperty(value = "objValue")
    XcfcInfoBanner [] infoBanner;

	public XcfcInfoBanner[] getInfoBanner() {
		return infoBanner;
	}

	public void setInfoBanner(XcfcInfoBanner[] infoBanner) {
		this.infoBanner = infoBanner;
	}
}

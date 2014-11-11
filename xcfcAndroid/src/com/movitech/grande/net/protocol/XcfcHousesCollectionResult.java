package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcHouseDetail;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月4日 下午4:47:48 
 * 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcHousesCollectionResult extends BaseResult {
	@JsonProperty(value = "objValue")
	XcfcHouseDetail[] houseDetail;

	public XcfcHouseDetail[] getHouseDetail() {
		return houseDetail;
	}

	public void setHouseDetail(XcfcHouseDetail[] houseDetail) {
		this.houseDetail = houseDetail;
	}

	@Override
	public String toString() {
		return "XcfcHousesCollectionResult [houseDetail="
				+ Arrays.toString(houseDetail) + "]";
	}

}

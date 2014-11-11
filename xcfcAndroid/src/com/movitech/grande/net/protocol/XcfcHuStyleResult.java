package com.movitech.grande.net.protocol;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movitech.grande.model.XcfcBuildHuStyle;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月19日 下午2:27:07
 * 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcHuStyleResult extends BaseResult{
	@JsonProperty(value = "objValue")
	private XcfcBuildHuStyle [] huStyleItems;

	public XcfcBuildHuStyle[] getHuStyleItems() {
		return huStyleItems;
	}

	public void setHuStyleItems(XcfcBuildHuStyle[] huStyleItems) {
		this.huStyleItems = huStyleItems;
	}

	@Override
	public String toString() {
		return "XcfcHuStyleResult [huStyleItems="
				+ Arrays.toString(huStyleItems) + "]";
	}
}

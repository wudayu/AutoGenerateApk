package com.movitech.grande.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年9月5日 下午5:31:10
 * 类说明
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcBuildHuStyle {
	private String id;
	private String buildingId;
	private String houseType;
	private String itemId;
	@JsonProperty(value = "attach")
	private XcfcHouseDetailAttach attach;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public XcfcHouseDetailAttach getAttach() {
		return attach;
	}
	public void setAttach(XcfcHouseDetailAttach attach) {
		this.attach = attach;
	}
}

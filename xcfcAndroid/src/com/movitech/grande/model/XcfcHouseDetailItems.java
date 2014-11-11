package com.movitech.grande.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 20, 2014 3:01:50 PM
 * @Description: This is David Wu's property.
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class XcfcHouseDetailItems implements Serializable {

	/* about sourceType:
	 * 1.实景图,2.效果图,3.户型图,4.样板间,5.楼盘图片,6.交通配套,7.楼盘介绍,8.交通图,9.楼盘缩略图
	 * use HouseDetailImageSourceType
	 */

	private static final long serialVersionUID = 5376283845515821413L;

	private String id;
	private String sourceType;
	private String buildingId;
	private String attachId;
	private String houseType;
	private String housePicType;
	private String houseTypeArea;
	private String houseTypeDesc;
	private String houseTypeName;

	
	@JsonProperty(value = "attach")
	private XcfcHouseDetailAttach attach;
	
	public String getHouseTypeName() {
		return houseTypeName;
	}
	public void setHouseTypeName(String houseTypeName) {
		this.houseTypeName = houseTypeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getHouseType() {
		return houseType;
	}
	public String getHousePicType() {
		return housePicType;
	}
	public void setHousePicType(String housePicType) {
		this.housePicType = housePicType;
	}
	public String getHouseTypeArea() {
		return houseTypeArea;
	}
	public void setHouseTypeArea(String houseTypeArea) {
		this.houseTypeArea = houseTypeArea;
	}
	public String getHouseTypeDesc() {
		return houseTypeDesc;
	}
	public void setHouseTypeDesc(String houseTypeDesc) {
		this.houseTypeDesc = houseTypeDesc;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public XcfcHouseDetailAttach getAttach() {
		return attach;
	}
	public void setAttach(XcfcHouseDetailAttach attach) {
		this.attach = attach;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "XcfcHouseDetailItems [id=" + id + ", sourceType=" + sourceType
				+ ", buildingId=" + buildingId + ", attachId=" + attachId
				+ ", houseType=" + houseType + ", housePicType=" + housePicType
				+ ", houseTypeArea=" + houseTypeArea + ", houseTypeDesc="
				+ houseTypeDesc + ", houseTypeName=" + houseTypeName
				+ ", attach=" + attach + "]";
	}
	
}

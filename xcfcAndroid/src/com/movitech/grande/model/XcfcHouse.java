package com.movitech.grande.model;

import java.io.Serializable;


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
public class XcfcHouse implements Serializable {

	private static final long serialVersionUID = 5376283845515821413L;

	private String id;
	private String name;
	private String area; // district
	private String city;
	private String discount;
	private String characteristic;
	private String createTime;
	private String propertyFee;
	private String propertyType;
	private String saleStartAt;
	private String type;
	private String hotline;
	private String isHot;
	private String isRecommand; // recommend
	private String picsrc;
	private String planSuite;
	private String plotRatio;
	private String pricePerSuiteScope;
	private String fitmentLevel;
	private String buildingArea;
	private String briefIntroduction;
	private String greeningRate;
	private String buildingType;
	private String price;
	private String salePoint;
	private String buildingTypeName;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCharacteristic() {
		return characteristic;
	}
	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getPropertyFee() {
		return propertyFee;
	}
	public void setPropertyFee(String propertyFee) {
		this.propertyFee = propertyFee;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getSaleStartAt() {
		return saleStartAt;
	}
	public void setSaleStartAt(String saleStartAt) {
		this.saleStartAt = saleStartAt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHotline() {
		return hotline;
	}
	public void setHotline(String hotline) {
		this.hotline = hotline;
	}
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	public String getIsRecommand() {
		return isRecommand;
	}
	public void setIsRecommand(String isRecommand) {
		this.isRecommand = isRecommand;
	}
	public String getPicsrc() {
		return picsrc;
	}
	public void setPicsrc(String picsrc) {
		this.picsrc = picsrc;
	}
	public String getPlanSuite() {
		return planSuite;
	}
	public void setPlanSuite(String planSuite) {
		this.planSuite = planSuite;
	}
	public String getPlotRatio() {
		return plotRatio;
	}
	public void setPlotRatio(String plotRatio) {
		this.plotRatio = plotRatio;
	}
	public String getPricePerSuiteScope() {
		return pricePerSuiteScope;
	}
	public void setPricePerSuiteScope(String pricePerSuiteScope) {
		this.pricePerSuiteScope = pricePerSuiteScope;
	}
	public String getBuildingArea() {
		return buildingArea;
	}
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public String getFitmentLevel() {
		return fitmentLevel;
	}
	public void setFitmentLevel(String fitmentLevel) {
		this.fitmentLevel = fitmentLevel;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public String getGreeningRate() {
		return greeningRate;
	}
	public void setGreeningRate(String greeningRate) {
		this.greeningRate = greeningRate;
	}
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSalePoint() {
		return salePoint;
	}
	public void setSalePoint(String salePoint) {
		this.salePoint = salePoint;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBuildingTypeName() {
		return buildingTypeName;
	}
	public void setBuildingTypeName(String buildingTypeName) {
		this.buildingTypeName = buildingTypeName;
	}

	@Override
	public String toString() {
		return "XcfcHouse [id=" + id + ", name=" + name + ", area=" + area
				+ ", city=" + city + ", discount=" + discount
				+ ", characteristic=" + characteristic + ", createTime="
				+ createTime + ", propertyFee=" + propertyFee
				+ ", propertyType=" + propertyType + ", saleStartAt="
				+ saleStartAt + ", type=" + type + ", hotline=" + hotline
				+ ", isHot=" + isHot + ", isRecommand=" + isRecommand
				+ ", picsrc=" + picsrc + ", planSuite=" + planSuite
				+ ", plotRatio=" + plotRatio + ", pricePerSuiteScope="
				+ pricePerSuiteScope + ", fitmentLevel=" + fitmentLevel
				+ ", buildingArea=" + buildingArea + ", briefIntroduction="
				+ briefIntroduction + ", greeningRate=" + greeningRate
				+ ", buildingType=" + buildingType + ", price=" + price
				+ ", salePoint=" + salePoint + ", buildingTypeName="
				+ buildingTypeName + "]";
	}
}

package com.movitech.grande.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class XcfcHouseDetail implements Serializable {

	private static final long serialVersionUID = 5376283845515821413L;

	private String address;
	private String name;
	private String id;
	private String type;
	private String creater;
	private String propertyType;
	private String ratio;
	private String city;
	private String area; // district
	
	@JsonProperty(value = "items")
	private XcfcHouseDetailItems[] items;
	
	private String saleStartAt;
	private String characteristic;
	private String createTime;
	private String isPublish;
	private String propertyRightsName;
	private String fitmentLevelName;
	private String discount;
	private String pricePerSuiteScope;//total price
	private String fitmentLever;
	private String planSuite;
	private String buildingArea;
	private String plotRatio;
	private String greeningRate;
	private String propertyFee;
	private String advantage;
	private String briefIntroduction;
	private String isRecommand; // recommend
	private String hotline;
	private String parkInfo;
	private String addressX;
	private String addressY;
	private String trafficCfg;
	private String isHot;
	private String price;
	private String buildingType;
	private String buildingTypeName;//deleted
	private String salePoint;
	private String discountEndTime;
	private String propertyCompany;
	private String deliveryTime;
	private String propertyRights;
	
	@JsonProperty(value = "houseTypeItems")
	private XcfcHouseDetailItems[] houseTypeItems;
	
	private String recommendedNum;
	private String bespeakNum;
	
	public String getAddress() {
		return address;
	}


	public String getFitmentLever() {
		return fitmentLever;
	}


	public void setFitmentLever(String fitmentLever) {
		this.fitmentLever = fitmentLever;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPropertyType() {
		return propertyType;
	}


	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}


	public String getRatio() {
		return ratio;
	}


	public void setRatio(String ratio) {
		this.ratio = ratio;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getSaleStartAt() {
		return saleStartAt;
	}


	public void setSaleStartAt(String saleStartAt) {
		this.saleStartAt = saleStartAt;
	}


	public String getCharacteristic() {
		return characteristic;
	}


	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}


	public String getCreater() {
		return creater;
	}


	public void setCreater(String creater) {
		this.creater = creater;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getDiscount() {
		return discount;
	}


	public void setDiscount(String discount) {
		this.discount = discount;
	}


	public String getPricePerSuiteScope() {
		return pricePerSuiteScope;
	}


	public void setPricePerSuiteScope(String pricePerSuiteScope) {
		this.pricePerSuiteScope = pricePerSuiteScope;
	}


	public String getFitmentLevelName() {
		return fitmentLevelName;
	}


	public void setFitmentLevelName(String fitmentLevelName) {
		this.fitmentLevelName = fitmentLevelName;
	}


	public String getPlanSuite() {
		return planSuite;
	}


	public void setPlanSuite(String planSuite) {
		this.planSuite = planSuite;
	}


	public String getBuildingArea() {
		return buildingArea;
	}


	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}


	public String getPlotRatio() {
		return plotRatio;
	}


	public void setPlotRatio(String plotRatio) {
		this.plotRatio = plotRatio;
	}


	public String getGreeningRate() {
		return greeningRate;
	}


	public void setGreeningRate(String greeningRate) {
		this.greeningRate = greeningRate;
	}


	public String getPropertyFee() {
		return propertyFee;
	}


	public void setPropertyFee(String propertyFee) {
		this.propertyFee = propertyFee;
	}


	public String getAdvantage() {
		return advantage;
	}


	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}


	public String getIsRecommand() {
		return isRecommand;
	}


	public void setIsRecommand(String isRecommand) {
		this.isRecommand = isRecommand;
	}


	public String getHotline() {
		return hotline;
	}


	public void setHotline(String hotline) {
		this.hotline = hotline;
	}


	public String getParkInfo() {
		return parkInfo;
	}


	public void setParkInfo(String parkInfo) {
		this.parkInfo = parkInfo;
	}


	public String getAddressX() {
		return addressX;
	}


	public void setAddressX(String addressX) {
		this.addressX = addressX;
	}


	public String getAddressY() {
		return addressY;
	}


	public void setAddressY(String addressY) {
		this.addressY = addressY;
	}


	public String getTrafficCfg() {
		return trafficCfg;
	}


	public void setTrafficCfg(String trafficCfg) {
		this.trafficCfg = trafficCfg;
	}


	public String getIsHot() {
		return isHot;
	}


	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getBuildingType() {
		return buildingType;
	}


	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}


	public String getBuildingTypeName() {
		return buildingTypeName;
	}


	public void setBuildingTypeName(String buildingTypeName) {
		this.buildingTypeName = buildingTypeName;
	}


	public String getSalePoint() {
		return salePoint;
	}


	public void setSalePoint(String salePoint) {
		this.salePoint = salePoint;
	}


	public String getRecommendedNum() {
		return recommendedNum;
	}


	public void setRecommendedNum(String recommendedNum) {
		this.recommendedNum = recommendedNum;
	}


	public String getBespeakNum() {
		return bespeakNum;
	}


	public void setBespeakNum(String bespeakNum) {
		this.bespeakNum = bespeakNum;
	}


	public String getBriefIntroduction() {
		return briefIntroduction;
	}


	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}


	public String getDiscountEndTime() {
		return discountEndTime;
	}


	public void setDiscountEndTime(String discountEndTime) {
		this.discountEndTime = discountEndTime;
	}


	public String getIsPublish() {
		return isPublish;
	}


	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}


	public String getPropertyCompany() {
		return propertyCompany;
	}


	public void setPropertyCompany(String propertyCompany) {
		this.propertyCompany = propertyCompany;
	}


	public String getDeliveryTime() {
		return deliveryTime;
	}


	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}


	public String getPropertyRights() {
		return propertyRights;
	}


	public void setPropertyRights(String propertyRights) {
		this.propertyRights = propertyRights;
	}


	public String getPropertyRightsName() {
		return propertyRightsName;
	}


	public void setPropertyRightsName(String propertyRightsName) {
		this.propertyRightsName = propertyRightsName;
	}


	public XcfcHouseDetailItems[] getItems() {
		return items;
	}


	public void setItems(XcfcHouseDetailItems[] items) {
		this.items = items;
	}


	public XcfcHouseDetailItems[] getHouseTypeItems() {
		return houseTypeItems;
	}


	public void setHouseTypeItems(XcfcHouseDetailItems[] houseTypeItems) {
		this.houseTypeItems = houseTypeItems;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * 
	 * @param type, use HouseDetailImageSourceType
	 * @return pictures list
	 */
	public List<String> getImageAddressesBySourceType(int type) {
		List<String> addresses = new ArrayList<String>();
		if (items == null) {
			return addresses;
		}
		for (int i = 0; i < items.length; ++i) {
			if (("" + type).equals(items[i].getSourceType())) {
				addresses.add(items[i].getAttach().getUname());
			}
		}
		return addresses;
	}
	
	/**
	 * 
	 * @return hustyle image Url
	 */
	public List<String> getHuStyleImageAddress(){
		List<String> addresses = new ArrayList<String>();
		for (int i = 0; i < houseTypeItems.length; ++i) {
			addresses.add(houseTypeItems[i].getAttach().getUname());
		}
		return addresses;
	}


	@Override
	public String toString() {
		return "XcfcHouseDetail [address=" + address + ", name=" + name
				+ ", id=" + id + ", type=" + type + ", creater=" + creater
				+ ", propertyType=" + propertyType + ", ratio=" + ratio
				+ ", city=" + city + ", area=" + area + ", items="
				+ Arrays.toString(items) + ", saleStartAt=" + saleStartAt
				+ ", characteristic=" + characteristic + ", createTime="
				+ createTime + ", isPublish=" + isPublish
				+ ", propertyRightsName=" + propertyRightsName
				+ ", fitmentLevelName=" + fitmentLevelName + ", discount="
				+ discount + ", pricePerSuiteScope=" + pricePerSuiteScope
				+ ", fitmentLever=" + fitmentLever + ", planSuite=" + planSuite
				+ ", buildingArea=" + buildingArea + ", plotRatio=" + plotRatio
				+ ", greeningRate=" + greeningRate + ", propertyFee="
				+ propertyFee + ", advantage=" + advantage
				+ ", briefIntroduction=" + briefIntroduction + ", isRecommand="
				+ isRecommand + ", hotline=" + hotline + ", parkInfo="
				+ parkInfo + ", addressX=" + addressX + ", addressY="
				+ addressY + ", trafficCfg=" + trafficCfg + ", isHot=" + isHot
				+ ", price=" + price + ", buildingType=" + buildingType
				+ ", buildingTypeName=" + buildingTypeName + ", salePoint="
				+ salePoint + ", discountEndTime=" + discountEndTime
				+ ", propertyCompany=" + propertyCompany + ", deliveryTime="
				+ deliveryTime + ", propertyRights=" + propertyRights
				+ ", houseTypeItems=" + Arrays.toString(houseTypeItems)
				+ ", recommendedNum=" + recommendedNum + ", bespeakNum="
				+ bespeakNum + "]";
	}

}

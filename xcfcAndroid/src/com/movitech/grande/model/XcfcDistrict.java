package com.movitech.grande.model;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 20, 2014 9:21:13 AM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcDistrict {

	private String id;
	private String cityId;
	private String name;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "XcfcDistrict [id=" + id + ", cityId=" + cityId + ", name="
				+ name + "]";
	}
}

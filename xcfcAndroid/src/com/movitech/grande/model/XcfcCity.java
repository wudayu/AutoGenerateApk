package com.movitech.grande.model;

import java.io.Serializable;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 18, 2014 8:22:47 PM
 * @Description: This is David Wu's property.
 *
 **/
public class XcfcCity implements Serializable {

	private static final long serialVersionUID = -2324347434268675482L;

	private String id;
	private String name;
	private String provinceId;
	private String areaCode;

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
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Override
	public String toString() {
		return "XcfcCity [id=" + id + ", name=" + name + ", provinceId="
				+ provinceId + ", areaCode=" + areaCode + "]";
	}

}

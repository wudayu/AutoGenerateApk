package com.movitech.grande.model;

import java.io.Serializable;

public class CompanyModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6378879856157611349L;

	private int iconResid;
	
	private String cmpName;
	
	private String[] childrenCity;

	public int getIconResid() {
		return iconResid;
	}

	public void setIconResid(int iconResid) {
		this.iconResid = iconResid;
	}

	public String getCmpName() {
		return cmpName;
	}

	public void setCmpName(String cmpName) {
		this.cmpName = cmpName;
	}

	public String[] getChildrenCity() {
		return childrenCity;
	}

	public void setChildrenCity(String[] childrenCity) {
		this.childrenCity = childrenCity;
	}
	
	

}

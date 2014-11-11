package com.movitech.grande.model;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月17日 上午11:15:37 
 * 类说明
 */
public class XcfcEarnIntegral {
	private String integral;
	private String integrals;
	private String isMark;//0是没有签到，大于0为已签到

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getIntegrals() {
		return integrals;
	}

	public void setIntegrals(String integrals) {
		this.integrals = integrals;
	}

	public String getIsMark() {
		return isMark;
	}

	public void setIsMark(String isMark) {
		this.isMark = isMark;
	}

	@Override
	public String toString() {
		return "XcfcEarnIntegral [integral=" + integral + ", integrals="
				+ integrals + ", isMark=" + isMark + "]";
	}
}

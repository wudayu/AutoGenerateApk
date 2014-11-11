package com.movitech.grande.generic.interfaces;

public interface INetwork {
	public boolean isNetworkAvailable();

	public boolean isWifiAvailable();

	public boolean isMobileDataAvailable();

	public String getConnectionTypeName();

	public String getMacAddress();

	public String getLocalIpAddress();

	public void downloadFileFromURL(String url, String path);

	public boolean is2GNetWork();

}

package com.movitech.grande.generic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.movitech.grande.generic.interfaces.INetwork;

/**
 * @description 网络工具类库
 */
@EBean
public class NetworkUtils implements INetwork {

	public static final String NET_TYPE_WIFI = "WIFI";
	public static final String NET_TYPE_MOBILE = "MOBILE";
	public static final String NET_TYPE_NO_NETWORK = "NO_NETWORK";

	@RootContext
	Context context;

	/**
	 * 判断设备当前是否有可用网络
	 * 
	 * @return
	 */
	@Override
	public boolean isNetworkAvailable() {
		final ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			return info.isConnected();// or info.isAvailable()?
		}
		return false;
	}

	/**
	 * 判断设备当前WIFI网络是否可用
	 * 
	 * @return
	 */
	@Override
	public boolean isWifiAvailable() {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		return wifiState == State.CONNECTED;
	}

	/**
	 * 判断设备当前移动数据网络是否可用(2G/3G/4G)
	 * 
	 * @return
	 */
	@Override
	public boolean isMobileDataAvailable() {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State dataState = manager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		return dataState == State.CONNECTED;
	}

	/**
	 * 是否为2G网络 比较慢的那种
	 * 
	 * @param context
	 * @return
	 */
	@Override
	public boolean is2GNetWork() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo net = cm.getActiveNetworkInfo();
			if (null == net) {
				return true;
			}
			if (net.getState() == NetworkInfo.State.CONNECTED) {
				int type = net.getType();
				int subtype = net.getSubtype();
				return !isHighSpeedConnection(type, subtype);
			}
		}
		return false;
	}

	/**
	 * 网络连接是否为快速的 <br>
	 * 网络连接类型判断
	 * 
	 * @param type
	 * @param subType
	 * @return
	 */
	private boolean isHighSpeedConnection(int type, int subType) {
		if (type == ConnectivityManager.TYPE_WIFI) {
			return true;
		} else if (type == ConnectivityManager.TYPE_MOBILE) {
			switch (subType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return false; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return true; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return true; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return false; // ~ 100 kbps GSM
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return true; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return true; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return true; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return true; // ~ 400-7000 kbps
			case 13: // ~ TelephonyManager.NETWORK_TYPE_LTE
				return true; // ~ LTE - 4G
			case 14: // ~ TelephonyManager.NETWORK_TYPE_EHRPD
				return true; // ~ EHRPD - CDMA+
			case 15: // ~ TelephonyManager.NETWORK_TYPE_HSPAP
				return true; // ~ HSPA+
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return false;
			default:
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取设备当前网络类型
	 * 
	 * @return
	 */
	@Override
	public String getConnectionTypeName() {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return NET_TYPE_NO_NETWORK;
		} else {
			return networkInfo.getTypeName();
		}
	}

	/**
	 * 获取设备MAC地址。WIFI未启动时未必能获取到
	 * 
	 * @return
	 */
	public String getMacAddress() {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	/**
	 * 获取设备IP地址
	 * 
	 * @return
	 */

	public String getLocalIpAddress() {
		try {
			String ipv4;
			List<NetworkInterface> nilist = Collections.list(NetworkInterface
					.getNetworkInterfaces());
			for (NetworkInterface ni : nilist) {
				List<InetAddress> ialist = Collections.list(ni
						.getInetAddresses());
				for (InetAddress address : ialist) {
					if (!address.isLoopbackAddress()
							&& InetAddressUtils.isIPv4Address(ipv4 = address
									.getHostAddress())) {
						return ipv4;
					}
				}
			}
		} catch (SocketException ex) {
			Utils.debug(ex.toString());
		}

		return "";
	}

	/**
	 * 从网络下载文件。请自己开线程。
	 * 
	 * @param url
	 * @param path
	 */
	public void downloadFileFromURL(String url, String path) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept-Encoding", "gzip");
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				Header encoding = httpResponse
						.getFirstHeader("Content-Encoding");
				InputStream is = null;
				BufferedOutputStream bos = null;
				try {
					if (encoding != null
							&& encoding.getValue().contains("gzip")) {
						is = new GZIPInputStream(entity.getContent());
					} else {
						is = entity.getContent();
					}
					byte[] tmp = new byte[4096];
					int length = -1;
					File file = new File(path);
					bos = new BufferedOutputStream(new FileOutputStream(file));
					while ((length = is.read(tmp)) != -1) {
						bos.write(tmp, 0, length);
					}
				} catch (IllegalStateException e) {
					Utils.debug(e.toString());
				} catch (FileNotFoundException e) {
					Utils.debug(e.toString());
				} catch (IOException e) {
					Utils.debug(e.toString());
				} finally {
					if (bos != null) {
						bos.flush();
						bos.close();
						bos = null;
					}
					if (is != null) {
						is.close();
						is = null;
					}
				}
			}
		} catch (ClientProtocolException e) {
			Utils.debug(e.toString());
		} catch (IOException e) {
			Utils.debug(e.toString());
		}
	}
}

package com.movitech.grande;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Application;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcBrokerDetail;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.model.XcfcDistrict;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.sp.UserSP_;
import com.nostra13.universalimageloader.utils.L;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 16, 2014 4:11:02 PM
 * @Description: This is David Wu's property.
 *
 **/
@EApplication
public class MainApp extends Application {

	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@Pref
	UserSP_ userSP;

	@Override
	public void onCreate() {
		super.onCreate();
		initBaiduMap();
		initUIL();
	}

	private void initUIL() {
		imageUtils.initImageLoader();
		L.writeLogs(false);
	}

	private void initBaiduMap() {
		SDKInitializer.initialize(getApplicationContext());
	}

	private XcfcUser currUser = null;
	private XcfcBrokerDetail brokerDetail = null;
	private XcfcCity[] cities = null;
	private XcfcCity currCity = null;
	private XcfcDistrict[] districts = null;
	private XcfcDistrict currDistrict = null;
	private String deviceId = null;
	//private String guestId = null;


	public XcfcCity[] getCities() {
		return cities;
	}

	public void setCities(XcfcCity[] cities) {
		this.cities = cities;
	}

	public XcfcUser getCurrUser() {
		return currUser;
	}

	public void setCurrUser(XcfcUser currUser) {
		this.currUser = currUser;

		/*if (this.currUser == null) {
			userSP.currUserId().put("");
		} else {
			userSP.currUserId().put(currUser.getId());
			userSP.currUserCityName().put(currUser.getCity());
			userSP.currPhone().put(currUser.getMphone());
			userSP.currUserType().put(currUser.getBrokerType());
		}*/
	}

	public XcfcCity getCurrCity() {
		currCity = new XcfcCity();
		currCity.setName(Constant.CITY);
		return currCity;
	}

	public void setCurrCity(XcfcCity currCity) {
		this.currCity = currCity;
	}

	public XcfcDistrict[] getDistricts() {
		return districts;
	}

	public void setDistricts(XcfcDistrict[] districts) {
		this.districts = districts;
	}

	public XcfcDistrict getCurrDistrict() {
		return currDistrict;
	}

	public void setCurrDistrict(XcfcDistrict currDistrict) {
		this.currDistrict = currDistrict;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/*public String getGuestId() {
		return guestId;
	}

	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
*/
	public XcfcBrokerDetail getBrokerDetail() {
		return brokerDetail;
	}

	public void setBrokerDetail(XcfcBrokerDetail brokerDetail) {
		this.brokerDetail = brokerDetail;
	}
	
	
	public boolean isLogin() {
		return !TextUtils.isEmpty(userSP.currPhone().get());
	}
	
}

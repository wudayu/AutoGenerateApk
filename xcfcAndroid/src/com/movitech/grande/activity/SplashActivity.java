package com.movitech.grande.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Intent;
import android.os.Handler;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.MobileType;
import com.movitech.grande.constant.Rolling;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcGuestIdResult;
import com.movitech.grande.net.protocol.XcfcVersionResult;
import com.movitech.grande.utils.NetWorkUtil;
import com.movitech.grande.PushService_;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.GuideActivity_;
import com.movitech.grande.activity.MainActivity_;
import com.movitech.grande.sp.AppStateSP_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 19, 2014 2:17:37 PM
 * @Description: 开启应用的主加载页面
 * 
 **/
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;
	@Pref
	AppStateSP_ appStateSP;
	
	NetWorkUtil netWorkUtil = null;

	
	@AfterViews
	void afterViews() {
		netWorkUtil = new NetWorkUtil(context);
		if (!netWorkUtil.isNetworkConnected()) {
			//无网络情况下让该页面停留5s中,直接进入主页面
			new Handler().postDelayed(new Runnable() {  
	            @Override  
	            public void run() {  
	                Intent intent = new Intent(SplashActivity.this, MainActivity_.class);  
	                startActivity(intent);  
	            }  
	  
	        }, Rolling.ROLLING_BREAK);  
			return;
		}
		
		/*//为了使得每次打开都能加载改页面的欢迎图片
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				getDeviceId();
				doFromServer();
			}
		}, Rolling.SLEEP_TIME);*/
		getDeviceId();
		doFromServer();
		PushService_.intent(this).start();
	}
   
	/**
	 * 获取 Device ID
	 */
	private void getDeviceId() {
		mApp.setDeviceId(Utils.getDeviceId(this));
	}

	@Background
	void doFromServer() {
		getCitiesFromServer();
		//checkVersionFromServer();
		getGuestIdFromServer();
	}

	/**
	 * 获取 城市列表
	 * 注：目前而言，使用本地arrays来加载城市，代替网络加载
	 */
	void getCitiesFromServer() {
		// For now, we get cities from the arrays.xml file
		String[] citiesStr = getResources().getStringArray(R.array.cities_name);
		XcfcCity[] cities = new XcfcCity[citiesStr.length];
		for (int i = 0; i < citiesStr.length; ++i) {
			cities[i] = new XcfcCity();
			cities[i].setName(citiesStr[i]);
		}
		mApp.setCities(cities);
		mApp.setCurrCity(null);
	}

	
	void checkVersionFromServer() {
		XcfcVersionResult result = netHandler.postForGetVersion(MobileType.MOBILE_ANDROID);

		if (null == result || !result.getResultSuccess()) {
			return;
		}
		goBackMainThreadUpdate(result);
		// TODO use the result to judge download new version or not
		
		//String currVersion = Utils.getVersionName(this);
		//Utils.debug(Utils.TAG, "Current version is : " + result.getObjValue());
		//Utils.debug(Utils.TAG, "getVersionName is : " + currVersion);
		//Utils.debug(Utils.TAG, "needUpdate = " + (currVersion == null ? false : currVersion.compareTo(result.getObjValue()) < 0));
	}
	@UiThread
	void goBackMainThreadUpdate(XcfcVersionResult result){
		
	}
	
	void getGuestIdFromServer() {
		XcfcGuestIdResult rst = netHandler.postForGetGuestIdResult(mApp.getDeviceId());
		if (null != rst && rst.getResultSuccess() != false) {
			//保存游客的id
			XcfcUser xcfcUser = new XcfcUser();
			xcfcUser.setId(rst.getGuestId());
			mApp.setCurrUser(xcfcUser);
			//mApp.setGuestId(rst.getGuestId());
		} else {
			Utils.toastMessageForce(this, getString(R.string.error_network_connection_not_well));			
			//this.closeAllActivity();
			return;
		}
		goToActivity();
	}

	@UiThread
	void goToActivity() {
		if (appStateSP.isFirstStartUp().getOr(true)) {
			appStateSP.isFirstStartUp().put(false);
			GuideActivity_.intent(this).start();
			this.finish();
		} else {
			// 判断是否是通过Push进入的
			final String pushMessage = getIntent().getStringExtra(ExtraNames.XCFC_PUSH_TYPE);
			if (null == pushMessage || "".equals(pushMessage)) {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						MainActivity_.intent(SplashActivity.this).start();
						SplashActivity.this.finish();
					}
				}, Rolling.SLEEP_TIME);
				//
			} else {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						Intent intent = new Intent(SplashActivity.this, MainActivity_.class);
						intent.putExtra(ExtraNames.XCFC_PUSH_TYPE, pushMessage);
						startActivity(intent);
						SplashActivity.this.finish();
					}
				}, Rolling.SLEEP_TIME);
				//FIXME :918 修改时为了能每次都能显示启动液
				/*Intent intent = new Intent(this, MainActivity_.class);
				intent.putExtra(ExtraNames.XCFC_PUSH_TYPE, pushMessage);
				startActivity(intent);*/
			}
		}

		//this.finish();
	}
		
}

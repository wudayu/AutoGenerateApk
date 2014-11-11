package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.widget.ImageView;
import android.widget.TextView;

import com.movitech.grande.constant.MobileType;
import com.movitech.grande.constant.ServiceTermType;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcServiceTerms;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcServiceTermsResult;
import com.movitech.grande.net.protocol.XcfcVersionResult;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月10日 下午3:29:12
 * 类说明
 */
@EActivity(R.layout.activity_service_trems)
public class ServiceTermsActivity extends BaseActivity{
    @ViewById(R.id.tv_service_content)
    TextView tvServiceContent;
    
    @ViewById(R.id.iv_back)
    ImageView ivBack;
    
    @Bean(NetHandler.class)
    NetHandler netHandler;
    
    XcfcServiceTerms serviceTerms = null;
    String version = null;
    @AfterViews
    void afterViews(){
    	//doLoadDataVerison();
    	doLoadData();
    }
    
    //service terms
    @Background 
    void doLoadData(){
    	XcfcServiceTermsResult termsResult = netHandler.postForGetServiceTerms(ServiceTermType.REGISTRATION_PROTOCOL);
    	
    	if (null == termsResult) {
    		goBackMainThread(getString(R.string.error_server_went_wrong), false);
    		return;
		}else if (termsResult.getResultSuccess() == false) {
			goBackMainThread(termsResult.getResultMsg(), false);
			return;
		}
		serviceTerms = termsResult.getServiceTerms();
		goBackMainThread("获取成功", true);
    }
    
    @UiThread
    void doBindData(){
    	tvServiceContent.setText(serviceTerms.getContent());
    }
	void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this, msg);
		if (success) {
			doBindData();
		}
	}
	
	@Background 
    void doLoadDataVerison(){
    	XcfcVersionResult versionResult = netHandler.postForGetVersion(MobileType.MOBILE_ANDROID);
    	
    	if (null == versionResult) {
    		goBackMainThreadVersion(getString(R.string.error_server_went_wrong), false);
		}if (versionResult.getResultSuccess() == false) {
			goBackMainThreadVersion(versionResult.getResultMsg(), false);
			return;
		}
		version = versionResult.getObjValue();
		goBackMainThreadVersion("获取成功", true);
    }
    
    @UiThread
    void doBindDataVersion(){
    	//tvServiceContent.setText(serviceTerms.getContent()+"\n"+version);
    }
	void goBackMainThreadVersion(String msg, boolean success) {
		Utils.toastMessage(this, msg);
		if(success) {
			doBindDataVersion();
		}
	}
	
	@Click
	void ivBack(){
		this.finish();
	}
}

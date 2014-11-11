package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcUserResult;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月14日 下午1:59:51
 * 类说明
 */
@EActivity(R.layout.activity_qq_login_first)
public class QQBindActivity extends BaseActivity{
   
	@ViewById(R.id.edt_reg_phone)
	EditText edtRegPhone;
	@ViewById(R.id.edt_reg_password)
	EditText edtRegPassword;
	@ViewById(R.id.edt_reg_password_again)
	EditText edtRegPasswordAgain;
	
	@ViewById(R.id.btn_reg_immediate)
	Button btnRegImmediate;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	
	String token;
	
	@Bean(NetHandler.class)
	INetHandler netHandler;
    boolean isLoading = false;
	@App
	MainApp mApp;
	@AfterViews
	void afterViews(){
		token = getIntent().getStringExtra("token");
	}
	
	@Background
	void doLoadQQBind(String token, String clientPhone,
			String password, String id){
		isLoading = true;
		XcfcUserResult rst = netHandler.postForGetQQBind(token, clientPhone, password, id);
		if (null == rst) {
			goBackMainQQThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainQQThread(rst.getResultMsg(), false);
			return;
		}

		XcfcUser user = rst.getUser();
		mApp.setCurrUser(user);
		goBackMainQQThread(getString(R.string.correct_login_success), true);
	}
	
	@UiThread
	void goBackMainQQThread(String resultMsg, boolean success) {
		isLoading = false;
		if (success) {// 绑定成功
			setResult(ReqCode.SIGN_IN);
			Utils.toastMessageForce(QQBindActivity.this, resultMsg);
			this.finish();
		}else{
			Utils.toastMessageForce(QQBindActivity.this, resultMsg);
		}
	}
	
	@Click
	void btnRegImmediate(){
		if ("".equals(edtRegPhone.getText().toString().trim())) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_phone_number));
			return;
		}else if ("".equals(edtRegPassword.getText().toString().trim())) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_password));
			return;
		}else if ("".equals(edtRegPasswordAgain.getText().toString().trim())) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_passwordagain));
			return;
		}else if (!edtRegPassword.getText().toString().equals(edtRegPasswordAgain.getText().toString())) {
			Utils.toastMessageForce(this, getString(R.string.hint_password_different));
			return;
		}
		String clientPhone = edtRegPhone.getText().toString().trim();
		String password = edtRegPassword.getText().toString().trim();
		if (mApp.getCurrUser()==null) {
			return;
		}
		String id = mApp.getCurrUser().getId();
		if (!isLoading) {
			doLoadQQBind(token, clientPhone, password, id);
		}	
	}
	
	@Click
	void ivBack(){
		this.finish();
	}
}

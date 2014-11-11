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
import com.movitech.grande.constant.BrokerType;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcAddSubInstResult;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月25日 下午4:19:19
 * 类说明
 */
@EActivity(R.layout.activity_sub_mechanism)
public class AddSubInstitutionActivity extends BaseActivity{
	
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.edt_user_nick)
	EditText edtUserNick;
	@ViewById(R.id.edt_reg_phone)
	EditText edtRegPhone;
	@ViewById(R.id.edt_user_name)
	EditText edtUserName;
	@ViewById(R.id.edt_reg_password)
	EditText edtRegPassword;
	@ViewById(R.id.edt_reg_password_again)
	EditText edtRegPasswordAgain;
	
	@ViewById(R.id.btn_reg_immediate)
	Button btnRegImmediate;
	
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;
	@AfterViews
	void afterViews(){
		
	}
	
	@Click
	void ivBack(){
		this.finish();
	}
	
	@Click
	void btnRegImmediate() {
		//界面友好提示
		if ("".equals(edtUserNick.getText().toString().trim())) {
			Utils.toastMessageForce(this,
					getString(R.string.hint_input_usernick));
			return;
		} else if ("".equals(edtRegPhone.getText().toString().trim())) {
			Utils.toastMessageForce(this,
					getString(R.string.hint_input_phone_number));
			return;
		} else if ("".equals(edtUserName.getText().toString().trim())) {
			Utils.toastMessageForce(this,
					getString(R.string.hint_input_username));
			return;
		} else if ("".equals(edtRegPassword.getText().toString().trim())) {
			Utils.toastMessageForce(this,
					getString(R.string.hint_input_password));
			return;
		} else if ("".equals(edtRegPasswordAgain.getText().toString().trim())) {
			Utils.toastMessageForce(this,
					getString(R.string.hint_input_passwordagain));
			return;
		} else if (!edtRegPassword.getText().toString()
				.equals(edtRegPasswordAgain.getText().toString())) {
			Utils.toastMessageForce(this,
					getString(R.string.hint_password_different));
			return;
		}
		
		String userNick = edtUserNick.getText().toString().trim();
		String orgAccount = edtRegPhone.getText().toString().trim();
		String userName = edtUserName.getText().toString().trim();
		String passWord = edtRegPassword.getText().toString().trim();
		String superiorId = mApp.getCurrUser().getId();
		doAddSubInstitution(orgAccount, superiorId, userNick, userName,
				passWord, BrokerType.SUB_ORG_ANIZATION);
	}
	
	@Background
	void doAddSubInstitution(String phone,  String superiorId,  String screenName, String userName, String password, String brokerType){
		
		XcfcAddSubInstResult instResult = netHandler.postForAddSubInst(phone, superiorId, screenName, userName, password, brokerType);
		
		if (null == instResult) {
			goBackMainThread(getString(R.string.add_suborg_error), false);
			return;
		}
		if (instResult.getResultSuccess() == false) {
			goBackMainThread(getString(R.string.add_suborg_error), false);
			return;
		}
		goBackMainThread(getString(R.string.add_suborg_success), true);
	}
	
	@UiThread
	void goBackMainThread(String msg, boolean success) {
		Utils.toastMessageForce(this, msg);
		if (success) {
			this.finish();
		}
	}

}

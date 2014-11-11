package com.movitech.grande.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.BrokerType;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.constant.VerificationCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.net.protocol.XcfcGuestIdResult;
import com.movitech.grande.net.protocol.XcfcVerificationCodeResult;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.ServiceTermsActivity_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 16, 2014 14:55:20 AM
 * @Description: This is David Wu's property.
 * 
 **/
@EActivity(R.layout.activity_reg)
public class RegActivity extends BaseActivity {

	@ViewById(R.id.tv_reg_note)
	TextView tvRegNote;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_reg_password)
	ImageView ivRegPassword;
	@ViewById(R.id.btn_reg_immediate)
	Button btnRegImmediate;
	@ViewById(R.id.txt_send_auth_code)
	TextView txtSendAuthCode;
	@ViewById(R.id.edt_reg_phone)
	EditText edtRegPhone;
	@ViewById(R.id.edt_user_nick)
	EditText edtUserNick;
	@ViewById(R.id.iv_user_nick)
	ImageView ivUserNick;
	@ViewById(R.id.edt_reg_password)
	EditText edtRegPassword;
	@ViewById(R.id.edt_reg_password_again)
	EditText edtRegPasswordAgain;
	@ViewById(R.id.iv_reg_password_again)
	ImageView ivRegPasswordAgain;
	@ViewById(R.id.iv_reg_auth_code)
	ImageView ivRegAuthCode;
	@ViewById(R.id.ll_send_auth_code)
	LinearLayout llSendAuthCode;
	@ViewById(R.id.edt_reg_auth_code)
	EditText edtRegAuthCode;
	@ViewById(R.id.iv_cancel_remark)
	ImageView ivCancelRemark;
	@ViewById(R.id.iv_reg_phone)
	ImageView ivRegPhone;
	@ViewById(R.id.ll_sheet)
	LinearLayout llSheet;
	
	@ViewById(R.id.rl_reg_city)
	RelativeLayout rlRegCity;
	
	@ViewById(R.id.edt_reg_location)
	TextView edtRegLocation;
	
	@ViewById(R.id.rl_reg_build)
	RelativeLayout rlRegBuild;
	
	@ViewById(R.id.edt_reg_build)
	TextView edtRegBuild;

	@ViewById(R.id.cb_check)
	CheckBox cbCheck;
	@ViewById(R.id.tv_agreen)
	TextView tvAgreen;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;

	Timer timer = null;

	private static final int SECOND = 60;

	int count = SECOND;
	
	String intentCity = null;
	String intentCityId = null;
	String buildName = null;
    String buildId = null;
	@AfterViews
	void afterViews() {
		//温馨提示部分
		//tvRegNote.setText(getString(R.string.hint_reg_note_left) + mApp.getCurrCity().getName() + getString(R.string.hint_reg_note_right));
		//服务条框部分
		initTxtAgreen();
	}

	private void initTxtAgreen() {
		String serviceTerm = context.getString(R.string.service_terms);
		SpannableString spannableString = new SpannableString(serviceTerm);
		spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFC672D")), 4, serviceTerm.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tvAgreen.setText(spannableString);
	}

	@Click
	void ivBack() {
		this.finish();
	}
	
	@Click
	void rlRegCity() {
		Intent intent = new Intent(RegActivity.this, ChooseTwoCityActivity_.class);
		startActivityForResult(intent, ReqCode.CHOOSE_CITY_TWO);
	}
	
	@Click
	void rlRegBuild() {
		if (intentCity == null) {
			Utils.toastMessageForce(RegActivity.this, "请先选择城市");
			return;
		}
		Intent intent = new Intent(RegActivity.this, ChooseBuildActivity_.class);
		intent.putExtra(ExtraNames.CHOOSE_BUILD, intentCityId);
		startActivityForResult(intent, ReqCode.CHOOSE_BUILD_CODE);
	}
	
	long recordTime = -1;
	@Click
	void llSendAuthCode() {
		if ("".equals(edtRegPhone.getText().toString())) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_phone_number));
			return;
		}
		if (recordTime > 0 && (System.currentTimeMillis() - recordTime) < SECOND) {
			Utils.toastMessageForce(this, getString(R.string.hint_send_code_again));
			return;
		}
		recordTime = System.currentTimeMillis();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				changeNumber();
			}
		}, 1000, 1000);

		doSendMessage();
	}

	@Background
	void doSendMessage() {
		BaseResult baseResult = netHandler.postForGetVerificationCode(edtRegPhone.getText().toString().trim());
		if (null == baseResult) {
			goBackMainThreadCode("", false);
			return;
		}
		if (baseResult.getResultSuccess() == false) {
			goBackMainThreadCode("", false);
			return;
		}
		goBackMainThreadCode("", true);
	}
	
	@UiThread
	void goBackMainThreadCode(String msg, boolean success){
		if (success) {
			Utils.toastMessageForce(RegActivity.this, getResources().getString(R.string.txt_send_code_ing));
		}else {
			Utils.toastMessageForce(RegActivity.this, getResources().getString(R.string.txt_send_code_error));
		}
	}

	@UiThread
	void changeNumber() {
		if (count < 0) {
			txtSendAuthCode.setText(R.string.txt_reg_auth_code_send);
			txtSendAuthCode.setTextColor(getResources().getColor(R.color.letter_grey_deep_full));
			llSendAuthCode.setClickable(true);

			if (timer != null)
				timer.cancel();
			count = SECOND;
		} else {
			txtSendAuthCode.setText(count-- + getString(R.string.txt_reg_auth_code_wait));
			txtSendAuthCode.setTextColor(getResources().getColor(R.color.letter_grey_deep_10));
			llSendAuthCode.setClickable(false);
		}
	}

	void startRegButton() {
		btnRegImmediate.setClickable(false);
	}

	@UiThread
	void endRegButton() {
		btnRegImmediate.setClickable(true);
	}

	@Click
	void tvAgreen() {
		startActivity(new Intent(RegActivity.this, ServiceTermsActivity_.class));
	}
	@Click
	void btnRegImmediate() {
		if (intentCity == null) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_reg_city));
			return;
		}
		
		if (buildName == null) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_reg_build));
			return;
		}
		//电话为空
		if ("".equals(edtRegPhone.getText().toString().trim())) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_phone_number));
			return;
		}else if ("".equals(edtRegAuthCode.getText().toString().trim())) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_authcode));
			return;
		}else if ("".equals(edtUserNick.getText().toString().trim())) {
			Utils.toastMessageForce(this,getString(R.string.hint_input_usernick));
			return;
		}else if ("".equals(edtRegPassword.getText().toString().trim()) ) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_password));
			return;
		}else if ("".equals(edtRegPasswordAgain.getText().toString().trim())) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_passwordagain));
			return;
		}else if (!edtRegPassword.getText().toString().equals(edtRegPasswordAgain.getText().toString())) {
			Utils.toastMessageForce(this, getString(R.string.hint_password_different));
			return;
		}
        if (!cbCheck.isChecked()) {
        	Utils.toastMessageForce(this, getString(R.string.please_agreen_service));
        	return;
		}
		startRegButton();
		checkTheCode();
	}

	@Background
	void checkTheCode() {
		XcfcVerificationCodeResult result = netHandler.postForCheckVerificationCode(edtRegPhone.getText().toString(), edtRegAuthCode.getText().toString());

		if (null == result) {
			doToast(getString(R.string.error_server_went_wrong));
			return;
		}

		if (result.getResultSuccess() == false) {
			doToast(result.getResultMsg());
			return;
		}

		if (result.getObjValue() == VerificationCode.WRONG) {
			doToast(result.getResultMsg());
			return;
		}

		doReg();
	}

	void doToast(String msg) {
		Utils.toastMessageForce(this, msg);
		endRegButton();
	}
    
//	void doReg() {
//
//		//接口需要传一级城市公司的城市名（ps:合肥公司，上传合肥）
//		BaseResult rst = netHandler.postForPostRegBroker(mApp.getCurrUser().getId(), edtUserNick.getText().toString(), edtRegPhone.getText().toString(), "", "", mApp.getDeviceId(), BrokerType.NORMAL, edtRegPassword.getText().toString(), mApp.getCurrCity().getName(), buildId);
//
//		if (null == rst) {
//			goBackMainThread(getString(R.string.error_server_went_wrong), false);
//			return;
//		}
//		if (rst.getResultSuccess() == false) {
//			goBackMainThread(rst.getResultMsg(), false);
//			return;
//		}
//
//		goBackMainThread(rst.getResultMsg(), true);
//	
//	}
	void doReg() {
		//接口需要传一级城市公司的城市名（ps:合肥公司，上传合肥）
		BaseResult rst = netHandler.postForRegBroker(mApp.getCurrUser().getId(), edtUserNick.getText().toString(), edtRegPhone.getText().toString(), "", "", mApp.getDeviceId(), BrokerType.NORMAL, edtRegPassword.getText().toString(), mApp.getCurrCity().getName(), buildId);

		if (null == rst) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainThread(rst.getResultMsg(), false);
			return;
		}

		goBackMainThread(rst.getResultMsg(), true);
	}

	@UiThread
	void goBackMainThread(String msg, boolean success) {
		Utils.toastMessageForce(this, msg);

		endRegButton();

		if (success) {
			//getGuestIdFromServer();
			finish();
		}
	}

	@Background
	void getGuestIdFromServer() {
		XcfcGuestIdResult rst = netHandler.postForGetGuestIdResult(mApp.getDeviceId());
		if (null != rst && rst.getResultSuccess() != false) {
			//mApp.setGuestId(rst.getGuestId());
		} else {
			Utils.toastMessageForce(this, getString(R.string.error_network_connection_not_well));
		}
	}
	
	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data)  {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ReqCode.CHOOSE_CITY_TWO) {
			intentCity = data.getStringExtra(ExtraNames.CHOOSE_TWO);
			intentCityId = data.getStringExtra(ExtraNames.CHOOSE_TWO_ID);
			edtRegLocation.setText(intentCity);
			edtRegBuild.setText("");
		} 
		if (resultCode == ReqCode.CHOOSE_BUILD_CODE) {
			buildId = data.getStringExtra(ExtraNames.CHOOSE_BUILD_ID);
			buildName = data.getStringExtra(ExtraNames.CHOOSE_BUILD_INTENT);
			edtRegBuild.setText(buildName);
		}
	}
}
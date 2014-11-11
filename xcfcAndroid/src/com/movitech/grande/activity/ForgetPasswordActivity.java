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

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.VerificationCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.net.protocol.XcfcForgetPassWordResult;
import com.movitech.grande.net.protocol.XcfcVerificationCodeResult;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月3日 下午1:01:50 类说明
 */
@EActivity(R.layout.activity_forget_password)
public class ForgetPasswordActivity extends BaseActivity {

	@ViewById(R.id.rl_first_step)
	RelativeLayout rlFirstStep;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.edt_forget_phone)
	EditText edtForgetPhone;
	@ViewById(R.id.edt_forget_code)
	EditText edtForgetCode;
	@ViewById(R.id.ll_send_auth_code)
	LinearLayout llSendAuthCode;
	@ViewById(R.id.btn_next_step)
	Button btnNextStep;
	@ViewById(R.id.txt_send_auth_code)
	TextView txtSendAuthCode;

	@ViewById(R.id.rl_complete)
	RelativeLayout rlComplete;
	@ViewById(R.id.edt_forget_new_password)
	EditText edtForgetNewPassword;
	@ViewById(R.id.edt_new_password_again)
	EditText edtNewPasswordAgain;
	@ViewById(R.id.btn_password_complete)
	Button btnPasswordComplete;

	Timer timer = null;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;

	private static final int SECOND = 60;

	int count = SECOND;
	String userName = null;
	String newPassWord = null;

	@AfterViews
	void afterViews() {
	}

	@Click
	void ivBack() {
		this.finish();
	}
	
	long recordTime = -1;
	@Click
	void llSendAuthCode() {
		if ("".equals(edtForgetPhone.getText().toString())) {
			Utils.toastMessageForce(this,getString(R.string.hint_input_phone_number));
			return;
		}
		if (recordTime >0 && (System.currentTimeMillis() - recordTime) < SECOND) {
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
		BaseResult baseResult =	netHandler.postForGetVerificationCode(edtForgetPhone.getText().toString().trim());
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
			Utils.toastMessageForce(ForgetPasswordActivity.this, getResources().getString(R.string.txt_send_code_ing));
		}else {
			Utils.toastMessageForce(ForgetPasswordActivity.this, getResources().getString(R.string.txt_send_code_error));
		}
	}
	@UiThread
	void changeNumber() {
		if (count < 0) {
			txtSendAuthCode.setText(R.string.txt_reg_auth_code_send);
			txtSendAuthCode.setTextColor(getResources().getColor(
					R.color.letter_grey_deep_full));
			llSendAuthCode.setClickable(true);

			if (timer != null)
				timer.cancel();
			count = SECOND;
		} else {
			txtSendAuthCode.setText(count--
					+ getString(R.string.txt_reg_auth_code_wait));
			txtSendAuthCode.setTextColor(getResources().getColor(
					R.color.letter_grey_deep_10));
			llSendAuthCode.setClickable(false);
		}
	}

	@Click
	void btnNextStep() {
		userName = edtForgetPhone.getText().toString();
		if (userName.equals("")) {
			Utils.toastMessageForce(ForgetPasswordActivity.this,
					getString(R.string.toast_phone_error));
			return;
		} else if (edtForgetCode.getText().toString().equals("")) {
			Utils.toastMessageForce(ForgetPasswordActivity.this,
					getString(R.string.toast_auth_code_error));
			return;
		}

		checkTheCode();
		btnNextStep.setClickable(false);
	}

	@Background
	void checkTheCode() {
		XcfcVerificationCodeResult result = netHandler
				.postForCheckVerificationCode(edtForgetPhone.getText()
						.toString(), edtForgetCode.getText().toString());
		btnNextStep.setClickable(true);
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
		doDataBind();

	}

	void doToast(String msg) {
		Utils.toastMessageForce(this, msg);
	}

	@UiThread
	void doDataBind() {
		rlFirstStep.setVisibility(View.GONE);
		rlComplete.setVisibility(View.VISIBLE);
	}

	@Click
	void btnPasswordComplete() {
		if ("".equals(edtForgetNewPassword.getText().toString())) {
			doToast(getString(R.string.edt_input_password));
			return;
		} else if ("".equals(edtNewPasswordAgain.getText().toString())) {
			doToast(getString(R.string.edt_input_password_again));
			return;
		} else if (!edtForgetNewPassword.getText().toString()
				.equals(edtNewPasswordAgain.getText().toString())) {
			doToast(getString(R.string.edt_check_password_error));
			return;
		}
		newPassWord = edtForgetNewPassword.getText().toString();
		doSendNewPassWord();
		// this.finish();
	}

	@Background
	void doSendNewPassWord() {
		XcfcForgetPassWordResult passWordResult = netHandler
				.postForgetNewPassWord(userName, newPassWord);
		if (null == passWordResult) {
			doToast(getString(R.string.error_server_went_wrong));
			return;
		}

		if (passWordResult.getResultSuccess() == false) {
			doToast(passWordResult.getResultMsg());
			return;
		}
		complete();
	}

	@UiThread
	void complete() {
		doToast(getString(R.string.edt_fix_newpassword_succeed));
		this.finish();
	}
}

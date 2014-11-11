package com.movitech.grande.activity;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.movitech.grande.MainApp;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.sp.UserSP_;

/**
 * 
 * @author: Tao Yangjun
 * @En_Name: Potter Tao
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 19, 2014 13:48:10 PM
 * @Description: This is Potter Tao's property.
 * 
 **/
@EActivity(R.layout.activity_repassward)
public class RePasswardActivity extends BaseActivity {

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.edt_repwd_now)
	EditText edtRepwdNow;
	@ViewById(R.id.edt_repwd_new)
	EditText edtRepwdNew;
	@ViewById(R.id.edt_repwd_new_again)
	EditText edtRepwdNewAgain;
	@ViewById(R.id.btn_repwd)
	Button btnRepwd;

	@Bean(NetHandler.class)
	INetHandler netHandler;

	@App
	MainApp mApp;
	
	@Pref
	UserSP_ userSP;
	
	// 返回按钮点击事件
	@Click
	void ivBack() {
		this.finish();
	}

	@Click
	void btnRepwd() {
		// 修改密码
		if (!"".equals(userSP.currPhone().get()))
	    {
	      XcfcUser user = new XcfcUser();
	      user.setId(userSP.currUserId().get());
	      user.setMphone(userSP.currPhone().get());
	      mApp.setCurrUser(user);
	    }
		String username = mApp.getCurrUser().getMphone();
		String oldPassword = edtRepwdNow.getText().toString();
		String newPassword = edtRepwdNew.getText().toString();
		String newPasswordAgain = edtRepwdNewAgain.getText().toString();

		if ("".equals(oldPassword) || "".equals(newPassword)
				|| "".equals(newPasswordAgain)
				|| !newPassword.equals(newPasswordAgain)) {
			Utils.toastMessageForce(this, getString(R.string.hint_password_different));
			return;
		}
		access(username, oldPassword, newPassword);
	}

	@Background
	void access(String username, String oldpassword, String newpassword) {
		BaseResult rst = netHandler.postForRePasswordResult(username, oldpassword, newpassword);
		if (null == rst) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainThread(rst.getResultMsg(), false);
			return;
		}

		// 修改成功
		goBackMainThread("修改成功", true);
	}

	@UiThread
	void goBackMainThread(String msg, boolean success) {
		Utils.toastMessageForce(this, msg);
		if (success) {
			this.finish();
		}
	}
}

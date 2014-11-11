package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.BackgroundExecutor;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.QQKey;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcUserResult;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.ForgetPasswordActivity_;
import com.movitech.grande.activity.QQBindActivity_;
import com.movitech.grande.activity.RegActivity_;
import com.movitech.grande.sp.LoginSP_;
import com.movitech.grande.sp.UserSP_;
import com.movitech.grande.views.LoginExitDialog;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * @author Warkey.Song
 *
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

	public static final String TAG = "LoginActivity";
	public static QQAuth mQQAuth;
	public static String mAppid;
    private String token;
	private Tencent mTencent;

	@ViewById(R.id.iv_login_more)
	ImageView ivLoginMore;
	@ViewById(R.id.rl_login_register)
	RelativeLayout rlLoginRegister;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.rl_bottom)
	RelativeLayout rlBottom;
	@ViewById(R.id.iv_user_image)
	ImageView ivUserImage;
	@ViewById(R.id.rl_login_account)
	RelativeLayout rlLoginAccount;
	@ViewById(R.id.rl_login_qq)
	RelativeLayout rlLoginQq;
	@ViewById(R.id.rl_login_password)
	RelativeLayout rlLoginPassword;
	@ViewById(R.id.rl_login_btn)
	RelativeLayout rlLoginBtn;
	@ViewById(R.id.edt_login_password)
	EditText edtLoginPassword;
	@ViewById(R.id.tv_login_qq)
	TextView tvLoginQq;
	@ViewById(R.id.rl_login_qq_btn)
	RelativeLayout rlLoginQqBtn;
	@ViewById(R.id.edt_login_username)
	EditText edtLoginUsername;
	@ViewById(R.id.iv_login_password_forget)
	ImageView ivLoginPasswordForget;
	@ViewById(R.id.rl_login_password_forget)
	RelativeLayout rlLoginPasswordForget;
	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_login_password)
	ImageView ivLoginPassword;
	@ViewById(R.id.rl_user_image)
	RelativeLayout rlUserImage;
	@ViewById(R.id.iv_login_qq)
	ImageView ivLoginQq;
	@ViewById(R.id.iv_login_username)
	ImageView ivLoginUsername;

	@Bean(NetHandler.class)
	INetHandler netHandler;

	@App
	MainApp mApp;
	@Pref
	UserSP_ userSP;
	@Pref
	LoginSP_ loginSP;
	
	LoginExitDialog loginProgressingDialog = null;
	
	@AfterViews
	void afterViews() {
		//mAppid = "222222";
		mAppid = QQKey.QQ_ID;
		mQQAuth = QQAuth.createInstance(mAppid, context);
		mTencent = Tencent.createInstance(mAppid, LoginActivity.this);

		ivUserImage.setBackgroundResource(R.drawable.default_avatar);
	}

	@Click
	void rlLoginBtn() {
		String username = edtLoginUsername.getText().toString();
		String password = edtLoginPassword.getText().toString();

		if ("".equals(username)) {
			Utils.toastMessageForce(this, getString(R.string.hint_edt_login_account));
			return;
		}
		if ("".equals(password)) {
			Utils.toastMessageForce(this, getString(R.string.hint_input_password));
			return;
		}

		showProcessDialog();
		access(username, password);
	}

	@Click
	void ivBack() {
		finish();
	}

	@Background(id = "task")
	void access(String username, String password) {
		XcfcUserResult rst = netHandler.postForUserLoginResult(username, password);
		if (null == rst) {
			goBackMainThread(getString(R.string.error_network_connection_not_well), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainThread(rst.getResultMsg(), false);
			return;
		}

		XcfcUser user = rst.getUser();
        
		XcfcCity city = new XcfcCity();
		city.setName(user.getCity());
		mApp.setCurrCity(city);
		mApp.setCurrUser(user);
		
		//保存用户信息到本地
		userSP.currUserId().put(user.getId());
		userSP.currPhone().put(user.getMphone());
		userSP.currUserType().put(user.getBrokerType());
		if (null != mApp.getCurrUser().getApproveState()) {
			userSP.currUserApproveState().put(user.getApproveState());
		}if (null == mApp.getCurrUser().getApproveState()) {//为null时表示未认证
			userSP.currUserApproveState().put("");
		}
		goBackMainThread(getString(R.string.correct_login_success), true);
	}

	@UiThread
	void goBackMainThread(String msg, boolean success) {
		dismissProcessingDialog();
		Utils.toastMessageForce(this, msg);
		if (success) {
			setResult(ReqCode.SIGN_IN);
			this.finish();

		}
	}

	@Click
	void rlLoginPasswordForget() {
		ForgetPasswordActivity_.intent(this).start();
	}
	@Click 
	void rlLoginRegister() {
		RegActivity_.intent(this).start();
	}

	// FIXME the codes below are test codes, please deletes these later
	@Click
	void rlLoginQq() {
		if (!mQQAuth.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
					try {
						token = values.getString("access_token");
						doLoadIsBindQQ(token);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					/*Toast.makeText(LoginActivity.this, values.toString(),
							Toast.LENGTH_SHORT).show();*/
				}
			};
			mTencent.login(this, "all", listener);
		} else {
			mQQAuth.logout(this);
		}
	}

	@Background
	void doLoadIsBindQQ(String token) {
		XcfcUserResult rst = netHandler.postForCheckQQBind(token);
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
		if (!success) {
			Intent intent = new Intent(LoginActivity.this, QQBindActivity_.class);
			intent.putExtra("token", token);
			startActivityForResult(intent, ReqCode.SIGN_IN);
			//Utils.toastMessageForce(LoginActivity.this, "未绑定");
			this.finish();
		}else{//绑定成功
			setResult(ReqCode.SIGN_IN);
			//Utils.toastMessageForce(LoginActivity.this, "已绑定");
			this.finish();
		}
	}
	private class BaseUiListener implements IUiListener {
		@Override
		public void onComplete(Object response) {
			/*Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
					.show();*/
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {
		}

		@Override
		public void onError(UiError e) {
			Log.e(TAG, "Tencent OAuth onError:" + "code:" + e.errorCode
					+ ", msg:" + e.errorMessage + ", detail:" + e.errorDetail);
		}

		@Override
		public void onCancel() {
			Log.e(TAG, "Tencent OAuth onCancel");
		}
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ReqCode.SIGN_IN) {
			setResult(ReqCode.SIGN_IN);
			this.finish();
		}
	}
	
	
	/**
	 * 显示进度条
	 */
	private void showProcessDialog() {
		loginProgressingDialog = new LoginExitDialog(context, true,
				new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
                       BackgroundExecutor.cancelAll("task", true);
					}
				});
		loginProgressingDialog.setLoadTxt("登录中");
		loginProgressingDialog.show();
	}
	
	/**
	 * 关闭进度条
	 */
	private void dismissProcessingDialog() {
		if (loginProgressingDialog != null)
			loginProgressingDialog.dismiss();
	}
}

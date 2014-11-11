package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.haerbin.R;

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
@EActivity(R.layout.activity_rename)
public class ReNameActivity extends BaseActivity {

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.edt_nick_name)
	EditText edtNickName;
	@ViewById(R.id.btn_nick_complete)
	Button btnNickComplete;
	
	@Bean(NetHandler.class)
	INetHandler netHandler;

	@App
	MainApp mApp;
	
	String name = null;

	@AfterViews
	void afterViews(){
		String nickName = getIntent().getStringExtra(ExtraNames.JUMP_TO_RENICK);
		edtNickName.setText(nickName);
	}
	// 返回按钮点击事件
	@Click
	void ivBack() {
		this.finish();
	}

	@Click
	void btnNickComplete() {
		// 修改姓名
//		XcfcUser user = mApp.getCurrUser();
//		String userid = user.getId();
		name = edtNickName.getText().toString();

		if ("".equals(name)) {
			Utils.toastMessageForce(this, getResources().getString(R.string.toast_nick_name_is_null));
			return;
		}

		access(mApp.getCurrUser().getId(), name);
	}

	@Background
	void access(String id, String name) {
		BaseResult rst = netHandler.postForReNameResult(id, name);
		if (null == rst) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainThread(rst.getResultMsg(), false);
			return;
		}

		// 修改成功
		goBackMainThread(rst.getResultMsg(), true);
	}

	@UiThread
	void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this, msg);
		if (success) {
			XcfcUser currUser = new XcfcUser();
			currUser.setId(mApp.getCurrUser().getId());
			currUser.setMphone(mApp.getCurrUser().getMphone());
			currUser.setPhotosrc(mApp.getCurrUser().getPhotosrc());
			currUser.setScreenName(name);
			currUser.setName(mApp.getCurrUser().getName());
			currUser.setApproveState(mApp.getCurrUser().getApproveState());
			mApp.setCurrUser(currUser);
			Intent intent = new Intent();
			intent.putExtra(ExtraNames.NICK_NAME, name);
		    setResult(ReqCode.RE_NICK_NAME, intent);
			this.finish();
		}
	}
}

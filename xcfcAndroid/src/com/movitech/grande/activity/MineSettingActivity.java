package com.movitech.grande.activity;

import java.io.File;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.ApproveState;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ImageLoaderHelper;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.model.XcfcBrokerDetail;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcGuestIdResult;
import com.movitech.grande.net.protocol.XcfcImageReturnResult;
import com.movitech.grande.sp.UserSP_;
import com.movitech.grande.views.LoginExitDialog;
import com.movitech.grande.views.SelectPicPopupWindow;

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

@EActivity(R.layout.activity_mine_setting)
public class MineSettingActivity extends BaseActivity {

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.rl_personal_photo)
	RelativeLayout rlPersonalPhoto;
	@ViewById(R.id.rl_my_setting_name)
	RelativeLayout rlMySettingName;
	@ViewById(R.id.rl_my_setting_card)
	RelativeLayout mySettingCard;
	@ViewById(R.id.rl_my_setting_password)
	RelativeLayout rlMySettingPassword;
	@ViewById(R.id.rl_my_setting_certification)
	RelativeLayout rlMySettingCertification;
	@ViewById(R.id.btn_login_exit)
	Button btnLoginExit;
	@ViewById(R.id.tv_nickname)
	TextView tvNickname;
	@ViewById(R.id.txt_my_name)
	TextView txtMyName;
	@ViewById(R.id.tv_real_name_certi)
	TextView tvRealNameCerti;
    @ViewById(R.id.iv_user_image)
    ImageView ivUserImage;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	Bitmap avaterBitmap = null;
	@App
	MainApp mApp;
	@Pref
	UserSP_ userSP;

	// 自定义的弹出框类
	SelectPicPopupWindow menuWindow;
	// 用来标识请求照相功能的activity
	public static final int CAMERA_WITH_DATA = 3023;
	// 去上传文件
	protected static final int TO_UPLOAD_FILE = 1;
	// 上传文件响应
	protected static final int UPLOAD_FILE_DONE = 2;
	public static final int UPLOAD_FILE_FAIL = 3;
	
	XcfcBrokerDetail brokerDetail;
	
	LoginExitDialog exitProgressingDialog = null;
	LoginExitDialog uploadProgressingDialog = null;
	
	@AfterViews
	void afterViews() {
		if (!mApp.isLogin() || mApp.getCurrUser() == null){
			return;
		}

	    brokerDetail = mApp.getBrokerDetail();
	    if (brokerDetail == null) {
	    	return;
		}
		// 头像图片部分
		// TODO 这里从服务器获取头像目前一直是null或者""，所以头像显示不出来，暂时使用本地头像
		if (null != brokerDetail.getPhotosrc() && !"".equals(brokerDetail.getPhotosrc()))
			imageUtils.loadHeaderImage(brokerDetail.getPhotosrc(), ivUserImage);
		else
			imageUtils.loadHeaderImage(ImageLoaderHelper.URI_PREFIX_FILE + userSP.latestUserHeaderSrc().get(), ivUserImage);

		tvNickname.setText(brokerDetail.getScreenName());
		txtMyName.setText(ApproveState.PASSED.equals(brokerDetail.getApproveState()) ? brokerDetail.getName() : brokerDetail.getMphone());

		if (ApproveState.UNCERTIFICATE == brokerDetail.getApproveState()) {
			tvRealNameCerti.setText(getString(R.string.hint_approve_null));
		} else if (ApproveState.PASSED.equals(brokerDetail.getApproveState())) {
			tvRealNameCerti
				.setText(getString(R.string.hint_approve_passed));
		} else if (ApproveState.WAITFOR.equals(brokerDetail.getApproveState())) {
			tvRealNameCerti
				.setText(getString(R.string.hint_approve_waitfor));
		} else if (ApproveState.UNPASS.equals(brokerDetail.getApproveState())) {
			tvRealNameCerti
				.setText(getString(R.string.hint_approve_unpass));
		}
	}

	@Click
	void rlMySettingName() {
		Intent intent = new Intent(MineSettingActivity.this,
				ReNameActivity_.class);
		intent.putExtra(ExtraNames.JUMP_TO_RENICK, mApp.getCurrUser()
				.getScreenName());
		startActivityForResult(intent, ReqCode.RE_NICK_NAME);
	}

	@Click
	void rlMySettingCard() {
		SettingMaxCardActivity_.intent(this).start();
	}

	@Click
	void rlMySettingPassword() {
		RePasswardActivity_.intent(this).start();
	}

	@Click
	void rlMySettingCertification() {
		if (brokerDetail == null) {
			return;
		}
		Intent intent = new Intent(MineSettingActivity.this,
				CertificationActivity_.class);
		intent.putExtra("approveState", brokerDetail.getApproveState());
		startActivityForResult(intent, ReqCode.REAL_NAME_CERTIFICATION);
		// CertificationActivity_.intent(this).start();
	}

	// 返回按钮点击事件
	@Click
	void ivBack() {
		this.finish();
	}

	@Click
	void btnLoginExit() {
		View dialogView = LayoutInflater.from(MineSettingActivity.this)
				.inflate(R.layout.dialog_for_all_setting_layer, null);
		TextView content = (TextView) dialogView
				.findViewById(R.id.dialog_content);
		Button cancle = (Button) dialogView.findViewById(R.id.dialog_cancle);
		Button btnOk = (Button) dialogView.findViewById(R.id.dialog_ok);

		content.setCompoundDrawables(null, null, null, null);
		content.setText("您确定退出当前帐号？");

		final Dialog customDialog = new Dialog(MineSettingActivity.this,
				R.style.dialog);
		customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		customDialog.setContentView(dialogView);
		customDialog.setCancelable(false);
		customDialog.show();
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				customDialog.dismiss();
				showProcessDialog();
				getGuestIdFromServer();
			}
		});
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				customDialog.dismiss();
			}
		});
	}

	XcfcGuestIdResult rst;
	@Background
	void getGuestIdFromServer() {
		rst = netHandler.postForGetGuestIdResult(mApp.getDeviceId());
		if (null != rst && rst.getResultSuccess() != false) {
			//mApp.setGuestId(rst.getGuestId());
			exitBackMainThread(true);
		} else 
			exitBackMainThread(false);
	}
	
	@UiThread
	void exitBackMainThread(boolean success) {
		dismissProcessingDialog();
		if (success) {
			//退出时保存游客的id
			XcfcUser xcfcUser = new XcfcUser();
			xcfcUser.setId(rst.getGuestId());
			mApp.setCurrUser(xcfcUser);
			//保存用户信息到本地
			userSP.currUserId().put("");
			userSP.currPhone().put("");
			userSP.currUserType().put("");
			userSP.currUserApproveState().put("");
			setResult(ReqCode.SIGN_OUT);
			finish();
		}else {
			Utils.toastMessageForce(this, getString(R.string.error_network_connection_not_well));
		}
		
	}

	String takePicturePath = null;
	String cuttedImagePath = null;
	String compressedPath = null;

	@Click
	void rlPersonalPhoto() {
		takePicturePath = imageUtils.getNewTmpImagePath();
		imageUtils.selectGetImageWay(this, ivUserImage, takePicturePath);
	}

	public void cutTheImage(Uri uri) {
		cuttedImagePath = imageUtils.getNewTmpImagePath();
		imageUtils.cutTheImageNormal(this, uri, cuttedImagePath);
	}

	private void setPicToView(String cuttedImagePath) {
		compressedPath = imageUtils.compressImage(cuttedImagePath);

		// 上传图片
		showUploadHeadDialog();
		uploadHeader(compressedPath);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (resultCode == Activity.RESULT_OK) {
				switch (requestCode) {
				// 如果是直接从相册获取
				case ReqCode.ALBUM:
					cutTheImage(data.getData());
					break;
				// 如果是调用相机拍照时
				case ReqCode.CAMERA:
					cutTheImage(Uri.fromFile(new File(takePicturePath)));
					break;
				// 取得裁剪后的图片
				case ReqCode.CUTTED:
					setPicToView(cuttedImagePath);
					break;
				default:
					break;
				}
			}
			if (resultCode == ReqCode.RE_NICK_NAME) {
				String nickName = data.getStringExtra(ExtraNames.NICK_NAME);
				tvNickname.setText(nickName);
			}
			if (resultCode == ReqCode.REAL_NAME_CERTIFICATION) {
				tvRealNameCerti.setText(getString(R.string.hint_approve_waitfor));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Background
	void uploadHeader(String filePath) {
		XcfcImageReturnResult result = netHandler.postForUploadHeader("attachFile", filePath, mApp.getCurrUser().getId());
		if (result == null || !result.getResultSuccess()) {
			finishUploadImage(getString(R.string.error_server_went_wrong), false);
			return;
		}

		finishUploadImage(result.getResultMsg(), true);
	}

	@UiThread
	void finishUploadImage(String msg, boolean success) {
		dismissUploadHeadDialog();
		Utils.toastMessageForce(this, msg);

		if (success) {
			imageUtils.loadHeaderImage(ImageLoaderHelper.URI_PREFIX_FILE + compressedPath, ivUserImage);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onDestroy() {
		if (avaterBitmap != null && !avaterBitmap.isRecycled()) {
			avaterBitmap.recycle();
			avaterBitmap = null;
		}
		super.onDestroy();
	}
	
	/**
	 * 显示进度条
	 */
	private void showProcessDialog() {
		exitProgressingDialog = new LoginExitDialog(context, true,
				new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
                       
					}
				});
		exitProgressingDialog.setLoadTxt("正在注销");
		exitProgressingDialog.show();
	}
	
	/**
	 * 关闭进度条
	 */
	private void dismissProcessingDialog() {
		if (exitProgressingDialog != null)
			exitProgressingDialog.dismiss();
		exitProgressingDialog = null;
	}

	/**
	 * 显示进度条（上传头像）
	 */
	private void showUploadHeadDialog() {
		uploadProgressingDialog = new LoginExitDialog(context, false, null);
		uploadProgressingDialog.setLoadTxt("正在上传");
		uploadProgressingDialog.show();
	}
	
	/**
	 * 关闭进度条
	 */
	private void dismissUploadHeadDialog() {
		if (uploadProgressingDialog != null)
			uploadProgressingDialog.dismiss();
		uploadProgressingDialog = null;
	}
}

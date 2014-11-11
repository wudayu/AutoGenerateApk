package com.movitech.grande.activity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.ImageLoaderHelper;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcCertificationResult;
import com.movitech.grande.net.protocol.XcfcImageReturnResult;
import com.movitech.grande.views.LoginExitDialog;
import com.movitech.grande.views.SelectPicPopupWindow;

@EActivity(R.layout.activity_certification)
public class CertificationActivity extends BaseActivity {

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.edt_real_name)
	EditText edtRealName;
	@ViewById(R.id.tv_user_birth_edit)
	TextView tvUserBirthEdit;
	@ViewById(R.id.tv_user_male)
	TextView tvUserMale;
	@ViewById(R.id.tv_user_female)
	TextView tvUserFemale;

	// @ViewById(R.id.certification_sex_edit)
	// EditText certificationSexEdit;
	// @ViewById(R.id.certification_sex_radioGroup)
	// RadioGroup certificationSexRadioGroup;
	// @ViewById(R.id.radioMale)
	// RadioButton radioMale;
	// @ViewById(R.id.radioFemale)
	// RadioButton radioFemale;
	@ViewById(R.id.edt_bank_name)
	EditText edtBankName;
	@ViewById(R.id.edt_bank_account)
	EditText edtBankAccount;
	@ViewById(R.id.edt_card_no)
	EditText edtCardNo;
	@ViewById(R.id.rl_card_front)
	RelativeLayout rlCardFront;
	@ViewById(R.id.iv_card_front)
	ImageView ivCardFront;

	@ViewById(R.id.rl_card_reverse)
	RelativeLayout rlCardReverse;
	@ViewById(R.id.iv_card_reverse)
	ImageView ivCardReverse;
	@ViewById(R.id.certification_step_commit)
	Button certificationStepCommit;
	@ViewById(R.id.ll_male_selected)
	LinearLayout llMaleSelected;
	@ViewById(R.id.ll_female_selected)
	LinearLayout llFemaleSelected;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@App
	MainApp mApp;

	// 去上传文件
	protected static final int TO_UPLOAD_FILE = 1;
	// 上传文件响应
	protected static final int UPLOAD_FILE_DONE = 2; //
	// 选择文件
	public static final int TO_SELECT_PHOTO = 3;
	// 上传初始化
	// private static final int UPLOAD_INIT_PROCESS = 4;
	// 上传中
	// private static final int UPLOAD_IN_PROCESS = 5;
	// 这里的这个URL是我服务器的javaEE环境URL
	int key = 0;// 上传图片的标记
	String idCardUpImageUname = null;
	String idCardDownImageUname = null;

	int mYear = -1;
	int mMonth = -1;
	int mDay = -1;
	int isMale = 0;
	String sex = "";
	String realName = null;
	String birthday = null;
	String bankName = null;
	String bankCard = null;
	String idCardNum = null;
	Calendar date = Calendar.getInstance(Locale.CHINA);
	String currentUserId = null;
	// 自定义的弹出框类
	SelectPicPopupWindow menuWindow;
	// 用来标识请求照相功能的activity
	private int ImageClickID = 0;

	// 返回按钮点击事件
	@Click
	void ivBack() {
		this.finish();
	}

	@Click
	void certificationStepCommit() {
		realName = edtRealName.getText().toString();
		birthday = tvUserBirthEdit.getText().toString();

		if (isMale == 0) {
			sex = getString(R.string.txt_my_setting_user_gender_male);
		} else {
			sex = getString(R.string.txt_my_setting_user_gender_female);
		}
		bankName = edtBankName.getText().toString();
		bankCard = edtBankAccount.getText().toString();
		idCardNum = edtCardNo.getText().toString();
		if ("".equals(edtRealName.getText().toString())
				|| "".equals(tvUserBirthEdit.getText().toString())
				|| "".equals(sex)
				|| "".equals(edtBankName.getText().toString())
				|| "".equals(edtBankAccount.getText().toString())
				|| "".equals(edtCardNo.getText().toString())) {
			Utils.toastMessageForce(this,
					getResources().getString(R.string.str_fill_the_form));
			return;
		}

		
		access(currentUserId, realName, birthday, sex, bankName, bankCard, idCardNum, idCardUpImageUname, idCardDownImageUname);
		Utils.toastMessage(this, "请先选择图片！");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
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
	}

	String takePicturePath = null;
	String cuttedImagePath = null;

	@Click
	void rlCardFront() {
		takePicturePath = imageUtils.getNewTmpImagePath();
		// 上传身份证正面
		ImageClickID = 1;
		imageUtils.selectGetImageWay(this, ivCardFront, takePicturePath);
	}

	@Click
	void rlCardReverse() {
		takePicturePath = imageUtils.getNewTmpImagePath();
		// 反面
		ImageClickID = 2;
		imageUtils.selectGetImageWay(this, ivCardReverse, takePicturePath);
	}

	public void cutTheImage(Uri uri) {
		cuttedImagePath = imageUtils.getNewTmpImagePath();
		imageUtils.cutTheImageNormal(this, uri, cuttedImagePath);
	}

	LoginExitDialog uploadProgressingDialog = null;

	private void setPicToView(String cuttedImagePath) {
		String compressedPath = imageUtils.compressImage(cuttedImagePath);

		if (ImageClickID == 1) {
			imageUtils.loadRoundCornerImage(ImageLoaderHelper.URI_PREFIX_FILE + compressedPath, ivCardFront);
		} else if (ImageClickID == 2) {
			imageUtils.loadRoundCornerImage(ImageLoaderHelper.URI_PREFIX_FILE + compressedPath, ivCardReverse);
		}

		// 上传图片
		showUploadHeadDialog();
		access(compressedPath);
	}

	@Background
	void access(String filePath) {
		XcfcImageReturnResult result = netHandler.postForUploadPic("attachFile", filePath);
		if (result == null || !result.getResultSuccess()) {
			finishUploadImage(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (ImageClickID == 1)
			idCardUpImageUname = result.getImage().getUname();
		else if (ImageClickID == 2)
			idCardDownImageUname = result.getImage().getUname();

		finishUploadImage(result.getResultMsg(), true);
	}

	@UiThread
	void finishUploadImage(String msg, boolean success) {
		dismissUploadHeadDialog();
		Utils.toastMessageForce(this, msg);

		if (!success) {
			if (ImageClickID == 1) {
				imageUtils.loadRoundCornerImage(ImageLoaderHelper.URI_PREFIX_DRAWABLE + R.drawable.sm_idcard_a, ivCardFront);
			} else if (ImageClickID == 2) {
				imageUtils.loadRoundCornerImage(ImageLoaderHelper.URI_PREFIX_DRAWABLE + R.drawable.sm_idcard_b, ivCardReverse);
			}
		}

	}

	@Click
	void tvUserBirthEdit() {
		new DatePickerDialog(CertificationActivity.this, d, mYear > 0 ? mYear
				: date.get(Calendar.YEAR), mMonth > 0 ? mMonth
				: date.get(Calendar.MONTH), mDay > 0 ? mDay
				: date.get(Calendar.DAY_OF_MONTH)).show();
	}

	@Click
	void llMaleSelected() {
		llMaleSelected
				.setBackgroundResource(R.drawable.shape_gender_selected_bg);
		llFemaleSelected.setBackgroundResource(android.R.color.transparent);
		tvUserMale.setTextColor(context.getResources().getColor(
				R.color.letter_grey_deep_full));
		tvUserFemale.setTextColor(context.getResources().getColor(
				R.color.letter_grey_deep_9));
		isMale = 0;
	}

	@Click
	void llFemaleSelected() {
		llFemaleSelected
				.setBackgroundResource(R.drawable.shape_gender_selected_bg);
		llMaleSelected.setBackgroundResource(android.R.color.transparent);
		tvUserFemale.setTextColor(context.getResources().getColor(
				R.color.letter_grey_deep_full));
		tvUserMale.setTextColor(context.getResources().getColor(
				R.color.letter_grey_deep_9));
		isMale = 1;
	}

	@AfterViews
	void afterViews() {
		currentUserId = mApp.getCurrUser().getId();
	}

	@Background
	void access(String id, String realName, String birthday, String sex, String bankName, String bankNum, String idCardNum, String idCardImg, String idCardNegImg) {
		Utils.debug("images = " + idCardImg + " and " + idCardNegImg);

		XcfcCertificationResult rst = netHandler.postForCertificationResult(id, realName, birthday, sex, bankName, bankNum, idCardNum, idCardImg, idCardNegImg);
		if (null == rst) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainThread(
					getResources().getString(R.string.toast_certification_fail),
					false);
			return;
		}

		goBackMainThread(
				getResources().getString(
						R.string.toast_certification_submit_already), true);
	}

	@UiThread
	void goBackMainThread(String msg, boolean success) {
		if (!success) {
			Utils.toastMessageForce(this, msg);
			return;
		}

		// 弹出
		View dialogView = LayoutInflater.from(CertificationActivity.this)
				.inflate(R.layout.dialog_for_all_setting_layer, null);
		TextView noText = (TextView) dialogView
				.findViewById(R.id.dialog_btn_tv);
		Button cancle = (Button) dialogView
				.findViewById(R.id.dialog_cancle);
		Button btnOk = (Button) dialogView.findViewById(R.id.dialog_ok);

		noText.setVisibility(View.GONE);
		cancle.setVisibility(View.GONE);
		final Dialog customDialog = new Dialog(CertificationActivity.this,
				R.style.dialog);
		customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		customDialog.setContentView(dialogView);
		customDialog.setCancelable(false);
		customDialog.show();
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 返回审核中状态。。。
				setResult(ReqCode.REAL_NAME_CERTIFICATION);
				CertificationActivity.this.finish();
			}
		});
	}

	// 当点击DatePickerDialog控件的设置按钮时，调用该方法
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	/* 更新日期显示 */
	private void updateDateDisplay() {
		tvUserBirthEdit.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay));
	}

	/**
	 * 显示进度条
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
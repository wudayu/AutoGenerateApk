package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.widget.ImageView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.RuleBannerTypeId;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcStringResult;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.haerbin.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 4, 2014 5:03:49 PM
 * @Description: This is David Wu's property.
 * 
 **/
@EActivity(R.layout.activity_score_rule)
public class ScoreRuleActivity extends BaseActivity {

	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.tmp_image)
	ImageView tmpImage;

	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@App
	MainApp mApp;
	@Bean(NetHandler.class)
	NetHandler netHandler;
	
	ImageLoader imageLoader = null;

	ProcessingDialog processingDialog = null;
	@AfterViews
	void afterViews() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				return;
			}
		});
		processingDialog.show();
		doLoadDataScorePic();
	}

	@Click
	void ivBack() {
		finish();
	}

	@Background
	void doLoadDataScorePic(){
		XcfcStringResult result = netHandler.postForGetRuleBanner(RuleBannerTypeId.INTEGRAL_BANNER);

		if (result == null || !result.getResultSuccess() || result.getObjValue() == null || "".equals(result.getObjValue())) {
			goBackMainThread(null, false);
			return;
		}
		goBackMainThread(result.getObjValue(), true);
	}

	@UiThread
	void goBackMainThread(String url, boolean success) {

		if (success)
			imageUtils.loadImage(url, tmpImage);

		dismissProcessingDialog();
	}
	
	
	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}
	
}

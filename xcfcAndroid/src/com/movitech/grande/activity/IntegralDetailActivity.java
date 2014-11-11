package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.IntegralHistoryAdapter;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcIntegral;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcIntegralResult;
import com.movitech.grande.views.AutoScaleTextView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.ScoreRuleActivity_;

/**
 * 
 * @author Warkey.Song
 * @version: 1.0
 * @Created Time: Jun 17, 2014 3:35:23 PM
 * @Description：Customer's detail Infomation
 */
@EActivity(R.layout.activity_my_integral)
public class IntegralDetailActivity extends BaseActivity {

	@ViewById(R.id.lv_customer_integral)
	ListView lvCustomerIntegral;
	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.rl_customer_info)
	RelativeLayout rlCustomerInfo;
	@ViewById(R.id.tv_my_integral)
    AutoScaleTextView tvMyIntegral;
	@ViewById(R.id.btn_integral_rule)
	Button btnIntegralRule;


	@Bean(NetHandler.class)
	INetHandler netHandler;

	XcfcIntegral[] integrals;
	IntegralHistoryAdapter integralHistoryAdapter;

	ProcessingDialog processingDialog = null;

	@App
	MainApp mApp;

	@AfterViews
	void afterViews() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				BackgroundExecutor.cancelAll("queryData", false);
				IntegralDetailActivity.this.finish();
			}
		});
		processingDialog.show();

		access(mApp.getCurrUser().getId());
	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	@Background(id="queryData")
	void access(String brokerId) {
		XcfcIntegralResult rst = netHandler.postForGetIntegralResult(brokerId);
		if (null == rst) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (rst.getResultSuccess() == false) {
			goBackMainThread(rst.getResultMsg(), false);
			return;
		}

		integrals = rst.getIntegrals();
		goBackMainThread(rst.getResultMsg(), true);
	}

	@UiThread
	void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this, msg);
		if (success) {
			integralHistoryAdapter = new IntegralHistoryAdapter(context,
					integrals);
			lvCustomerIntegral.setAdapter(integralHistoryAdapter);
			int total = 0;
			for (int i = 0; i < integrals.length; ++i) {
				if (integrals[i].getIntegral() != null) {
					total += Integer.valueOf(integrals[i].getIntegral());
				}			
			}

			tvMyIntegral.setText("" + total);
		}
		dismissProcessingDialog();
	}

	@Click
	void ivBack() {
		this.finish();
	}

	@Click
	void btnIntegralRule() {
		ScoreRuleActivity_.intent(this).start();
	}
}

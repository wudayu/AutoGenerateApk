package com.movitech.grande.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.CusRecordFragmentAdapter;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.fragment.CustomerRecordFragment;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcCustomerDetail;
import com.movitech.grande.model.XcfcMyCustomer;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcCustomerDetailResult;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.CirclePageIndicator;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.fragment.CustomerRecordFragment_;

/**
 * 
 * @author Warkey.Song
 * @version: 1.0
 * @Created Time: Jun 17, 2014 3:35:23 PM
 * @Description：Customer's detail Infomation
 */
@EActivity(R.layout.activity_customer_detail)
public class CustomerDetailActivity extends BaseActivity {

	private static String[] status = { "", "50,60", "70,100", "20,40", "10,30" };
	@ViewById(R.id.iv_back_customer)
	ImageView ivBackCustomer;// 返回button
	@ViewById(R.id.vp_fragment_customer)
	BaseViewPager vpFragmentCustomer;

	@ViewById(R.id.circle_indicator)
	CirclePageIndicator circleIndicator;

	CustomerRecordFragment customerRecordFragment = null;

	@Bean(NetHandler.class)
	INetHandler netHandler;

	@App
	MainApp mApp;

	XcfcCustomerDetail customerDetail = null;
	XcfcMyCustomer customer;
	String customerId;
	int posColor;
	int currentItem;
	
	ProcessingDialog processingDialog = null;
	/**
	 * view加载完成后执行一些操作
	 */
	@AfterViews
	void afterViews() {
		Intent intent = getIntent();
		customerId = intent.getStringExtra("customersId");
		posColor = intent.getIntExtra("customersColor", 0);
		currentItem = intent.getIntExtra("currentItem", 0);

		access(mApp.getCurrUser().getId(), customerId, status[currentItem]);
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				//BackgroundExecutor.cancelAll("queryData", false);
				//InfoDetailActivity.this.finish();
			}
		});
		processingDialog.show();
	}

	@Background
	void access(String brokerId, String clientId, String status) {
		XcfcCustomerDetailResult rst = netHandler
				.postForGetCustomerDetailResult(brokerId, clientId, status);
		if (null == rst) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (rst.getResultSuccess() == false) {
			goBackMainThread(rst.getResultMsg(), false);
			return;
		}
		customerDetail = rst.getCustomerDetail();
		customer = customerDetail.getClient();
		goBackMainThread("", true);
	}

	@UiThread
	void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this, msg);
		if (success) {
			initPages();
		}
		dismissProcessingDialog();
	}

	private void initPages() {
		List<Fragment> pages = new ArrayList<Fragment>();
		for (int i = 0; i < customerDetail.getClientBuildingRelations().length; i++) {
			customerRecordFragment = new CustomerRecordFragment_();
			customerRecordFragment.setClientBuildingRelations(customerDetail
					.getClientBuildingRelations()[i]);
			customerRecordFragment.setCustomer(customer);
			customerRecordFragment.setPosColor(posColor);
			pages.add(customerRecordFragment);
		}
		CusRecordFragmentAdapter adapter = new CusRecordFragmentAdapter(
				getSupportFragmentManager());
		adapter.addAll(pages);
		vpFragmentCustomer.setAdapter(adapter);
		circleIndicator.setViewPager(vpFragmentCustomer);
	}

	@Click
	void ivBackCustomer() {
		if (currentItem == 0) {
			setResult(ReqCode.CLIENT_IMPORTANT);
		}
		this.finish();
	}
	
	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

}

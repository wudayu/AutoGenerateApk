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
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.movitech.grande.MainApp;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.adapter.ChooseTwoCityAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.model.XcfcDistrict;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcDistrictsResult;
import com.movitech.grande.views.ProcessingDialog;

@EActivity(R.layout.activity_choose_two_city)
public class ChooseTwoCityActivity extends BaseActivity {
   
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.lv_city)
	ListView lvCity;
	
	@Bean(NetHandler.class)
	INetHandler netHandler;
	
	XcfcDistrict[] districts = null;//获取的区域集合
	@App
	MainApp mApp;
	ChooseTwoCityAdapter cityAdapter;
	
	ProcessingDialog processingDialog;
	@AfterViews
	void afterViews() {
		
		showProcessDialog();
		doLoadCity();
	}
	
	@Click 
	void ivBack() {
		this.finish();
	}
	
	@Background
	void doLoadCity() {
		XcfcDistrictsResult result = netHandler.postForGetDistrictsResult(mApp.getCurrCity() == null ? "" : mApp.getCurrCity().getName());

		if (result == null) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (!result.getResultSuccess()) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		districts = result.getDistricts();
		
		goBackMainThread("", true);
	}
	
	private void goBackMainThread(String msg, boolean success) {
		
		dismissProcessingDialog();
		
		if (success) {
			doBindData();
		}
	}
	
	@UiThread
	void doBindData() {
		cityAdapter = new ChooseTwoCityAdapter(context, districts, R.layout.item_choose_two_city, "");
		lvCity.setAdapter(cityAdapter);
		
		lvCity.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				Intent intent = new Intent();
				intent.putExtra(ExtraNames.CHOOSE_TWO, districts[position].getName());
				intent.putExtra(ExtraNames.CHOOSE_TWO_ID, districts[position].getId());
				setResult(ReqCode.CHOOSE_CITY_TWO, intent);
				
				ChooseTwoCityActivity.this.finish();
			}
		});
	}
	
	
	/**
	 * 显示进度条
	 */
	private void showProcessDialog() {
		processingDialog = new ProcessingDialog(context, true,
				new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
                       
					}
				});
		processingDialog.show();
	}
	
	/**
	 * 关闭进度条
	 */
	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}
}

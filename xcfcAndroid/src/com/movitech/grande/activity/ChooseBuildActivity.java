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
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.movitech.grande.MainApp;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.adapter.ChooseBuildAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcHousesResult;
import com.movitech.grande.views.ProcessingDialog;

@EActivity(R.layout.activity_choose_build)
public class ChooseBuildActivity extends BaseActivity {
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.lv_city)
	ListView lvCity;
	
	@Bean(NetHandler.class)
	INetHandler netHandler;
	
	XcfcHouse[] houses = null;
	@App
	MainApp mApp;
	
	int totalPage;
	
	ChooseBuildAdapter buildAdapter;
	
	ProcessingDialog processingDialog;
	
	@AfterViews
	void afterViews() {
		showProcessDialog();
		doLoadData();
	}
	
	@Click
	void ivBack() {
		this.finish();
	}
	
	@Background
	void doLoadData() {
		String intentCityId = getIntent().getStringExtra(ExtraNames.CHOOSE_BUILD);
		XcfcHousesResult result = netHandler.postForGetHousesResult(1, mApp.getCurrCity().getName(), intentCityId, "", "", "");		
		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		houses = result.getPageResult().getHouses();
        totalPage = result.getPageResult().getPageNo();
		goBackMainThread(result.getResultMsg(), true);
	}

	private void goBackMainThread(String string, boolean success) {
		dismissProcessingDialog();
		if (success) {
			doBindData();
		}
	}
	
	@UiThread
	void doBindData() {
		if (houses == null || houses.length == 0) {
			Utils.toastMessageForce(ChooseBuildActivity.this, "暂无数据");
		}
		buildAdapter = new ChooseBuildAdapter(context, R.layout.item_activity_intent_build_listview, "");
		buildAdapter.addData(houses);
		lvCity.setAdapter(buildAdapter);
		lvCity.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				Intent intent = new Intent();
				intent.putExtra(ExtraNames.CHOOSE_BUILD_INTENT, houses[position].getName());
				intent.putExtra(ExtraNames.CHOOSE_BUILD_ID, houses[position].getId());
				setResult(ReqCode.CHOOSE_BUILD_CODE, intent);
				ChooseBuildActivity.this.finish();
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

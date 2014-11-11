package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.movitech.grande.adapter.IntentBuildAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcHousesResult;
import com.movitech.grande.haerbin.R;

/**
 * 
 * @author Warkey.Song
 * @version 创建时间：2014年7月1日 上午10:37:08 意向楼盘选择
 */
@Deprecated
@EActivity(R.layout.activity_intent_build)
public class IntentBuildActivity extends BaseActivity {

	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.lv_intent_build_list)
	ListView lvIntentBuildList;

	XcfcHouse[] houses = null;

	@Bean(NetHandler.class)
	INetHandler netHandler;
    
	ProgressDialog progressDialog;
    
	Bundle bundle = null;
	String city = null;
	String districtId = null;
	@AfterViews
	void afterViews() {
		progressDialog = ProgressDialog.show(context,
				getString(R.string.str_loading),
				getString(R.string.str_please_loading));
		initData();
		// 载入数据
		doLoadDataAndBindData();
	}
	private void initData(){
		bundle = getIntent().getBundleExtra("IntentHouse");
		city = bundle.getString("city");
		districtId = bundle.getString("districtId");
	}

	@Background
	void doLoadDataAndBindData() {
		// FIXME 可能不止一页楼盘
		XcfcHousesResult result = netHandler.postForGetHousesResult(1, city, districtId, "", "", "");

		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		houses = result.getPageResult().getHouses();

		goBackMainThread(result.getResultMsg(), true);
	}

	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this, msg);

		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		if (progressDialog != null)
			progressDialog.dismiss();
		IntentBuildAdapter adapter = new IntentBuildAdapter(IntentBuildActivity.this,
			 R.layout.item_activity_intent_build_listview,"");
		adapter.addData(houses);
		lvIntentBuildList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcHouse selectedHouse = ((IntentBuildAdapter.ViewCache) view
						.getTag()).house;
				Intent data = new Intent();
				data.putExtra(ExtraNames.XCFC_HOUSE, selectedHouse);
				setResult(ReqCode.SELECT_HOUSE, data);
				finish();

				/*if (useForChoose) {
					Intent data = new Intent();
					data.putExtra(ExtraNames.XCFC_HOUSE, selectedHouse);
					setResult(ReqCode.SELECT_HOUSE, data);
					finish();
				} else {
					// 若不是作为选择楼盘界面，那么就是普通的用户查看界面 // 此时，点击item则需要跳转到相应的楼盘
					Intent intent = new Intent(context,
							HousesDetailActivity_.class);
					intent.putExtra("id", selectedHouse.getId());
					context.startActivity(intent);
				}*/

			}
		});
		lvIntentBuildList.setAdapter(adapter);
	}

	@Click
	void ivBack() {
		finish();
	}
}

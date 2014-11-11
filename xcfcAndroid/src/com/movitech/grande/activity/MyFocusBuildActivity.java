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
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.FocusBuildAdapter;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcHousesCollectionResult;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.BuildDetailActivity_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月4日 下午3:17:55
 *  类说明
 */
@EActivity(R.layout.activity_my_focus_build)
public class MyFocusBuildActivity extends BaseActivity {

	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.lv_focus_build)
	ListView lvFocusBuild;

	@App
	MainApp mApp;
	
	@Bean(NetHandler.class)
	NetHandler netHandler;
	@Bean(ImageUtils.class)
	ImageUtils imageUtils;
	
	ProcessingDialog processingDialog = null;
	
	XcfcHouseDetail [] xcfcHouses = null;
	@AfterViews
	void afterViews() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				BackgroundExecutor.cancelAll("focusBuild", false);
			}
		});
		processingDialog.show();
		doLoadDataFocusBuild();
	}

	@Click
	void ivBack() {
		this.finish();
	}
	
	@Background(id = "focusBuild")
	void doLoadDataFocusBuild(){
		XcfcHousesCollectionResult housesResult = netHandler.postForGetFocusBuild(mApp.getCurrUser().getId());
		if (housesResult == null) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (housesResult.getResultSuccess() == false) {
			goBackMainThread(housesResult.getResultMsg(), false);
			return;
		}
		xcfcHouses = housesResult.getHouseDetail();
		goBackMainThread(getString(R.string.toast_my_focus_succeed), true);
	}
	
	void goBackMainThread(String msg, boolean succeed){
		Utils.toastMessage(this, msg);
		if (succeed) {
			doBindData();
		}
		if (processingDialog != null)
			processingDialog.dismiss();
	}
	
	@UiThread
	void doBindData(){
		FocusBuildAdapter adapter = new FocusBuildAdapter(context, xcfcHouses, R.layout.item_listview_fragment_search_house, imageUtils);
		lvFocusBuild.setAdapter(adapter);
		
		lvFocusBuild.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				XcfcHouseDetail selectedHouse = xcfcHouses[position];	
				Intent intent = new Intent(context, BuildDetailActivity_.class);
				intent.putExtra("id", selectedHouse.getId());
				context.startActivity(intent);
			}
		});
	}
}

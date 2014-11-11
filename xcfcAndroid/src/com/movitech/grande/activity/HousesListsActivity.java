package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.HouseListAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcHousesResult;
import com.movitech.grande.utils.NetWorkUtil;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.BuildDetailActivity_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 16, 2014 9:26:47 PM
 * @Description: 所有楼盘列表页面
 * 
 **/
@EActivity(R.layout.activity_houses_lists)
public class HousesListsActivity extends BaseActivity {

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.ll_location)
	LinearLayout llLocation;
	@ViewById(R.id.iv_choose_location)
	ImageView ivChooseLocation;
	@ViewById(R.id.txt_location)
	TextView txtLocation;
	@ViewById(R.id.lv_houses)
	LoadDataListView lvHouses;

	@Extra
	boolean useForChoose;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@App
	MainApp mApp;

	XcfcHouse[] houses = null;
	HouseListAdapter adapter = null;
	XcfcCity city = null;
	View houseLoadMore = null;
	int currentPage = 1;
	int totalPage;
	int totalSize;
	
	boolean isLoadingMore = false;//标记是否正在加载更多  false---否，true---是

	ProcessingDialog processingDialog = null;
	NetWorkUtil netWorkUtil = null;
	@AfterViews
	void afterViews() {
		netWorkUtil = new NetWorkUtil(context);
		if (!netWorkUtil.isNetworkConnected()) {
			Utils.toastMessageForce(HousesListsActivity.this,
					getString(R.string.error_network_connection_not_well));
			return;
		}
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				BackgroundExecutor.cancelAll("queryData", false);
				HousesListsActivity.this.finish();
			}
		});
		processingDialog.show();

		city = mApp.getCurrCity();
		
		
		txtLocation.setText(city.getName());
		
		houseLoadMore = this.getLayoutInflater().inflate(R.layout.item_loading,
				null);
		houseLoadMore.setOnClickListener(null);
		lvHouses.addFooterView(houseLoadMore);

		lvHouses.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
			public void toBottom() {
				if (isLoadingMore) {
					return;
				}
				loadNewForHouses(true);
			}
		});
		// 载入数据
		doLoadDataAndBindData(currentPage, true);
	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	private void initListAdapter() {
		adapter = new HouseListAdapter(HousesListsActivity.this,
				R.layout.item_recommend_houses, imageUtils);
		lvHouses.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcHouse selectedHouse = ((HouseListAdapter.ViewCache) view
						.getTag()).house;

				if (useForChoose) {
					Intent data = new Intent();
					data.putExtra(ExtraNames.XCFC_HOUSE, selectedHouse);
					setResult(ReqCode.SELECT_HOUSE, data);
					finish();
				} else {
					// 若不是作为选择楼盘界面，那么就是普通的用户查看界面
					// 此时，点击item则需要跳转到相应的楼盘
					Intent intent = new Intent(context,
							BuildDetailActivity_.class);
					intent.putExtra("id", selectedHouse.getId());
					context.startActivity(intent);
				}
			}
		});
	}

	XcfcHousesResult xcfcHousesResult;

	@Background
	void loadNewForHouses(boolean useCity) {
		isLoadingMore = true;
		currentPage = lvHouses.getCurrentPage() + 1;
		xcfcHousesResult = netHandler.postForGetHousesResult(currentPage,
				useCity ? city.getName() : "", "", "", "", "");
		boolean ret = xcfcHousesResult == null
				|| !xcfcHousesResult.getResultSuccess();
		if (!ret) {
			addHousesItems();
		}
	}

	@UiThread
	void addHousesItems() {
		adapter.addItems(xcfcHousesResult.getPageResult().getHouses());
		lvHouses.setCurrentPage(currentPage);
		isLoadingMore =false;
	}

	@Background
	void doLoadDataAndBindData(int pageNo, boolean useCity) {
		// FIXME 可能不止一页楼盘
		XcfcHousesResult result = netHandler.postForGetHousesResult(pageNo,
				useCity ? city.getName() : "", "", "", "", "");

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
		totalSize = result.getPageResult().getTotalSize();
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
		initListAdapter();
		lvHouses.setAdapter(adapter);
		adapter.resetData(houses);
		lvHouses.setTotalPageCount(totalPage);
		lvHouses.setCurrentPage(1);

		dismissProcessingDialog();
	}

//	@Click
//	void llLocation() {
//		if (mApp.getCurrUser() != null) {
//			return;
//		}
//		Intent intent = new Intent(this, ChooseCityActivity_.class);
//		intent.putExtra(ExtraNames.XCFC_CITY_USE_RESULT, true);
//		if (txtLocation.getText() == null || txtLocation.getText().toString().equals("")) {
//			return;
//		}
//		intent.putExtra("currentCity", txtLocation.getText().toString());
//		startActivityForResult(intent, ReqCode.SELECT_CITY);
//	}

	@OnActivityResult(ReqCode.SELECT_CITY)
	void onResult(Intent data) {
		if (data == null)
			return;

		city = (XcfcCity) data.getSerializableExtra(ExtraNames.XCFC_CITY);
		txtLocation.setText(city.getName());
		//lvHouses.resetInitData();
		//currentPage = 1;
		lvHouses.removeFooterView(houseLoadMore);
		houseLoadMore = this.getLayoutInflater().inflate(R.layout.item_loading,
				null);
		lvHouses.addFooterView(houseLoadMore);
		lvHouses.addOnScrollListener();			
		doLoadDataAndBindData(1, true);
	}

	@Click
	void ivBack() {
		finish();
	}
}

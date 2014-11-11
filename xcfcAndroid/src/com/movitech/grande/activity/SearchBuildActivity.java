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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.movitech.grande.MainApp;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.adapter.HouseSearchListAdapter;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcHousesResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;

@EActivity(R.layout.activity_search_build)
public class SearchBuildActivity extends BaseActivity {
	@ViewById(R.id.edt_search_string)
	EditText edtSearchString;
	@ViewById(R.id.tv_sear_ok)
	TextView tvSearOk;
	@ViewById(R.id.lv_search_result)
	LoadDataListView lvSearchResult;
	@ViewById(R.id.rl_search_btn)
	RelativeLayout rlSearchBtn;
	
	View houseLoadMore = null;
	boolean isLoadingMore = false;// 标记是否正在加载更多 false---否，true---是
	ImageDownLoader downLoader = null;
	int totalPage;
	int currentPage;
	int totalSize;
	XcfcHouse[] houses = null;
	XcfcHouse[] searchHouses = null;
	@ViewById(R.id.tv_search_num)
	TextView tvSearchNum;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@App
	MainApp mApp;
	XcfcHousesResult xcfcHousesResult;

	HouseSearchListAdapter searchAdapter = null;

	ProcessingDialog processingDialog = null;

	@AfterViews
	void afterViews() {
		// 初始化界面

		houseLoadMore = this.getLayoutInflater().inflate(R.layout.item_loading,
				null);
		houseLoadMore.setOnClickListener(null);
		lvSearchResult.addFooterView(houseLoadMore);

		lvSearchResult.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
			public void toBottom() {
				if (isLoadingMore) {
					return;
				}
				loadNewForHouses();
			}
		});

		edtSearchString.addTextChangedListener(mTextWatcher);
		
		//showInputMethod();
	}

	@Background(id = "searchData")
	void doLoadSearchData() {
		String keyWord = edtSearchString.getText().toString();
		String city = mApp.getCurrCity() == null ? "" : mApp.getCurrCity()
				.getName();
		XcfcHousesResult result = netHandler.postForGetHousesResult(1, city,
				"", keyWord, "", "");

		if (null == result) {
			goSearchBackMainThread(getString(R.string.error_server_went_wrong),
					false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goSearchBackMainThread(result.getResultMsg(), false);
			return;
		}

		searchHouses = result.getPageResult().getHouses();
		totalPage = result.getPageResult().getPageNo();
		totalSize = result.getPageResult().getTotalSize();

		goSearchBackMainThread(result.getResultMsg(), true);
	}

	private void goSearchBackMainThread(String resultMsg, boolean success) {
		if (success) {
			doBindSearchData();
		}
	}

	@UiThread
	void doBindSearchData() {
		hideInputMethod();
		if (searchHouses == null)
			return;
		if (processingDialog != null)
			processingDialog.dismiss();
		tvSearchNum.setText(getString(R.string.txt_search_build_gong) + totalSize + getString(R.string.txt_search_build_result));
		searchAdapter = new HouseSearchListAdapter(context, searchHouses,
				R.layout.item_listview_fragment_search_house, imageUtils);

		lvSearchResult.setAdapter(searchAdapter);
		lvSearchResult.setTotalPageCount(totalPage);
		lvSearchResult.setCurrentPage(1);

		tvSearchNum.setVisibility(View.VISIBLE);
		lvSearchResult.setVisibility(View.VISIBLE);
		lvSearchResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcHouse selectedHouse = searchHouses[position];
				// Intent intent = new Intent(context,
				// HousesDetailActivity_.class);
				Intent intent = new Intent(context, BuildDetailActivity_.class);
				intent.putExtra("id", selectedHouse.getId());
				context.startActivity(intent);
				
				SearchBuildActivity.this.finish();

			}
		});
	}

	@Background
	void loadNewForHouses() {
		isLoadingMore = true;
		String keyWord = edtSearchString.getText().toString();
		currentPage = lvSearchResult.getCurrentPage() + 1;
		String cityName = mApp.getCurrCity() == null ? "" : mApp.getCurrCity().getName();
		xcfcHousesResult = netHandler.postForGetHousesResult(currentPage,
				cityName, "", keyWord, "", "");
		boolean ret = xcfcHousesResult == null
				|| !xcfcHousesResult.getResultSuccess();
		if (!ret) {
			addHousesItems();
		}
	}

	@UiThread
	void addHousesItems() {
		searchAdapter.addItems(xcfcHousesResult.getPageResult().getHouses());
		lvSearchResult.setCurrentPage(currentPage);
		isLoadingMore = false;
	}

	@Click
	void rlSearchBtn() {
		String tvString = tvSearOk.getText().toString();
		if (tvString.hashCode() == getString(R.string.btn_search_build_cancle).hashCode()) {
			//hideInputMethod();
			this.finish();
		} else {
			//hideInputMethod();
			processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					BackgroundExecutor.cancelAll("searchData", false);
				}
			});
			processingDialog.show();
			doLoadSearchData();
		}
	}
	
	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			String tmpString = edtSearchString.getText().toString();
			if (tmpString != null) {
				if (tmpString.trim().length() > 0) {
					tvSearOk.setText(getString(R.string.btn_search_build_submit));
				} else {
					tvSearOk.setText(getString(R.string.btn_search_build_cancle));
					tvSearchNum.setVisibility(View.GONE);
					lvSearchResult.setVisibility(View.GONE);
				}
			}
		}
	};

	@SuppressWarnings("unused")
	private void showInputMethod() {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		((FragmentActivity) context).getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}

	private void hideInputMethod() {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		try {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			if (imm.isActive()) {
				imm.hideSoftInputFromWindow(((FragmentActivity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			Log.i("HouseFragment", e.toString());
		}
	}
	
}

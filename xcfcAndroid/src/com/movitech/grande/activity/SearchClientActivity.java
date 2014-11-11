package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.ClientSearchAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcClient;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcClientResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.CustomerDetailActivity_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月14日 下午4:51:37 
 * 类说明:搜索客户
 */
@EActivity(R.layout.activity_search_client)
public class SearchClientActivity extends BaseActivity {

	@ViewById(R.id.edt_search_str)
	EditText edtSearchStr;
	@ViewById(R.id.rl_search_btn)
	RelativeLayout rlSearchBtn;
	@ViewById(R.id.tv_sear_ok)
	TextView tvSearOk;
	@ViewById(R.id.tv_search_num)
	TextView tvSearchNum;
	@ViewById(R.id.rl_nosearch)
	RelativeLayout rlNosearch;
	@ViewById(R.id.lv_search_result)
	LoadDataListView lvSearchResult;

	@App
	MainApp mApp;
	@Bean(NetHandler.class)
	NetHandler netHandler;

	XcfcClient[] clients = null;
	
	int toatlPage;
	int currentPage;
	int totalClient;
	boolean isLoadingMore = false;//标记是否正在加载更多  false---否，true---是
	View clientLoadMore = null;
	
	ClientSearchAdapter clientSearchAdapter = null;

	@AfterViews
	void afterViews() {
		edtSearchStr.addTextChangedListener(mTextWatcher);
		showInputMethod();
		
		clientLoadMore = this.getLayoutInflater().inflate(R.layout.item_loading,
				null);
		clientLoadMore.setOnClickListener(null);
		lvSearchResult.addFooterView(clientLoadMore);

		lvSearchResult.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
			public void toBottom() {
				if (isLoadingMore) {
					return;
				}
				String keyWord = edtSearchStr.getText().toString();
				loadNewForClients(keyWord);
			}
		});
	}

	@Background
	void doLoadData(String keyWord) {
		XcfcClientResult clientResult = netHandler.postForGetClients(1, mApp
				.getCurrUser().getId(), keyWord);
		if (null == clientResult) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (clientResult.getResultSuccess() == false) {
			goBackMainThread(clientResult.getResultMsg(), false);
			return;
		}

		clients = clientResult.getPageResult().getClients();
		toatlPage = clientResult.getPageResult().getPageNo();
		totalClient = clientResult.getPageResult().getTotalSize();
		goBackMainThread("", true);
	}

	void goBackMainThread(String msg, boolean succeed) {
		Utils.toastMessage(SearchClientActivity.this, msg);
		if (succeed) {
			doBindData();
		} else {
			rlNosearch.setVisibility(View.VISIBLE);
			lvSearchResult.setVisibility(View.GONE);
		}
	}

	@UiThread
	void doBindData() {
		if (clients == null || clients.length == 0) {
			return;
		}
		hideInputMethod();
		rlNosearch.setVisibility(View.GONE);
		lvSearchResult.setVisibility(View.VISIBLE);
		tvSearchNum.setVisibility(View.VISIBLE);
		tvSearchNum.setText(getString(R.string.txt_search_build_gong) + totalClient + getString(R.string.txt_search_build_result));
		
		clientSearchAdapter = new ClientSearchAdapter(context);
		clientSearchAdapter.setClients(clients);
		clientSearchAdapter.notifyDataSetChanged();
		
		lvSearchResult.setAdapter(clientSearchAdapter);
		lvSearchResult.setCurrentPage(1);
		lvSearchResult.setTotalPageCount(toatlPage);
		
		lvSearchResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcClient xcfcClient = clients[position];
				Intent intent = new Intent(SearchClientActivity.this,
						CustomerDetailActivity_.class);
				intent.putExtra("customersId",xcfcClient.getId());
				intent.putExtra("customersColor",position % 6);
				intent.putExtra(ExtraNames.MINE_CURRENT_CUSTOMER, 0);
				startActivity(intent);
			}
		});
	}

	XcfcClientResult xcfcClientResult;

	@Background
	void loadNewForClients(String keyWord) {
		isLoadingMore = true;
		currentPage = lvSearchResult.getCurrentPage() + 1;
		xcfcClientResult = netHandler.postForGetClients(currentPage, mApp
				.getCurrUser().getId(), keyWord);
		boolean ret = xcfcClientResult == null
				|| !xcfcClientResult.getResultSuccess();
		if (!ret) {
			addClientsItems();
		}
	}

	@UiThread
	void addClientsItems() {
		clientSearchAdapter.addItems(xcfcClientResult.getPageResult().getClients());
		lvSearchResult.setCurrentPage(currentPage);
		isLoadingMore =false;
	}
	
	@Click
	void rlSearchBtn() {

		String tvString = tvSearOk.getText().toString();
		if (tvString.hashCode() == getString(R.string.btn_search_build_cancle)
				.hashCode()) {
			hideInputMethod();
			this.finish();
		} else {
			//hideInputMethod();
			// progressDialog = ProgressDialog.show(context,
			// getString(R.string.str_loading),
			// getString(R.string.str_please_loading));
			String keyWord = edtSearchStr.getText().toString();
			doLoadData(keyWord);
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
			String tmpString = edtSearchStr.getText().toString();
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
				imm.hideSoftInputFromWindow(((FragmentActivity) context)
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			Log.i("SearchClientActivity", e.toString());
		}

	}
}

package com.movitech.grande.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.CommissionSuccessedAdapter;
import com.movitech.grande.model.XcfcCommission;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcCommissionResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月19日 下午4:27:51 
 * 
 */
@EFragment(R.layout.fragment_commission)
public class CommissionSuccessFragment extends BaseFragment {
	@ViewById(R.id.lv_commission)
	LoadDataListView lvCommission;
	@ViewById(R.id.tv_commission_count)
	TextView tvCommissionCount;
	@ViewById(R.id.tv_commission)
	TextView tvCommission;
	@ViewById(R.id.tv_commission_unit)
	TextView tvCommissionUnit;

	ProcessingDialog processingDialog = null;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;
	View successCommissionLoadMore = null;
	XcfcCommission[] commissionsArray = null;
	CommissionSuccessedAdapter successedAdapter = null;
	boolean isLoadIng = false;
	int totalSize;
	int currentPage;
	int totalPage;
	String commissionSuccessCount;
	XcfcUser user = null;
	
	@AfterViews
	void afterViews() {
		showProgressDialog();
		// 初始化ListView
		successCommissionLoadMore = getActivity().getLayoutInflater().inflate(
				R.layout.item_loading, null);

		successCommissionLoadMore.setOnClickListener(null);
	    lvCommission.addFooterView(successCommissionLoadMore);

		lvCommission.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
			public void toBottom() {
				if (!isLoadIng) {
					//showProgressDialog();
					loadNewForCommission();
				}
			}
		});
		doLoadDataAndBindData();
	}
	
	public void setUser(String userId){
		user = new XcfcUser();
		user.setId(userId);
	}
	@Background
	void doLoadDataAndBindData() {
		isLoadIng = true;
		XcfcCommissionResult result = netHandler.postForCommissionResult(1, user == null ? mApp.getCurrUser().getId() : user.getId(), "100");
		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}
		commissionsArray = result.getPageResult().getCommissions();
		totalSize = result.getPageResult().getTotalSize();
		//currentPage = result.getPageResult().getCurPageNo();
		commissionSuccessCount = result.getPageResult().getAmounts();
		totalPage = result.getPageResult().getPageNo();
		goBackMainThread(result.getResultMsg(), true);

	}

	private void goBackMainThread(String msg, boolean success) {
		isLoadIng = false;
		dismissProcessingDialog();
		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		successedAdapter = new CommissionSuccessedAdapter(context, commissionsArray);
		lvCommission.setAdapter(successedAdapter);
		lvCommission.setTotalPageCount(totalPage);
		lvCommission.setCurrentPage(1);
		tvCommission.setText(getString(R.string.txt_my_commission_succeed_total));
		tvCommissionCount.setText(Math.round(Double.valueOf(commissionSuccessCount)) + "");
		tvCommissionUnit.setVisibility(View.VISIBLE);
	}
	
	XcfcCommissionResult commissionResult;
	@Background
	 void loadNewForCommission() {
		if (isLoadIng)
			return;
		isLoadIng = true;
		currentPage = lvCommission.getCurrentPage() + 1;
		commissionResult = netHandler.postForCommissionResult(currentPage, user == null ? mApp.getCurrUser()
				.getId() : user.getId(), "100");
		boolean ret = commissionResult == null
				|| !commissionResult.getResultSuccess();
		if (!ret) {
			addCommissionItems();
		}
		isLoadIng = false;
	}
	@UiThread
	void addCommissionItems() {
		successedAdapter.addItems(commissionResult.getPageResult().getCommissions());
		lvCommission.setCurrentPage(currentPage);
	}
	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}
	
	public void reLoadData() {
		if (!isLoadIng) {
			//showProgressDialog();
			lvCommission.resetVar();
			lvCommission.resetFootView();
			lvCommission.addOnScrollListener();
			doLoadDataAndBindData();
		}
	}
	private void showProgressDialog() {
		processingDialog = new ProcessingDialog(context, true,
				new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
					}
				});
		processingDialog.show();
	}
}

package com.movitech.grande.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.activity.MyCustomerActivity;
import com.movitech.grande.adapter.CustomerGridViewAdapter;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcMyCustomer;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcMyCustomerResult;
import com.movitech.grande.views.LoadDataGridView;
import com.movitech.grande.views.LoadDataGridView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.CustomerDetailActivity_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月18日 下午4:22:09 类说明
 */
@EFragment(R.layout.layout_gridview_activity_customer)
public class CustomerRecomOkFragment extends BaseFragment {

	@ViewById(R.id.tv_customer_count)
	TextView tvCustomerCount;
	@ViewById(R.id.gridview_customer)
	LoadDataGridView gridviewCustomer;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;

	XcfcMyCustomer[] customers = null;
	int customerCount;

	CustomerGridViewAdapter gridViewAdapter = null;
	int currentPage = 1;
	int totalPage;
	boolean isLoadingMore = false;// 标记是否正在加载更多 false---否，true---是
	boolean isLoadDataIng = true;
	XcfcUser user = null;
	
	@AfterViews
	void afterViews() {
		gridviewCustomer
				.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
					public void toBottom() {
						if (isLoadingMore) {
							return;
						}
						loadNewForCustomers(true);
					}
				});
		// initVar();
		doLoadDataAndBindData(1, user == null ? mApp.getCurrUser().getId()
				: user.getId(), "", "", "",
				"20,40");
	}
	
	public void setUser(String userId){
		user = new XcfcUser();
		user.setId(userId);
	}
	@Background(id = "loadCustomerData")
	void doLoadDataAndBindData(int pageNo, String id, String isVip,
			String isVisited, String isSignup, String state) {
		// TODO get the data from server & initialize triple List<>
		isLoadDataIng = true;
		XcfcMyCustomerResult result = netHandler.postForGetMyCustomersResult(
				pageNo, id, isVip, isVisited, isSignup, state);

		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}
		customers = result.getPageResult().getCustomers();
		customerCount = result.getPageResult().getTotalSize();
		totalPage = result.getPageResult().getPageNo();
		goBackMainThread(result.getResultMsg(), true);

	}

	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(getActivity(), msg);
	    isLoadDataIng = false;
		if (success) {
			doBindData();
		}

		((MyCustomerActivity) getActivity()).dismissProcessingDialog();
	}

	@UiThread
	void doBindData() {
		if (getActivity() == null || this.isDetached())
			return;

		if (isAdded()) {
			tvCustomerCount.setText(getString(R.string.txt_mycustomer_gong) + customerCount	+ getString(R.string.txt_mycustomer_result));
		}
	    gridViewAdapter = new CustomerGridViewAdapter(
				context, customers);
		gridviewCustomer.setAdapter(gridViewAdapter);
		gridviewCustomer.setTotalPageCount(totalPage);
		gridviewCustomer.setCurrentPage(1);
		gridviewCustomer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcMyCustomer selectedCustomer = ((CustomerGridViewAdapter.ViewHolder)view.getTag()).customer;
				Intent intent = new Intent(getActivity(),
						CustomerDetailActivity_.class);
				intent.putExtra("customersId", selectedCustomer.getId());
				intent.putExtra("customersColor", position % 6);
				intent.putExtra("currentItem", 3);
				startActivityForResult(intent, ReqCode.CLIENT_IMPORTANT);
			}
		});
	}

	XcfcMyCustomerResult xcfcMyCustomerResult;

	@Background
	void loadNewForCustomers(boolean useCity) {
		isLoadingMore = true;
		currentPage = gridviewCustomer.getCurrentPage() + 1;
		xcfcMyCustomerResult = netHandler.postForGetMyCustomersResult(
				currentPage, user == null ? mApp.getCurrUser().getId()
						: user.getId(), "", "", "", "20,40");
		boolean ret = xcfcMyCustomerResult == null
				|| !xcfcMyCustomerResult.getResultSuccess();
		if (!ret) {
			addCustomerItems();
		}
	}

	@UiThread
	void addCustomerItems() {
		gridViewAdapter.addItems(xcfcMyCustomerResult.getPageResult()
				.getCustomers());
		gridviewCustomer.setCurrentPage(currentPage);
		isLoadingMore = false;
	}
	
	public void loadDataAgain(){
		if (isLoadDataIng) {
			return;
		}
		doLoadDataAndBindData(1,user == null ? mApp.getCurrUser().getId()
				: user.getId(), "", "", "", "20,40");
	}
	
	
}

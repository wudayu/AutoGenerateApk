package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.movitech.grande.adapter.InfoListAdapter;
import com.movitech.grande.constant.NewsCatagoryId;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcInfo;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcInfoesResult;
import com.movitech.grande.utils.NetWorkUtil;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.InfoDetailActivity_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 10, 2014 10:53:57 AM
 * @Description: This is David Wu's property.
 * 
 **/
@EActivity(R.layout.activity_hot_action)
public class HotActionActivity extends BaseActivity {

	public static final int PAGE_COUNT = 3;

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;

	@ViewById(R.id.lv_hot_action)
	LoadDataListView lvHotAction;

	View hotActionLoadMore = null;
	XcfcInfo[] hotActionInfos = null;
	InfoListAdapter hotActionAdapter = null;
	int hotActionCurrPage = 1;
	int hotActionTotalPage;

	ProcessingDialog processingDialog = null;
    NetWorkUtil netWorkUtil = null;
	@Click
	void ivBack() {
		this.finish();
	}

	@AfterViews
	void afterViews() {
		netWorkUtil = new NetWorkUtil(context);
		if (!netWorkUtil.isNetworkConnected()) {
			Utils.toastMessageForce(HotActionActivity.this,
					getString(R.string.error_network_connection_not_well));
			return;
		}
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				BackgroundExecutor.cancelAll("queryData", false);
				HotActionActivity.this.finish();
			}
		});
		processingDialog.show();

		// 初始化ListView
		hotActionLoadMore = this.getLayoutInflater().inflate(
				R.layout.item_loading, null);

		hotActionLoadMore.setOnClickListener(null);
		lvHotAction.addFooterView(hotActionLoadMore);

		lvHotAction.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
			public void toBottom() {
				loadNewForHouses();
			}
		});
		// 载入数据
		doLoadDataAndBindData();
	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	XcfcInfoesResult resultHotActions;

	boolean isLoading = false;
	@Background
	void loadNewForHouses() {
		if (isLoading)
			return;

		isLoading = true;
		hotActionCurrPage = lvHotAction.getCurrentPage() + 1;
		resultHotActions = netHandler.postForGetInfoesResult(hotActionCurrPage,
				NewsCatagoryId.CATAGORY_ID_HOUSE, "0", "");
		boolean ret = resultHotActions == null
				|| !resultHotActions.getResultSuccess();
		if (!ret) {
			addHousesItems();
		}
		isLoading = false;
	}

	@UiThread
	void addHousesItems() {
		hotActionAdapter.addItems(resultHotActions.getPageResult().getInfoes());
		lvHotAction.setCurrentPage(hotActionCurrPage);
	}

	@Background(id="queryData")
	void doLoadDataAndBindData() {
		XcfcInfoesResult resultHouses = netHandler.postForGetInfoesResult(
				hotActionCurrPage, NewsCatagoryId.CATAGORY_ID_HOUSE, "0", "");

		if (null == resultHouses) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (resultHouses.getResultSuccess() == false) {
			goBackMainThread(resultHouses.getResultMsg(), false);
			return;
		}

		hotActionInfos = resultHouses.getPageResult().getInfoes();
		hotActionTotalPage = resultHouses.getPageResult().getPageNo();

		goBackMainThread(resultHouses.getResultMsg(), true);
	}

	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(this, msg);

		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		hotActionAdapter = new InfoListAdapter(this, hotActionInfos,
				R.layout.item_listview_fragment_info, imageUtils);
		lvHotAction.setAdapter(hotActionAdapter);
		lvHotAction.setTotalPageCount(hotActionTotalPage);
		lvHotAction.setCurrentPage(1);

		lvHotAction.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcInfo info = ((InfoListAdapter.ViewCache) view.getTag()).info;
				InfoDetailActivity_.intent(HotActionActivity.this)
						.infoId(info.getId()).start();
			}
		});

		dismissProcessingDialog();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

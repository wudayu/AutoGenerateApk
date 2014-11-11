package com.movitech.grande.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.movitech.grande.adapter.InfoListAdapter;
import com.movitech.grande.constant.NewsCatagoryId;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.model.XcfcInfo;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcInfoesResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.InfoDetailActivity_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 17, 2014 2:23:09 PM
 * @Description: This is David Wu's property.
 * 
 **/
@Deprecated
@EFragment(R.layout.fragment_houses_detail_dynamic)
public class HousesDetailDynamicFragment extends BaseFragment {

	@ViewById(R.id.lv_fragment_houses_detail_dynamic)
	LoadDataListView lvFragmentHousesDetailDynamic;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;

	XcfcHouseDetail houseDetail = null;
	InfoListAdapter adapter = null;

	XcfcInfo[] infoes = null;
	int currPage = 1;
	int totalPage;

	@AfterViews
	void afterViews() {
		// 初始化ListView
		View hotActionLoadMore = getActivity().getLayoutInflater().inflate(
				R.layout.item_loading, null);

		hotActionLoadMore.setOnClickListener(null);
		lvFragmentHousesDetailDynamic.addFooterView(hotActionLoadMore);

		lvFragmentHousesDetailDynamic
				.setOnScrollToEdgeCallBack(new OnScrollToEdgeCallBack() {
					public void toBottom() {
						loadNewForInfoes();
					}
				});
	}

	XcfcInfoesResult infoResult;

	@Background
	void loadNewForInfoes() {
		currPage = lvFragmentHousesDetailDynamic.getCurrentPage() + 1;
		infoResult = netHandler.postForGetHotActionsResult(currPage, NewsCatagoryId.CATAGORY_ID_HOUSE, "", houseDetail.getId());
		boolean ret = infoResult == null
				|| !infoResult.getResultSuccess();
		if (!ret) {
			addInfoesItems();
		}
	}

	@UiThread
	void addInfoesItems() {
		adapter.addItems(infoResult.getPageResult().getInfoes());
		lvFragmentHousesDetailDynamic.setCurrentPage(currPage);
	}

	public void setHouseDetail(XcfcHouseDetail houseDetail) {
		this.houseDetail = houseDetail;

		doLoadDataAndBindData();
	}

	@Background
	void doLoadDataAndBindData() {
		if (getActivity() == null || this.isDetached())
			return;

		XcfcInfoesResult result = netHandler.postForGetHotActionsResult(1, NewsCatagoryId.CATAGORY_ID_HOUSE, "", houseDetail.getId());
		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		infoes = result.getPageResult().getInfoes();
		totalPage = result.getPageResult().getPageNo();

		goBackMainThread(result.getResultMsg(), true);
	}

	boolean dataLoadSuccess = false;
	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(getActivity(), msg);

		dataLoadSuccess = success;
	}

	public void bindDataNow() {
		if (dataLoadSuccess)
			doBindData();
	}

	@UiThread
	void doBindData() {
		if (getActivity() == null || this.isDetached())
			return;

		adapter = new InfoListAdapter(getActivity(), infoes,
				R.layout.item_listview_fragment_info, imageUtils);
		lvFragmentHousesDetailDynamic.setAdapter(adapter);
		lvFragmentHousesDetailDynamic.setTotalPageCount(totalPage);
		lvFragmentHousesDetailDynamic.setCurrentPage(1);

		lvFragmentHousesDetailDynamic.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				XcfcInfo info = ((InfoListAdapter.ViewCache) view.getTag()).info;
				InfoDetailActivity_.intent(getActivity()).infoId(info.getId()).start();
			}
		});
	}

}

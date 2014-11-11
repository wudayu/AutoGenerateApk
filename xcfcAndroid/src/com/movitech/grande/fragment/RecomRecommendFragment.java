package com.movitech.grande.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.IntentBuildAdapter;
import com.movitech.grande.adapter.SelectedDistrictsAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.model.XcfcDistrict;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcDistrictsResult;
import com.movitech.grande.net.protocol.XcfcHousesResult;
import com.movitech.grande.net.protocol.XcfcRecommendedResult;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.SelectedDistrictDialog;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.LoginActivity_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 10, 2014 10:54:34 AM
 * @Description: This is David Wu's property.
 * 
 **/
@EFragment(R.layout.fragment_recom_recommend)
public class RecomRecommendFragment extends BaseFragment {

	@ViewById(R.id.txt_intent_houses)
	TextView txtIntentHouses;
	@ViewById(R.id.txt_man_recommended)
	TextView txtManRecommended;
	@ViewById(R.id.edt_remark)
	EditText edtRemark;
	@ViewById(R.id.edt_man_recommended)
	EditText edtManRecommended;
	@ViewById(R.id.txt_intent_city)
	TextView txtIntentCity;
	@ViewById(R.id.txt_contact_way)
	TextView txtContactWay;
	@ViewById(R.id.edt_intent_city)
	EditText edtIntentCity;
	@ViewById(R.id.txt_intent_city_unit)
	TextView txtIntentCityUnit;
	@ViewById(R.id.edt_contact_way)
	EditText edtContactWay;
	@ViewById(R.id.edt_intent_houses)
	TextView edtIntentHouses;
	@ViewById(R.id.btn_recommend_immediate)
	Button btnRecommendImmediate;
	@ViewById(R.id.iv_contact_man_recommended)
	ImageView ivContactManRecommended;
	@ViewById(R.id.txt_remark)
	TextView txtRemark;
	@ViewById(R.id.ll_sheet)
	LinearLayout llSheet;
	@ViewById(R.id.rl_intent_house_right)
	RelativeLayout rlIntentHouseRight;
	
	@App
	MainApp mApp;
	@Bean(NetHandler.class)
	INetHandler netHandler;

	String username = null;
	String usernumber = null;
	String recomendedBuildingId = null;
	String districtId = "";
	XcfcHouseDetail intentHouseDetail = null;
	XcfcCity city = null;
	XcfcDistrict[] districts = null;
	XcfcDistrict district = null;
	XcfcHouse[] houses = null;
	
	int currentPage = 1;
	int totalPage;
	boolean isLoadingMore = false;// 标记是否正在加载更多 false---否，true---是
	// 自定义的弹出框类
	SelectedDistrictDialog menuDialog = null;

	boolean isIntentHouseing = false;


	@AfterViews
	void afterViews() {
		initViews();
	}

	void initViews() {
		edtIntentCity.setFocusable(false);
	}

	@Override
	public void onResume() {
		edtIntentCity.setText("");
		edtIntentHouses.setText("");
		if (city == null)
			city = mApp.getCurrCity();

		if (null == city) {
			edtIntentCity.setText("");
		} else {
		//	edtIntentCity.setText(city.getName());
			//queryDistrict();
		}
		super.onResume();
	}

	@Click
	void edtIntentDistrict() {
		
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			XcfcDistrict distr = ((SelectedDistrictsAdapter.ViewCache) view.getTag()).district;
			if (distr.getName().equals(
				getString(R.string.txt_list_header_no_districts))) {
				edtIntentCity.setText(getString(R.string.txt_list_header_no_districts));
				//txtIntentDistrictUnit.setVisibility(View.VISIBLE);
			} else {
				edtIntentCity.setText(distr.getName());
			}
			districtId = distr.getId();
			if (distr.getName().endsWith(
					getString(R.string.txt_intent_district_unit))) {
				//txtIntentDistrictUnit.setVisibility(View.GONE);
			}
			if (menuDialog == null)
				return;

			menuDialog.dismiss();
		}
	};

	private OnItemClickListener intentItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			XcfcHouse xcfcHouse = ((IntentBuildAdapter.ViewCache) view.getTag()).house;
			edtIntentHouses.setText(xcfcHouse.getName());
			recomendedBuildingId = xcfcHouse.getId();
			if (menuDialog == null)
				return;

			menuDialog.dismiss();
		}

	};

    //加载更多	
    private OnScrollToEdgeCallBack edgeCallBackHouse = new OnScrollToEdgeCallBack() {
		
		@Override
		public void toBottom() {
			if (isLoadingMore) {
				return;
			}
			loadNewForHouses();
		}
	};
	
	XcfcHousesResult xcfcHousesMoreResult;
	@Background
	void loadNewForHouses() {
		isLoadingMore = true;
		if (menuDialog == null) {
			return;
		}
		currentPage = menuDialog.lvSelect.getCurrentPage() + 1;
		String intentCity = mApp.getCurrCity().getName();
		xcfcHousesMoreResult = netHandler.postForGetHousesResult(currentPage, intentCity, districtId, "", "", "");
		boolean ret = xcfcHousesMoreResult == null
				|| !xcfcHousesMoreResult.getResultSuccess();
		if (!ret) {
			addHousesItems();
		}
	}

	@UiThread
	void addHousesItems() {
		menuDialog.intentBuildAdapter.addItem(xcfcHousesMoreResult.getPageResult().getHouses());
		menuDialog.lvSelect.setCurrentPage(currentPage);
		isLoadingMore = false;
	}	
	@Click
	void edtIntentCity() {
		queryDistrict();
	}

	@Click
	void ivContactManRecommended() {
		startActivityForResult(new Intent(Intent.ACTION_PICK,
				ContactsContract.Contacts.CONTENT_URI), ReqCode.GET_CONTACT);
	}

	/**
	 * 意向楼盘
	 */
	@Click
	void rlIntentHouseRight() {
		doLoadDataAndBindDataIntentHouse();
		rlIntentHouseRight.setClickable(false);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;

		if (resultCode == Activity.RESULT_OK) {
			ContentResolver reContentResolverol = getActivity()
					.getContentResolver();
			Uri contactData = data.getData();
			@SuppressWarnings("deprecation")
			Cursor cursor = getActivity().managedQuery(contactData, null, null,
					null, null);
			cursor.moveToFirst();
			username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = reContentResolverol.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
			while (phone.moveToNext()) {
				usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				edtManRecommended.setText(username);
				edtContactWay.setText(usernumber);
			}
		}

		if (resultCode == ReqCode.SELECT_CITY) {
			XcfcCity xcfcCity = (XcfcCity) data.getSerializableExtra(ExtraNames.XCFC_CITY);

			if (xcfcCity == null)
				return;

			edtIntentCity.setText("");
			// 清空当前区域信息
			districts = null;
			district = null;
			edtIntentHouses.setText("");
			//edtIntentDistrict.setText("");
			//txtIntentDistrictUnit.setVisibility(View.VISIBLE);
			// 刷新区域信息
			//queryDistrict();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Background
	void queryDistrict() {
		XcfcDistrictsResult result = netHandler.postForGetDistrictsResult(mApp.getCurrCity().getName());
		if (result == null) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (!result.getResultSuccess()) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		districts = result.getDistricts();
		goBackMainThread("", true);
	}

	
	private void goBackMainThread(String msg, boolean success) {
		Utils.toastMessage(getActivity(), msg);

		if (success) {
			doBindData();
		}
	}
	@UiThread
	 void doBindData() {
		String districtString = edtIntentCity.getText().toString();

		XcfcDistrict[] disArray = new XcfcDistrict[districts.length + 1];
		disArray[0] = new XcfcDistrict();
		disArray[0].setName(getString(R.string.txt_list_header_no_districts));
		disArray[0].setCityId("");
		disArray[0].setId("");
		for (int i = 1; i < disArray.length; i++) {
			disArray[i] = new XcfcDistrict();
			disArray[i].setCityId(districts[i - 1].getCityId());
			disArray[i].setId(districts[i - 1].getId());
			disArray[i].setName(districts[i - 1].getName());
		}
		createMyDialog(disArray, districtString);
		//清空意向楼盘
	    edtIntentHouses.setText("");
	}

	ProcessingDialog processingDialog = null;

	@Click
	void btnRecommendImmediate() {
		if ("".equals(edtManRecommended.getText().toString())) {
			Utils.toastMessageForce(getActivity(),
					getString(R.string.hint_input_name));
			return;
		}else if ("".equals(edtContactWay.getText().toString())) {
			Utils.toastMessageForce(getActivity(),
					getString(R.string.hint_input_contactway));
			return;
		}else if ("".equals(edtIntentCity.getText().toString())) {
			Utils.toastMessageForce(getActivity(),
					getString(R.string.hint_input_city));
			return;
		}else if ("".equals(edtIntentHouses.getText().toString())) {
			Utils.toastMessageForce(getActivity(),
					getString(R.string.hint_input_house));
			return;
		}
		{
			/*if (mApp.getCurrUser() == null) {
				//未登录的时候清空楼盘和城市
				edtIntentCity.setText("");
				edtIntentHouses.setText("");
				edtRemark.setText("");
				LoginActivity_.intent(this).start();
				return;
			}*/
			
			if (!mApp.isLogin()) {
				//未登录的时候清空楼盘和城市
				edtIntentCity.setText("");
				edtIntentHouses.setText("");
				edtRemark.setText("");
				LoginActivity_.intent(this).start();
				return;
			}
			String clientName = edtManRecommended.getText().toString();
			String clientPhone = Utils.formatNumberOnlyDigits(edtContactWay.getText().toString());
			String brokerId = mApp.getCurrUser().getId();
			String recommendMark = edtRemark.getText().toString();

			processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					BackgroundExecutor.cancelAll("queryData", false);
				}
			});
			processingDialog.show();
            System.out.println("clientName----------" + clientName);
            System.out.println("clientPhone----------" + clientPhone);
            System.out.println("recomendedBuildingId----------" + recomendedBuildingId);
            System.out.println("brokerId----------" + brokerId);
            System.out.println("recommendMark----------" + recommendMark);
            
			doLoadRecommendedRequest(clientName, clientPhone, recomendedBuildingId, brokerId, recommendMark);
		}
	}

	@Background(id="queryData")
	void doLoadRecommendedRequest(String clientName, String clientPhone, String recommendedBuilding, String brokerId, String recommendMark) {
		XcfcRecommendedResult recommendedResult = netHandler.postForGetRecommendResult(clientName, clientPhone, recommendedBuilding, brokerId, recommendMark);
		if (recommendedResult == null) {
			goBackMainThreadRecommended(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (!recommendedResult.getResultSuccess()) {
			goBackMainThreadRecommended(recommendedResult.getResultMsg(), false);
			return;
		}
		goBackMainThreadRecommended(recommendedResult.getResultMsg(), true);
	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}
	
	void goBackMainThreadRecommended(String msg, boolean success) {
		dismissProcessingDialog();

		if (success) {
			doBindDataRecommended();
			
			return;
		}
		if (msg == null) {
			return;
		}
		Utils.toastMessageForce(getActivity(), msg);	
	}

	@UiThread
	void doBindDataRecommended() {
		Utils.toastMessageForce(getActivity(), getString(R.string.str_recommended_success));
		clearViews();
	}

	public void setIntentHouse(XcfcHouseDetail houseDetail) {
		intentHouseDetail = houseDetail;
		edtIntentHouses.setText(houseDetail.getName());
		edtIntentCity.setText(houseDetail.getArea());
		recomendedBuildingId = houseDetail.getId();
	}

	@SuppressWarnings("deprecation")
	private void createMyDialog(Object[] objects, String houseOrDistrict) {
		if (objects instanceof XcfcDistrict[]) {
			menuDialog = new SelectedDistrictDialog(context, itemClickListener,null,
					R.style.add_dialog, houseOrDistrict, "");
			XcfcDistrict[] distric = (XcfcDistrict[]) objects;
			menuDialog.setDistricts(distric);
			menuDialog.setAdapter();
			menuDialog.lvSelect.setTotalPageCount(1);
			menuDialog.lvSelect.setCurrentPage(1);
			
		} else if (objects instanceof XcfcHouse[]) {
			menuDialog = new SelectedDistrictDialog(context, intentItemClickListener, edgeCallBackHouse, R.style.add_dialog, "", houseOrDistrict);
			XcfcHouse[] house = (XcfcHouse[]) objects;
			menuDialog.setHouses(house);
			menuDialog.setIntentHouseAdapter();
			rlIntentHouseRight.setClickable(true);
			menuDialog.lvSelect.setTotalPageCount(totalPage);
			menuDialog.lvSelect.setCurrentPage(1);
		}

		Window window = menuDialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		WindowManager windowManager = getActivity().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = menuDialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		menuDialog.getWindow().setAttributes(lp);
		menuDialog.setCanceledOnTouchOutside(true);
		menuDialog.show();
	}

	@Background
	void doLoadDataAndBindDataIntentHouse() {
		
		String intentCity = mApp.getCurrCity().getName();
		XcfcHousesResult result = netHandler.postForGetHousesResult(1, intentCity, districtId, "", "", "");
		if (null == result) {
			goBackMainThreadIntentHouse(getString(R.string.error_server_went_wrong), false);
			rlIntentHouseRight.setClickable(true);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThreadIntentHouse(result.getResultMsg(), false);
			rlIntentHouseRight.setClickable(true);
			return;
		}
		houses = result.getPageResult().getHouses();
        totalPage = result.getPageResult().getPageNo();
		goBackMainThreadIntentHouse(result.getResultMsg(), true);
	}

	private void goBackMainThreadIntentHouse(String msg, boolean success) {

		if (success) {
			doBindDataIntentHouse();
		}
	}

	@UiThread
	void doBindDataIntentHouse() {
		if (houses == null || houses.length == 0) {
			Utils.toastMessageForce(getActivity(), getString(R.string.txt_no_matching_house));
			rlIntentHouseRight.setClickable(true);
			return;
		}
		String intentHouseString = edtIntentHouses.getText().toString();
		createMyDialog(houses, intentHouseString);
	}
	
	/**
	 * 清空数据
	 */
	public void clearViews() {
		edtManRecommended.setText("");
		edtContactWay.setText("");
		edtIntentCity.setText("");
		edtIntentHouses.setText("");
		edtRemark.setText("");
	}
}

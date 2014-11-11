package com.movitech.grande.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

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
import com.movitech.grande.net.protocol.XcfcAppointmentResult;
import com.movitech.grande.net.protocol.XcfcDistrictsResult;
import com.movitech.grande.net.protocol.XcfcHousesResult;
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
@SuppressLint("SimpleDateFormat")
@EFragment(R.layout.fragment_recom_appointment)
public class RecomAppointmentFragment extends BaseFragment {

	@ViewById(R.id.txt_intent_houses)
	TextView txtIntentHouses;
	@ViewById(R.id.iv_intent_houses)
	ImageView ivIntentHouses;
	@ViewById(R.id.iv_cancel_contact_way)
	ImageView ivCancelContactWay;
	@ViewById(R.id.txt_appointment_time)
	TextView txtAppointmentTime;
	@ViewById(R.id.txt_man_appointment)
	TextView txtManAppointment;
	@ViewById(R.id.iv_man_appointment)
	ImageView ivManAppointment;
	@ViewById(R.id.txt_intent_city_unit)
	TextView txtIntentCityUnit;
	@ViewById(R.id.edt_contact_way)
	EditText edtContactWay;
	@ViewById(R.id.iv_cancel_remark)
	ImageView ivCancelRemark;
	@ViewById(R.id.iv_intent_note)
	ImageView ivIntentNote;
	@ViewById(R.id.iv_cancel_appointment_time)
	ImageView ivCancelAppointmentTime;
//	@ViewById(R.id.edt_intent_district)
//	EditText edtIntentDistrict;
	@ViewById(R.id.txt_remark)
	TextView txtRemark;
	@ViewById(R.id.iv_cancel_intent_houses)
	ImageView ivCancelIntentHouses;
	@ViewById(R.id.iv_cancel_man_appointment)
	ImageView ivCancelManAppointment;
	@ViewById(R.id.edt_man_appointment)
	EditText edtManAppointment;
	@ViewById(R.id.edt_remark)
	EditText edtRemark;
	@ViewById(R.id.btn_appointment_immediate)
	Button btnAppointmentImmediate;
	@ViewById(R.id.edt_appointment_date)
	TextView edtAppointmentDate;

	@ViewById(R.id.iv_contact_way)
	ImageView ivContactWay;
	@ViewById(R.id.txt_intent_city)
	TextView txtIntentCity;
	@ViewById(R.id.txt_contact_way)
	TextView txtContactWay;
	@ViewById(R.id.edt_intent_city)
	EditText edtIntentCity;
	@ViewById(R.id.iv_intent_city)
	ImageView ivIntentCity;
	@ViewById(R.id.iv_contact_man_appointment)
	ImageView ivContactManAppointment;
	@ViewById(R.id.iv_appointment_time)
	ImageView ivAppointmentTime;
	@ViewById(R.id.edt_intent_houses)
	TextView edtIntentHouses;
	@ViewById(R.id.ll_sheet)
	LinearLayout llSheet;
	@ViewById(R.id.rl_intent_house_right)
	RelativeLayout rlIntentHouseRight;
	
	/**选择日期*/
	@ViewById(R.id.rl_appoint_date)
	RelativeLayout rlAppointDate;
	

	@App
	MainApp mApp;
	@Bean(NetHandler.class)
	INetHandler netHandler;

	int mYear;
	int mMonth;
	int mDay;
	int mHour;
	int mMinute;
	String username = null;
	String usernumber = null;
	String dateAndTime = null;
	String recomendedBuildingId = null;
	String districtId = "";
	boolean isDateSelected = false;
	boolean isIntentHouseing = false;
	XcfcHouseDetail intentHouseDetail = null;
	XcfcCity city = null;
	XcfcDistrict[] districts = null;//获取的区域集合
	XcfcDistrict district = null;//选择的区域信息
	XcfcHouse[] houses = null;
	int currentPage = 1;
	int totalPage;
	boolean isLoadingMore = false;// 标记是否正在加载更多 false---否，true---是
	// 自定义的弹出框类
	SelectedDistrictDialog menuDialog = null;
	
	@AfterViews
	void afterViews() {
		initViews();
	}

	void initViews() {
		//edtAppointmentDate.setFocusable(false);
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
			//edtIntentCity.setText(city.getName());
			//queryDistrict()		
			}
		super.onResume();
	}

	@Click
	void edtIntentCity() {
		
		queryDistrict();
	}

	

	@Click
	void rlIntentHouseRight() {
		doLoadDataAndBindDataIntentHouse();
		rlIntentHouseRight.setClickable(false);
	}


	@Click
	void ivContactManAppointment() {
		startActivityForResult(new Intent(Intent.ACTION_PICK,
				ContactsContract.Contacts.CONTENT_URI), ReqCode.GET_CONTACT);
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
			username = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			Cursor phone = reContentResolverol.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phone.moveToNext()) {
				usernumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				edtManAppointment.setText(username);
				edtContactWay.setText(usernumber);
			}
		}

		if (resultCode == ReqCode.SELECT_CITY) {
			XcfcCity xcfcCity = (XcfcCity) data
					.getSerializableExtra(ExtraNames.XCFC_CITY);
			if (xcfcCity == null)
				return;

			// 清空当前区域信息
			districts = null;
			district = null;
			edtIntentHouses.setText("");
			// 刷新区域信息
			//queryDistrict();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			XcfcDistrict distr = ((SelectedDistrictsAdapter.ViewCache) view
					.getTag()).district;
     		if (distr.getName().equals(getString(R.string.txt_list_header_no_districts))) {
				edtIntentCity.setText(getString(R.string.txt_list_header_no_districts));
				//txtIntentDistrictUnit.setVisibility(View.VISIBLE);
			} else {
				edtIntentCity.setText(distr.getName());
			}
			districtId = distr.getId();
//			if (distr.getName().endsWith(
//					getString(R.string.txt_intent_district_unit))) {
//				//txtIntentDistrictUnit.setVisibility(View.GONE);
//			}
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

	
	//上拉加载更多楼盘列表
	private OnScrollToEdgeCallBack edgeCallBackHouse = new OnScrollToEdgeCallBack() {
		
		@Override
		public void toBottom() {
			if (isLoadingMore) {
				return;
			}
			loadNewForHouses();
		}
	};
	@Background
	void queryDistrict() {
//		XcfcDistrictsResult result = netHandler.postForGetDistrictsResult(city
//				.getName());
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
			//MainActivity_.intent(this).start();
		}
	}
	@UiThread
	 void doBindData() {
		
//		if ("".equals(edtIntentCity.getText().toString()) || districts == null) {
//			Utils.toastMessage(getActivity(),
//					getString(R.string.warning_please_select_city));
//			//清空意向楼盘
//			edtIntentHouses.setText("");
//			return;
//		}

//		if (edtIntentDistrict.getText() == null)
//			return;

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

	Calendar date = Calendar.getInstance(Locale.CHINA);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Click
	void rlAppointDate() {
		isDateSelected = true;
		new DatePickerDialog(getActivity(), d, date.get(Calendar.YEAR),
		date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
	}
	
	/*// 日期选择器
	@Click
	void edtAppointmentDate() {
		isDateSelected = true;
		new DatePickerDialog(getActivity(), d, date.get(Calendar.YEAR),
				date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
	}*/

	// 当点击DatePickerDialog控件的设置按钮时，调用该方法
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDateDisplay();
		}
	};

	TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
		// 同DatePickerDialog控件
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateTimeDisplay();
		}
	};

	/* 更新日期显示 */
	private void updateDateDisplay() {
		edtAppointmentDate.setText(new StringBuilder().append(mYear)
				.append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
				.append("-").append((mDay < 10) ? "0" + mDay : mDay));
		dateAndTime = edtAppointmentDate.getText().toString();
	}

	/* 更新时间显示 */
	private void updateTimeDisplay() {
		edtAppointmentDate.setText(new StringBuilder().append(mHour)
				.append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
		dateAndTime = dateAndTime + edtAppointmentDate.getText().toString();
	}

	ProcessingDialog processingDialog = null;

	@Click
	void btnAppointmentImmediate() {
		if ("".equals(edtManAppointment.getText().toString())) {
			Utils.toastMessageForce(getActivity(), getString(R.string.hint_input_name));
			return;
		}else if ("".equals(edtContactWay.getText().toString())) {
			Utils.toastMessageForce(getActivity(), getString(R.string.hint_input_contactway));
			return;
		}else if ("".equals(edtIntentCity.getText().toString())) {
			Utils.toastMessageForce(getActivity(), getString(R.string.hint_input_city));
			return;
		}else if ("".equals(edtIntentHouses.getText().toString())) {
			Utils.toastMessageForce(getActivity(), getString(R.string.hint_input_house));
			return;
		}else if ("".equals(edtAppointmentDate.getText().toString())) {
			Utils.toastMessageForce(getActivity(), getString(R.string.hint_input_date));
			return;
		}
		{
		/*	if (mApp.getCurrUser() == null) {
				edtIntentCity.setText("");
				edtIntentHouses.setText("");
				edtRemark.setText("");
				LoginActivity_.intent(this).start();
				return;
			}*/
			
			if (!mApp.isLogin()) {
				edtIntentCity.setText("");
				edtIntentHouses.setText("");
				edtRemark.setText("");
				LoginActivity_.intent(this).start();
				return;
			}
		
			String clientName = edtManAppointment.getText().toString();
			String clientPhone = Utils.formatNumberOnlyDigits(edtContactWay.getText().toString());		
			String brokerId = mApp.getCurrUser().getId();
			String bespeakMark = edtRemark.getText().toString();
			String bespeakTime = dateAndTime;

			processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					BackgroundExecutor.cancelAll("queryData", false);
				}
			});
			processingDialog.show();
			doLoadAppointmentRequest(clientName, clientPhone, recomendedBuildingId, brokerId, bespeakMark, bespeakTime);
		}

	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	@Background(id="queryData")
	void doLoadAppointmentRequest(String clientName, String clientPhone,
			String recommendedBuilding, String brokerId, String bespeakMark,
			String bespeakTime) {
		XcfcAppointmentResult appointmentResult = netHandler.postForGetAppointmentResult(clientName, clientPhone, recommendedBuilding, brokerId, bespeakMark, bespeakTime);

		if (appointmentResult == null) {
			goBackMainThreadAppointment(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (!appointmentResult.getResultSuccess()) {
			goBackMainThreadAppointment(appointmentResult.getResultMsg(), false);
			return;
		}
		goBackMainThreadAppointment(appointmentResult.getResultMsg(), true);
	}
     

	void goBackMainThreadAppointment(String msg, boolean success) {
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
		Utils.toastMessageForce(getActivity(), getString(R.string.str_appointment_success));
		clearViews();
	}

	public void setIntentHouse(XcfcHouseDetail houseDetail) {
		intentHouseDetail = houseDetail;
		edtIntentHouses.setText(houseDetail.getName());
		edtIntentCity.setText(houseDetail.getArea());
	    recomendedBuildingId = houseDetail.getId();
	}
	
	@Background
	void doLoadDataAndBindDataIntentHouse() {
		
		String intentCity = mApp.getCurrCity().getName();
			
		XcfcHousesResult result = netHandler.postForGetHousesResult(1, intentCity, districtId, "", "", "");	
		
		if (null == result) {
			rlIntentHouseRight.setClickable(true);
			goBackMainThreadIntentHouse(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			rlIntentHouseRight.setClickable(true);
			goBackMainThreadIntentHouse(result.getResultMsg(), false);
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

	@SuppressWarnings("deprecation")
	private void createMyDialog(Object[] objects, String houseOrDistrict) {
		if (objects instanceof XcfcDistrict[]) {
			menuDialog = new SelectedDistrictDialog(context, itemClickListener, null,
					R.style.add_dialog, houseOrDistrict, "");
			XcfcDistrict[] distric = (XcfcDistrict[]) objects;
			menuDialog.setDistricts(distric);
			menuDialog.setAdapter();
			menuDialog.lvSelect.setTotalPageCount(1);
			menuDialog.lvSelect.setCurrentPage(1);
		} else if (objects instanceof XcfcHouse[]) {
			menuDialog = new SelectedDistrictDialog(context,
					intentItemClickListener,edgeCallBackHouse, R.style.add_dialog, "", houseOrDistrict);
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
	
	/**
	 * 清空数据
	 */
	private void clearViews() {
		edtManAppointment.setText("");
		edtContactWay.setText("");
		edtIntentCity.setText("");
		edtIntentHouses.setText("");
		edtAppointmentDate.setText("");
		edtRemark.setText("");
	}
}

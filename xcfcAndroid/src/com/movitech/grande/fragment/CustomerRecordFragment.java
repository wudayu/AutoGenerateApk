package com.movitech.grande.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.CustomerHistoryAdapter;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.ClientBuildingRelations;
import com.movitech.grande.model.XcfcMyCustomer;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcFavBuildResult;
import com.movitech.grande.net.protocol.XcfcIsCollectResult;
import com.movitech.grande.haerbin.R;

/**
 * 
 * @author Warkey.Song
 * 
 */
@EFragment(R.layout.fragment_customer_record)
public class CustomerRecordFragment extends BaseFragment {

	@ViewById(R.id.lv_customer_record)
	ListView lvCustomerRecord;

	@ViewById(R.id.rl_customer_image)
	RelativeLayout rlCustomerImage;
	@ViewById(R.id.iv_customer_avator)
	ImageView ivCustomerAvator;

	@ViewById(R.id.tv_customer_name)
	TextView tvCustomerName;
	@ViewById(R.id.tv_customer_tel)
	TextView tvCustomerTel;
	@ViewById(R.id.tv_recom_build)
	TextView tvRecomBuild;
	@ViewById(R.id.tv_statu_ok)
	TextView tvStatuOk;
	@ViewById(R.id.tv_average_commission_data)
	TextView tvAverageCommissionData;
	@ViewById(R.id.tv_remain_validity_data)
	TextView tvRemainValidityData;
	@ViewById(R.id.iv_important_client)
	ImageView ivImportantClient;
	@ViewById(R.id.tv_important_client)
	TextView tvImportantClient;
	
	CustomerHistoryAdapter customerHistoryAdapter;
	XcfcMyCustomer customer = null;
	ClientBuildingRelations clientBuildingRelations = null;
	
	boolean isFavFlag = false;
	@Bean(NetHandler.class)
	NetHandler netHandler;
	@App
	MainApp mApp;
	
	String clientId;
	
	int posColor;
	int[] shaps = { R.drawable.shape_customer_avater_bg,
			R.drawable.shape_customer_avater_blue_bg,
			R.drawable.shape_customer_avater_green_bg,
			R.drawable.shape_customer_avater_orange_bg,
			R.drawable.shape_customer_avater_purple_bg,
			R.drawable.shape_customer_avater_red_bg };
	int[] colors = { R.color.shape_customer_avater_bg_color,
			R.color.shape_customer_avater_blue_bg_color,
			R.color.shape_customer_avater_green_bg_color,
			R.color.shape_customer_avater_orange_bg_color,
			R.color.shape_customer_avater_purple_bg_color,
			R.color.shape_customer_avater_red_bg_color };

	public XcfcMyCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(XcfcMyCustomer customer) {
		this.customer = customer;
	}

	public ClientBuildingRelations getClientBuildingRelations() {
		return clientBuildingRelations;
	}

	public void setClientBuildingRelations(
			ClientBuildingRelations clientBuildingRelations) {
		this.clientBuildingRelations = clientBuildingRelations;
	}

	public int getPosColor() {
		return posColor;
	}

	public void setPosColor(int posColor) {
		this.posColor = posColor;
	}

	
	@AfterViews
	void afterViews() {
		rlCustomerImage.setBackgroundResource(shaps[posColor]);
		clientId = customer.getId();
		doLoadIsCollect();
		if ("1".equals(customer.getGender())) {
			ivCustomerAvator.setImageResource(R.drawable.ico_boy);
		} else if ("2".equals(customer.getGender())) {
			ivCustomerAvator.setImageResource(R.drawable.ico_girl);
		} else {
			ivCustomerAvator.setImageResource(R.drawable.ico_boy);
		}
		tvCustomerName.setTextColor(getResources().getColor(colors[posColor]));
		tvCustomerTel.setTextColor(getResources().getColor(colors[posColor]));

		tvCustomerName.setText(customer.getName());
		tvCustomerTel.setText(customer.getPhone());
		if (clientBuildingRelations.getClientStatus() != null) {
			String status = clientStatus(clientBuildingRelations
					.getClientStatus());
			tvStatuOk.setText(status);
		} else
			tvStatuOk.setText("");
		tvRecomBuild.setText(clientBuildingRelations.getBuildingName());
		tvAverageCommissionData.setText(clientBuildingRelations
				.getBuildingRatio());
		int todayCount = getGapCount(clientBuildingRelations.getProtectTime());
		if (todayCount < 0) {
			todayCount = 0;
		}
		if (!isAdded()) {
			return;
		}
		if (clientBuildingRelations.getClientStatus() != null
				&& clientBuildingRelations.getClientStatus().equals(
						context.getString(R.string.client_status_hundred))) {
			tvRemainValidityData.setText("");
		}else
		    tvRemainValidityData.setText(todayCount + getString(R.string.txt_today_unit));

		customerHistoryAdapter = new CustomerHistoryAdapter(context,
				clientBuildingRelations.getHistorys());

		lvCustomerRecord.setAdapter(customerHistoryAdapter);
	}

	/**
	 * 点击是否设为重要客户
	 */
	@Click
	void ivImportantClient() {
		if (!isFavFlag) {
			doLoadImportantClient("0");
		} else {
			doLoadImportantClient("1");
		}
	}
	
	@Background
	void doLoadImportantClient(String isLike) {
		XcfcFavBuildResult xcfcFavBuildResult = netHandler.postForGetFavBuild(
				mApp.getCurrUser().getId(), clientId, isLike, "1");
		if (null == xcfcFavBuildResult) {
			goBackMainThreadFav(getString(R.string.error_server_went_wrong),
					false, isLike);
			return;
		}
		if (xcfcFavBuildResult.getResultSuccess() == false) {
			goBackMainThreadFav(xcfcFavBuildResult.getResultMsg(), false, isLike);
			return;
		}
		// houseDetail = xcfcFavBuildResult.getHouseDetail();
		// 修改成功
		goBackMainThreadFav("", true, isLike);
	}
	
	@UiThread
	void goBackMainThreadFav(String msg, boolean success, String isLike) {
		if (success) {
			if (isLike.equals("0")) {
				isFavFlag = true;
				ivImportantClient.setBackgroundResource(R.drawable.ico_imcustomer_on);
				tvImportantClient.setText(getString(R.string.client_important_on));
				Utils.toastMessageForce(getActivity(), getString(R.string.client_important_succeed));
			}else if (isLike.equals("1")) {
				isFavFlag = false;
				ivImportantClient.setBackgroundResource(R.drawable.ico_imcustomer_off);
				tvImportantClient.setText(getString(R.string.client_important_off));
				Utils.toastMessageForce(getActivity(), getString(R.string.client_important_cancel));
			}
		}
	}
	
	@Background
	void doLoadIsCollect() {
		XcfcIsCollectResult isCollectResult = netHandler.postForGetIsCollect(
				mApp.getCurrUser().getId(), clientId);
		if (null == isCollectResult) {
			goBackMainIsCollect(getString(R.string.error_server_went_wrong),
					false);
			return;
		}
		if (isCollectResult.getResultSuccess() == false) {
			goBackMainIsCollect(isCollectResult.getResultMsg(), false);
			return;
		}
		if (isCollectResult.isObjValue()) {
			goBackMainIsCollect("", true);
		}
	}

	@UiThread
	void goBackMainIsCollect(String msg, boolean succeed) {
		if (succeed) {
			isFavFlag = true;
			if (!isAdded()) {
				return;
			}
			ivImportantClient.setBackgroundResource(R.drawable.ico_imcustomer_on);
			tvImportantClient.setText(getString(R.string.client_important_on));
		}
	}

	private String clientStatus(String statusCode) {
		
		if (!isAdded()) {
			return "";
		}
		if (statusCode.equals(context.getString(R.string.client_status_ten))) {
			return context.getString(R.string.client_status_ten_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_twenty))) {
			return context.getString(R.string.client_status_twenty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_thirty))) {
			return context.getString(R.string.client_status_thirty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_forty))) {
			return context.getString(R.string.client_status_forty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_fifty))) {
			return context.getString(R.string.client_status_fifty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_sixty))) {
			return context.getString(R.string.client_status_sixty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_seventy))) {
			return context.getString(R.string.client_status_seventy_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_hundred))) {
			return context.getString(R.string.client_status_hundred_str);
		}else if (statusCode.equals(context
				.getString(R.string.client_status_minus_ten))) {
			return context.getString(R.string.client_status_minus_ten_str);
			
		}else 
			return context.getString(R.string.client_status_other_str);
		
	}

	/**
	 * 获取两个日期之间的间隔天数
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private int getGapCount(String protectTime) {
		if (protectTime == null) {
			return 0;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
		try {
			Date endDate = sdf.parse(protectTime);
			Date startDate = new Date(System.currentTimeMillis());// 获取当前时间
			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.setTime(startDate);
			fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
			fromCalendar.set(Calendar.MINUTE, 0);
			fromCalendar.set(Calendar.SECOND, 0);
			fromCalendar.set(Calendar.MILLISECOND, 0);

			Calendar toCalendar = Calendar.getInstance();
			toCalendar.setTime(endDate);
			toCalendar.set(Calendar.HOUR_OF_DAY, 0);
			toCalendar.set(Calendar.MINUTE, 0);
			toCalendar.set(Calendar.SECOND, 0);
			toCalendar.set(Calendar.MILLISECOND, 0);

			return (int) ((toCalendar.getTime().getTime() - fromCalendar
					.getTime().getTime()) / (1000 * 60 * 60 * 24));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;

	}
}

package com.movitech.grande.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.CommissionGrantAdapter;
import com.movitech.grande.adapter.CommissionSuccessedAdapter;
import com.movitech.grande.adapter.CommissionWaitAdapter;
import com.movitech.grande.adapter.ViewPagerAdapter;
import com.movitech.grande.adapter.CommissionGrantAdapter.ConfirmApplyCallBack;
import com.movitech.grande.adapter.CommissionWaitAdapter.OnClickApplyCommissionWaitCallBack;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcCommission;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcCommissionApplyResult;
import com.movitech.grande.net.protocol.XcfcCommissionConfirmResult;
import com.movitech.grande.net.protocol.XcfcCommissionResult;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.UnderlinePageIndicator;
import com.movitech.grande.views.UnderlinePageIndicator.OnPageSelectedCallBack;
import com.movitech.grande.haerbin.R;

/**
 * 
 * @author Warkey.Song
 * 
 */
@Deprecated
@EActivity(R.layout.activity_my_commission)
public class MyCommissionActivity extends BaseActivity {
	public static final int PAGE_COUNT = 3;
	@ViewById(R.id.txt_commission_wait)
	TextView txtCommissionWait;
	@ViewById(R.id.txt_commission_grant)
	TextView txtCommissionGrant;
	@ViewById(R.id.txt_commission_ok)
	TextView txtCommissionOk;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.rl_commission_wait)
	RelativeLayout rlCommissionWait;
	@ViewById(R.id.rl_commission_grant)
	RelativeLayout rlCommissionGrant;
	@ViewById(R.id.rl_commission_ok)
	RelativeLayout rlCommissionOk;

	RelativeLayout rlBottom;
	TextView tvApplyCount;
	Button btnClickApply;

	@ViewById(R.id.indicator_fragment_commission)
	UnderlinePageIndicator indicatorFragmentCommission;
	@ViewById(R.id.vp_fragment_commission)
	BaseViewPager vpFragmentCommission;
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;

	View waitView = null;
	View grantView = null;
	View succeedView = null;
	ListView waitListView = null;
	ListView grantListView = null;
	ListView succeedListView = null;
	TextView[] txtCommissionCountArray = null;
	TextView[] txtCommissionArray = null;
	TextView[] txtCommissionUnit = null;
	CheckBox cbCheck = null;

	Bundle bundle = null;
	List<TextView> textViews = null;
	List<XcfcCommission[]> xcfcCommissionList = null;

	int currentItem;
	int applyMoney = 0;
	String[] applyId = null;
	CommissionWaitAdapter waitAdapter = null;
	CommissionGrantAdapter grantAdapter = null;
	CommissionSuccessedAdapter successedAdapter = null;
	boolean[] isLoading = null;

	double[] commisssionCount = null;

	ProcessingDialog processingDialog = null;

	@AfterViews
	void afterViews() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				BackgroundExecutor.cancelAll("queryData", false);
				MyCommissionActivity.this.finish();
			}
		});
		processingDialog.show();

		// 标签
		initializeTab();
		// 获取显示的页
		initCurrentItem();
		// 页面的初始化
		initPages();
		loadForWhichView(currentItem);
	}

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	private void initCurrentItem() {
		bundle = getIntent().getExtras();
		commisssionCount = new double[3];
		if (bundle != null) {
			currentItem = bundle.getInt(ExtraNames.MINE_CURRENT_COMMISSION);
			
			commisssionCount = bundle.getDoubleArray(ExtraNames.MINE_COMMISSION_MONEY);
		} else {
			currentItem = 0;
			commisssionCount[0] = 0;
			commisssionCount[1] = 0;
			commisssionCount[2] = 0;
		}
	}

	private void initializeTab() {
		textViews = new ArrayList<TextView>();
		textViews.add(txtCommissionWait);
		textViews.add(txtCommissionGrant);
		textViews.add(txtCommissionOk);
	}

	private void initPages() {
		xcfcCommissionList = new ArrayList<XcfcCommission[]>();
		isLoading = new boolean[3];
		
		for (int i = 0; i < 3; i++) {
			XcfcCommission[] commArray = null;
			xcfcCommissionList.add(commArray);
			isLoading[i] = false;
		}

		List<View> pagesView = new ArrayList<View>();
		txtCommissionCountArray = new TextView[3];
		txtCommissionArray = new TextView[3];
		txtCommissionUnit = new TextView[3];
		waitView = LayoutInflater.from(context).inflate(
				R.layout.fragment_commission, null);
		grantView = LayoutInflater.from(context).inflate(
				R.layout.fragment_commission, null);
		succeedView = LayoutInflater.from(context).inflate(
				R.layout.fragment_commission, null);
		rlBottom = (RelativeLayout) waitView.findViewById(R.id.rl_bottom);
		cbCheck = (CheckBox) waitView.findViewById(R.id.cb_check);
		tvApplyCount = (TextView) waitView.findViewById(R.id.tv_apply_count);
		btnClickApply = (Button) waitView.findViewById(R.id.btn_click_apply);
		waitListView = (ListView) waitView.findViewById(R.id.lv_commission);
		grantListView = (ListView) grantView.findViewById(R.id.lv_commission);
		succeedListView = (ListView) succeedView
				.findViewById(R.id.lv_commission);
		txtCommissionCountArray[0] = (TextView) waitView
				.findViewById(R.id.tv_commission_count);
		txtCommissionCountArray[1] = (TextView) grantView
				.findViewById(R.id.tv_commission_count);
		txtCommissionCountArray[2] = (TextView) succeedView
				.findViewById(R.id.tv_commission_count);
		txtCommissionArray[0] = (TextView) waitView
				.findViewById(R.id.tv_commission);
		txtCommissionArray[1] = (TextView) grantView
				.findViewById(R.id.tv_commission);
		txtCommissionArray[2] = (TextView) succeedView
				.findViewById(R.id.tv_commission);
		txtCommissionUnit[0] = (TextView) waitView
				.findViewById(R.id.tv_commission_unit);
		txtCommissionUnit[1] = (TextView) grantView
				.findViewById(R.id.tv_commission_unit);
		txtCommissionUnit[2] = (TextView) succeedView
				.findViewById(R.id.tv_commission_unit);
		pagesView.add(waitView);
		pagesView.add(grantView);
		pagesView.add(succeedView);
		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter();
		pagerAdapter.init();
		pagerAdapter.addAll(pagesView);
		vpFragmentCommission.setOffscreenPageLimit(PAGE_COUNT - 1);
		vpFragmentCommission.setAdapter(pagerAdapter);
		vpFragmentCommission.setCurrentItem(currentItem);
		indicatorFragmentCommission.setViewPager(vpFragmentCommission);
		indicatorFragmentCommission
				.setOnPageChangeListener(new VPOnPageChangeListener());
		changeWordIndicator(currentItem);
	}

	@Background
	void doLoadDataAndBindData(int pageNo, String id, String status) {
		int currentPage;
		if (status.equals("30")) {
			currentPage = 0;
			isLoading[0] = true;
		} else if (status.equals("40,50,60")) {
			currentPage = 1;
			isLoading[1] = true;
		} else {
			currentPage = 2;
			isLoading[2] = true;
		}
		isLoading[currentItem] = true;
		XcfcCommissionResult result = netHandler.postForCommissionResult(1, id,
				status);
		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong),
					false, currentPage);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false, currentPage);
			return;
		}
		XcfcCommission[] commissionsArray = result.getPageResult()
				.getCommissions();
		if (commissionsArray == null)
			return;

		xcfcCommissionList.set(currentPage, commissionsArray);
		goBackMainThread(result.getResultMsg(), true, currentPage);

	}

	private void goBackMainThread(String msg, boolean success, int currentPage) {
		dismissProcessingDialog();
		Utils.toastMessage(this, msg);
		if (success) {
			doBindData(currentPage);
		}
	}

	@UiThread
	void doBindData(int currentPage) {
		switch (currentPage) {
		case 0:
			waitAdapter = new CommissionWaitAdapter(context,10);
			waitAdapter.addData(xcfcCommissionList.get(0));
			waitListView.setAdapter(waitAdapter);
			rlBottom.setVisibility(View.VISIBLE);
			if (xcfcCommissionList.get(0) == null)
				return;

			applyId = new String[xcfcCommissionList.get(0).length];
			txtCommissionArray[0]
					.setText(getString(R.string.txt_my_commission_wait_total));
			txtCommissionCountArray[0].setText((int)commisssionCount[currentItem] + "");
			txtCommissionUnit[0].setVisibility(View.VISIBLE);
			tvApplyCount.setText(getString(R.string.txt_my_commission_wait_apply_total) + 0);
			waitAdapter.setApplyCallBack(new OnClickApplyCommissionWaitCallBack() {
						@Override
						public void apply(Map<Integer, Boolean> map) {
							applyMoney = (int) calApplyCommissionCount(map);
							String applyText = getString(R.string.txt_my_commission_wait_apply_total)+ applyMoney;
							SpannableString spannableString = new SpannableString(applyText);
							spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.letter_green_deep_standard)),3, spannableString.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
							tvApplyCount.setText(spannableString);
							if (!map.containsValue(false)) {
								cbCheck.setChecked(true);
							} else {
								cbCheck.setChecked(false);
							}
						}
					});
			btnClickApply.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!(applyMoney > 0)) {
						Utils.toastMessageForce(MyCommissionActivity.this, getString(R.string.txt_my_commission_wait_apply_reminder));
						return;
					}
					btnClickApply();
				}
			});
			cbCheck.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (((CheckBox) v).isChecked()) {
						waitAdapter.setIsSelectedMapAll();
					} else {
						waitAdapter.initDate();
					}
					waitAdapter.notifyDataSetChanged();
				}
			});
			isLoading[0] = false;
			break;
		case 1:
			grantAdapter = new CommissionGrantAdapter(context,
					xcfcCommissionList.get(1));
			grantListView.setAdapter(grantAdapter);
			txtCommissionArray[1].setText(getString(R.string.txt_my_commission_grant_total));
			txtCommissionUnit[1].setVisibility(View.VISIBLE);
			txtCommissionCountArray[1].setText((int)commisssionCount[currentItem] + "");
			isLoading[1] = false;
			grantAdapter.setConfirmApply(new ConfirmApplyCallBack() {

				@Override
				public void confirmApply(String id) {
					// 处理确认提佣的网络申请
					doLoadCommissionConfirmData(mApp.getCurrUser().getId(), id);
				}
			});
			break;
		case 2:
			successedAdapter = new CommissionSuccessedAdapter(context,
					xcfcCommissionList.get(2));
			succeedListView.setAdapter(successedAdapter);
			txtCommissionArray[2].setText(getString(R.string.txt_my_commission_succeed_total));
			txtCommissionCountArray[2].setText((int)commisssionCount[currentItem] + "");
			txtCommissionUnit[2].setVisibility(View.VISIBLE);
			isLoading[2] = false;
			break;
		default:
			break;
		}

	}

	@Background
	void doLoadCommissionConfirmData(String brokerId, String id) {
		XcfcCommissionConfirmResult confirmResult = netHandler
				.postForCheckConfirm(brokerId, id);
		if (null == confirmResult) {
			goBackMainThreadCommissionConfirm(
					getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (confirmResult.getResultSuccess() == false) {
			goBackMainThreadCommissionConfirm(confirmResult.getResultMsg(),
					false);
			return;
		}
		goBackMainThreadCommissionConfirm(confirmResult.getResultMsg(), true);
	}

	private void goBackMainThreadCommissionConfirm(String msg, boolean succeed) {
		if (succeed) {
			doBindAndDataConfirm();
			Utils.toastMessageForce(this, msg);
			return;
		}
		if (msg == null) {
			return;
		}
		Utils.toastMessageForce(this, msg);
	}

	@UiThread
	void doBindAndDataConfirm() {
		doLoadDataAndBindData(1, mApp.getCurrUser().getId(), "40,50,60");
	}

	@Background
	void doLoadCommissionApplyData(String brokerId, String[] id) {
		String ssId = "";
		for (int i = 0; i < id.length; i++) {
			if (!id[i].equals("")) {
				ssId = id[i] + "," + ssId;
			}
		}
		if (ssId.endsWith(",")) {
			ssId = ssId.substring(0, ssId.length() - 1);
		}
		XcfcCommissionApplyResult result = netHandler
				.postForCommissionApplyResult(brokerId, ssId);
		if (null == result) {
			goBackMainThreadCommissionApply(
					getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThreadCommissionApply(result.getResultMsg(), false);
			return;
		}
		goBackMainThreadCommissionApply(result.getResultMsg(), true);
	}

	private void goBackMainThreadCommissionApply(String msg, boolean success) {
		Utils.toastMessageForce(this, msg);
		if (success) {
			doBindDataApplyCommission();
		}
	}

	@UiThread
	void doBindDataApplyCommission() {
		doLoadDataAndBindData(1, mApp.getCurrUser().getId(), "30");
	}
	
	// 计算此次要申请提佣的金额
	private float calApplyCommissionCount(Map<Integer, Boolean> map) {
		float moneyCount = 0;
		XcfcCommission[] commissions = xcfcCommissionList.get(0);
		for (Map.Entry<Integer, Boolean> entry : map.entrySet()) {
			int key = entry.getKey();
			if (map.get(key)) {
				applyId[key] = commissions[key].getId();
				if (commissions[key].getAmount() == null)
					return 0;
				moneyCount = moneyCount
						+ Float.valueOf(commissions[key].getAmount());
			} else {
				applyId[key] = "";
			}
		}
		return moneyCount;
	}

	@Click
	void rlCommissionWait() {
		currentItem = 0;
		vpFragmentCommission.setCurrentItem(0);
	}

	@Click
	void rlCommissionGrant() {
		currentItem = 1;
		vpFragmentCommission.setCurrentItem(1);
	}

	@Click
	void rlCommissionOk() {
		currentItem = 2;
		vpFragmentCommission.setCurrentItem(2);
	}

	@Click
	void ivBack() {
		MyCommissionActivity.this.finish();
	}

	private void btnClickApply() {
		final Dialog dialog = new Dialog(context, R.style.add_dialog);
		dialog.setContentView(R.layout.dialog_apply_commission);
		Button btnOk = (Button) dialog.findViewById(R.id.rl_ok);
		Button btnCancle = (Button) dialog.findViewById(R.id.rl_cancle);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doLoadCommissionApplyData(mApp.getCurrUser().getId(), applyId);
				dialog.dismiss();
			}
		});
		btnCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	class VpFragmentInfoOnPageSelectedCallBack implements
			OnPageSelectedCallBack {
		@Override
		public void onPageSelected(int pos) {
			changeWordIndicator(pos);
		}
	}

	void changeWordIndicator(int pos) {
		for (int i = 0; i < textViews.size(); ++i)
			textViews.get(i).setTextColor(
					getResources().getColor(
							i == pos ? R.color.col_words_active
									: R.color.col_words_inactive));
	}

	class VPOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int pos) {
			currentItem = pos;
			if (xcfcCommissionList.get(pos) == null) {

				loadForWhichView(pos);
			}
			changeWordIndicator(pos);
		}
	}

	void loadForWhichView(int currentItem) {
		switch (currentItem) {
		case 0:
			if (isLoading[0])
				return;
			doLoadDataAndBindData(1, mApp.getCurrUser().getId(), "30");
			break;
		case 1:
			if (isLoading[1])
				return;
			doLoadDataAndBindData(1, mApp.getCurrUser().getId(), "40,50,60");
			break;
		case 2:
			if (isLoading[2])
				return;
			doLoadDataAndBindData(1, mApp.getCurrUser().getId(), "100");
			break;
		default:
			break;
		}
	}
}

package com.movitech.grande.fragment;

import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.adapter.CommissionWaitAdapter;
import com.movitech.grande.adapter.CommissionWaitAdapter.OnClickApplyCommissionWaitCallBack;
import com.movitech.grande.constant.ApproveState;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcCommission;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcCommissionApplyResult;
import com.movitech.grande.net.protocol.XcfcCommissionResult;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.views.ProcessingDialog;


/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月19日 下午4:27:29 类说明
 */
@EFragment(R.layout.fragment_commission)
public class CommissionWaitFragment extends BaseFragment {
	@ViewById(R.id.tv_commission_count)
	TextView tvCommissionCount;
	@ViewById(R.id.tv_commission)
	TextView tvCommission;
	@ViewById(R.id.tv_commission_unit)
	TextView tvCommissionUnit;
	@ViewById(R.id.rl_bottom)
	RelativeLayout rlBottom;
	@ViewById(R.id.tv_apply_count)
	TextView tvApplyCount;
	@ViewById(R.id.cb_check)
	CheckBox cbCheck;
	@ViewById(R.id.btn_click_apply)
	Button btnClickApply;

	@ViewById(R.id.lv_commission)
	LoadDataListView lvCommission;
	ProcessingDialog processingDialog = null;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;
	View waitCommissionLoadMore = null;
	XcfcCommission[] commissionsArray = null;
	CommissionWaitAdapter waitAdapter = null;
	String[] applyId = null;
	int applyMoney = 0;
	
	String commissionWaitCount;
	boolean isLoadIng = false;
	int totalSize;
	int currentPage;
	int totalPage;

	XcfcUser user = null;
	@AfterViews
	void afterViews() {
		
		waitCommissionLoadMore = getActivity().getLayoutInflater().inflate(
				R.layout.item_loading, null);

		waitCommissionLoadMore.setOnClickListener(null);
		lvCommission.addFooterView(waitCommissionLoadMore);
		showProgressDialog();
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

	private void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	@Background
	void doLoadDataAndBindData() {
		isLoadIng = true;
		XcfcCommissionResult result = netHandler.postForCommissionResult(1,
				user == null ? mApp.getCurrUser().getId() : user.getId(), "30");
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
		commissionWaitCount = result.getPageResult().getAmounts();
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
		waitAdapter = new CommissionWaitAdapter(context, totalSize);
		waitAdapter.addData(commissionsArray);
		lvCommission.setAdapter(waitAdapter);
		lvCommission.setTotalPageCount(totalPage);
		lvCommission.setCurrentPage(1);
		rlBottom.setVisibility(View.VISIBLE);
		applyId = new String[totalSize];
		tvCommission.setText(getString(R.string.txt_my_commission_wait_total));
		//tvCommissionCount.setText(commissionWaitCount);
		tvCommissionCount.setText(Math.round(Double.valueOf(commissionWaitCount)) + "");
		tvCommissionUnit.setVisibility(View.VISIBLE);
		tvApplyCount.setText(getString(R.string.txt_my_commission_wait_apply_total) + 0);
		waitAdapter.setApplyCallBack(new OnClickApplyCommissionWaitCallBack() {
			@Override
			public void apply(Map<Integer, Boolean> map) {
				applyMoney = (int) calApplyCommissionCount(map);
				String applyText = getString(R.string.txt_my_commission_wait_apply_total) + applyMoney;
				SpannableString spannableString = new SpannableString(applyText);
				spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.letter_green_deep_standard)), 3, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
					Utils.toastMessageForce(getActivity(), getString(R.string.txt_my_commission_wait_apply_reminder));
					return;
				} 
				if(mApp.getCurrUser().getApproveState() != null && !mApp.getCurrUser().getApproveState().equals(ApproveState.PASSED))
				{
					Utils.toastMessageForce(getActivity(), getString(R.string.txt_my_commission_wait_apply_no_certi));
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

	}
	XcfcCommissionResult commissionResult;
	@Background
	void loadNewForCommission() {
		if (isLoadIng)
			return;
		isLoadIng = true;
		currentPage = lvCommission.getCurrentPage() + 1;
		commissionResult = netHandler.postForCommissionResult(currentPage,  user == null ? mApp.getCurrUser()
				.getId() : user.getId(), "30");
		boolean ret = commissionResult == null
				|| !commissionResult.getResultSuccess();
		if (!ret) {
			addCommissionItems();
		}
		isLoadIng = false;
	}
	@UiThread
	void addCommissionItems() {
		waitAdapter.addItems(commissionResult.getPageResult().getCommissions());
		lvCommission.setCurrentPage(currentPage);
	}
	// 计算此次要申请提佣的金额
	private float calApplyCommissionCount(Map<Integer, Boolean> map) {
		float moneyCount = 0;
		List<XcfcCommission> commissionList = waitAdapter.getCommissions();
		if (commissionList == null) {
			return 0;
		}
		//XcfcCommission[] commissions = commissionsArray;
		XcfcCommission[] commissions = new XcfcCommission[commissionList.size()];
		for (int i = 0; i < commissions.length; i++) {
			commissions[i] = commissionList.get(i);
		}
		for (Map.Entry<Integer, Boolean> entry : map.entrySet()) {
			int key = entry.getKey();
			if (map.get(key)) {
				applyId[key] = commissions[key].getId();
				if (commissions[key].getAmount() == null)
					return 0;
				moneyCount = moneyCount + Float.valueOf(commissions[key].getAmount());
			} else {
				applyId[key] = "";
			}
		}
		return moneyCount;
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
		XcfcCommissionApplyResult result = netHandler.postForCommissionApplyResult(brokerId, ssId);
		if (null == result) {
			goBackMainThreadCommissionApply(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThreadCommissionApply(result.getResultMsg(), false);
			return;
		}
		goBackMainThreadCommissionApply(result.getResultMsg(), true);
	}

	private void goBackMainThreadCommissionApply(String msg, boolean success) {
		if (success) {
			doBindDataApplyCommission();
		}
	}

	@UiThread
	void doBindDataApplyCommission() {
		doLoadDataAndBindData();
	}
	
	public void reLoadData(){
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

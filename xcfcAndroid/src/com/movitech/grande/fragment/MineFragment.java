package com.movitech.grande.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.ApproveState;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ImageLoaderHelper;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcBrokerDetail;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcBrokerDetailResult;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.CommissionActivity_;
import com.movitech.grande.activity.IntegralDetailActivity_;
import com.movitech.grande.activity.MineSettingActivity_;
import com.movitech.grande.activity.MineTeamActivity_;
import com.movitech.grande.activity.MyCustomerActivity_;
import com.movitech.grande.activity.MyFocusBuildActivity_;
import com.movitech.grande.sp.UserSP_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 10, 2014 10:54:56 AM
 * @Description: This is David Wu's property.
 * 
 **/
@EFragment(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {

	@ViewById(R.id.ll_certification_status)
	LinearLayout rlCertificationStatus;
	@ViewById(R.id.ll_level_status)
	LinearLayout llLevelStatus;
	@ViewById(R.id.ll_current_place)
	LinearLayout llCurrentPlace;
  
	@ViewById(R.id.rl_block)
	RelativeLayout rlBlock;
	
	@ViewById(R.id.rl_my_team)
	RelativeLayout rlMyTeam;
	@ViewById(R.id.iv_my_team)
	ImageView ivMyTeam;
	@ViewById(R.id.rl_name_detail)
	LinearLayout rlNameDetail;
	@ViewById(R.id.iv_user_image)
	ImageView ivUserImage;
	@ViewById(R.id.txt_count_sell_successed)
	TextView txtCountSellSuccessed;
	@ViewById(R.id.ll_earned_commission)
	LinearLayout llEarnedCommission;
	@ViewById(R.id.iv_my_score)
	ImageView ivMyScore;
	@ViewById(R.id.rl_my_score)
	RelativeLayout rlMyScore;
	@ViewById(R.id.ll_grant_commission)
	LinearLayout llGrantCommission;
	@ViewById(R.id.ll_recommend_confirmed)
	RelativeLayout llRecommendConfirmed;
	@ViewById(R.id.ll_sell_successed)
	RelativeLayout llSellSuccessed;
	@ViewById(R.id.rl_personal_info)
	RelativeLayout rlPersonalInfo;
	@ViewById(R.id.iv_my_attention)
	ImageView ivMyAttention;
	@ViewById(R.id.rl_current_place)
	LinearLayout rlCurrentPlace;
	@ViewById(R.id.iv_my_score_indicator)
	ImageView ivMyScoreIndicator;
	@ViewById(R.id.ll_client_block_fragment_mine)
	LinearLayout llClientBlockFragmentMine;
	@ViewById(R.id.txt_count_recommend_successed)
	TextView txtCountRecommendSuccessed;
	@ViewById(R.id.txt_count_importent_client)
	TextView txtCountImportentClient;
	@ViewById(R.id.txt_current_position)
	TextView txtCurrentPosition;
	@ViewById(R.id.rl_my_attention)
	RelativeLayout rlMyAttention;
	@ViewById(R.id.txt_count_sea_house)
	TextView txtCountSeaHouse;
	@ViewById(R.id.txt_grant_commission)
	TextView txtGrantCommission;
	@ViewById(R.id.ll_importent_client)
	RelativeLayout llImportentClient;
	@ViewById(R.id.ll_sea_house)
	RelativeLayout llSeaHouse;
	@ViewById(R.id.txt_count_recommend_confirmed)
	TextView txtCountRecommendConfirmed;
	@ViewById(R.id.ll_recommend_successed)
	RelativeLayout llRecommendSuccessed;
	@ViewById(R.id.rl_user_image)
	RelativeLayout rlUserImage;
	@ViewById(R.id.txt_commission_total)
	TextView txtCommissionTotal;
	@ViewById(R.id.txt_certification_status)
	TextView txtCertificationStatus;
	@ViewById(R.id.txt_earned_commission)
	TextView txtEarnedCommission;
	@ViewById(R.id.txt_wait_commission)
	TextView txtWaitCommission;
	@ViewById(R.id.rl_commission_info)
	LinearLayout rlCommissionInfo;
	@ViewById(R.id.ll_wait_commission)
	LinearLayout llWaitCommission;
	@ViewById(R.id.txt_integral)
	TextView txtIntegral;
	@ViewById(R.id.iv_certification_status)
	ImageView ivCertificationStatus;
	@ViewById(R.id.txt_level_status)
	TextView txtLevelStatus;
	@ViewById(R.id.txt_name)
	TextView txtName;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@Pref
	UserSP_ userSP;
	@App
	MainApp mApp;

	XcfcBrokerDetail brokerDetail = null;
	XcfcUser broker = null;
	Bundle bundle = null;
	boolean isLoadBrokerInfoIng = false;

	@AfterViews
	void afterViews() {
		setBundleInt();

		broker = mApp.getCurrUser();
		if (broker != null)
			doLoadDataAndBindData();
	}

	@Override
	public void onResume() {
		if (broker != null && !isLoadBrokerInfoIng) {
			doLoadDataAndBindData();
		} 
		super.onResume();
	}

	public void recomToMineInitData() {
		if (broker != null && !isLoadBrokerInfoIng) {
			doLoadDataAndBindData();
		} 
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == ReqCode.SIGN_IN) {
			// Do Refresh
			broker = mApp.getCurrUser();
			doLoadDataAndBindData();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Background
	void doLoadDataAndBindData() {
		isLoadBrokerInfoIng = true;
		XcfcBrokerDetailResult result = netHandler.postForGetBrokerDetail(broker.getId());

		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}

		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		brokerDetail = result.getBrokerDetail();

		goBackMainThread(result.getResultMsg(), true);
	}

	private void goBackMainThread(String msg, boolean success) {
		isLoadBrokerInfoIng = false;
		Utils.toastMessage(this.getActivity(), msg);

		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		mApp.setBrokerDetail(brokerDetail);
		// 头像图片部分
		// TODO 这里从服务器获取头像目前一直是null或者""，所以头像显示不出来，暂时使用本地头像
		if (null != brokerDetail.getPhotosrc() && !"".equals(brokerDetail.getPhotosrc()))
			imageUtils.loadHeaderImage(brokerDetail.getPhotosrc(), ivUserImage);
		else
			imageUtils.loadHeaderImage(ImageLoaderHelper.URI_PREFIX_FILE + userSP.latestUserHeaderSrc().get(), ivUserImage);
		// 认证部分
		if (ApproveState.UNCERTIFICATE == brokerDetail.getApproveState()) {
			txtCertificationStatus.setText(getString(R.string.hint_approve_null));
			ivCertificationStatus.setBackgroundResource(R.drawable.ico_certification_no);
		} else if (ApproveState.PASSED.equals(brokerDetail.getApproveState())) {
			txtCertificationStatus.setText(getString(R.string.hint_approve_passed));
			ivCertificationStatus.setBackgroundResource(R.drawable.ico_certification_ok);
			userSP.currUserApproveState().put(brokerDetail.getApproveState());
		} else if (ApproveState.WAITFOR.equals(brokerDetail.getApproveState())) {
			txtCertificationStatus.setText(getString(R.string.hint_approve_waitfor));
			ivCertificationStatus.setBackgroundResource(R.drawable.ico_certification_wait);
			userSP.currUserApproveState().put(brokerDetail.getApproveState());
		} else if (ApproveState.UNPASS.equals(brokerDetail.getApproveState())) {
			txtCertificationStatus.setText(getString(R.string.hint_approve_unpass));
			ivCertificationStatus.setBackgroundResource(R.drawable.ico_certification_no);
			userSP.currUserApproveState().put(brokerDetail.getApproveState());
		}
		// 等级部分
		String level = brokerDetail.getLevel();
		txtLevelStatus.setText(level);
		// 排名部分
		txtCurrentPosition.setText("" + brokerDetail.getRanking());
		// 名称部分
		txtName.setText("" + (ApproveState.PASSED.equals(brokerDetail.getApproveState()) ? brokerDetail.getName() : brokerDetail.getScreenName()));
		// 佣金部分
		double[] commission = brokerDetail.getBrokerageNum();
		if (commission != null) {
			txtWaitCommission.setText("" + Math.round(brokerDetail.getBrokerageNum()[0]));
			txtGrantCommission.setText("" + Math.round(brokerDetail.getBrokerageNum()[1]));
			txtEarnedCommission.setText("" + Math.round(brokerDetail.getBrokerageNum()[2]));
			double total = 0.0;
			for (int i = 0; i < brokerDetail.getBrokerageNum().length; ++i)
				total += brokerDetail.getBrokerageNum()[i];
			txtCommissionTotal.setText("" + Math.round(total));
		} else {
			txtWaitCommission.setText("" + 0);
			txtGrantCommission.setText("" + 0);
			txtEarnedCommission.setText("" + 0);
			txtCommissionTotal.setText("" + 0);
		}
		// 客户部分
		int[] userNums = brokerDetail.getUserNum();
		if (userNums != null) {
			txtCountRecommendConfirmed.setText("" + brokerDetail.getUserNum()[0]);
			txtCountRecommendSuccessed.setText("" + brokerDetail.getUserNum()[1]);
			txtCountImportentClient.setText("" + brokerDetail.getUserNum()[2]);
			txtCountSeaHouse.setText("" + brokerDetail.getUserNum()[3]);
			txtCountSellSuccessed.setText("" + brokerDetail.getUserNum()[4]);
		} else {
			txtCountRecommendConfirmed.setText("" + 0);
			txtCountRecommendSuccessed.setText("" + 0);
			txtCountImportentClient.setText("" + 0);
			txtCountSeaHouse.setText("" + 0);
			txtCountSellSuccessed.setText("" + 0);
		}
		// 积分部分
		txtIntegral.setText(brokerDetail.getIntegral() + getString(R.string.txt_hint_integral));
	}

	private void setBundleInt() {
		bundle = new Bundle();
	}

	// 我的客户点击事件组
	@Click
	void llRecommendConfirmed() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 4);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, "");
		Intent intent = new Intent(getActivity(), MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llRecommendSuccessed() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 3);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, "");
		Intent intent = new Intent(getActivity(), MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llSeaHouse() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 1);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, "");
		Intent intent = new Intent(getActivity(), MyCustomerActivity_.class);		
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llSellSuccessed() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 2);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, "");
		Intent intent = new Intent(getActivity(), MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llImportentClient() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 0);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, "");
		Intent intent = new Intent(getActivity(), MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	// 我的佣金点击事件组
	@Click
	void llWaitCommission() {
		Bundle bundle = new Bundle();
		bundle.putInt(ExtraNames.MINE_CURRENT_COMMISSION, 0);
		bundle.putString(ExtraNames.ORG_TO_COMMISSION, "");
		if(brokerDetail != null){
		    bundle.putDoubleArray(ExtraNames.MINE_COMMISSION_MONEY, brokerDetail.getBrokerageNum());
		}
		
		Intent intent = new Intent(context, CommissionActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llGrantCommission() {
		Bundle bundle = new Bundle();
		bundle.putInt(ExtraNames.MINE_CURRENT_COMMISSION, 1);
		bundle.putString(ExtraNames.ORG_TO_COMMISSION, "");
		if (brokerDetail != null) {
			bundle.putDoubleArray(ExtraNames.MINE_COMMISSION_MONEY, brokerDetail.getBrokerageNum());
		}
		
		Intent intent = new Intent(context, CommissionActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llEarnedCommission() {
		Bundle bundle = new Bundle();
		bundle.putInt(ExtraNames.MINE_CURRENT_COMMISSION, 2);
		bundle.putString(ExtraNames.ORG_TO_COMMISSION, "");
		if(brokerDetail != null){
			 bundle.putDoubleArray(ExtraNames.MINE_COMMISSION_MONEY, brokerDetail.getBrokerageNum());
		}
		
		Intent intent = new Intent(context, CommissionActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void ivUserImage() {
			MineSettingActivity_.intent(getActivity()).startForResult(
					ReqCode.SIGN_OUT);		
	}

	@Click
	void rlMyScore() {
		IntegralDetailActivity_.intent(getActivity()).start();
	}

	@Click
	void rlMyAttention() {
		MyFocusBuildActivity_.intent(getActivity()).start();
	}
	
	@Click
	void rlMyTeam(){
		MineTeamActivity_.intent(getActivity()).start();
	}
	
	/**
	 * 总机构页面
	 */
	public void organizationViews() {
		rlCertificationStatus.setVisibility(View.GONE);
		llLevelStatus.setVisibility(View.GONE);
		llCurrentPlace.setVisibility(View.GONE);
		rlMyTeam.setVisibility(View.VISIBLE);
		rlMyScore.setVisibility(View.GONE);
		rlBlock.setVisibility(View.GONE);
	}
	
	/**
	 * 经纪人的页面
	 */
	public void brokerViews() {
		rlCertificationStatus.setVisibility(View.VISIBLE);
		llLevelStatus.setVisibility(View.VISIBLE);
		llCurrentPlace.setVisibility(View.VISIBLE);
		rlMyTeam.setVisibility(View.GONE);
		rlMyScore.setVisibility(View.VISIBLE);
		rlBlock.setVisibility(View.GONE);
	}
	
	/**
	 * 子机构页面
	 */
	public void subOrganizationViews(){
		rlCertificationStatus.setVisibility(View.GONE);
		llLevelStatus.setVisibility(View.GONE);
		llCurrentPlace.setVisibility(View.GONE);
		rlMyTeam.setVisibility(View.GONE);
		rlMyScore.setVisibility(View.GONE);
		rlBlock.setVisibility(View.VISIBLE);
	}
}

package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.OrgBrokerAccountStatus;
import com.movitech.grande.constant.SubOrgAccountStatus;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcOrgBrokerDetail;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.BaseResult;
import com.movitech.grande.net.protocol.XcfcOrgBrokerDetailResult;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.CommissionActivity_;
import com.movitech.grande.activity.MyCustomerActivity_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月27日 上午9:25:34
 * 类说明
 */
@EActivity(R.layout.activity_institution_infomation)
public class SubInstitutionInfoActivity extends BaseActivity{

	@ViewById(R.id.tb_selected)
	ToggleButton tbSelected;
	@ViewById(R.id.txt_tb_status)
	TextView txtTbStatus;
	@ViewById(R.id.rl_personal_info)
	RelativeLayout rlPersonalInfo;	
	
	@ViewById(R.id.rl_user_image)
	RelativeLayout rlUserImage;
	@ViewById(R.id.iv_user_image)
	ImageView ivUserImage;
	@ViewById(R.id.txt_name)
	TextView txtName;
	@ViewById(R.id.txt_name_institution)
	TextView txtNameInstitution;
	
	//佣金
	@ViewById(R.id.rl_commission_info)
	LinearLayout rlCommissionInfo;

	@ViewById(R.id.txt_commission_total)
	TextView txtCommissionTotal;
	@ViewById(R.id.txt_wait_commission)
	TextView txtWaitCommission;
	@ViewById(R.id.txt_grant_commission)
	TextView txtGrantCommission;
	@ViewById(R.id.txt_earned_commission)
	TextView txtEarnedCommission;
	
	@ViewById(R.id.ll_wait_commission)
	LinearLayout llWaitCommission;
	@ViewById(R.id.ll_earned_commission)
	LinearLayout llEarnedCommission;
	@ViewById(R.id.ll_grant_commission)
	LinearLayout llGrantCommission;
	
	//客户
	@ViewById(R.id.ll_client_block_fragment_mine)
	LinearLayout llClientBlockFragmentMine;
	
	@ViewById(R.id.ll_recommend_confirmed)
	RelativeLayout llRecommendConfirmed;
	@ViewById(R.id.ll_sell_successed)
	RelativeLayout llSellSuccessed;
	@ViewById(R.id.ll_importent_client)
	RelativeLayout llImportentClient;
	@ViewById(R.id.ll_sea_house)
	RelativeLayout llSeaHouse;
	@ViewById(R.id.ll_recommend_successed)
	RelativeLayout llRecommendSuccessed;
	
	@ViewById(R.id.txt_count_sell_successed)
	TextView txtCountSellSuccessed;
	@ViewById(R.id.txt_count_recommend_successed)
	TextView txtCountRecommendSuccessed;
	@ViewById(R.id.txt_count_importent_client)
	TextView txtCountImportentClient;
	@ViewById(R.id.txt_count_recommend_confirmed)
	TextView txtCountRecommendConfirmed;
	@ViewById(R.id.txt_count_sea_house)
	TextView txtCountSeaHouse;
	
	//账户状态
	@ViewById(R.id.rl_account_status)
	RelativeLayout rlAccountStatus;
	
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	Bundle bundle = null;
	XcfcOrgBrokerDetail brokerDetail = null;
	String orgBrokerId = null;
	
    boolean isSwitchStatus = false;
    
    boolean isCheckFirst = true;
    
	@AfterViews
	void afterViews(){	
		setBundleInt();
		tbSelected.setOnCheckedChangeListener(changeListener);		
		initData();
		doLoadDataInstitution();
	}
	
	OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {	
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			// TODO Auto-generated method stub
			if (isChecked) {
				if (!isSwitchStatus) {
					doLoadSubOrgStatus(SubOrgAccountStatus.SUB_ORG_OPEN);
				}
				txtTbStatus.setTextColor(Color.parseColor("#FF70DB64"));
				txtTbStatus.setText(getString(R.string.txt_tb_on));
				// 选中
			}else{
				//未选中
				if (!isSwitchStatus) {
					doLoadSubOrgStatus(SubOrgAccountStatus.SUB_ORG_CLOSE);
				}
				txtTbStatus.setTextColor(Color.parseColor("#FFA6A6A6"));
				txtTbStatus.setText(getString(R.string.txt_tb_off));
			}
		}
	};
	
	private void setBundleInt() {
		bundle = new Bundle();
	}

	@Background
	void doLoadSubOrgStatus(String status){
		isSwitchStatus = true;
		
		BaseResult result = netHandler.postForGetSubOrgStatus(orgBrokerId, status);
		if (null == result) {
			goBackMainThreadTogBtn(status, false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThreadTogBtn(status, false);
			return;
		}
		goBackMainThreadTogBtn(status, true);
	}
	
	@UiThread
	void goBackMainThreadTogBtn(String msg, boolean success){
		isSwitchStatus = false;	
		if (success) {
			if (msg.equals(SubOrgAccountStatus.SUB_ORG_OPEN)) {
				if (isCheckFirst) {
					isCheckFirst = false;
					return;
				}
				Utils.toastMessageForce(SubInstitutionInfoActivity.this, "账号已开启");
			}else if (msg.equals(SubOrgAccountStatus.SUB_ORG_CLOSE)) {
				Utils.toastMessageForce(SubInstitutionInfoActivity.this, "账号已禁用");
			}
		}else {
			Utils.toastMessageForce(SubInstitutionInfoActivity.this, "操作失败");
		}
	}
	
	private void initData() {
		if (getIntent().getStringExtra(ExtraNames.TEAM_SUB_ID) != null) {
			orgBrokerId = getIntent().getStringExtra(ExtraNames.TEAM_SUB_ID);
		}
      }

	
	@Background
	void doLoadDataInstitution(){
		XcfcOrgBrokerDetailResult detailResult = netHandler.postForGetOrgBrokerInfo(orgBrokerId);
		if (null == detailResult) {
			goBackMainThread("", false);
			return;
		}
		if (detailResult.getResultSuccess() == false) {
			goBackMainThread("", false);
			return;
		}
		brokerDetail = detailResult.getOrgBrokerDetail();
		goBackMainThread("", true);
	
	}
	
	private void goBackMainThread(String string, boolean success) {
		if (success) {
			doBindData();
		}
	}

	@UiThread
	void doBindData() {
		txtName.setText("张晓丽");
		txtNameInstitution.setText("(中原地产)");
	    

		// 头像图片部分
		// TODO 这里从服务器获取头像目前一直是null或者""，所以头像显示不出来，暂时使用本地头像
		if (null != brokerDetail.getPhotosrc() && !"".equals(brokerDetail.getPhotosrc()))
			imageUtils.loadHeaderImage(brokerDetail.getPhotosrc(), ivUserImage);
		/*else
			imageUtils.loadHeaderImage(ImageLoaderHelper.URI_PREFIX_FILE + userSP.latestUserHeaderSrc().get(), ivUserImage);*/
		// 名称部分
		txtName.setText(brokerDetail.getName());
		// 佣金部分
		double[] commission = brokerDetail.getBrokerageNum();
		if (commission != null) {
			txtWaitCommission.setText(""
					+ Math.round(brokerDetail.getBrokerageNum()[0]));
			txtGrantCommission.setText(""
					+ Math.round(brokerDetail.getBrokerageNum()[1]));
			txtEarnedCommission.setText(""
					+ Math.round(brokerDetail.getBrokerageNum()[2]));
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
			txtCountRecommendConfirmed.setText(""
					+ brokerDetail.getUserNum()[0]);
			txtCountRecommendSuccessed.setText(""
					+ brokerDetail.getUserNum()[1]);
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
		
		if (brokerDetail.getIsDisabled() == OrgBrokerAccountStatus.ORG_BROKER_CLOSE) {
			tbSelected.setChecked(false);
			txtTbStatus.setTextColor(Color.parseColor("#FFA6A6A6"));
			txtTbStatus.setText(getString(R.string.txt_tb_off));
		}else if (brokerDetail.getIsDisabled() == OrgBrokerAccountStatus.ORG_BROKER_OPEN) {
			tbSelected.setChecked(true);
			txtTbStatus.setTextColor(Color.parseColor("#FF70DB64"));
			txtTbStatus.setText(getString(R.string.txt_tb_on));
		}
	}
	
	@Click
	void ivBack(){
		this.finish();
	}

	// 我的客户点击事件组
	@Click
	void llRecommendConfirmed() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 4);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, orgBrokerId);
		Intent intent = new Intent(SubInstitutionInfoActivity.this,
				MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llRecommendSuccessed() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 3);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, orgBrokerId);
		Intent intent = new Intent(SubInstitutionInfoActivity.this,
				MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llSeaHouse() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 1);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, orgBrokerId);
		Intent intent = new Intent(SubInstitutionInfoActivity.this,
				MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llSellSuccessed() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 2);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, orgBrokerId);
		Intent intent = new Intent(SubInstitutionInfoActivity.this,
				MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llImportentClient() {
		bundle.putInt(ExtraNames.MINE_CURRENT_CUSTOMER, 0);
		bundle.putString(ExtraNames.ORG_TO_CUSTOMER, orgBrokerId);
		Intent intent = new Intent(SubInstitutionInfoActivity.this,
				MyCustomerActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	// 我的佣金点击事件组
	@Click
	void llWaitCommission() {
		Bundle bundle = new Bundle();
		bundle.putInt(ExtraNames.MINE_CURRENT_COMMISSION, 0);
		bundle.putString(ExtraNames.ORG_TO_COMMISSION, orgBrokerId);
		if (brokerDetail != null) {
			bundle.putDoubleArray(ExtraNames.MINE_COMMISSION_MONEY,
					brokerDetail.getBrokerageNum());
		}

		Intent intent = new Intent(context, CommissionActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llGrantCommission() {
		Bundle bundle = new Bundle();
		bundle.putInt(ExtraNames.MINE_CURRENT_COMMISSION, 1);
		bundle.putString(ExtraNames.ORG_TO_COMMISSION, orgBrokerId);
		if (brokerDetail != null) {
			bundle.putDoubleArray(ExtraNames.MINE_COMMISSION_MONEY,
					brokerDetail.getBrokerageNum());
		}

		Intent intent = new Intent(context, CommissionActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Click
	void llEarnedCommission() {
		Bundle bundle = new Bundle();
		bundle.putInt(ExtraNames.MINE_CURRENT_COMMISSION, 2);
		bundle.putString(ExtraNames.ORG_TO_COMMISSION, orgBrokerId);
		if (brokerDetail != null) {
			bundle.putDoubleArray(ExtraNames.MINE_COMMISSION_MONEY,
					brokerDetail.getBrokerageNum());
		}
		Intent intent = new Intent(context, CommissionActivity_.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
}

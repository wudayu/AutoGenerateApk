package com.movitech.grande.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.MainActivityPageAdapter;
import com.movitech.grande.constant.BrokerType;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.MobileType;
import com.movitech.grande.constant.NewsCatagoryId;
import com.movitech.grande.constant.PushMessageKeyCode;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.constant.Rolling;
import com.movitech.grande.fragment.HousesFragment;
import com.movitech.grande.fragment.InfoFragment;
import com.movitech.grande.fragment.MineFragment;
import com.movitech.grande.fragment.RecomFragment;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.model.XcfcUser;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcStringResult;
import com.movitech.grande.net.protocol.XcfcVersionResult;
import com.movitech.grande.utils.Global;
import com.movitech.grande.utils.NetWorkUtil;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.CertificationActivity_;
import com.movitech.grande.activity.LoginActivity_;
import com.movitech.grande.fragment.HousesFragment_;
import com.movitech.grande.fragment.InfoFragment_;
import com.movitech.grande.fragment.MineFragment_;
import com.movitech.grande.fragment.RecomFragment_;
import com.movitech.grande.sp.NewsSP_;
import com.movitech.grande.sp.UserSP_;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity
 {
	public static final int PAGE_COUNT = 4;

	@ViewById(R.id.iv_recom_indicator)
	ImageView ivRecomIndicator;
	@ViewById(R.id.ll_bottom)
	LinearLayout llBottom;
	@ViewById(R.id.rl_mine)
	RelativeLayout rlMine;
	@ViewById(R.id.iv_info_indicator)
	ImageView ivInfoIndicator;
	@ViewById(R.id.rl_info)
	RelativeLayout rlInfo;
	@ViewById(R.id.iv_mine)
	ImageView ivMine;
	@ViewById(R.id.view_pager)
	ViewPager viewPager;
	@ViewById(R.id.iv_houses)
	ImageView ivHouses;
	@ViewById(R.id.txt_houses)
	TextView txtHouses;
	@ViewById(R.id.rl_houses)
	RelativeLayout rlHouses;
	@ViewById(R.id.iv_info)
	ImageView ivInfo;
	@ViewById(R.id.iv_recom)
	ImageView ivRecom;
	@ViewById(R.id.txt_info)
	TextView txtInfo;
	@ViewById(R.id.txt_mine)
	TextView txtMine;
	@ViewById(R.id.iv_houses_indicator)
	ImageView ivHousesIndicator;
	@ViewById(R.id.rl_recom)
	RelativeLayout rlRecom;
	@ViewById(R.id.iv_mine_indicator)
	ImageView ivMineIndicator;
	@ViewById(R.id.txt_recom)
	TextView txtRecom;

	// 团队
	@ViewById(R.id.rl_team)
	RelativeLayout rlTeam;
	@ViewById(R.id.iv_team)
	ImageView ivTeam;
	@ViewById(R.id.txt_team)
	TextView txtTeam;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;
	@Pref
	NewsSP_ newsSP;
	@Pref
	UserSP_ userSP;

	// 各个子页面实例
	HousesFragment houses = null;
	InfoFragment info = null;
	RecomFragment recom = null;
	MineFragment mine = null;

	int[] pageResourcesIvOff = null;
	int[] pageResourcesIvOn = null;
	RelativeLayout[] pageResourcesRl = null;
	ImageView[] pageResourceIv = null;
	TextView[] pageResourceTxt = null;
	
	boolean IsCancelCerti = false;


	NetWorkUtil netWorkUtil = null;
	List<Fragment> pages = null;
	MainActivityPageAdapter adapter = null;

	@AfterViews
	void afterViews() {
		netWorkUtil = new NetWorkUtil(context);
		
		if (!netWorkUtil.isNetworkConnected()) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Utils.toastMessageForce(MainActivity.this,
							getString(R.string.error_network_connection_not_well));
				}

			}, Rolling.ROLLING_BREAK);
		}

		initUserState();
		initPages();
		
		// 如果是机构登陆则变换界面
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
			   switchViews();
			}

		}, Rolling.SWITCH_VIEWS);

		// 检查版本更新FIXME:Later Add
		checkVersionFromServer();
	/*	if (mApp.getCurrUser() != null) {
			setMyMessageIndicator();
		}*/
		
		if (mApp.isLogin()) {
			setMyMessageIndicator();
		}
	}

	private void initUserState() {
		if (!"".equals(userSP.currUserId().get())) {
			XcfcUser user = new XcfcUser();
			user.setId(userSP.currUserId().get());
			if (!"".equals(userSP.currUserType().get())) {
				user.setBrokerType(userSP.currUserType().get());
			}
			user.setApproveState(userSP.currUserApproveState().get());
			user.setCity(userSP.currUserCityName().get());
			mApp.setCurrUser(user);
			
			//初始化当前城市
			XcfcCity city = new XcfcCity();
			city.setName(user.getCity());
			mApp.setCurrCity(city);
		}
	}

	private void initPages() {
		pages = new ArrayList<Fragment>();
		houses = new HousesFragment_();
		info = new InfoFragment_();
		recom = new RecomFragment_();
		mine = new MineFragment_();
		pages.add(houses);
		pages.add(info);
		pages.add(recom);
		pages.add(mine);

		adapter = new MainActivityPageAdapter(getSupportFragmentManager());
		adapter.addAll(pages);
		viewPager.setOffscreenPageLimit(PAGE_COUNT - 1);
		viewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		initPageResources();

		clickedPageButton(0);

		// 初始化未读消息
		initUnread();
		// 初始化
		initPushSituation();

	}

	private void initPushSituation() {
		String pushType = getIntent().getStringExtra(ExtraNames.XCFC_PUSH_TYPE);
		if (pushType == null)
			return;

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				rlInfo();
			}
		}, 500);

		if (pushType.equals(PushMessageKeyCode.INFO_POLICY)) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					info.rlNewcityActivities();
				}
			}, 500);

		} else if (pushType.equals(PushMessageKeyCode.ACTIVITY)) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					info.rlHousesActivities();
				}
			}, 500);
		} else if (pushType.equals(PushMessageKeyCode.MY_MESSAGE)) {
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					info.rlMyMessages();
				}
			}, 500);
		}

		//Utils.debug(Utils.TAG, "PUSH TYPE = " + pushType);
	}

	private void initPageResources() {
		pageResourcesIvOff = new int[PAGE_COUNT];
		pageResourcesIvOn = new int[PAGE_COUNT];
		pageResourcesRl = new RelativeLayout[PAGE_COUNT];
		pageResourceIv = new ImageView[PAGE_COUNT];
		pageResourceTxt = new TextView[PAGE_COUNT];

		pageResourcesIvOff[0] = R.drawable.menu_1;
		pageResourcesIvOn[0] = R.drawable.menu_1_on;
		pageResourcesRl[0] = rlHouses;
		pageResourceIv[0] = (ImageView) rlHouses.findViewById(R.id.iv_houses);
		pageResourceTxt[0] = (TextView) rlHouses.findViewById(R.id.txt_houses);
		pageResourcesIvOff[1] = R.drawable.menu_2;
		pageResourcesIvOn[1] = R.drawable.menu_2_on;
		pageResourcesRl[1] = rlInfo;
		pageResourceIv[1] = (ImageView) rlInfo.findViewById(R.id.iv_info);
		pageResourceTxt[1] = (TextView) rlInfo.findViewById(R.id.txt_info);
		pageResourcesIvOff[2] = R.drawable.menu_3;
		pageResourcesIvOn[2] = R.drawable.menu_3_on;
		pageResourcesRl[2] = rlRecom;
		pageResourceIv[2] = (ImageView) rlRecom.findViewById(R.id.iv_recom);
		pageResourceTxt[2] = (TextView) rlRecom.findViewById(R.id.txt_recom);
		pageResourcesIvOff[3] = R.drawable.menu_4;
		pageResourcesIvOn[3] = R.drawable.menu_4_on;
		pageResourcesRl[3] = rlMine;
		pageResourceIv[3] = (ImageView) rlMine.findViewById(R.id.iv_mine);
		pageResourceTxt[3] = (TextView) rlMine.findViewById(R.id.txt_mine);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		final XcfcHouseDetail houseDetailFromRecomm = (XcfcHouseDetail) intent
				.getSerializableExtra(ExtraNames.JUMP_FROM_RECOMM);
		boolean isToAppoint = intent.getBooleanExtra(
				ExtraNames.JUMP_TO_APPOINT, false);
		llBottom.setVisibility(View.VISIBLE);
		if (null != houseDetailFromRecomm) {
			rlRecom();
			clickedPageButton(2);
			if (isToAppoint) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						recom.rlTopWordIndicatorAppointment();
						recom.getRecommendFragment().setIntentHouse(
								houseDetailFromRecomm);
						recom.getAppointmentFragment().setIntentHouse(
								houseDetailFromRecomm);
					}
				}, 200);
			} else {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						recom.rlTopWordIndicatorRecommend();
						recom.getRecommendFragment().setIntentHouse(
								houseDetailFromRecomm);
						recom.getAppointmentFragment().setIntentHouse(
								houseDetailFromRecomm);
					}
				}, 200);
			}
		} else {
			rlHouses();
			clickedPageButton(0);
		}

		super.onNewIntent(intent);
	}

	// 注册广播
	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	// 底部标签点击事件组
	@Click
	public void rlHouses() {
		String pushMessage = getIntent().getStringExtra(
				ExtraNames.XCFC_PUSH_TYPE);
		if (null == pushMessage || "".equals(pushMessage)) {
			viewPager.setCurrentItem(0, false);
			clickedPageButton(0);
		} else {
			viewPager.setCurrentItem(0, false);
			clickedPageButton(0);
			houses.loadData();
		}
		

	}

	@Click
	public void rlInfo() {
		if (info.getCurrentItem() == 2 && mApp.getCurrUser() == null) {
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}
		info.checkDataLoaded();
		viewPager.setCurrentItem(1, false);
		ivInfoIndicator.setVisibility(View.GONE);
		clickedPageButton(1);
		
	}

	@Click
	public void rlRecom() {
		viewPager.setCurrentItem(2, false);
		clickedPageButton(2);
	}

	@Click
	void rlTeam() {
		viewPager.setCurrentItem(2, false);
		clickedPageButton(2);
	}

	@Click
	public void rlMine() {
		if (!mApp.isLogin()) {
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}
		/*if ( null == mApp.getCurrUser()) {
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}*/
		viewPager.setCurrentItem(3, false);
		clickedPageButton(3);
		callMineFragment();
		startCertifiActivity();
	}

	private void clickedPageButton(int pos) {
		for (int i = 0; i < PAGE_COUNT; ++i) {
			if (i == pos) {
				pageResourcesRl[i].setBackgroundColor(getResources().getColor(
						R.color.main_app_bar));
				pageResourceIv[i].setBackgroundResource(pageResourcesIvOn[i]);
				pageResourceTxt[i].setTextColor(getResources().getColor(
						R.color.col_words_active_white));
			} else {
				pageResourcesRl[i].setBackgroundColor(getResources().getColor(
						android.R.color.transparent));
				pageResourceIv[i].setBackgroundResource(pageResourcesIvOff[i]);
				pageResourceTxt[i].setTextColor(getResources().getColor(
						R.color.letter_grey_deep_6));
			}
		}
	}

	public HousesFragment getHouses() {
		return houses;
	}

	public InfoFragment getInfo() {
		return info;
	}

	public RecomFragment getRecom() {
		return recom;
	}

	public MineFragment getMine() {
		return mine;
	}

	// Begin 双击返回键退出代码组
	private Boolean isExit = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByDoubleClick();
		}
		return false;
	}

	private void exitByDoubleClick() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true;
			Toast.makeText(this, R.string.str_exit_app_toast_message,
					Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false;
				}
			}, 2000);
		} else {
			this.closeAllActivity();
		}
	}

	// End

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		houses.onActivityResult(requestCode, resultCode, data);
		info.onActivityResult(requestCode, resultCode, data);
		recom.onActivityResult(requestCode, resultCode, data);
		mine.onActivityResult(requestCode, resultCode, data);

		if (resultCode == ReqCode.SIGN_IN) {
			// rlMine();
			
		/*	userSP.currUserId().put(mApp.getCurrUser().getId());
			userSP.currPhone().put(mApp.getCurrUser().getMphone());
			userSP.currUserType().put(mApp.getCurrUser().getBrokerType());
			if (null != mApp.getCurrUser().getApproveState()) {
				userSP.currUserApproveState().put(mApp.getCurrUser().getApproveState());
			}if (null == mApp.getCurrUser().getApproveState()) {//为null时表示未认证
				userSP.currUserApproveState().put("");
			}*/
			initUserState();//初始化mApp状态
			setMyMessageIndicator();
			switchViews();//替换页面的显示
			IsCancelCerti = false;//登陆后重置状态
			startCertifiActivity();//是否进入实名认证界面
			//houses.setLocation(mApp.getCurrUser().getCity());//绑定城市公司
			//houses.setImageLocation(false);
		}
		if (resultCode == ReqCode.SIGN_OUT) {
			IsCancelCerti = false;//退出后重置状态
			rlHouses();
			//mApp.setCurrUser(null);
			//houses.setImageLocation(true);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Background
	void setMyMessageIndicator() {
		XcfcStringResult result = netHandler.postForGetUnreadCount(mApp.getCurrUser().getId());

		if (null != result && result.getResultSuccess() && null != result.getObjValue()) {
			String count = result.getObjValue();
			if (!"0".equals(count.trim()))
				setMyMessageUnreadUi(count);
		}
	}

	@UiThread
	void setMyMessageUnreadUi(String count) {
		houses.setRlUnreadMyMessages(count);
		info.setRlUnreadMyMessages(count);
	}

	@Background
	void initUnread() {
		XcfcStringResult resultNewCity = netHandler.postForGetLatestTime(NewsCatagoryId.CATAGORY_ID_NEWCITY);
		XcfcStringResult resultHouse = netHandler.postForGetLatestTime(NewsCatagoryId.CATAGORY_ID_HOUSE);
		boolean hasNewsNewCity = false;
		boolean hasNewsHouse = false;

		if (null != resultNewCity && resultNewCity.getResultSuccess() && null != resultNewCity.getObjValue() && newsSP.latestHouseNewsDate().get().compareTo(resultNewCity.getObjValue()) < 0) {
			newsSP.latestHouseNewsDate().put(resultNewCity.getObjValue());
			hasNewsNewCity = true;
		}
		if (null != resultHouse	&& resultHouse.getResultSuccess() && null != resultHouse.getObjValue() && newsSP.latestHouseNewsDate().get().compareTo(resultHouse.getObjValue()) < 0) {
			newsSP.latestHouseNewsDate().put(resultHouse.getObjValue());
			hasNewsHouse = true;
		}

		setUnreadUi(hasNewsNewCity, hasNewsHouse);
	}

	@UiThread
	void setUnreadUi(boolean hasNewsNewCity, boolean hasNewsHouse) {
		if (hasNewsNewCity || hasNewsHouse) {
			ivInfoIndicator.setVisibility(View.VISIBLE);
		}

		if (hasNewsHouse) {
			info.setRlUnreadHousesActivities();
		}
	}

	/**
	 * 调用MineFragment的网络访问
	 */
	public void callMineFragment() {
		mine.recomToMineInitData();
	}


	public void initGlobal(String serverVersion) {
		try {
			// Global.localVersion =
			// getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
			// //设置本地版本号
			Global.serverVersion = serverVersion;
			Global.localVersion = Utils.getVersionName(this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	public void checkVersion() {
		if (Global.localVersion.compareTo(Global.serverVersion) < 0) {
			updateDialog();
		}
	}

	/**
	 * 检查版本号
	 */
	@Background
	void checkVersionFromServer() {
		XcfcVersionResult result = netHandler.postForGetVersion(MobileType.MOBILE_ANDROID);

		if (null == result || !result.getResultSuccess()) {
			return;
		}
		goBackMainThreadUpdate(result);
		// TODO use the result to judge download new version or not

		String currVersion = Utils.getVersionName(this);
		Utils.debug(Utils.TAG, "Current version is : " + result.getObjValue());
		Utils.debug(Utils.TAG, "getVersionName is : " + currVersion);
		Utils.debug(Utils.TAG,"needUpdate = "+ (currVersion == null ? false : currVersion.compareTo(result.getObjValue()) < 0));
	}

	@UiThread
	void goBackMainThreadUpdate(XcfcVersionResult result) {
		initGlobal(result.getObjValue());
		checkVersion();
	}

	// update soft dialog
	@SuppressWarnings("deprecation")
	public void updateDialog() {
		View dialogView = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog_for_all_setting_layer, null);
		TextView content = (TextView) dialogView
				.findViewById(R.id.dialog_content);
		TextView dialogBtnTv = (TextView) dialogView
				.findViewById(R.id.dialog_btn_tv);
		Button cancle = (Button) dialogView.findViewById(R.id.dialog_cancle);
		Button btnOk = (Button) dialogView.findViewById(R.id.dialog_ok);
		cancle.setVisibility(View.GONE);
		dialogBtnTv.setVisibility(View.GONE);
		content.setCompoundDrawables(null, null, null, null);
		content.setText(context.getResources().getString(R.string.update_msg));

		final Dialog customDialog = new Dialog(MainActivity.this,
				R.style.dialog);
		customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		customDialog.setContentView(dialogView);
		customDialog.setCancelable(false);

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent updateIntent = new Intent(MainActivity.this,
//						UpdateService_.class);
//				updateIntent.putExtra("titleId", R.string.app_name);
//				startService(updateIntent);
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(Constant.SHARE_URL);
				intent.setData(content_url);
				startActivity(intent);
				customDialog.dismiss();
			}
		});
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				customDialog.dismiss();
			}
		});
		// show dialog in android 2.3
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		customDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		WindowManager.LayoutParams lp = customDialog.getWindow()
				.getAttributes();
		lp.width = (int) (display.getWidth() * 0.85); // 设置宽度
		customDialog.getWindow().setAttributes(lp);
		customDialog.show();
		customDialog.setCanceledOnTouchOutside(false);
	}

	private void teamResource() {
		rlRecom.setVisibility(View.GONE);
		rlTeam.setVisibility(View.VISIBLE);
		pageResourcesIvOff[2] = R.drawable.team_off;
		pageResourcesIvOn[2] = R.drawable.team_on;
		pageResourcesRl[2] = rlTeam;
		pageResourceIv[2] = (ImageView) rlTeam.findViewById(R.id.iv_team);
		pageResourceTxt[2] = (TextView) rlTeam.findViewById(R.id.txt_team);
		pageResourceIv[2].setBackgroundResource(pageResourcesIvOff[2]);
	}

	private void recomResource() {
		rlTeam.setVisibility(View.GONE);
		rlRecom.setVisibility(View.VISIBLE);
		pageResourcesIvOff[2] = R.drawable.menu_3;
		pageResourcesIvOn[2] = R.drawable.menu_3_on;
		pageResourcesRl[2] = rlRecom;
		pageResourceIv[2] = (ImageView) rlRecom.findViewById(R.id.iv_recom);
		pageResourceTxt[2] = (TextView) rlRecom.findViewById(R.id.txt_recom);
		pageResourceIv[2].setBackgroundResource(pageResourcesIvOff[2]);
	}

	/**
	 * 根据登陆的用户实现不同页面的变换
	 */
	private void switchViews() {
		if (!mApp.isLogin()) {//未登录
			return;
		}
		if (mApp.getCurrUser() == null || mApp.getCurrUser().getBrokerType() == null) {
			return;
		}
		if (mApp.getCurrUser().getBrokerType().equals(BrokerType.NORMAL)) {
			recom.recomViews();
			recomResource();
			mine.brokerViews();
		}else if (mApp.getCurrUser().getBrokerType().equals(BrokerType.SUB_ORG_ANIZATION)) {
			recom.recomViews();
			recomResource();
			mine.subOrganizationViews();			
		} else if (mApp.getCurrUser().getBrokerType().equals(BrokerType.ORG_ANIZATION)) {
			recom.loadDateTeamList();
			teamResource();
			recom.teamViews();
			mine.organizationViews();
		}
	}
	
	/**
	 * 登陆提示是否要实名认证
	 */
	private void startCertifiActivity(){
		initUserState();//初始化mApp状态
		if (IsCancelCerti) {
			return;
		}
		/*if (mApp.getCurrUser() == null) {
			return;
		}*/
		if (!mApp.isLogin()) {
			return;
		}
		if (mApp.getCurrUser().getBrokerType() !=null && mApp.getCurrUser().getBrokerType().equals(BrokerType.NORMAL)) {
			if (mApp.getCurrUser().getApproveState() != null && mApp.getCurrUser().getApproveState().equals("")) {
				certifiDialog();
			}
		}else {
			return;
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void certifiDialog(){

		View dialogView = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog_for_certifi_note, null);
		TextView content = (TextView) dialogView
				.findViewById(R.id.dialog_content);
		Button cancle = (Button) dialogView.findViewById(R.id.dialog_cancle);
		Button btnOk = (Button) dialogView.findViewById(R.id.dialog_ok);
		content.setText(context.getResources().getString(R.string.dialog_title_certi_note));
		cancle.setText(context.getResources().getString(R.string.dialog_cancel));
		btnOk.setText(context.getResources().getString(R.string.dialog_ok));

		final Dialog customDialog = new Dialog(MainActivity.this,
				R.style.dialog);
		customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		customDialog.setContentView(dialogView);
		customDialog.setCancelable(false);

		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, CertificationActivity_.class);
				intent.putExtra("approveState", "");
				startActivity(intent);
				customDialog.dismiss();
				IsCancelCerti = true;
			}
		});
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				IsCancelCerti = true;
				customDialog.dismiss();
			}
		});
		// show dialog in android 2.3
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		customDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		WindowManager.LayoutParams lp = customDialog.getWindow()
				.getAttributes();
		lp.width = (int) (display.getWidth() * 0.85); // 设置宽度
		customDialog.getWindow().setAttributes(lp);
		customDialog.show();	
	}
}
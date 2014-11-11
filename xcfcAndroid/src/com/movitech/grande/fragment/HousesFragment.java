package com.movitech.grande.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.activity.MainActivity;
import com.movitech.grande.adapter.HouseSearchListAdapter;
import com.movitech.grande.adapter.ViewPagerAdapter;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.constant.Rolling;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.Utils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.model.XcfcCity;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.model.XcfcHouseBanner;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.net.protocol.XcfcHousesResult;
import com.movitech.grande.utils.ConvertStrToArray;
import com.movitech.grande.utils.Formatter;
import com.movitech.grande.utils.NetWorkUtil;
import com.movitech.grande.views.CirclePageIndicator;
import com.movitech.grande.views.LoadDataListView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.BuildDetailActivity_;
import com.movitech.grande.activity.ChooseCityActivity_;
import com.movitech.grande.activity.CommissionEarnActivity_;
import com.movitech.grande.activity.HotActionActivity_;
import com.movitech.grande.activity.HousesListsActivity_;
import com.movitech.grande.activity.InfoDetailActivity_;
import com.movitech.grande.activity.LoginActivity_;
import com.movitech.grande.activity.ScoreEarnActivity_;
import com.movitech.grande.activity.SearchBuildActivity_;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 10, 2014 10:53:15 AM
 * @Description: This is David Wu's property.
 * 
 **/
@EFragment(R.layout.fragment_houses)
public class HousesFragment extends BaseFragment {

	@ViewById(R.id.vp_publicity)
	ViewPager vpPublicity;
	@ViewById(R.id.txt_earn_commission_detail)
	TextView txtEarnCommissionDetail;
	@ViewById(R.id.rl_search_horizontal)
	RelativeLayout rlSearchHorizontal;
	@ViewById(R.id.rl_search)
	RelativeLayout rlSearch;
	@ViewById(R.id.iv_earn_score)
	ImageView ivEarnScore;
	@ViewById(R.id.iv_recommend_houses)
	ImageView ivRecommendHouses;
	@ViewById(R.id.rl_others_tools)
	RelativeLayout rlOthersTools;
	@ViewById(R.id.rl_message)
	RelativeLayout rlMessage;
	@ViewById(R.id.rl_earn_commission)
	RelativeLayout rlEarnCommission;
	@ViewById(R.id.iv_search_hint)
	ImageView ivSearchHint;
	@ViewById(R.id.ll_location)
	LinearLayout llLocation;
	@ViewById(R.id.rl_search_hint)
	LinearLayout rlSearchHint;
	@ViewById(R.id.txt_others_tools_detail)
	TextView txtOthersToolsDetail;
	@ViewById(R.id.rl_recommend_houses)
	RelativeLayout rlRecommendHouses;
	@ViewById(R.id.iv_adv_close)
	ImageView ivAdvClose;
	@ViewById(R.id.txt_others_activities)
	TextView txtOthersActivities;
	@ViewById(R.id.ll_others)
	LinearLayout llOthers;
	@ViewById(R.id.iv_recommend_houses_cicle)
	ImageView ivRecommendHousesCicle;
	@ViewById(R.id.iv_location)
	ImageView ivLocation;
	@ViewById(R.id.rl_unread)
	RelativeLayout rlUnread;
	@ViewById(R.id.iv_adv)
	ImageView ivAdv;
	@ViewById(R.id.iv_earn_commission)
	ImageView ivEarnCommission;
	@ViewById(R.id.hsv_recommend_houses)
	HorizontalScrollView hsvRecommendHouses;
	@ViewById(R.id.rl_adv)
	RelativeLayout rlAdv;
	@ViewById(R.id.rl_search_btn)
	RelativeLayout rlSearchBtn;
	@ViewById(R.id.iv_others_activities)
	ImageView ivOthersActivities;
	@ViewById(R.id.tv_sear_ok)
	TextView tvSearOk;
	@ViewById(R.id.txt_recommend_houses)
	TextView txtRecommendHouses;
	@ViewById(R.id.txt_recommend_houses_more)
	TextView txtRecommendHousesMore;
	@ViewById(R.id.txt_earn_commission)
	TextView txtEarnCommission;
	@ViewById(R.id.iv_others_tools)
	ImageView ivOthersTools;
	@ViewById(R.id.rl_earn_commission_external)
	RelativeLayout rlEarnCommissionExternal;
	@ViewById(R.id.txt_earn_score)
	TextView txtEarnScore;
	@ViewById(R.id.rl_slide_hint)
	RelativeLayout rlSlideHint;
	@ViewById(R.id.iv_message)
	ImageView ivMessage;
	@ViewById(R.id.iv_recommend_houses_more)
	ImageView ivRecommendHousesMore;
	@ViewById(R.id.rl_others_activities)
	RelativeLayout rlOthersActivities;
	@ViewById(R.id.ll_earn_buttons)
	LinearLayout llEarnButtons;
	@ViewById(R.id.txt_location)
	TextView txtLocation;
	@ViewById(R.id.rl_search_bind)
	RelativeLayout rlSearchBind;
	@ViewById(R.id.edt_search_string)
	EditText edtSearchString;
	@ViewById(R.id.txt_adv)
	TextView txtAdv;
	@ViewById(R.id.rl_earn_score)
	RelativeLayout rlEarnScore;
	@ViewById(R.id.ll_recommend_houses)
	LinearLayout llRecommendHouses;
	@ViewById(R.id.txt_others_activities_detail)
	TextView txtOthersActivitiesDetail;
	@ViewById(R.id.rl_body)
	RelativeLayout rlBody;
	@ViewById(R.id.txt_slide_hint)
	TextView txtSlideHint;
	@ViewById(R.id.rl_top)
    RelativeLayout rlTop;
	@ViewById(R.id.iv_slide_hint)
	ImageView ivSlideHint;
	@ViewById(R.id.txt_others_tools)
	TextView txtOthersTools;
	@ViewById(R.id.txt_earn_score_detail)
	TextView txtEarnScoreDetail;
	@ViewById(R.id.lv_search_result)
	LoadDataListView lvSearchResult;
	@ViewById(R.id.rl_earn_score_external)
	RelativeLayout rlEarnScoreExternal;
	@ViewById(R.id.txt_unread)
	TextView txtUnread;
	@ViewById(R.id.txt_search_hint)
	TextView txtSearchHint;
	@ViewById(R.id.circle_indicator)
	CirclePageIndicator circleIndicator;

	@ViewById(R.id.tv_search_num)
	TextView tvSearchNum;

	@Bean(NetHandler.class)
	INetHandler netHandler;
	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@App
	MainApp mApp;

	XcfcCity city = null;
	XcfcHouse[] houses = null;
	XcfcHouse[] searchHouses = null;
	XcfcHouseBanner[] houseBanners = null;
	HouseSearchListAdapter searchAdapter = null;
	ProcessingDialog processingDialog = null;
	NetWorkUtil netWorkUtil = null;
	String [] buildType = null;
	int totalPage;
	int currentPage;
	int totalSize;
	View houseLoadMore = null;
	boolean isLoadingMore = false;//标记是否正在加载更多  false---否，true---是
	ImageDownLoader downLoader = null;
	
	private Timer timer = null;
	private TimerTask timerTask = null;
	@AfterViews
	void afterViews() {
		txtLocation.setText(Constant.CITY);
		// 初始化界面
		initPages();
		
		loadData();
	}

	boolean touching = false;
	private void initSlideViewPage() {
		
		List<View> views = new ArrayList<View>();
		//downLoader = new ImageDownLoader(getActivity().getApplicationContext());
		for (int i = 0; i < houseBanners.length; ++i) {
			final ImageView iv = (ImageView) LayoutInflater.from(context).inflate(R.layout.layout_iv_publicity, null);
			final XcfcHouseBanner banner = houseBanners[i];
			
			imageUtils.loadHouseBannerImage(banner.getPicsrc(), iv);
//			Bitmap bitmap = downLoader.downloadImage(banner.getPicsrc(), new onImageLoaderListener() {
//				
//				@Override
//				public void onImageLoader(Bitmap bitmap, String url) {
//					if (iv != null && bitmap != null) {
//						iv.setImageBitmap(bitmap);
//					}
//				}
//			}, true);
//			if (iv.getDrawable() == null && bitmap == null) {
//				iv.setBackgroundResource(R.drawable.default_house_banner);
//			} else if (iv.getDrawable() == null && bitmap != null) {
//				iv.setImageBitmap(bitmap);
//			}
			//banner 的点击事件
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					InfoDetailActivity_.intent(HousesFragment.this.getActivity())
						.infoId(banner.getNewsId()).start();
				}
			});
			views.add(iv);
		}
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		adapter.init();
		adapter.addAll(views);
		vpPublicity.setAdapter(adapter);
		circleIndicator.setViewPager(vpPublicity);
//		new Timer().schedule(new TimerTask() {
//			@Override
//			public void run() {
//				if (!touching)
//					switchPage();
//			}
//		}, 0, Rolling.ROLLING_BREAK);
		cancleBannerRollTask();
		startBannerRollTask();
		vpPublicity.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					touching = true;
					break;
				case MotionEvent.ACTION_UP:
					touching = false;
					break;
				}
				return false;
			}
		});
	}

	
	
	@UiThread
	void switchPage() {
		vpPublicity.setCurrentItem((vpPublicity.getCurrentItem() + 1) % Constant.BANNER_COUNT);
	}

	private void initPages() {
		/*
		city = mApp.getCurrCity();
		if (city == null) {
			city = new XcfcCity();
			city.setName(getString(R.string.txt_location_unknown));
		}
		txtLocation.setText(city.getName());*/
		// 载入数据
		//doLoadDataAndBindData();
		
//		if (mApp.getCurrUser() != null) {
//			setImageLocation(false);
//		}else 
//			setImageLocation(true);
//		
//		if (mApp.getCities() == null) {
//			return;
//		}
		
		//initPushSplash();
		/*// 首先选择城市
		Intent intent = new Intent(this.getActivity(), ChooseCityActivity_.class);
		intent.putExtra(ExtraNames.XCFC_CITY_FIRST, true);
		startActivityForResult(intent, ReqCode.SELECT_CITY);*/
	}
	
	
	/**
	 * 显示所在位置并构造当前城市
	 * 
	 */
//	public void setLocation(String currCityName) {
//		if (currCityName != null) {
//			XcfcCity bindcity = new XcfcCity();
//			bindcity.setName(currCityName);
//			city = bindcity;
//			txtLocation.setText(currCityName);
//		}
//	}
	
//	public void setImageLocation(boolean isVisiable) {
//		if (isVisiable) 
//			ivLocation.setVisibility(View.VISIBLE);
//		else 
//			ivLocation.setVisibility(View.GONE);
//	}
	
	//推送消息时要手动加载
	public void loadData(){
		doLoadDataAndBindData();
		doLoadHouseBannerData();
	}

	@Background
	void doLoadDataAndBindData() {
//		String currentCity = null;
//		if (city == null) {
//			if (txtLocation.getText() != null) {
//				currentCity = txtLocation.getText().toString();
//			}
//		}else {
//			currentCity = city.getName();
//		}
		XcfcHousesResult result = netHandler.postForGetHousesResult(1,
				Constant.CITY, "", "", "0", "");
		if (null == result) {
			goBackMainThread(getString(R.string.error_server_went_wrong), false);
			return;
		}
		if (result.getResultSuccess() == false) {
			goBackMainThread(result.getResultMsg(), false);
			return;
		}

		houses = result.getPageResult().getHouses();
		
		goBackMainThread(result.getResultMsg(), true);
	}

	private void goBackMainThread(String msg, boolean success) {
		if (success) {
			doBindData();
		}
	}

	public void setRlUnreadMyMessages(String count) {
		rlUnread.setVisibility(View.VISIBLE);
		txtUnread.setText(count);
	}

	@UiThread
	void doBindData() {
		if (houses == null)
			return;

		llRecommendHouses.removeAllViews();

		for (int i = 0; i < houses.length; i++) {
			llRecommendHouses.addView(addOneRecommendHouse(houses[i]));
		}
	}
	
	//获取三条banner
	@Background
	void doLoadHouseBannerData() {
		houseBanners = netHandler.postForGetHouseBanner("", mApp.getCurrCity().getName());
		if (null == houseBanners) {
			return;
		}
		
		goBackMainThreadBanner();
	}

	@UiThread
	void goBackMainThreadBanner(){
		for (int i = 0; i < houseBanners.length; i++) {
			Utils.debug(houseBanners[i].getPicsrc());
		}
		initSlideViewPage();
	}

	private View addOneRecommendHouse(Object data) {
		final XcfcHouse xcfcHouse = (XcfcHouse) data;
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_recommend_houses, null);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(context, HousesDetailActivity_.class);
				Intent intent = new Intent(context, BuildDetailActivity_.class);
				intent.putExtra("id", xcfcHouse.getId());
				context.startActivity(intent);
			}
		});

		final ImageView ivHouse = (ImageView) view
				.findViewById(R.id.iv_layout_recommend_houses);
		TextView txtName = (TextView) view
				.findViewById(R.id.txt_name_layout_recommend_houses);
		TextView txtSection = (TextView) view
				.findViewById(R.id.txt_section_layout_recommend_houses);
		TextView txtDetail = (TextView) view
				.findViewById(R.id.txt_detail_layout_recommend_houses);
		TextView txtPrivilege = (TextView) view
				.findViewById(R.id.txt_privilege_layout_recommend_houses);
		TextView txtArea = (TextView) view
				.findViewById(R.id.txt_area_layout_recommend_houses);
		RelativeLayout rlSpecialityFirst = (RelativeLayout) view.findViewById(R.id.rl_speciality_first_layout_recommend_houses);
		TextView  txtSpecialityFirst = (TextView) view.findViewById(R.id.txt_speciality_first_layout_recommend_houses);
		
		RelativeLayout rlSpecialitySecond = (RelativeLayout) view
				.findViewById(R.id.rl_speciality_second_layout_recommend_houses);
		TextView txtSpecialitySecond = (TextView) view
				.findViewById(R.id.txt_speciality_second_layout_recommend_houses);
		RelativeLayout rlSpecialityThird = (RelativeLayout) view
				.findViewById(R.id.rl_speciality_third_layout_recommend_houses);
		TextView txtSpecialityThirdd = (TextView) view
				.findViewById(R.id.txt_speciality_third_layout_recommend_houses);
		imageUtils.loadHouseRecommendImage(xcfcHouse.getPicsrc(), ivHouse);
		txtName.setText(xcfcHouse.getName());
		txtSection.setText(Formatter.formatSection(xcfcHouse.getArea()));
		txtPrivilege.setText(xcfcHouse.getDiscount());
		txtDetail.setText(xcfcHouse.getSalePoint());
		if (!xcfcHouse.getPricePerSuiteScope().equals("")) {
			txtArea.setText(xcfcHouse.getPricePerSuiteScope()
					+ context.getResources().getString(R.string.str_square_total_unit));
		} else if (!xcfcHouse.getPrice().equals("")) {
			txtArea.setText(xcfcHouse.getPrice()
					+ getString(R.string.str_square));
		}
		buildType = ConvertStrToArray.convertStrToArray(xcfcHouse.getBuildingType());
		if (buildType == null) {
			rlSpecialityFirst.setVisibility(View.VISIBLE);
			txtSpecialityFirst.setText(xcfcHouse.getBuildingType());
		}else if (buildType.length == 2) {
			rlSpecialityFirst.setVisibility(View.VISIBLE);
			txtSpecialityFirst.setText(buildType[0]);
			rlSpecialitySecond.setVisibility(View.VISIBLE);
			txtSpecialitySecond.setText(buildType[1]);
		}else if (buildType.length >= 3) {
			rlSpecialityFirst.setVisibility(View.VISIBLE);
			txtSpecialityFirst.setText(buildType[0]);
			rlSpecialitySecond.setVisibility(View.VISIBLE);
			txtSpecialitySecond.setText(buildType[1]);
			rlSpecialityThird.setVisibility(View.VISIBLE);
			txtSpecialityThirdd.setText(buildType[2]);
		}		
		//}
		return view;
	}
	@Click
	void ivRecommendHousesMore() {
		HousesListsActivity_.intent(getActivity()).start();
	}

	@Click
	void ivAdvClose() {
		rlAdv.setVisibility(View.GONE);
	}

//	@Click
//	void llLocation() {
//		//如果已登录则点击无效
//		if (mApp.getCurrUser() != null) {
//			return;
//		}
//			netWorkUtil = new NetWorkUtil(context);
//			if (!netWorkUtil.isNetworkConnected()) {
//				Intent intent = new Intent(getActivity().getApplicationContext(), ChooseCityActivity_.class);
//				intent.putExtra(ExtraNames.XCFC_CITY_NET_ERROR, true);
//				if (txtLocation.getText() == null || txtLocation.getText().toString().equals("")) {
//					return;
//				}
//				intent.putExtra("currentCity", txtLocation.getText().toString());
//				startActivityForResult(intent, ReqCode.NET_ERROR_SELECT_CITY);
//				return;
//			}
//		if (null == mApp.getCities()) {
//			Utils.toastMessage(getActivity(), getString(R.string.str_city_list_cannot_be_reached));
//			return;
//		}
//		
//		ChooseCityActivity_.intent(getActivity()).startForResult(
//				ReqCode.SELECT_CITY);
//	}

	@Click
	void rlSearch() {
		Intent intent = new Intent(getActivity(), SearchBuildActivity_.class);
		startActivity(intent);
	}

	@Click
	void rlOthersActivities() {
		HotActionActivity_.intent(context).start();
	}

	//购房工具
	@Click
	void rlOthersTools() {
		//ToolActivity_.intent(context).start();
	}
	// 赚积分
	@Click
	void rlEarnScoreExternal(){
		/*if (mApp.getCurrUser() == null) {
			LoginActivity_.intent(this).start();
			return;
		}*/
		if (!mApp.isLogin()) {
			LoginActivity_.intent(this).start();
			return;
		}

		ScoreEarnActivity_.intent(context).start();
	}
	// 赚佣金
	@Click
	void rlEarnCommissionExternal(){
		((MainActivity) getActivity()).rlRecom();
	}

	// 注册广播
	@Override
	public void onResume() {
		super.onResume();
		/*if (mApp.getCurrUser() != null) {
			//setImageLocation(false);
			//setLocation(mApp.getCurrUser().getCity());
			
		}*/
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}




	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ReqCode.SELECT_CITY) {
			city = mApp.getCurrCity();
			txtLocation.setText(city.getName());
			doLoadDataAndBindData();
			doLoadHouseBannerData();
		}else if (requestCode == ReqCode.NET_ERROR_SELECT_CITY) {
			String currentSelectCity = data.getStringExtra(ExtraNames.NET_ERROR_SELECT_CITY);
			txtLocation.setText(currentSelectCity);
			Utils.toastMessageForce(getActivity(), getString(R.string.error_network_connection_not_well));
		}

		super.onActivityResult(requestCode, resultCode, data);
	}


	@Click
	void rlMessage() {
		/*if (mApp.getCurrUser() == null) {
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}*/
		if (!mApp.isLogin()) {
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}

		rlUnread.setVisibility(View.GONE);

		((MainActivity) getActivity()).rlInfo();
		((MainActivity) getActivity()).getInfo().rlMyMessages();
	}
	
	@Click
	void vpPublicity() {
		CommissionEarnActivity_.intent(getActivity()).start();
	}
	
	 private void initPushSplash() {
		 if (mApp.getCurrUser()!=null) {
			return;
		}
		  String pushMessage = getActivity().getIntent().getStringExtra(ExtraNames.XCFC_PUSH_TYPE);
			if (null == pushMessage || "".equals(pushMessage)) {
				Intent intent = new Intent(this.getActivity(), ChooseCityActivity_.class);
				intent.putExtra(ExtraNames.XCFC_CITY_FIRST, true);
				startActivityForResult(intent, ReqCode.SELECT_CITY);
			}
	  }
	 
	private void startBannerRollTask() {
		timerTask = new TimerTask() {
			@Override
			public void run() {
				if (!touching)
					switchPage();
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, 0, Rolling.ROLLING_BREAK);
	}

	private void cancleBannerRollTask() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
}

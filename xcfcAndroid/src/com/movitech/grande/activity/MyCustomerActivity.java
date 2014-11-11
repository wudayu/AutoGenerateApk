package com.movitech.grande.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.MainActivityPageAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.fragment.CustomerImportantFragment;
import com.movitech.grande.fragment.CustomerRecomConfirmFragment;
import com.movitech.grande.fragment.CustomerRecomOkFragment;
import com.movitech.grande.fragment.CustomerSalesFragment;
import com.movitech.grande.fragment.CustomerSeaHouseFragment;
import com.movitech.grande.model.XcfcMyCustomer;
import com.movitech.grande.net.INetHandler;
import com.movitech.grande.net.NetHandler;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.CustomHorizontalScrollView;
import com.movitech.grande.views.ProcessingDialog;
import com.movitech.grande.views.UnderlinePageIndicator;
import com.movitech.grande.views.CustomHorizontalScrollView.OnEdgeListener;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.SearchClientActivity_;
import com.movitech.grande.fragment.CustomerImportantFragment_;
import com.movitech.grande.fragment.CustomerRecomConfirmFragment_;
import com.movitech.grande.fragment.CustomerRecomOkFragment_;
import com.movitech.grande.fragment.CustomerSalesFragment_;
import com.movitech.grande.fragment.CustomerSeaHouseFragment_;

/**
 * 
 * @author Warkey.Song
 * 
 */
@EActivity(R.layout.activity_my_customer)
public class MyCustomerActivity extends BaseActivity {
	public static final int PAGE_COUNT = 5;
	@ViewById(R.id.txt_tag_customer_important)
	TextView txtTagCustomerImportant;
	@ViewById(R.id.txt_tag_customer_sea_house)
	TextView txtTagCustomerSeaHouse;
	@ViewById(R.id.txt_tag_customer_sales)
	TextView txtTagCustomerSales;
	@ViewById(R.id.txt_tag_customer_recommended_ok)
	TextView txtTagCustomerRecommendedOk;
	@ViewById(R.id.txt_tag_customer_recommended_confirm)
	TextView txtTagCustomerRecommendedConfirm;

	@ViewById(R.id.rl_tag_customer_important)
	RelativeLayout rlTagCustomerImportant;
	@ViewById(R.id.rl_tag_customer_sea_house)
	RelativeLayout rlTagCustomerSeaHouse;
	@ViewById(R.id.rl_tag_customer_sales)
	RelativeLayout rlTagCustomerSales;
	@ViewById(R.id.rl_tag_customer_recommended_ok)
	RelativeLayout rlTagCustomerRecommendedOk;
	@ViewById(R.id.rl_tag_customer_recommended_confirm)
	RelativeLayout rlTagCustomerRecommendedConfirm;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_search)
	ImageView ivSearch;
	@ViewById(R.id.rl_search_client)
	RelativeLayout rlSearchClient;
	@ViewById(R.id.iv_navigator_left)
	ImageView ivNavigatorLeft;
	@ViewById(R.id.iv_navigator_right)
	ImageView ivNavigatorRight;
	@ViewById(R.id.vp_customer_type)
	BaseViewPager vpCustomerType;
	@ViewById(R.id.indicator_customer_type)
	UnderlinePageIndicator indicatorCustomerType;

	@ViewById(R.id.sv_top)
	CustomHorizontalScrollView svTop;
	List<View> views = null;
	GridView[] gridViews = null;
	TextView[] tvCounts = null;
	List<TextView> textViews = null;
	
	
	@Bean(NetHandler.class)
	INetHandler netHandler;
	@App
	MainApp mApp;
	List<XcfcMyCustomer[]> customersList = null;
		
	CustomerImportantFragment importantFragment = null;
	CustomerSeaHouseFragment seaHouseFragment = null;
	CustomerSalesFragment salesFragment = null;
	CustomerRecomOkFragment recomOkFragment = null;
	CustomerRecomConfirmFragment recomConfirmFragment = null;
	// 数据
	Bundle bundle = null;
	int currentItem;// 显示的第几页
	int subViewWidth;

	ProcessingDialog processingDialog = null;

	@AfterViews
	void afterViews() {
		processingDialog = new ProcessingDialog(context, true, new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				MyCustomerActivity.this.finish();
			}
		});
		processingDialog.show();

		// 获取上个页面传的数据
		initData();
		// 初始化5个标签
		initializeTab();
		// 渲染5个子页面
		initializeViewPager();

		// 载入数据
		//loadForWhichView(currentItem);
	}

	public void dismissProcessingDialog() {
		if (processingDialog != null)
			processingDialog.dismiss();
	}

	private void initData() {
		bundle = getIntent().getExtras();
		if (bundle != null) {
			currentItem = bundle.getInt(ExtraNames.MINE_CURRENT_CUSTOMER);		
		}

	}

	private void initializeViewPager() {
		List<Fragment> pages = new ArrayList<Fragment>();
		importantFragment = new CustomerImportantFragment_();
		seaHouseFragment = new CustomerSeaHouseFragment_();
		salesFragment = new CustomerSalesFragment_();
		recomOkFragment = new CustomerRecomOkFragment_();
		recomConfirmFragment = new CustomerRecomConfirmFragment_();
		if (bundle != null && !("".equals(bundle.getString(ExtraNames.ORG_TO_CUSTOMER)))) {
			importantFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_CUSTOMER));
			seaHouseFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_CUSTOMER));
			salesFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_CUSTOMER));
			recomConfirmFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_CUSTOMER));
			recomOkFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_CUSTOMER));
		}
		pages.add(importantFragment);
		pages.add(seaHouseFragment);
		pages.add(salesFragment);
		pages.add(recomOkFragment);
		pages.add(recomConfirmFragment);
		MainActivityPageAdapter adapter = new MainActivityPageAdapter(
				getSupportFragmentManager());
		adapter.addAll(pages);
		vpCustomerType.setOffscreenPageLimit(PAGE_COUNT - 1);
		vpCustomerType.setAdapter(adapter);
		vpCustomerType.setOnTouchListener(null);
		vpCustomerType.setCurrentItem(currentItem);
		initTextColor(currentItem);
		indicatorCustomerType.setViewPager(vpCustomerType);
		indicatorCustomerType.setOnPageChangeListener(new VPOnPageChangeListener());
	}

	private void initializeTab() {
		textViews = new ArrayList<TextView>();

		textViews.add(txtTagCustomerImportant);
		textViews.add(txtTagCustomerSeaHouse);
		textViews.add(txtTagCustomerSales);
		textViews.add(txtTagCustomerRecommendedOk);
		textViews.add(txtTagCustomerRecommendedConfirm);
	}

	

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		View view = (View) svTop.getChildAt(svTop.getChildCount() - 1);

		subViewWidth = view.getRight();

		svTop.setOnEdgeListener(new OnEdgeListener() {
			@Override
			public void onRight() {
				ivNavigatorLeft.setVisibility(View.VISIBLE);
				ivNavigatorRight.setVisibility(View.GONE);
			}

			@Override
			public void onLeft() {
				ivNavigatorRight.setVisibility(View.VISIBLE);
				ivNavigatorLeft.setVisibility(View.GONE);
			}
		});
		//显示最右的tab标签
		if (currentItem == 4) {
			svTop.fullScroll(ScrollView.FOCUS_RIGHT);
		}
		super.onWindowFocusChanged(hasFocus);
	}

	@Click
	void ivNavigatorRight() {
		svTop.fullScroll(ScrollView.FOCUS_RIGHT);
	}

	@Click
	void ivNavigatorLeft() {
		svTop.fullScroll(ScrollView.FOCUS_LEFT);
	}

	// 返回按钮点击事件
	@Click
	void ivBack() {
		this.finish();
	}
	
	@Click
	void rlSearchClient(){
		Intent intent = new Intent(MyCustomerActivity.this, SearchClientActivity_.class);
		startActivityForResult(intent, ReqCode.CLIENT_IMPORTANT);
	}

	// 顶部标签点击事件组
	@Click
	void rlTagCustomerImportant() {
		importantFragment.loadDataAgain();
		vpCustomerType.setCurrentItem(0);
	}

	@Click
	void rlTagCustomerSeaHouse() {
		seaHouseFragment.loadDataAgain();
		vpCustomerType.setCurrentItem(1);
	}

	@Click
	void rlTagCustomerSales() {
		salesFragment.loadDataAgain();
		vpCustomerType.setCurrentItem(2);
	}

	@Click
	void rlTagCustomerRecommendedOk() {		
		recomOkFragment.loadDataAgain();
		vpCustomerType.setCurrentItem(3);
	}

	@Click
	void rlTagCustomerRecommendedConfirm() {		
		recomConfirmFragment.loadDataAgain();
		vpCustomerType.setCurrentItem(4);
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
			vpCustomerType.setCurrentItem(currentItem);
			
			switch (currentItem) {
			case 0:
				importantFragment.loadDataAgain();
				break;
			case 1:
				seaHouseFragment.loadDataAgain();
				break;
			case 2:
				salesFragment.loadDataAgain();
				break;
			case 3:
				recomOkFragment.loadDataAgain();
				break;
			case 4:
				recomConfirmFragment.loadDataAgain();
				break;

			default:
				break;
			}
			changeWordIndicator(pos);
		}

	}

	void changeWordIndicator(int pos) {
		for (int i = 0; i < textViews.size(); ++i)
			textViews.get(i).setTextColor(
					getResources().getColor(
							i == pos ? R.color.letter_main_red
									: R.color.col_words_inactive));
		// 此处为了解决手指滑动到右边显示最右的tab
		if (pos == 4) {
			svTop.fullScroll(ScrollView.FOCUS_RIGHT);
		}
		if (pos == 1) {
			svTop.fullScroll(ScrollView.FOCUS_LEFT);
		}
	}

	void initTextColor(int pos) {
		for (int i = 0; i < textViews.size(); ++i)
			textViews.get(i).setTextColor(
					getResources().getColor(
							i == pos ? R.color.letter_main_red
									: R.color.col_words_inactive));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == ReqCode.CLIENT_IMPORTANT) {
			importantFragment.loadDataAgain();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}

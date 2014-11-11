package com.movitech.grande.fragment;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.MainApp;
import com.movitech.grande.adapter.InfoFragmentPageAdapter;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.UnderlinePageIndicator;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.LoginActivity_;
import com.movitech.grande.fragment.InfoHouseFragment_;
import com.movitech.grande.fragment.InfoMymessageFragment_;
import com.movitech.grande.fragment.InfoNewcityFragment_;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 10, 2014 10:53:57 AM
 * @Description: This is David Wu's property.
 *
 **/
@EFragment(R.layout.fragment_info)
public class InfoFragment extends BaseFragment{

	public static final int PAGE_COUNT = 3;

	@ViewById(R.id.txt_my_messages)
	TextView txtMyMessages;
	@ViewById(R.id.ll_indicate_words)
	LinearLayout llIndicateWords;
	@ViewById(R.id.vp_fragment_info)
	BaseViewPager vpFragmentInfo;
	@ViewById(R.id.indicator_fragment_info)
	UnderlinePageIndicator indicatorFragmentInfo;
	@ViewById(R.id.txt_houses_activities)
	TextView txtHousesActivities;
	@ViewById(R.id.txt_unread_my_messages)
	TextView txtUnreadMyMessages;
	@ViewById(R.id.txt_unread_newcity_activities)
	TextView txtUnreadNewcityActivities;
	@ViewById(R.id.rl_newcity_activities)
	RelativeLayout rlNewcityActivities;
	@ViewById(R.id.rl_my_messages)
	RelativeLayout rlMyMessages;
	@ViewById(R.id.rl_unread_my_messages)
	RelativeLayout rlUnreadMyMessages;
	@ViewById(R.id.rl_unread_houses_activities)
	RelativeLayout rlUnreadHousesActivities;
	@ViewById(R.id.rl_houses_activities)
	RelativeLayout rlHousesActivities;
	@ViewById(R.id.txt_unread)
	TextView txtUnread;
	@ViewById(R.id.txt_newcity_activities)
	TextView txtNewcityActivities;
	@ViewById(R.id.rl_unread_newcity_activities)
	RelativeLayout rlUnreadNewcityActivities;

	@ViewById(R.id.iv_left)
	ImageView ivLeft;
	@ViewById(R.id.iv_center)
	ImageView ivCenter;
	@ViewById(R.id.iv_right)
	ImageView ivRight;
	@App
	MainApp mApp;

	InfoNewcityFragment newcityFragment = null;
	InfoHouseFragment houseFragment = null;
	InfoMymessageFragment mymessageFragment = null;

	List<TextView> textViews = null;

	private int currentItem = 0;
	
	@AfterViews
	void afterViews() {
		// 初始化三个标签
		initializeTab();
		// 渲染三个子页面
		initializeViewPager();
	}

	public void checkDataLoaded() {
		newcityFragment.checkDataLoaded();
		houseFragment.checkDataLoaded();
	}

	private void initializeTab() {
		textViews = new ArrayList<TextView>();

		textViews.add(txtNewcityActivities);
		textViews.add(txtHousesActivities);
		textViews.add(txtMyMessages);
	}

	public int getCurrentItem() {
		
		return currentItem;
	}
	
	private void initializeViewPager() {
		List<Fragment> pages = new ArrayList<Fragment>();
		newcityFragment = new InfoNewcityFragment_();
		houseFragment = new InfoHouseFragment_();
		mymessageFragment = new InfoMymessageFragment_();
		pages.add(newcityFragment);
		pages.add(houseFragment);
		pages.add(mymessageFragment);

		InfoFragmentPageAdapter adapter = new InfoFragmentPageAdapter(
				getChildFragmentManager());
		adapter.addAll(pages);
		vpFragmentInfo.setOffscreenPageLimit(PAGE_COUNT - 1);
		vpFragmentInfo.setAdapter(adapter);
		
		//默认是楼盘活动
		vpFragmentInfo.setCurrentItem(1, false);
		setImageViewVisiable();
		changeWordIndicator(1);
		ivCenter.setVisibility(View.VISIBLE);
		
		indicatorFragmentInfo.setSelectedColor(getResources().getColor(R.color.col_info_indicator));
		indicatorFragmentInfo.setViewPager(vpFragmentInfo);
		indicatorFragmentInfo.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				changeWordIndicator(position);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}

	void changeWordIndicator(int pos) {
		/*if (mApp.getCurrUser() == null && pos == 2) {
			
			vpFragmentInfo.setCurrentItem(1);
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}*/
       if (!mApp.isLogin() && pos == 2) {
			vpFragmentInfo.setCurrentItem(1);
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}
		for (int i = 0; i < textViews.size(); ++i)
			textViews.get(i).setTextColor(getResources().getColor(i == pos ? R.color.col_words_active_white : R.color.txt_color_black));
		if (pos == 1)
			rlUnreadHousesActivities.setVisibility(View.INVISIBLE);
		if (pos == 2)
			rlUnreadMyMessages.setVisibility(View.INVISIBLE);
	}

	public void setRlUnreadHousesActivities() {
		rlUnreadHousesActivities.setVisibility(View.VISIBLE);
	}

	public void setRlUnreadMyMessages(String count) {
		rlUnreadMyMessages.setVisibility(View.VISIBLE);
		txtUnreadMyMessages.setText(count);
	}

	// 顶部标签点击事件组
	@Click
	public void rlNewcityActivities() {
		vpFragmentInfo.setCurrentItem(0, false);
		setImageViewVisiable();
		ivLeft.setVisibility(View.VISIBLE);
		//newcityFragment.reDoLoad();	
		currentItem = 0;
		
	}
	@Click
	public void rlHousesActivities() {
		vpFragmentInfo.setCurrentItem(1, false);
		setImageViewVisiable();
		ivCenter.setVisibility(View.VISIBLE);
		//houseFragment.reDoLoad();
		currentItem = 1;
	}
	@Click
	public void rlMyMessages() {
		
		/*if (mApp.getCurrUser() == null) {
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}*/
		if (!mApp.isLogin()) {//未登录
			LoginActivity_.intent(this).startForResult(ReqCode.SIGN_IN);
			return;
		}
		currentItem = 2;
		vpFragmentInfo.setCurrentItem(2, false);
		setImageViewVisiable();
		ivRight.setVisibility(View.VISIBLE);
		mymessageFragment.reDoLoad();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == ReqCode.SIGN_IN) {
			mymessageFragment.loadMyMessageData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//三角图标的隐藏
	public void setImageViewVisiable() {
		ivLeft.setVisibility(View.INVISIBLE);
		ivCenter.setVisibility(View.INVISIBLE);
		ivRight.setVisibility(View.INVISIBLE);
	}
}

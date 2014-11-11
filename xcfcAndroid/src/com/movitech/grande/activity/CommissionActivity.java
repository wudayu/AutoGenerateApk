package com.movitech.grande.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.adapter.MainActivityPageAdapter;
import com.movitech.grande.constant.ExtraNames;
import com.movitech.grande.fragment.CommissionGrantFragment;
import com.movitech.grande.fragment.CommissionSuccessFragment;
import com.movitech.grande.fragment.CommissionWaitFragment;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.UnderlinePageIndicator;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.fragment.CommissionGrantFragment_;
import com.movitech.grande.fragment.CommissionSuccessFragment_;
import com.movitech.grande.fragment.CommissionWaitFragment_;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月19日 下午5:47:04
 * 类说明
 */
@EActivity(R.layout.activity_my_commission)
public class CommissionActivity extends BaseActivity{
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
	
	@ViewById(R.id.indicator_fragment_commission)
	UnderlinePageIndicator indicatorFragmentCommission;
	@ViewById(R.id.vp_fragment_commission)
	BaseViewPager vpFragmentCommission;
	
	Bundle bundle = null;
	List<TextView> textViews = null;
	int currentItem;
	
	CommissionWaitFragment commissionWaitFragment = null;
	CommissionGrantFragment commissionGrantFragment = null;
	CommissionSuccessFragment commissionSuccessFragment = null;
	
	
	@AfterViews
	void afterViews() {
		// 标签
		initializeTab();
		// 获取显示的页
		initCurrentItem();
		// 页面的初始化
		initPages();
	}
	
	private void initPages() {
		
		List<Fragment> pages = new ArrayList<Fragment>();
		commissionWaitFragment = new CommissionWaitFragment_();
		commissionGrantFragment = new CommissionGrantFragment_();
		commissionSuccessFragment = new CommissionSuccessFragment_();
		if (bundle != null && !("".equals(bundle.getString(ExtraNames.ORG_TO_COMMISSION)))) {
			commissionWaitFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_COMMISSION));
			commissionGrantFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_COMMISSION));
			commissionSuccessFragment.setUser(getIntent().getStringExtra(ExtraNames.ORG_TO_COMMISSION));
		}
		pages.add(commissionWaitFragment);
		pages.add(commissionGrantFragment);
     	pages.add(commissionSuccessFragment);
		
		MainActivityPageAdapter adapter = new MainActivityPageAdapter(
				getSupportFragmentManager());
		adapter.addAll(pages);
		vpFragmentCommission.setOffscreenPageLimit(PAGE_COUNT - 1);
		vpFragmentCommission.setAdapter(adapter);
		vpFragmentCommission.setOnTouchListener(null);
		vpFragmentCommission.setCurrentItem(currentItem);
		initTextColor(currentItem);
		indicatorFragmentCommission.setViewPager(vpFragmentCommission);
		indicatorFragmentCommission.setOnPageChangeListener(new VPOnPageChangeListener());
	}

	private void initializeTab() {
		textViews = new ArrayList<TextView>();
		textViews.add(txtCommissionWait);
		textViews.add(txtCommissionGrant);
		textViews.add(txtCommissionOk);
	}
	
	private void initCurrentItem() {
		bundle = getIntent().getExtras();
		if (bundle != null) {
			currentItem = bundle.getInt(ExtraNames.MINE_CURRENT_COMMISSION);
		} else {
			currentItem = 0;
		}
	}
	void initTextColor(int pos) {
		for (int i = 0; i < textViews.size(); ++i)
			textViews.get(i).setTextColor(
					getResources().getColor(
							i == pos ? R.color.letter_main_red
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
			vpFragmentCommission.setCurrentItem(currentItem);
			
			switch (currentItem) {
			case 0:
				commissionWaitFragment.reLoadData();
				break;
			case 1:
				commissionGrantFragment.reLoadData();
				break;
			case 2:
				commissionSuccessFragment.reLoadData();
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
		}
		
		@Click
		void rlCommissionWait() {
			currentItem = 0;
			vpFragmentCommission.setCurrentItem(0);
			commissionWaitFragment.reLoadData();
		}

		@Click
		void rlCommissionGrant() {
			currentItem = 1;
			vpFragmentCommission.setCurrentItem(1);
			commissionGrantFragment.reLoadData();
		}

		@Click
		void rlCommissionOk() {
			currentItem = 2;
			vpFragmentCommission.setCurrentItem(2);
			commissionSuccessFragment.reLoadData();
		}

		@Click
		void ivBack() {
			CommissionActivity.this.finish();
		}

}

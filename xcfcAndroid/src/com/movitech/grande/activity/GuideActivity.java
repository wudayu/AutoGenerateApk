package com.movitech.grande.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import android.view.View.OnClickListener;

import com.movitech.grande.adapter.ViewPagerAdapter;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.MainActivity_;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 9, 2014 5:58:10 PM
 * @Description: This is David Wu's property.
 *
 **/
@EActivity(R.layout.activity_guide)
public class GuideActivity extends BaseActivity {

//	@ViewById(R.id.indicator)
//	CirclePageIndicator indicator;
	@ViewById(R.id.vp_guide)
	BaseViewPager vpGuide;

	@AfterViews
	void afterView() {
		initViewPager();
	}

	void initViewPager() {
		List<View> views = new ArrayList<View>();
		View viewA = new View(this);
		View viewB = new View(this);
		//View viewC = new View(this);
		viewA.setBackgroundResource(R.drawable.guide01);
		viewB.setBackgroundResource(R.drawable.guide02);
		//viewC.setBackgroundResource(R.drawable.guide03);
		
		viewB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity_.intent(GuideActivity.this).start();
				finish();
			}
		});
		views.add(viewA);
		views.add(viewB);
		//views.add(viewC);
		
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		adapter.init();
		adapter.addAll(views);

		vpGuide.setAdapter(adapter);
		//indicator.setViewPager(vpGuide);
	}
}

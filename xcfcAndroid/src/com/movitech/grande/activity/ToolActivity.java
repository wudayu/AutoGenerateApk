package com.movitech.grande.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.CommissionEarnActivity_;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 27, 2014 10:39:13 AM
 * @Description: This is David Wu's property.
 *
 **/
@EActivity(R.layout.activity_others_tool)
public class ToolActivity extends BaseActivity {

	@ViewById(R.id.rl_top)
	RelativeLayout rlTop;
	@ViewById(R.id.iv_back)
	ImageView ivBack;
	@ViewById(R.id.iv_house_price)
	ImageView ivHousePrice;
	@ViewById(R.id.iv_house_city)
	ImageView ivHouseCity;
	@ViewById(R.id.iv_house_loan)
	ImageView ivHouseLoan;

	@Click
	void ivHousePrice() {
		CommissionEarnActivity_.intent(this).start();
	}

	@Click
	void ivHouseCity() {
		CommissionEarnActivity_.intent(this).start();
	}

	@Click
	void ivHouseLoan() {
		CommissionEarnActivity_.intent(this).start();
	}

	@Click
	void ivBack() {
		finish();
	}
}

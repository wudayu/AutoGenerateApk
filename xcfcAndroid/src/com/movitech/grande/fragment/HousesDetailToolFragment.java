package com.movitech.grande.fragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.haerbin.R;
import com.movitech.grande.activity.CommissionEarnActivity_;

/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 17, 2014 2:24:12 PM
 * @Description: This is David Wu's property.
 *
 **/
@Deprecated
@EFragment(R.layout.fragment_houses_detail_tool)
public class HousesDetailToolFragment extends BaseFragment {

	@ViewById(R.id.iv_house_price)
	ImageView ivHousePrice;
	@ViewById(R.id.iv_house_city)
	ImageView ivHouseCity;
	@ViewById(R.id.rl_house_city)
	RelativeLayout rlHouseCity;
	@ViewById(R.id.txt_house_loan)
	TextView txtHouseLoan;
	@ViewById(R.id.iv_house_loan)
	ImageView ivHouseLoan;
	@ViewById(R.id.txt_house_price)
	TextView txtHousePrice;
	@ViewById(R.id.rl_house_price)
	RelativeLayout rlHousePrice;
	@ViewById(R.id.rl_house_loan)
	RelativeLayout rlHouseLoan;
	@ViewById(R.id.txt_house_city)
	TextView txtHouseCity;


	@Click
	void ivHousePrice() {
		CommissionEarnActivity_.intent(getActivity()).start();
	}

	@Click
	void ivHouseCity() {
		CommissionEarnActivity_.intent(getActivity()).start();
	}

	@Click
	void ivHouseLoan() {
		CommissionEarnActivity_.intent(getActivity()).start();
	}
}

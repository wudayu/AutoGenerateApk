package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: July 2, 2014 6:43:30 PM
 * @Description: This is David Wu's property.
 *
 **/
public class InfoFragmentPageAdapter extends FragmentPagerAdapter {
	List<Fragment> fragments = null;

	public InfoFragmentPageAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<Fragment>();
	}

	public void addAll(List<Fragment> pages) {
		fragments.clear();
		fragments.addAll(pages);
		this.notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}

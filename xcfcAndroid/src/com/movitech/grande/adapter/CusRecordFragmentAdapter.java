package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CusRecordFragmentAdapter extends FragmentPagerAdapter{
	
	List<Fragment> fragments = null;

	public CusRecordFragmentAdapter(FragmentManager fm) {
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

package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 12, 2014 11:04:47 PM
 * @Description: This is David Wu's property.
 *
 **/
public class ViewPagerAdapter extends PagerAdapter {

	private List<View> views;

	/**
	 * 初始化
	 */
	public void init() {
		this.views = new ArrayList<View>();
	}

	public void addAll(List<View> datas) {
		this.views.addAll(datas);
		this.notifyDataSetChanged();
	}
	
	public void removeAll() {
		this.views.clear();
		this.notifyDataSetChanged();
	}

	public int getCount() {
		return views.size();
	}

	/**
	 * 获取View
	 * 
	 * @param p
	 * @return
	 */
	public View getView(int p) {
		return views.get(p);
	}

	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(views.get(position));
		return views.get(position);
	}

	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}

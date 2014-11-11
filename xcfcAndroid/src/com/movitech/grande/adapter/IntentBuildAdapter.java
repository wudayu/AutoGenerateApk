package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.haerbin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月1日 上午11:11:06
 * 类说明
 */
public class IntentBuildAdapter extends BaseAdapter{
	private Context context;
	private List<XcfcHouse> houses;
	private int resource;
	private LayoutInflater inflater;
	private String isSelectedHouse;

	public IntentBuildAdapter(Context context, int resource, String isSelectedHouse) {
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.isSelectedHouse = isSelectedHouse;
		//addData(houses);
	}

	public void addData(XcfcHouse[] houses) {
		this.houses = new ArrayList<XcfcHouse>();
		for (int i = 0; i < houses.length; ++i) {
			this.houses.add(houses[i]);
		}
	}
	
	//加载更多楼盘列表
	public void addItem(XcfcHouse[] houseItem){
		for (int i = 0; i < houseItem.length; ++i) {
			this.houses.add(houseItem[i]);
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return houses == null ? 0 : houses.size();
	}

	@Override
	public Object getItem(int position) {
		return houses == null ? null : houses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (houses == null)
			return null;
		ViewCache cache = null;
		if (convertView == null) {
			convertView = inflater.inflate(resource, null);
			cache = new ViewCache();
			cache.ivSelected = (ImageView) convertView.findViewById(R.id.iv_selected);
			cache.tvBuildName = (TextView) convertView.findViewById(R.id.tv_build_name);
			cache.tvBuildDistrict = (TextView) convertView.findViewById(R.id.tv_build_district);

			convertView.setTag(cache);
		} else {
			cache = (ViewCache) convertView.getTag();
		}

		XcfcHouse house = houses.get(position);	
		if (isSelectedHouse.equals(house.getName())) {
			cache.tvBuildName.setTextColor(context.getResources().getColor(R.color.letter_green_deep_standard));
			cache.tvBuildDistrict.setTextColor(context.getResources().getColor(R.color.letter_green_deep_standard));
			cache.ivSelected.setVisibility(View.VISIBLE);
		} else {
			cache.tvBuildName.setTextColor(context.getResources().getColor(R.color.letter_grey_deep_6));
			cache.tvBuildDistrict.setTextColor(context.getResources().getColor(R.color.letter_grey_deep_6));
			cache.ivSelected.setVisibility(View.GONE);
		}
		// FIXME use the src
		cache.house = house;
		cache.tvBuildName.setText(house.getName());
		cache.tvBuildDistrict.setText("["+house.getArea()+"]");	
		return convertView;
	}

	public final class ViewCache {
		public XcfcHouse house;
		public ImageView ivSelected;
		public TextView tvBuildDistrict;	// area
		public TextView tvBuildName;	// 
		
	}

}

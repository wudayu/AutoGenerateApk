package com.movitech.grande.adapter;


import com.movitech.grande.haerbin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChooseCitySubAdapter extends BaseAdapter {
	
	private String[] childrenCity;
	private Context mContext;
	
	public ChooseCitySubAdapter(Context context, String[] childrenCity) {
		this.mContext = context;
		this.childrenCity=childrenCity;
	}

	@Override
	public int getCount() {
		
		return childrenCity.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return childrenCity[position];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_subcitys, null);
			holder.tvCity = (TextView) convertView.findViewById(R.id.tvCity);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		String city  =getItem(position);
		holder.tvCity.setText(city);
		
		return convertView;
	}

	class ViewHolder {
		public TextView tvCity;
		
	}

}

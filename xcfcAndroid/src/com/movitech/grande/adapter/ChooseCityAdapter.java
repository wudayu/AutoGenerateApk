package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import com.movitech.grande.haerbin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ChooseCityAdapter extends BaseAdapter {
	
	private List<String> citys;
	private Context mContext;
	
	public ChooseCityAdapter(Context context, String[] citystr) {
		this.mContext = context;
		if (citystr != null) {
			citys = new ArrayList<String>();
			for (int i = 0; i < citystr.length; i++) {
				citys.add(citystr[i]);
			}
		}
	}

	@Override
	public int getCount() {
		return citys == null ? 0 : citys.size();
	}

	@Override
	public Object getItem(int position) {
		return citys == null ? null : citys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_city, null);
			holder.llCity = (LinearLayout) convertView.findViewById(R.id.ll_city_name_bg);
			holder.ivCity = (ImageView) convertView.findViewById(R.id.iv_city);
			holder.tvCityChina = (TextView) convertView.findViewById(R.id.tv_city_name_china);
			holder.tvCityEng = (TextView) convertView.findViewById(R.id.tv_city_name_eng);
			holder.ivCityChoosed = (ImageView) convertView.findViewById(R.id.iv_city_choosed);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvCityChina.setText(citys.get(position));
		holder.tvCityEng.setText(citys.get(position));
		return convertView;
	}
	
	class ViewHolder {
		public LinearLayout llCity;
		public ImageView ivCity;
		public ImageView ivCityChoosed;
		public TextView tvCityChina;
		public TextView tvCityEng;
	}
	
}

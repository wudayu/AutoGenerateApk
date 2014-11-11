package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import com.movitech.grande.haerbin.R;
import com.movitech.grande.model.XcfcDistrict;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseTwoCityAdapter extends BaseAdapter {
	private Context context;
	private List<XcfcDistrict> district;
	private int resource;
	private LayoutInflater inflater;
	private String isSelectedDistrict;

	public ChooseTwoCityAdapter(Context context, XcfcDistrict[] district,
			int resource, String isSelectedDistrict) {
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.isSelectedDistrict = isSelectedDistrict;
		addData(district);
	}

	private void addData(XcfcDistrict[] district) {
		this.district = new ArrayList<XcfcDistrict>();
		for (int i = 0; i < district.length; ++i) {
			this.district.add(district[i]);
		}
	}

	@Override
	public int getCount() {
		return district == null ? 0 : district.size();
	}

	@Override
	public Object getItem(int position) {
		return district == null ? null : district.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (district == null)
			return null;
		ViewCache cache = null;
		if (convertView == null) {
			convertView = inflater.inflate(resource, null);
			cache = new ViewCache();
			cache.ivSelected = (ImageView) convertView
					.findViewById(R.id.iv_selected);
			cache.tvSelectedDistricts = (TextView) convertView
					.findViewById(R.id.tv_city_name);
			convertView.setTag(cache);
		} else {
			cache = (ViewCache) convertView.getTag();
		}
		XcfcDistrict districts = district.get(position);
		if (isSelectedDistrict.equals(districts.getName())) {
			cache.tvSelectedDistricts.setTextColor(context.getResources().getColor(R.color.letter_green_deep_standard));
			cache.ivSelected.setVisibility(View.VISIBLE);
		} else {
			cache.tvSelectedDistricts.setTextColor(context.getResources().getColor(R.color.letter_grey_deep_6));
			cache.ivSelected.setVisibility(View.GONE);
		}
		// FIXME use the src
		cache.district = districts;
		cache.tvSelectedDistricts.setText(districts.getName());
		return convertView;
	}

	public final class ViewCache {
		public XcfcDistrict district;
		public ImageView ivSelected;
		public TextView tvSelectedDistricts; // area
	}
}

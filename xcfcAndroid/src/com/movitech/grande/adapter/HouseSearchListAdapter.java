package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.utils.ConvertStrToArray;
import com.movitech.grande.haerbin.R;

/**
 * 
 * @author Warkey.Song
 * 
 */
public class HouseSearchListAdapter extends BaseAdapter {
	
	private String [] buildType = null;
	private List<XcfcHouse> houses;
	private int resource;
	private LayoutInflater inflater;
	private Context context;
	private IImageUtils imageUtils;

	public HouseSearchListAdapter(Context context, XcfcHouse[] houses,
			int resource, IImageUtils utils) {
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.imageUtils = utils;
		addData(houses);
	}

	private void addData(XcfcHouse[] houses) {
		this.houses = new ArrayList<XcfcHouse>();

		for (int i = 0; i < houses.length; ++i) {
			this.houses.add(houses[i]);
		}
	}

	public void addItems(XcfcHouse[] xcfcHouses){
		for (int i = 0; i < xcfcHouses.length; ++i) {
			this.houses.add(xcfcHouses[i]);
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

			cache.ivRecommendHouse = (ImageView) convertView.findViewById(R.id.iv_layout_recommend_houses);
			cache.txtName = (TextView) convertView.findViewById(R.id.txt_name_layout_recommend_houses);
			cache.txtSection = (TextView) convertView.findViewById(R.id.txt_section_layout_recommend_houses);
			cache.txtDetail = (TextView) convertView.findViewById(R.id.txt_detail_layout_recommend_houses);
			cache.txtPrivilege = (TextView) convertView.findViewById(R.id.txt_privilege_layout_recommend_houses);
			cache.txtPrice = (TextView) convertView.findViewById(R.id.txt_price_layout_recommend_houses);
			cache.ivRecommendFlag = (ImageView) convertView.findViewById(R.id.iv_recommend_flag);
			cache.rlSpecialityFirst = (RelativeLayout) convertView.findViewById(R.id.rl_speciality_first_layout_recommend_houses);
			cache.txtSpecialityFirst = (TextView) convertView.findViewById(R.id.txt_speciality_first_layout_recommend_houses);
			cache.rlSpecialitySecond = (RelativeLayout) convertView.findViewById(R.id.rl_speciality_second_layout_recommend_houses);
			cache.txtSpecialitySecond = (TextView) convertView.findViewById(R.id.txt_speciality_second_layout_recommend_houses);
			cache.rlSpecialityFirst = (RelativeLayout) convertView.findViewById(R.id.rl_speciality_first_layout_recommend_houses);
			cache.rlSpecialityThird= (RelativeLayout) convertView.findViewById(R.id.rl_speciality_third_layout_recommend_houses);
			cache.txtSpecialityThird = (TextView) convertView.findViewById(R.id.txt_speciality_third_layout_recommend_houses);
			convertView.setTag(cache);
		} else {
			cache = (ViewCache) convertView.getTag();
		}

		XcfcHouse house = houses.get(position);

		cache.house = house;
		imageUtils.loadRoundCornerImage(house.getPicsrc(), cache.ivRecommendHouse);
		cache.txtName.setText(house.getName());
		cache.txtSection.setText("[" + house.getArea() + "]");
		cache.txtDetail.setText(house.getSalePoint());
		cache.txtPrivilege.setText(house.getDiscount());
		if (!house.getPricePerSuiteScope().equals("")) {
			cache.txtPrice.setText(house.getPricePerSuiteScope()
					+ context.getResources().getString(R.string.str_square_total_unit));
		} else if (!house.getPrice().equals("")) {
			cache.txtPrice.setText(house.getPrice()
					+ context.getResources().getString(R.string.str_square));
		}
		if (house.getIsRecommand().equals("0")) {
			cache.ivRecommendFlag.setVisibility(View.VISIBLE);
		} else {
			cache.ivRecommendFlag.setVisibility(View.GONE);
		}
		cache.rlSpecialityFirst.setVisibility(View.GONE);
		cache.txtSpecialityFirst.setText("");
		cache.rlSpecialitySecond.setVisibility(View.GONE);
		cache.txtSpecialitySecond.setText("");
		cache.rlSpecialityThird.setVisibility(View.GONE);
		cache.txtSpecialityThird.setText("");
		buildType = ConvertStrToArray.convertStrToArray(house.getBuildingType());
		if (buildType == null) {
			cache.rlSpecialityFirst.setVisibility(View.VISIBLE);
			cache.txtSpecialityFirst.setText(house.getBuildingType());
		}else if (buildType.length == 2) {
			cache.rlSpecialityFirst.setVisibility(View.VISIBLE);
			cache.txtSpecialityFirst.setText(buildType[0]);
			cache.rlSpecialitySecond.setVisibility(View.VISIBLE);
			cache.txtSpecialitySecond.setText(buildType[1]);
		}else if (buildType.length >= 3) {
			cache.rlSpecialityFirst.setVisibility(View.VISIBLE);
			cache.txtSpecialityFirst.setText(buildType[0]);
			cache.rlSpecialitySecond.setVisibility(View.VISIBLE);
			cache.txtSpecialitySecond.setText(buildType[1]);
			cache.rlSpecialityThird.setVisibility(View.VISIBLE);
			cache.txtSpecialityThird.setText(buildType[2]);
		}		
		return convertView;
	}

	public final class ViewCache {
		public XcfcHouse house;
		public ImageView ivRecommendHouse;
		public TextView txtName; // name
		public TextView txtSection; // area
		public TextView txtDetail; // type
		public TextView txtPrivilege; // discount
		public TextView txtPrice; // pricePerSuiteScope
		public ImageView ivRecommendFlag;// isRecommend
		public RelativeLayout rlSpecialityFirst;// buildType
		public TextView txtSpecialityFirst;
		public RelativeLayout rlSpecialitySecond;// buildType
		public TextView txtSpecialitySecond;
		public RelativeLayout rlSpecialityThird;// buildType
		public TextView txtSpecialityThird;
	}
}

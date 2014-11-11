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

import com.movitech.grande.constant.HouseDetailImageSourceType;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.utils.ConvertStrToArray;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月4日 下午4:56:05
 * 类说明
 */
public class FocusBuildAdapter extends BaseAdapter {
	private String [] buildType = null;
	private List<XcfcHouseDetail> houses;
	private int resource;
	private LayoutInflater inflater;
	private Context context;
    private ImageUtils imageUtils;
	public FocusBuildAdapter(Context context, XcfcHouseDetail[] houses, int resource, ImageUtils imageUtils) {
		this.context = context;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.imageUtils = imageUtils;
		addData(houses);
	}

	private void addData(XcfcHouseDetail[] houses) {
		this.houses = new ArrayList<XcfcHouseDetail>();

		for (int i = 0; i < houses.length; ++i) {
			this.houses.add(houses[i]);
		}
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
			cache.rlSpecialityThird= (RelativeLayout) convertView.findViewById(R.id.rl_speciality_third_layout_recommend_houses);
			cache.txtSpecialityThird = (TextView) convertView.findViewById(R.id.txt_speciality_third_layout_recommend_houses);
			convertView.setTag(cache);
		} else {
			cache = (ViewCache) convertView.getTag();
		}

		XcfcHouseDetail house = houses.get(position);

		cache.house = house;
		List<String> housePicUrlList = house.getImageAddressesBySourceType(HouseDetailImageSourceType.THUMBNAIL);
		if (housePicUrlList != null && housePicUrlList.size() > 0 ) {
			imageUtils.loadRoundCornerImage(housePicUrlList.get(0), cache.ivRecommendHouse);
		}
		
		cache.txtName.setText(house.getName());
		cache.txtSection.setText("["+house.getArea()+"]");
		cache.txtDetail.setText(house.getSalePoint());
		cache.txtPrivilege.setText(house.getDiscount());
		if (house.getPricePerSuiteScope() != null && !house.getPricePerSuiteScope().equals("")) {
			cache.txtPrice.setText(house.getPricePerSuiteScope() + context.getResources().getString(R.string.str_square_total_unit));
		} else if (house.getPrice() != null && !house.getPrice().equals("")) {
			cache.txtPrice.setText(house.getPrice() + context.getResources().getString(R.string.str_square));
		}
		if (house.getIsRecommand().equals("0")) {
			cache.ivRecommendFlag.setVisibility(View.VISIBLE);
		}else {
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
		public XcfcHouseDetail house;
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


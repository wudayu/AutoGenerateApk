package com.movitech.grande.adapter;

import java.util.List;

import com.movitech.grande.haerbin.R;
import com.movitech.grande.model.CompanyModel;
import com.movitech.grande.views.MutilLineTextViewLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class ChooseCitysAdapter extends BaseAdapter {
	
	private List<CompanyModel> cmps;
	private Context mContext;
	
	public ChooseCitysAdapter(Context context, List<CompanyModel> cmps) {
		this.mContext = context;
		this.cmps=cmps;
	}

	@Override
	public int getCount() {
		return cmps == null ? 0 : cmps.size();
	}

	@Override
	public CompanyModel getItem(int position) {
		return cmps == null ? null : cmps.get(position);
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_citys, null);
			holder.ivCity = (ImageView) convertView.findViewById(R.id.iv_city);
			holder.tvCityChina = (TextView) convertView.findViewById(R.id.tvCityChina);
			holder.subcmps = (LinearLayout) convertView.findViewById(R.id.subcmps);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		CompanyModel  model = getItem(position);
		holder.tvCityChina.setText(model.getCmpName());
		holder.ivCity.setBackgroundResource(model.getIconResid());
		holder.subcmps.removeAllViews();
		holder.subcmps.addView(new MutilLineTextViewLayout(mContext,model.getChildrenCity()),
				 new LinearLayout.LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		return convertView;
	}
	
	class ViewHolder {
	
		public ImageView ivCity;
		public TextView tvCityChina;
		public LinearLayout subcmps;
	}
	
}

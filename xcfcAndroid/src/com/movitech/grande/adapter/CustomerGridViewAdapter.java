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

import com.movitech.grande.model.XcfcMyCustomer;
import com.movitech.grande.haerbin.R;

public class CustomerGridViewAdapter extends BaseAdapter {

	private List<XcfcMyCustomer> customers;
	private Context context;
	int[] shaps = { R.drawable.shape_customer_avater_bg,
			R.drawable.shape_customer_avater_blue_bg,
			R.drawable.shape_customer_avater_green_bg,
			R.drawable.shape_customer_avater_orange_bg,
			R.drawable.shape_customer_avater_purple_bg,
			R.drawable.shape_customer_avater_red_bg };
	int[] colors = { R.color.shape_customer_avater_bg_color,
			R.color.shape_customer_avater_blue_bg_color,
			R.color.shape_customer_avater_green_bg_color,
			R.color.shape_customer_avater_orange_bg_color,
			R.color.shape_customer_avater_purple_bg_color,
			R.color.shape_customer_avater_red_bg_color };

	public CustomerGridViewAdapter(Context context, XcfcMyCustomer[] customers) {
		this.context = context;
		addData(customers);
	}

	public void addItems(XcfcMyCustomer[] customers) {
		if (this.customers == null) {
			return;
		}
		for (int i = 0; i < customers.length; ++i) {
			this.customers.add(customers[i]);
		}
		this.notifyDataSetChanged();
	}

	private void addData(XcfcMyCustomer[] customers) {
		this.customers = new ArrayList<XcfcMyCustomer>();

		for (int i = 0; i < customers.length; ++i) {
			this.customers.add(customers[i]);
		}
	}

	@Override
	public int getCount() {
		return customers == null ? 0 : customers.size();
	}

	@Override
	public Object getItem(int position) {
		return customers == null ? null : customers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_gridview_activity_customer, null);
			holder.rl_customer_avater = (RelativeLayout) convertView
					.findViewById(R.id.rl_customer_avater);
			holder.iv_customer_avater = (ImageView) convertView
					.findViewById(R.id.iv_customer_avater);
			holder.rl_customer_build = (RelativeLayout) convertView
					.findViewById(R.id.rl_customer_build);
			holder.tv_build_num = (TextView) convertView
					.findViewById(R.id.tv_build_num);
			holder.tv_customer_name = (TextView) convertView
					.findViewById(R.id.tv_gv_customer_name);
			holder.tv_customer_tel = (TextView) convertView
					.findViewById(R.id.tv_gv_customer_tel);
			holder.tv_gv_build_name = (TextView) convertView
					.findViewById(R.id.tv_gv_build_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		 
		XcfcMyCustomer customer = (XcfcMyCustomer) getItem(position);
		holder.customer = customer;
		holder.rl_customer_avater.setBackgroundResource(shaps[position % 6]);
		holder.tv_build_num.setBackgroundResource(shaps[position % 6]);
		String sex = customer.getGender();
		if ("1".equals(sex)) {
			holder.iv_customer_avater.setImageResource(R.drawable.ico_boy);
		} else if ("2".equals(sex)) {
			holder.iv_customer_avater.setImageResource(R.drawable.ico_girl);
		} else {
			holder.iv_customer_avater.setImageResource(R.drawable.ico_boy);
		}
		holder.tv_customer_name.setTextColor(context.getResources().getColor(
				colors[position % 6]));
		holder.tv_gv_build_name.setTextColor(context.getResources().getColor(
				colors[position % 6]));
		holder.tv_customer_name.setText(customer.getName());
		holder.tv_customer_tel.setText(customer.getPhone());
		holder.tv_build_num.setText(customer.getBuildingNum());
		holder.tv_gv_build_name.setText("(" + customer.getBuildingName() + ")");
		return convertView;
	}

	public final class ViewHolder {
		public XcfcMyCustomer customer;
		RelativeLayout rl_customer_avater;
		ImageView iv_customer_avater;
		RelativeLayout rl_customer_build;
		TextView tv_build_num;
		TextView tv_customer_name;
		TextView tv_customer_tel;
		TextView tv_gv_build_name;
	}
}

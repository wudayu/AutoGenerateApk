package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.movitech.grande.model.XcfcIntegral;
import com.movitech.grande.haerbin.R;

public class IntegralHistoryAdapter extends BaseAdapter {

	private List<XcfcIntegral> integrals;
	private Context context;

	public IntegralHistoryAdapter(Context context, XcfcIntegral[] integrals) {
		this.context = context;
		addData(integrals);
	}

	private void addData(XcfcIntegral[] histories) {
		this.integrals = new ArrayList<XcfcIntegral>();

		for (int i = 0; i < histories.length; ++i) {
			this.integrals.add(histories[i]);
		}
	}

	@Override
	public int getCount() {
		return integrals == null ? 0 : integrals.size();
	}

	@Override
	public Object getItem(int position) {
		return integrals == null ? null : integrals.get(position);
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
					R.layout.item_fragment_integral, null);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_note = (TextView) convertView.findViewById(R.id.tv_note);
			holder.iv_cicle = (ImageView) convertView
					.findViewById(R.id.iv_cicle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		XcfcIntegral integral = (XcfcIntegral) getItem(position);
		// (1.app下载登录，2.app登录,3.推荐后台确认，4.预约后台确认)
		String title = "";
		if ("1".equals(integral.getSourceType())) {
			title = "app注册";
		} else if ("2".equals(integral.getSourceType())) {
			title = "app登录";
		} else if ("3".equals(integral.getSourceType())) {
			title = "预约后台确认";
		} else if ("4".equals(integral.getSourceType())) {
			title = "推荐后台确认";
		} else if ("5".equals(integral.getSourceType())) {
			title = "案场签到";
		} else if ("6".equals(integral.getSourceType())) {
			title = "每日签到";
		} else if ("7".equals(integral.getSourceType())) {
			title = "定向资源介绍";
		} else if ("8".equals(integral.getSourceType())) {
			title = "实名认证为经纪人";
		} else if ("9".equals(integral.getSourceType())) {
			title = "分享文章";
		} else if ("10".equals(integral.getSourceType())) {
			title = "房子成交数量2-4套";
		} else if ("11".equals(integral.getSourceType())) {
			title = "房子成交数量大于4套";
		}
		holder.tv_title.setText(title);
		holder.tv_note.setText("+" + integral.getIntegral());
		holder.tv_date.setText(integral.getCreateTime());

		if (position != 0) {
			holder.iv_cicle.setImageResource(R.drawable.timeline_point);
		} else {
			holder.iv_cicle.setImageResource(R.drawable.timeline_point_on);
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_date;
		TextView tv_title;
		TextView tv_note;
		ImageView iv_cicle;
	}
}

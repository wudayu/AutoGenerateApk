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

import com.movitech.grande.model.ClientBuildingRelationsHistory;
import com.movitech.grande.haerbin.R;

public class CustomerHistoryAdapter extends BaseAdapter {

	private List<ClientBuildingRelationsHistory> histories;
	private Context context;

	public CustomerHistoryAdapter(Context context,
			ClientBuildingRelationsHistory[] histories) {
		this.context = context;
		addData(histories);
	}

	private void addData(ClientBuildingRelationsHistory[] histories) {
		this.histories = new ArrayList<ClientBuildingRelationsHistory>();

		for (int i = 0; i < histories.length; ++i) {
			this.histories.add(histories[i]);
		}
	}

	@Override
	public int getCount() {
		return histories == null ? 0 : histories.size();
	}

	@Override
	public Object getItem(int position) {
		return histories == null ? null : histories.get(position);
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
					R.layout.item_fragment_customer_record, null);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.tv_note = (TextView) convertView.findViewById(R.id.tv_note);
			holder.iv_cicle = (ImageView) convertView
					.findViewById(R.id.iv_cicle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ClientBuildingRelationsHistory history = (ClientBuildingRelationsHistory) getItem(position);

		
		String dateAndTime = history.getCreateTime();
		if (dateAndTime != null) {
			if (dateAndTime.contains(" ")) {
				int index = dateAndTime.indexOf(" ");
				dateAndTime = dateAndTime.substring(0, index);
			}
		}
		holder.tv_date.setText(dateAndTime);
		if (history.getClientStatus() != null) {
			String status = clientStatus(history.getClientStatus());
			holder.tv_status.setText(status);
		}else 
			holder.tv_status.setText("");
		
		if (history.getRemark() == null) {
			holder.tv_note.setText(context.getString(R.string.client_note_txt) + context.getString(R.string.client_note_str));
		}else
			holder.tv_note.setText(context.getString(R.string.client_note_txt) + history.getRemark());
		if (position != 0) {
			holder.iv_cicle.setImageResource(R.drawable.timeline_point);
		} else {
			holder.iv_cicle.setImageResource(R.drawable.timeline_point_on);
		}
		return convertView;
	}

	class ViewHolder {
		TextView tv_date;
		TextView tv_status;
		TextView tv_note;
		ImageView iv_cicle;
	}

	private String clientStatus(String statusCode) {
		
		if (statusCode.equals(context.getString(R.string.client_status_ten))) {
			return context.getString(R.string.client_status_ten_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_twenty))) {
			return context.getString(R.string.client_status_twenty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_thirty))) {
			return context.getString(R.string.client_status_thirty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_forty))) {
			return context.getString(R.string.client_status_forty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_fifty))) {
			return context.getString(R.string.client_status_fifty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_sixty))) {
			return context.getString(R.string.client_status_sixty_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_seventy))) {
			return context.getString(R.string.client_status_seventy_str);
		} else if (statusCode.equals(context
				.getString(R.string.client_status_hundred))) {
			return context.getString(R.string.client_status_hundred_str);
		}else if (statusCode.equals(context
				.getString(R.string.client_status_minus_ten))) {
			return context.getString(R.string.client_status_minus_ten_str);
			
		}else 
			return context.getString(R.string.client_status_other_str);
		
	}
}

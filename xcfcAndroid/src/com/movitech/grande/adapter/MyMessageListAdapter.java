package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.movitech.grande.model.XcfcMyMessage;
import com.movitech.grande.haerbin.R;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 15, 2014 5:03:10 PM
 * @Description: This is David Wu's property.
 *
 **/
public class MyMessageListAdapter extends BaseAdapter {

	private List<XcfcMyMessage> infoes;
	private int resource;
	private LayoutInflater inflater;

	public MyMessageListAdapter(Context context, XcfcMyMessage[] infoes, int resource) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.infoes = new ArrayList<XcfcMyMessage>();
		addData(infoes);
	}

	private void addData(XcfcMyMessage[] infoes) {
		for (int i = 0; i < infoes.length; ++i) {
			this.infoes.add(infoes[i]);
		}
	}

	public void addItems(XcfcMyMessage[] infoes) {
		for (int i = 0; i < infoes.length; ++i) {
			this.infoes.add(infoes[i]);
		}

		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return infoes == null ? 0 : infoes.size();
	}

	@Override
	public Object getItem(int position) {
		return infoes == null ? null : infoes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (infoes == null)
			return null;

		ViewCache cache = null;

		if (convertView == null) {
			convertView = inflater.inflate(resource, null);

			cache = new ViewCache();

			cache.txtMessage = (TextView) convertView.findViewById(R.id.txt_message);
			cache.txtDate = (TextView) convertView.findViewById(R.id.txt_date);

			convertView.setTag(cache);
		} else {
			cache = (ViewCache) convertView.getTag();
		}

		XcfcMyMessage info = infoes.get(position);
		cache.info = info;
		cache.txtMessage.setText(info.getContent());
		if (info.getCreateTime() !=null && info.getCreateTime().contains(" ")) {
			cache.txtDate.setText(info.getCreateTime().substring(0, info.getCreateTime().indexOf(" ")));
		}
		
		
		return convertView;
	}

	public final class ViewCache {
		public XcfcMyMessage info;
		public TextView txtMessage;
		public TextView txtDate;
	}

}

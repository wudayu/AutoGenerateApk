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

import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.model.XcfcInfo;
import com.movitech.grande.haerbin.R;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 23, 2014 10:48:23 AM
 * @Description: This is David Wu's property.
 *
 **/
public class InfoListAdapter extends BaseAdapter {

	private List<XcfcInfo> infoes;
	private int resource;
	private LayoutInflater inflater;
	private IImageUtils imageUtils;

	public InfoListAdapter(Context context, XcfcInfo[] infoes, int resource, IImageUtils imageUtils) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.infoes = new ArrayList<XcfcInfo>();
		this.imageUtils = imageUtils;
		addData(infoes);
	}

	private void addData(XcfcInfo[] infoes) {
		for (int i = 0; i < infoes.length; ++i) {
			this.infoes.add(infoes[i]);
		}
	}

	public void addItems(XcfcInfo[] infoes) {
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

			cache.ivItem = (ImageView) convertView.findViewById(R.id.iv_item);
			cache.txtItem = (TextView) convertView.findViewById(R.id.txt_item);
			cache.txtDate = (TextView) convertView.findViewById(R.id.txt_date);

			convertView.setTag(cache);
		} else {
			cache = (ViewCache) convertView.getTag();
		}

		XcfcInfo info = infoes.get(position);
		cache.info = info;
	    //imageUtils.loadHouseDetailDynamicImage(info.getPicsrc(), cache.ivItem);
		//8.13 fix
		imageUtils.loadInfoImage(info.getPicsrc(), cache.ivItem);
		
		cache.txtItem.setText(info.getTitle());
		cache.txtDate.setText(info.getCreateTime());
		
		return convertView;
	}

	public final class ViewCache {
		public XcfcInfo info;
		public ImageView ivItem;
		public TextView txtItem;
		public TextView txtDate;
	}

}

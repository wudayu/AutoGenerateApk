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
 * @Created Time: July 2, 2014 8:47:12 PM
 * @Description: This is David Wu's property.
 *
 **/
public class InfoGridAdapter extends BaseAdapter {

	private static final int TYPE_LEFT = 0;
	private static final int TYPE_RIGHT = 1;
	private static final int TYPE_COUNT = 2;

	private static int LEFT_RESOURCE = R.layout.item_gridview_info_left;
	private static int RIGHT_RESOURCE = R.layout.item_gridview_info_right;

	private List<XcfcInfo> infoes;
	private LayoutInflater inflater;
	private IImageUtils imageUtils;

	public InfoGridAdapter(Context context, XcfcInfo[] infoes, IImageUtils imageUtils) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		if (infoes == null || infoes.size() <= position)
			return null;
		else
			return infoes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (infoes == null)
			return null;

		int type = getItemViewType(position);

		if (type == TYPE_LEFT) {
			ViewCache cacheLeft = null;

			if (null == convertView) {
				convertView = inflater.inflate(LEFT_RESOURCE, null);

				cacheLeft = new ViewCache();

				cacheLeft.ivItem = (ImageView) convertView.findViewById(R.id.iv_item);
				cacheLeft.txtItem = (TextView) convertView.findViewById(R.id.txt_item);
				cacheLeft.txtDate = (TextView) convertView.findViewById(R.id.txt_date);

				convertView.setTag(cacheLeft);
			} else {
				cacheLeft = (ViewCache) convertView.getTag();
			}
			XcfcInfo infoLeft = infoes.get(position);
			cacheLeft.info = infoLeft;
			
			imageUtils.loadInfoImage(infoLeft.getPicsrc(), cacheLeft.ivItem);
			cacheLeft.txtItem.setText(infoLeft.getTitle());
			cacheLeft.txtDate.setText(infoLeft.getCreateTime());

			ImageView ivRightBranchLine = (ImageView) convertView.findViewById(R.id.iv_right_branch_line);
			if (position == infoes.size() - 1)
				ivRightBranchLine.setVisibility(View.GONE);
			else
				ivRightBranchLine.setVisibility(View.VISIBLE);

		} else if (type == TYPE_RIGHT) {
			ViewCache cacheRight = null;

			if (null == convertView) {
				convertView = inflater.inflate(RIGHT_RESOURCE, null);

				cacheRight = new ViewCache();

				cacheRight.ivItem = (ImageView) convertView.findViewById(R.id.iv_item);
				cacheRight.txtItem = (TextView) convertView.findViewById(R.id.txt_item);
				cacheRight.txtDate = (TextView) convertView.findViewById(R.id.txt_date);

				convertView.setTag(cacheRight);
			} else {
				cacheRight = (ViewCache) convertView.getTag();
			}
			XcfcInfo infoRight = infoes.get(position);
			cacheRight.info = infoRight;
			imageUtils.loadInfoImage(infoRight.getPicsrc(), cacheRight.ivItem);
			cacheRight.txtItem.setText(infoRight.getTitle());
			cacheRight.txtDate.setText(infoRight.getCreateTime());
		}
		
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}
	@Override
	public int getItemViewType(int position) {
		if (position % 2 == 0) {
			return TYPE_LEFT;
		} else
			return TYPE_RIGHT;
	}

	public final class ViewCache {
		public XcfcInfo info;
		public ImageView ivItem;
		public TextView txtItem;
		public TextView txtDate;
	}

}

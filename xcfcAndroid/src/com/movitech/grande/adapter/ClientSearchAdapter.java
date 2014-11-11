package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import com.movitech.grande.model.XcfcClient;
import com.movitech.grande.haerbin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月15日 下午3:13:04 类说明
 */
public class ClientSearchAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<XcfcClient> clients;
	int[] shapes = { R.drawable.shape_search_client_avater_bg,
			R.drawable.shape_search_client_avater_blue_bg,
			R.drawable.shape_search_client_avater_green_bg,
			R.drawable.shape_search_client_avater_orange_bg,
			R.drawable.shape_search_client_avater_purple_bg,
			R.drawable.shape_search_client_avater_red_bg };

	public ClientSearchAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setClients(XcfcClient[] xcfcClients) {
		this.clients = new ArrayList<XcfcClient>();

		for (int i = 0; i < xcfcClients.length; ++i) {
			this.clients.add(xcfcClients[i]);
		}
	}
	
	public void addItems(XcfcClient[] xcfcClients){
		for (int i = 0; i < xcfcClients.length; ++i) {
			this.clients.add(xcfcClients[i]);
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return clients == null ? 0 : clients.size();
	}

	@Override
	public Object getItem(int position) {
		return clients == null ? null : clients.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewCache viewCache = null;
		if (convertView == null) {
			viewCache = new ViewCache();
			convertView = inflater.inflate(R.layout.item_search_client, null);
			viewCache.rlClientAvater = (RelativeLayout) convertView
					.findViewById(R.id.rl_client_avater);
			viewCache.ivImportantClient = (ImageView) convertView
					.findViewById(R.id.iv_important_client);
			viewCache.tvClientName = (TextView) convertView
					.findViewById(R.id.tv_client_name);
			viewCache.tvBuildName = (TextView) convertView
					.findViewById(R.id.tv_build_name);
			viewCache.tvClientPhone = (TextView) convertView
					.findViewById(R.id.tv_client_phone);
			convertView.setTag(viewCache);
			viewCache.rlCustomerBuild = (RelativeLayout) convertView
					.findViewById(R.id.rl_customer_build);
			viewCache.tvBuildNum = (TextView) convertView
					.findViewById(R.id.tv_build_num);
		} else {
			viewCache = (ViewCache) convertView.getTag();
		}
		viewCache.rlClientAvater.setBackgroundResource(shapes[position % 6]);
		viewCache.tvBuildNum.setBackgroundResource(shapes[position % 6]);
		
		final XcfcClient xcfcClient = clients.get(position);
		viewCache.client = xcfcClient;
		viewCache.tvClientName.setText(xcfcClient.getName());
		// viewCache.tvBuildName.setText(xcfcClient.getBuildingName());
		viewCache.tvBuildNum.setText(xcfcClient.getBuildingNum());
		viewCache.tvClientPhone.setText(xcfcClient.getPhone());
		if (xcfcClient.getIsVip().equals("0")) {
			viewCache.ivImportantClient.setVisibility(View.VISIBLE);
		} else if (xcfcClient.getIsVip().equals("1")) {
			viewCache.ivImportantClient.setVisibility(View.GONE);
		}
		return convertView;
	}

	public final class ViewCache {
		public XcfcClient client;
		public RelativeLayout rlClientAvater;
		public ImageView ivImportantClient;
		public TextView tvClientName; // name
		public TextView tvBuildName;
		public TextView tvClientPhone;
		public RelativeLayout rlCustomerBuild;
		public TextView tvBuildNum;
	}

}

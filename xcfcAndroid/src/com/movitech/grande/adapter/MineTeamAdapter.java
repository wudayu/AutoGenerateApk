package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcTeam;
import com.movitech.grande.views.CircleImageView;
import com.movitech.grande.haerbin.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月27日 下午12:01:26
 * 类说明
 */
public class MineTeamAdapter extends BaseAdapter{

	private Context context;
	
	private List<XcfcTeam> xcfcTeams;	
	
	ImageDownLoader imageDownLoader;
	
	public void setData(XcfcTeam [] teams){
		if (xcfcTeams == null) {
			xcfcTeams = new ArrayList<XcfcTeam>();
		}
		for (int i = 0; i < teams.length; i++) {
			xcfcTeams.add(teams[i]);
		}
		this.notifyDataSetChanged();
	}
	
	public MineTeamAdapter(Context context){
		this.context = context;
		imageDownLoader = new ImageDownLoader(context);
		
	}
	@Override
	public int getCount() {
		return xcfcTeams == null ? 0 : xcfcTeams.size();
	}

	@Override
	public Object getItem(int position) {
		return xcfcTeams == null ? null : xcfcTeams.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_mine_team, null);
			viewHolder.ivUserAvater = (CircleImageView) convertView.findViewById(R.id.iv_user_avater);
			viewHolder.txtUserName = (TextView) convertView.findViewById(R.id.txt_user_name);
			viewHolder.txtWaitCommissionAccount = (TextView) convertView.findViewById(R.id.txt_wait_commission_account);
			viewHolder.txtRecomAccount = (TextView) convertView.findViewById(R.id.txt_recom_account);
		    convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.xcfcTeam = xcfcTeams.get(position);
		final XcfcTeam team = xcfcTeams.get(position);
		final CircleImageView avaterView = viewHolder.ivUserAvater;
		viewHolder.txtUserName.setText(team.getName());
		viewHolder.txtWaitCommissionAccount.setText(team.getWaitNums());
		viewHolder.txtRecomAccount.setText(team.getRecommands());
		Bitmap bitmapAvater = imageDownLoader.downloadImage(team.getPic(), new onImageLoaderListener() {
			
			@Override
			public void onImageLoader(Bitmap bitmap, String url) {
				if (bitmap != null) {
					avaterView.setImageBitmap(bitmap);
				}
			}
		}, true);
		if (bitmapAvater != null && avaterView.getDrawingCache()== null) {
			avaterView.setImageBitmap(bitmapAvater);
		}
		return convertView;
	}
	
	public class ViewHolder{
		public XcfcTeam xcfcTeam;
		public CircleImageView ivUserAvater;
		public TextView txtUserName;
		public TextView txtWaitCommissionAccount;
		public TextView txtRecomAccount;
	}
}

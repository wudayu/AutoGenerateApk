package com.movitech.grande.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.movitech.grande.constant.CommissionStatus;
import com.movitech.grande.model.XcfcCommission;
import com.movitech.grande.haerbin.R;

/**
 * 
 * @author Warkey.Song
 * 
 */
public class CommissionGrantAdapter extends BaseAdapter {
	private static final String [] COMMISSION_STATUS = {"10","20","30","40","50","60","100"};
	private List<XcfcCommission> commissions;
	private LayoutInflater inflater;
	private Context context;
	
	private ConfirmApplyCallBack confirmApplyCallBack;

	public CommissionGrantAdapter(Context context, XcfcCommission[] commissions) {
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		addData(commissions);
	}

	private void addData(XcfcCommission[] commission) {
		this.commissions = new ArrayList<XcfcCommission>();
		if (commission == null)
			return;

		for (int i = 0; i < commission.length; ++i) {
			this.commissions.add(commission[i]);
		}
	}
	public void addItems(XcfcCommission[] xcfcComm) {
		for (int i = 0; i < xcfcComm.length; ++i) {
			this.commissions.add(xcfcComm[i]);
		}

		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return commissions == null ? 0 : commissions.size();
	}

	@Override
	public Object getItem(int position) {
		return commissions == null ? null : commissions.get(position);
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
			convertView = inflater.inflate(
					R.layout.item_listview_fragment_commission_grant, null);
			holder.tv_commision_count = (TextView) convertView
					.findViewById(R.id.tv_commision_count);
			holder.tv_recommend_ok = (TextView) convertView
					.findViewById(R.id.tv_recommend_ok);
			holder.tv_recommend_client_name = (TextView) convertView
					.findViewById(R.id.tv_recommend_client_name);
			holder.tv_apply_date_can_edit = (TextView) convertView
					.findViewById(R.id.tv_apply_date_can_edit);
			holder.btnConfirmApply = (Button) convertView
					.findViewById(R.id.btn_confirm_apply);
			holder.tvDealing = (TextView) convertView
					.findViewById(R.id.tv_dealing);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final XcfcCommission xcfcCommission = commissions.get(position);
		if (xcfcCommission.getAmount() == null) {
			holder.tv_commision_count.setText("");
		} else {
			holder.tv_commision_count.setText("+"
					+ Math.round(Double.valueOf(xcfcCommission.getAmount())));
		}
		String recommedString = "";
		if (xcfcCommission.getClientName() != null
				&& xcfcCommission.getBuildingName() != null) {
			recommedString = recommedString + "("
					+ xcfcCommission.getClientName() + "-"
					+ xcfcCommission.getBuildingName() + ")";
		}
		holder.tv_recommend_ok.setText(matchCommissionStatus(xcfcCommission.getStatus()));
		holder.tv_recommend_client_name.setText(recommedString);
		String dateAndTime = xcfcCommission.getCreateDate();
		if (dateAndTime != null) {
			if (dateAndTime.contains(" ")) {
				int index = dateAndTime.indexOf(" ");
				dateAndTime = dateAndTime.substring(0, index);
			}
		}
		holder.tv_apply_date_can_edit.setText(dateAndTime + "");
		if (xcfcCommission.getStatus()
				.equals(CommissionStatus.COMMISSION_FORTY)
				|| xcfcCommission.getStatus().equals(
						CommissionStatus.COMMISSION_FIFTY)) {
			holder.btnConfirmApply.setVisibility(View.GONE);
			holder.tvDealing.setVisibility(View.VISIBLE);
		} else if (xcfcCommission.getStatus().equals(
				CommissionStatus.COMMISSION_SIXTY)) {
			holder.tvDealing.setVisibility(View.GONE);
			holder.btnConfirmApply.setVisibility(View.VISIBLE);
		}
		
		holder.btnConfirmApply.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				confirmApplyCallBack.confirmApply(xcfcCommission.getId());
			}
		});
		// holder.tv_apply_date_can_edit.setText(xcfcCommission.getCreateDate());
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_commision_count;
		public TextView tv_recommend_ok;
		public TextView tv_recommend_client_name;
		public TextView tv_apply_date_can_edit;
		public Button btnConfirmApply;
		public TextView tvDealing;
	}

	public void setConfirmApply(ConfirmApplyCallBack confirmApplyCallBack){
		this.confirmApplyCallBack = confirmApplyCallBack;
	}
	public interface ConfirmApplyCallBack{
		public void confirmApply(String id);
	}
	
	private String matchCommissionStatus(String status){
		if (status.equals(COMMISSION_STATUS[0])) {
			return context.getString(R.string.txt_commission_wait_confirm);
		}else if (status.equals(COMMISSION_STATUS[1])) {
			return context.getString(R.string.txt_commission_wait_review);
		}else if (status.equals(COMMISSION_STATUS[2])) {
			return context.getString(R.string.txt_commission_not_apply);
		}else if (status.equals(COMMISSION_STATUS[3])) {
			return context.getString(R.string.txt_commission_al_apply);
		}else if (status.equals(COMMISSION_STATUS[4])) {
			return context.getString(R.string.txt_commission_financial_apply);
		}else if (status.equals(COMMISSION_STATUS[5])) {
			return context.getString(R.string.txt_commission_al_grant);
		}else if (status.equals(COMMISSION_STATUS[6])) {
			return context.getString(R.string.txt_commission_al_confirm);
		}else
			return "";
	}
}

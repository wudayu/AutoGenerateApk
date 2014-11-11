package com.movitech.grande.views;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;

import com.movitech.grande.adapter.IntentBuildAdapter;
import com.movitech.grande.adapter.SelectedDistrictsAdapter;
import com.movitech.grande.model.XcfcDistrict;
import com.movitech.grande.model.XcfcHouse;
import com.movitech.grande.views.LoadDataListView.OnScrollToEdgeCallBack;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * 作者 E-mail:
 * @version 创建时间：2014年7月1日 下午2:27:37
 *  类说明
 */
public class SelectedDistrictDialog extends Dialog {
	public MaxHeightListView lvSelect;
	private View mMenuView;
	private Context context;
	private String isSelectedDistrict;
	
	private String isSelectedHouse;
	
	public IntentBuildAdapter intentBuildAdapter = null;
	View houseLoadMore = null;
	ListAdapter districtsAdapter = null;
	private XcfcDistrict[] districts;

	private XcfcHouse[] houses;

	public XcfcHouse[] getHouses() {
		return houses;
	}

	public void setHouses(XcfcHouse[] houses) {
		this.houses = houses;
	}

	public XcfcDistrict[] getDistricts() {
		return districts;
	}

	public void setDistricts(XcfcDistrict[] districts) {
		this.districts = districts;
	}

	public SelectedDistrictDialog(Context context,
			OnItemClickListener listener, OnScrollToEdgeCallBack scorllCallBackListener, int theme, String districtString, String isSelectedHouse) {
		super(context, theme);
		this.context = context;
		this.isSelectedDistrict = districtString;
		this.isSelectedHouse = isSelectedHouse;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.dialog_selected_district, null);
		this.setContentView(mMenuView);
		lvSelect = (MaxHeightListView) mMenuView
				.findViewById(R.id.lv_select_district);
		lvSelect.setListViewHeight(context.getResources().getDimensionPixelSize(R.dimen.tmp_width));
		houseLoadMore = this.getLayoutInflater().inflate(
				R.layout.item_loading, null);
		houseLoadMore.setOnClickListener(null);
		if (scorllCallBackListener != null) {
			lvSelect.addFooterView(houseLoadMore);
		}
		lvSelect.setOnItemClickListener(listener);   
		lvSelect.setOnScrollToEdgeCallBack(scorllCallBackListener);
	}

	/**
	 * 意向楼盘选择
	 */
	public void setIntentHouseAdapter(){
        intentBuildAdapter = new IntentBuildAdapter(context,
				R.layout.item_activity_intent_build_listview, isSelectedHouse);
        intentBuildAdapter.addData(houses);
        lvSelect.setAdapter(intentBuildAdapter);
		int posotion = 0;
		for (int i = 0; i < houses.length; i++) {
			if (isSelectedHouse.equals(""))
				return;

			if (isSelectedHouse.equals(houses[i].getName())) {
				posotion = i;
				break;
			}
		}
		lvSelect.setSelection(posotion);
	}
	
	public void setAdapter() {
		districtsAdapter = new SelectedDistrictsAdapter(context, districts,
				R.layout.item_select_district_dialog, isSelectedDistrict);
		lvSelect.setAdapter(districtsAdapter);
		int posotion = 0;
		for (int i = 0; i < districts.length; i++) {
			if (isSelectedDistrict.equals(""))
				return;

			if (isSelectedDistrict.equals(districts[i].getName())) {
				posotion = i;
				break;
			}
		}
		lvSelect.setSelection(posotion);
	}
}

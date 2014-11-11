package com.movitech.grande.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.haerbin.R;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 17, 2014 2:19:42 PM
 * @Description: This is David Wu's property.
 * 
 **/
@Deprecated
@EFragment(R.layout.fragment_houses_detail_map)
public class HousesDetailMapFragment extends BaseFragment {


	@ViewById(R.id.bmap_view)
	MapView bmapView;

	BaiduMap mBaiduMap = null;

	XcfcHouseDetail houseDetail = null;
	Bitmap popShow = null;

	@AfterViews
	void afterViews() {
	}

	public void setHouseDetail(XcfcHouseDetail houseDetail) {
		this.houseDetail = houseDetail;
	}

	boolean dataBinded = false;

	public void bindDataNow() {
		if (dataBinded)
			return;
		if (houseDetail == null) {
			return;
		}
		double x = 0;
		double y = 0;
		try {
			/*x = Double.valueOf(houseDetail.getAddressX());
			y = Double.valueOf(houseDetail.getAddressY());*/
			x = Double.valueOf(houseDetail.getAddressX());
			y = Double.valueOf(houseDetail.getAddressY());
		} catch (NumberFormatException e) {
			return;
		}

		LatLngBounds.Builder builder = new LatLngBounds.Builder();
		mBaiduMap = bmapView.getMap();
		// 定义Maker坐标点
		LatLng point = new LatLng(y, x);

		// 构建Marker图标
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.map_view_pop, null);
		TextView txtBuildName = (TextView) layout
				.findViewById(R.id.txt_map_build_name);
		//txtBuildName.setText(houseDetail.getName());
		txtBuildName.setText(houseDetail.getName());
		popShow = convertViewToBitmap(layout);
		if (popShow != null) {

			BitmapDescriptor bitmapDrawable = BitmapDescriptorFactory
					.fromBitmap(popShow);

			// 构建Marker图标
			// BitmapDescriptor bitmap = BitmapDescriptorFactory
			// .fromResource(R.drawable.timeline_point_on);
			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions().position(point).icon(
					bitmapDrawable);
			// 在地图上添加Marker，并显示
			builder.include(point);
			mBaiduMap.addOverlay(option);

			LatLngBounds bounds = builder.build();
			MapStatusUpdate goTo = MapStatusUpdateFactory
					.newLatLngBounds(bounds);
			mBaiduMap.animateMapStatus(goTo);
		}
		dataBinded = true;
	}

	// view转bitmap{{
	public Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	@Override
	public void onPause() {
		if (popShow != null && !popShow.isRecycled()) {
			popShow.recycle();
			popShow = null;
		}
		System.gc();
		super.onPause();
	}
	
	
	
}

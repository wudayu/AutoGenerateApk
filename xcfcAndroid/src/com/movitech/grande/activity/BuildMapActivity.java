package com.movitech.grande.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
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
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月13日 下午4:58:03
 * 类说明
 */

@EActivity(R.layout.activity_build_map)
public class BuildMapActivity extends BaseActivity{


	@ViewById(R.id.bmap_view)
	MapView bmapView;

	BaiduMap mBaiduMap = null;

	Bitmap popShow = null;

	@AfterViews
	void afterViews() {
		bindDataNow();
	}

	boolean dataBinded = false;

	void bindDataNow() {
		if (dataBinded)
			return;
		
		double x = 0;
		double y = 0;
		try {
			x = Double.valueOf(getIntent().getStringExtra("longitude"));
			y = Double.valueOf(getIntent().getStringExtra("latitude"));
			
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
		txtBuildName.setText(getIntent().getStringExtra("buildName"));
		popShow = convertViewToBitmap(layout);
		if (popShow != null) {

			BitmapDescriptor bitmapDrawable = BitmapDescriptorFactory
					.fromBitmap(popShow);

			// 构建Marker图标
			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions().position(point).icon(
					bitmapDrawable);
			// 在地图上添加Marker，并显示
			builder.include(point);
			mBaiduMap.addOverlay(option);

			LatLngBounds bounds = builder.build();
			MapStatusUpdate goTo = MapStatusUpdateFactory
					.newLatLngBounds(bounds);
			mBaiduMap.setMapStatus(goTo);
			
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
	    protected void onResume() {  
	        //MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()  
		 bmapView.onResume();  
	        super.onResume();  
	    }  
	  
	  
	  
	    @Override  
	    protected void onPause() {  
	        //MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()  
	    	bmapView.onPause();  
	        super.onPause();  
	    }  
	  
	    @Override  
	    protected void onDestroy() {  
	        //MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()  
	    	bmapView.onDestroy();  
	    	if (popShow != null && !popShow.isRecycled()) {
				popShow.recycle();
				popShow = null;
			}
	        System.gc();
			super.onDestroy();
	    }  
	
}

package com.movitech.grande.fragment;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.HouseDetailImageSourceType;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.haerbin.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 17, 2014 2:20:04 PM
 * @Description: This is David Wu's property.
 * 
 **/
@Deprecated
@EFragment(R.layout.fragment_houses_detail_traffic)
public class HousesDetailTrafficFragment extends BaseFragment {

	@ViewById(R.id.iv_houses_detail_traffic)
	ImageView ivHousesDetailTraffic;

	@Bean(ImageUtils.class)
	IImageUtils imageUtils;

	ImageDownLoader imageDownLoader;
	@App
	MainApp mApp;

	XcfcHouseDetail houseDetail = null;

	ImageLoader imageLoader = null;
	Bitmap bitmap = null;

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
		// 8.4号修改
		imageDownLoader = new ImageDownLoader(getActivity()
				.getApplicationContext());
		List<String> addresses = this.houseDetail
				.getImageAddressesBySourceType(HouseDetailImageSourceType.DETAIL_TRAFFIC);
		if (addresses.size() > 0) {
			// imageUtils.loadImage(addresses.get(0), ivHousesDetailTraffic);
			bitmap = imageDownLoader.downloadImage(addresses.get(0),
					new onImageLoaderListener() {

						@Override
						public void onImageLoader(Bitmap bitmap, String url) {
							if (ivHousesDetailTraffic != null && bitmap != null) {
								ivHousesDetailTraffic.setImageBitmap(bitmap);
							}
						}
					}, true);
			if (bitmap != null
					&& ivHousesDetailTraffic.getDrawingCache() == null) {
				ivHousesDetailTraffic.setImageBitmap(bitmap);
			}
		}
		dataBinded = true;
	}

	@Override
	public void onDestroy() {
		if (bitmap != null && !bitmap.isRecycled()) {

			bitmap.recycle();
			bitmap = null;
		}

		System.gc();
		super.onDestroy();
	}
}

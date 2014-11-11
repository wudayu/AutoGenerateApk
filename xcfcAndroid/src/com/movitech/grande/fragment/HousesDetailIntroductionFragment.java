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
 * @Created Time: Jun 17, 2014 2:18:52 PM
 * @Description: This is David Wu's property.
 * 
 **/
@Deprecated
@EFragment(R.layout.fragment_houses_detail_introduction)
public class HousesDetailIntroductionFragment extends BaseFragment {
/*
	@ViewById(R.id.txt_project_speciality)
	TextView txtProjectSpeciality;
	@ViewById(R.id.txt_rate_container)
	TextView txtRateContainer;
	@ViewById(R.id.iv_houses_detail_introduction)
	ImageView ivHousesDetailIntroduction;
	@ViewById(R.id.txt_rate_green)
	TextView txtRateGreen;
	@ViewById(R.id.txt_first_opensell)
	TextView txtFirstOpensell;
	@ViewById(R.id.txt_realestate_category)
	TextView txtRealestateCategory;
	@ViewById(R.id.txt_building_category)
	TextView txtBuildingCategory;
	@ViewById(R.id.txt_decoration_situation)
	TextView txtDecorationSituation;


	@AfterViews
	void afterViews() {
		doLoadDataAndBindData();
	}

	@Background
	void doLoadDataAndBindData() {
		// TODO get the data from server
		doBindData();
	}

	@UiThread
	void doBindData() {
	}
*/

	@ViewById(R.id.tmp_image)
	ImageView tmpImage;

	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	@App
	MainApp mApp;
	ImageDownLoader imageDownLoader;
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
		//8.4号修改
		//imageDownLoader = new ImageDownLoader(getActivity().getApplicationContext());
		List<String> addresses = this.houseDetail.getImageAddressesBySourceType(HouseDetailImageSourceType.DETAIL_INTRODUCTION);
		if (addresses.size() > 0) {
			// imageUtils.loadImage(addresses.get(0), tmpImage);
			imageDownLoader = new ImageDownLoader(context);
			bitmap = imageDownLoader.downloadImage(addresses.get(0),
					new onImageLoaderListener() {

						@Override
						public void onImageLoader(Bitmap bitmap, String url) {
							if (tmpImage != null && bitmap != null) {
								 tmpImage.setImageBitmap(bitmap);
							}
						}
					}, true);
			if (bitmap != null && tmpImage.getDrawingCache()==null) {
				tmpImage.setImageBitmap(bitmap);
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

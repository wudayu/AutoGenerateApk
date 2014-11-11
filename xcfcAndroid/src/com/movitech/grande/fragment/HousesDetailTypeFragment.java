package com.movitech.grande.fragment;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movitech.grande.adapter.ViewPagerAdapter;
import com.movitech.grande.constant.HouseDetailImageSourceType;
import com.movitech.grande.generic.ImageUtils;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.model.XcfcHouseDetail;
import com.movitech.grande.views.BaseViewPager;
import com.movitech.grande.views.UnderlinePageIndicator;
import com.movitech.grande.haerbin.R;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 17, 2014 2:21:55 PM
 * @Description: This is David Wu's property.
 *
 **/
@Deprecated
@EFragment(R.layout.fragment_houses_detail_type)
public class HousesDetailTypeFragment extends BaseFragment {

	@ViewById(R.id.ll_indicate_words)
	LinearLayout llIndicateWords;
	@ViewById(R.id.txt_pic_houses_sample)
	TextView txtPicHousesSample;
	@ViewById(R.id.txt_pic_houses_traffic)
	TextView txtPicHousesTraffic;
	@ViewById(R.id.txt_pic_houses_effect)
	TextView txtPicHousesEffect;
	@ViewById(R.id.txt_pic_houses_sight)
	TextView txtPicHousesSight;
	@ViewById(R.id.txt_pic_houses_type)
	TextView txtPicHousesType;
	@ViewById(R.id.vp_fragment_info)
	BaseViewPager vpFragmentInfo;
	@ViewById(R.id.indicator_fragment_info)
	UnderlinePageIndicator indicatorFragmentInfo;
	@ViewById(R.id.rl_pic_houses_sample)
	RelativeLayout rlPicHousesSample;
	@ViewById(R.id.rl_pic_houses_sight)
	RelativeLayout rlPicHousesSight;
	@ViewById(R.id.rl_pic_houses_effect)
	RelativeLayout rlPicHousesEffect;
	@ViewById(R.id.rl_pic_houses_type)
	RelativeLayout rlPicHousesType;
	@ViewById(R.id.rl_pic_houses_traffic)
	RelativeLayout rlPicHousesTraffic;

	@Bean(ImageUtils.class)
	IImageUtils imageUtils;
	

	ImageDownLoader imageDownLoader;
	
	List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	private static final int[] types = {
			HouseDetailImageSourceType.HOUSES_TYPE,
			HouseDetailImageSourceType.HOUSES_EFFECT,
			HouseDetailImageSourceType.HOUSES_TRAFFIC,
			HouseDetailImageSourceType.HOUSES_SIGHT,
			HouseDetailImageSourceType.HOUSES_SAMPLE };

	List<TextView> textViews = null;
	XcfcHouseDetail houseDetail = null;

	public void setHouseDetail(XcfcHouseDetail houseDetail) {
		this.houseDetail = houseDetail;
	}

	boolean dataBinded = false;
	public void bindDataNow() {
		if (dataBinded)
			return;

		doBindData();

		dataBinded = true;
	}

	@AfterViews
	void afterViews() {
		initializeTab();
	}

	private void initializeTab() {
		textViews = new ArrayList<TextView>();

		textViews.add(txtPicHousesType);
		textViews.add(txtPicHousesEffect);
		textViews.add(txtPicHousesTraffic);
		textViews.add(txtPicHousesSight);
		textViews.add(txtPicHousesSample);
	}

	void doBindData() {
		if (getActivity() == null || this.isDetached())
			return;
		if (houseDetail == null) {
			return;
		}
		imageDownLoader = new ImageDownLoader(getActivity().getApplicationContext());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
			     LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.dp_five), 0, 0);

		List<View> views = new ArrayList<View>();
		for (int i = 0; i < types.length; ++i) {
			View baseViewGroup = View.inflate(getActivity(),
					R.layout.layout_houses_detail_type, null);
			LinearLayout ll = (LinearLayout) baseViewGroup
					.findViewById(R.id.ll_images_group);
			List<String> addresses = houseDetail.getImageAddressesBySourceType(types[i]);
			for (int j = 0; j < addresses.size(); ++j) {
				final ImageView imageView = new ImageView(getActivity());
				imageView.setAdjustViewBounds(true);
				imageView.setMaxHeight(getResources().getDimensionPixelSize(R.dimen.length_largest));
				imageView.setMaxWidth(getResources().getDimensionPixelSize(R.dimen.length_largest));
				imageView.setScaleType(ScaleType.FIT_XY);
				Bitmap bitmap = null;
				//imageUtils.loadImage(addresses.get(j), imageView);
				bitmap = imageDownLoader.downloadImage(addresses.get(j), new onImageLoaderListener() {
					
					@Override
					public void onImageLoader(Bitmap bitmap, String url) {
						 if(imageView != null && bitmap != null){  
							 imageView.setImageBitmap(bitmap);  
		                    } 	
					}
				}, true);
				if (bitmap != null && imageView.getDrawingCache() == null) {
					imageView.setImageBitmap(bitmap);
				}
				if (bitmap != null && !bitmap.isRecycled())
					bitmaps.add(bitmap);
				ll.addView(imageView, layoutParams);
			}

			views.add(baseViewGroup);
			
		}

		ViewPagerAdapter adapter = new ViewPagerAdapter();
		adapter.init();
		adapter.addAll(views);
		vpFragmentInfo.setAdapter(adapter);
		indicatorFragmentInfo.setViewPager(vpFragmentInfo);
		indicatorFragmentInfo.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				changeWordIndicator(position);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}

	void changeWordIndicator(int pos) {
		for (int i = 0; i < textViews.size(); ++i)
			textViews.get(i).setTextColor(
					getResources().getColor(
							i == pos ? R.color.col_words_active
									: R.color.col_words_inactive));
	}

	@Click
	void rlPicHousesType() {
		vpFragmentInfo.setCurrentItem(0);
	}
	@Click
	void rlPicHousesEffect() {
		vpFragmentInfo.setCurrentItem(1);
	}
	@Click
	void rlPicHousesTraffic() {
		vpFragmentInfo.setCurrentItem(2);
	}
	@Click
	void rlPicHousesSight() {
		vpFragmentInfo.setCurrentItem(3);
	}
	@Click
	void rlPicHousesSample() {
		vpFragmentInfo.setCurrentItem(4);
	}
	
	@Override
	public void onDestroy() {
		if (bitmaps != null) {
			for (int i = 0; i < bitmaps.size(); i++) {
				if (bitmaps.get(i) != null && !bitmaps.get(i).isRecycled()) {
					bitmaps.get(i).recycle();
				}
			}
			bitmaps.clear();
		}	
		System.gc();
		super.onDestroy();
	}
}

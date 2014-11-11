package com.movitech.grande.activity;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movitech.grande.image.ImageDownLoader;
import com.movitech.grande.image.ImageDownLoader.onImageLoaderListener;
import com.movitech.grande.image.imagezoom.GestureImageView;
import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月12日 下午1:02:08 
 * 类说明 
 */
@EActivity(R.layout.activity_image_pager_zoom)
public class ImagePagerZoomActivity extends BaseActivity implements
		OnPageChangeListener {

	@ViewById(R.id.view_pager)
	ViewPager viewPager;
	@ViewById(R.id.txt_page)
	TextView txtPage;

	//List<String> huStylePic = null;
	String huStylePic = null;
	
	ImageDownLoader downLoader = null;

	@AfterViews
	void afterViews() {
		int imagePosition = 0;
		//huStylePic = getIntent().getStringArrayListExtra("stylePic");
		huStylePic = getIntent().getStringExtra("picUrl");
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(imagePosition);
		viewPager.setOnPageChangeListener(this);
		viewPager.setEnabled(false);
		// 设定当前的页数和总页数
		if (huStylePic == null) {
			return;
		}
		//txtPage.setText((imagePosition + 1) + "/" + huStylePic.size());
		txtPage.setVisibility(View.GONE);
	}

	/**
	 * ViewPager的适配器
	 * 
	 * @author Warkey.Song
	 */
	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = LayoutInflater.from(ImagePagerZoomActivity.this)
					.inflate(R.layout.view_build_detail_hustyle_zoom, null);
			final GestureImageView zoomImageView = (GestureImageView) view
					.findViewById(R.id.zoom_image_view);
			
			//String imagePath = huStylePic.get(position);
			String imagePath = huStylePic;
			downLoader = new ImageDownLoader(context);
			Bitmap bitmap = null;
			bitmap = downLoader.downloadImage(
					imagePath, new onImageLoaderListener() {
						@Override
						public void onImageLoader(Bitmap bitmap, String url) {
							if (bitmap != null && zoomImageView != null) {
								zoomImageView.setImageBitmap(bitmap);
							}
						}
					}, true);
			if (bitmap != null && zoomImageView.getDrawable() == null) {
				zoomImageView.setImageBitmap(bitmap);
			}
			
			container.addView(view);
			return view;
		}

		@Override
		public int getCount() {
			//return huStylePic == null ? 0 : huStylePic.size();
			return huStylePic == null ? 0 : 1;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int currentPage) {
		// 每当页数发生改变时重新设定一遍当前的页数和总页数
		if (huStylePic == null) {
			return;
		}
		//txtPage.setText((currentPage + 1) + "/" + huStylePic.size());
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

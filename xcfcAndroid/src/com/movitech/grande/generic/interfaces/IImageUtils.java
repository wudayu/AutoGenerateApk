package com.movitech.grande.generic.interfaces;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 9:27:33 AM
 * @Description: This is David Wu's property.
 *
 **/
public interface IImageUtils {

	public void cleanImageCache();

	public void cleanImageCache(long size, long millSecAgo);

	public Bitmap resizeBitmap(Bitmap bitmap, float toW, float toH,
			int scaleType);

	public Bitmap resizeBitmap(String path, int max);

	public Point getScaledSize(String path, int max);

	public Bitmap getBitmapFromCachedFile(String url, int defRes);

	public int isRotatedImage(String path);

	public Bitmap rotateBitmap(Bitmap bitmap, int angle);

	public byte[] getBitmapData(Bitmap source);

	public String compress(String pathOri, int quality);

	public void initImageLoader();

	public void loadImage(String uri, ImageView imageView);

	public void loadImage(String uri, ImageView imageView, DisplayImageOptions options);

	public void loadRoundCornerImage(String uri, ImageView imageView);

	public void loadHeaderImage(String uri, ImageView imageView);

	public void loadInfoImage(String uri, ImageView imageView);

	public void loadInfoDetailImage(String uri, ImageView imageView);

	public void loadHouseBannerImage(String uri, ImageView imageView);

	public void loadHouseDetailDynamicImage(String uri, ImageView imageView);

	public void loadHouseListImage(String uri, ImageView imageView);

	public void loadHouseRecommendImage(String uri, ImageView imageView);

	public String getNewTmpImagePath();

	public String getNewTmpImagePath(String imageSuffix);

	public void selectGetImageWay(final Activity activity, View hangView, final String takePicturePath);

	public void cutTheImage(Activity activity, Uri uri, String cuttedImagePath, int aspectX, int aspectY, int outputX, int outputY, String outputFormat);

	public void cutTheImageNormal(Activity activity, Uri uri, String cuttedImagePath);

	public void cutTheImageHead(Activity activity, Uri uri, String cuttedImagePath);

	public String compressImage(String imagePath);

	public String compressImage(String imagePath, int quality);
}
package com.movitech.grande.generic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.movitech.grande.MainApp;
import com.movitech.grande.constant.Constant;
import com.movitech.grande.constant.ImageLoaderHelper;
import com.movitech.grande.constant.ReqCode;
import com.movitech.grande.generic.interfaces.IFile;
import com.movitech.grande.generic.interfaces.IFileHandler;
import com.movitech.grande.generic.interfaces.IFileHandler.CacheDir;
import com.movitech.grande.generic.interfaces.IImageUtils;
import com.movitech.grande.generic.interfaces.INetwork;
import com.movitech.grande.generic.interfaces.ISDCard;
import com.movitech.grande.haerbin.R;
import com.movitech.grande.views.SelectPicPopupWindow;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 9:26:23 AM
 * @Description: This is David Wu's property.
 *
 **/
@EBean(scope = Scope.Singleton)
public class ImageUtils implements IImageUtils {

	public static final String TMP_IMAGE_INITIAL = "TMP_IMG_";
	public static final String COMPRESSED_IMAGE_INITIAL = "COMPRESSED_IMG_";

	public static final String IMAGE_CONTENT_TYPE = "image/*";

	public static final String FORMAT_IN_STRING_JPEG = "JPEG";
	public static final String FORMAT_IN_STRING_PNG = "PNG";
	public static final String SUFFIX_JPEG = ".jpg";
	public static final String SUFFIX_PNG = ".png";

	public static final int IMAGE_LOAD_NORMAL = 0x101;
	public static final int IMAGE_LOAD_ROUND_CORNER = 0x102;
	public static final int IMAGE_LOAD_HEAD = 0x103;


	@RootContext
	Context context;
	@App
	MainApp mApp;

	@Bean(SDCard.class)
	ISDCard mSDCard;
	@Bean(FileHandler.class)
	IFileHandler fileHandler;
	@Bean(FileUtils.class)
	IFile file;
	@Bean(NetworkUtils.class)
	INetwork network;

	ImageLoader imageLoader = null;
	ImageLoaderConfiguration defaultUilLoader = null;

	DisplayImageOptions defaultUilDisplay = null;
	DisplayImageOptions roundUilDisplay = null;
	DisplayImageOptions headerUilDisplay = null;

	DisplayImageOptions infoUilDisplay = null;
	DisplayImageOptions infoDetailDisplay = null;
	DisplayImageOptions houseBannerUilDisplay = null;
	DisplayImageOptions houseDetailDynamic = null;
	DisplayImageOptions houseListDynamic = null;
	DisplayImageOptions houseRecommendDynamic = null;

	String mImageCacheDir = null;

	@Override
	public void initImageLoader() {
		initUIL();

		if (imageLoader == null)
			imageLoader = ImageLoader.getInstance();
		if (!imageLoader.isInited())
			imageLoader.init(defaultUilLoader);
		mImageCacheDir = fileHandler.getCacheDirByType(CacheDir.IMAGE);
		
		
		
	}

	@Override
	public void cleanImageCache() {
		cleanImageCache(0, 0);
	}

	@Override
	public void cleanImageCache(long size, long millSecAgo) {
		file.cleanCache(mImageCacheDir, size, millSecAgo);
	}

	@SuppressWarnings("deprecation")
	private void initUIL() {
		
		defaultUilDisplay = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_houseimg) // resource or drawable
		        .showImageForEmptyUri(R.drawable.default_houseimg) // resource or drawable
		        .showImageOnFail(R.drawable.default_houseimg) // resource or drawable
		        // .resetViewBeforeLoading(false)  // default false
		        // .delayBeforeLoading(1000)
		        .resetViewBeforeLoading()
		        //.memoryCache(new WeakMemoryCache()) 
		        .cacheInMemory(false) // default false
		        .cacheOnDisk(true) // default false
		        // .preProcessor(...)
		        // .postProcessor(...)
		        // .extraForDownloader(...)
		        .considerExifParams(true) // default
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        //.imageScaleType(ImageScaleType.EXACTLY) // default IN_SAMPLE_POWER_OF_2
		        .bitmapConfig(Bitmap.Config.RGB_565) // default 8888
		        // .decodingOptions(...)
		        .displayer(new SimpleBitmapDisplayer()) // default SimpleBitmapDisplayer
		        // .handler(new Handler()) // default
		        .build();

		roundUilDisplay = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_houseimg)
		        .showImageForEmptyUri(R.drawable.default_houseimg)
		        .showImageOnFail(R.drawable.default_houseimg)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		        //.imageScaleType(ImageScaleType.EXACTLY)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner)))
		        .build();

		headerUilDisplay = new DisplayImageOptions.Builder()
		        .showImageForEmptyUri(R.drawable.default_avatar)
		        .showImageOnFail(R.drawable.default_avatar)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		       // .imageScaleType(ImageScaleType.EXACTLY)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner_header)))
		        .build();

		infoUilDisplay = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defalut_list_info)
		        .showImageForEmptyUri(R.drawable.defalut_list_info)
		        .showImageOnFail(R.drawable.defalut_list_info)
		        .cacheOnDisk(true)
		        .resetViewBeforeLoading()
		        .considerExifParams(true)
		       // .imageScaleType(ImageScaleType.EXACTLY)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner)))
		        .build();

		infoDetailDisplay = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_info_detail)
		        .showImageForEmptyUri(R.drawable.default_info_detail)
		        .showImageOnFail(R.drawable.default_info_detail)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		       // .imageScaleType(ImageScaleType.EXACTLY)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner)))
		        .build();

		houseBannerUilDisplay = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_house_banner)
		        .showImageForEmptyUri(R.drawable.default_house_banner)
		        .showImageOnFail(R.drawable.default_house_banner)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        //.imageScaleType(ImageScaleType.EXACTLY)
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .build();
	
		houseDetailDynamic = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_house_detail_dynamic)
		        .showImageForEmptyUri(R.drawable.default_house_detail_dynamic)
		        .showImageOnFail(R.drawable.default_house_detail_dynamic)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		       // .imageScaleType(ImageScaleType.EXACTLY)
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner)))
		        .build();
	
		houseListDynamic = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_house_list)
		        .showImageForEmptyUri(R.drawable.default_house_list)
		        .showImageOnFail(R.drawable.default_house_list)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        //.imageScaleType(ImageScaleType.EXACTLY)
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner)))
		        .build();
	
		houseRecommendDynamic = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_house_recommend)
		        .showImageForEmptyUri(R.drawable.default_house_recommend)
		        .showImageOnFail(R.drawable.default_house_recommend)
		        .cacheOnDisk(true)
		        .considerExifParams(true)
		        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// default
		        //.imageScaleType(ImageScaleType.EXACTLY)
		        .bitmapConfig(Bitmap.Config.RGB_565)
		        .displayer(new RoundedBitmapDisplayer(context.getResources().getDimensionPixelSize(R.dimen.round_corner)))
		        .build();

		defaultUilLoader = new ImageLoaderConfiguration.Builder(context)
		// .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
        // .diskCacheExtraOptions(480, 800, null)
        // .taskExecutor(...)
        // .taskExecutorForCachedImages(...)
        // .threadPoolSize(3) // default
        // .threadPriority(Thread.NORM_PRIORITY - 1) // default
        .tasksProcessingOrder(QueueProcessingType.LIFO) // default FIFO
        // .denyCacheImageMultipleSizesInMemory()
        // .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
        // .memoryCacheSize(2 * 1024 * 1024)
        // .memoryCacheSizePercentage(13) // default
        .diskCache(new UnlimitedDiscCache(new File(Constant.SD_IMAGE_CACHE))) // default
        // .diskCacheExtraOptions(maxImageWidthForDiskCache, maxImageHeightForDiskCache, processorForDiskCache)
        .diskCacheSize(128 * 1024 * 1024)
        // .diskCacheFileCount(100)
        // .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
        // .imageDownloader(new BaseImageDownloader(context)) // default
        // .imageDecoder(new BaseImageDecoder()) // default
        .defaultDisplayImageOptions(defaultUilDisplay) // default
        // .writeDebugLogs()
        .build();
	}

	@Override
	public void loadInfoImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, infoUilDisplay);
	}

	@Override
	public void loadInfoDetailImage(String uri, ImageView imageView) {
		try {
			loadImage(uri, imageView, infoDetailDisplay);
		} catch (Exception e) {
			// TODO: handle exception
		}	
		
	}

	@Override
	public void loadHouseBannerImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, houseBannerUilDisplay);
	}

	@Override
	public void loadHouseDetailDynamicImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, houseDetailDynamic);
	}

	@Override
	public void loadHouseListImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, houseListDynamic);
	}


	@Override
	public void loadHouseRecommendImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, houseRecommendDynamic);
	}


	@Override
	public void loadRoundCornerImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, roundUilDisplay);
	}

	@Override
	public void loadHeaderImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, headerUilDisplay);
	}

	@Override
	public void loadImage(String uri, ImageView imageView) {
		loadImage(uri, imageView, defaultUilDisplay);
	}

	public void loadImage(String uri, ImageView imageView, DisplayImageOptions options) {
		try {
			// 使用图片缓存，如果缓存中有对应图片则不从网络加载，而从本地加载
			String fileName = file.getFileNameInUrl(uri);
			String fullPath = mImageCacheDir + fileName;

			if (file.isFileExists(fullPath)) {
				imageLoader.displayImage(ImageLoaderHelper.URI_PREFIX_FILE
						+ fullPath, imageView, options);
			} else {
				imageLoader.displayImage(uri, imageView, options,
						new SaveCacheImageLoadingListener(fullPath));
			}
		} catch (OutOfMemoryError e) {

		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public class SaveCacheImageLoadingListener implements ImageLoadingListener {

		String fullPath = null;

		public SaveCacheImageLoadingListener(String fullPath) {
			this.fullPath = fullPath;
		}

		/**
		 * Is called when image loading task was started
		 *
		 * @param imageUri Loading image URI
		 * @param view     View for image
		 */
		@Override
		public void onLoadingStarted(String imageUri, View view) {}
		/**
		 * Is called when an error was occurred during image loading
		 *
		 * @param imageUri   Loading image URI
		 * @param view       View for image. Can be <b>null</b>.
		 * @param failReason {@linkplain com.nostra13.universalimageloader.core.assist.FailReason The reason} why image
		 *                   loading was failed
		 */
		@Override
		public void onLoadingFailed(String imageUri, View view, FailReason failReason) {}

		/**
		 * Is called when image is loaded successfully (and displayed in View if one was specified)
		 *
		 * @param imageUri    Loaded image URI
		 * @param view        View for image. Can be <b>null</b>.
		 * @param loadedImage Bitmap of loaded and decoded image
		 */
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			file.saveBitmap(fullPath, loadedImage, CompressFormat.JPEG);
		}

		/**
		 * Is called when image loading task was cancelled because View for image was reused in newer task
		 *
		 * @param imageUri Loading image URI
		 * @param view     View for image. Can be <b>null</b>.
		 */
		@Override
		public void onLoadingCancelled(String imageUri, View view) {
			
		}
	}

	/**
	 * 按照指定宽高缩放图片
	 * 
	 * @param bitmap
	 * @param toW
	 * @param toH
	 * @param scaleType
	 *            0：按照宽高缩放；1:按照宽度缩放；2:按照高度缩放
	 * @return
	 */
	@Override
	public Bitmap resizeBitmap(Bitmap bitmap, float toW, float toH,
			int scaleType) {
		int bitmapW = bitmap.getWidth();
		int bitmapH = bitmap.getHeight();
		// 判断是否需要缩放
		if (toW == bitmapW && toH == bitmapH) {
			return bitmap;
		}

		Matrix matrix = new Matrix();
		float scaleW, scaleH;
		scaleW = toW / bitmapW;
		scaleH = toH / bitmapH;
		if (scaleType == 0) {
			matrix.postScale(scaleW, scaleH);
		} else if (scaleType == 1) {
			matrix.postScale(scaleW, scaleW);
		} else {
			matrix.postScale(scaleH, scaleH);
		}

		Bitmap returenBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapW,
				bitmapH, matrix, true);
		matrix = null;
		return returenBitmap;
	}

	/**
	 * 获取重新计算大小的Bitmap 最大边不超过max
	 * 
	 * @param path
	 * @param max
	 * @return
	 */
	@Override
	public Bitmap resizeBitmap(String path, int max) {
		int sample = 1;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		opts.inSampleSize = sample;
		BitmapFactory.decodeFile(path, opts);
		int w = opts.outWidth;
		int h = opts.outHeight;
		if (Math.max(w, h) > max * 4) {
			sample = 8;
		} else if (Math.max(w, h) > max * 2) {
			sample = 4;
		} else if (Math.max(w, h) > max) {
			sample = 2;
		}
		opts.inPreferredConfig = Config.ARGB_8888;
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = sample;
		try {
			return (sample == 1) ? BitmapFactory.decodeFile(path, opts)
					: getScaledBitmap(BitmapFactory.decodeFile(path, opts), max);
		} catch (OutOfMemoryError e) {
			Utils.debug(e.toString());
			opts.inSampleSize = sample * 2;
			try {
				return getScaledBitmap(BitmapFactory.decodeFile(path, opts),
						max);
			} catch (OutOfMemoryError e1) {
				try {
					Utils.debug(e1.toString());
					opts.inSampleSize = sample * 2;
					return getScaledBitmap(
							BitmapFactory.decodeFile(path, opts), max);
				} catch (Exception e2) {
					Utils.debug(e2.toString());
					return null;
				}
			}
		}
	}

	@Override
	public Point getScaledSize(String path, int max) {
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		opts.inSampleSize = 1;
		BitmapFactory.decodeFile(path, opts);
		int w = opts.outWidth;
		int h = opts.outHeight;
		float scale = 1.0f;
		int destW = w;
		int destH = h;
		if (w > h) {
			scale = (max * 1.0f) / w;
			destW = max;
			destH = (int) (scale * h);
		} else {
			scale = (max * 1.0f) / h;
			destH = max;
			destW = (int) (scale * w);
		}
		return new Point(destW, destH);
	}

	/**
	 * 获取缩放之后的Bitmap
	 * 
	 * @param bitmap
	 * @param max
	 * @return
	 */
	private Bitmap getScaledBitmap(Bitmap bitmap, int max) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		float scale = 1.0f;
		int destW = w;
		int destH = h;
		if (w > h) {
			scale = (max * 1.0f) / w;
			destW = max;
			destH = (int) (scale * h);
		} else {
			scale = (max * 1.0f) / h;
			destH = max;
			destW = (int) (scale * w);
		}
		return Bitmap.createScaledBitmap(bitmap, destW, destH, true);
	}


	@Override
	public Bitmap getBitmapFromCachedFile(String url, int defRes) {
		String path = fileHandler
				.getCacheDirByType(IFileHandler.CacheDir.IMAGE)
				+ file.getFileNameInUrl(url);
		if (!file.isFileExists(path)) {
			return BitmapFactory.decodeResource(context.getResources(), defRes);
		}
		return BitmapFactory.decodeFile(path);
	}

	/**
	 * 判断原始素材的方向 （适用于相机拍摄的照片）
	 * 
	 * @param path
	 * @return
	 */
	@Override
	public int isRotatedImage(String path) {
		int angle = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientationPhoto = exifInterface.getAttributeInt("Orientation",
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientationPhoto) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				angle = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				angle = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				angle = 270;
				break;
			default:
				angle = 0;
				break;
			}
		} catch (IOException e) {
			Utils.debug(e.toString());
		}
		return angle;
	}

	/**
	 * 旋转Bitmap
	 * 
	 * @param bitmap
	 * @param angle
	 * @return
	 */
	@Override
	public Bitmap rotateBitmap(Bitmap bitmap, int angle) {
		Matrix m = new Matrix();
		m.setRotate(angle);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		try {
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m, true);
		} catch (OutOfMemoryError oom) {
			try {
				m.postScale(1.0f, 1.0f);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, m,
						true);
			} catch (Exception e) {
				Utils.debug(e.toString());
			}
		}
		return bitmap;
	}

	/**
	 * 获取bitmap数据
	 * 
	 * @param source
	 * @return
	 */
	@Override
	public byte[] getBitmapData(Bitmap source) {
		if (source == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		source.compress(CompressFormat.JPEG, 80, baos);
		byte[] result = baos.toByteArray();
		if (null != baos) {
			try {
				baos.close();
			} catch (IOException e) {
				Utils.debug(e.toString());
			}
		}
		return result;
	}

	@Override
	public String compress(String pathOri, int quality) {
		String suffix = null;

		if (pathOri.endsWith(".png")) {
			suffix = ".png";
		} else {
			suffix = ".jpg";
		}

		int angle = isRotatedImage(pathOri);

		String tmpPath = fileHandler
				.getCacheDirByType(IFileHandler.CacheDir.IMAGE)
				+ "pic_"
				+ System.currentTimeMillis() + suffix;

		Bitmap bitmap = null;

		CompressFormat format = CompressFormat.PNG;

		if (pathOri.endsWith(".png")) {
			bitmap = resizeBitmap(pathOri, 1024);

			format = CompressFormat.PNG;
		} else {
			bitmap = resizeBitmap(pathOri, 1024);

			if (angle != 0) {
				bitmap = rotateBitmap(bitmap, angle);
			}

			format = CompressFormat.JPEG;
		}

		if (bitmap == null) {
			return "";
		}

		saveBitmap(new File(tmpPath), bitmap, quality, format);

		return tmpPath;
	}

	private boolean saveBitmap(File file, Bitmap bitmap, int quality,
			CompressFormat format) {
		if (mSDCard.isSdcardAvaliable()) {
			try {
				FileOutputStream out = new FileOutputStream(file);

				if (bitmap.compress(format, quality, out)) {
					out.flush();
					out.close();
					return true;
				}
			} catch (FileNotFoundException e) {
				Utils.debug(e.toString());
			} catch (IOException e) {
				Utils.debug(e.toString());
			}
		} else {
			Utils.debug("SDCard is not available!");
		}

		return false;
	}

	@Override
	public String getNewTmpImagePath() {
		return getNewTmpImagePath(ImageUtils.SUFFIX_JPEG);
	}

	@Override
	public String getNewTmpImagePath(String imageSuffix) {
		return mImageCacheDir + TMP_IMAGE_INITIAL + System.currentTimeMillis() + imageSuffix;
	}

	@Override
	public void selectGetImageWay(final Activity activity, View hangView, final String takePicturePath) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(hangView.getWindowToken(), 0);
		}
		// 实例化SelectPicPopupWindow
		final SelectPicPopupWindow menuWindow = new SelectPicPopupWindow(activity);
		menuWindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuWindow.dismiss();
				switch (v.getId()) {
				case R.id.btn_take_photo:
					// 跳转相机拍照
					String sdStatus = Environment.getExternalStorageState();
					if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
						Utils.toastMessageForce(activity, context.getString(R.string.error_can_not_find_sdcard));
						return;
					}
					Intent intentTakenPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 指定调用相机拍照后的照片存储的路径
					intentTakenPic.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(takePicturePath)));
					activity.startActivityForResult(intentTakenPic, ReqCode.CAMERA);
					break;
				case R.id.btn_pick_photo:
					Intent intentPickPic = new Intent(Intent.ACTION_PICK, null);
					intentPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_CONTENT_TYPE);
					activity.startActivityForResult(intentPickPic, ReqCode.ALBUM);
					break;
				default:
					break;
				}
			}
		});
		// 显示窗口
		menuWindow.showAtLocation(hangView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	@Override
	public void cutTheImage(Activity activity, Uri uri, String cuttedImagePath, int aspectX, int aspectY, int outputX, int outputY, String outputFormat) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_CONTENT_TYPE);
		intent.putExtra("crop", "true");

		// aspectX aspectY 宽高的比例
		if (aspectX > 0)
			intent.putExtra("aspectX", aspectX);
		if (aspectY > 0)
			intent.putExtra("aspectY", aspectY);

		// outputX outputY 裁剪图片宽高
		if (outputX > 0)
			intent.putExtra("outputX", outputX);
		if (outputY > 0)
			intent.putExtra("outputY", outputY);

		intent.putExtra("outputFormat", FORMAT_IN_STRING_JPEG);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cuttedImagePath)));

		activity.startActivityForResult(intent, ReqCode.CUTTED);
	}

	@Override
	public void cutTheImageNormal(Activity activity, Uri uri, String cuttedImagePath) {
		cutTheImage(activity, uri, cuttedImagePath, -1, -1, -1, -1, FORMAT_IN_STRING_JPEG);
	}

	@Override
	public void cutTheImageHead(Activity activity, Uri uri, String cuttedImagePath) {
		cutTheImage(activity, uri, cuttedImagePath, 1, 1, -1, -1, FORMAT_IN_STRING_JPEG);
	}

	@Override
	public String compressImage(String imagePath) {
		return compressImage(imagePath, Constant.IMAGE_QUALITY);
	}

	@Override
	public String compressImage(String imagePath, int quality) {
		return compress(imagePath, quality);
	}
}

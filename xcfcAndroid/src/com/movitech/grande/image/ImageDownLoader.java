package com.movitech.grande.image;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月4日 上午10:06:26
 * 类说明
 */

public class ImageDownLoader {
	/**
	 * 缓存Image的类，当存储Image的大小大于LruCache设定的值，系统自动释放内存
	 */
	private LruCache<String, Bitmap> mMemoryCache;
	/**
	 * 操作文件相关类对象的引用
	 */
	private FileUtils fileUtils;
	//private static ImageDownLoader instance;
	/**
	 * 下载Image的线程池
	 */
	private ExecutorService mImageThreadPool = null;
	
	
	public ImageDownLoader(Context context){
		//获取系统分配给每个应用程序的最大内存，每个应用系统分配16M
		int maxMemory = (int) Runtime.getRuntime().maxMemory();  
        int mCacheSize = maxMemory / 8;
        //给LruCache分配1/8 4M
		mMemoryCache = new LruCache<String, Bitmap>(mCacheSize) {

			//必须重写此方法，来测量Bitmap的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
			
		};
		
		fileUtils = new FileUtils(context);
	}
	 /**
	   * 使用单例，保证整个应用中只有一个线程池和一份内存缓存和文件缓存
	   */
	  /*public static ImageDownLoader getInstance(Context context)
	  {
	    if (instance == null)
	      instance = new ImageDownLoader(context);
	    return instance;
	  }*/
	
	/**
	 * 获取线程池的方法，因为涉及到并发的问题，我们加上同步锁
	 * @return
	 */
	public ExecutorService getThreadPool() {
		if(mImageThreadPool == null){
			synchronized(ExecutorService.class) {
				if(mImageThreadPool == null) {
					//为了下载图片更加的流畅，我们用了2个线程来下载图片
					mImageThreadPool = Executors.newFixedThreadPool(2);
				}
			}
		}
		
		return mImageThreadPool;
		
	}
	
	/**
	 * 添加Bitmap到内存缓存
	 * @param key
	 * @param bitmap
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {  
	    if (getBitmapFromMemCache(key) == null && bitmap != null) {  
	        mMemoryCache.put(key, bitmap);  
	    }  
	}  
	 
	/**
	 * 从内存缓存中获取一个Bitmap
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFromMemCache(String key) {  
	    return mMemoryCache.get(key);  
	} 
	
	/**
	 * 先从内存缓存中获取Bitmap,如果没有就从SD卡或者手机缓存中获取，SD卡或者手机缓存
	 * 没有就去下载
	 * @param url
	 * @param listener
	 * @return
	 */
	@SuppressLint("HandlerLeak") 
	public Bitmap downloadImage(final String url, final onImageLoaderListener listener, final Boolean isMoreAction){
		//替换Url中非字母和非数字的字符，这里比较重要，因为我们用Url作为文件名，比如我们的Url
		//是Http://xiaanming/abc.jpg;用这个作为图片名称，系统会认为xiaanming为一个目录，
		//我们没有创建此目录保存文件就会报错
		if (url != null && !url.equals("")) {
			final String subUrl = url.replaceAll("[^\\w]", "");
			Bitmap bitmap = showCacheBitmap(subUrl);
			if (bitmap != null) {
				return bitmap;
			} else {
				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						listener.onImageLoader((Bitmap) msg.obj, url);
					}
				};

				getThreadPool().execute(new Runnable() {

					@Override
					public void run() {
						Bitmap bitmap = getBitmapFormUrl(url, isMoreAction);
						Message msg = handler.obtainMessage();
						msg.obj = bitmap;
						handler.sendMessage(msg);

						try {
							// 保存在SD卡或者手机目录
							if (!isMoreAction) {
								return;
							}
							fileUtils.savaBitmap(subUrl, bitmap);
						} catch (IOException e) {
							e.printStackTrace();
						}

						// 将Bitmap 加入内存缓存
						//addBitmapToMemoryCache(subUrl, bitmap);
					}
				});
			}

		}
		return null;
	}
	
	/**
	 * 获取Bitmap, 内存中没有就去手机或者sd卡中获取
	 * @param url
	 * @return
	 */
	public Bitmap showCacheBitmap(String url) {
		try {
			if(getBitmapFromMemCache(url) != null) {
				return getBitmapFromMemCache(url);
			}else if(fileUtils.isFileExists(url) && fileUtils.getFileSize(url) != 0){
				//从SD卡获取手机里面获取Bitmap
			
				Bitmap bitmap = fileUtils.getBitmap(url);			
				//将Bitmap 加入内存缓存
				//addBitmapToMemoryCache(url, bitmap);
				return bitmap;
			}
			
		} catch(OutOfMemoryError e) {
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	
	/**
	 * 从Url中获取Bitmap
	 * @param url
	 * @return
	 */
	private Bitmap getBitmapFormUrl(String url, boolean isInSample) {
		Bitmap bitmap = null;
		HttpURLConnection con = null;
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPreferredConfig = Bitmap.Config.RGB_565;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			if (isInSample) {
				opt.inSampleSize = 2;
			}else
				opt.inSampleSize = 1;		
			opt.inJustDecodeBounds = false;
			URL mImageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) mImageUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			//bitmap = BitmapFactory.decodeStream(is, null, opt);
			bitmap = decodeSampledBitmapFromResourceMemOpt(is);
			is.close();
		} catch (OutOfMemoryError e) {
          
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return bitmap;
	}
	
	/**
	 * 取消正在下载的任务
	 */
	public synchronized void cancelTask() {
		if(mImageThreadPool != null){
			mImageThreadPool.shutdownNow();
			mImageThreadPool = null;
		}
	}
	
	
	/**
	 * 异步下载图片的回调接口
	 * @author len
	 *
	 */
	public interface onImageLoaderListener{
		void onImageLoader(Bitmap bitmap, String url);
	}
	
	/**
	 * 字节生成bitmap
	 * @param inputStream
	 * @return
	 */
	public Bitmap decodeSampledBitmapFromResourceMemOpt(InputStream inputStream) {
		//如果图片流为空
		byte[] byteArr = new byte[0];
		byte[] buffer = new byte[1024];
		int len;
		int count = 0;
		try {
			while ((len = inputStream.read(buffer)) > -1) {
				if (len != 0) {
					if (count + len > byteArr.length) {
						byte[] newbuf = new byte[(count + len) * 2];
						System.arraycopy(byteArr, 0, newbuf, 0, count);
						byteArr = newbuf;
					}
					System.arraycopy(buffer, 0, byteArr, count, len);
					count += len;
				}
			}
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1;
			options.inPurgeable = true;
			options.inInputShareable = true;
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			return BitmapFactory.decodeByteArray(byteArr, 0, count, options);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

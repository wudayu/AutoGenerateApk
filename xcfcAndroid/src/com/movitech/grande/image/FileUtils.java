package com.movitech.grande.image;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月4日 上午10:05:40
 * 类说明
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

@EBean(scope = Scope.Singleton)
public class FileUtils {

	/**
	 * sd卡的根目录
	 */
	private static String mSdRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	/**
	 * 手机的缓存根目录
	 */
	private static String mDataRootPath = null;
	/**
	 * 保存Image的目录名
	 */
	private final static String FOLDER_NAME = "/hftImage";

	public FileUtils(Context context) {

		// mDataRootPath = context.getCacheDir().getPath();
		mDataRootPath = context.getFilesDir().getAbsolutePath();
	}

	/**
	 * 获取储存Image的目录
	 * 
	 * @return
	 */
	private String getStorageDirectory() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) ? mSdRootPath + FOLDER_NAME
				: mDataRootPath + FOLDER_NAME;
	}

	/**
	 * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
	 * 
	 * @param fileName
	 * @param bitmap
	 * @throws IOException
	 */
	public void savaBitmap(String fileName, Bitmap bitmap) throws IOException {
		if (bitmap == null) {
			return;
		}
		String path = getStorageDirectory();
		File folderFile = new File(path);
		if (!folderFile.exists()) {
			folderFile.mkdir();
		}
		File file = new File(path + File.separator + fileName);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		bitmap.compress(CompressFormat.JPEG, 100, fos);
		fos.flush();
		fos.close();
	}

	/**
	 * 从手机或者sd卡获取Bitmap
	 * 
	 * @param fileName
	 * @return
	 */
	public Bitmap getBitmap(String fileName) {
		String filePath = getStorageDirectory() + File.separator + fileName;
		FileInputStream inputStream;
		File file = new File(filePath);
		Bitmap mBitmap = null;
		try {
			inputStream = new FileInputStream(file);
			// mBitmap = decodeSampledBitmapFromResource(inputStream, 800, 800);
			mBitmap = decodeSampledBitmapFromResourceMemOpt(inputStream);
		/*	return BitmapFactory.decodeFile(getStorageDirectory()
					+ File.separator + fileName);*/
			return mBitmap;
		} catch (Exception e) {

		}
		return mBitmap;
	}
	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExists(String fileName) {
		return new File(getStorageDirectory() + File.separator + fileName)
				.exists();
	}

	/**
	 * 获取文件的大小
	 * 
	 * @param fileName
	 * @return
	 */
	public long getFileSize(String fileName) {
		return new File(getStorageDirectory() + File.separator + fileName)
				.length();
	}

	/**
	 * 删除SD卡或者手机的缓存图片和目录
	 */
	public void deleteFile() {
		File dirFile = new File(getStorageDirectory());
		if (!dirFile.exists()) {
			return;
		}
		if (dirFile.isDirectory()) {
			String[] children = dirFile.list();
			for (int i = 0; i < children.length; i++) {
				new File(dirFile, children[i]).delete();
			}
		}

		dirFile.delete();
	}
	
	
	

	
	/**
	 * 字节生成bitmap
	 * @param inputStream
	 * @return
	 */
	public Bitmap decodeSampledBitmapFromResourceMemOpt(FileInputStream inputStream) {

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
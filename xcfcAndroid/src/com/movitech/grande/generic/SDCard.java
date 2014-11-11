package com.movitech.grande.generic;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.movitech.grande.generic.interfaces.ISDCard;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 10:02:41 AM
 * @Description: This is David Wu's property.
 *
 **/

@EBean
public class SDCard implements ISDCard {
	
	@RootContext
	Context context;

	/**
	 * SD卡是否可用
	 * 
	 * @return true->SD卡可用
	 */
	@Override
	public boolean isSdcardAvaliable() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * SD卡空间是否足够 单位是b
	 * 
	 * @param size
	 *            文件大小
	 * @return true->空间充足
	 */
	@Override
	@SuppressWarnings("deprecation")
	public boolean isAvaiableSpace(long size) {
		boolean ishasSpace = false;
		if (isSdcardAvaliable()) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			StatFs statFs = new StatFs(sdcard);
			long blockSize = statFs.getBlockSize();
			long blocks = statFs.getAvailableBlocks();
			long availableSpare = blocks * blockSize;
			if (availableSpare > size) {
				ishasSpace = true;
			}
		} else {
			Utils.debug("SD Card Not Found !");
			return false;
		}
		return ishasSpace;
	}
}
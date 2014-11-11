package com.movitech.grande.generic;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.movitech.grande.constant.Constant;

/**
 * @description 常用工具方法类
 */
@SuppressWarnings("deprecation")
public class Utils {

	public static final String TAG = "testcase";

	private static final String LOG_FILE_NAME = "debug.log";
	private static final boolean DEBUG = Constant.DEBUG;

	private static Toast mToast;

	public static void debug(String message) {
		if (DEBUG) {
			System.out.println(message);
			// output(message);
		}
	}

	public static void debug(String tag, String message) {
		if (DEBUG) {
			Log.v(tag, message);
			// output(message);
		}
	}

	private static long lastClickTime;

	/**
	 * 防止重复点击
	 * 
	 * @return 是否重复点击了
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param ctx
	 * @return
	 */
	public static int getStatusBarHeight(Activity ctx) {
		Rect frame = new Rect();
		ctx.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

		return frame.top;
	}

	/**
	 * 获取图片缩略图地址 <br>
	 * 最大边长200像素
	 * 
	 * @param url
	 * @return
	 */
	public static String getThumbUrl(String url) {
		return url + "!200";
	}

	/**
	 * 获取图片缩略图地址 <br>
	 * 最大边长可变
	 * 
	 * @param url
	 * @param max
	 * @return
	 */
	public static String getThumbUrl(String url, int max) {
		return url + "!" + max;
	}

	public static synchronized void output(String message) {

		File file = new File(Environment.getExternalStorageDirectory(),
				LOG_FILE_NAME);
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try {
			fos = new FileOutputStream(file, true);
			dos = new DataOutputStream(fos);
			SimpleDateFormat sdf = new SimpleDateFormat("MM.dd HH:mm:ss",
					Locale.US);
			String suffix = sdf.format(new Date());
			String content = suffix + "  " + message + "\n";
			dos.writeUTF(content);
		} catch (IOException e) {
			debug(e.toString());
		} catch (Exception e) {
			debug(e.toString());
		} finally {
			try {
				if (dos != null) {
					dos.flush();
					dos.close();
					dos = null;
				}
				if (fos != null) {
					fos.flush();
					fos.close();
					fos = null;
				}
			} catch (IOException e) {
				debug(e.toString());
			}
		}
	}

	/**
	 * 判断是否是合法的Email地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		Matcher mc = pattern.matcher(email);
		return mc.matches();
	}

	/**
	 * 判断是否是合法的URL
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isValidURL(String url) {
		Pattern patterna = Patterns.WEB_URL;
		Matcher mca = patterna.matcher(url);
		return mca.matches();
	}

	/**
	 * 是否为空
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		boolean result;
		if (!(object instanceof String)) {
			return (null == object);
		}
		if (TextUtils.isEmpty((CharSequence) (object))) {
			result = true;
		} else {
			String str = String.valueOf(object);
			str = str.toLowerCase(Locale.ENGLISH);
			result = ("null").equals(str);
		}
		return result;
	}

	/**
	 * 如果键盘没有收回 自动关闭键盘
	 * 
	 * @param activity
	 *            Activity
	 * @param v
	 *            控件View
	 */
	public static void autoCloseKeyboard(Activity activity, View v) {
		/** 收起键盘 */
		View view = activity.getWindow().peekDecorView();
		if (view != null && view.getWindowToken() != null) {
			InputMethodManager imm = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	/**
	 * 判断当前应用是否在前台
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAppForground(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> l = mActivityManager
				.getRunningAppProcesses();
		Iterator<RunningAppProcessInfo> i = l.iterator();
		while (i.hasNext()) {
			RunningAppProcessInfo info = i.next();
			if (info.uid == context.getApplicationInfo().uid
					&& info.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断指定Activity是否在前台
	 * 
	 * @param context
	 * @param activity
	 *            , example: new ComponentName("mobi.palmwin.helen",
	 *            "mobi.palmwin.helen.activity.LiveActivity_");
	 * @return
	 */
	public static boolean isActivityForground(Context context,
			ComponentName activity) {
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> l = mActivityManager.getRunningTasks(1);
		Iterator<RunningTaskInfo> i = l.iterator();

		while (i.hasNext()) {
			RunningTaskInfo info = i.next();
			if (info.topActivity.compareTo(activity) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否为锁屏状态
	 * 
	 * @param c
	 * @return
	 */
	public final static boolean isScreenLocked(Context c) {
		KeyguardManager mKeyguardManager = (KeyguardManager) c
				.getSystemService(Context.KEYGUARD_SERVICE);
		return mKeyguardManager.inKeyguardRestrictedInputMode();
	}

	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public static String getMD5(String str) {
		String resultString = null;
		try {
			resultString = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			Utils.debug("getMD5 : " + e.toString());
		}
		return resultString;
	}

	/**
	 * 唤醒屏幕，如果当前锁屏了
	 */
	@SuppressLint("Wakelock")
	public static void notifyScreen(Context context) {
		if (!isScreenLocked(context)) {
			return;
		}
		KeyguardManager km = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardLock kl = km.newKeyguardLock("unLock");
		kl.disableKeyguard(); // 解锁
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);// 获取电源管理器对象
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		wl.acquire(5000);// 点亮屏幕
		// wl.release();//释放
	}

	/**
	 * 转小写
	 * 
	 * @param input
	 * @return
	 */
	public static String toLowerCase(String input) {
		return input.toLowerCase(Locale.ENGLISH);
	}

	/**
	 * 转大写
	 * 
	 * @param input
	 * @return
	 */
	public static String toUpperCase(String input) {
		return input.toUpperCase(Locale.ENGLISH);
	}

	/**
	 * 数组相加
	 * 
	 * @param arrays
	 * @return
	 */
	public static Integer[] intArrayPlus(Integer[]... arrays) {
		int size = 0;
		for (int i = 0; i < arrays.length; i++) {
			Integer[] temp = arrays[i];
			size += temp.length;
		}

		Integer[] all = new Integer[size];
		int dstPos = 0;
		for (int i = 0; i < arrays.length; i++) {
			System.arraycopy(arrays[i], 0, all, dstPos, arrays[i].length);
			dstPos += arrays[i].length;
		}
		return all;
	}

	/**
	 * 打印消息并且用Toast显示消息
	 * 
	 * @param activity
	 * @param message
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,
			final String message, String logLevel) {
		if ("w".equals(logLevel)) {
			Log.w("sdkDemo", message);
		} else if ("e".equals(logLevel)) {
			Log.e("sdkDemo", message);
		} else {
			Log.d("sdkDemo", message);
		}
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast != null) {
					mToast.cancel();
					mToast = null;
				}
				mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
				mToast.show();
			}
		});
	}

	public static final void toastMessageForce(final Activity activity,
			final String message) {
		toastMessage(activity, message, null);
	}

	/**
	 * 打印消息并且用Toast显示消息
	 * 
	 * @param activity
	 * @param message
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,
			final String message) {
		if (Constant.NO_TOAST)
			return;

		toastMessage(activity, message, null);
	}

	/**
	 * 根据一个网络连接(String)获取bitmap图像
	 * 
	 * @param imageUri
	 * @return
	 * @throws MalformedURLException
	 */
	public static Bitmap getbitmap(String imageUri) {
		Log.v(TAG, "getbitmap:" + imageUri);
		// 显示网络上的图片
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();

			Log.v(TAG, "image download finished." + imageUri);
		} catch (IOException e) {
			e.printStackTrace();
			Log.v(TAG, "getbitmap bmp fail---");
			return null;
		}
		return bitmap;
	}

	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager;

		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * getDeviceId() function Returns the unique device ID. for example,the
		 * IMEI for GSM and the MEID or ESN for CDMA phones.
		 */
		String imeistring = telephonyManager.getDeviceId();

		/*
		 * getSubscriberId() function Returns the unique subscriber ID, for
		 * example, the IMSI for a GSM phone.
		 */
		// String imsistring = telephonyManager.getSubscriberId();

		return imeistring;
	}
	
	/**
	 * 仅包含数字的号码格式
	 * 
	 * @param number
	 * @return example:"18651817673"
	 */
	public static String formatNumberOnlyDigits(String number) {
		if(Utils.isNull(number)){
			return "";
		}
		return number.replaceAll("[^0-9]", "");
	}

	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Utils.debug(Utils.TAG, e.toString());
		}

		return packInfo == null ? null : packInfo.versionName;
	}
}

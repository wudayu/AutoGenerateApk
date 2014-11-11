package com.movitech.grande.utils;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.movitech.grande.constant.Constant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class FileUtils {
	private static String SDPATH;

	private static final String TAG = "FileUtil";

	private int FILESIZE = 4 * 1024;

	public static String getSDPATH() {
		return SDPATH;
	}

	public FileUtils() {
		// 得到当前外部存储设备的目录( /SDCARD )
		SDPATH = Constant.SD_CARD_XCFC;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */
	public File createSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean deleteFile(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.delete();
	}

	/**
	 * @param absolutePath
	 * @return
	 * 
	 *         创建时间：2011-5-16 创建人：wangbing 方法描述：根据传入的绝对路径删除文件 （参数含义说明如下）
	 */
	public boolean deleteFileWithPath(String absolutePath) {
		File file = new File(absolutePath);
		return file.delete();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public File write2SDFromInput(String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			int lastSeperator = fileName.lastIndexOf("/");
			if (lastSeperator != -1) {
				String path = fileName.substring(0, lastSeperator);
				createSDDir(path);
			}
			file = createSDFile(fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * @param fileName
	 * @param bytes
	 * @return
	 * 
	 *         创建时间：2011-5-16 创建人：wangbing 方法描述：把byte数组写入到文件 位于SDCARD根目录下
	 *         （参数含义说明如下） 文件名，字节数组(建议不能太大，容易内存溢出)
	 */
	public String writeFile2SD(String fileName, byte[] bytes) {
		File file = null;
		OutputStream output = null;
		try {
			int lastSeperator = fileName.lastIndexOf("/");
			if (lastSeperator != -1) {
				String path = fileName.substring(0, lastSeperator);
				createSDDir(path);
			}
			file = createSDFile(fileName);
			output = new FileOutputStream(file);
			output.write(bytes);
			output.flush();
			return file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	// 建立一个MIME类型与文件后缀名的匹配表
	private final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".aspx", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
			{ ".c", "text/plain" }, { ".class", "application/octet-stream" },
			{ ".conf", "text/plain" }, { ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".exe", "application/octet-stream" }, { ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" },
			{ ".h", "text/plain" }, { ".htm", "text/html" },
			{ ".html", "text/html" }, { ".jar", "application/java-archive" },
			{ ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" },
			{ ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" }, { ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".prop", "text/plain" },
			{ ".rar", "application/x-rar-compressed" },
			{ ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
			{ ".rtf", "application/rtf" }, { ".sh", "text/plain" },
			{ ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/xml" },
			{ ".xml", "text/plain" }, { ".z", "application/x-compress" },
			{ ".zip", "application/zip" }, { "", "*/*" },
			{ ".xls", "application/vnd.ms-excel" } };

	/**
	 * 打开文件
	 * 
	 * @param file
	 */
	public void openFile(Context context, File file) {
		try {

			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 设置intent的Action属性
			intent.setAction(Intent.ACTION_VIEW);
			// 获取文件file的MIME类型
			String type = getMIMEType(file);
			// 设置intent的data和Type属性。
			intent.setDataAndType(Uri.fromFile(file), type);
			context.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(context, "打开文件出错，请检查是否安装相应软件", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private String getMIMEType(File file) {
		String type = "*/*";
		String fName = file.getName();

		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}

		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;

		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	/**
	 * 判断存储卡上的剩余空间是否大于sizeMb，单位为M
	 * 
	 * @param sizeMb
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isAvaiableSpace(int sizeMb) {
		boolean ishasSpace = false;
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			StatFs statFs = new StatFs(sdcard);
			long blockSize = statFs.getBlockSize();
			long blocks = statFs.getAvailableBlocks();
			long availableSpare = (blocks * blockSize) / (1024 * 1024);
			Log.d("剩余空间", "availableSpare = " + availableSpare);
			if (availableSpare > sizeMb) {
				ishasSpace = true;
			}
		}
		return ishasSpace;
	}

	public static File getCacheFile(String imageUri) {
		File cacheFile = null;
		try {
			File fff = new File(Constant.SD_CARD_XCFC);
			if (!fff.exists()) {
				fff.mkdir();
			}
			File dir = new File(Constant.SD_DATA);
			if (!dir.exists()) {
				dir.mkdir();
			}

			String fileName = getFileName(imageUri);
			cacheFile = new File(dir, fileName);
			// Log.v(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir
			// + ",file:" + fileName);

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "getCacheFileError:" + e.getMessage());
		}

		return cacheFile;
	}

	public static String getFileName(String path) {
		int index = path.lastIndexOf("/");
		return path.substring(index + 1).replace("?", "");
	}

	
	//判断机器Android是否已经root，即是否获取root权限
    protected static boolean haveRoot()
    {

      int i = execRootCmdSilent("echo test"); //通过执行测试命令来检测
      if (i != -1)  return true;
      return false;
    }
    protected static int execRootCmdSilent(String paramString)
    {
    	Object localObject = -1;
      try
      {
        Process localProcess = Runtime.getRuntime().exec("su");
         localObject = localProcess.getOutputStream();
        DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream)localObject);
        String str = String.valueOf(paramString);
        localObject = str + "\n";
        localDataOutputStream.writeBytes((String)localObject);
        localDataOutputStream.flush();
        localDataOutputStream.writeBytes("exit\n");
        localDataOutputStream.flush();
        localProcess.waitFor();
        localObject = localProcess.exitValue();
        return (Integer) localObject;
      }
      catch (Exception localException)
      {
    	 return (Integer) localObject;
      }
    }
}

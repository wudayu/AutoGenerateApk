package com.movitech.grande.generic.interfaces;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 10:04:36 AM
 * @Description: This is David Wu's property.
 *
 **/

public interface IFile {
	public String getFileDir();

	public String getCacheDir();

	public boolean isFileExists(String filePath);

	public boolean createFolder(String folderPath);

	public boolean deleteFile(String filePathAndName);

	public boolean deleteFolder(String path, boolean recursive);

	public boolean deleteFilesInFloder(String path, boolean recursive);

	public String getFileBaseName(final String fileName);

	public String getFileExtension(final String fileName);

	public boolean copyFile(String sourcePath, String targetPath);

	public void copyFile(File src, File dst) throws IOException;

	public String getDirPath(String filePath);

	public String getFileName(String filePath);

	public String getFileNameWithoutSuffix(String path);

	public String readTextFile(String path);

	public ArrayList<String> listFiles(String path, FileFilter filter,
			boolean recursive);

	public boolean renameFile(String pathOri, String pathNew);

	public String getFileNameInUrl(String url);

	public String getDataSizeTxt(Long size);

	public void saveBitmap(String fullPath, Bitmap bitmap, CompressFormat format);

	public void saveBitmap(String fullPath, Bitmap bitmap, int quality,
			CompressFormat format);

	public void saveBitmap(File file, Bitmap bitmap, int quality,
			CompressFormat format);

	public Bitmap decodeFile(File f);

	public void copyFileFromAsset(InputStream source, File dst);

	public long getFolderOrFileSize(String path);

	public void cleanCache(String path, long size, long millSecAgo);

	public void createNoMediaFile(String path);

	public void doUnZipAssets(String path, String zipfileInAssets);
}

package com.movitech.grande.generic;

import java.io.File;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;

import android.content.Context;

import com.movitech.grande.generic.interfaces.IFile;
import com.movitech.grande.generic.interfaces.IFileHandler;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 10:18:23 AM
 * @Description: This is David Wu's property.
 *
 **/

@EBean(scope = Scope.Singleton)
public class FileHandler implements IFileHandler {
	@Bean(FileUtils.class)
	IFile file;
	@RootContext
	Context context;

	public FileHandler(Context context) {
		this.context = context;
	}

	public String getCacheDirByType(CacheDir dir) {
		String path = file.getCacheDir() + dir;

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	public String getFileDirByType(DataDir dir) {
		String path = file.getFileDir() + dir;

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

}
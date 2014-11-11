package com.movitech.grande.generic;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;

import com.movitech.grande.generic.interfaces.IZip;

/**
 * 
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 10:07:49 AM
 * @Description: This is David Wu's property.
 * 
 **/

@EBean(scope = Scope.Singleton)
public class ZipUtils implements IZip {
	@Override
	public void extnativeZipFileList(InputStream in, String path)
			throws Exception {
		File f = new File(path);

		if (!f.exists()) {
			f.mkdirs();
		}

		ZipInputStream zin = new ZipInputStream(in);
		ZipEntry entry;

		while ((entry = zin.getNextEntry()) != null) {
			String entryName = entry.getName();

			if (entry.isDirectory()) {
				File directory = new File(path, entryName);

				if (!directory.exists()) {
					directory.mkdirs();
				}

				zin.closeEntry();
			} else {
				FileOutputStream fos = new FileOutputStream(path + entryName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				byte[] b = new byte[2048];
				int len = 0;

				while ((len = zin.read(b)) != -1) {
					bos.write(b, 0, len);
				}

				bos.close();
				fos.close();
				zin.closeEntry();
			}
		}
	}
}
package com.movitech.grande.generic.interfaces;

import java.io.InputStream;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 16, 2014 10:12:05 AM
 * @Description: This is David Wu's property.
 *
 **/
public interface IZip {
	public void extnativeZipFileList(InputStream in, String path)
			throws Exception;
}

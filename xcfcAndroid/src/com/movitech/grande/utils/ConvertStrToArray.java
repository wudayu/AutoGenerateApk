package com.movitech.grande.utils;
/**
 * @author Warkey.Song
 * @version 创建时间：2014年8月25日 下午2:16:37
 * 类说明
 */
public class ConvertStrToArray {
	 //使用String的split 方法  
	public static String[] convertStrToArray(String str) {
		if (str == null) {
			return null;
		}
		if (str.contains("、")) {
			String[] strArray = null;
			strArray = str.split("、"); // 拆分字符为"、" ,然后把结果交给数组strArray
			return strArray;
		} else
			return null;
	}
}

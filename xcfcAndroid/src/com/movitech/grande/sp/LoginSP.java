package com.movitech.grande.sp;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年9月1日 下午1:52:56
 * 类说明
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface LoginSP {

	@DefaultBoolean(true)
	boolean isFirstLogin();
}

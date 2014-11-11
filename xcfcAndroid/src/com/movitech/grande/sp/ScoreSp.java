package com.movitech.grande.sp;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月15日 上午10:57:49
 * 类说明
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface ScoreSp {
   @DefaultString("")
   String signIdAndTime();
}

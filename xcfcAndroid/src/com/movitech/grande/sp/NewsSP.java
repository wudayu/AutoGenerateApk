package com.movitech.grande.sp;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 15, 2014 12:04:50 PM
 * @Description: This is David Wu's property.
 *
 **/
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface NewsSP {

	@DefaultString("0000-00-00 00:00:00")
	String latestHouseNewsDate();
}

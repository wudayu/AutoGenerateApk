package com.movitech.grande.sp;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 10, 2014 10:43:05 AM
 * @Description: This is David Wu's property.
 *
 **/
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface AppStateSP {

	@DefaultBoolean(true)
	boolean isFirstStartUp();

}

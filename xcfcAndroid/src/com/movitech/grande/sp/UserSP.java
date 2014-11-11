package com.movitech.grande.sp;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 18, 2014 2:09:15 PM
 * @Description: This is David Wu's property.
 *
 **/
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface UserSP {

	@DefaultString("")
	String currUserId();

	@DefaultString("")
	String latestUserHeaderSrc();
	
	@DefaultString("")
	String currBrokerType();
	
	@DefaultString("")
	String currPhone();
	
	@DefaultString("")
	String currUserType();
	
	@DefaultString("")
	String currUserApproveState();
	
	@DefaultString("")
	String currUserCityName();
}

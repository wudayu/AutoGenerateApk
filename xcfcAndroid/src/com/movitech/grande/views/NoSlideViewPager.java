package com.movitech.grande.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 12, 2014 11:22:05 PM
 * @Description: This is David Wu's property.
 *
 **/
public class NoSlideViewPager extends BaseViewPager {

	public NoSlideViewPager(Context context) {
		super(context);
	}

	public NoSlideViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
  
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}
}

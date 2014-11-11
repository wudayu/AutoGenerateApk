package com.movitech.grande.activity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.widget.ImageView;

import com.movitech.grande.haerbin.R;

/**
 * 
 * @author Warkey.Song
 * @Description: This is Earn Commission Activity.
 */
@EActivity(R.layout.activity_commission_earn)
public class CommissionEarnActivity extends BaseActivity{
   @ViewById(R.id.iv_back)
   ImageView ivBack;
   
   @Click
   void ivBack(){
	   finish();
   }
}

package com.movitech.grande.views;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.movitech.grande.haerbin.R;

/**
 * @author Warkey.Song
 * @version 2014年11月6日 上午9:39:46
 */
public class LoginExitDialog extends AlertDialog {

	private String loadTxt = null;
	
	public LoginExitDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public LoginExitDialog(Context context, int theme) {
		super(context, theme);
	}

	public LoginExitDialog(Context context) {
		super(context);
	}

	
	public void setLoadTxt(String loadTxt) {
		this.loadTxt  =loadTxt;
	}
	@Override
	public void show() {
		super.show();

		setContentView(R.layout.layout_loading_login);
		TextView tvLoad = (TextView) findViewById(R.id.tv_load);
		ImageView ivCircle = (ImageView) findViewById(R.id.iv_processing);
		tvLoad.setText(loadTxt);
		ivCircle.setBackgroundResource(R.drawable.processing_dialog_animation);
		AnimationDrawable frameAnimation = (AnimationDrawable) ivCircle
				.getBackground();
		frameAnimation.start();
	}
	
}

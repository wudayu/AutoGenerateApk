package com.movitech.grande.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;


/**
 *
 *
 **/
public class CustomHorizontalScrollView extends HorizontalScrollView {

	public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomHorizontalScrollView(Context context) {
		super(context);
		init();
	}

	View contentView = null;

	private void init() {}

	OnEdgeListener onEdgeListener = null;

	public interface OnEdgeListener {
		public void onLeft();
		public void onRight();
	}

	public void setOnEdgeListener(OnEdgeListener listener) {
		onEdgeListener = listener;
		contentView = this.getChildAt(0);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (contentView != null && contentView.getMeasuredWidth() <= getScrollX() + getWidth()) {
			if (onEdgeListener != null) {
				onEdgeListener.onRight();
			}
		} else if (getScrollX() == 0) {
			if (onEdgeListener != null) {
				onEdgeListener.onLeft();
			}
		}

		super.onScrollChanged(l, t, oldl, oldt);
	}
}

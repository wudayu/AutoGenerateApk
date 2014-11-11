package com.movitech.grande.views;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author Warkey.Song
 * @version 创建时间：2014年7月7日 上午11:12:26 类说明
 */

public class MaxHeightListView extends LoadDataListView {

	/** listview高度 */
	private int listViewHeight;

	public int getListViewHeight() {
		return listViewHeight;
	}

	public void setListViewHeight(int listViewHeight) {
		this.listViewHeight = listViewHeight;
	}

	public MaxHeightListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MaxHeightListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MaxHeightListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (listViewHeight > -1) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(listViewHeight,
					MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}

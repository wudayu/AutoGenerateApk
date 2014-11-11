package com.movitech.grande.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;


/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jul 3, 2014 12:01:47 PM
 * @Description: This is David Wu's property.
 *
 **/
public class LoadDataGridView extends GridView {

	public LoadDataGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadDataGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadDataGridView(Context context) {
		super(context);
		init();
	}

	private int visibleLastIndex = 0;	//最后的可视项索引
    private int visibleItemCount;	// 当前窗口可见项总数
    private ListAdapter currAdapter;
    private OnScrollToEdgeCallBack onScrollToEdgeCallBack;
    private int totalPageCount = -1;
    private int currentPage = -1;

    private void init() {
    	this.setOnScrollListener(new ScrollListener());
    }
    public int getVisibleLastIndex() {
		return visibleLastIndex;
	}

	public void setVisibleLastIndex(int visibleLastIndex) {
		this.visibleLastIndex = visibleLastIndex;
	}

	public int getVisibleItemCount() {
		return visibleItemCount;
	}

	public void setVisibleItemCount(int visibleItemCount) {
		this.visibleItemCount = visibleItemCount;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		this.currAdapter = adapter;

		super.setAdapter(adapter);
	}

	public void setOnScrollToEdgeCallBack(OnScrollToEdgeCallBack callBack) {
		this.onScrollToEdgeCallBack = callBack;
	}

	private final class ScrollListener implements OnScrollListener {
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			LoadDataGridView.this.visibleItemCount = visibleItemCount;
	        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
	          
		}
	
		@Override  
	    public void onScrollStateChanged(AbsListView view, int scrollState) {  
	        int itemsLastIndex = currAdapter.getCount() - 1;    //数据集最后一项的索引
           
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& visibleLastIndex == itemsLastIndex) {
				if (onScrollToEdgeCallBack != null) {
					onScrollToEdgeCallBack.toBottom();
				}
			}
	    }
	}

	public interface OnScrollToEdgeCallBack {
		public void toBottom();
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;

		loadedAllData();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPageCount) {
		this.currentPage = currentPageCount;

		loadedAllData();
	}

	private void loadedAllData() {
		if (totalPageCount > 0 && currentPage >= totalPageCount) {
			this.setOnScrollListener(null);
		}
	}

	public void addOnScrollListener(){
		init();
	}
	
	public void resetVar(){
		 totalPageCount = -1;
	     currentPage = -1;

	}
}

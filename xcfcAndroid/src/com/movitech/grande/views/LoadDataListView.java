package com.movitech.grande.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.movitech.grande.haerbin.R;



/**
 *
 * @author: Wu Dayu
 * @En_Name: David Wu
 * @E-mail: wudayu@gmail.com
 * @version: 1.0
 * @Created Time: Jun 23, 2014 12:42:48 PM
 * @Description: This is David Wu's property.
 *
 **/
public class LoadDataListView extends ListView {

	public LoadDataListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public LoadDataListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoadDataListView(Context context) {
		super(context);
		init(context);
	}

	private Context context;
	private int visibleLastIndex = 0;	//最后的可视项索引
    private int visibleItemCount;	// 当前窗口可见项总数
    private ListAdapter currAdapter;
    private OnScrollToEdgeCallBack onScrollToEdgeCallBack;
    private View footerView;
    private View headerView;
    private int totalPageCount = -1;
    private int currentPage = -1;

    private void init(Context context) {
    	this.context = context;
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
	public void addHeaderView(View v) {
		this.headerView = v;
		super.addHeaderView(v);
	}

	@Override
	public void addFooterView(View v) {
		this.footerView = v;

		super.addFooterView(v);
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
			LoadDataListView.this.visibleItemCount = visibleItemCount;
			if (headerView != null) {
				visibleLastIndex = firstVisibleItem + visibleItemCount - 2;
			}else
	            visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		}
	
		@Override  
	    public void onScrollStateChanged(AbsListView view, int scrollState) {  
	        int itemsLastIndex = currAdapter.getCount() - 1;    //数据集最后一项的索引 
	       
			int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& visibleLastIndex == lastIndex) {
				if (onScrollToEdgeCallBack != null && footerView != null) {
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
		if (totalPageCount > 0 && currentPage > 0
				&& currentPage >= totalPageCount) {
			/*
			 * 1.推荐/预约地区选择增加了一项“不限”导致adapter中的getCount返回多一条数据；
			 * 2.地区加载并未使用footview 
			 */
			if (footerView == null) {
				return;
			}
			
			TextView txtLoading = (TextView) footerView
					.findViewById(R.id.txt_loading);
			txtLoading.setText(context.getString(R.string.str_total_head)
					+ currAdapter.getCount()
					+ context.getString(R.string.str_total_tail));
			this.setOnScrollListener(null);
		}
	}	
	public void addOnScrollListener(){
		this.setOnScrollListener(new ScrollListener());
	}

	public void resetVar(){
		 totalPageCount = -1;
	     currentPage = -1;	     
	}
	
	public void resetFootView(){
		if (footerView == null) {
			return;
		}
		
		TextView txtLoading = (TextView) footerView
				.findViewById(R.id.txt_loading);
		txtLoading.setText(context.getString(R.string.str_loading));		
	}
}

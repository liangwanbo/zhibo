package com.mytv365.zb.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.mytv365.zb.R;


public class LoadMoreListView extends ListView implements OnScrollListener{
	private View footer;
	
	private int totalItem;
	private int lastItem;
	
	private boolean isLoading;
	
	private OnLoadMore onLoadMore;
	
	private LayoutInflater inflater;
	
	public LoadMoreListView(Context context) {
		super(context);
		init(context);
	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	@SuppressLint("InflateParams")
	private void init(Context context) {
		inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.load_more_footer,null ,false);
		footer.setVisibility(View.GONE);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.lastItem = firstVisibleItem+visibleItemCount;
		this.totalItem = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(this.totalItem == lastItem&&scrollState == SCROLL_STATE_IDLE){
			Log.v("isLoading", "yes");
			if(!isLoading){
				isLoading=true;
				footer.setVisibility(View.VISIBLE);
				onLoadMore.loadMore();


			}
		}
	}
	public void setLoadMoreListen(OnLoadMore onLoadMore){
		this.onLoadMore = onLoadMore;
	}
	/**
	 * 加载完成调用此方法
	 */
	public void onLoadComplete(){
		footer.setVisibility(View.GONE);
		isLoading = false;
	}
	
	public interface OnLoadMore{
		public void loadMore();
	}

}
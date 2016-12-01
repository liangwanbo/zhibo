package com.mytv365.zb.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class PullableWebView extends WebView implements Pullable {

	private boolean isRefresh = true;// 是否支持下拉
	private boolean isLoadMore = true;// 是否支持上拉

	public PullableWebView(Context context) {
		super(context);
	}

	public PullableWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (isRefresh)
			if (getScrollY() == 0)
				return true;
			else
				return false;
		else
			return false;
	}

	@Override
	public boolean canPullUp() {
		if (isLoadMore)
			if (getScrollY() >= getContentHeight() * getScale()
					- getMeasuredHeight())
				return true;
			else
				return false;
		else
			return false;
	}

	/**
	 * 返回是否支持下拉刷新
	 * 
	 * @return
	 */
	public boolean isRefresh() {
		return isRefresh;
	}

	/**
	 * 设置是否支持下拉刷新
	 * 
	 * @param isRefresh
	 */
	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	/**
	 * 返回是否支持上拉加载更多
	 * 
	 * @return
	 */
	public boolean isLoadMore() {
		return isLoadMore;
	}

	/**
	 * 设置是否支持上拉加载更多
	 * 
	 * @param isLoadMore
	 */
	public void setLoadMore(boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
	}

}

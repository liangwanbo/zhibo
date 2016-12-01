package com.mytv365.zb.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class PullableGridView extends GridView implements Pullable {

	/** 是否支持往下拉 */
	private boolean isRefresh = true;
	/** 是否支持往上拉 */
	private boolean isLoadMore = true;

	/** 是否往下拉 */
	public boolean isRefresh() {
		return isRefresh;
	}

	/**
	 * 设置是否支持往下拉
	 * 
	 * @param isRefresh
	 *            支持：true；不支持：false
	 */
	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	/** 是否往上拉 */
	public boolean isLoadMore() {
		return isLoadMore;
	}

	/**
	 * 设置是否支持往上拉
	 * 
	 * @param isRefresh
	 *            支持：true；不支持：false
	 */
	public void setLoadMore(boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
	}

	public PullableGridView(Context context) {
		super(context);
	}

	public PullableGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (isRefresh) {
			if (getCount() == 0) {
				// 没有item的时候也可以下拉刷新
				return true;
			} else if (getChildAt(0) != null) {
				if (getFirstVisiblePosition() == 0
						&& getChildAt(0).getTop() >= 0) {
					// 滑到顶部了
					return true;
				} else
					return false;
			} else
				return false;
		} else {
			return false;
		}
	}

	@Override
	public boolean canPullUp() {
		if (isLoadMore) {
			if (getCount() == 0) {
				// 没有item的时候不可以上拉加载
				return false;
			} else if (getLastVisiblePosition() == (getCount() - 1)) {
				// 滑到底部了
				if (getChildAt(getLastVisiblePosition()
						- getFirstVisiblePosition()) != null
						&& getChildAt(
								getLastVisiblePosition()
										- getFirstVisiblePosition())
								.getBottom() <= getMeasuredHeight())
					return true;
			}
			return false;
		} else {
			return false;
		}
	}

}

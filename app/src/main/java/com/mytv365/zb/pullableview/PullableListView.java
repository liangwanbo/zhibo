package com.mytv365.zb.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable {

	public boolean isRefresh = true; // 是否下拉
	public boolean isLoadMore = true; // 是否上拉

	public PullableListView(Context context) {
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle) {
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
					// 滑到ListView的顶部了
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

	public boolean isRefresh() {
		return isRefresh;
	}

	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	public boolean isLoadMore() {
		return isLoadMore;
	}

	public void setLoadMore(boolean isLoadMore) {
		this.isLoadMore = isLoadMore;
	}
}

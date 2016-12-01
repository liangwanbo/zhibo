package com.mytv365.zb.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable {

	private boolean isPull;// 是否支持刷新

	public PullableScrollView(Context context) {
		super(context);
		isPull = true;
	}

	public PullableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		isPull = true;
	}

	public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		isPull = true;
	}

	@Override
	public boolean canPullDown() {
		if (isPull) {
			if (getScrollY() == 0)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	@Override
	public boolean canPullUp() {
		// if (getScrollY() >= (getChildAt(0).getHeight() -
		// getMeasuredHeight()))
		// return true;
		// else
		return false;
	}

	/**
	 * 设置是否支持刷新
	 * 
	 * @param isPull
	 */
	public void setPull(boolean isPull) {
		this.isPull = isPull;
	}

}

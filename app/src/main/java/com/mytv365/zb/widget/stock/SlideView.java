package com.mytv365.zb.widget.stock;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class SlideView extends LinearLayout {

	private static final String TAG = "sj_test";

	private Context mContext;
	private LinearLayout mViewContent;
	private Scroller mScroller;
	private boolean isCanSlide = true;// 是否可以滑动的
	private OnSlideListener mOnSlideListener;
	private int mHolderWidth = 120;// 右部分的虚宽
	private int mLastX = 0;
	private int mLastY = 0;

	private static final int TAN = 2;

	public interface OnSlideListener {
		int SLIDE_STATUS_OFF = 0;
		int SLIDE_STATUS_START_SCROLL = 1;
		int SLIDE_STATUS_ON = 2;

		/**
		 * @param view
		 *            current SlideView
		 * @param status
		 *            SLIDE_STATUS_ON or SLIDE_STATUS_OFF
		 */
		public void onSlide(View view, int status);
	}

	public SlideView(Context context, int layoutId, int mainViewId) {
		super(context);
		addRightContentView(layoutId, mainViewId);
	}

	public SlideView(Context context, AttributeSet attrs, int layoutId,
			int mainViewId) {
		super(context, attrs);
		addRightContentView(layoutId, mainViewId);
	}

	private void addRightContentView(int drawableId, int mainViewId) {
		mContext = getContext();
		mScroller = new Scroller(mContext);
		setOrientation(LinearLayout.HORIZONTAL);
		View.inflate(mContext, drawableId, this);
		mViewContent = (LinearLayout) findViewById(mainViewId);
		mHolderWidth = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
						.getDisplayMetrics()));
	}

	public void setMainContentView(View view) {
		mViewContent.addView(view);
	}

	public void setOnSlideListener(OnSlideListener onSlideListener) {
		mOnSlideListener = onSlideListener;
	}

	public OnSlideListener getOnSlideListener() {
		return mOnSlideListener;
	}

	public void shrink() {
		if (getScrollX() != 0) {
			this.smoothScrollTo(0, 0);
		}
	}

	public void onRequireTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int scrollX = getScrollX();
		// Log.d(TAG, "x=" + x + "  y=" + y);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			if (mOnSlideListener != null) {
				mOnSlideListener.onSlide(this,
						OnSlideListener.SLIDE_STATUS_START_SCROLL);
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			if (isCanSlide) {
				int deltaX = x - mLastX;
				int deltaY = y - mLastY;
				if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
					break;
				}
				int newScrollX = scrollX - deltaX;
				if (deltaX != 0) {
					if (newScrollX < 0) {// --->
						// log("------------> newScrollX=" + newScrollX +
						// " deltaX="
						// + deltaX);
						newScrollX = 0;
					} else if (newScrollX > mHolderWidth) {// <---
						newScrollX = mHolderWidth;
						// log("<------------ newScrollX=" + newScrollX +
						// " deltaX="
						// + deltaX);
					}
					this.scrollTo(newScrollX, 0);
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP: {
			int newScrollX = 0;
			if (scrollX - mHolderWidth * 0.75 > 0) {
				newScrollX = mHolderWidth;
			}
			this.smoothScrollTo(newScrollX, 0);
			if (mOnSlideListener != null) {
				mOnSlideListener.onSlide(this,
						newScrollX == 0 ? OnSlideListener.SLIDE_STATUS_OFF
								: OnSlideListener.SLIDE_STATUS_ON);
			}
			break;
		}
		default:
			break;
		}
		mLastX = x;
		mLastY = y;
	}

	public void smoothScrollTo(int destX, int destY) {
		// 缓慢滚动到指定位置
		int scrollX = getScrollX();
		int delta = destX - scrollX;
		log("scrollX==" + scrollX + "--delta==" + delta);
		mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
		invalidate();
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	public int getmHolderWidth() {
		return mHolderWidth;
	}

	public boolean isCanSlide() {
		return isCanSlide;
	}

	public void setCanSlide(boolean isCanSlide) {
		this.isCanSlide = isCanSlide;
	}

	/**
	 * @Title: setmHolderWidth
	 * @Description: 设置右部分的宽度
	 * @param mHolderWidth
	 *            右部分的虚宽
	 * @param ll
	 * @param rightPartWidth
	 *            右部分的实宽
	 * @return void
	 * @throws
	 */
	public void setmHolderWidth(int mHolderWidth, LinearLayout ll) {
		this.mHolderWidth = mHolderWidth;
		LayoutParams lp = (LayoutParams) ll.getLayoutParams();
		lp.width = mHolderWidth;
	}

	void log(String msg) {
		Log.d(TAG, msg);
	}
}

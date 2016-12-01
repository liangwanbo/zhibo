package com.mytv365.zb.widget.stock;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.mytv365.zb.R;


public class SlideListView extends ListView {
	
	private static final String TAG = "sj_test";
	
	public SlideView mFocusedItemView;
	private int viewStatus = -1;
	private boolean isCanSlide = true;// 是否可以滑动的
	MyOnItemOnClickListener mMyOnItemOnClickListener;
	private int positionNotSlide = -23;// 指定某项不给滑动
	private TypedArray mTypedArray;
	private int mRightViewWidth;
	private int delayToRestate = 200;

	public MyOnItemOnClickListener getMyOnItemOnClickListener() {
		return mMyOnItemOnClickListener;
	}

	public void setMyOnItemOnClickListener(
			MyOnItemOnClickListener myOnItemOnClickListener) {
		this.mMyOnItemOnClickListener = myOnItemOnClickListener;
	}

	public SlideListView(Context context) {
		super(context);

	}

	public SlideListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.swipelistviewstyle);
		// 获取自定义属性和默认值
		mRightViewWidth = (int) mTypedArray.getDimension(
				R.styleable.swipelistviewstyle_right_width, delayToRestate);
		mTypedArray.recycle();
	}

	public SlideListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.swipelistviewstyle);
		// 获取自定义属性和默认值
		mRightViewWidth = (int) mTypedArray.getDimension(
				R.styleable.swipelistviewstyle_right_width, delayToRestate);
		mTypedArray.recycle();
	}

	public int getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(int viewStatus) {
		this.viewStatus = viewStatus;
	}

	public void shrinkListItem(int position) {
		View item = getChildAt(position);
		if (item != null) {
			try {
				((SlideView) item).shrink();
			} catch (ClassCastException e) {
				e.printStackTrace();
			}
		}
	}

	float x = 0, y = 0;
	float x1 = 0, y1 = 0;
	boolean isDown = false;
	private int position;
	public int scrollStatus = -1;// -1未触摸触摸 0垂直 1水平

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			isDown = true;
			x = event.getX();
			y = event.getY();
			x1 = event.getX();
			y1 = event.getY();
			position = pointToPosition((int) x, (int) y);
//			if (position == INVALID_POSITION
//					|| getViewStatus() == OnSlideListener.SLIDE_STATUS_ON) {
//				if (mFocusedItemView != null) {
//					mFocusedItemView.getOnSlideListener().onSlide(
//							mFocusedItemView, OnSlideListener.SLIDE_STATUS_OFF);
//					mFocusedItemView.smoothScrollTo(0, 0);
//				}
//				return false;
//			} else {
				SlidViewBean data = (SlidViewBean) getItemAtPosition(position);
				if (data != null) {
					mFocusedItemView = data.slideView;
					if (getPositionNotSlide() == position) {
						mFocusedItemView.setCanSlide(false);
					} else {
						mFocusedItemView.setCanSlide(isCanSlide);
					}
				}
			}
			break;
//		}
		case MotionEvent.ACTION_MOVE: {
			isDown = true;
			if (Math.abs(x1 - event.getX()) > (Math.abs(y1 - event.getY()))
					&& Math.abs(x1 - event.getX()) > 3 && scrollStatus != 0) {
				// log("--ACTION_MOVE---       ----------" + scrollStatus);
				x1 = event.getX();
				y1 = event.getY();
				scrollStatus = 1;
			} else if (Math.abs(y1 - event.getY()) > 10 && scrollStatus != 1) {
				// log("--ACTION_MOVE---       ||||||||||" + scrollStatus);
				x1 = event.getX();
				y1 = event.getY();
				scrollStatus = 0;
			}
			break;
		}
		case MotionEvent.ACTION_UP: {
			scrollStatus = -1;
			isDown = false;
			float distanceX = x - event.getX();
			float distanceY = y - event.getY();
			float distance = (float) Math.sqrt(distanceX * distanceX
					+ distanceY * distanceY);
			if (distance < 10 && mMyOnItemOnClickListener != null
					&& position != INVALID_POSITION) {
				mMyOnItemOnClickListener.onMyItemClick(mFocusedItemView,
						position);
			}
		}
			break;
		default:
			break;
		}
		if (mFocusedItemView != null && scrollStatus != 0) {
			mFocusedItemView.onRequireTouchEvent(event);
		}
		if (scrollStatus == 1) {
			return true;
		} else {
			return super.onTouchEvent(event);
		}
	}

	public interface MyOnItemOnClickListener {
		void onMyItemClick(View view, int position);
	}

	public boolean isCanSlide() {
		return isCanSlide;
	}

	public void setCanSlide(boolean isCanSlide) {
		this.isCanSlide = isCanSlide;
		if (mFocusedItemView != null) {
			mFocusedItemView.setCanSlide(isCanSlide);
		}
	}

	public int getPositionNotSlide() {
		return positionNotSlide;
	}

	public void setPositionNotSlide(int positionNotSlide) {
		this.positionNotSlide = positionNotSlide;
	}

	public int getRightViewWidth() {
		return mRightViewWidth;
	}

	public void setRightViewWidth(int mRightViewWidth) {
		this.mRightViewWidth = mRightViewWidth;
	}

	void log(String content) {
		Log.i(TAG, content);
	}

	public static class SlidViewBean {
		public SlideView slideView;
	}
}


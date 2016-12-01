package com.fhrj.library.view.advertising.one;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/***
 * 自定义滑动
 * 
 * @author ZhangGuoHao
 * @date 2016年4月11日 下午6:39:05
 */
public class MyViewPager extends android.support.v4.view.ViewPager {

	/***标签页集合*/
	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private List<View> mViewList = new ArrayList<View>();
	/*** 是否滑动*/
	private boolean canSlideable = false;

	public MyViewPager(Context context) {
		super(context);
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		int height = 0;
//		 //下面遍历所有child的高度
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec,
//					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//			int h = child.getMeasuredHeight();
//			if (h > height)
//				height = h;
//		}
//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//				MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	/**
	 * 初始化PagerAdapter适配器，与addViewItem结合使用
	 */
	public void initPagerAdapter() {
		this.setAdapter(new MyPagerAdapter());
	}

	/**
	 * 初始化FragmentPagerAdapter适配器，与addFragmentItem结合，适合标签页数量少的情况使用
	 */
	public void initFragmentPagerAdapter(FragmentManager fm) {
		this.setAdapter(new MyFragmentPagerAdapter(fm));
	}

	/**
	 * 初始化FragmentPagerAdapter适配器，与addFragmentItem结合，适合标签页数量多的情况使用
	 */
	public void initFragmentStatePagerAdapter(FragmentManager fm) {
		this.setAdapter(new MyFragmentStatePagerAdapter(fm));
	}

	/**
	 * 添加Fragment标签页
	 * 
	 * @param item
	 *            Fragment标签页
	 */
	public void addFragmentItem(Fragment item) {
		if (!mFragmentList.contains(item)) {
			mFragmentList.add(item);
		}
	}

	/**
	 * 添加View标签页
	 * 
	 * @param item
	 *            View标签页
	 */
	public void addViewItem(View item) {
		if (!mViewList.contains(item)) {
			mViewList.add(item);
		}
	}

	/**
	 * 获取标签页Fragment
	 * 
	 * @param index
	 *            索引
	 * @return 标签页Fragment
	 */
	public Fragment getFragmentItem(int index) {
		return mFragmentList.get(index);
	}

	/**
	 * 获取标签页View
	 * 
	 * @param index
	 *            索引
	 * @return 标签页View
	 */
	public View getViewItem(int index) {
		return mViewList.get(index);
	}

	/**
	 * 设置是否可滑动
	 * 
	 * @param canSlideable
	 *            是否可滑动
	 */
	public void setCanSlideable(boolean canSlideable) {
		this.canSlideable = canSlideable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (!canSlideable)
			return false;
		else
			return super.onTouchEvent(arg0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (!canSlideable)
			return false;
		else
			return super.onInterceptTouchEvent(arg0);
	}

	
	/**
	 * 说明： Fragment对象会一直存留在内存中<br>
	 * onCreate()只会被调用一次， onCreateView()每次Fragment从不可见到可见时会被调用<br>
	 * 不适合Fragment数量比较多的场合使用<br>
	 */
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			return mFragmentList.get(index);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		 @Override
		 public Object instantiateItem(ViewGroup container, int position) {
		 return super.instantiateItem(container, position);
		 }

		 @Override
		 public void destroyItem(ViewGroup container, int position, Object
		 object) {
		 //这里Destroy的是Fragment的视图层次，并不是Destroy Fragment对象
		 super.destroyItem(container, position, object);
		 }
	}

	/**
	 * 说明：内存中值保留前、中、后三个Fragment对象，其他对象会被回收，适合Fragment数量比较多的场合
	 */
	public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

		public MyFragmentStatePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			return mFragmentList.get(index);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		 @Override
		 public Object instantiateItem(ViewGroup container, int position) {
		 return super.instantiateItem(container, position);
		 }

		 @Override
		 public void destroyItem(ViewGroup container, int position, Object
		 object) {
		 //这里Destroy的是Fragment的视图层次，并不是Destroy Fragment对象
		 super.destroyItem(container, position, object);
		 }
	}

	/**
	 * 可以添加任何View对象
	 */
	public class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mViewList.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {

			// 初始化标签页
			((MyViewPager) container).addView(mViewList.get(position), 0);

			return mViewList.get(position);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// 移除标签页
			((MyViewPager) container).removeView(mViewList.get(position));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// 用于判断是否需要添加标签页
			return arg0 == arg1;
		}
	}
	


}

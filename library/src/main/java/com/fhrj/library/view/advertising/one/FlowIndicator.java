package com.fhrj.library.view.advertising.one;



/***
 * 切换事件
 * @author ZhangGuoHao
 * @date 2016年5月15日 上午11:51:26
 */
public interface FlowIndicator extends ViewFlow.ViewSwitchListener {

	/**
	 * Set the current ViewFlow. This method is called by the ViewFlow when the
	 * FlowIndicator is attached to it.
	 * 
	 * @param view
	 */
	void setViewFlow(ViewFlow view);

	/**
	 * The scroll position has been changed. A FlowIndicator may implement this
	 * method to reflect the current position
	 * 
	 * @param h
	 * @param v
	 * @param oldh
	 * @param oldv
	 */
	void onScrolled(int h, int v, int oldh, int oldv);
}

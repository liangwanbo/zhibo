package com.fhrj.library.view.sideslip.app;

import com.fhrj.library.view.sideslip.SlidingMenu;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

public interface SlidingActivityBase {

	/**
	 * 将后面的视图内容设置为一个显式视图。这种视图直接放在后面视图的视图层次结构中。 它本身可以是一个复杂的视图层次结构。
	 * 
	 * @param view
	 *            显示所需的内容
	 * @param layoutParams
	 *            视图的布局参数
	 */
	void setBehindContentView(View view, LayoutParams layoutParams);

	/**
	 * 将后面的视图内容设置为一个显式视图。这种视图直接放在后面视图的视图层次结构中。 它本身可以是一个复杂的视图层次结构。调用此方法时，指定的布局参数
	 * 视图被忽略。的宽度和高度的视图的默认设置为match_parent。使用你的
	 * 自己的布局参数，调用setContentView（android.view.view，Android。观。ViewGroup
	 * LayoutParams代替。）。
	 *
	 * @param view
	 *            显示所需的内容
	 */
	void setBehindContentView(View view);

	/**
	 * 设置布局资源的后面视图内容。该资源将是 虚增，将所有顶层视图添加到后面视图。
	 *
	 * @param layoutResID
	 *            资源ID
	 */
	void setBehindContentView(int layoutResID);

	/**
	 * 获取与此活动相关的slidingmenu
	 *
	 * @return 与此活动相关的slidingmenu
	 */
	SlidingMenu getSlidingMenu();

	/**
	 * 切换slidingmenu 如果它是打开的，它将被关闭，反之亦然。
	 */
	void toggle();

	/**
	 * 关闭slidingmenu显示内容视图。
	 */
	void showContent();

	/**
	 * 打开slidingmenu和显示菜单视图
	 */
	void showMenu();

	/**
	 * 打开slidingmenu且表现出二次（右）菜单视图。将 如果只有一个菜单的默认菜单。
	 */
	void showSecondaryMenu();

	/**
	 * 控制是否ActionBar连同上面查看幻灯片的时候 菜单打开，或是否停留在位置。
	 *
	 * @param slidingActionBarEnabled
	 *            如果你想要的ActionBar滑随着 slidingmenu，假如果你想ActionBar停留的地方
	 */
	void setSlidingActionBarEnabled(boolean slidingActionBarEnabled);

}

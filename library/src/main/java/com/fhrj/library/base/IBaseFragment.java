package com.fhrj.library.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Fragment接口
 * @author 曾繁添
 * @version 1.0
 *
 */
public interface IBaseFragment extends IBaseConstant {

	/**
	 * 绑定渲染视图的布局文件
	 * @return 布局文件资源id
	 */
	int bindLayout();
	
	/**
	 * 绑定渲染View
	 * @return
	 */
	View bindView();
	
	/**
	 * 初始化界面参数
	 * @param parms
	 */
	void initParams(Bundle params);
	
	/**
	 * 初始化控件
	 */
	void initView(final View view);
	
	/**
	 * 业务处理操作（onCreateView方法中调用）
	 * @param mContext  当前Activity对象
	 */
	void doBusiness(Context mContext);
	
}

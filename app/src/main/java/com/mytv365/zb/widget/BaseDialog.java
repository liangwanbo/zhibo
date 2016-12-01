package com.mytv365.zb.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public abstract class BaseDialog extends Dialog {

	/** 布局id */
	private int reId;

	/** 数据相关 */
	private int width;// dialog宽度
	private int height;// dialog高度

	public BaseDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.height = -1;
		this.width = -1;
	}

	protected BaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		this.height = -1;
		this.width = -1;
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.height = -1;
		this.width = -1;
	}

	public BaseDialog(Context context, int theme, int width, int height) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.reId = init();
		View view = LayoutInflater.from(getContext()).inflate(reId, null);
		setContentView(view);
		initView(view);
		setViewData();
		initEvent();
	}

	/**
	 * 初始化
	 * 
	 * @return 布局
	 */
	protected abstract int init();

	/** 初始化控件 */
	protected abstract void initView(View view);

	/** 初始化控件属性 */
	protected abstract void setViewData();

	/** 初始化控件事件 */
	protected abstract void initEvent();

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		if (width != -1 || height != -1) {
			WindowManager.LayoutParams wlp = getWindow().getAttributes();
			if (width != -1)
				wlp.width = width;
			if (height != -1)
				wlp.height = height;
			getWindow().setAttributes(wlp);
		}
	}

}

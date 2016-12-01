package com.mytv365.zb.widget.webview;

import android.content.Context;
import android.view.View;

import com.mytv365.zb.R;
import com.mytv365.zb.widget.BaseDialog;


public class MyProgressDialog extends BaseDialog {

	public MyProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	protected MyProgressDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public MyProgressDialog(Context context, int theme, int width, int height) {
		super(context, theme, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int init() {
		// TODO Auto-generated method stub
		return R.layout.layout_dialog;
	}

	@Override
	protected void initView(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub

	}

}

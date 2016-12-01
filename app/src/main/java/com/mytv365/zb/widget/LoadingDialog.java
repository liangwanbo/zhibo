package com.mytv365.zb.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.mytv365.zb.R;


/**
 * 加载中Dialog
 * 
 * @author lexyhp
 */
public class LoadingDialog extends AlertDialog {

	private TextView tips_loading_msg;
	private int layoutResId;
	private String message = null;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            上下文
	 * @param layoutResId
	 *            要传入的dialog布局文件的id
	 */
	public LoadingDialog(Context context) {
		super(context, R.style.Dialog_Fullscreen);
		message = context.getResources().getString(R.string.loading);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_tips_loading);
		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
		tips_loading_msg.setText(this.message);
	}

}

package com.mytv365.zb.common;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
/***
 * 获取焦点事件
 * @author: 张国浩
 * @date:   2016年6月30日 下午7:17:11
 */
public class MyOnFocusChange implements OnFocusChangeListener{
	private EditText editText;
	public MyOnFocusChange(EditText editText){
		this.editText=editText;
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			editText.setCursorVisible(true);
		} else {
			editText.setCursorVisible(false);
		}
	}
	
}

package com.fhrj.library.view;
import com.fhrj.library.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/***
 * 自定义CheckBox,增加属性key/value
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:20:45
 */
public class CheckBox extends android.widget.CheckBox {

	private String mKey;
	private String mValue;

	public CheckBox(Context context) {
		this(context,null);
	}
	
	public CheckBox(Context context, AttributeSet attrs) {
		this(context, attrs,android.R.attr.checkboxStyle);
	}
	
	public CheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		// 获取自定义属性和默认值
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.TextView);
		mKey = mTypedArray.getString(R.styleable.TextView_key);
		mValue = mTypedArray.getString(R.styleable.TextView_value);
		mTypedArray.recycle();
	}
	
	public String getKey(){
		return mKey;
	}
	
	public void setKey(String key){
		this.mKey = key;
	}
	
	public String getValue() {
		return mValue;
	}

	public void setValue(String value) {
		this.mValue = value;
	}

}

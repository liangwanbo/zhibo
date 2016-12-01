package com.fhrj.library.view.radio;

import com.fhrj.library.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/***
 * 自定义RadioButton,增加属性key/value
 * 
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:21:22
 */
public class RadioButton extends android.widget.RadioButton {

	private String mKey;
	private String mValue;
	/***
	 * xml文件中设置的大小
	 */
	private Drawable drawableTop, drawableBottom, drawableLeft, drawableRight;
	private int mTopWith, mTopHeight, mBottomWith, mBottomHeight, mRightWith,
			mRightHeight, mLeftWith, mLeftHeight;

	public RadioButton(Context context) {
		this(context, null);
	}

	public RadioButton(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.radioButtonStyle);
	}

	public RadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// 获取自定义属性和默认值
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.TextView);
		mKey = mTypedArray.getString(R.styleable.TextView_key);
		mValue = mTypedArray.getString(R.styleable.TextView_value);
		mTypedArray.recycle();
		if (attrs != null) {
			float scale = context.getResources().getDisplayMetrics().density;
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.PengRadioButton);
			int count = a.getIndexCount();
			for (int i = 0; i < count; i++) {
				int attr = a.getIndex(i);
//				switch (attr) {
//				case R.styleable.PengRadioButton_peng_drawableBottom:
//					drawableBottom = a.getDrawable(attr);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableTop:
//					drawableTop = a.getDrawable(attr);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableLeft:
//					drawableLeft = a.getDrawable(attr);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableRight:
//					drawableRight = a.getDrawable(attr);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableTopWith:
//					mTopWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableTopHeight:
//					mTopHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableBottomWith:
//					mBottomWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableBottomHeight:
//					mBottomHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableRightWith:
//					mRightWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableRightHeight:
//					mRightHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableLeftWith:
//					mLeftWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				case R.styleable.PengRadioButton_peng_drawableLeftHeight:
//					mLeftHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);
//					break;
//				default:
//					break;
//				}
			}
			a.recycle();
			setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop,
					drawableRight, drawableBottom);
		}
	}

	// 设置Drawable定义的大小
	@Override
	public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
			Drawable top, Drawable right, Drawable bottom) {

		if (left != null) {
			left.setBounds(0, 0, mLeftWith <= 0 ? left.getIntrinsicWidth()
					: mLeftWith, mLeftHeight <= 0 ? left.getMinimumHeight()
					: mLeftHeight);
		}
		if (right != null) {
			right.setBounds(0, 0, mRightWith <= 0 ? right.getIntrinsicWidth()
					: mRightWith, mRightHeight <= 0 ? right.getMinimumHeight()
					: mRightHeight);
		}
		if (top != null) {
			top.setBounds(0, 0, mTopWith <= 0 ? top.getIntrinsicWidth()
					: mTopWith, mTopHeight <= 0 ? top.getMinimumHeight()
					: mTopHeight);
		}
		if (bottom != null) {
			bottom.setBounds(
					0,
					0,
					mBottomWith <= 0 ? bottom.getIntrinsicWidth() : mBottomWith,
					mBottomHeight <= 0 ? bottom.getMinimumHeight()
							: mBottomHeight);
		}
		setCompoundDrawables(left, top, right, bottom);
	}

	public String getKey() {
		return mKey;
	}

	public void setKey(String key) {
		this.mKey = key;
	}

	public String getValue() {
		return mValue;
	}

	public void setValue(String value) {
		this.mValue = value;
	}
}

package com.mytv365.zb.widget.stock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * K线、分时基类，主要功能：绘制网格线背景
 * 
 * @author 锐
 * 
 */
public class GridChartView extends View {

	/** 当前控件高度 */
	private float viewHight;
	/** 当前控件宽度 */
	private float viewWidth;
	/** 边框宽度 */
	private float strokeWidth;
	/** 默认边框宽度 */
	private final float DEFAULT_STROKEWIDTH = 2;
	/** 边框颜色 */
	private int strokeColor;
	/** 默认边框颜色 */
	private final int DEFAULT_STROKECOLOR = Color.GRAY;

	/** 是否显示边框（左边框、右边框、顶部边框、底部边框） */
	private boolean isLeftVisible;// 左
	private boolean isRightVisible;// 右
	private boolean isTopVisible;// 顶部
	private boolean isBottomVisible;// 底部

	/** 边框边距 */
	private float strokeLeft;// 左边距
	private float strokeRight;// 右边距
	private float strokeTop;// 顶边距
	private float strokeBottom;// 底边距
	private final float DEFAULT_MARGIN = 1;// 默认边距

	public GridChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GridChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public GridChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		strokeWidth = DEFAULT_STROKEWIDTH;
		strokeColor = DEFAULT_STROKECOLOR;
		isLeftVisible = true;
		isRightVisible = true;
		isTopVisible = true;
		isBottomVisible = true;
		strokeLeft = DEFAULT_MARGIN;
		strokeRight = DEFAULT_MARGIN;
		strokeTop = DEFAULT_MARGIN;
		strokeBottom = DEFAULT_MARGIN;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		viewHight = getHeight();
		viewWidth = getWidth();

		// 绘制边框
		drawFrame(canvas);

	}

	/**
	 * 绘制边框
	 * 
	 * @param canvas
	 *            画布
	 */
	private void drawFrame(Canvas canvas) {
		Paint paint = new Paint();
		paint.setStrokeWidth(strokeWidth);
		paint.setColor(strokeColor);
		paint.setAlpha(128);
		paint.setAntiAlias(true);

		if (isLeftVisible)
			canvas.drawLine(strokeLeft + strokeWidth / 2, strokeTop, strokeLeft
					+ strokeWidth / 2, viewHight - strokeBottom, paint);// 绘制左边竖线
		if (isRightVisible)
			canvas.drawLine(viewWidth - strokeRight - strokeWidth / 2,
					strokeTop, viewWidth - strokeRight - strokeWidth / 2,
					viewHight - strokeBottom, paint);// 绘制右边竖线
		if (isTopVisible)
			canvas.drawLine(strokeLeft, strokeTop + strokeWidth / 2, viewWidth
					- strokeRight, strokeTop + strokeWidth / 2, paint);// 绘制顶部横线
		if (isBottomVisible)
			canvas.drawLine(strokeLeft, viewHight - strokeBottom - strokeWidth
					/ 2, viewWidth - strokeRight, viewHight - strokeBottom
					- strokeWidth / 2, paint);// 绘制底部横线
	}

	/**
	 * 设置边框宽度
	 * 
	 * @param strokeWidth
	 */
	public GridChartView setStrokeWidth(float strokeWidth) {
		if (strokeWidth < 1) {
			strokeWidth = DEFAULT_STROKEWIDTH;
		}
		this.strokeWidth = strokeWidth;
		return this;
	}

	/**
	 * 获取边框宽度
	 * 
	 * @return strokeWidth
	 */
	public float getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * 设置边框颜色
	 * 
	 * @param strokeColor
	 */
	public GridChartView setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
		return this;
	}

	/**
	 * 获取边框颜色
	 * 
	 * @return strokeColor
	 */
	public int getStrokeColor() {
		return strokeColor;
	}

	/**
	 * 设置左边框是否显示
	 * 
	 * @param isVisible
	 *            true显示，false不显示
	 * @return
	 */
	public GridChartView setStrokeLeftVisibility(boolean isVisible) {
		this.isLeftVisible = isVisible;
		return this;
	}

	/**
	 * 设置右边框是否显示
	 * 
	 * @param isVisible
	 *            true显示，false不显示
	 * @return
	 */
	public GridChartView setStrokeRightVisibility(boolean isVisible) {
		this.isRightVisible = isVisible;
		return this;
	}

	/**
	 * 设置顶部边框是否显示
	 * 
	 * @param isVisible
	 *            true显示，false不显示
	 * @return
	 */
	public GridChartView setStrokeTopVisibility(boolean isVisible) {
		this.isTopVisible = isVisible;
		return this;
	}

	/**
	 * 设置底部边框是否显示
	 * 
	 * @param isVisible
	 *            true显示，false不显示
	 * @return
	 */
	public GridChartView setStrokeBottomVisibility(boolean isVisible) {
		this.isBottomVisible = isVisible;
		return this;
	}

	/**
	 * 设置边框左边距
	 * 
	 * @param margin
	 * @return
	 */
	public GridChartView setStrokeLeft(float margin) {
		if (margin < 1) {
			margin = DEFAULT_MARGIN;
		}
		this.strokeLeft = margin;
		return this;
	}

	/**
	 * 获取边框左边距
	 * 
	 * @return
	 */
	public float getStrokeLeft() {
		return strokeLeft;
	}

	/**
	 * 设置边框右边距
	 * 
	 * @param margin
	 * @return
	 */
	public GridChartView setStrokeRight(float margin) {
		if (margin < 1) {
			margin = DEFAULT_MARGIN;
		}
		this.strokeRight = margin;
		return this;
	}

	/**
	 * 获取边框右边距
	 * 
	 * @return
	 */
	public float getStrokeRight() {
		return strokeRight;
	}

	/**
	 * 设置边框顶边距
	 * 
	 * @param margin
	 * @return
	 */
	public GridChartView setStrokeTop(float margin) {
		if (margin < 1) {
			margin = DEFAULT_MARGIN;
		}
		this.strokeTop = margin;
		return this;
	}

	/**
	 * 获取边框顶边距
	 * 
	 * @return
	 */
	public float getStrokeTop() {
		return strokeTop;
	}

	/**
	 * 设置边框底边距
	 * 
	 * @param margin
	 * @return
	 */
	public GridChartView setStrokeBottom(float margin) {
		if (margin < 1) {
			margin = DEFAULT_MARGIN;
		}
		this.strokeBottom = margin;
		return this;
	}

	/**
	 * 获取边框底边距
	 * 
	 * @return
	 */
	public float getStrokeBottom() {
		return strokeBottom;
	}

}

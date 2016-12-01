package com.mytv365.zb.widget.stock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mytv365.zb.R;
import com.mytv365.zb.utils.Parse;

import java.util.ArrayList;
import java.util.List;


public class TimeChartView extends GridChartView {

	/** 分时数据 */
	private List<OHLCEntity> timesList;
	/** 成交量数据 */
	private List<StickEntity> stickData;
	/** 昨收价 */
	private float preclose;

	/** 记录列表中设置到上表中的数据的最大值 */
	private float upMaxData;
	/** 记录列表中设置到上表中的数据的最小值 */
	private float upMinData;
	/** 记录列表中设置到下表中的数据最大值 */
	private float downMaxData;
	/** 记录列表中设置到下表中的数据最小值 */
	private float downMinData;
	/** 上表与下表中间的日期时间文字的默认大小 */
	protected final float DEFAULT_DATETEXTSIZE = 25;
	/** 上表与下表中间的日期时间文字大小 */
	protected float dateTextSize = DEFAULT_DATETEXTSIZE;
	/** 上表与下表间距 */
	protected float spacing;

	/** 默认经线条数 */
	private final int DEFAULT_LONGITUDE_NUM = 3;
	/** 默认上表纬线条数 */
	private final int DEFAULT_LATITUDE_UP_NUM = 4;
	/** 默认下表纬线条数 */
	private final int DEFAULT_LATITUDE_DOWN_NUM = 2;
	/** 默认经线颜色 */
	private final int DEFAULT_LONGITUDE_COLOR = Color.GRAY;
	/** 默认纬线颜色 */
	private final int DEFAULT_LATITUDE_COLOR = Color.GRAY;
	/** 默认经、纬线宽 */
	private final float DEFAULT_LATITUDE_LONGITUDE_WIDTH = 2;

	/** 经线间距 */
	protected float longitudesSpacing;
	/** 纬线间距 */
	protected float latitudesSpacing;
	/** 经线条数 */
	private int longitudesNum = DEFAULT_LONGITUDE_NUM;
	/** 上表纬线条数 */
	protected int latitudesUpNum = DEFAULT_LATITUDE_UP_NUM;
	/** 下表纬线条数 */
	protected int latitudesDownNum = DEFAULT_LATITUDE_DOWN_NUM;
	/** 经线颜色 */
	private int longitudesColor = DEFAULT_LONGITUDE_COLOR;
	/** 纬线颜色 */
	private int latitudesColor = DEFAULT_LATITUDE_COLOR;
	/** 经、纬线宽 */
	private float latLongWidth = DEFAULT_LATITUDE_LONGITUDE_WIDTH;
	/** 是否绘制经线 */
	private boolean isDrawLongitudes;
	/** 是否绘制纬线 */
	private boolean isDrawLatitudes;

	/** 上表底部 */
	protected float upChartBottom;
	/** 下表底部 */
	protected float downChartBottom;
	/** 上表高度 */
	protected float upChartHeight;
	/** 下表高度 */
	protected float downChartHeight;

	/** 分时默认最大时间 */
	private final int MAX_DATE = 60 * 4;
	/** 分时最大时间（默认4个小时） */
	private int maxDate = MAX_DATE + 1;

	/** 各个数据点的间距 */
	private float dataSpacing;

	/** 上表的尺寸与数据比例 */
	private float upRatio;
	/** 下表的尺寸与数据比例 */
	private float downRatio;
	/** 下表柱形图宽 */
	private float columnWidth;

	/** 是否显示十字光标 */
	private boolean isShowCross;
	/** 十字光标X坐标 */
	private float crossX;
	/** 十字光标Y坐标 */
	private float crossY;

	/** 长按事件的Runnable */
	private Runnable mRunnable;
	/** 手指是否离开屏幕 */
	private boolean isReleased;
	/** 手指是否移动了 */
	private boolean isMoved;
	/** 是否可以执行OnTouch中的Move事件了 */
	private boolean isStartMoved;
	/** 按下时的X坐标 */
	private float touchDownX;
	/** 按下时的Y坐标 */
	private float touchDownY;
	/** 按下时的X坐标 */
	private float downX;
	/** 按下时的Y坐标 */
	private float downY;

	/** 设置是否自动计算左边距 */
	private boolean isAutoLeftPadding;

	/** 接口 */
	private OnTimeListener l;

	/** 左边要显示当前十字光标中心点的数据 */
	private float leftData;

	public TimeChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public TimeChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public TimeChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		upMaxData = 0;
		downMaxData = 0;
		isShowCross = false;
		spacing = dateTextSize + 5;// 比文字高度高5个像素
		isDrawLongitudes = true;
		isDrawLatitudes = true;
		isAutoLeftPadding = false;
		mRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isMoved || isReleased)
					return;

				if (timesList != null && timesList.size() > 0) {
					getParent().requestDisallowInterceptTouchEvent(true);
					for (int i = 0; i < timesList.size() - 1 && i < maxDate - 1; i++) {
						float Y = (float) (upChartBottom - (timesList.get(i)
								.getClose() - upMinData) * upRatio);
						float endY = (float) (upChartBottom - (timesList.get(
								i + 1).getClose() - upMinData)
								* upRatio);
						float X = getStrokeWidth() + getStrokeLeft() + 1f
								+ dataSpacing * i;
						float endX = getStrokeWidth() + getStrokeLeft() + 1f
								+ dataSpacing * (i + 1);
						float spacing = endX - X;
						float positionX = touchDownX - X;
						if (touchDownX >= X && touchDownX < endX) {
							isShowCross = true;
							if (positionX <= spacing / 2) {
								crossX = X;
								crossY = Y;
								leftData = (float) timesList.get(i).getClose();
								if (l != null) {
									l.listener(timesList.get(i),
											stickData.get(i));
									l.isShowDetails(isShowCross);
								}
							} else {
								crossX = endX;
								crossY = endY;
								leftData = (float) timesList.get(i + 1)
										.getClose();
								isShowCross = true;
								if (l != null) {
									l.listener(timesList.get(i + 1),
											stickData.get(i + 1));
									l.isShowDetails(isShowCross);
								}
							}
							postInvalidate();
							break;
						} else if (touchDownX >= endX
								&& (i + 1 == timesList.size() - 1 || i + 1 == maxDate - 1)) {
							crossX = endX;
							crossY = endY;
							leftData = (float) timesList.get(i + 1).getClose();
							isShowCross = true;
							if (l != null) {
								l.listener(timesList.get(i + 1),
										stickData.get(i + 1));
								l.isShowDetails(isShowCross);
							}
							postInvalidate();
							break;
						}
					}
				}
				isStartMoved = true;
			}
		};
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		if ((timesList == null || timesList.size() < 1)
				|| (stickData == null || stickData.size() < 1))
			return;
		float viewHight = getHeight();
		float viewWidth = getWidth();
		// 文字画笔
		TextPaint tp = new TextPaint();
		tp.setTextSize(dateTextSize);
		tp.setTypeface(Typeface.DEFAULT_BOLD);
		int upLength = Parse.getInstance().parse2String(upMaxData).length();
		int downLength = Parse.getInstance().parse2CNString(downMaxData)
				.length();
		int defaultLength = "0000.00".length();
		float dataWidth = 0;
		if (upLength > downLength) {
			if (defaultLength > upLength) {
				dataWidth = tp.measureText("0000.00");
			} else {
				dataWidth = tp.measureText(Parse.getInstance().parse2String(
						upMaxData));
			}
		} else {
			if (defaultLength > downLength) {
				dataWidth = tp.measureText("0000.00");
			} else {
				dataWidth = tp.measureText(Parse.getInstance().parse2CNString(
						downMaxData));
			}
		}
		if (isAutoLeftPadding)
			setStrokeLeft(getStrokeWidth() + dataWidth + 6);
		// setStrokeLeft(getStrokeWidth()
		// + (upDataWidth > downDataWidth ? upDataWidth + 6
		// : downDataWidth + 6));
		if (spacing == 0.0f)
			setStrokeBottom(dateTextSize);
		super.onDraw(canvas);
		latitudesSpacing = (viewHight - getStrokeWidth() * 2 - spacing
				- getStrokeTop() - getStrokeBottom())
				/ (latitudesUpNum + latitudesDownNum);
		longitudesSpacing = (viewWidth - getStrokeWidth() * 2 - getStrokeLeft()
				- getStrokeRight() - 2.0f)
				/ (longitudesNum + 1);

		upChartHeight = latitudesSpacing * latitudesUpNum;
		upChartBottom = latitudesSpacing * latitudesUpNum + getStrokeTop()
				+ getStrokeWidth();

		downChartHeight = latitudesSpacing * latitudesDownNum;
		downChartBottom = viewHight - getStrokeBottom() - getStrokeWidth();
		columnWidth = (getWidth() - getStrokeWidth() * 2 - getStrokeLeft() - getStrokeRight())
				/ maxDate;
		if (columnWidth > 2.0f) {
			columnWidth = 2.0f;
		}
		dataSpacing = (getWidth() - getStrokeWidth() * 2 - getStrokeLeft()
				- getStrokeRight() - 2.0f)
				/ (maxDate - 1);

		if (upMaxData >= preclose && upMinData <= preclose) {
			if (upMaxData - preclose >= preclose - upMinData) {
				upMinData = preclose - (upMaxData - preclose);
			} else {
				upMaxData = preclose + (preclose - upMinData);
			}
		} else if (upMaxData >= preclose && upMinData >= preclose) {
			upMinData = preclose - (upMaxData - preclose);
		} else {
			upMaxData = preclose + (preclose - upMinData);
		}

		upRatio = upChartHeight / (upMaxData - upMinData);

		downRatio = downChartHeight / (downMaxData - downMinData);

		// 画笔
		Paint paint = new Paint();

		if (isDrawLatitudes)
			// 绘制纬线
			drawLatitudes(canvas, paint);

		if (isDrawLongitudes)
			// 绘制经线
			drawLongitudes(canvas, paint);

		// 绘制上表折线
		drawUpLine(canvas, paint);

		// 绘制上表Y坐标上的X数据
		drawUpAxisXTitle(canvas, tp, dataWidth);

		// 绘制下表柱形图
		drawDownColumnChart(canvas, paint);

		// 绘制下表刻度
		drawDownAxisXTitle(canvas, tp, dataWidth);

		// 绘制时间
		drawTime(canvas, tp);

		// 绘制十字线
		drawCrossLine(canvas, paint);
	}

	/**
	 * 绘制纬线
	 * 
	 * @param canvas
	 *            画布
	 * @param paint
	 */
	private void drawLatitudes(Canvas canvas, Paint paint) {
		paint.setColor(latitudesColor);
		paint.setStrokeWidth(latLongWidth);
		paint.setAlpha(110);
		paint.setAntiAlias(true);
		for (int i = 1; i <= latitudesUpNum; i++) {
			canvas.drawLine(getStrokeLeft(), latitudesSpacing * i
					+ getStrokeWidth() + getStrokeTop(), getWidth()
					- getStrokeRight(), latitudesSpacing * i + getStrokeWidth()
					+ getStrokeTop(), paint);
		}
		for (int i = 1; i <= latitudesDownNum; i++) {
			canvas.drawLine(getStrokeLeft(),
					(getHeight() - getStrokeWidth() - getStrokeBottom())
							- latitudesSpacing * i, getWidth()
							- getStrokeRight(),
					(getHeight() - getStrokeWidth() - getStrokeBottom())
							- latitudesSpacing * i, paint);
		}
	}

	/**
	 * 绘制经线
	 * 
	 * @param canvas
	 *            画布
	 * @param paint
	 */
	private void drawLongitudes(Canvas canvas, Paint paint) {
		paint.setColor(longitudesColor);
		paint.setStrokeWidth(latLongWidth);
		paint.setAlpha(110);
		paint.setAntiAlias(true);
		for (int i = 1; i <= longitudesNum; i++) {
			canvas.drawLine(longitudesSpacing * i + getStrokeWidth()
					+ getStrokeLeft(), getStrokeWidth() + getStrokeTop(),
					longitudesSpacing * i + getStrokeWidth() + getStrokeLeft(),
					latitudesSpacing * latitudesUpNum + getStrokeWidth()
							+ getStrokeTop(), paint);
			canvas.drawLine(longitudesSpacing * i + getStrokeWidth()
					+ getStrokeLeft(), getHeight() - getStrokeWidth()
					- getStrokeBottom(), longitudesSpacing * i
					+ getStrokeWidth() + getStrokeLeft(), (getHeight()
					- getStrokeWidth() - getStrokeBottom())
					- latitudesSpacing * latitudesDownNum, paint);
		}
	}

	/**
	 * 绘制上表中的折线
	 * 
	 * @param canvas
	 */
	private void drawUpLine(Canvas canvas, Paint paint) {
		if (timesList == null)
			return;
		paint.setAlpha(180);
		paint.setStrokeWidth(2);
		paint.setAntiAlias(true);
		float startWhiteX = getStrokeWidth() + getStrokeLeft() + 1f;
		float startWhiteY = (float) (upChartBottom - (timesList.get(0)
				.getClose() - upMinData) * upRatio);
		Path path = new Path();
		for (int i = 1; i < timesList.size() && i < maxDate; i++) {
			// float startYellowX = getStrokeWidth() + getStrokeLeft() + 1f;
			// float startYellowY = upChartBottom
			// - latitudesSpacing
			// - (Float.parseFloat(map.get("daPanJiaQuan").toString()) -
			// upMinData)
			// * upRatio;
			float endWhiteY = (float) (upChartBottom - (timesList.get(i)
					.getClose() - upMinData) * upRatio);
			float endWhiteX = getStrokeWidth() + getStrokeLeft() + 1f
					+ dataSpacing * i;
			// float endYellowY = upChartBottom
			// - latitudesSpacing
			// - (Float.parseFloat(timesList.get(i).get("daPanJiaQuan")
			// .toString()) - upMinData) * upRatio;
			// float endYellowX = getStrokeWidth() + getStrokeLeft() + 1f
			// + dataSpacing * i;
			paint.setColor(0xffa5afbd);
			canvas.drawLine(startWhiteX, startWhiteY, endWhiteX, endWhiteY,
					paint);
			// paint.setColor(Color.YELLOW);
			// canvas.drawLine(startYellowX, startYellowY, endYellowX,
			// endYellowY,
			// paint);
			if (i == 1)
				path.moveTo(startWhiteX, startWhiteY);
			path.lineTo(startWhiteX, startWhiteY);
			if (i == timesList.size() - 1 || i == maxDate - 1) {
				path.lineTo(endWhiteX, endWhiteY);
				path.lineTo(endWhiteX, upChartBottom);
				path.lineTo(getStrokeWidth() + getStrokeLeft() + 1f,
						upChartBottom);
				path.lineTo(
						getStrokeWidth() + getStrokeLeft() + 1f,
						(float) (upChartBottom - (timesList.get(0).getClose() - upMinData)
								* upRatio));
			}
			startWhiteX = endWhiteX;
			startWhiteY = endWhiteY;
			// startYellowX = endYellowX;
			// startYellowY = endYellowY;
		}
		path.close();
		paint.setStyle(Style.FILL);
		paint.setAlpha(50);
		canvas.drawPath(path, paint);

		startWhiteX = getStrokeWidth() + getStrokeLeft() + 1f;
		startWhiteY = (float) (upChartBottom - (timesList.get(0).getGjjlx() - upMinData)
				* upRatio);
		for (int i = 1; i < timesList.size() && i < maxDate; i++) {
			// float startYellowX = getStrokeWidth() + getStrokeLeft() + 1f;
			// float startYellowY = upChartBottom
			// - latitudesSpacing
			// - (Float.parseFloat(map.get("daPanJiaQuan").toString()) -
			// upMinData)
			// * upRatio;
			float endWhiteY = (float) (upChartBottom - (timesList.get(i)
					.getGjjlx() - upMinData) * upRatio);
			float endWhiteX = getStrokeWidth() + getStrokeLeft() + 1f
					+ dataSpacing * i;
			// float endYellowY = upChartBottom
			// - latitudesSpacing
			// - (Float.parseFloat(timesList.get(i).get("daPanJiaQuan")
			// .toString()) - upMinData) * upRatio;
			// float endYellowX = getStrokeWidth() + getStrokeLeft() + 1f
			// + dataSpacing * i;
			paint.setColor(0xffffbb04);
			canvas.drawLine(startWhiteX, startWhiteY, endWhiteX, endWhiteY,
					paint);
			// paint.setColor(Color.YELLOW);
			// canvas.drawLine(startYellowX, startYellowY, endYellowX,
			// endYellowY,
			// paint);
			startWhiteX = endWhiteX;
			startWhiteY = endWhiteY;
			// startYellowX = endYellowX;
			// startYellowY = endYellowY;
		}
	}

	/**
	 * 绘制上表Y轴文字
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawUpAxisXTitle(Canvas canvas, TextPaint paint,
			float dataWidth) {
		paint.setColor(0xff798187);
		paint.setAntiAlias(true);
		if (getStrokeLeft() - getStrokeWidth() / 2 <= dataWidth) {
			canvas.drawText(Parse.getInstance().parse2String(upMaxData),
					getStrokeWidth() + getStrokeLeft() + 2, getStrokeWidth()
							+ getStrokeTop() + dateTextSize, paint);
			canvas.drawText(Parse.getInstance().parse2String(upMinData),
					getStrokeWidth() + getStrokeLeft() + 2, (getStrokeWidth()
							+ getStrokeTop() + latitudesSpacing
							* latitudesUpNum) - 2, paint);
		} else {
			float YData = upMaxData;
			float spacing = (upMaxData - upMinData) / (latitudesUpNum);
			for (int i = 0; i < latitudesUpNum + 1; i++) {
				if (i > 2)
					paint.setColor(getContext().getResources().getColor(
							R.color.z_green));
				else if (i < 2)
					paint.setColor(getContext().getResources().getColor(
							R.color.z_red));
				else
					paint.setColor(0xff798187);
				if (i == 0) {
					canvas.drawText(
							Parse.getInstance().parse2String(YData),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2String(YData)),
							getStrokeWidth() + getStrokeTop() + dateTextSize
									+ latitudesSpacing * i, paint);
				} else if (i == latitudesUpNum) {
					canvas.drawText(
							Parse.getInstance().parse2String(YData),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2String(YData)),
							(getStrokeWidth() + getStrokeTop() + latitudesSpacing
									* i) - 2, paint);
				} else {
					canvas.drawText(
							Parse.getInstance().parse2String(YData),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2String(YData)),
							getStrokeWidth() + getStrokeTop() + dateTextSize
									/ 2 + latitudesSpacing * i, paint);
				}
				YData -= spacing;
			}
		}

		float YData = upMaxData;
		float zDF = ((YData - preclose) / preclose) * 100;
		float spacing = (upMaxData - upMinData) / (latitudesUpNum);
		for (int i = 0; i < latitudesUpNum + 1; i++) {
			String zdf = Parse.getInstance().parse2String(zDF) + "%";
			if ("-0.00%".equals(zdf))
				zdf = "0.00%";
			if (i > 2)
				paint.setColor(getContext().getResources().getColor(
						R.color.z_green));
			else if (i < 2)
				paint.setColor(getContext().getResources().getColor(
						R.color.z_red));
			else
				paint.setColor(0xff798187);
			if (i == 0) {
				canvas.drawText(zdf, getWidth() - getStrokeRight()
						- getStrokeWidth() / 2 - paint.measureText(zdf) - 2,
						getStrokeWidth() + getStrokeTop() + dateTextSize
								+ latitudesSpacing * i, paint);
			} else if (i == latitudesUpNum) {
				canvas.drawText(zdf, getWidth() - getStrokeRight()
						- getStrokeWidth() / 2 - paint.measureText(zdf) - 2,
						(getStrokeWidth() + getStrokeTop() + latitudesSpacing
								* i) - 2, paint);
			} else {
				canvas.drawText(zdf, getWidth() - getStrokeRight()
						- getStrokeWidth() / 2 - paint.measureText(zdf) - 2,
						getStrokeWidth() + getStrokeTop() + dateTextSize / 2
								+ latitudesSpacing * i, paint);
			}
			YData -= spacing;
			zDF = ((YData - preclose) / preclose) * 100;
		}

	}

	/**
	 * 绘制下表柱形图
	 * 
	 * @param canvas
	 *            画布
	 */
	private void drawDownColumnChart(Canvas canvas, Paint paint) {
		if (stickData == null)
			return;
		paint.setAlpha(180);
		paint.setStrokeWidth(columnWidth);
		paint.setAntiAlias(true);
		// Map<String, Object> map = timesList.get(0);
		// float zuoShow = Float.parseFloat(map.get("zuoShow").toString());
		for (int i = 0; i < stickData.size() && i < maxDate; i++) {
			float chengJiaoLiang = (float) stickData.get(i).getHigh();
			// if (chengJiaoLiang >= zuoShow)
			paint.setColor(stickData.get(i).getColumnColor());
			// else
			// paint.setColor(Color.GREEN);
			float x = getStrokeWidth() + getStrokeLeft() + 1.0f + dataSpacing
					* i;
			canvas.drawLine(x, downChartBottom, x, downChartBottom
					- chengJiaoLiang * downRatio, paint);
		}
	}

	/**
	 * 绘制下表Y轴X坐标
	 * 
	 * @param canvas
	 * @param paint
	 * @param upDataWidth
	 * @param downDataWidth
	 */
	private void drawDownAxisXTitle(Canvas canvas, TextPaint paint,
			float dataWidth) {
		paint.setColor(0xff798187);
		paint.setAntiAlias(true);
		if (getStrokeLeft() - getStrokeWidth() / 2 <= dataWidth) {

		} else {
			float YData = downMinData;
			float spacing = (downMaxData - downMinData) / (latitudesDownNum);
			for (int i = 0; i < latitudesDownNum + 1; i++) {
				if (i == latitudesDownNum) {
					canvas.drawText(
							Parse.getInstance().parse2CNString(YData, false),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2CNString(YData, false)),
							downChartBottom + dateTextSize - latitudesSpacing
									* i, paint);
				} else if (i == 0) {
					canvas.drawText(
							"0"
							/* Parse.getInstance().parse2CNString(YData) */,
							getStrokeLeft() - getStrokeWidth() / 2 - 2
									- paint.measureText("0"/*
															 * Parse.getInstance(
															 * )
															 * .parse2CNString(
															 * YData)
															 */),
							downChartBottom - latitudesSpacing * i - 2, paint);
				} else {
					canvas.drawText(
							Parse.getInstance().parse2CNString(YData, false),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2CNString(YData, false)),
							downChartBottom + dateTextSize / 2
									- latitudesSpacing * i, paint);
				}
				YData += spacing;
			}
		}
	}

	/**
	 * 绘制时间
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawTime(Canvas canvas, TextPaint paint) {
		paint.setColor(0xff798187);
		paint.setAntiAlias(true);
		ArrayList<String> date = new ArrayList<String>();// 大小必须比经线数多2
		date.add("9:30");
		// date.add("10:30");
		date.add("11:30/13:00");
		// date.add("14:00");
		date.add("15:00");
		float Y = 0.0f;
		if (spacing != 0.0f) {
			Y = upChartBottom + dateTextSize + 5 / 2;
		} else {
			Y = getHeight();
		}
		for (int i = 0; i < date.size(); i++) {
			if (i == date.size() - 1) {
				canvas.drawText(date.get(i),
						getWidth() - getStrokeWidth() - getStrokeRight() - 2
								- paint.measureText(date.get(i)), Y, paint);
			} else if (i == 0) {
				canvas.drawText(date.get(i), getStrokeLeft() + getStrokeWidth()
						+ 2, Y, paint);
			} else {
				if ("11:30/13:00".equals(date.get(i))) {
					canvas.drawText(
							date.get(i),
							getStrokeWidth() + getStrokeLeft()
									+ longitudesSpacing
									* ((longitudesNum + 2 - 1) / 2)
									- paint.measureText(date.get(i)) / 2, Y,
							paint);
				} else {
					canvas.drawText(
							date.get(i),
							getStrokeWidth() + getStrokeLeft()
									+ longitudesSpacing * i
									- paint.measureText(date.get(i)) / 2, Y,
							paint);
				}
			}
		}
	}

	/**
	 * 绘制十字光标
	 * 
	 * @param canvas
	 *            画布
	 */
	private void drawCrossLine(Canvas canvas, Paint paint) {
		if (!isShowCross)
			return;
		paint.setStrokeWidth(2);
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setAlpha(180);
		// int rSpacing = 12;
		// 绘制十字光标横线
		// canvas.drawLine(getStrokeLeft() + getStrokeWidth(), crossY, crossX
		// - rSpacing >= getStrokeLeft() + getStrokeWidth() ? crossX
		// - rSpacing : getStrokeLeft() + getStrokeWidth(), crossY, paint);
		// // 绘制十字光标横线
		// canvas.drawLine(crossX + rSpacing <= getWidth() - getStrokeWidth()
		// - getStrokeRight() ? crossX + rSpacing : getWidth()
		// - getStrokeWidth() - getStrokeRight(), crossY, getWidth()
		// - getStrokeWidth() - getStrokeRight(), crossY, paint);

		// 绘制十字光标横线
		canvas.drawLine(getStrokeLeft() + getStrokeWidth(), crossY, getWidth()
				- getStrokeWidth() - getStrokeRight(), crossY, paint);
		// // 绘制上表十字光标竖线
		// canvas.drawLine(crossX, getStrokeWidth() + getStrokeTop(), crossX,
		// crossY - rSpacing >= getStrokeWidth() + getStrokeTop() ? crossY
		// - rSpacing : getStrokeWidth() + getStrokeTop(), paint);
		// // 绘制上表十字光标竖线
		// canvas.drawLine(crossX, crossY + rSpacing <= upChartBottom ? crossY
		// + rSpacing : upChartBottom, crossX, upChartBottom, paint);

		// 绘制上表十字光标竖线
		canvas.drawLine(crossX, getStrokeWidth() + getStrokeTop(), crossX,
				upChartBottom, paint);
		// 绘制下表十字光标竖线
		canvas.drawLine(crossX, getHeight() - getStrokeWidth()
				- getStrokeBottom(), crossX, downChartBottom - downChartHeight,
				paint);
		// 绘制十字光标交叉小圆点
		// Paint p = new Paint();
		// p.setColor(Color.WHITE);
		// p.setAntiAlias(true);
		// canvas.drawCircle(crossX, crossY, 6, p);

		// 绘制十字光标中心数据
		TextPaint tp = new TextPaint();
		tp.setColor(Color.WHITE);
		tp.setTextSize(dateTextSize);
		tp.setAntiAlias(true);
		tp.setTypeface(Typeface.DEFAULT_BOLD);

		paint.setColor(0xff141a2c);
		paint.setStrokeWidth(dateTextSize);
		canvas.drawLine(
				getStrokeLeft()
						- getStrokeWidth()
						/ 2
						- 6
						- tp.measureText(Parse.getInstance().parse2String(
								leftData)), crossY, getStrokeLeft()
						- getStrokeWidth() / 2 - 2, crossY, paint);

		canvas.drawText(
				Parse.getInstance().parse2String(leftData),
				getStrokeLeft()
						- getStrokeWidth()
						/ 2
						- 4
						- tp.measureText(Parse.getInstance().parse2String(
								leftData)), crossY + dateTextSize / 2, tp);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			touchDownX = event.getX();
			touchDownY = event.getY();
			downX = event.getRawX();
			downY = event.getRawY();
			isMoved = false;
			isReleased = false;
			isStartMoved = false;
			if (mRunnable != null) {
				removeCallbacks(mRunnable);
				postDelayed(mRunnable, 200);
			}
			getParent().requestDisallowInterceptTouchEvent(false);
			return true;

		case MotionEvent.ACTION_POINTER_DOWN:
			// 多点
			return true;

		case MotionEvent.ACTION_MOVE:
			// 触摸中
			if (Math.abs(event.getRawX() - downX) > 10
					|| Math.abs(event.getRawY() - downY) > 10.0f)
				isMoved = true;
			if (isStartMoved) {
				if (timesList != null && timesList.size() > 0) {
					getParent().requestDisallowInterceptTouchEvent(true);
					for (int i = 0; i < timesList.size() - 1 && i < maxDate - 1; i++) {
						float Y = (float) (upChartBottom - (timesList.get(i)
								.getClose() - upMinData) * upRatio);
						float endY = (float) (upChartBottom - (timesList.get(
								i + 1).getClose() - upMinData)
								* upRatio);
						float X = getStrokeWidth() + getStrokeLeft() + 1f
								+ dataSpacing * i;
						float endX = getStrokeWidth() + getStrokeLeft() + 1f
								+ dataSpacing * (i + 1);
						float spacing = endX - X;
						float positionX = event.getX() - X;
						if (event.getX() >= X && event.getX() < endX) {
							isShowCross = true;
							if (positionX <= spacing / 2) {
								crossX = X;
								crossY = Y;
								leftData = (float) timesList.get(i).getClose();
								if (l != null) {
									l.listener(timesList.get(i),
											stickData.get(i));
									l.isShowDetails(isShowCross);
								}
							} else {
								crossX = endX;
								crossY = endY;
								leftData = (float) timesList.get(i + 1)
										.getClose();
								if (l != null) {
									l.listener(timesList.get(i + 1),
											stickData.get(i + 1));
									l.isShowDetails(isShowCross);
								}
							}
							invalidate();
							return true;
						} else if (event.getX() >= endX
								&& (i + 1 == timesList.size() - 1 || i + 1 == maxDate - 1)) {
							isShowCross = true;
							crossX = endX;
							crossY = endY;
							leftData = (float) timesList.get(i + 1).getClose();
							if (l != null) {
								l.listener(timesList.get(i + 1),
										stickData.get(i + 1));
								l.isShowDetails(isShowCross);
							}
							invalidate();
							return true;
						}
					}
				}
			}
			return true;

		case MotionEvent.ACTION_UP:
			// 拿起
			isReleased = true;
			isShowCross = false;
			isStartMoved = false;
			if (l != null)
				l.isShowDetails(isShowCross);
			invalidate();
			return true;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 设置数据
	 * 
	 * @param timesList
	 *            分时数据
	 * @param upMaxData
	 *            最大价
	 * @param upMinData
	 *            最小价
	 * @param stickData
	 *            成交量数据
	 * @param downMinData
	 *            最小成交量
	 * @param preclose
	 *            昨收价
	 */
	public void setTimesList(List<OHLCEntity> timesList, float upMaxData,
			float upMinData, List<StickEntity> stickData, float downMinData,
			float preclose) {
		this.timesList = timesList;
		this.stickData = stickData;
		this.preclose = preclose;
		if ((timesList == null || timesList.size() < 1)
				|| (stickData == null || stickData.size() < 1))
			return;
		this.upMaxData = upMaxData;
		this.upMinData = upMinData;
		this.downMaxData = (float) stickData.get(0).getHigh();
		this.downMinData = downMinData;
		for (int i = 1; i < stickData.size() && i < maxDate; i++) {
			downMaxData = (float) (stickData.get(i).getHigh() < downMaxData ? downMaxData
					: stickData.get(i).getHigh());
		}
		upMaxData = (float) timesList.get(0).getClose();
		upMinData = (float) timesList.get(0).getClose();
		for (int i = 1; i < timesList.size() && i < maxDate; i++) {
			upMaxData = (float) (timesList.get(i).getClose() < upMaxData ? upMaxData
					: timesList.get(i).getClose());
			// upMaxData = Float.parseFloat(timesList.get(i).get("daPanJiaQuan")
			// .toString()) < upMaxData ? upMaxData
			// : Float.parseFloat(timesList.get(i).get("daPanJiaQuan")
			// .toString());
			upMinData = (float) (timesList.get(i).getClose() > upMinData ? upMinData
					: timesList.get(i).getClose());
			// upMinData = Float.parseFloat(timesList.get(i).get("daPanJiaQuan")
			// .toString()) > upMinData ? upMinData
			// : Float.parseFloat(timesList.get(i).get("daPanJiaQuan")
			// .toString());
			//
			// // 下表相关
			// downMaxData = Float.parseFloat(timesList.get(i)
			// .get("chengJiaoLiang").toString()) < downMaxData ? downMaxData
			// : Float.parseFloat(timesList.get(i).get("chengJiaoLiang")
			// .toString());
		}
		this.upMaxData = upMaxData;
		this.upMinData = upMinData;
		invalidate();
	}

	/**
	 * 设置上表与下表中间的日期与时间的文字大小
	 * 
	 * @param dateTextSize
	 *            像素单位
	 * @return
	 */
	public GridChartView setDateTextSize(float dateTextSize) {
		if (dateTextSize < 12) {
			dateTextSize = 12;
		}
		this.dateTextSize = dateTextSize;
		spacing = dateTextSize + 5;
		return this;
	}

	/**
	 * 获取上表与下表中间的日期与时间的文字大小
	 * 
	 * @return 像素单位
	 */
	public float getDateTextSize() {
		return dateTextSize;
	}

	/**
	 * 设置经线条数
	 * 
	 * @param longitudesNum
	 * @return
	 */
	public GridChartView setLongitudesNum(int longitudesNum) {
		if (longitudesNum < 3) {
			longitudesNum = DEFAULT_LONGITUDE_NUM;
		}
		this.longitudesNum = longitudesNum;
		return this;
	}

	/**
	 * 设置上表纬线条数
	 * 
	 * @param latitudesNum
	 * @return
	 */
	public GridChartView setLatitudesUpNum(int latitudesUpNum) {
		if (latitudesUpNum < 3) {
			latitudesUpNum = DEFAULT_LATITUDE_UP_NUM;
		}
		this.latitudesUpNum = latitudesUpNum;
		return this;
	}

	/**
	 * 设置经线颜色
	 * 
	 * @param longitudesColor
	 * @return
	 */
	public GridChartView setLongitudesColor(int longitudesColor) {
		this.longitudesColor = longitudesColor;
		return this;
	}

	/**
	 * 设置纬线颜色
	 * 
	 * @param latitudesColor
	 * @return
	 */
	public GridChartView setLatitudesColor(int latitudesColor) {
		this.latitudesColor = latitudesColor;
		return this;
	}

	/**
	 * 设置经、纬线宽度
	 * 
	 * @param latLongWidth
	 * @return
	 */
	public GridChartView setLatLongWidth(float latLongWidth) {
		if (latLongWidth < DEFAULT_LATITUDE_LONGITUDE_WIDTH) {
			latLongWidth = DEFAULT_LATITUDE_LONGITUDE_WIDTH;
		}
		this.latLongWidth = latLongWidth;
		return this;
	}

	/**
	 * 返回是否绘制经线
	 * 
	 * @return
	 */
	public boolean isDrawLongitudes() {
		return isDrawLongitudes;
	}

	/**
	 * 设置是否绘制经线
	 * 
	 * @param isDrawLongitudes
	 *            true绘制，false不绘制
	 * @return
	 */
	public GridChartView setDrawLongitudes(boolean isDrawLongitudes) {
		this.isDrawLongitudes = isDrawLongitudes;
		return this;
	}

	/**
	 * 返回是否绘制纬线
	 * 
	 * @return
	 */
	public boolean isDrawLatitudes() {
		return isDrawLatitudes;
	}

	/**
	 * 设置是否显示纬线
	 * 
	 * @param isDrawLatitudes
	 *            true绘制，false不绘制
	 * @return
	 */
	public GridChartView setDrawLatitudes(boolean isDrawLatitudes) {
		this.isDrawLatitudes = isDrawLatitudes;
		return this;
	}

	/**
	 * 设置上表与下表间距（如果设置值小于默认字体大小，则设置成默认字体大小大5像素；如果设置值大于默认字体大小＋5大小，那么字体也随之变化；
	 * 如果设置值等于0.0f，那么时间文字将绘制则底部，并且上表与下表间距为0，底部高度为默认字体高度）
	 * 
	 * @param spacing
	 */
	public void setSpacing(float spacing) {
		if (spacing < DEFAULT_DATETEXTSIZE && spacing > 0) {
			spacing = DEFAULT_DATETEXTSIZE + 5;
		} else if (spacing > DEFAULT_DATETEXTSIZE + 5) {
			this.spacing = spacing;
			dateTextSize = spacing - 5;
		} else {
			this.spacing = spacing;
		}
	}

	/**
	 * 是否设置了自动计算左边距
	 * 
	 * @return
	 */
	public boolean isAutoLeftPadding() {
		return isAutoLeftPadding;
	}

	/**
	 * 设置是否自动计算左边距
	 * 
	 * @param isAutoLeftPadding
	 */
	public void setAutoLeftPadding(boolean isAutoLeftPadding) {
		this.isAutoLeftPadding = isAutoLeftPadding;
	}

	/**
	 * 设置接口
	 */
	public void setOnTimeListener(OnTimeListener l) {
		this.l = l;
	}

	/**
	 * 通知外部接口
	 * 
	 * @author 锐
	 * 
	 */
	public interface OnTimeListener {
		/**
		 * 传递数据
		 * 
		 * @param data
		 */
		public void listener(OHLCEntity data, StickEntity stickEntity);

		/**
		 * 是否显示指标详情
		 * 
		 * @param isShowDetails
		 */
		public void isShowDetails(boolean isShowDetails);
	}
}
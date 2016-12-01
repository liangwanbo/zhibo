package com.mytv365.zb.widget.stock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.mytv365.zb.R;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Parse;

import java.util.List;


public class KLineChartView extends GridChartView {

	/** 接口 */
	private KLineListener l;

	/** 数据 */
	private List<OHLCEntity> OHLCData;// 上表数据
	private List<StickEntity> stickEntity;// 下表数据
	/** 左边要显示当前十字光标中心点的数据 */
	private float leftData;

	/** 默认上表纬线条数 */
	private final int DEFAULT_LATITUDE_UP_NUM = 4;
	/** 默认下表纬线条数 */
	private final int DEFAULT_LATITUDE_DOWN_NUM = 2;
	/** 默认经线条数 */
	private final int DEFAULT_LONGITUDE_NUM = 3;
	/** 默认上表高度与总高度比例 */
	private final float DEFAULT_UP_RATIO = 0.75f;
	/** 默认纬线颜色 */
	private final int DEFAULT_LATITUDE_COLOR = Color.GRAY;
	/** 默认经线颜色 */
	private final int DEFAULT_LONGITUDE_COLOR = Color.GRAY;
	/** 默认经纬线宽 */
	private final float DEFAULT_LATLONGWIDTH = 2;
	/** 默认字体大小 */
	private final float DEFAULT_TEXT_SIZE = 25;
	/** 默认最多显示数 */
	private final int DEFAULT_CANDLE_NUM = 15 * 2;

	/** 控件高度 */
	private float viewHeight;
	/** 控件宽度 */
	private float viewWidth;
	/** 上表纬线条数 */
	protected int latitudesUpNum = DEFAULT_LATITUDE_UP_NUM;
	/** 下表纬线条数 */
	protected int latitudesDownNum = DEFAULT_LATITUDE_DOWN_NUM;
	/** 经线条数 */
	protected int longitudesNum = DEFAULT_LONGITUDE_NUM;
	/** 上表纬线间距 */
	private float upLatitudesSpacing;
	/** 下表纬线间距 */
	private float downLatitudesSpacing;
	/** 经线间距 */
	private float longitudesSpacing;
	/** 上表与下表间距 */
	private float spacing;
	/** 上表纬线颜色 */
	private int latitudesColor = DEFAULT_LATITUDE_COLOR;
	/** 经纬线宽 */
	private float latLongWidth = DEFAULT_LATLONGWIDTH;
	/** 字体大小 */
	private float textSize = DEFAULT_TEXT_SIZE;
	/** 上表底部 */
	protected float upChartBottom;
	/** 下表底部 */
	protected float downChartBottom;
	/** 上表高度 */
	protected float upChartHeight;
	/** 下表高度 */
	protected float downChartHeight;
	/** 上表高度与控件高度比 */
	private float upRatio = DEFAULT_UP_RATIO;

	/** 上表最大数据 */
	private float upMaxData;
	/** 上表最小数据 */
	private float upMinData;
	/** 下表最大数据 */
	private float downMaxData;
	/** 下表最小数据 */
	private float downMinData;
	/** 上表的尺寸与数据比例 */
	private float upDataRatio;
	/** 下表的尺寸与数据比例 */
	private float downDataRatio;

	/** 最多显示数 */
	private int candleNum = DEFAULT_CANDLE_NUM;
	/** 数据间距 */
	private float dataSpacing;
	/** 记录当前滚动到的位置 */
	private int position;
	/** 是否显示十字光标 */
	private boolean isShowCrossLine;
	/** 设置是否自动计算左边距 */
	// private boolean isAutoLeftPadding;

	/** 手势相关 */
	private float downX;// 按下X坐标
	private float downY;// 按下Y坐标
	private float moveX;// 触摸中的X坐标
	private float moveY;// 触摸中的Y坐标
	private int touchMode;// 触摸模式
	private final int MODE1 = 1;// 左右滑动模式
	private final int MODE2 = 2;// 捏合模式
	private final int MODE3 = 3;// 十字光标模式
	private final int MODE4 = 4;// 点击加号模式
	private final int MODE5 = 5;// 点击减号模式
	private float oldDistance;// 旧的移动距离
	private float newDistance;// 新的移动距离
	private Runnable mRunnable;// 长按事件的线程
	private boolean isMoved;// 是否触摸中
	private boolean isReleased;// 手指是否离开屏幕
	private boolean isStartMoved;// 是否可以执行OnTouch中的Move事件了
	private boolean isLeftRightMoved;// 是否在左右滑动
	private float distance;// 捏合事件中的捏合距离
	private float crossX;// 十字光标X坐标
	private float crossY;// 十字光标Y坐标
	private int jiaAlaph;// 加号透明度
	private int jianAlaph;// 减号透明度

	public KLineChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public KLineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public KLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		spacing = textSize + 5;
		jiaAlaph = 255;
		jianAlaph = 255;
		position = 0;
		// isAutoLeftPadding = true;
		isShowCrossLine = false;
		mRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isMoved || isReleased)
					return;
				if (OHLCData != null && OHLCData.size() > 0) {

					float startX = getStrokeWidth() + getStrokeLeft();
					int index = 0;
					for (int i = position; i < OHLCData.size()
							&& i < candleNum + position; i++) {
						OHLCEntity entity = OHLCData.get(i);
						// 柱形图
						float openY = upChartBottom
								- ((float) entity.getOpen() - upMinData)
								* upDataRatio;
						float closeY = upChartBottom
								- ((float) entity.getClose() - upMinData)
								* upDataRatio;
						// 中线
						// float highY = upChartBottom - (entity.getHigh() -
						// upMinData)
						// * upDataRatio;
						// float lowY = upChartBottom - (entity.getLow() -
						// upMinData)
						// * upDataRatio;

						float endX = getStrokeWidth()
								+ getStrokeLeft()
								+ (dataSpacing * (index + 1) - dataSpacing * 0.25f);

						if ((downX > startX && downX < endX + dataSpacing
								* 0.25f)
								|| (downX > endX && index + 1 == OHLCData
										.size())) {
							isShowCrossLine = true;
							crossX = (endX - startX) / 2 + startX;
							if (openY < closeY) {// 绿色
								crossY = closeY;
							} else if (openY > closeY) {// 红色
								crossY = closeY;
							} else {// 红色
								crossY = openY;
							}
							leftData = Parse.getInstance().parseFloat(
									Parse.getInstance().parse2String(
											entity.getClose()));
							if (l != null) {
								l.isMove(true);
								l.transferData(OHLCData, stickEntity, i);
							}
							postInvalidate();
							break;
						}
						startX = getStrokeWidth() + getStrokeLeft()
								+ dataSpacing * (index + 1);
						index++;
					}

				}
				isStartMoved = true;
				touchMode = MODE3;
			}
		};
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if ((OHLCData == null || OHLCData.size() < 1)
				|| (stickEntity == null || stickEntity.size() < 1)) {
			return;
		}
		viewHeight = getHeight();
		viewWidth = getWidth();
		// 计算上表高度
		upChartHeight = (viewHeight - getStrokeWidth() * 2 - getStrokeTop()
				- getStrokeBottom() - spacing)
				* upRatio;
		// 计算上表底部位置
		upChartBottom = upChartHeight + getStrokeWidth() + getStrokeTop();
		// 计算经线间距
		longitudesSpacing = (viewWidth - (getStrokeWidth() * 2
				+ getStrokeLeft() + getStrokeRight()))
				/ (longitudesNum + 1);
		// 计算上表纬线间距
		upLatitudesSpacing = upChartHeight / latitudesUpNum;
		// 计算下表高度
		downChartHeight = (viewHeight - getStrokeWidth() * 2 - getStrokeTop()
				- getStrokeBottom() - spacing)
				* (1 - upRatio);
		// 计算下表底部位置
		downChartBottom = viewHeight - getStrokeWidth() - getStrokeBottom();
		// 计算下表纬线间距
		downLatitudesSpacing = downChartHeight / latitudesDownNum;
		// 计算数据（蜡烛）间距
		dataSpacing = (viewWidth - getStrokeWidth() * 2 - getStrokeLeft() - getStrokeRight())
				/ candleNum;

		// 计算上表最大、最小数值
		upMaxData = OHLCData.get(position).getHigh();
		upMinData = OHLCData.get(position).getLow();
		for (int i = position; i < OHLCData.size() && i < candleNum + position; i++) {
			upMaxData = upMaxData < OHLCData.get(i).getHigh() ? OHLCData.get(i)
					.getHigh() : upMaxData;
			upMaxData = upMaxData < OHLCData.get(i).getMA5() ? OHLCData.get(i)
					.getMA5() : upMaxData;
			upMaxData = upMaxData < OHLCData.get(i).getMA10() ? OHLCData.get(i)
					.getMA10() : upMaxData;
			upMaxData = upMaxData < OHLCData.get(i).getMA20() ? OHLCData.get(i)
					.getMA20() : upMaxData;
			upMinData = upMinData < OHLCData.get(i).getLow() ? upMinData
					: OHLCData.get(i).getLow();
			upMinData = upMinData < OHLCData.get(i).getMA5() ? upMinData
					: OHLCData.get(i).getMA5();
			upMinData = upMinData < OHLCData.get(i).getMA10() ? upMinData
					: OHLCData.get(i).getMA10();
			upMinData = upMinData < OHLCData.get(i).getMA20() ? upMinData
					: OHLCData.get(i).getMA20();
		}
		// upMaxData = upMaxData * 1.05f;
		// upMinData = upMinData * 0.95f;

		// 计算下表最大值
		downMaxData = (float) stickEntity.get(position).getHigh();
		downMinData = 0.0f;
		for (int i = position; i < stickEntity.size()
				&& i < candleNum + position; i++) {
			downMaxData = (float) (downMaxData < stickEntity.get(i).getHigh() ? stickEntity
					.get(i).getHigh() : downMaxData);
			downMaxData = (float) (downMaxData < stickEntity.get(i).getVMA5() ? stickEntity
					.get(i).getVMA5() : downMaxData);
			downMaxData = (float) (downMaxData < stickEntity.get(i).getVMA10() ? stickEntity
					.get(i).getVMA10() : downMaxData);
			downMaxData = (float) (downMaxData < stickEntity.get(i).getVMA20() ? stickEntity
					.get(i).getVMA20() : downMaxData);
		}
		// downMaxData = downMaxData * 1.05f;

		TextPaint tp = new TextPaint();
		tp.setTextSize(textSize);
		tp.setTypeface(Typeface.DEFAULT_BOLD);
		tp.setAntiAlias(true);
		// float upDataWidth = tp.measureText(Parse.getInstance().parse2String(
		// upMaxData));
		// float downDataWidth = tp.measureText(Parse.getInstance()
		// .parse2CNString(downMaxData));
		float dataWidth = tp.measureText("19990909");
		// if (isAutoLeftPadding) {
		// setStrokeLeft(getStrokeWidth() + dataWidth + 6);
		// // setStrokeLeft(getStrokeWidth()
		// // + (upDataWidth > downDataWidth ? upDataWidth + 6
		// // : downDataWidth + 6));
		// }
		// if (spacing == 0.0f)

		// 计算上表与数据的比例
		upDataRatio = upChartHeight / (upMaxData - upMinData);
		// 计算下表与数据比例
		downDataRatio = downChartHeight / (downMaxData - downMinData);

		Paint paint = new Paint();
		paint.setAntiAlias(true);

		// 绘制纬线
		drawLatitudes(canvas, paint);
		// 绘制经线
		drawLongitudes(canvas, paint);
		// 绘制蜡烛线
		drawCandleLine(canvas, paint, tp);
		// 绘制是上表折线
		drawUpLine(canvas, paint);
		// 绘制下表柱形图
		drawColumnChart(canvas, paint);
		// 绘制下表折线
		drawDownLine(canvas, paint);
		// 绘制上表左边Y轴刻度
		drawUpAxisXTitle(canvas, tp, dataWidth);
		// 绘制下表左边Y轴刻度
		drawDownAxisXTitle(canvas, tp, dataWidth);
		// 绘制加减号
		drawZoom(canvas);
		// 绘制十字光标
		drawCrossLine(canvas, paint, tp);
	}

	/**
	 * 绘制纬线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawLatitudes(Canvas canvas, Paint paint) {
		paint.setColor(latitudesColor);
		paint.setStrokeWidth(latLongWidth);
		paint.setAlpha(110);
		paint.setAntiAlias(true);
		for (int i = 1; i <= latitudesUpNum; i++) {
			canvas.drawLine(getStrokeLeft(), upLatitudesSpacing * i
					+ getStrokeWidth() + getStrokeTop(), getWidth()
					- getStrokeRight(), upLatitudesSpacing * i
					+ getStrokeWidth() + getStrokeTop(), paint);
		}
		for (int i = 1; i <= latitudesDownNum; i++) {
			canvas.drawLine(getStrokeLeft(),
					(getHeight() - getStrokeWidth() - getStrokeBottom())
							- downLatitudesSpacing * i, getWidth()
							- getStrokeRight(),
					(getHeight() - getStrokeWidth() - getStrokeBottom())
							- downLatitudesSpacing * i, paint);
		}
	}

	/**
	 * 绘制经线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawLongitudes(Canvas canvas, Paint paint) {
		paint.setColor(latitudesColor);
		paint.setStrokeWidth(latLongWidth);
		paint.setAlpha(110);
		paint.setAntiAlias(true);
		for (int i = 1; i <= longitudesNum; i++) {
			float X = getStrokeLeft() + i * longitudesSpacing;
			canvas.drawLine(X, getStrokeTop(), X, upChartBottom, paint);
			canvas.drawLine(X, upChartBottom + spacing, X, downChartBottom,
					paint);
		}
	}

	/**
	 * 绘制蜡烛线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawCandleLine(Canvas canvas, Paint paint, TextPaint tp) {
		paint.setStrokeWidth(2);
		float startX = getStrokeWidth() + getStrokeLeft();
		int index = 0;
		// String oldDate = "";
		float oldX = 0.0f;
		for (int i = position; i < OHLCData.size() && i < candleNum + position; i++) {
			OHLCEntity entity = OHLCData.get(i);
			// String date = Parse.getInstance().isNull(entity.getDate());
			// date = date.substring(0, 6);

			// 柱形图
			float openY = upChartBottom
					- ((float) entity.getOpen() - upMinData) * upDataRatio;
			float closeY = upChartBottom
					- ((float) entity.getClose() - upMinData) * upDataRatio;
			// 中线
			float highY = upChartBottom - (entity.getHigh() - upMinData)
					* upDataRatio;
			float lowY = upChartBottom - (entity.getLow() - upMinData)
					* upDataRatio;

			float endX = getStrokeWidth() + getStrokeLeft()
					+ (dataSpacing * (index + 1) - dataSpacing * 0.25f);

			// 绘制经线
			// if (entity.isDrawLongitude()) {
			// paint.setColor(Color.GRAY);
			// paint.setAlpha(150);
			//
			// tp.setColor(0xff868F9B);
			// String date = Parse.getInstance().isNull(entity.getDate());
			// date = date.substring(0, 6);
			// float textWidth = tp.measureText(date);
			// float textY = upChartBottom + textSize + 5 / 2;
			// float newX = 0.0f;
			// if (((endX - startX) / 2 + startX) - (textWidth / 2) <=
			// getStrokeLeft()
			// + getStrokeWidth()) {
			// newX = getStrokeWidth() + getStrokeLeft() + 2;
			// } else if (viewWidth - getStrokeWidth() - getStrokeRight()
			// - ((endX - startX) / 2 + startX) <= textWidth / 2) {
			// newX = viewWidth - textWidth - 2;
			// } else {
			// newX = ((endX - startX) / 2 + startX) - (textWidth / 2);
			// }
			// if (i != position) {
			// if (newX - oldX > textWidth + 2) {
			// canvas.drawLine((endX - startX) / 2 + startX,
			// getStrokeWidth() + getStrokeTop(),
			// (endX - startX) / 2 + startX, upChartBottom,
			// paint);
			// canvas.drawLine((endX - startX) / 2 + startX,
			// upChartBottom + spacing, (endX - startX) / 2
			// + startX, downChartBottom, paint);
			// canvas.drawText(date, newX, textY, tp);
			// oldX = newX;
			// }
			// } else {
			// canvas.drawLine((endX - startX) / 2 + startX,
			// getStrokeWidth() + getStrokeTop(), (endX - startX)
			// / 2 + startX, upChartBottom, paint);
			// canvas.drawLine((endX - startX) / 2 + startX, upChartBottom
			// + spacing, (endX - startX) / 2 + startX,
			// downChartBottom, paint);
			//
			// canvas.drawText(date, newX, textY, tp);
			// oldX = newX;
			// }
			// }
			// oldDate = date;

			if (openY < closeY) {
				stickEntity.get(i).setColumnColor(
						getContext().getResources().getColor(R.color.z_green));
				paint.setColor(getContext().getResources().getColor(
						R.color.z_green));// 绿色
				paint.setStyle(Style.FILL);
				canvas.drawRect(startX, openY, endX, closeY, paint);
				canvas.drawLine((endX - startX) / 2 + startX, highY,
						(endX - startX) / 2 + startX, lowY, paint);
			} else if (openY > closeY) {
				stickEntity.get(i).setColumnColor(
						getContext().getResources().getColor(R.color.z_red));
				paint.setColor(getContext().getResources().getColor(
						R.color.z_red));
				paint.setStyle(Style.STROKE);
				canvas.drawRect(startX, closeY, endX, openY, paint);
				paint.setStyle(Style.FILL);
				canvas.drawLine((endX - startX) / 2 + startX, highY,
						(endX - startX) / 2 + startX, closeY, paint);
				canvas.drawLine((endX - startX) / 2 + startX, lowY,
						(endX - startX) / 2 + startX, openY, paint);
			} else {
				stickEntity.get(i).setColumnColor(
						getContext().getResources().getColor(R.color.z_red));
				paint.setColor(getContext().getResources().getColor(
						R.color.z_red));
				paint.setStyle(Style.FILL);
				canvas.drawLine(startX, openY, endX, openY, paint);
				canvas.drawLine((endX - startX) / 2 + startX, highY,
						(endX - startX) / 2 + startX, lowY, paint);
			}

			startX = getStrokeWidth() + getStrokeLeft() + dataSpacing
					* (index + 1);
			index++;
			if (i == position) {
				tp.setColor(0xff868F9B);
				canvas.drawText(String.valueOf(entity.getDate()),
						getStrokeLeft() + getStrokeWidth() + 2, viewHeight, tp);
			}
		}
	}

	/**
	 * 绘制上表折线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawUpLine(Canvas canvas, Paint paint) {
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2);
		int index = 0;
		for (int i = position; i < OHLCData.size() && i < candleNum + position; i++) {
			OHLCEntity entity = OHLCData.get(i);
			if (i < OHLCData.size() - 1 && i < candleNum + position - 1) {
				float startMA5Y = upChartBottom - (entity.getMA5() - upMinData)
						* upDataRatio;
				float endMA5Y = upChartBottom
						- (OHLCData.get(i + 1).getMA5() - upMinData)
						* upDataRatio;
				float lineStartX = getStrokeWidth() + getStrokeLeft()
						+ dataSpacing * index + (dataSpacing * 0.75f / 2);
				float lineEndX = getStrokeWidth() + getStrokeLeft()
						+ dataSpacing * (index + 1) + (dataSpacing * 0.75f / 2);
				paint.setColor(Color.WHITE);
				canvas.drawLine(lineStartX, startMA5Y, lineEndX, endMA5Y, paint);

				float startMA10Y = upChartBottom
						- (entity.getMA10() - upMinData) * upDataRatio;
				float endMA10Y = upChartBottom
						- (OHLCData.get(i + 1).getMA10() - upMinData)
						* upDataRatio;
				paint.setColor(0xffffff00);
				canvas.drawLine(lineStartX, startMA10Y, lineEndX, endMA10Y,
						paint);

				float startMA25Y = upChartBottom
						- (entity.getMA20() - upMinData) * upDataRatio;
				float endMA25Y = upChartBottom
						- (OHLCData.get(i + 1).getMA20() - upMinData)
						* upDataRatio;
				paint.setColor(0xff800080);
				canvas.drawLine(lineStartX, startMA25Y, lineEndX, endMA25Y,
						paint);
			}
			index++;
		}
	}

	/**
	 * 绘制下表柱形图
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawColumnChart(Canvas canvas, Paint paint) {
		int index = 0;
		for (int i = position; i < stickEntity.size()
				&& i < candleNum + position; i++) {
			paint.setColor(stickEntity.get(i).getColumnColor());
			if (stickEntity.get(i).getColumnColor() == getContext()
					.getResources().getColor(R.color.z_red)) {
				paint.setStyle(Style.STROKE);
			} else {
				paint.setStyle(Style.FILL);
			}
			StickEntity entity = stickEntity.get(i);
			float startX = getStrokeWidth() + getStrokeLeft() + dataSpacing
					* index;
			float endX = getStrokeWidth() + getStrokeLeft()
					+ (dataSpacing * (index + 1) - dataSpacing * 0.25f);
			float highY = downChartBottom
					- ((float) entity.getHigh() - downMinData) * downDataRatio;
			float lowY = downChartBottom
					- ((float) entity.getLow() - downMinData) * downDataRatio;
			canvas.drawRect(startX, highY, endX, lowY, paint);
			index++;
		}
	}

	/**
	 * 绘制下表折线
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawDownLine(Canvas canvas, Paint paint) {
		paint.setStyle(Style.FILL);
		paint.setStrokeWidth(2);
		int index = 0;
		for (int i = position; i < stickEntity.size() - 1
				&& i < candleNum + position - 1; i++) {
			StickEntity entity = stickEntity.get(i);
			float startVMA5Y = downChartBottom
					- (entity.getVMA5() - downMinData) * downDataRatio;
			float endVMA5Y = downChartBottom
					- (stickEntity.get(i + 1).getVMA5() - downMinData)
					* downDataRatio;
			float lineStartX = getStrokeWidth() + getStrokeLeft() + dataSpacing
					* index + (dataSpacing * 0.75f / 2);
			float lineEndX = getStrokeWidth() + getStrokeLeft() + dataSpacing
					* (index + 1) + (dataSpacing * 0.75f / 2);
			paint.setColor(Color.WHITE);
			canvas.drawLine(lineStartX, startVMA5Y, lineEndX, endVMA5Y, paint);

			float startVMA10Y = downChartBottom
					- (entity.getVMA10() - downMinData) * downDataRatio;
			float endVMA10Y = downChartBottom
					- (stickEntity.get(i + 1).getVMA10() - downMinData)
					* downDataRatio;
			paint.setColor(0xffffff00);
			canvas.drawLine(lineStartX, startVMA10Y, lineEndX, endVMA10Y, paint);

			float startVMA25Y = downChartBottom
					- (entity.getVMA20() - downMinData) * downDataRatio;
			float endVMA25Y = downChartBottom
					- (stickEntity.get(i + 1).getVMA20() - downMinData)
					* downDataRatio;
			paint.setColor(0xff800080);
			canvas.drawLine(lineStartX, startVMA25Y, lineEndX, endVMA25Y, paint);
			index++;
		}
	}

	/**
	 * 绘制上表左边Y刻度
	 * 
	 * @param canvas
	 *            画布
	 * @param paint
	 *            画笔
	 * @param upDataWidth
	 *            上表最大数据的最大宽度
	 * @param downDataWidth
	 *            下表最大数据的最大宽度
	 */
	private void drawUpAxisXTitle(Canvas canvas, TextPaint paint,
			float dataWidth) {

		paint.setColor(0xff868F9B);
		if (getStrokeLeft() - getStrokeWidth() / 2 <= dataWidth) {
			canvas.drawText(Parse.getInstance().parse2String(upMaxData),
					getStrokeWidth() + getStrokeLeft() + 2, getStrokeWidth()
							+ getStrokeTop() + textSize, paint);
			canvas.drawText(Parse.getInstance().parse2String(upMinData),
					getStrokeWidth() + getStrokeLeft() + 2, (getStrokeWidth()
							+ getStrokeTop() + upLatitudesSpacing
							* latitudesUpNum) - 2, paint);
		} else {
			float YData = upMaxData;
			float spacing = (upMaxData - upMinData) / (latitudesUpNum);
			for (int i = 0; i < latitudesUpNum + 1; i++) {
				if (i < 2) {
					paint.setColor(getResources().getColor(R.color.z_red));
				} else if (i > 2) {
					paint.setColor(getResources().getColor(R.color.z_green));
				} else {
					paint.setColor(getResources()
							.getColor(R.color.z_list_title));
				}
				if (i == 0) {
					canvas.drawText(
							Parse.getInstance().parse2String(YData),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2String(YData)),
							getStrokeWidth() + getStrokeTop() + textSize
									+ upLatitudesSpacing * i, paint);
				} else if (i == latitudesUpNum) {
					canvas.drawText(
							Parse.getInstance().parse2String(YData),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2String(YData)),
							(getStrokeWidth() + getStrokeTop() + upLatitudesSpacing
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
							getStrokeWidth() + getStrokeTop() + textSize / 2
									+ upLatitudesSpacing * i, paint);
				}
				YData -= spacing;
			}
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
		paint.setColor(0xff868F9B);
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
							downChartBottom + textSize - downLatitudesSpacing
									* i, paint);
				} else if (i == 0) {
					canvas.drawText(
							Parse.getInstance().parse2CNString(YData, false),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2CNString(YData, false)),
							downChartBottom - downLatitudesSpacing * i - 2,
							paint);
				} else {
					canvas.drawText(
							Parse.getInstance().parse2CNString(YData, false),
							getStrokeLeft()
									- getStrokeWidth()
									/ 2
									- 2
									- paint.measureText(Parse.getInstance()
											.parse2CNString(YData, false)),
							downChartBottom + textSize / 2
									- downLatitudesSpacing * i, paint);
				}
				YData += spacing;
			}
		}
	}

	/** 绘制加减号 */
	private void drawZoom(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAlpha(jianAlaph);
		float width = getWidth() - getStrokeRight() - getStrokeWidth() / 2;
		Rect rect = new Rect((int) width
				- MyUtils.getInstance().dp2px(getContext(), 10 + 33 + 10 + 33),
				(int) upChartBottom
						- MyUtils.getInstance().dp2px(getContext(), 38),
				(int) width
						- MyUtils.getInstance().dp2px(getContext(),
								10 + 33 + 10), (int) upChartBottom
						- MyUtils.getInstance().dp2px(getContext(), 5));
		Rect rect1 = new Rect(0, 0, 200, 200);
		Bitmap bm = BitmapFactory.decodeResource(getResources(),
				R.drawable.zoomout);
		canvas.drawBitmap(bm, rect1, rect, paint);

		paint.setAlpha(jiaAlaph);
		Rect rect2 = new Rect((int) width
				- MyUtils.getInstance().dp2px(getContext(), 10 + 33),
				(int) upChartBottom
						- MyUtils.getInstance().dp2px(getContext(), 38),
				(int) width - MyUtils.getInstance().dp2px(getContext(), 10),
				(int) upChartBottom
						- MyUtils.getInstance().dp2px(getContext(), 5));
		Rect rect3 = new Rect(0, 0, 200, 200);
		Bitmap bm1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.zoomin);
		canvas.drawBitmap(bm1, rect3, rect2, paint);
	}

	/**
	 * 绘制十字光标
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawCrossLine(Canvas canvas, Paint paint, TextPaint tp) {
		if (isShowCrossLine) {
			paint.setStyle(Style.FILL);
			paint.setAlpha(180);
			paint.setColor(Color.WHITE);
			paint.setStrokeWidth(2);
			canvas.drawLine(getStrokeWidth() + getStrokeLeft(), crossY,
					viewWidth - getStrokeWidth() - getStrokeRight(), crossY,
					paint);
			canvas.drawLine(crossX, getStrokeTop() + getStrokeWidth(), crossX,
					upChartBottom, paint);
			canvas.drawLine(crossX, upChartBottom + spacing, crossX,
					downChartBottom, paint);

			tp.setColor(Color.WHITE);

			paint.setColor(0xff141a2c);
			paint.setStrokeWidth(textSize);
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
									leftData)), crossY + textSize / 2, tp);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			float width = getWidth() - getStrokeRight() - getStrokeWidth() / 2;
			downX = event.getX();
			downY = event.getY();
			moveX = event.getRawX();
			moveY = event.getRawY();
			if ((downX > width
					- MyUtils.getInstance().dp2px(getContext(),
							10 + 33 + 10 + 33) && downX < width
					- MyUtils.getInstance().dp2px(getContext(), 10 + 33 + 10))
					&& (downY > upChartBottom
							- MyUtils.getInstance().dp2px(getContext(), 38) && downY < upChartBottom
							- MyUtils.getInstance().dp2px(getContext(), 5))) {
				touchMode = MODE5;
				return true;
			}
			if ((downX > width
					- MyUtils.getInstance().dp2px(getContext(), 10 + 33) && downX < width
					- MyUtils.getInstance().dp2px(getContext(), 10))
					&& (downY > upChartBottom
							- MyUtils.getInstance().dp2px(getContext(), 38) && downY < upChartBottom
							- MyUtils.getInstance().dp2px(getContext(), 5))) {
				touchMode = MODE4;
				return true;
			}
			touchMode = 0;
			if (downX < getStrokeLeft() + getStrokeWidth() / 2) {
				return false;
			}
			isMoved = false;
			isReleased = false;
			isStartMoved = false;
			isLeftRightMoved = false;
			getParent().requestDisallowInterceptTouchEvent(true);
			if (mRunnable != null) {
				removeCallbacks(mRunnable);
				postDelayed(mRunnable, 200);
			}
			return true;

		case MotionEvent.ACTION_POINTER_DOWN:
			// 多点
			oldDistance = spacing(event);
			return true;

		case MotionEvent.ACTION_MOVE:
			// 触摸
			if (Math.abs(event.getRawY() - moveY) > 50
					&& Math.abs(event.getRawX() - moveX) < 150
					&& touchMode == 0) {
				isMoved = true;
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			if (downX < getStrokeLeft() + getStrokeWidth() / 2) {
				return false;
			}
			if (Math.abs(Math.abs(event.getX()) - Math.abs(downX)) > 50
					&& Math.abs(Math.abs(event.getY()) - Math.abs(downY)) < 150
					&& touchMode == 0) {
				isMoved = true;
				touchMode = MODE1;
			}
			if (spacing(event) > dataSpacing && !isStartMoved
					&& !isLeftRightMoved && touchMode == 0) {
				isMoved = true;
				touchMode = MODE2;
			}
			// if (touchMode == MODE1) {
			// // 左右滑动事件
			// float indexX = event.getRawX() - moveX;
			// if (indexX < 0 - dataSpacing / 2) {
			// moveX = event.getRawX();
			// isMoved = true;
			// isLeftRightMoved = true;
			// if (this.OHLCData.size() < candleNum) {
			// return false;
			// }
			// position++;
			// if (position >= this.OHLCData.size() - candleNum) {
			// position = this.OHLCData.size() - candleNum;
			// }
			// invalidate();
			// return true;
			// } else if (indexX > dataSpacing / 2) {
			// moveX = event.getRawX();
			// isMoved = true;
			// isLeftRightMoved = true;
			// if (this.OHLCData.size() < candleNum) {
			// return false;
			// }
			// position--;
			// if (position <= 0) {
			// position = 0;
			// }
			// invalidate();
			// return true;
			// }
			// } else if (touchMode == MODE2) {
			// // 捏合事件
			// newDistance = spacing(event);
			// if (newDistance > dataSpacing / 2
			// && Math.abs(newDistance - oldDistance) > dataSpacing / 2) {
			// isMoved = true;
			// if (newDistance > oldDistance) {
			// if (candleNum <= 1 * 15) {
			// return false;
			// }
			// candleNum--;
			// invalidate();
			// } else {
			// if (candleNum >= 6*15) {
			// return false;
			// }
			// if (OHLCData.size() - position <= candleNum) {
			// if (position > 0)
			// position--;
			// }
			// candleNum++;
			// invalidate();
			// }
			// // 重置距离
			// oldDistance = newDistance;
			// }
			// } else if (touchMode == MODE3) {
			if (isStartMoved) {
				if (OHLCData != null && OHLCData.size() > 0) {
					calculationCrossLine(event);
					return true;
				}
			}
			// }
			return true;

		case MotionEvent.ACTION_POINTER_UP:
			touchMode = 0;
			return true;

		case MotionEvent.ACTION_UP:
			// 拿起
			float width1 = getWidth() - getStrokeRight() - getStrokeWidth() / 2;
			getParent().requestDisallowInterceptTouchEvent(false);
			if (touchMode == MODE5) {
				if ((event.getX() > width1
						- MyUtils.getInstance().dp2px(getContext(),
								10 + 33 + 10 + 33) && event.getX() < width1
						- MyUtils.getInstance().dp2px(getContext(),
								10 + 33 + 10))
						&& (event.getY() > upChartBottom
								- MyUtils.getInstance().dp2px(getContext(), 38) && event
								.getY() < upChartBottom
								- MyUtils.getInstance().dp2px(getContext(), 5))) {
					jiaAlaph = 255;
					narrowKLine();
					return true;
				}
			}
			if (touchMode == MODE4) {
				if ((event.getX() > width1
						- MyUtils.getInstance().dp2px(getContext(), 10 + 33) && event
						.getX() < width1
						- MyUtils.getInstance().dp2px(getContext(), 10))
						&& (event.getY() > upChartBottom
								- MyUtils.getInstance().dp2px(getContext(), 38) && event
								.getY() < upChartBottom
								- MyUtils.getInstance().dp2px(getContext(), 5))) {
					jianAlaph = 255;
					enlargeKline();
					return true;
				}
			}
			isReleased = true;
			isShowCrossLine = false;
			if (l != null) {
				l.isMove(false);
				if (stickEntity != null && stickEntity.size() > 1)
					l.transferData(OHLCData, stickEntity,
							stickEntity.size() - 1);
			}
			invalidate();
			return true;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 计算十字光标位置
	 * 
	 * @param event
	 */
	@SuppressLint("WrongCall")
	private void calculationCrossLine(MotionEvent event) {
		float startX = getStrokeWidth() + getStrokeLeft();
		int index = 0;
		for (int i = position; i < OHLCData.size() && i < candleNum + position; i++) {
			OHLCEntity entity = OHLCData.get(i);
			// 柱形图
			float openY = upChartBottom
					- ((float) entity.getOpen() - upMinData) * upDataRatio;
			float closeY = upChartBottom
					- ((float) entity.getClose() - upMinData) * upDataRatio;
			// 中线
			// float highY = upChartBottom - (entity.getHigh() - upMinData)
			// * upDataRatio;
			// float lowY = upChartBottom - (entity.getLow() - upMinData)
			// * upDataRatio;

			float endX = getStrokeWidth() + getStrokeLeft()
					+ (dataSpacing * (index + 1) - dataSpacing * 0.25f);

			if ((event.getX() > startX && event.getX() < endX + dataSpacing
					* 0.25f)
					|| (event.getX() > endX && index + 1 == OHLCData.size())) {
				isShowCrossLine = true;
				crossX = (endX - startX) / 2 + startX;
				if (openY < closeY) {// 绿色
					crossY = closeY;
				} else if (openY > closeY) {// 红色
					crossY = closeY;
				} else {// 红色
					crossY = openY;
				}
				leftData = Parse.getInstance().parseFloat(
						Parse.getInstance().parse2String(entity.getClose()));
				if (l != null) {
					l.isMove(true);
					l.transferData(OHLCData, stickEntity, i);
				}
				invalidate();
				return;
			}
			startX = getStrokeWidth() + getStrokeLeft() + dataSpacing
					* (index + 1);
			index++;
		}
	}

	/**
	 * 计算移动距离
	 * 
	 * @param event
	 * @return
	 */
	private float spacing(MotionEvent event) {
		try {
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return (float)Math.sqrt(x * x + y * y);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
		return 0.0f;
	}

	/**
	 * 获取上表与下表间距
	 * 
	 * @return
	 */
	public float getSpacing() {
		return spacing;
	}

	/**
	 * 设置上表与下表间距
	 * 
	 * @param spacing
	 */
	public void setSpacing(float spacing) {
		if (spacing < DEFAULT_TEXT_SIZE && spacing > 0) {
			spacing = DEFAULT_TEXT_SIZE + 5;
		} else if (spacing > DEFAULT_TEXT_SIZE + 5) {
			this.spacing = spacing;
			textSize = spacing - 5;
		} else {
			this.spacing = spacing;
		}
	}

	/**
	 * 获取上表底部与控件顶部距离（是负值）
	 * 
	 * @return
	 */
	public float getUpChartBottom() {
		return upChartBottom;
	}

	/**
	 * 设置数据
	 * 
	 * @param OHLCData
	 */
	public void setData(List<OHLCEntity> OHLCData, List<StickEntity> stickEntity) {
		if (OHLCData == null || stickEntity == null)
			return;
		this.OHLCData = OHLCData;
		this.stickEntity = stickEntity;
		if (this.OHLCData.size() < candleNum) {
			position = 0;
		} else {
			position = this.OHLCData.size() - candleNum;
		}
		invalidate();
	}

	/**
	 * 缩小K线图
	 * 
	 * @return false不可以再缩小了
	 */
	public boolean narrowKLine() {
		if (this.OHLCData == null) {
			jianAlaph = 255;
			return true;
		}
		if (candleNum >= (int) Math.pow(2, 5) * 15) {
			jianAlaph = 130;
			return false;
		}
		// if (OHLCData.size() - position <= candleNum) {
		if (position > 0)
			position -= candleNum;
		if (position < 0)
			position = 0;
		// }
		candleNum *= 2;
		if (candleNum >= (int) Math.pow(2, 5) * 15) {
			candleNum = (int) (Math.pow(2, 5) * 15);
			jianAlaph = 130;
			invalidate();
			return false;
		}
		jianAlaph = 255;
		invalidate();
		return true;
	}

	/**
	 * 放大K线图
	 * 
	 * @return false不可以再放大了
	 */
	public boolean enlargeKline() {
		if (this.OHLCData == null) {
			jiaAlaph = 255;
			return true;
		}
		if (candleNum <= (int) Math.pow(2, 0) * 15) {
			jiaAlaph = 130;
			return false;
		}

		candleNum /= 2;
		if (this.OHLCData.size() - position >= 1 + candleNum) {
			position += candleNum;
			if (this.OHLCData.size() - position <= 1 + candleNum) {
				position = this.OHLCData.size() - candleNum;
			}
		}
		if (candleNum <= (int) Math.pow(2, 0) * 15) {
			candleNum = (int) (Math.pow(2, 0) * 15);
			jiaAlaph = 130;
			invalidate();
			return false;
		}
		jiaAlaph = 255;
		invalidate();
		return true;
	}

	/**
	 * 获取文字大小
	 * 
	 * @return
	 */
	public float getTextSize() {
		return textSize;
	}

	/**
	 * 设置文字大小
	 * 
	 * @param textSize
	 */
	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	/**
	 * 设置接口
	 * 
	 * @param l
	 */
	public void setKLineListener(KLineListener l) {
		this.l = l;
	}

	/**
	 * 接口
	 * 
	 * @author dingrui
	 * 
	 */
	public interface KLineListener {
		public void isMove(boolean isMove);

		public void transferData(List<OHLCEntity> OHLCData,
								 List<StickEntity> stickEntity, int position);
	}
}
package com.mytv365.zb.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import com.mytv365.zb.common.DataCenter;

public class UtilFont {
	/**
	 * 设置字体颜色
	 * 
	 * @param str
	 * @param color
	 * @return
	 */
	public static SpannableStringBuilder setFontStyle(String str, int color) {
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		style.setSpan(new ForegroundColorSpan(color), 0, str.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		return style;
	}
	public static SpannableStringBuilder setSelfStyle(String str) {
		SpannableStringBuilder styles = new SpannableStringBuilder(str);
		return styles;
	}
	/**
	 * 获取字体颜色，红涨，绿跌，白不变
	 * 
	 * @param str
	 * @return
	 */
	public static int getColor(String str) {
		if (str == null)
			return Color.WHITE;
		int color = Color.WHITE;
		if (Double.parseDouble(str) > 0) {
			color = Color.RED;
		} else if (Double.parseDouble(str) < 0) {
			color = Color.GREEN;
		}
		return color;
	}

	/**
	 * 移动位置 （单位1分钟）
	 * 
	 * @param str
	 * @return
	 */
	public static int getTime(String str) {
		int time = -1;
		time = Integer.parseInt(str);
		if (time <= 1500 && time >= 1400)
			time = time - 1400 + 180;
		else if (time <= 1400 && time >= 1300)
			time = time - 1300 + 120;
		else if (time <= 1130 && time >= 1100)
			time = time - 1100 + 90;
		else if (time <= 1100 && time >= 1000)
			time = time - 1000 + 30;
		else if (time <= 959 && time >= 930)
			time = time - 930;
		return time;
	}

	/**
	 * 价格移动位置 （单位0.01元）
	 * 
	 * @param str
	 *            当前价
	 * @return
	 */
	public static float getMoveY(String str) {
		float proceMove;
		float price = Float.parseFloat(str);
		float preclose = Float
				.parseFloat(DataCenter.getInstance().ggxqBean.preclose);// 昨日收盘价
		float tmp = price - preclose;
		proceMove = (tmp * 100);// 当前移动（0.01元）
		return proceMove;
	}

	/**
	 * 获取平均值
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static String getMean(String str1, String str2) {
		String mean;
		float price1 = Float.parseFloat(str1);
		float price2 = Float.parseFloat(str2);
		mean = String.valueOf((price1 + price2) / 2);
		return mean;
	}

}

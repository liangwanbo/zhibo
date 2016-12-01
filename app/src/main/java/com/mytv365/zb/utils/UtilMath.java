package com.mytv365.zb.utils;

import android.util.Log;

public class UtilMath {
	/**
	 * 获取涨幅 获取小数的后两位，加%
	 */
	public static String getIncrease(String value) {
		// Log.i("UtilMath", "===getIncrease：" + value);
		float fValue = Float.parseFloat(value);
		// Log.i("UtilMath", "===fValue：" + fValue);
		value = String.format("%.2f", fValue * 100).toString();
		Log.i("UtilMath", "===value：" + value);
		int pos = value.lastIndexOf(".");
		Log.i("UtilMath", "===pos：" + pos);
		try {
			value = value.substring(0, pos + 3);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		// Log.i("UtilMath", "===value：" + value);
		float zf;
		zf = Parse.getInstance().parseFloat(value);
		if (zf > 0.0f)
			return "+" + value + "%";
		return value + "%";
	}

	/**
	 * 获取涨幅 获取小数的后两位，加%，无＋号
	 */
	public static String getIncrease_(String value) {
		// Log.i("UtilMath", "===getIncrease：" + value);
		float fValue = Float.parseFloat(value);
		// Log.i("UtilMath", "===fValue：" + fValue);
		value = String.format("%.2f", fValue * 100).toString();
		Log.i("UtilMath", "===value：" + value);
		int pos = value.lastIndexOf(".");
		Log.i("UtilMath", "===pos：" + pos);
		try {
			value = value.substring(0, pos + 3);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		// Log.i("UtilMath", "===value：" + value);
		return value + "%";
	}

}

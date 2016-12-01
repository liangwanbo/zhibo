package com.fhrj.library.tools;

import android.util.Log;

/***
 * 日志工具类
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:26:53
 */
public class ToolLog {
	
	private static final String TAG = "ToolLog";
	
	/**
	 * 上线后关闭log
	 */
	private static final Boolean DEBUG = true;
	
	public static void d(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.d(TAG, tag + " : " + msg);
		}
	}

	public static void d(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.d(TAG, tag + " : " + msg, error);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.i(TAG, tag + " : " + msg);
		}
	}

	public static void i(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.i(TAG, tag + " : " + msg, error);
		}
	}

	public static void w(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.w(TAG, tag + " : " + msg);
		}
	}

	public static void w(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.w(TAG, tag + " : " + msg, error);
		}
	}

	public static void e(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.e(TAG, tag + " : " + msg);
		}
	}

	public static void e(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.e(TAG, tag + " : " + msg, error);
		}
	}
}

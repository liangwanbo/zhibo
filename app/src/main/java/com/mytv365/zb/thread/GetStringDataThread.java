package com.mytv365.zb.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mytv365.zb.utils.GetApiData;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.SaveListObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GetStringDataThread extends Thread {

	private Context context;
	private String uri;
	private Handler handler;
	private int msgWhat_OK;
	private int msgWhat_TIME_OUT;
	private int msgWhat_DATA_EXCEPTION;
	private boolean isCache;// 是否缓存,默认为true

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param uri
	 *            获取数据的地址
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK
	 *            成功消息代码，如果不发消息传:-1
	 * @param msgWhat_TIME_OUT
	 *            获取数据超时代码，如果不发消息传:-1
	 * @param msgWhat_DATA_EXCEPTION
	 *            数据解析异常代码，如果不发消息传:-1
	 */
	public GetStringDataThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.isCache = true;
	}

	/**
	 * 构造函数(带是否缓存的参数，true为缓存，false为不缓存）
	 * 
	 * @param context
	 *            上下文
	 * @param uri
	 *            获取数据的地址
	 * @param handler
	 *            消息队列
	 * @param msgWhat_OK
	 *            成功消息代码，如果不发消息传:-1
	 * @param msgWhat_TIME_OUT
	 *            获取数据超时代码，如果不发消息传:-1
	 * @param msgWhat_DATA_EXCEPTION
	 *            数据解析异常代码，如果不发消息传:-1
	 * @param isCache
	 *            是否缓存
	 */
	public GetStringDataThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			boolean isCache) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.isCache = isCache;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		getData(context, uri, handler, msgWhat_OK, msgWhat_TIME_OUT,
				msgWhat_DATA_EXCEPTION, isCache);
	}

	/**
	 * 获取数据的方法
	 * 
	 * @param context
	 * @param uri
	 * @param handler
	 * @param msgWhat_OK
	 * @param msgWhat_TIME_OUT
	 * @param msgWhat_DATA_EXCEPTION
	 * @param isCache
	 * @param
	 */
	private void getData(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			boolean isCache) {
		File file = null;
		if (isCache)
			file = MyUtils.getInstance().getCache(context, "/data", uri, true);
		String data = null;
		if (MyUtils.getInstance().isNetworkConnected(context)) {
			InputStream in = GetApiData.getInstance().getData(uri);
			if (in == null) {
				if (!isCache) {
					sendEmptyMessage(msgWhat_TIME_OUT, null);
					return;
				}
				data = (String) SaveListObject.getInstance().openObject(file);
				if (data != null) {
					sendEmptyMessage(msgWhat_OK, data);
				} else {
					System.out.println("数据集合空");
					sendEmptyMessage(msgWhat_TIME_OUT, data);
				}
				return;
			}
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int i = -1;
				while ((i = in.read()) != -1) {
					baos.write(i);
				}
				data = baos.toString();

				in.close();
				if (isCache)
					SaveListObject.getInstance().saveObject(file, data);
				sendEmptyMessage(msgWhat_OK, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Log.e("Exception", "获取数据时，流处理异常");
				sendEmptyMessage(msgWhat_DATA_EXCEPTION, null);
			}

		} else {
			if (!isCache) {
				sendEmptyMessage(msgWhat_TIME_OUT, null);
				return;
			}
			data = (String) SaveListObject.getInstance().openObject(file);
			if (data != null) {
				sendEmptyMessage(msgWhat_OK, data);
			} else {
				sendEmptyMessage(msgWhat_TIME_OUT, null);
			}
		}

	}

	/**
	 * 发消息
	 */
	private void sendEmptyMessage(int emptyMessage, String data) {
		if (emptyMessage != -1) {
			if (handler != null) {
				Message msg = handler.obtainMessage();
				msg.what = emptyMessage;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		}
	}
}

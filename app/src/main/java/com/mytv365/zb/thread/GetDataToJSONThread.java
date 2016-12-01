package com.mytv365.zb.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Resolve;
import com.mytv365.zb.utils.SaveListObject;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

public class GetDataToJSONThread extends Thread {

	private Context context;
	private String uri;
	private Handler handler;
	private int msgWhat_OK;
	private int msgWhat_TIME_OUT;
	private int msgWhat_DATA_EXCEPTION;
	private OnUpdateData onUpdateData;
	private boolean isCache;// 是否缓存,默认为true

	private static GetDataToJSONThread getDataThread;

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
	public GetDataToJSONThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = null;
		this.isCache = true;
	}

	public GetDataToJSONThread(Context context, String uri, Handler handler,
							   int msgWhat_OK, int msgWhat_TIME_OUT) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.onUpdateData = null;
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
	public GetDataToJSONThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			boolean isCache) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = null;
		this.isCache = isCache;
	}

	/**
	 * 带更新解析下来的数据的构造函数(带是否缓存的参数，true为缓存，false为不缓存）
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
	 * @param onUpdateData
	 *            更新数据的接口
	 * @param isCache
	 *            是否缓存
	 */
	public GetDataToJSONThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			OnUpdateData onUpdateData, boolean isCache) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = onUpdateData;
		this.isCache = isCache;
	}

	/**
	 * 带更新解析下来的数据的构造函数
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
	 * @param onUpdateData
	 *            更新数据的接口
	 */
	public GetDataToJSONThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			OnUpdateData onUpdateData) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		this.msgWhat_DATA_EXCEPTION = msgWhat_DATA_EXCEPTION;
		this.onUpdateData = onUpdateData;
		this.isCache = true;
	}

	/**
	 * 公共方法(不带缓存）
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
	public static boolean startThread(Context context, String uri,
			Handler handler, int msgWhat_OK, int msgWhat_TIME_OUT,
			int msgWhat_DATA_EXCEPTION, boolean isRealmName) {
		if (getDataThread == null) {
			getDataThread = new GetDataToJSONThread(context, uri, handler,
					msgWhat_OK, msgWhat_TIME_OUT, msgWhat_DATA_EXCEPTION, false);
		} else {
			if (!getDataThread.isAlive()) {
				getDataThread = new GetDataToJSONThread(context, uri, handler,
						msgWhat_OK, msgWhat_TIME_OUT, msgWhat_DATA_EXCEPTION,
						isRealmName);
			} else {
				System.out.println("上个线程在活动");
				return false;
			}
		}
		getDataThread.start();
		return true;
	}

	/**
	 * 带更新解析下来的数据的公共方法
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
	 * @param onUpdateData
	 *            更新数据的接口
	 */
	public static boolean startThread(Context context, String uri,
			Handler handler, int msgWhat_OK, int msgWhat_TIME_OUT,
			int msgWhat_DATA_EXCEPTION, OnUpdateData onUpdateData,
			boolean isRealmName) {
		if (getDataThread == null) {
			getDataThread = new GetDataToJSONThread(context, uri, handler,
					msgWhat_OK, msgWhat_TIME_OUT, msgWhat_DATA_EXCEPTION,
					onUpdateData, isRealmName);
		} else {
			if (!getDataThread.isAlive()) {
				getDataThread = new GetDataToJSONThread(context, uri, handler,
						msgWhat_OK, msgWhat_TIME_OUT, msgWhat_DATA_EXCEPTION,
						onUpdateData, isRealmName);
			} else {
				return false;
			}
		}
		getDataThread.start();
		return true;
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
	 */
	private	File file = null;
	private	String data =null;
	private Object obj = null;
	private void getData(Context context, String uri, Handler handler,
			final int msgWhat_OK, final int msgWhat_TIME_OUT, int msgWhat_DATA_EXCEPTION,
			final boolean isCache) {
		if (isCache)
			file = MyUtils.getInstance().getCache(context, "/dataJSON", uri,
					true);

		if (MyUtils.getInstance().isNetworkConnected(context)) {
//			InputStream in = GetApiData.getInstance().getData(uri);
				OkGo.get(uri)
						.headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
						.headers("header1", "headerValue1")
						.setCertificates()
						.execute(new StringCallback() {
							@Override
							public void onSuccess(String s, Call call, Response response) {
								Log.i("test","add--"+s);
								data =s;

								if (data == null) {
									if (!isCache) {
										sendEmptyMessage(msgWhat_TIME_OUT, null);
										return;
									}
									obj = SaveListObject.getInstance().openObject(file);
									if (obj != null) {
										if (onUpdateData != null) {
											if (obj != null)
												onUpdateData.updateCacheData(obj);
										}
										sendEmptyMessage(msgWhat_OK, obj);
									} else {
										System.out.println("数据集合空");
										sendEmptyMessage(msgWhat_TIME_OUT, null);
									}
									return;
								}
								obj = Resolve.getInstance().json(data);
								if (onUpdateData != null) {
									if (obj != null)
										onUpdateData.updateNetworkData(obj);
								}
								if (isCache)
									SaveListObject.getInstance().saveObject(file, obj);
								sendEmptyMessage(msgWhat_OK, obj);

							}
							@Override
							public void onError(Call call, Response response, Exception e) {
								super.onError(call, response, e);
							}
						});

		} else {
			if (!isCache) {
				sendEmptyMessage(msgWhat_TIME_OUT, null);
				return;
			}
			obj = SaveListObject.getInstance().openObject(file);
			if (obj != null) {
				if (onUpdateData != null) {
					if (obj != null)
						onUpdateData.updateCacheData(obj);
				}
				sendEmptyMessage(msgWhat_OK, obj);
			} else {
				sendEmptyMessage(msgWhat_TIME_OUT, null);
			}
		}

	}

	/**
	 * 发消息
	 */
	private void sendEmptyMessage(int emptyMessage, Object obj) {
		if (emptyMessage != -1) {
			Message msg = handler.obtainMessage();
			msg.what = emptyMessage;
			msg.obj = obj;
			if (handler != null) {
				handler.sendMessage(msg);
			}
		}
	}

	/**
	 * 更新解析下来的数据，比如：插入数据，移除数据
	 * 
	 * @author 锐
	 * 
	 */
	public interface OnUpdateData {
		/** 更新网络数据 */
		public void updateNetworkData(Object obj);

		/** 更新缓存数据 */
		public void updateCacheData(Object obj);
	}


}

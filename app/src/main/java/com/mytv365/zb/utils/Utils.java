package com.mytv365.zb.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	// 在百度开发者中心查询应用的API Key
	// public static final String API_KEY =
	// "uZbmgZKhfumvGYGowcjSPFc1";//"GkWwrvZrCaMQfCZ190ujndZm";
	public static final String TAG = "PushDemoActivity";
	public static final String RESPONSE_METHOD = "method";
	public static final String RESPONSE_CONTENT = "content";
	public static final String RESPONSE_ERRCODE = "errcode";
	public static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
	public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
	public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
	public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
	public static final String EXTRA_ACCESS_TOKEN = "access_token";
	public static final String EXTRA_MESSAGE = "message";
	public static final int MSG_WARNING = 0;
	public static final int MSG_SUCCESS = 1;
	public static final int MSG_ERROR = 2;


	// 获取AppKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	/**
	 * toast的展现
	 * 
	 * @param thisActivity
	 * @param msg
	 */
	public static void showMessageToast(Context thisActivity, String msg) {
	}

	/**
	 * dialog的展现
	 * 
	 * @param thisActivity
	 * @param msg
	 *//*

	public static void showMessage(final Activity thisActivity, String msg) {
		final View views = Utils.showMsgBox(thisActivity, "提示", msg, true);
		Button btn_conflrm = (Button) views.findViewById(R.id.btn_conflrm);
		btn_conflrm.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Utils.hideMsgBox(thisActivity, views);
			}
		});
		Button btn_cancel = (Button) views.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Utils.hideMsgBox(thisActivity, views);
			}
		});
	}
	
	public static void showMessage(final Activity thisActivity,int MSG_TYPE, String msg) {
		if(MSG_TYPE == MSG_WARNING){
			new SweetAlertDialog(thisActivity, SweetAlertDialog.WARNING_TYPE)
			.setTitleText("提示").setContentText(msg)
			.setConfirmText("确定").show();
		}else if(MSG_TYPE == MSG_SUCCESS){
			new SweetAlertDialog(thisActivity, SweetAlertDialog.SUCCESS_TYPE)
			.setTitleText("提示").setContentText(msg)
			.setConfirmText("确定").show();
		}else if(MSG_TYPE == MSG_ERROR){
			new SweetAlertDialog(thisActivity, SweetAlertDialog.ERROR_TYPE)
			.setTitleText("提示").setContentText(msg)
			.setConfirmText("确定").show();
		}
	}*/
	
	/**
	 * 判断当前是否连接网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info != null && info.isConnectedOrConnecting();
	}
	
	/**
	 * 判断网络类型
	 * 0 没有网络 
	 * 1 wap or net
	 * 2 wifi
	 * */
	public static int getNetworkType(Context context){
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=cm.getActiveNetworkInfo();
		if(networkInfo==null){
			return 0;
		}
		int type=networkInfo.getType();
		if(type==ConnectivityManager.TYPE_MOBILE){
			return 1;
		}else if(type==ConnectivityManager.TYPE_WIFI){
			return 2;
		}
		return 0;
	}

	public static void showNetWorkErr(Context context) {
		Utils.showMessageToast(context, "当前操作需要网络支持请打开wifi或移动数据连接");
	}
/*
	*//**
	 * 网络请求数据失败时要显示的数据
	 * 
	 * @param thisActivity
	 * @param isIndex
	 *//*
	public static void showNetImg(Activity thisActivity, boolean isIndex) {
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		View layout;
		if (isIndex) {
			layout = inflater.inflate(R.layout.networkerr, null);
		} else {
			layout = inflater.inflate(R.layout.networkerr2, null);
		}
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		thisActivity.addContentView(layout, params);
	}*/

	/**
	 * 加载到空内容时的提示
	 * 
	 * @param thisActivity
	 * @param isIndex
	 *//*
	public static View showEmptyImg(Activity thisActivity, boolean isfirst) {
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		View layout;
		layout = inflater.inflate(R.layout.empty_content, null);
		return layout;

	}*/
/*

	*/
/**
	 * 展示加载页面
	 * 
	 * @param thisActivity
	 * @return
	 *//*

	public static View showWait(Activity thisActivity) {
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		View layout = inflater.inflate(R.layout.wait_layout, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		thisActivity.addContentView(layout, params);

		return layout;
	}
*/

	/**
	 * 展示加载页面
	 * 
	 * @param thisActivity
	 * @return
	 *//*
	public static View showWaitWithMsg(Activity thisActivity, String msg) {
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		View layout = inflater.inflate(R.layout.wait_layout, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		TextView side_cell_title = (TextView) layout
				.findViewById(R.id.side_cell_title);
		side_cell_title.setText(msg);

		thisActivity.addContentView(layout, params);

		return layout;
	}*/

	/**
	 * 隐藏加载页面
	 * 
	 * @param thisActivity
	 * @param view
	 *//*
	public static void hideWait(Activity thisActivity, View view) {
		if (view != null) {
			View loadingLayout = thisActivity.findViewById(R.id.loadingLayout);
			ViewGroup pr = (ViewGroup) view.getParent();
			if (loadingLayout != null && pr != null) {
				pr.removeView(loadingLayout);
			}

		}
	}*/

	/**
	 * 显示msgbox
	 * 
	 * @param thisActivity
	 * @param title
	 * @param msg
	 * @return
	 *//*

	public static View showMsgBox(Activity thisActivity, String title,
			String msg, boolean isOk) {
		LayoutInflater inflater = LayoutInflater.from(thisActivity);
		View layout = null;
		if (isOk) {
			layout = inflater.inflate(R.layout.confirm_box_ok, null);
		} else {
			layout = inflater.inflate(R.layout.confirm_box, null);
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		thisActivity.addContentView(layout, params);

		RelativeLayout confirm_box_lay = (RelativeLayout) thisActivity
				.findViewById(R.id.confirm_box_lay);
		confirm_box_lay.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {

			}
		});

		TextView text_c_title = (TextView) thisActivity
				.findViewById(R.id.text_c_title);
		TextView text_c_msg = (TextView) thisActivity
				.findViewById(R.id.text_c_msg);
		text_c_title.setText(title);
		text_c_msg.setText(msg);

		return layout;
	}*/

/*	*//**
	 * 隐藏提示框
	 * 
	 * @param thisActivity
	 * @param view
	 *//*
	public static void hideMsgBox(Activity thisActivity, View view) {
		if (view != null) {
			View loadingLayout = thisActivity.findViewById(R.id.confirmLayout);
			ViewGroup pr = (ViewGroup) view.getParent();
			if (loadingLayout != null && pr != null) {
				pr.removeView(loadingLayout);
			}

		}
	}*/

	/**
	 * 保存指定的图片
	 * 
	 * @param bitName
	 * @param mBitmap
	 * @param thisActivity
	 * @return
	 * @throws IOException
	 */
	public static String saveMyBitmap(String bitName, Bitmap mBitmap,
			Activity thisActivity) throws IOException {
		String PATH = Environment.getExternalStorageDirectory() + "/91tiqiu/";
		File files = new File(PATH);
		if (!files.exists()) {
			files.mkdirs();
		}
		String url = PATH + bitName;
		File f = new File(url);
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	
	
	
	
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMddHHmmss");
		return dateFormat.format(date) + getRandomString(5);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 时间格式化(*时/分/秒 前)
	 * 
	 * @param timeStr
	 * @return
	 */
	public static String getStandardDate(String timeStr) {

		StringBuffer sb = new StringBuffer();

		if (!isNum(timeStr)) {
			return "";
		}

		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - (t * 1000);
		long mill = (long) Math.ceil(time / 1000);// 秒前

		long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

		long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

		long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

		if (day - 1 > 0) {
			sb.append(getStrTime(timeStr));
		} else if (hour - 1 > 0) {
			if (hour >= 12) {
				sb.append(getStrTime(timeStr));
			} else {
				sb.append(hour + "小时前");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时前");
			} else {
				sb.append(minute + "分钟前");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟前");
			} else {
				sb.append(mill + "秒前");
			}
		} else {
			sb.append("刚刚");
		}
		return sb.toString();
	}
	
	/**
	 * 将时间格式化成HH:mm 类型
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTimeHHMM(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/**
	 * 将时间格式化成 yyyy-MM-dd HH:mm 类型
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTimeYear(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/**
	 * 将时间格式化成 yy-MM-dd HH:mm:ss 类型
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTimeLastResh(long cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		re_StrTime = sdf.format(new Date(cc_time));
		return re_StrTime;
	}

	/**
	 * 将时间格式化成 yy-MM-dd类型
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTimeYearAndDay(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long lcc_time = Long.parseLong(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/**
	 * 将时间格式化成 MM-dd HH:mm 类型
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	/**
	 * 将时间格式化成 MM-dd 类型
	 * 
	 * @param cc_time
	 * @return
	 */
	public static String getStrTimeHour(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}

	public static String getSinaKey() {
		return "60125097";
	}

	/**
	 * 比较当前时间与指定时间的大小(2009-9-2)
	 * 
	 * @throws ParseException
	 */
	public static boolean isBigThanNow(String s) throws ParseException {
		SimpleDateFormat sformart = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sformart.parse(s);
		return date1.getTime() > System.currentTimeMillis();

	}

	/**
	 * 将时间转换成相对现在n小时的形式
	 * 
	 * @param i
	 * @return
	 */
	public static String parseTimeToHourFromNow(long i) {
		long currentTimeMillis = System.currentTimeMillis();
		String str = "";
		long hour = (currentTimeMillis - i * 1000L) / 3600000;
		long minute = ((currentTimeMillis - i * 1000L) / 60000);
		long second = ((currentTimeMillis - i * 1000L) / 1000);
		if (hour >= 12) {
			str = getStrTime(i + "");
		} else if (hour >= 1) {
			str = hour + "小时以前";
		} else if (minute >= 1) {
			str = minute + "分钟前";
		} else if (second >= 1) {
			str = second + "秒前";
		} else {
			str = "刚刚";
		}
		return str;

	}
	
	/**
	 * 将时间转换成时间戳
	 * */
	public static long getTimeStamp(String time){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		long timeStemp = 0;
		try {
			Date date=format.parse(time);
			timeStemp =date.getTime();
			System.out.println("时间戳：   "+timeStemp);
		} catch (ParseException e) {
//			e.printStackTrace();
		}
		String str=String.valueOf(timeStemp);
		str=str.substring(0, 10);
		System.out.println("处理后的时间戳：   "+str);
		return Long.parseLong(str);
	}
	
	/**
	 * 将时间转换成时间戳
	 * */
	public static long getTimeStampToYear(String time){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		long timeStemp = 0;
		try {
			Date date=format.parse(time);
			timeStemp =date.getTime();
		} catch (ParseException e) {
//			e.printStackTrace();
		}
		String str=String.valueOf(timeStemp);
		str=str.substring(0, 10);
		System.out.println("处理后的时间戳：   "+str);
		return Long.parseLong(str);
	}

	/**
	 * activity 间的跳转
	 * 
	 * @param A
	 * @param B
	 */
	public static void goToActivity(Activity A, Class B) {
		Intent intent = new Intent(A, B);
		A.startActivity(intent);
	}

	/**
	 * activity的跳转及数据的传递
	 * 
	 * @param A
	 * @param B
	 * @param names
	 * @param values
	 */
	public static void goToActivity(Activity A, Class B, String names,
			String values) {
		Intent intent = new Intent(A, B);
		intent.putExtra(names, values);
		A.startActivity(intent);
	}

/*	*//**
	 * 按返回时的退出操作
	 *
	 * @param activity
	 *//*
	public static void onBackPressed(final Activity activity) {
		new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("退出提示")
				.setContentText("真的要狠心离开吗？")
				.setCancelText("取消")
				.setConfirmText("退出")
				.showCancelButton(true)
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {

							@Override
							public void onClick(
									SweetAlertDialog sweetAlertDialog) {
								sweetAlertDialog.dismiss();
							}
						})
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {

							@Override
							public void onClick(
									SweetAlertDialog sweetAlertDialog) {
								MobclickAgent.onKillProcess(activity);
								System.exit(0);

							}
						}).show();
	}*/

	/**
	 * 将Uri转换成url
	 * 
	 * @param uri
	 * @param act
	 * @return
	 */
	public static String uri2url(Uri uri, Activity act) {

		String[] proj = { MediaStore.Images.Media.DATA };

		Cursor actualimagecursor = act
				.managedQuery(uri, proj, null, null, null);

		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor
				.getString(actual_image_column_index);

		File file = new File(img_path);
		return file.getAbsolutePath();
	}

	/**
	 * 判断是否登录
	 * 
	 * @param
	 * @return
	public static Boolean isLogin(Context mContext) {
		DataSave dataSave = new DataSave(mContext);
		String key = dataSave.Load_String("key");
		if (key.length() > 0 && !key.equals("none")) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean isLogin(Activity mActivity) {
		DataSave dataSave = new DataSave(mActivity);
		String key = dataSave.Load_String("key");
		if (key.length() > 0 && !key.equals("none")) {
			return true;
		} else {
			return false;
		}
	}*/

	public static void coloseInputSoft(Activity act) {
		if (act.getCurrentFocus() != null) {
			((InputMethodManager) act
					.getSystemService(act.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(act.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 判断是否是pad(>7就认为是pad)
	 * 
	 * @param mActivity
	 * @return
	 */
	public static int isPad(Activity mActivity) {
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2)
				+ Math.pow(dm.heightPixels, 2));
		double screenSize = diagonalPixels / (160 * dm.density);
		// Log.e("screenSize",screenSize+"");
		if (screenSize >= 7) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 判断指定app是否运行
	 * 
	 * @param mActivity
	 * @param name
	 * @return
	 */
	public static boolean isWorked(Context mActivity, String name) {
		ActivityManager myManager = (ActivityManager) mActivity
				.getSystemService(Context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager
				.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString()
					.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取系统属性
	 * 
	 * @param propName
	 * @return
	 */
	public static String getSystemProperty(String propName) {
		String line;
		BufferedReader input = null;
		try {
			Process p = Runtime.getRuntime().exec("getprop " + propName);
			input = new BufferedReader(
					new InputStreamReader(p.getInputStream()), 1024);
			line = input.readLine();
			input.close();
		} catch (IOException ex) {
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return line;
	}

	/**
	 * 是否是miui
	 * 
	 * @return
	 */
	public static boolean isMiui() {
		String ver = getSystemProperty("ro.miui.ui.version.name");
		if (ver.length() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*// imageload图片加载
	public static void asyncImageLoad(String url, ImageView imageView,
			final ProgressBar waiting_bar,Context context) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(null)
				.showImageForEmptyUri(R.drawable.no_pic)
				.showImageOnFail(R.drawable.no_pic).cacheInMemory(true)
				.cacheOnDisk(true).displayer(new SimpleBitmapDisplayer())
				.build();
		imageLoader.displayImage(url, imageView, options,
				new ImageLoadingListener() {
					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						// TODO 自动生成的方法存根
						if (waiting_bar != null) {
							waiting_bar.setVisibility(View.GONE);
						}
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						// TODO 自动生成的方法存根
						if (waiting_bar != null) {
							waiting_bar.setVisibility(View.GONE);
						}
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						// TODO 自动生成的方法存根
						if (waiting_bar != null) {
							waiting_bar.setVisibility(View.GONE);
						}
					}

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						// TODO 自动生成的方法存根

					}
				});
	}*/

	/*
	 * 将int类型转换成时间(mm:ss)
	 */
	public static String changeInt2Time(int t) {
		int s, min = 0;
		String minutes, seconds;
		s = t / 1000;
		if (s >= 60) {
			min = s / 60;
			s = s % 60;
		}
		// ⒎置朕D樽址串
		if (s < 10) {
			seconds = "0" + s;
		} else {
			seconds = s + "";
		}
		if (min < 10) {
			minutes = "0" + min;
		} else {
			minutes = min + "";
		}
		String time = minutes + ":" + seconds;

		if (min > 1000) {
			time = "00:00";
		}

		return time;
	}
	
	
	
	/**
	 * 将时间(mm:ss)转换成int类型
	 * 
	 * @param t
	 * @return
	 */
	public static int changeTime2Int(String t) {
		String list[] = t.split(":");
		int s, min, time;
		min = Integer.parseInt(list[0]);
		s = Integer.parseInt(list[1]);

		s = min * 60 + s;
		time = s * 1000;

		return time;
	}

	/**
	 * int类型数据格式化成至少两位的字符串
	 * 
	 * @param i
	 * @return
	 */
	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	/**
	 * 获取音乐缓存的路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getMusicPath(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory() + "/ibabyzone/";

		} else {
			return context.getFilesDir().toString() + "/ibabyzone/";
		}
	}

	/**
	 * 判断该路径是否存在
	 * 
	 * @param musicFile
	 * @return
	 */
	public static boolean fileIsExists(String musicFile) {
		File f = new File(musicFile);
		if (!f.exists()) {
			return false;
		}
		return true;
	}

	/**
	 * 删除指定的目录
	 * 
	 * @param musicFile
	 */
	public static void delFile(String musicFile) {
		File f = new File(musicFile);
		f.delete();
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFileName
	 * @param destDirName
	 * @return
	 */
	public static boolean moveFile(String srcFileName, String destDirName) {
		File srcFile = new File(srcFileName);
		if (!srcFile.exists() || !srcFile.isFile())
			return false;
		/*
		 * <<<<<<< .mine try { int bytesum = 0; int byteread = 0; File oldfile =
		 * new File(srcFileName); if (oldfile.exists()) { // 文件存在时 InputStream
		 * inStream = new FileInputStream(srcFileName); // 读入原文件
		 * FileOutputStream fs = new FileOutputStream(destDirName); byte[]
		 * buffer = new byte[1444]; int length; while ((byteread =
		 * inStream.read(buffer)) != -1) { bytesum += byteread; // 字节数 文件大小 //
		 * System.out.println(bytesum); fs.write(buffer, 0, byteread); }
		 * inStream.close(); } } catch (Exception e) { return false;
		 * 
		 * } return true; =======
		 */
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(srcFileName);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(srcFileName); // 读入原文件
				FileOutputStream fs = new FileOutputStream(destDirName);
				byte[] buffer = new byte[1444];
				int i = 0;
				while ((byteread = inStream.read(buffer)) != -1) {
					if (i == 0) {
						buffer[0] = 0x57;
						buffer[1] = 0x41;
						buffer[2] = 0x1a;
						buffer[3] = 0x2b;
					}
					bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
					i++;
				}
				inStream.close();
			}
		} catch (Exception e) {
			return false;

		}
		return true;
		// >>>>>>> .r582
	}

	/**
	 * 判读当前网络是否是否连接wifi
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isWifi(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	public static JSONArray joinJSONArray(JSONArray mData, JSONArray array) {
		StringBuffer buffer = new StringBuffer();
		try {
			int len = mData.length();
			for (int i = 0; i < len; i++) {
				JSONObject obj1 = (JSONObject) mData.get(i);
				if (i == len - 1)
					buffer.append(obj1.toString());
				else
					buffer.append(obj1.toString()).append(",");
			}
			len = array.length();
			if (len > 0)
				buffer.append(",");
			for (int i = 0; i < len; i++) {
				JSONObject obj1 = (JSONObject) array.get(i);
				if (i == len - 1)
					buffer.append(obj1.toString());
				else
					buffer.append(obj1.toString()).append(",");
			}
			buffer.insert(0, "[").append("]");
			return new JSONArray(buffer.toString());
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 根据类型跳转不同的Activity
	 * 
	 * @param thisActivity
	 *            当前的Activity必传
	 * @param from
	 *            跳转类型必传
	 * @param aid
	 *            主ID，但凡有主ID的都需要传
	 * @param json
	 *            需要传复杂数据的时候传递，可以为null
	 */

	public static void checkGoto(final Activity thisActivity, String from,
			final String aid, JSONObject json) {
		if (from == null) {
			Utils.showMessageToast(thisActivity, "缺少跳转类型");
			return;
		}

	}

	public static void urlGOTO(Activity thisActivity, WebView webview,
			String url, String aid, String myUrl) {

	}

	public static String Encode64(String str) {
		byte[] encode = null;
		try {
			encode = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bas = null;
		try {
			bas = new String(Base64.encode(encode, 0, encode.length,
					Base64.DEFAULT), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bas;
	}

	public static String Decode64(String str) {
		byte[] encode = null;
		try {
			encode = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bas = null;
		try {
			bas = new String(Base64.decode(encode, 0, encode.length,
					Base64.DEFAULT), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bas;
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String encode(String str) {
		byte[] encode = null;
		try {
			encode = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bas = null;
		try {
			bas = new String(Base64.encode(encode, 0, encode.length,
					Base64.DEFAULT), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bas;
	}

	public static String decode(String str) {
		byte[] encode = null;
		try {
			encode = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bas = null;
		try {
			bas = new String(Base64.decode(encode, 0, encode.length,
					Base64.DEFAULT), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bas;
	}

	/**
	 * 将指定的图片保存在指定的路径下
	 * */
	public  static void savePhotoToSDCard(String path, String photoName,
			Bitmap photoBitmap) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}

			File photoFile = new File(path, photoName); // 在指定路径下创建文件
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);

				if (photoBitmap != null) {

					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,

					fileOutputStream)) {

						fileOutputStream.flush();
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
	/**
	 * 手机号验证
	 * */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 邮箱验证
	 * */
	public static boolean isEmailNO(String email) {
		Pattern p = Pattern
				.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 中文验证
	 * */
	public static boolean isChineseName(String name) {
		Pattern p = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]){2,5}$");
		Matcher m = p.matcher(name);
		return m.matches();
	}
	
	/**
	 * 将uri转换为string
	 * */
	public static String getRealFilePath(Context context, Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}
}

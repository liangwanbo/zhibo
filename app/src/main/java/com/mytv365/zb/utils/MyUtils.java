package com.mytv365.zb.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {

	public static MyUtils utils;

	private MyUtils() {

	}

	public static MyUtils getInstance() {
		if (utils == null)
			utils = new MyUtils();
		return utils;
	}

	/**
	 * 判断网络是否连接，返回false为没有任何连接
	 * 
	 * @param context
	 *            上下文
	 * @return boolean
	 */
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * make true current connect service is wifi
	 * 
	 * @param mContext
	 * @return
	 */
	public static boolean isWifiNetwork(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	private boolean isGpsEnable(Context mContext) {
		LocationManager locationManager = ((LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE));
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * MD5加密，32位
	 * 
	 * @param url
	 *            需加密的字符串
	 * @return 加密后的字符串
	 */
	public String MD5(String url) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			// e.printStackTrace();
			return getFile(url);
		}
		char[] charArray = url.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 可逆的加密算法
	 */
	public String encryptmd5(String str) {
		char[] a = str.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 'l');
		}

		return new String(a);
	}

	/**
	 * 将URL转成能够识别的目录
	 */
	public String getFile(String url) {
		String path = url;
		if (path.contains("?")) {
			path = path.replace("?", "_");
		}
		if (path.contains("/")) {
			path = path.replace("/", "_");
		}
		if (path.contains(".")) {
			path = path.replace(".", "_");
		}
		return path;
	}

	/**
	 * 获取缓存目录中的自定义文件路径
	 * 
	 * @param context
	 *            上下文
	 * @param path
	 *            二级目录，三级目录中间用"/"分隔，前后不需要加"/"
	 * @param fileName
	 *            文件名，自动转成md5的名字
	 * @param isMD5
	 *            文件名是否加密
	 * @return File
	 */
	public File getCache(Context context, String path, String fileName,
			boolean isMD5) {
		File file = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file = new File(context.getExternalCacheDir().getPath() + "/"
					+ path);
		} else {
			file = new File(context.getCacheDir() + "/" + path);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		if (isMD5)
			file = new File(file.getPath() + "/" + MD5(fileName));
		else
			file = new File(file.getPath() + "/" + fileName);
		return file;
	}

	/**
	 * 获取缓存目录中的自定义目录
	 * 
	 * @param context
	 *            上下文
	 * @param path
	 *            自定义目录
	 * @return File
	 */
	public File getCache(Context context, String path) {
		File file = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file = new File(context.getExternalCacheDir().getPath() + "/"
					+ path);
		} else {
			file = new File(context.getCacheDir() + "/" + path);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 获取内部版本号
	 * 
	 * @param context
	 *            上下文
	 * @return String
	 */
	public String getVersionCode(Context context) {
		String versionCode = null;
		try {
			PackageInfo pi = context.getPackageManager()
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_CONFIGURATIONS);
			versionCode = String.valueOf(pi.versionCode);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取外部版本名称
	 * 
	 * @param context
	 *            上下文
	 * @return String 版本名称
	 */
	public String getVersionName(Context context) {
		String versionName = null;
		try {
			PackageInfo pi = context.getPackageManager()
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_CONFIGURATIONS);
			versionName = pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 获取设备号（IMEI）
	 * 
	 * @param context
	 *            上下文
	 * @return String 设备号
	 */
	public String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String dwc = null;
		try {
			dwc = tm.getDeviceId();
		} catch (Exception e) {
			// TODO: handle exception
			dwc = getSN(context);
		}
		return dwc;
	}

	/**
	 * 获取设备序列号
	 * 
	 * @param context
	 *            上下文
	 * @return String
	 */
	public String getSN(Context context) {
		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * 获取GUID
	 * 
	 * @return String
	 */
	public String GetGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 
	 * 转换时间格式
	 * 
	 * @param format
	 *            传入的time的格式，例："yyyy/MM/dd HH:mm:ss"
	 * @param time
	 *            时间
	 * @param isTime
	 *            true为返回时:分:秒，false为返回年-月-日
	 * @return String
	 */
	@SuppressLint("DefaultLocale")
	public String parseTime(String format, String time, boolean isTime) {
		if (!"".equals(time)) {
			Date date = stringToDate(format, time);
			if (date == null)
				return time;
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int years = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int min = calendar.get(Calendar.MINUTE);
			int ss = calendar.get(Calendar.SECOND);

			if (isTime) {
				return String.format("%02d:%02d:%02d", hour, min, ss);
			}
			return String.format("%d-%02d-%02d", years, month, day);
		}
		return time;
	}

	/**
	 * String类型的时间转Date
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public Date stringToDate(String format, String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	/**
	 * 时间戳转换成时间字符窜
	 * 
	 * @param format
	 *            时间格式
	 * @param time
	 *            时间戳
	 * @return
	 */
	public String date2String(String format, long time) {
		Date d = new Date(time);
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(d);
	}

	/**
	 * 将时间字符串转为时间戳
	 * 
	 * @param format
	 *            时间格式
	 * @param time
	 *            时间
	 * @return
	 */
	public long string2Date(String format, String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Log.e("error", "将字符串转换为时间戳异常");
			return 0;
		}
	}

	/**
	 * 字符串转化为时间时间戳
	 * @author Administrator
	 *
	 * @param format1
	 *         时间格式       
	 * @param format2
	 *         时间格式
	 * @param time
	 *          时间
	 */
	public String  string2Dates(String format1,String format2, String time){
	    SimpleDateFormat formatter1=new SimpleDateFormat(format1);  
	    SimpleDateFormat formatter2=new SimpleDateFormat(format2);   
	    try {
			time=formatter1.format(formatter2.parse(time));
			return time;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Log.e("error", "string2Dates--将字符串转换为时间戳异常");
			return null;
		}
		
	}
	/**
	 * 判断是否月份中的第一周
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public boolean dayOfWeekInMonth(String format, String date) {
		Date d = stringToDate(format, date);
		if (d == null)
			return false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		if (calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断某个服务是否正在运行的方法
	 * 
	 * @param mContext
	 * @param serviceName
	 *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
	 * @return true代表正在运行，false代表服务没有正在运行
	 */
	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	/**
	 * 格式化文字的颜色与大小
	 * 
	 * @param text1
	 *            第一个字符串
	 * @param text2
	 *            第二个字符串
	 * @param size1
	 *            第一个字符串大小（与原尺寸的比例）
	 * @param size2
	 *            第二个字符串大小（与原尺寸的比例）
	 * @param color1
	 *            第一个字符串的颜色
	 * @param color2
	 *            第二个字符串的颜色
	 * @return
	 */
	public SpannableString formatText(String text1, String text2, float size1,
			float size2, int color1, int color2) {
		SpannableString spa = new SpannableString(text1 + text2);
		spa.setSpan(new RelativeSizeSpan(size1), 0, text1.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spa.setSpan(new RelativeSizeSpan(size2), text1.length(), text1.length()
				+ text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spa.setSpan(new ForegroundColorSpan(color1), 0, text1.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spa.setSpan(new ForegroundColorSpan(color2), text1.length(),
				text1.length() + text2.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spa;
	}

	/**
	 * <<<<<<< .mine 设置颜色并四舍五入
	 * 
	 * @param context
	 *            上下文
	 * @param text
	 *            需要比对的数值
	 * @param dou
	 *            进行比对的数
	 * @param isJiaHao
	 *            正数前是否加＋号
	 * @return
	 */
	public SpannableString formatText(Context context, double text, double dou,
			boolean isJiaHao) {
		String str = String.format("%.2f", text);
		int color;
		double d = Parse.getInstance().parseDouble(str, "#.##");
		if (d == 0) {
			str = "--";
			color = Color.GRAY;
		} else {
			dou = Parse.getInstance().parseDouble(dou, "#.##");
			if (d > dou) {
				if (isJiaHao)
					str = "+" + str;
				color = Color.RED;
			} else if (d < dou)
				color = Color.GREEN;
			else
				color = Color.GRAY;
		}

		SpannableString spa = new SpannableString(String.valueOf(str));
		spa.setSpan(new ForegroundColorSpan(color), 0, str.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spa;
	}

	/**
	 * 计算涨跌与涨幅
	 * 
	 * @param context
	 *            上下文
	 * @param text
	 *            需要比对的数值
	 * @param dou
	 *            进行比对到数
	 * @param isJiaHao
	 *            正数前是否加＋号
	 * @return
	 */
	public List<SpannableString> formatTextZdZf(Context context,
			double newPrice, double prePrice, boolean isJiaHao) {
		int color;
		String str;
		String zfS;
		if (Parse.getInstance().parseDouble(newPrice, "#.##") == 0) {
			str = "--";
			zfS = "--";
			color = Color.GRAY;
		} else {
			double zd = newPrice - prePrice;
			str = String.format("%.2f", zd);

			// 计算涨幅
			double zf = zd / prePrice * 100;
			zfS = String.format("%.2f", zf) + "%";

			if (zd > 0) {
				if (isJiaHao) {
					str = "+" + str;
					zfS = "+" + zfS;
				}
				color = Color.RED;
			} else if (zd < 0)
				color = Color.GREEN;
			else
				color = Color.GRAY;
		}

		List<SpannableString> list = new ArrayList<SpannableString>();

		SpannableString spa = new SpannableString(str);
		spa.setSpan(new ForegroundColorSpan(color), 0, str.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		SpannableString spa1 = new SpannableString(zfS);
		spa1.setSpan(new ForegroundColorSpan(color), 0, zfS.length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		list.add(spa);
		list.add(spa1);
		return list;
	}

	/**
	 * ======= >>>>>>> .r13149 获取24小时格式的当前系统时间
	 * 
	 * @param format
	 *            time的格式，例："yyyy/MM/dd HH:mm:ss"
	 * @param locale
	 *            时区，例：Locale.CHINA（代表中国时区）
	 */
	public String getTime24(String format, Locale locale) {
		if (format.contains("h")) {
			format = format.replace("h", "H");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(new Date());
	}

	/**
	 * 获取12小时格式的当前系统时间
	 * 
	 * @param format
	 *            time的格式，例："yyyy/MM/dd hh:mm:ss"
	 * @param locale
	 *            时区，例：Locale.CHINA（代表中国时区）
	 */
	public String getTime12(String format, Locale locale) {
		if (format.contains("H")) {
			format = format.replace("H", "h");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(new Date());
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 *            上下文
	 * @param dpValue
	 *            dp单位
	 * @return px（像素）单位
	 */
	public int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 *            上下文
	 * @param pxValue
	 *            像素单位
	 * @return dp单位
	 */
	public int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 键盘隐藏并把焦点置为false
	 * 
	 * @param context
	 *            上下文
	 * @param editTexts
	 *            EditText数组
	 */
	public void setKeyBoardFocusable(Context context, EditText... editTexts) {
		for (int i = 0; i < editTexts.length; i++) {
			editTexts[i].setFocusable(false);
			editTexts[i].setFocusableInTouchMode(false);
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editTexts[i].getWindowToken(), 0);
		}
	}

	/**
	 * 键盘隐藏
	 * 
	 * @param context
	 *            上下文
	 * @param editTexts
	 *            EditText数组
	 */
	public void setKeyBoardGone(Context context, EditText... editTexts) {
		for (int i = 0; i < editTexts.length; i++) {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editTexts[i].getWindowToken(), 0);
		}
	}

	/**
	 * 拆分比较
	 * 
	 * @param need
	 *            需要比较的字符
	 * @param alive
	 *            与之进行比较的字符
	 * @param split
	 *            两个相比较的间隔符
	 * @return need大于等于alive返回fals，need小于alive返回true
	 */
	public boolean isMin(String need, String alive, String split) {
		String split1 = split;
		if (split1.contains("\\"))
			split1 = split1.replace("\\", "");
		boolean bool = true;
		String[] needs;// 需要比对的数组
		String[] alives;// 进行比对数组
		if (need.contains(split1)) {
			needs = need.split(split);
		} else {
			needs = new String[1];
			needs[0] = need;
		}
		if (alive.contains(split1)) {
			alives = alive.split(split);
		} else {
			alives = new String[1];
			alives[0] = alive;
		}
		if (alives.length > needs.length) {
			for (int i = 0; i < alives.length; i++) {
				if (i < needs.length) {
					if (Parse.getInstance().parseInt(needs[i]) >= Parse
							.getInstance().parseInt(alives[i])) {
						if (Parse.getInstance().parseInt(needs[i]) == Parse
								.getInstance().parseInt(alives[i])) {
							bool = false;
						} else {
							bool = false;
							break;
						}
					} else {
						bool = true;
						break;
					}
				} else {
					if (Parse.getInstance().parseInt(alives[i]) <= 0) {
						if (Parse.getInstance().parseInt(needs[i]) == 0) {
							bool = false;
						} else {
							bool = false;
							break;
						}
					} else {
						bool = true;
						break;
					}
				}
			}
		} else {
			for (int i = 0; i < needs.length; i++) {
				if (i < alives.length) {
					if (Parse.getInstance().parseInt(needs[i]) >= Parse
							.getInstance().parseInt(alives[i])) {
						if (Parse.getInstance().parseInt(needs[i]) == Parse
								.getInstance().parseInt(alives[i])) {
							bool = false;
						} else {
							bool = false;
							break;
						}
					} else {
						bool = true;
						break;
					}
				} else {
					if (Parse.getInstance().parseInt(needs[i]) >= 0) {
						if (Parse.getInstance().parseInt(needs[i]) == 0) {
							bool = false;
						} else {
							bool = false;
							break;
						}
					} else {
						bool = true;
						break;
					}
				}
			}
		}
		return bool;
	}

	/**
	 * 设置输入框焦点
	 * 
	 * @param isFocusable
	 *            焦点布尔值
	 * @param editTexts
	 *            EditText数组
	 */
	public void setFocusable(boolean isFocusable, EditText... editTexts) {
		for (int i = 0; i < editTexts.length; i++) {
			editTexts[i].setFocusable(isFocusable);
			editTexts[i].setFocusableInTouchMode(isFocusable);
		}
	}

	/**
	 * 压缩图片
	 * 
	 * @param newW
	 *            新图片宽度
	 * @param newH
	 *            新图片高度
	 * @param file
	 *            本地图片地址
	 * @return
	 */
	public Options reduceBitmap(int newW, int newH, String path) {
		if (path == null || path.length() == 0) {
			return null;
		}
		Options op = new Options();
		op.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, op);
		int oldW = op.outWidth;
		int oldH = op.outHeight;
		int size = 1;// 默认不缩放
		if (oldW > oldH) {// 宽大与高的情况
			if (oldW > newW) {
				size = oldW / newW;
			}
		} else {// 高大于宽的情况
			if (oldH > newH) {
				size = oldH / newH;
			}
		}
		Options op1 = new Options();
		op1.inJustDecodeBounds = false;
		op1.inSampleSize = size;
		return op1;
	}

	/**
	 * 保存图片到本地缓存“images_data”目录中
	 * 
	 * @param context
	 *            上下文
	 * @param bm
	 *            需要保存的Bitmap对象
	 * @return 保存成功后的地址，保存失败返回null
	 */
	public String saveBitmap(Context context, Bitmap bm) {
		if (bm == null) {
			return null;
		}
		File file = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file = new File(context.getExternalCacheDir().getPath()
					+ "/images_data");
		} else {
			file = new File(context.getCacheDir().getPath() + "/images_data");
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file.getPath() + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".jpeg");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);
			fos.flush();
			fos.close();
			if (bm != null) {
				bm.recycle();
				bm = null;
			}
			Options op = new Options();
			op.inSampleSize = 16;
			bm = BitmapFactory.decodeFile(file.getPath());
			if (bm == null) {
				return null;
			}
			bm.recycle();
			bm = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
		return file.getPath();
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	public String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	private final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };

	/**
	 * 顶部
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示文字
	 */
	public void showToastTop(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.show();
	}

	/**
	 * 顶部（自定义位置）
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示文字
	 * @param x
	 *            横向位置
	 * @param y
	 *            纵向位置
	 */
	public void showToastTop(Context context, String msg, int x, int y) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.TOP, x, y);
		toast.show();
	}

	/**
	 * 中间
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示文字
	 */
	public void showToastCenter(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 中间（自定义位置）
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示文字
	 * @param x
	 *            横向位置
	 * @param y
	 *            纵向位置
	 */
	public void showToastCenter(Context context, String msg, int x, int y) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, x, y);
		toast.show();
	}

	/**
	 * 底部
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示文字
	 */
	public void showToastBottom(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	}

	/**
	 * 底部（自定义位置）
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示文字
	 * @param x
	 *            横向位置
	 * @param y
	 *            纵向位置
	 */
	public void showToastBottom(Context context, String msg, int x, int y) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.BOTTOM, x, y);
		toast.show();
	}

	/**
	 * 默认Toast
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            提示文字
	 */
	public void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 正则表达式匹配
	 * 
	 * @param regex
	 *            正则表达式
	 * @param str
	 *            要匹配的字符串
	 * @return 匹配结果
	 */
	public boolean regexMatch(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

}

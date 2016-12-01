package com.mytv365.zb.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

/**
 * http的所有接口信息
 */
public class UtilHttp {


	private static Context mContext;
	// URL
	private static final String URL_SITE = "http://gphq.mytv365.com/Stock_WebService/stockNowInfo";
	private static final String URL_QUOTE = URL_SITE + "/getStockKChartByCode?";
	/** 搜索接口 通过传入股票名称关键字，股票代码关键字，股票名称拼音首字母关键字，搜索出要的股票列表 */
	//183.129.206.37:8080/Stock_WebService/stockNowInfo/serachStock?codeOrName=300520
	public static final String URL_SEARCH = URL_SITE +"/serachStock?";
	/** 获取自选股 */
	public static final String URL_GET_SELF = "http://yhzb.mytv365.com/Arena_App/optionStock/list?rows=50";
	//183.129.206.37:8080/Stock_WebService/stockNowInfo/serachStockMore?code=300072,601336,107287,112149
	//gphq.mytv365.com/Stock_WebService/stockNowInfo/serachStockMore?
	public static final String URL_GET_SELF2 = "http://gphq.mytv365.com/Stock_WebService/stockNowInfo/serachStockMore?code=%s";
	/** 添加自选股 */
	public static final String URL_ADD_SELF = "http://yhzb.mytv365.com/Arena_App/optionStock/add?";
	// http://120.55.100.220/api/stock/delete?temp_token=2&stock_id=601988&market=2
	/**
	 * 删除自选股
	 * http://192.168.0.47/Arena_App/optionStock/delete?stockNo=1
	 */
	public static final String URL_DEL_SELF = "http://yhzb.mytv365.com/Arena_App/optionStock/delete?";
	/** 沪深行情页面数据，包含热门板块，涨幅榜，跌幅榜 */
	private static final String URL_HSHQ = URL_QUOTE + "type=hshq";
	/** 个股行情页面数据，包含个股实时数据，分时行情，k线 line=0 分时 =1 日K =5 周K =30 =360 */
	private static final String URL_GGHQ = URL_QUOTE + "line=0&pageSize=1";
	/** 板块列表 一级 */
	private static final String URL_BLOCK_LIST = URL_QUOTE + "type=blocklist";
	/** 取得板块中所有股票 */
	private static final String URL_BLOCK_STOCK = URL_QUOTE
			+ "type=blockstock&code=%s";
	/** 涨幅榜 */
	private static final String URL_UP_LIST = URL_QUOTE + "type=uplist";
	/** 跌幅榜 */
	private static final String URL_DOWN_LIST = URL_QUOTE + "type=downlist";
	/** 个股新闻列表 */
	//183.129.206.37:8080/Stock_WebService/stockNowInfo/getStockNewsByStockCode?pageSize=5&currentPage=3&stockCode=300072
	public static final String URL_NEWS = URL_SITE + "/getStockNewsByStockCode?";
	/** 个股公告列表 */
	//183.129.206.37:8080/Stock_WebService/stockNowInfo/getStockNoticeByStockCode?pageSize=5&currentPage=1&stockCode=000005
	public static final String URL_INFO = URL_SITE + "/getStockNoticeByStockCode?";
	// private static final String URL_ZZ =
	// URL+"type=noticelist&code=000998&market=1";
	/** 新闻内容详细 */
	//183.129.206.37:8080/Stock_WebService/stockNowInfo/getStockNewsInfoById?id=0000000000000g7ipj
	public static final String URL_NEWS_ITEM = URL_SITE + "/getStockNewsInfoById?";
	/** 公告内容详细 */
	//183.129.206.37:8080/Stock_WebService/stockNowInfo/getStockNoticeInfoById?id=0000000000000g7k3u
	public static final String URL_INFO_ITEM = URL_SITE + "/getStockNoticeInfoById?";
	/** F10 我会以web页面形式提供。你们只需要知道一个地址。例如 */
	private static final String URL_F10 = URL_SITE
			+ "/F10/Index.aspx?code=000998";

	// 服务启动时初始化
	public UtilHttp(Context context) {
		mContext = context;
	}

	/**
	 * 网络面数据
	 */
	public static String getNetString(String urlstr) {
		String result = null;
		try {
			HttpGet httpGet = new HttpGet(urlstr);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			Log.i("UtilHttp", "===getHshq()"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				 Log.i("UtilHttp", "===result:" + result);
			}
		} catch (ParseException e) {
			Log.i("UtilHttp", "===ParseException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("UtilHttp", "===IOException:" + e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加自选股
	 */
//	public static String addSelf() {
//		String result = null;
//		try {
//			HttpGet httpGet = new HttpGet(URL_ADD_SELF);
//			HttpResponse httpResponse = new DefaultHttpClient()
//					.execute(httpGet);
//			Log.i("UtilHttp", "===addSelf()"
//					+ httpResponse.getStatusLine().getStatusCode());
//			if (httpResponse.getStatusLine().getStatusCode() == 200) {
//				result = EntityUtils.toString(httpResponse.getEntity());
//				// Log.i("UtilHttp", "===result:" + result);
//			}
//		} catch (ParseException e) {
//			Log.i("UtilHttp", "===ParseException:" + e);
//			e.printStackTrace();
//		} catch (IOException e) {
//			Log.i("UtilHttp", "===IOException:" + e);
//			e.printStackTrace();
//		}
//		return result;
//	}

	/**
	 * 沪深行情页面数据
	 */
	public String getHshq() {
		String result = null;
		try {
			HttpGet httpGet = new HttpGet(URL_HSHQ);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			Log.i("UtilHttp", "===getHshq()"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				// Log.i("UtilHttp", "===result:" + result);
			}
		} catch (ParseException e) {
			Log.i("UtilHttp", "===ParseException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("UtilHttp", "===IOException:" + e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 个股行情
	 */
	public String getGghq(String code) {
		Log.i("UtilHttp", "===URL" + URL_GGHQ + "&stockCode=" + code);
		String result = null;
		try {
			HttpGet httpGet = new HttpGet(URL_GGHQ + "&stockCode=" + code
					);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			Log.i("UtilHttp", "===getGghq()"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				 Log.i("UtilHttp", "===result:" + result);
			}
		} catch (ParseException e) {
			Log.i("UtilHttp", "===ParseException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("UtilHttp", "===IOException:" + e);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * URL_ZX + "type=gghq&line=0
	 * @param string 
	 * 
	 * @return
	 */
	public static String getKLineByCodeAndKValue(String qpCode, String kvalue) {
		String result = null;
		try {
			HttpGet httpGet = new HttpGet(URL_QUOTE + "stockCode="
					+ qpCode  + "&line=" + kvalue
					);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			Log.i("UtilHttp", "===getGghq()"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				// Log.i("UtilHttp", "===result:" + result);
			}
		} catch (ParseException e) {
			Log.i("UtilHttp", "===ParseException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("UtilHttp", "===IOException:" + e);
			e.printStackTrace();
		}

		return result;
	}

	// 新闻
	public static String getHostList() {
		String result = null;
		try {
			HttpGet httpGet = new HttpGet(URL_BLOCK_LIST);

			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			Log.i("UtilHttp", "===getGghq()"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				// Log.i("UtilHttp", "===result:" + result);
			}
		} catch (ParseException e) {
			Log.i("UtilHttp", "===ParseException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("UtilHttp", "===IOException:" + e);
			e.printStackTrace();
		}

		return result;
	}

	// 涨跌幅榜
	public static String getZDF(String types, String index) {
		String result = null;
		try {
			HttpGet httpGet = new HttpGet(
					String.format(
							"http://stock.microinvestment.cn/Quote.ashx?type=%s&limit=%s",
							types, index));

			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			Log.i("UtilHttp", "===getGghq()"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity);
					// Log.i("UtilHttp", "===result:" + result);
					return result;
				} else {
					return null;
				}
			}
		} catch (ParseException e) {
			Log.i("UtilHttp", "===ParseException:" + e);
			// e.printStackTrace();
		} catch (IOException e) {
			Log.i("UtilHttp", "===IOException:" + e);
			// e.printStackTrace();
		}
		return null;

	}

	// 新闻  
	public static String getXW(String stockCode,String newsGongGao,String pageSize, String params) {
		String result = null;
		try {
//			HttpGet httpGet = new HttpGet(
//					String.format(
//							"http://stock.microinvestment.cn/info.ashx?type=%s&code=%s&market=%s&limit=%s",
//							types, code, market, index));
			HttpGet httpGet;
			if(newsGongGao.equals("newslist")){
			httpGet = new HttpGet(
					String.format(
							URL_NEWS+"stockCode=%s&pageSize=%s&currentPage=1",
							stockCode,pageSize));
			}else{
			httpGet = new HttpGet(
						String.format(
								URL_INFO+"stockCode=%s&pageSize=%s&currentPage=1",
								stockCode,pageSize));
			}
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpGet);
			// Log.i("UtilHttp", "===getGghq()"
			// + httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				 Log.i("UtilHttps", "===result:" + result);
			}
		} catch (ParseException e) {
			Log.i("UtilHttp", "===ParseException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.i("UtilHttp", "===IOException:" + e);
			e.printStackTrace();
		}

		return result;
	}

	// public String postLogin(String company_id, String username, String
	// password) {
	// String strResult = null;
	// HttpPost httpRequest = new HttpPost(URL_LOGIN);
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair(Globals.ACCESS_TOKEN,
	// Globals.TOKEN)); // 系统授权码
	// params.add(new BasicNameValuePair(Globals.COMPANY_ID, company_id));
	// // 企业ID
	// params.add(new BasicNameValuePair(Globals.USERNAME, username)); //
	// 用户名
	// params.add(new BasicNameValuePair(Globals.PASSWORD, password)); // 密码
	// try {
	// UtilHttpTime.httpouttime();
	// // 发出HTTP request
	// httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	// // 取得HTTP response
	// HttpResponse httpResponse = new
	// DefaultHttpClient(UtilHttpTime.httpParameters).execute(httpRequest);
	// // 若状态码为200 ok
	// if (httpResponse.getStatusLine().getStatusCode() == 200) {
	// // 取出回应字串
	// strResult = EntityUtils.toString(httpResponse.getEntity(),
	// HTTP.UTF_8);
	// }
	// } catch (ClientProtocolException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// return null;
	// }
	// return strResult;
	// }

	/**
	 * 判断网络是否连接 连接成功返回true，失败返回false
	 */
	public boolean isNetworkConnected() {
		// 获取网络连接服务
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isConnectedOrConnecting();
		}
		return false;
	}

}

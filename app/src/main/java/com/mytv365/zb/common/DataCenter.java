package com.mytv365.zb.common;

import android.content.Context;
import android.util.Log;

import com.mytv365.zb.model.GgxqBean;
import com.mytv365.zb.model.ItemGgxqTime;
import com.mytv365.zb.model.ItemGgxqTime2;
import com.mytv365.zb.model.ItemIndex;
import com.mytv365.zb.model.ItemKLine1;
import com.mytv365.zb.model.ItemMyStock;
import com.mytv365.zb.model.ItemSelf;
import com.mytv365.zb.model.ItemStock;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Parse;
import com.mytv365.zb.utils.UtilHttp;
import com.mytv365.zb.widget.stock.OHLCEntity;
import com.mytv365.zb.widget.stock.StickEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 数据中心
 */
public class DataCenter {
	private static DataCenter instance = null;

	private Context mContext;
	public UtilHttp http;

	/**  */
	public String mStrList;
	/** 个股详情 */
	public String mStrGghq;
	/** 沪深行情 */
	public String mStrHshq;

	/**  */
	public ArrayList<ItemSelf> listSelf;
	/**  */
	public ArrayList<ItemIndex> listIndex;
	/**  */
	public ArrayList<ItemMyStock> listMyStock;
	/** 个股详情实时走势 */
	public ArrayList<ItemGgxqTime> listGgxqTime;
	/** 个股详情实时走势 坐标 */
	public ArrayList<ItemGgxqTime2> listGgxqTime2;
	/** 热门板块 */
	public ArrayList<ItemStock> listHot;
	/** 涨幅榜 */
	public ArrayList<ItemStock> listStockUp;
	/** 跌幅榜 */
	public ArrayList<ItemStock> listStockDown;

	public ArrayList<ItemKLine1> kLine1;

	/** 个股详情 */
	public GgxqBean ggxqBean;

	/** 是否自动刷新 */
	public boolean autoupdate;

	/** 个股行情（明细列表） */
	public ArrayList<Map<String, Object>> mXList;

	/** service启动时间 */
	public long startTime;
	/** 刷新间隔时间 */
	public int interval_time = 20;
	/** app uid */
	public int uid;
	/** 电量 */
	public int electricity;
	/** 坐标经度 */
	public double mLatitude;
	/** 坐标纬度 */
	public double mLongitude;

	public static DataCenter getInstance() {
		if (instance == null) {
			instance = new DataCenter();
		}
		return instance;
	}

	private DataCenter() {

	}

	public static LinkedList<HashMap<String, String>> parseSearchList(
			String waiteStr) {
		LinkedList<HashMap<String, String>> linkes = new LinkedList<HashMap<String, String>>();
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);
//				JSONObject dataJSONOb = jsob.optJSONObject("resultData");
//				if (dataJSONOb != null) {

					JSONArray jsonarray = jsob.optJSONArray("resultData");
					if (jsonarray != null) {
						int length = jsonarray.length();
						HashMap<String, String> hashMap;
						for (int i = 0; i < length; i++) {
							JSONObject jsobs = jsonarray.optJSONObject(i);
							String code = jsobs.optString("stockCode");
							String name = jsobs.optString("stocksName");

							hashMap = new HashMap<String, String>();
							hashMap.put("code", code);
							hashMap.put("name", name);

							linkes.add(hashMap);
						}

						return linkes;
					}

//				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return linkes;
	}

	/**
	 * 解析已添加的自选股
	 */
	public List<Map<String, String>> parseOptionalUnit(String str) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if (str != null) {
			try {
				JSONObject jsob = new JSONObject(str);
					JSONArray jsonarray = jsob.optJSONArray("resultData");
					if (jsonarray != null) {
						int length = jsonarray.length();
						HashMap<String, String> hashMap;
						for (int i = 0; i < length; i++) {
							JSONObject jsobs = jsonarray.optJSONObject(i);
							String stock_id = jsobs.optString("stockNo");

							hashMap = new HashMap<String, String>();
							hashMap.put("stock_id", stock_id);
							list.add(hashMap);
						}

						return list;
					}


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;
	}

	public static List<OHLCEntity> listOhlc = new ArrayList<OHLCEntity>();
	public static List<StickEntity> vol = new ArrayList<StickEntity>();
	public static List<StickEntity> timevol = new ArrayList<StickEntity>();
	public static List<OHLCEntity> timeohlc = new LinkedList<OHLCEntity>();

	public static LinkedList<HashMap<String, String>> parseHostList(
			String waiteStr) {
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);
				JSONObject dataJSONOb = jsob.optJSONObject("data");
				if (dataJSONOb != null) {

					JSONArray jsonarray = dataJSONOb.optJSONArray("list");
					if (jsonarray != null) {
						int length = jsonarray.length();
						LinkedList<HashMap<String, String>> linkes = new LinkedList<HashMap<String, String>>();
						HashMap<String, String> hashMap;
						for (int i = 0; i < length; i++) {
							JSONObject jsobs = jsonarray.optJSONObject(i);
							String code = jsobs.optString("code");
							String name = jsobs.optString("name");
							String change = jsobs.optString("change");

							String changep = jsobs.optString("changep");
							String cje = jsobs.optString("cje");

							hashMap = new HashMap<String, String>();
							hashMap.put("code", code);
							hashMap.put("name", name);
							hashMap.put("change", change);
							hashMap.put("changep", changep);
							hashMap.put("cje", cje);
							linkes.add(hashMap);
						}
						return linkes;
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	public static HashMap<String, String> parseNoticeContent(String waiteStr) {
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);
//				JSONObject dataJSONOb = jsob.optJSONObject("resultCode");
				JSONObject jsobs = jsob.optJSONObject("resultData");
//				if (jsobs != null) {

					
					if (jsobs != null) {
						String id = jsobs.optString("discId");
						long time = Long.parseLong(jsobs.optString("declaRedate"));
						String date = MyUtils.getInstance().date2String("yyyy/MM/dd", time);
						String title = jsobs.optString("title");
						String path = "";
						String content = jsobs.optString("textContent");
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("id", id);
						hashMap.put("date", date);
						hashMap.put("title", title);
						hashMap.put("path", path);
						hashMap.put("content", content);
						return hashMap;
					}
//				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	public static HashMap<String, String> parseNewsContent(String waiteStr) {
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);
//				JSONObject dataJSONOb = jsob.optJSONObject("resultData");
//				if (dataJSONOb != null) {

				
					JSONObject jsobs = jsob.optJSONObject("resultData");
					if (jsobs != null) {
						String id = jsobs.optString("gUid");
						long time = Long.parseLong(jsobs.optString("declareDate"));
						String date =MyUtils.getInstance().date2String("yyyy/MM/dd", time);
						String title = jsobs.optString("titleMain");
						String src = jsobs.optString("srcName");
						String content = jsobs.optString("textContent");
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("id", id);
						hashMap.put("date", date);
						hashMap.put("title", title);
						hashMap.put("src", src);
						hashMap.put("content", content);

						return hashMap;
					}

//				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	public static LinkedList<HashMap<String, String>> parseBakuaiList(
			String waiteStr, String listtype) {
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);
				JSONObject dataJSONOb = jsob.optJSONObject("data");
				if (dataJSONOb != null) {

					JSONArray jsonarray = dataJSONOb.optJSONArray(listtype);
					if (jsonarray != null) {
						int length = jsonarray.length();
						LinkedList<HashMap<String, String>> linkes = new LinkedList<HashMap<String, String>>();
						HashMap<String, String> hashMap;
						for (int i = 0; i < length; i++) {
							JSONObject jsobs = jsonarray.optJSONObject(i);
							String code = jsobs.optString("code");
							String name = jsobs.optString("name");
							String market = jsobs.optString("market");
							String time = jsobs.optString("time");
							String close = jsobs.optString("close");
							String change = jsobs.optString("change");

							String changep = jsobs.optString("changep");
							String cje = jsobs.optString("cje");
							String cjl = jsobs.optString("cjl");
							hashMap = new HashMap<String, String>();
							hashMap.put("code", code);
							hashMap.put("name", name);
							hashMap.put("market", market);
							hashMap.put("cjl", cjl);
							hashMap.put("time", time);
							hashMap.put("close", close);
							hashMap.put("change", change);
							hashMap.put("changep", changep);
							hashMap.put("cje", cje);
							linkes.add(hashMap);
						}

						return linkes;
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	public static LinkedList<HashMap<String, String>> parseZDF(String waiteStr,
			String type) {
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);
				JSONObject dataJSONOb = jsob.optJSONObject("data");
				if (dataJSONOb != null) {

					JSONArray jsonarray = dataJSONOb.optJSONArray(type);
					if (jsonarray != null) {
						int length = jsonarray.length();
						LinkedList<HashMap<String, String>> linkes = new LinkedList<HashMap<String, String>>();
						HashMap<String, String> hashMap;
						for (int i = 0; i < length; i++) {
							JSONObject jsobs = jsonarray.optJSONObject(i);
							String code = jsobs.optString("code");
							String name = jsobs.optString("name");
							String market = jsobs.optString("market");
							String time = jsobs.optString("time");
							String close = jsobs.optString("close");
							String change = jsobs.optString("change");

							String changep = jsobs.optString("changep");
							String cje = jsobs.optString("cje");
							String cjl = jsobs.optString("cjl");
							hashMap = new HashMap<String, String>();
							hashMap.put("code", code);
							hashMap.put("name", name);
							hashMap.put("market", market);
							hashMap.put("cjl", cjl);
							hashMap.put("time", time);
							hashMap.put("close", close);
							hashMap.put("change", change);
							hashMap.put("changep", changep);
							hashMap.put("cje", cje);
							linkes.add(hashMap);
						}
						return linkes;
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return null;
			}

		}

		return null;
	}

	//
	public static LinkedList<HashMap<String, String>> parseXW(String waiteStr,String newsGongGao) {
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);JSONArray 
				jsonarray = jsob.optJSONArray("resultData");				
					if (jsonarray != null) {
						int length = jsonarray.length();
						LinkedList<HashMap<String, String>> linkes = new LinkedList<HashMap<String, String>>();
						HashMap<String, String> hashMap;
						for (int i = 0; i < length; i++) {
							JSONObject jsobs = jsonarray.optJSONObject(i);
							if(newsGongGao.equals("newslist")){
							String title = jsobs.optString("srcName");
							String dates = jsobs.optString("titleMain");
							String ids = jsobs.optString("gUid");
							hashMap = new HashMap<String, String>();
							hashMap.put("date", dates);
							hashMap.put("title", title);
							hashMap.put("id", ids);
							
							linkes.add(hashMap);
							}else{
								String title = jsobs.optString("title");
								long time = Long.parseLong(jsobs.optString("declaRedate"));
								String dates = MyUtils.getInstance().date2String("yyyy/MM/dd", time);
								String ids = jsobs.optString("discId");
								hashMap = new HashMap<String, String>();
								hashMap.put("date", dates);
								hashMap.put("title", title);
								hashMap.put("id", ids);
								
								linkes.add(hashMap);
							}
							
						}

						return linkes;
					}

//				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	// 接卸kline数据
	public static LinkedList<OHLCEntity> parseKLine(String waiteStr) {
		if (waiteStr != null) {
			try {
				JSONObject jsob = new JSONObject(waiteStr);
				JSONObject dataJSONOb = jsob.optJSONObject("resultData");
//				if (dataJSONOb != null) {
//					JSONObject jsonobject = dataJSONOb.optJSONObject("kChart");
					if (dataJSONOb != null) {

						JSONArray jsonarray = dataJSONOb.optJSONArray("kChart");
						if (jsonarray != null) {
							if (listOhlc == null) {
								listOhlc = new ArrayList<OHLCEntity>();
							}
							if (vol == null) {
								vol = new ArrayList<StickEntity>();
							}
							vol.clear();
							listOhlc.clear();
							int length = jsonarray.length();
							int temp = 0;
							// if(length > 30)
							// {
							// temp = length-30;
							// }
							OHLCEntity ohlcEntity = null;
							StickEntity stickEntity = null;
							double max;
							double min;

							for (int i = temp; i < length; i++) {
								// if (length - i > 60) {
								// continue;
								// }
								JSONObject temps = jsonarray.optJSONObject(i);
								if (temps != null) {
									String time =MyUtils.getInstance().date2String("yyyyMMdd", temps.optLong("enddate", 0));
									int date = Integer.parseInt(time);
									double open = temps.optDouble("open", 0);
									double close = temps.optDouble("close", 0);
									double high = temps.optDouble("hprice", 0);
									double low = temps.optDouble("lprice", 0);
									double change = temps
											.optDouble("change", 0);
									double changep = temps.optDouble("changep",
											0);
									double cje = temps.optDouble("cje", 0);
									double cjl = temps.optDouble("cjl", 0);
									ohlcEntity = new OHLCEntity(open,
											(float) high, (float) low, close,
											date);
									ohlcEntity.setChange(change);
									ohlcEntity.setChangep(changep);
									ohlcEntity.setCje(cje);
									ohlcEntity.setCjl(cjl / 100);

									listOhlc.add(ohlcEntity);
									stickEntity = new StickEntity();
									int tempdate = date;
									if (tempdate < 10000) {
										tempdate = tempdate + 10000000;
									}

									stickEntity.setDate(tempdate);
									stickEntity.setHigh(cjl / 100);
									stickEntity.setLow(0);
									vol.add(stickEntity);

								}
							}

							// return listOhlc;
						}

//					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}
	

	// 服务启动时初始化
	public void initDB(Context context) {
		mContext = context;
		http = new UtilHttp(mContext);
		listSelf = new ArrayList<ItemSelf>();
		listIndex = new ArrayList<ItemIndex>();
		listMyStock = new ArrayList<ItemMyStock>();
		listGgxqTime = new ArrayList<ItemGgxqTime>();
		listGgxqTime2 = new ArrayList<ItemGgxqTime2>();
		listHot = new ArrayList<ItemStock>();
		listStockUp = new ArrayList<ItemStock>();
		listStockDown = new ArrayList<ItemStock>();
		kLine1 = new ArrayList<ItemKLine1>();
	}

	/**
	 * 解析是否自动刷新
	 * 
	 * @param str
	 */
	public void parseAutoupdate(String str) {
		if (str == null) {
			autoupdate = true;
			return;
		}
		try {
			JSONObject jo = new JSONObject(str);
			autoupdate = jo.getBoolean("autoupdate");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 解析个股详情
	 * 
	 * @param str
	 */
	public void parserGgxq() {
		try {
			DataCenter.getInstance().ggxqBean = new GgxqBean();
			// Log.e("DataCenter", "processTaskList = " + str);
			JSONObject jsondata = new JSONObject(
					DataCenter.getInstance().mStrGghq);
			 Log.i("SelfActivity", "resultMessage = " + jsondata.getString("resultMessage"));
			JSONObject jsonObject = jsondata.getJSONObject("resultData");
			if(jsonObject != null){
//			 Log.i("SelfActivity", "jsonObject = " + jsonObject.toString());
			JSONObject stockinfo = jsonObject.getJSONObject("stockNowInfo");
//			 Log.i("SelfActivity", "stockinfo = " + stockinfo.toString());
			DataCenter.getInstance().ggxqBean.code = stockinfo
					.getString("code");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.code = "
//					+ DataCenter.getInstance().ggxqBean.code);
			DataCenter.getInstance().ggxqBean.name = stockinfo
					.getString("name");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.name = "
//					+ DataCenter.getInstance().ggxqBean.name);
			DataCenter.getInstance().ggxqBean.market = stockinfo
					.getString("market");
//			Log.i("SelfActivity",
//					"===DataCenter.getInstance().ggxqBean.market = "
//							+ DataCenter.getInstance().ggxqBean.market);
			DataCenter.getInstance().ggxqBean.stocktype = stockinfo
					.getString("stocktype");
//			Log.i("SelfActivity",
//					"DataCenter.getInstance().ggxqBean.stocktype = "
//							+ DataCenter.getInstance().ggxqBean.stocktype);
			DataCenter.getInstance().ggxqBean.preclose = stockinfo
					.getString("preclose");
//			Log.i("SelfActivity",
//					"DataCenter.getInstance().ggxqBean.preclose = "
//							+ DataCenter.getInstance().ggxqBean.preclose);
			DataCenter.getInstance().ggxqBean.open = stockinfo
					.getString("open");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.open = "
//					+ DataCenter.getInstance().ggxqBean.open);
			DataCenter.getInstance().ggxqBean.close = stockinfo
					.getString("close");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.close = "
//					+ DataCenter.getInstance().ggxqBean.close);
			DataCenter.getInstance().ggxqBean.hprice = stockinfo
					.getString("hprice");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.hprice = "
//					+ DataCenter.getInstance().ggxqBean.hprice);
			DataCenter.getInstance().ggxqBean.lprice = stockinfo
					.getString("lprice");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.lprice = "
//					+ DataCenter.getInstance().ggxqBean.lprice);
			DataCenter.getInstance().ggxqBean.cje = stockinfo.getString("cje");
			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.cje = "
					+ DataCenter.getInstance().ggxqBean.cje);
			DataCenter.getInstance().ggxqBean.cjl = stockinfo.getString("cjl");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.cjl = "
//					+ DataCenter.getInstance().ggxqBean.cjl);
			DataCenter.getInstance().ggxqBean.bp1 = stockinfo.getString("bp1");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bp1 = "
//					+ DataCenter.getInstance().ggxqBean.bp1);
			DataCenter.getInstance().ggxqBean.bp2 = stockinfo.getString("bp2");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bp2 = "
//					+ DataCenter.getInstance().ggxqBean.bp2);
			DataCenter.getInstance().ggxqBean.bp3 = stockinfo.getString("bp3");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bp3 = "
//					+ DataCenter.getInstance().ggxqBean.bp3);
			DataCenter.getInstance().ggxqBean.bp4 = stockinfo.getString("bp4");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bp4 = "
//					+ DataCenter.getInstance().ggxqBean.bp4);
			DataCenter.getInstance().ggxqBean.bp5 = stockinfo.getString("bp5");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bp5 = "
//					+ DataCenter.getInstance().ggxqBean.bp5);
			DataCenter.getInstance().ggxqBean.bv1 = stockinfo.getString("bv1");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bv1 = "
//					+ DataCenter.getInstance().ggxqBean.bv1);
			DataCenter.getInstance().ggxqBean.bv2 = stockinfo.getString("bv2");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bv2 = "
//					+ DataCenter.getInstance().ggxqBean.bv2);
			DataCenter.getInstance().ggxqBean.bv3 = stockinfo.getString("bv3");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bv3 = "
//					+ DataCenter.getInstance().ggxqBean.bv3);
			DataCenter.getInstance().ggxqBean.bv4 = stockinfo.getString("bv4");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bv4 = "
//					+ DataCenter.getInstance().ggxqBean.bv4);
			DataCenter.getInstance().ggxqBean.bv5 = stockinfo.getString("bv5");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.bv5 = "
//					+ DataCenter.getInstance().ggxqBean.bv5);
			DataCenter.getInstance().ggxqBean.sp1 = stockinfo.getString("sp1");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sp1 = "
//					+ DataCenter.getInstance().ggxqBean.sp1);
			DataCenter.getInstance().ggxqBean.sp2 = stockinfo.getString("sp2");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sp2 = "
//					+ DataCenter.getInstance().ggxqBean.sp2);
			DataCenter.getInstance().ggxqBean.sp3 = stockinfo.getString("sp3");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sp3 = "
//					+ DataCenter.getInstance().ggxqBean.sp3);
			DataCenter.getInstance().ggxqBean.sp4 = stockinfo.getString("sp4");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sp4 = "
//					+ DataCenter.getInstance().ggxqBean.sp4);
			DataCenter.getInstance().ggxqBean.sp5 = stockinfo.getString("sp5");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sp5 = "
//					+ DataCenter.getInstance().ggxqBean.sp5);
			DataCenter.getInstance().ggxqBean.sv1 = stockinfo.getString("sv1");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sv1 = "
//					+ DataCenter.getInstance().ggxqBean.sv1);
			DataCenter.getInstance().ggxqBean.sv2 = stockinfo.getString("sv2");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sv2 = "
//					+ DataCenter.getInstance().ggxqBean.sv2);
			DataCenter.getInstance().ggxqBean.sv3 = stockinfo.getString("sv3");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sv3 = "
//					+ DataCenter.getInstance().ggxqBean.sv3);
			DataCenter.getInstance().ggxqBean.sv4 = stockinfo.getString("sv4");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sv4 = "
//					+ DataCenter.getInstance().ggxqBean.sv4);
			DataCenter.getInstance().ggxqBean.sv5 = stockinfo.getString("sv5");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.sv5 = "
//					+ DataCenter.getInstance().ggxqBean.sv5);
			DataCenter.getInstance().ggxqBean.change = stockinfo
					.getString("change");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.change = "
//					+ DataCenter.getInstance().ggxqBean.change);
			DataCenter.getInstance().ggxqBean.changep = stockinfo
					.getString("changep");
//			Log.i("SelfActivity",
//					"DataCenter.getInstance().ggxqBean.changep = "
//							+ DataCenter.getInstance().ggxqBean.changep);
			DataCenter.getInstance().ggxqBean.pe = stockinfo.getString("pe");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.pe = "
//					+ DataCenter.getInstance().ggxqBean.pe);
			DataCenter.getInstance().ggxqBean.pb = stockinfo.getString("pb");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.pb = "
//					+ DataCenter.getInstance().ggxqBean.pb);

			DataCenter.getInstance().ggxqBean.fla = stockinfo.getString("fla");
//			Log.i("SelfActivity", "DataCenter.getInstance().ggxqBean.fla = "
//					+ DataCenter.getInstance().ggxqBean.fla);
			DataCenter.getInstance().ggxqBean.total = stockinfo
					.getString("total");

			DataCenter.getInstance().ggxqBean.countup = stockinfo
					.getString("countup");

			DataCenter.getInstance().ggxqBean.countmid = stockinfo
					.getString("countmid");

			DataCenter.getInstance().ggxqBean.countdown = stockinfo
					.getString("countdown");

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
//			DataCenter.getInstance().ggxqBean.code = "000998";
//			DataCenter.getInstance().ggxqBean.name = "隆平高科";
//			DataCenter.getInstance().ggxqBean.market = "1";
//			DataCenter.getInstance().ggxqBean.stocktype = "1";
//			DataCenter.getInstance().ggxqBean.preclose = "28.940";
//			DataCenter.getInstance().ggxqBean.open = "28.420";
//			DataCenter.getInstance().ggxqBean.close = "28.140";
//			DataCenter.getInstance().ggxqBean.hprice = "28.440";
//			DataCenter.getInstance().ggxqBean.lprice = "27.400";
//			DataCenter.getInstance().ggxqBean.cje = "863670324";
//			DataCenter.getInstance().ggxqBean.cjl = "30965174";
//			
//			DataCenter.getInstance().ggxqBean.bp1 = "28.030";
//			DataCenter.getInstance().ggxqBean.bp2 = "28.020";
//			DataCenter.getInstance().ggxqBean.bp3 = "28.010";
//			DataCenter.getInstance().ggxqBean.bp4 = "28.000";
//			DataCenter.getInstance().ggxqBean.bp5 = "27.980";
//			
//			DataCenter.getInstance().ggxqBean.bv1 = "1000";
//			DataCenter.getInstance().ggxqBean.bv2 = "400";
//			DataCenter.getInstance().ggxqBean.bv3 = "17700";
//			DataCenter.getInstance().ggxqBean.bv4 = "78700";
//			DataCenter.getInstance().ggxqBean.bv5 ="500";
//
//			DataCenter.getInstance().ggxqBean.sp1 = "28.130";
//			DataCenter.getInstance().ggxqBean.sp2 = "28.150";
//			DataCenter.getInstance().ggxqBean.sp3 = "28.160";
//			DataCenter.getInstance().ggxqBean.sp4 = "28.180";
//			DataCenter.getInstance().ggxqBean.sp5 = "28.190";
//			
//			DataCenter.getInstance().ggxqBean.sv1 = "2600";
//			DataCenter.getInstance().ggxqBean.sv2 ="500";
//			DataCenter.getInstance().ggxqBean.sv3 = "9800";
//			DataCenter.getInstance().ggxqBean.sv4 ="16300";
//			DataCenter.getInstance().ggxqBean.sv5 = "2800";
//
//			DataCenter.getInstance().ggxqBean.change = "-0.880";
//;
//			DataCenter.getInstance().ggxqBean.changep = "-0.0304";
//			
//			DataCenter.getInstance().ggxqBean.pe = "108.46";
//
//			DataCenter.getInstance().ggxqBean.pb ="7.65";
//
//
//			DataCenter.getInstance().ggxqBean.fla = "100000";
//;
//			DataCenter.getInstance().ggxqBean.total = "1000000";
//
//			DataCenter.getInstance().ggxqBean.countup = "--";
//
//			DataCenter.getInstance().ggxqBean.countmid = "--";
//
//			DataCenter.getInstance().ggxqBean.countdown = "--";
	}

	public void parserGgxqTime() {
		ItemGgxqTime item;
		try {

			JSONObject jsondata = new JSONObject(
					DataCenter.getInstance().mStrGghq);
			JSONObject jsonObject = jsondata.getJSONObject("resultData");
			JSONObject jsonObj = jsonObject.getJSONObject("stockNowInfo");
			String market = jsonObj.getString("market");
			String code = jsonObj.getString("code");
			double preclose = jsonObj.optDouble("preclose", 0);
			// 进行中的任务
			JSONArray array = jsonObject.optJSONArray("kChart");
			if (array != null) {
				DataCenter.getInstance().listGgxqTime.clear();
				StickEntity stickEntity;
				OHLCEntity ohlcEntity = null;
				int length = array.length();

				/*
				 * int temp = 0; if(length > 100) { temp = length-100; }
				 */
				List<StickEntity> stickList = new ArrayList<StickEntity>();
				// timevol = new LinkedList<StickEntity>();
				ArrayList<ItemGgxqTime> itemGgxqTimeList = new ArrayList<ItemGgxqTime>();
				List<OHLCEntity> ohlcList = new ArrayList<OHLCEntity>();
				double closes = 0;
				for (int i = 0; i < length; i++) {
					item = new ItemGgxqTime();
					String number = array.getJSONObject(i).getString("time");
					item.time = number.substring(0,number.indexOf("."));
					double close = array.getJSONObject(i).optDouble("close", 0);
					item.close = String.valueOf(close);
					// Log.i("SelfActivity", "==parserGgxqTime（）item.close = " +
					// item.close);

					double cjl;
					int color;
					if (i > 0) {
						cjl = (Math.abs(array.getJSONObject(i).optDouble("cjl")
								- array.getJSONObject(i - 1).optDouble("cjl"))) / 100;
						if (close >= array.getJSONObject(i - 1).optDouble(
								"close", 0)) {
							color = 0xFFD0021B;
						} else {
							color = 0xFF7ED321;
						}
					} else {
						cjl = array.getJSONObject(i).optDouble("cjl") / 100;
						if (close >= preclose) {
							color = 0xFFD0021B;
						} else {
							color = 0xFF7ED321;
						}
					}

					item.cjl = String.valueOf(cjl);
					closes += close;
					Log.i("UtilHttp", "---Data---"+Integer.parseInt(item.time) +"--"+item.time );
					int temptime = Integer.parseInt(item.time);
					
					if (temptime < 10000) {
						temptime = temptime + 10000000;
					}

					double cjl1 = array.getJSONObject(i).optDouble("cjl", 0);
					double cje = array.getJSONObject(i).optDouble("cje", 0);

					// new asyncTaskLoadKLineData().execute(String.valueOf(1));
					double gjjlx = 0;
					if (("000001".equals(code) )
							|| ("300072".equals(code) )
							|| ("399006".equals(code) )) {
						if (i == 0) {
							gjjlx = close;
						} else {
							gjjlx = closes / (i + 1);
						}
					} else {
						gjjlx = cje / cjl1;
					}

					stickEntity = new StickEntity(cjl, 0, temptime);
					stickEntity.setColumnColor(color);
					stickList.add(stickEntity);
					itemGgxqTimeList.add(item);

					ohlcEntity = new OHLCEntity(0, 0, 0, close, temptime);
					ohlcEntity.setGjjlx(gjjlx);
					ohlcList.add(ohlcEntity);
				}
				timevol = stickList;
				listGgxqTime = itemGgxqTimeList;
				timeohlc = ohlcList;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 解析明细列表
	 */
	public void parseMX() {

		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONObject jsondata = new JSONObject(
					DataCenter.getInstance().mStrGghq);
			jsondata = jsondata.getJSONObject("resultData");
			JSONArray array = jsondata.getJSONArray("kChart");
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					Map<String, Object> item = new HashMap<String, Object>();
					item.put(
							"code",
							Parse.getInstance().isNull(
									array.getJSONObject(i).get("code")));
					item.put(
							"market",
							Parse.getInstance().isNull(
									array.getJSONObject(i).get("market")));
					item.put(
							"price",
							Parse.getInstance().isNull(
									array.getJSONObject(i).get("hprice")));
					item.put(
							"time",
							Parse.getInstance().isNull(
									array.getJSONObject(i).get("time")));
					item.put(
							"cjl",
							Parse.getInstance().isNull(
									array.getJSONObject(i).get("cjl")));
					item.put(
							"buysell",
							Parse.getInstance().isNull(
									array.getJSONObject(i).get("lprice")));
					list.add(item);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mXList = list;
	}
}

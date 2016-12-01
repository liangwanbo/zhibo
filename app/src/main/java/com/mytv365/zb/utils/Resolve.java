package com.mytv365.zb.utils;

import android.util.Log;
import android.util.Xml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Resolve {

	private final int JSON_NO = 0;
	private final int JSON_OBJECT = 1;
	private final int JSON_ARRAY = 2;

	public static Resolve resolve;

	private Resolve() {

	}

	public static Resolve getInstance() {
		if (resolve == null) {
			resolve = new Resolve();
		}
		return resolve;
	}

	/**
	 * 通用XML解析
	 * 
	 * @param in
	 *            数据流
	 * @return Map〈String,Map〈String,Object〉〉。
	 *         XML标签内部的Text数据，如果是JSONArray格式会自动解析成ArrayList
	 *         <Map<String,Object>>类型， 若是JSONObject格式会解析成Map〈String,Object〉类型。
	 *         Text数据会存储在对应的标签的Map中
	 *         ，key为"text"，取法为Map.get("text")，用的时候需要强转成Map〈String
	 *         ,Object〉或ArrayList〈Map〈String,Object〉〉。
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public Map<String, Map<String, Object>> resolveXML(InputStream in)
			throws XmlPullParserException, IOException {

		boolean bool = true;
		Map<String, Map<String, Object>> map = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(in, "UTF-8");
		int event = pullParser.getEventType();
		long num = 0;
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				bool = true;
				map = new HashMap<String, Map<String, Object>>();
				break;
			case XmlPullParser.START_TAG:
				String name = pullParser.getName();
				Map<String, Object> nameValue = new HashMap<String, Object>();
				int size = pullParser.getAttributeCount();
				for (int i = 0; i < size; i++) {
					String key = pullParser.getAttributeName(i);
					String value = pullParser.getAttributeValue(null, key);
					nameValue.put(key, value);
				}
				Object t = null;
				try {
					t = pullParser.nextText();
					bool = true;
				} catch (Exception e) {
					// TODO: handle exception
					t = "";
					bool = false;
				}
				t = json(t.toString());
				nameValue.put("text", t);
				map.put(name + "," + num++, nameValue);
				break;
			case XmlPullParser.END_TAG:
				bool = true;
				break;

			}
			if (bool)
				event = pullParser.next();
		}
		return map;
	}

	/**
	 * 获取条目集合
	 * 
	 * @param map
	 *            从服务器拿到的数据并解析出来的Map集合
	 * @param item
	 *            需要获得哪一个标签内的数据，item为标签名
	 * @return ArrayList〈Map〈String, Object〉〉，
	 *         XML中的Text取法为list.get(index).get("text")。
	 */
	@SuppressWarnings("unused")
	public ArrayList<Map<String, Object>> getList(
			Map<String, Map<String, Object>> map, String item) {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (map != null) {
			for (int i = 0; i < map.size(); i++) {
				if (map.get(item + "," + i) != null) {
					map.get(item + "," + i).put("POSITION", i);
					list.add(map.get(item + "," + i));
				}
			}
		}
		return list;
	}

	/**
	 * 获取二级标签（二级标签名都不一样时）
	 * 
	 * @param map
	 *            从服务器拿到的数据并解析出来的Map集合
	 * @param item
	 *            一级标签名
	 * @param items
	 *            二级标签名
	 * @return
	 */
	public ArrayList<Map<String, Object>> getList(
			Map<String, Map<String, Object>> map, String item, String... items) {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (map != null) {
			int size = 0;
			int index = 0;
			int ind = 0;
			for (int i = 0; i < map.size(); i++) {
				ind = i;
				if (map.get(item + "," + i) != null) {
					if (index == 2) {
						size = i - size;
						break;
					}
					size = i;
					index++;
				}
			}
			if (index == 1)
				size = ind;
			for (int i = 0; i < map.size(); i++) {
				if (map.get(item + "," + i) != null) {
					list.add(map.get(item + "," + i));
					Map<String, Object> m = new HashMap<String, Object>();
					for (int j = 0; j < items.length; j++) {
						for (int j2 = i; j2 < i + size; j2++) {
							if (map.get(items[j] + "," + j2) != null) {
								m.put(items[j], map.get(items[j] + "," + j2));
								Log.e("解析数据：", map.get(items[j] + "," + j2)
										.toString());
							}
						}
					}
					list.get(list.size() - 1).put("sub", m);
				}
			}
		}
		return list;
	}

	/**
	 * 获取二级标签（二级标签名都相同时）
	 * 
	 * @param map
	 *            从服务器拿到的数据并解析出来的Map集合
	 * @param item
	 *            一级标签名
	 * @param subItem
	 *            二级标签名
	 * @return
	 */
	public ArrayList<Map<String, Object>> getList(
			Map<String, Map<String, Object>> map, String item, String subItem) {
		ArrayList<Map<String, Object>> list = null;
		if (map != null) {
			list = getList(map, item);
			ArrayList<Map<String, Object>> subItemList = getList(map, subItem);
			for (int i = 0; i < list.size(); i++) {
				ArrayList<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
				for (int j = 0; j < subItemList.size(); j++) {
					if (i != list.size() - 1) {
						if (Parse.getInstance().parseInt(
								list.get(i).get("POSITION")) < Parse
								.getInstance().parseInt(
										subItemList.get(j).get("POSITION"))
								&& Parse.getInstance().parseInt(
										list.get(i + 1).get("POSITION")) > Parse
										.getInstance().parseInt(
												subItemList.get(j).get(
														"POSITION"))) {
							subList.add(subItemList.get(j));
						}
					} else {
						if (Parse.getInstance().parseInt(
								list.get(i).get("POSITION")) < Parse
								.getInstance().parseInt(
										subItemList.get(j).get("POSITION"))) {
							subList.add(subItemList.get(j));
						}
					}
					list.get(i).put("subList", subList);
				}
			}
		}
		return list != null ? list : new ArrayList<Map<String, Object>>();
	}

	/**
	 * 万能JSON数据解析
	 * 
	 * @param result
	 *            需要解析的字符串
	 * @return 
	 *         返回Object类型，传进来的是JSONObject类型的数据需强转Map〈String,Object〉类型，传进来时JSONArray
	 *         类型的需强转ArrayList 〈Map〈String,Object〉〉类型的，嵌套数据以此类推。
	 *         Map的key就是JSONObject的key
	 */

	public Object json(String result) {

		int j = isJSON(result);
		try {
			switch (j) {
			case JSON_OBJECT:
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jobj;
				jobj = new JSONObject(result);
				@SuppressWarnings("rawtypes")
				Iterator keyss = jobj.keys();
				while (keyss.hasNext()) {
					String key = (String) keyss.next();
					Object value = jobj.getString(key).toString();
					value = json(value.toString());
					map.put(key, value);
				}
				return map;

			case JSON_ARRAY:
				JSONArray arr = new JSONArray(result);
				ArrayList<Map<String, Object>> text = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < arr.length(); i++) {
					Map<String, Object> textMap = new HashMap<String, Object>();
					JSONObject obj = arr.getJSONObject(i);
					@SuppressWarnings("rawtypes")
					Iterator keys = obj.keys();
					while (keys.hasNext()) {
						String key = (String) keys.next();
						Object value = obj.getString(key).toString();
						value = json(value.toString());
						textMap.put(key, value);
					}
					text.add(textMap);
				}
				return text;

			default:
				return result;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return result;
		}
	}

	/**
	 * 判断是否JSON数据，JSONArray数据，JSONObject数据
	 */
	private int isJSON(String result) {
		if (result != null && result.length() >= 2) {
			String str = result.substring(0, 2);
			if (str.equals("{\"")) {
				return JSON_OBJECT;
			} else if (str.equals("[{")) {
				return JSON_ARRAY;
			}
		}
		return JSON_NO;
	}
}
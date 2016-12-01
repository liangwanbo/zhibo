package com.mytv365.zb.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mytv365.zb.utils.Parse;

import java.util.ArrayList;
import java.util.Map;

public abstract class MyBaseAdapter extends BaseAdapter {

	protected ArrayList<Map<String, Object>> list;// 列表数据
	protected LayoutInflater getInflater;
	protected Context context;// 上下文

	public MyBaseAdapter(ArrayList<Map<String, Object>> list, Context context) {
		if (list == null)
			list = new ArrayList<Map<String, Object>>();
		this.list = list;
		this.context = context;
		getInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public ArrayList<Map<String, Object>> getData() {
		return list != null ? list : new ArrayList<Map<String, Object>>();
	}

	/**
	 * 设置数据并更新适配器
	 * 
	 * @param list
	 */
	public void setData(ArrayList<Map<String, Object>> list) {
		if (list == null)
			list = new ArrayList<Map<String, Object>>();
		this.list = list;
		notifyDataSetChanged();
	}

	/**
	 * 转型用
	 * 
	 * @return
	 */
	public Parse getParse() {
		return Parse.getInstance();
	}

	/**
	 * 设置textview，可以是HTML文本
	 * 
	 * @param key
	 *            集合中的Map的key
	 * @param position
	 *            当前位置
	 * @param textViews
	 *            需要设置的TextView，个数与第一个参数的长度一致，并且设置的要一一对应
	 */
	public void displayText(String[] key, int position,
			TextView... textViews) {
		for (int i = 0; i < textViews.length; i++) {
			textViews[i].setText(Html.fromHtml(getParse().isNull(
					list.get(position).get(key[i]))));
		}
	}
}

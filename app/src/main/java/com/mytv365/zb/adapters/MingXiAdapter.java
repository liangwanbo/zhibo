package com.mytv365.zb.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytv365.zb.R;

import java.util.ArrayList;
import java.util.Map;


public class MingXiAdapter extends MyBaseAdapter {

	private float zd;

	// public MingXiAdapter(ArrayList<Map<String, Object>> list, Context
	// context) {
	// super(list, context);
	// // TODO Auto-generated constructor stub
	// }

	public MingXiAdapter(ArrayList<Map<String, Object>> list, Context context,
			float zd) {
		super(list, context);
		// TODO Auto-generated constructor stub
		this.zd = zd;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = getInflater.inflate(R.layout.item_mingxi, null);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.tv_liang = (TextView) convertView
					.findViewById(R.id.tv_liang);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		String time = getParse().isNull(list.get(position).get("time"));
		if (time.length() < 6) {
			time = "0" + time;
		}
		holder.tv_time.setText(String.format("%s:%s", time.substring(0, 2),
				time.substring(2, 4)));
		String jia = getParse().parse2String(list.get(position).get("price"));
		holder.tv_price.setText(jia.equals("0.00") ? "--" : jia);
		String liang = getParse().parseToCHString(
				getParse().parseInt(list.get(position).get("cjl")) / 100 + "");
		holder.tv_liang.setText(liang.equals("0") ? "--" : liang);
		if (getParse().isNull(list.get(position).get("buysell")).equals("0")) {
			holder.tv_liang.setTextColor(Color.WHITE);
		} else if (getParse().isNull(list.get(position).get("buysell")).equals(
				"1")) {
			holder.tv_liang.setTextColor(context.getResources().getColor(
					R.color.z_red));
		} else {
			holder.tv_liang.setTextColor(context.getResources().getColor(
					R.color.z_green));

		}
		if (zd < 0.0f) {
			holder.tv_price.setTextColor(context.getResources().getColor(
					R.color.z_green));
		} else if (zd > 0.0f) {
			holder.tv_price.setTextColor(context.getResources().getColor(
					R.color.z_red));
		} else {
			holder.tv_price.setTextColor(Color.WHITE);
		}
		return convertView;
	}

	static class Holder {
		TextView tv_time;// 时间
		TextView tv_price;// 价格
		TextView tv_liang;// 量
	}

	public void setData(ArrayList<Map<String, Object>> list, float zd) {
		this.list = list;
		this.zd = zd;
		notifyDataSetChanged();
	}

}

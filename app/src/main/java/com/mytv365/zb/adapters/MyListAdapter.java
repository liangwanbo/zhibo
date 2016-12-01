package com.mytv365.zb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.model.ItemSelf;

import java.util.ArrayList;


public class MyListAdapter extends BaseAdapter {
	private Context mContext;
	private ViewHolder mHolder;
	private ArrayList<ItemSelf> mList;

	public MyListAdapter(Context context, ArrayList<ItemSelf> list) {
		mContext = context;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item, null);
			mHolder = new ViewHolder();
			mHolder.name = (TextView) convertView.findViewById(R.id.name);
			mHolder.price = (TextView) convertView.findViewById(R.id.price);
			mHolder.price2 = (TextView) convertView
					.findViewById(R.id.price2);
			mHolder.increase = (TextView) convertView
					.findViewById(R.id.increase);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

//		mHolder.name.setText(mList.get(position).name);
//		mHolder.price.setText(mList.get(position).price);
//		mHolder.price2.setText(mList.get(position).price2);
//		mHolder.increase.setText(mList.get(position).increase);
		return convertView;
	}

	class ViewHolder {
		TextView name;//
		TextView price;//
		TextView price2;//
		TextView increase;//
	}
}
package com.mytv365.zb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.model.ItemStock;

public class HotPanelAdapter extends BaseAdapter {
	private Context context;
	private ViewHolder mHolder;
	
	public HotPanelAdapter(Context context) {
		super();
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(DataCenter.getInstance().listHot != null)
		{
			return DataCenter.getInstance().listHot.size();
		}
		
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(DataCenter.getInstance().listHot != null)
		{
			return DataCenter.getInstance().listHot.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_hot, null);
			mHolder = new ViewHolder();
			mHolder.hotname = (TextView) convertView.findViewById(R.id.hotname);
			mHolder.hotzf = (TextView) convertView.findViewById(R.id.hotzf);
			mHolder.hotzl = (TextView) convertView.findViewById(R.id.hotzl);
			
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		ItemStock item = DataCenter.getInstance().listHot.get(position);
		mHolder.hotname.setText(item.name);
		mHolder.hotzf.setText(item.code);
		mHolder.hotzl.setText(item.cjl);
		
		return convertView;
		
	}
	
	class ViewHolder {
		TextView hotname;
		TextView hotzf;  //xx%
		TextView hotzl;
		
	}
	
	
}

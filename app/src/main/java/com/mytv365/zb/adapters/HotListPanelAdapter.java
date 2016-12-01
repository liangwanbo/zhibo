package com.mytv365.zb.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.utils.Parse;
import com.mytv365.zb.utils.UtilFont;
import com.mytv365.zb.utils.UtilMath;

import java.util.HashMap;
import java.util.LinkedList;

public class HotListPanelAdapter extends BaseAdapter {
	
	
	private Context mContext;
	private ViewHolder mHolder;
	private LinkedList<HashMap<String,String>> mList;
	public HotListPanelAdapter(Context context,LinkedList<HashMap<String,String>> datalist) {
		super();
		// TODO Auto-generated constructor stub
		mContext = context;
		mList = datalist;
	}

	@Override
	public int getCount() {
		if(mList != null)
		{
			return mList.size();
		}
		return 0;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_hot_block, null);
			mHolder = new ViewHolder();
			mHolder.name = (TextView) convertView.findViewById(R.id.hotname);
			mHolder.change = (TextView) convertView.findViewById(R.id.hotchange);
			mHolder.cje = (TextView) convertView.findViewById(R.id.hotcje);
			
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		
		SpannableString sp = new SpannableString(mList.get(position).get("name") + "\n"
				+ mList.get(position).get("code"));
		sp.setSpan(new RelativeSizeSpan(1.0f), 0,
				(mList.get(position).get("name") + "\n").length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(0.75f),
				(mList.get(position).get("name") + "\n").length(), (mList.get(position).get("name")
						+ "\n" + mList.get(position).get("code")).length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mHolder.name.setText(sp);
		
		//mHolder.name.setText(mList.get(position).get("name"));
	
		mHolder.change.setText(UtilFont.setFontStyle(UtilMath.getIncrease_(mList.get(position).get("changep")),
				UtilFont.getColor(mList.get(position).get("changep"))));
		mHolder.cje.setText( Parse.getInstance()
				.parse2CNString(mList.get(position).get("cje")));
		return convertView;
	}


	class ViewHolder {
		TextView name;

		TextView change;
		TextView cje;
	}
}

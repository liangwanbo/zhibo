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
import com.mytv365.zb.views.activity.stock.IncreaseActivity;

import java.util.HashMap;
import java.util.LinkedList;


public class EmListSelfAdapter extends BaseAdapter {
	private Context mContext;
	private ViewHolder mHolder;
	private LinkedList<HashMap<String, String>> mList;

	public EmListSelfAdapter(IncreaseActivity mActivity,
			LinkedList<HashMap<String, String>> list) {
		mContext = mActivity;
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
					R.layout.item_my_stock, null);
			mHolder = new ViewHolder();
			mHolder.name = (TextView) convertView.findViewById(R.id.name);
			mHolder.close = (TextView) convertView.findViewById(R.id.close);
			mHolder.change = (TextView) convertView.findViewById(R.id.change);
			mHolder.changep = (TextView) convertView.findViewById(R.id.changep);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.name.setText(mList.get(position).get("name") + "\n"
				+ mList.get(position).get("code"));

		SpannableString sp = new SpannableString(mList.get(position)
				.get("name") + "\n" + mList.get(position).get("code"));
		sp.setSpan(new RelativeSizeSpan(1.0f), 0,
				(mList.get(position).get("name") + "\n").length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(0.75f), (mList.get(position)
				.get("name") + "\n").length(), (mList.get(position).get("name")
				+ "\n" + mList.get(position).get("code")).length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mHolder.name.setText(sp);

		mHolder.close.setText(UtilFont.setFontStyle(Parse.getInstance()
				.parse2String(
				mList.get(position).get("close")), UtilFont.getColor(mList.get(position).get("change"))));
		mHolder.change.setText(UtilFont.setFontStyle(Parse
				.getInstance().parse2String(
				mList.get(position).get("change")),
				UtilFont.getColor(mList.get(position).get("change"))));
		mHolder.changep.setText(UtilFont.setFontStyle(
				UtilMath.getIncrease_(mList.get(position).get("changep")),
				UtilFont.getColor(mList.get(position).get("changep"))));
		return convertView;
	}

	class ViewHolder {
		TextView name;
		TextView close;
		TextView change;
		TextView changep;
	}
}
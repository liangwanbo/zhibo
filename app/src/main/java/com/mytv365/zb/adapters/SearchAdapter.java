package com.mytv365.zb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.receiver.OnItemClickListener;
import com.mytv365.zb.utils.Parse;

import java.util.HashMap;
import java.util.LinkedList;

public class SearchAdapter extends BaseAdapter {
	SearchItemHolder holder;
	Context searchContext;
	private OnItemClickListener l;
	LinkedList<HashMap<String, String>> dataList;

	public SearchAdapter(Context context,
			LinkedList<HashMap<String, String>> dataList) {
		searchContext = context;
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (dataList != null) {
			return dataList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (dataList != null) {
			return dataList.get(position);
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
			convertView = LayoutInflater.from(searchContext).inflate(
					R.layout.search_item, null);
			holder = new SearchItemHolder();
			holder.rl_parent = (RelativeLayout) convertView
					.findViewById(R.id.rl_parent);
			holder.codeTextView = (TextView) convertView
					.findViewById(R.id.searchcode);
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.searchName);
			holder.addButton = (Button) convertView
					.findViewById(R.id.addbuttonid);
			holder.clearButton = (Button) convertView
					.findViewById(R.id.cleebuttonid);
			holder.history_title = (TextView) convertView
					.findViewById(R.id.history_title);
			convertView.setTag(holder);
		} else {
			holder = (SearchItemHolder) convertView.getTag();
		}
		HashMap<String, String> itemstock = dataList.get(position);
		holder.codeTextView.setText(Parse.getInstance().isNull(
				itemstock.get("code")));
		holder.nameTextView.setText(Parse.getInstance().isNull(
				itemstock.get("name")));
		boolean isAdd = Parse.getInstance().parseBool(itemstock.get("isAdd"));
		if (isAdd) {
			holder.addButton.setVisibility(View.GONE);
			holder.clearButton.setVisibility(View.VISIBLE);
		} else {
			holder.addButton.setVisibility(View.VISIBLE);
			holder.clearButton.setVisibility(View.GONE);
		}
		if (Parse.getInstance().parseBool(itemstock.get("isShowTitle"))) {
			holder.history_title.setVisibility(View.VISIBLE);
		} else {
			holder.history_title.setVisibility(View.GONE);
		}
		holder.addButton.setOnClickListener(new MyOnclickListener(dataList,
				position));
		holder.clearButton.setOnClickListener(new MyOnclickListener(dataList,
				position));
		holder.rl_parent.setOnClickListener(new MyOnclickListener(dataList,
				position));
		return convertView;
	}

	class SearchItemHolder {
		RelativeLayout rl_parent;// 父布局
		TextView codeTextView;
		TextView nameTextView;
		Button addButton;
		Button clearButton;
		TextView history_title;

	}

	public void setOnItemClickListener(OnItemClickListener l) {
		this.l = l;
	}

	class MyOnclickListener implements OnClickListener {

		private LinkedList<HashMap<String, String>> list;
		private int position;

		public MyOnclickListener(LinkedList<HashMap<String, String>> list,
				int position) {
			this.list = list;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (l != null)
				l.onItemClickListener(v, position, list);
		}

	}

}

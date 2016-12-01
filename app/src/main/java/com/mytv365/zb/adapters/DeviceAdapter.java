package com.mytv365.zb.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.receiver.OnItemClickListener1;
import com.mytv365.zb.utils.Parse;

import java.util.HashMap;
import java.util.List;


public class DeviceAdapter extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	List<HashMap<String, String>> device;
	public int foodpoition = 0;
	private boolean isShowNewsAndF10;// 是否显示新闻与F10
	private OnItemClickListener1 l;// 点击事件接口
	private int height;// 屏幕高度（不包含状态栏）

	private int selectedPosition = -1;

	private int id;

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public DeviceAdapter(Context context, List<HashMap<String, String>> device,
			int position, boolean isShowNewsAndF10, int height) {
		this.context = context;
		this.device = device;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.foodpoition = position;
		this.isShowNewsAndF10 = isShowNewsAndF10;
		this.height = height;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (device == null) {
			return 0;
		}
		return device.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.xwgg_item, null);
			viewHolder = new ViewHolder();
			viewHolder.titletextView = (TextView) convertView
					.findViewById(R.id.titleids);
			viewHolder.datetitletextView = (TextView) convertView
					.findViewById(R.id.datestid);

			viewHolder.rl_item = (RelativeLayout) convertView
					.findViewById(R.id.rl_item);
			viewHolder.layout_title = (LinearLayout) convertView
					.findViewById(R.id.layout_title);
			viewHolder.tv_news = (TextView) convertView
					.findViewById(R.id.tv_news);
			viewHolder.tv_notice = (TextView) convertView
					.findViewById(R.id.tv_notice);
			viewHolder.tv_f10 = (TextView) convertView
					.findViewById(R.id.tv_f10);
			viewHolder.rl_refresh = (RelativeLayout) convertView
					.findViewById(R.id.rl_refresh);
			viewHolder.pb_progress = (ProgressBar) convertView
					.findViewById(R.id.pb_progress);
			viewHolder.rl_loadmore = (RelativeLayout) convertView
					.findViewById(R.id.rl_loadmore);
			viewHolder.pb_loadmore_progress = (ProgressBar) convertView
					.findViewById(R.id.pb_loadmore_progress);
			viewHolder.tv_loadmore = (TextView) convertView
					.findViewById(R.id.tv_loadmore);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_news.setBackgroundColor(0xff1A2C43);
		viewHolder.tv_notice.setBackgroundColor(0xff1A2C43);
		viewHolder.tv_f10.setBackgroundColor(0xff1A2C43);
		viewHolder.rl_loadmore.setVisibility(View.GONE);
		if (position == 0) {
			viewHolder.tv_news.setTag(false);
			viewHolder.tv_notice.setTag(false);
			viewHolder.tv_f10.setTag(false);
			if (id == viewHolder.tv_news.getId()) {
				viewHolder.tv_news.setTag(true);
			} else if (id == viewHolder.tv_notice.getId()) {
				viewHolder.tv_notice.setTag(true);
			} else {
				viewHolder.tv_f10.setTag(true);
			}
			viewHolder.layout_title.setVisibility(View.VISIBLE);
			if (isShowNewsAndF10) {
				viewHolder.tv_news.setVisibility(View.VISIBLE);
				viewHolder.tv_notice.setVisibility(View.VISIBLE);
				viewHolder.tv_f10.setVisibility(View.VISIBLE);
				viewHolder.tv_news.setTextColor(context.getResources()
						.getColor(R.color.z_list_title));
				viewHolder.tv_notice.setTextColor(context.getResources()
						.getColor(R.color.z_list_title));
				viewHolder.tv_f10.setTextColor(context.getResources().getColor(
						R.color.z_list_title));
				viewHolder.tv_news.setOnClickListener(new MyOnClickListener(
						position, device));
				viewHolder.tv_f10.setOnClickListener(new MyOnClickListener(
						position, device));
				if (Parse.getInstance().parseBool(viewHolder.tv_news.getTag())) {
					viewHolder.tv_news.setBackgroundColor(context
							.getResources().getColor(R.color.z_colorblock));
					viewHolder.tv_news.setTextColor(Color.WHITE);
				} else if (Parse.getInstance().parseBool(
						viewHolder.tv_notice.getTag())) {
					viewHolder.tv_notice.setBackgroundColor(context
							.getResources().getColor(R.color.z_colorblock));
					viewHolder.tv_notice.setTextColor(Color.WHITE);
				} else {
					viewHolder.tv_f10.setBackgroundColor(context.getResources()
							.getColor(R.color.z_colorblock));
					viewHolder.tv_f10.setTextColor(Color.WHITE);
				}
			} else {
				viewHolder.tv_news.setVisibility(View.GONE);
				viewHolder.tv_notice.setVisibility(View.VISIBLE);
				viewHolder.tv_f10.setVisibility(View.GONE);
				viewHolder.tv_notice.setBackgroundColor(context.getResources()
						.getColor(R.color.z_colorblock));
			}
			viewHolder.tv_notice.setOnClickListener(new MyOnClickListener(
					position, device));

		} else {
			viewHolder.layout_title.setVisibility(View.GONE);
		}
		viewHolder.rl_refresh.setVisibility(View.GONE);
		viewHolder.rl_item.setVisibility(View.GONE);
		viewHolder.pb_progress.setVisibility(View.GONE);
		viewHolder.tv_loadmore.setText("点击加载更多");
		if (Parse.getInstance().parseBool(device.get(0).get("isRefresh"))) {
			viewHolder.rl_refresh.setVisibility(View.VISIBLE);
			viewHolder.pb_progress.setVisibility(View.VISIBLE);
		} else if (Parse.getInstance().parseBool(device.get(0).get("isNo"))) {
			viewHolder.rl_loadmore.setVisibility(View.VISIBLE);
			viewHolder.tv_loadmore.setVisibility(View.VISIBLE);
			viewHolder.tv_loadmore.setText("暂无");
			viewHolder.tv_loadmore.setClickable(false);
		} else if (Parse.getInstance()
				.parseBool(device.get(0).get("isShowF10"))) {
			viewHolder.rl_item.setVisibility(View.GONE);
		} else {
			viewHolder.rl_item.setVisibility(View.VISIBLE);
			viewHolder.titletextView.setText(device.get(position).get("title"));
			viewHolder.datetitletextView.setText(device.get(position).get(
					"date"));
			viewHolder.rl_item.setOnClickListener(new MyOnClickListener(
					position, device));
		}
		LayoutParams lp = viewHolder.rl_refresh.getLayoutParams();
		lp.height = height;
		viewHolder.rl_refresh.setLayoutParams(lp);
		if (position == device.size() - 1 && device.size() > 1) {
			if (Parse.getInstance().parseBool(viewHolder.tv_news.getTag())) {
				viewHolder.rl_loadmore.setVisibility(View.VISIBLE);
				viewHolder.pb_loadmore_progress.setVisibility(View.GONE);
				viewHolder.tv_loadmore.setVisibility(View.GONE);
				if (Parse.getInstance().parseBool(
						device.get(position).get("isLoading"))) {
					viewHolder.pb_loadmore_progress.setVisibility(View.VISIBLE);
				} else {
					viewHolder.tv_loadmore.setVisibility(View.VISIBLE);
					viewHolder.tv_loadmore
							.setOnClickListener(new MyOnClickListener(position,
									device));
				}
			} else if (Parse.getInstance().parseBool(
					viewHolder.tv_notice.getTag())) {
				viewHolder.rl_loadmore.setVisibility(View.VISIBLE);
				viewHolder.pb_loadmore_progress.setVisibility(View.GONE);
				viewHolder.tv_loadmore.setVisibility(View.GONE);
				if (Parse.getInstance().parseBool(
						device.get(position).get("isLoading"))) {
					viewHolder.pb_loadmore_progress.setVisibility(View.VISIBLE);
				} else {
					viewHolder.tv_loadmore.setVisibility(View.VISIBLE);
					viewHolder.tv_loadmore
							.setOnClickListener(new MyOnClickListener(position,
									device));
				}
			}
		}
		return convertView;
	}

	public static class ViewHolder {
		public TextView titletextView;
		public TextView datetitletextView;
		RelativeLayout rl_item;// 内容布局
		LinearLayout layout_title;// 标题
		TextView tv_news;// 新闻
		TextView tv_notice;// 公告
		TextView tv_f10;// f10
		RelativeLayout rl_refresh;// 刷新布局
		ProgressBar pb_progress;// 进度条
		RelativeLayout rl_loadmore;// 加载更多布局
		ProgressBar pb_loadmore_progress;//  加载更多时的进度条
		TextView tv_loadmore;// 点击加载更多按钮
	}

	/**
	 * 设置点击事件
	 * 
	 * @param l
	 */
	public void setOnItemClickListener(OnItemClickListener1 l) {
		this.l = l;
	}

	/**
	 * 设置数据
	 * 
	 * @param device
	 * @param isShowNewsAndF10
	 */
	public void setData(List<HashMap<String, String>> device,
			boolean isShowNewsAndF10) {
		this.device = device;
		this.isShowNewsAndF10 = isShowNewsAndF10;
		notifyDataSetChanged();
	}

	class MyOnClickListener implements View.OnClickListener {

		private int position;
		private List<HashMap<String, String>> list;

		public MyOnClickListener(int position,
				List<HashMap<String, String>> list) {
			this.position = position;
			this.list = list;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (l != null)
				l.onItemClickListener(v, position, list);
		}
	}

	/**
	 * 设置标题栏按钮背景色
	 * 
	 * @param id
	 */
	public void setTitleBackground(int id) {
		this.id = id;
	}

}

package com.mytv365.zb.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.model.ItemMyStock;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.UtilFont;
import com.mytv365.zb.utils.UtilHttp;
import com.mytv365.zb.utils.UtilMath;
import com.mytv365.zb.widget.stock.SlideView;
import com.mytv365.zb.widget.stock.SlideView.OnSlideListener;
import com.mytv365.zb.widget.stock.XSlideListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class ListSelfAdapter extends BaseAdapter implements OnSlideListener{
	private Context mContext;
	private ArrayList<ItemMyStock> mList;
	private SlideView mLastSlideViewWithStatusOn;
	private XSlideListView mListView;
	public ListSelfAdapter(Context context, ArrayList<ItemMyStock> list, XSlideListView listView) {
		mContext = context;
		mList = list;
		mListView = listView;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder mHolder;
		SlideView slideView = (SlideView) convertView;
		if (slideView == null) {
			View itemView = LayoutInflater.from(mContext).inflate(
					R.layout.item_my_stocks, null);
			slideView = new SlideView(mContext.getApplicationContext(),
					R.layout.slide_view_merge, R.id.view_content);
			slideView.setMainContentView(itemView);
			mHolder = new ViewHolder(slideView);
//			if (position % 2 == 0) {
//				slideView.setmHolderWidth(
//						mListView.getRightViewWidth() / 2,
//						mHolder.llRightPart);
//				mHolder.detail.setVisibility(View.GONE);
//			} else {
//				slideView.setmHolderWidth(mListView.getRightViewWidth(),
//						mHolder.llRightPart);
//				mHolder.detail.setVisibility(View.VISIBLE);
//			}
			slideView.setOnSlideListener(this);
			slideView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) slideView.getTag();
		}
		// mHolder.name.setText(mList.get(position).name + "\n"
		// + mList.get(position).code);
		mList.get(position).slideView = slideView;
		mList.get(position).slideView.shrink();
		SpannableString sp = new SpannableString(mList.get(position).name
				+ "\n" + mList.get(position).code);
		sp.setSpan(new RelativeSizeSpan(1.0f), 0,
				(mList.get(position).name + "\n").length(),
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new RelativeSizeSpan(0.75f),
				(mList.get(position).name + "\n").length(),
				(mList.get(position).name + "\n" + mList.get(position).code)
						.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		mHolder.name.setText(sp);

//		mHolder.close.setText(UtilFont.setFontStyle(mList.get(position).close,
//				UtilFont.getColor(mList.get(position).changep)));
		mHolder.close.setText(UtilFont.setSelfStyle(mList.get(position).close));

//		mHolder.change.setText(UtilFont.setFontStyle(
//				mList.get(position).change,
//				UtilFont.getColor(mList.get(position).change)));

//
		if(UtilMath.getIncrease_(mList.get(position).changep).indexOf("-") != -1){
			mHolder.changep.setBackgroundResource(R.drawable.btn_bg_styles);
		}else{
			mHolder.changep.setBackgroundResource(R.drawable.btn_bg_style);
		}
		mHolder.changep.setText(UtilFont.setSelfStyle(
				UtilMath.getIncrease_(mList.get(position).changep)));

//		mHolder.detail.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (mLastSlideViewWithStatusOn != null) {
//					mLastSlideViewWithStatusOn.smoothScrollTo(0, 0);
//					mListView.setViewStatus(SLIDE_STATUS_OFF);
//				}
//
//			}
//		});
		mHolder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mLastSlideViewWithStatusOn != null) {
					mLastSlideViewWithStatusOn.smoothScrollTo(0, 0);
					mListView.setViewStatus(SLIDE_STATUS_OFF);
				}
				String code = mList.get(position).code;
					OkGo.get(UtilHttp.URL_DEL_SELF
					)
							.headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
							.headers("header1", "headerValue1")
							.params("stockNo", code)
							.setCertificates()
							.execute(new StringCallback() {
								@Override
								public void onSuccess(String s, Call call, Response response) {
									try {
										JSONObject object=new JSONObject(s);
										Log.i("ufo", "--s--"+s);
										int resultcode=object.getInt("resultCode");
										String resultMessage =object.getString("resultMessage");
										if(resultcode == 100){
											MyUtils.getInstance().showToast(mContext.getApplicationContext(),
													"删除成功！");
											mList.remove(position);
											if(mList.size() == 0) {
												MyUtils.getInstance().showToast(mContext.getApplicationContext(),
														"股票,已删完！");
											}
											notifyDataSetChanged();
										}else{
											MyUtils.getInstance().showToast(mContext.getApplicationContext(),
													resultMessage);
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}

								@Override
								public void onError(Call call, Response response, Exception e) {
									super.onError(call, response, e);
								}
							});
			}
		});
		return slideView;
	}

	public void setData(ArrayList<ItemMyStock> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}

	class ViewHolder {
		public TextView name;
		public TextView close;
		public TextView change;
		public TextView changep;
		public TextView detail, delete;
		public LinearLayout llRightPart;

		ViewHolder(View view) {
			name = (TextView) view.findViewById(R.id.name);
			close = (TextView) view.findViewById(R.id.close);
			change = (TextView) view.findViewById(R.id.change);
			changep = (TextView) view.findViewById(R.id.changep);
//			detail = (TextView) view.findViewById(R.id.detail);
			delete = (TextView) view.findViewById(R.id.delete);
//			llRightPart = (LinearLayout) view
//					.findViewById(R.id.llRightPart);
		}
	}
	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();// 收回右部分
		}
		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
		mListView.setViewStatus(status);// 改变该itemView的状态
	}

}
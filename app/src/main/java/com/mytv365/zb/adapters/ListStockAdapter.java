package com.mytv365.zb.adapters;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.model.ItemStock;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Parse;
import com.mytv365.zb.utils.UtilFont;
import com.mytv365.zb.utils.UtilMath;
import com.mytv365.zb.views.activity.stock.HshqActivity;

import java.util.ArrayList;


public class ListStockAdapter extends BaseAdapter {
	private HshqActivity mContext;
	private ViewHolder mHolder;
	private ArrayList<ItemStock> mList;
	private final int typ0 = 0;
	private final int typ1 = 1;
	private final double dw = 100000000;
	ViewHotHolder viewHotHolder = null;

	public ListStockAdapter(HshqActivity mActivity, ArrayList<ItemStock> list) {
		mContext = mActivity;
		mList = list;
	}

	@Override
	public int getCount() {
		return DataCenter.getInstance().listStockUp.size();
	}

	@Override
	public Object getItem(int arg0) {

		return DataCenter.getInstance().listStockUp.get(arg0);

	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if (position == 1) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		int type = getItemViewType(position);

		if (convertView == null) {
			switch (type) {
			case typ0: {
				viewHotHolder = new ViewHotHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.hshq_hotitem, null);
				viewHotHolder.hotname1 = (TextView) convertView
						.findViewById(R.id.hotname1);
				viewHotHolder.hotzf1 = (TextView) convertView
						.findViewById(R.id.hotzf1);
				viewHotHolder.hotzl1 = (TextView) convertView
						.findViewById(R.id.hotzl1);

				viewHotHolder.hotname2 = (TextView) convertView
						.findViewById(R.id.hotname2);
				viewHotHolder.hotzf2 = (TextView) convertView
						.findViewById(R.id.hotzf2);
				viewHotHolder.hotzl2 = (TextView) convertView
						.findViewById(R.id.hotzl2);

				viewHotHolder.hotname3 = (TextView) convertView
						.findViewById(R.id.hotname3);
				viewHotHolder.hotzf3 = (TextView) convertView
						.findViewById(R.id.hotzf3);
				viewHotHolder.hotzl3 = (TextView) convertView
						.findViewById(R.id.hotzl3);

				viewHotHolder.hotname4 = (TextView) convertView
						.findViewById(R.id.hotname4);
				viewHotHolder.hotzf4 = (TextView) convertView
						.findViewById(R.id.hotzf4);
				viewHotHolder.hotzl4 = (TextView) convertView
						.findViewById(R.id.hotzl4);

				viewHotHolder.hotname5 = (TextView) convertView
						.findViewById(R.id.hotname5);
				viewHotHolder.hotzf5 = (TextView) convertView
						.findViewById(R.id.hotzf5);
				viewHotHolder.hotzl5 = (TextView) convertView
						.findViewById(R.id.hotzl5);

				viewHotHolder.hotname6 = (TextView) convertView
						.findViewById(R.id.hotname6);
				viewHotHolder.hotzf6 = (TextView) convertView
						.findViewById(R.id.hotzf6);
				viewHotHolder.hotzl6 = (TextView) convertView
						.findViewById(R.id.hotzl6);

				viewHotHolder.linayLout1 = (LinearLayout) convertView
						.findViewById(R.id.hot1pid);
				viewHotHolder.linayLout2 = (LinearLayout) convertView
						.findViewById(R.id.hot2pid);
				viewHotHolder.linayLout3 = (LinearLayout) convertView
						.findViewById(R.id.hot3pid);
				viewHotHolder.linayLout4 = (LinearLayout) convertView
						.findViewById(R.id.hot4pid);
				viewHotHolder.linayLout5 = (LinearLayout) convertView
						.findViewById(R.id.hot5pid);
				viewHotHolder.linayLout6 = (LinearLayout) convertView
						.findViewById(R.id.hot6pid);
				convertView.setTag(viewHotHolder);

				viewHotHolder.linayLout1
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mContext.gotoZyBack(0);
							}
						});
				viewHotHolder.linayLout2
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mContext.gotoZyBack(1);
							}
						});
				viewHotHolder.linayLout3
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mContext.gotoZyBack(2);
							}
						});
				viewHotHolder.linayLout4
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mContext.gotoZyBack(3);
							}
						});
				viewHotHolder.linayLout5
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mContext.gotoZyBack(4);
							}
						});
				viewHotHolder.linayLout6
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								mContext.gotoZyBack(5);
							}
						});

				break;
			}
			case typ1: {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_my_stock, null);
				mHolder = new ViewHolder();
				mHolder.name = (TextView) convertView.findViewById(R.id.name);
				// mHolder.code = (TextView)
				// convertView.findViewById(R.id.code);
				mHolder.close = (TextView) convertView.findViewById(R.id.close);
				mHolder.change = (TextView) convertView
						.findViewById(R.id.change);
				mHolder.changep = (TextView) convertView
						.findViewById(R.id.changep);
				mHolder.iconText = (TextView) convertView
						.findViewById(R.id.iconrow);
				mHolder.headTitleView = (TextView) convertView
						.findViewById(R.id.itemheadview);
				mHolder.headDivider = (View) convertView
						.findViewById(R.id.headDivider);
				mHolder.z_layout_Title = (LinearLayout) convertView
						.findViewById(R.id.z_layout_title);
				mHolder.divider = (View) convertView.findViewById(R.id.divider);
				mHolder.ll_parent = (LinearLayout) convertView
						.findViewById(R.id.ll_parent);
				convertView.setTag(mHolder);

				break;
			}
			default:
				break;
			}

		} else {
			switch (type) {
			case typ0: {
				viewHotHolder = (ViewHotHolder) convertView.getTag();
				break;
			}
			case typ1: {
				mHolder = (ViewHolder) convertView.getTag();
				break;
			}
			default:
				break;

			}

		}

		switch (type) {
		// 沪深行情 6板块 数据
		case typ0: {
			if (0 < DataCenter.getInstance().listHot.size()) {
				ItemStock item = DataCenter.getInstance().listHot.get(0);
				viewHotHolder.hotname1.setText(item.name);
				String chageepstr = null;
				try {
					double changeps = Double.parseDouble(item.changep) * 100;
					chageepstr = String.format("%.2f", changeps);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					chageepstr = item.changep;
				}
				double db = Parse.getInstance().parseDouble(chageepstr);
				if (db > 0) {
					viewHotHolder.hotzf1.setTextColor(mContext.getResources()
							.getColor(R.color.z_red));
					chageepstr = "+" + chageepstr + "%";
				} else {
					if (db < 0) {
						viewHotHolder.hotzf1.setTextColor(mContext
								.getResources().getColor(R.color.z_green));
					} else {
						viewHotHolder.hotzf1.setTextColor(Color.WHITE);
					}
					chageepstr = chageepstr + "%";
				}
				viewHotHolder.hotzf1.setText(chageepstr);
				String cjestr = null;
				try {

					if (item.cje.length() < 13) {
						double changeps = Double.parseDouble(item.cje) / dw;
						cjestr = String.format("%.2f亿", changeps);
					} else if (item.cje.length() >= 13) {
						double changeps = (Double.parseDouble(item.cje) / dw) / 10000;
						cjestr = String.format("%.2f万亿", changeps);
					}

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					cjestr = item.cje;
				}
				viewHotHolder.hotzl1.setText(cjestr);
			}
			if (1 < DataCenter.getInstance().listHot.size()) {
				ItemStock item = DataCenter.getInstance().listHot.get(1);
				viewHotHolder.hotname2.setText(item.name);
				String chageepstr = null;
				try {
					double changeps = Double.parseDouble(item.changep) * 100;
					chageepstr = String.format("%.2f", changeps);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					chageepstr = item.changep;
				}
				double db = Parse.getInstance().parseDouble(chageepstr);
				if (db > 0) {
					viewHotHolder.hotzf2.setTextColor(mContext.getResources()
							.getColor(R.color.z_red));
					chageepstr = "+" + chageepstr + "%";
				} else {
					if (db < 0) {
						viewHotHolder.hotzf2.setTextColor(mContext
								.getResources().getColor(R.color.z_green));
					} else {
						viewHotHolder.hotzf2.setTextColor(Color.WHITE);
					}
					chageepstr = chageepstr + "%";
				}
				viewHotHolder.hotzf2.setText(chageepstr);
				String cjestr = null;
				try {

					if (item.cje.length() < 13) {
						double changeps = Double.parseDouble(item.cje) / dw;
						cjestr = String.format("%.2f亿", changeps);
					} else if (item.cje.length() >= 13) {
						double changeps = (Double.parseDouble(item.cje) / dw) / 10000;
						cjestr = String.format("%.2f万亿", changeps);
					}

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					cjestr = item.cje;
				}
				viewHotHolder.hotzl2.setText(cjestr);
			}

			if (2 < DataCenter.getInstance().listHot.size()) {
				ItemStock item = DataCenter.getInstance().listHot.get(2);
				viewHotHolder.hotname3.setText(item.name);
				String chageepstr = null;
				try {
					double changeps = Double.parseDouble(item.changep) * 100;
					chageepstr = String.format("%.2f", changeps);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					chageepstr = item.changep;
				}
				double db = Parse.getInstance().parseDouble(chageepstr);
				if (db > 0) {
					viewHotHolder.hotzf3.setTextColor(mContext.getResources()
							.getColor(R.color.z_red));
					chageepstr = "+" + chageepstr + "%";
				} else {
					if (db < 0) {
						viewHotHolder.hotzf3.setTextColor(mContext
								.getResources().getColor(R.color.z_green));
					} else {
						viewHotHolder.hotzf3.setTextColor(Color.WHITE);
					}
					chageepstr = chageepstr + "%";
				}
				viewHotHolder.hotzf3.setText(chageepstr);
				String cjestr = null;
				try {
					if (item.cje.length() < 13) {
						double changeps = Double.parseDouble(item.cje) / dw;
						cjestr = String.format("%.2f亿", changeps);
					} else if (item.cje.length() >= 13) {
						double changeps = (Double.parseDouble(item.cje) / dw) / 10000;
						cjestr = String.format("%.2f万亿", changeps);
					}

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					cjestr = item.cje;
				}
				viewHotHolder.hotzl3.setText(cjestr);
			}

			if (3 < DataCenter.getInstance().listHot.size()) {
				ItemStock item = DataCenter.getInstance().listHot.get(3);
				viewHotHolder.hotname4.setText(item.name);
				String chageepstr = null;
				try {
					double changeps = Double.parseDouble(item.changep) * 100;
					chageepstr = String.format("%.2f", changeps);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					chageepstr = item.changep;
				}
				double db = Parse.getInstance().parseDouble(chageepstr);
				if (db > 0) {
					viewHotHolder.hotzf4.setTextColor(mContext.getResources()
							.getColor(R.color.z_red));
					chageepstr = "+" + chageepstr + "%";
				} else {
					if (db < 0) {
						viewHotHolder.hotzf4.setTextColor(mContext
								.getResources().getColor(R.color.z_green));
					} else
						viewHotHolder.hotzf4.setTextColor(Color.WHITE);
					chageepstr = chageepstr + "%";
				}
				viewHotHolder.hotzf4.setText(chageepstr);
				String cjestr = null;
				try {

					if (item.cje.length() < 13) {
						double changeps = Double.parseDouble(item.cje) / dw;
						cjestr = String.format("%.2f亿", changeps);
					} else if (item.cje.length() >= 13) {
						double changeps = (Double.parseDouble(item.cje) / dw) / 10000;
						cjestr = String.format("%.2f万亿", changeps);
					}

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					cjestr = item.cje;
				}
				viewHotHolder.hotzl4.setText(cjestr);
			}

			if (4 < DataCenter.getInstance().listHot.size()) {
				ItemStock item = DataCenter.getInstance().listHot.get(4);
				viewHotHolder.hotname5.setText(item.name);
				String chageepstr = null;
				try {
					double changeps = Double.parseDouble(item.changep) * 100;
					chageepstr = String.format("%.2f", changeps);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					chageepstr = item.changep;
				}
				double db = Parse.getInstance().parseDouble(chageepstr);
				if (db > 0) {
					viewHotHolder.hotzf5.setTextColor(mContext.getResources()
							.getColor(R.color.z_red));
					chageepstr = "+" + chageepstr + "%";
				} else {
					if (db < 0)
						viewHotHolder.hotzf5.setTextColor(mContext
								.getResources().getColor(R.color.z_green));
					else
						viewHotHolder.hotzf5.setTextColor(Color.WHITE);
					chageepstr = chageepstr + "%";
				}
				viewHotHolder.hotzf5.setText(chageepstr);
				String cjestr = null;
				try {
					if (item.cje.length() < 13) {
						double changeps = Double.parseDouble(item.cje) / dw;
						cjestr = String.format("%.2f亿", changeps);
					} else if (item.cje.length() >= 13) {
						double changeps = (Double.parseDouble(item.cje) / dw) / 10000;
						cjestr = String.format("%.2f万亿", changeps);
					}

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					cjestr = item.cje;
				}
				viewHotHolder.hotzl5.setText(cjestr);
			}
			if (5 < DataCenter.getInstance().listHot.size()) {
				ItemStock item = DataCenter.getInstance().listHot.get(5);
				viewHotHolder.hotname6.setText(item.name);
				String chageepstr = null;
				try {
					double changeps = Double.parseDouble(item.changep) * 100;
					chageepstr = String.format("%.2f", changeps);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					chageepstr = item.changep;
				}
				double db = Parse.getInstance().parseDouble(chageepstr);
				if (db > 0) {
					viewHotHolder.hotzf6.setTextColor(mContext.getResources()
							.getColor(R.color.z_red));
					chageepstr = "+" + chageepstr + "%";
				} else {
					if (db < 0)
						viewHotHolder.hotzf6.setTextColor(mContext
								.getResources().getColor(R.color.z_green));
					else
						viewHotHolder.hotzf6.setTextColor(Color.WHITE);
					chageepstr = chageepstr + "%";
				}
				viewHotHolder.hotzf6.setText(chageepstr);
				String cjestr = null;
				try {
					if (item.cje.length() < 13) {
						double changeps = Double.parseDouble(item.cje) / dw;
						cjestr = String.format("%.2f亿", changeps);
					} else if (item.cje.length() >= 13) {
						double changeps = (Double.parseDouble(item.cje) / dw) / 10000;
						cjestr = String.format("%.2f万亿", changeps);
					}

				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					cjestr = item.cje;
				}
				viewHotHolder.hotzl6.setText(cjestr);
			}
			break;
		}
		// 涨跌幅榜
		case typ1: {
			ItemStock itemstock = DataCenter.getInstance().listStockUp
					.get(position);
			if (itemstock.item == 1) {
				LayoutParams lp = mHolder.ll_parent.getLayoutParams();
				lp.height = MyUtils.getInstance().dp2px(mContext, 30);
				mHolder.ll_parent.setLayoutParams(lp);
				convertView.setBackgroundColor(mContext.getResources()
						.getColor(R.color.z_colorblock));
				mHolder.headTitleView.setVisibility(View.VISIBLE);
				mHolder.headDivider.setVisibility(View.GONE);
				if (itemstock.index == 1) {
					mHolder.headTitleView.setText(mContext.getResources()
							.getText(R.string.uppanel));
					mHolder.z_layout_Title.setVisibility(View.VISIBLE);
					mHolder.divider.setVisibility(View.VISIBLE);
				} else if (itemstock.index == -1) {
					mHolder.headTitleView.setText(mContext.getResources()
							.getText(R.string.downpanel));
					mHolder.z_layout_Title.setVisibility(View.VISIBLE);
					mHolder.divider.setVisibility(View.VISIBLE);
					mHolder.headDivider.setVisibility(View.VISIBLE);
				} else if (itemstock.index == 2) {
					mHolder.headTitleView.setText(mContext.getResources()
							.getText(R.string.hotpaneltitle));
				}

				// mHolder.headTitleView.setTextSize(16);
				// mHolder.headTitleView.setTextColor(Color.WHITE);
				mHolder.iconText.setVisibility(View.VISIBLE);
				mHolder.name.setVisibility(View.GONE);
				mHolder.close.setVisibility(View.GONE);
				mHolder.change.setVisibility(View.GONE);
				mHolder.changep.setVisibility(View.GONE);
			} else {
				LayoutParams lp = mHolder.ll_parent.getLayoutParams();
				lp.height = MyUtils.getInstance().dp2px(mContext, 40);
				mHolder.ll_parent.setLayoutParams(lp);
				convertView.setBackgroundColor(mContext.getResources()
						.getColor(R.color.z_colorblock));
				// mHolder.name.setTextSize(14);
				mHolder.iconText.setVisibility(View.GONE);
				mHolder.headTitleView.setVisibility(View.GONE);
				mHolder.z_layout_Title.setVisibility(View.GONE);
				mHolder.headDivider.setVisibility(View.GONE);

				mHolder.divider.setVisibility(View.GONE);

				SpannableString sp = new SpannableString(itemstock.name + "\n"
						+ itemstock.code);
				sp.setSpan(new RelativeSizeSpan(1.0f), 0,
						(itemstock.name + "\n").length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				sp.setSpan(new RelativeSizeSpan(0.75f),
						(itemstock.name + "\n").length(), (itemstock.name
								+ "\n" + itemstock.code).length(),
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				mHolder.name.setText(sp);

				// mHolder.name.setText(itemstock.name + "\n" + itemstock.code);
				mHolder.close.setText(UtilFont.setFontStyle(Parse.getInstance()
						.parse2String(itemstock.close), UtilFont
						.getColor(itemstock.change)));
				mHolder.change.setText(UtilFont.setFontStyle(Parse
						.getInstance().parse2String(itemstock.change), UtilFont
						.getColor(itemstock.change)));
				mHolder.changep.setText(UtilFont.setFontStyle(
						UtilMath.getIncrease_(itemstock.changep),
						UtilFont.getColor(itemstock.change)));

				mHolder.name.setVisibility(View.VISIBLE);
				mHolder.close.setVisibility(View.VISIBLE);
				mHolder.change.setVisibility(View.VISIBLE);
				mHolder.changep.setVisibility(View.VISIBLE);
			}
			break;
		}
		default:

			break;

		}

		return convertView;
	}

	class ViewHolder {
		TextView name;
		// TextView code;
		TextView close;
		TextView change;
		TextView changep;
		TextView iconText;
		TextView headTitleView;
		LinearLayout z_layout_Title;
		View divider;// 分割线
		View headDivider;// 5dp的分割线
		LinearLayout ll_parent;
	}

	class ViewHotHolder {
		TextView hotname1;
		TextView hotzf1; // xx%
		TextView hotzl1;

		TextView hotname2;
		TextView hotzf2; // xx%
		TextView hotzl2;

		TextView hotname3;
		TextView hotzf3; // xx%
		TextView hotzl3;

		TextView hotname4;
		TextView hotzf4; // xx%
		TextView hotzl4;

		TextView hotname5;
		TextView hotzf5; // xx%
		TextView hotzl5;

		TextView hotname6;
		TextView hotzf6; // xx%
		TextView hotzl6;

		LinearLayout linayLout1;
		LinearLayout linayLout2;
		LinearLayout linayLout3;
		LinearLayout linayLout4;
		LinearLayout linayLout5;
		LinearLayout linayLout6;

	}

}
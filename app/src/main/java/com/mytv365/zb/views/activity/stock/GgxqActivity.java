package com.mytv365.zb.views.activity.stock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.DeviceAdapter;
import com.mytv365.zb.adapters.MingXiAdapter;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.receiver.OnItemClickListener1;
import com.mytv365.zb.thread.GetDataToJSONThread;
import com.mytv365.zb.utils.Consts;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Parse;
import com.mytv365.zb.utils.UtilFont;
import com.mytv365.zb.utils.UtilHttp;
import com.mytv365.zb.utils.UtilMath;
import com.mytv365.zb.widget.MyListView;
import com.mytv365.zb.widget.stock.KLineChartView;
import com.mytv365.zb.widget.stock.KLineChartView.KLineListener;
import com.mytv365.zb.widget.stock.LineEntity;
import com.mytv365.zb.widget.stock.OHLCEntity;
import com.mytv365.zb.widget.stock.StickEntity;
import com.mytv365.zb.widget.stock.TimeChartView;
import com.mytv365.zb.widget.stock.TimeChartView.OnTimeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 个股行情
 */
public class GgxqActivity extends Activity implements OnClickListener {

	/** 控件相关 */
	LinearLayout parent_h;
	RelativeLayout rl_fs_h;
	LinearLayout kxianLayout_h;
	KLineChartView k_line_h;

	TimeChartView timeChartView_h;

	RelativeLayout rl_wd_mx_h;
	TextView tv_wd_h;
	TextView tv_mx_h;
	LinearLayout ll_wd_h;
	LinearLayout ll_mx_h;
	TextView code_h;
	TextView ggxqgm_h;
	TextView tv_sp5_h;
	TextView tv_sv5_h;
	TextView tv_sp4_h;
	TextView tv_sv4_h;
	TextView tv_sp3_h;
	TextView tv_sv3_h;
	TextView tv_sp2_h;
	TextView tv_sv2_h;
	TextView tv_sp1_h;
	TextView tv_sv1_h;
	TextView tv_bp5_h;
	TextView tv_bv5_h;
	TextView tv_bp4_h;
	TextView tv_bv4_h;
	TextView tv_bp3_h;
	TextView tv_bv3_h;
	TextView tv_bp2_h;
	TextView tv_bv2_h;
	TextView tv_bp1_h;
	TextView tv_bv1_h;
	MyListView lv_mx_h;
	RelativeLayout rl_fuquan_h;
	TextView tv_ma5_h;
	TextView tv_ma10_h;
	TextView tv_ma20_h;
	TextView tv_fuquan_h;

	LinearLayout ll_k_bottom_ma_h;
	TextView tv_ma5_bottom_h;
	TextView tv_ma10_bottom_h;
	TextView tv_ma20_bottom_h;

	LinearLayout ll_time_left_h;
	TextView tv_time_h_;
	TextView tv_price_h;
	TextView tv_zhangdie_h;
	TextView tv_zhangdiefu_h;
	TextView tv_chengjiao_h;

	LinearLayout ll_kline_left_k_h;
	TextView tv_time_k_h;
	TextView tv_open_price_k_h;
	TextView tv_close_price_k_h;
	TextView tv_high_k_h;
	TextView tv_low_k_h;
	TextView tv_zhangdie_k_h;
	TextView tv_zhangdiefu_k_h;
	TextView tv_chengjiaoe_k_h;
	TextView tv_chengjiaoliang_k_h;

	TextView title_close_h;
	TextView change_h;
	TextView ggxqchangep_h;
	TextView hprice_h;
	TextView lprice_h;
	TextView open_h;
	TextView cje_h;
	TextView cjl_h;
	TextView pe_h;
	TextView pb_h;
	TextView fla_h;
	TextView total_h;
	TextView tv_time_h;
	TextView tv_day_h;
	TextView tv_week_h;
	TextView tv_month_h;
	TextView z_cje_h;
	TextView z_pb_h;
	TextView z_lprice_h;
	TextView z_cjl_h;
	TextView z_total_h;
	TextView z_open_h;
	TextView z_pe_h;
	TextView z_fla_h;

	TextView name;
	TextView title;
	TextView title_close;
	TextView change;
	TextView ggxqchangep;
	TextView code;
	TextView ggxqgm;
	TextView hprice;
	TextView lprice;
	TextView open;
	TextView cje;
	TextView cjl;
	TextView pe;
	TextView pb;
	TextView fla;
	TextView total;
	TextView z_cje;
	TextView z_pb;
	TextView z_lprice;
	TextView z_cjl;
	TextView z_total;
	TextView z_open;
	TextView z_pe;
	TextView z_fla;

	private ImageView iv_add_clear;// 添加、删除自选按钮

	TimeChartView timeChartView;// 分时
	KLineChartView k_line;// K线

	LinearLayout back;
	ImageView ivRefresh;
	// GgxqView ggxqView;

	TextView tv_time;
	TextView tv_day;
	TextView tv_week;
	TextView tv_month;

	private ListView content_view;
	private LinearLayout layout_title_1;
	private TextView tv_news_1;
	private TextView tv_notice_1;
	private TextView tv_f10_1;

	private RelativeLayout rl_wd_mx;// 五档明细根布局
	private TextView tv_wd;// 五档按钮
	private TextView tv_mx;// 明细按钮
	private LinearLayout ll_wd;// 五档布局
	private LinearLayout ll_mx;// 明细布局
	// 卖
	private TextView tv_sp5;// 第5档价格
	private TextView tv_sv5;// 第5档量
	private TextView tv_sp4;// 第4档价格
	private TextView tv_sv4;// 第4档量
	private TextView tv_sp3;// 第3档价格
	private TextView tv_sv3;// 第3档量
	private TextView tv_sp2;// 第2档价格
	private TextView tv_sv2;// 第2档量
	private TextView tv_sp1;// 第1档价格
	private TextView tv_sv1;// 第1档量
	// 买
	private TextView tv_bp5;// 第5档价格
	private TextView tv_bv5;// 第5档量
	private TextView tv_bp4;// 第4档价格
	private TextView tv_bv4;// 第4档量
	private TextView tv_bp3;// 第3档价格
	private TextView tv_bv3;// 第3档量
	private TextView tv_bp2;// 第2档价格
	private TextView tv_bv2;// 第2档量
	private TextView tv_bp1;// 第1档价格
	private TextView tv_bv1;// 第1档量
	// 明细相关
	private MyListView lv_mx;// 明细列表
	// 明细适配器
	private MingXiAdapter mxAdapter;

	// MA5、MA10、MA20、复权布局
	private RelativeLayout rl_fuquan;
	// MA5、MA10、MA20、复权
	private TextView tv_ma5, tv_ma10, tv_ma20, tv_fuquan;

	// K线下表MA5、MA10、MA20、布局
	private LinearLayout ll_k_bottom_ma;// 布局
	private TextView tv_ma5_bottom;// MA5
	private TextView tv_ma10_bottom;// MA10
	private TextView tv_ma20_bottom;// MA20

	// 分时十字光标点击左边数据布局
	private LinearLayout ll_time_left;// 父布局
	private TextView tv_time_;// 时间
	private TextView tv_price;// 价格
	private TextView tv_zhangdie;// 涨跌
	private TextView tv_zhangdiefu;// 涨跌幅
	private TextView tv_chengjiao;// 成交

	// K线十字光标左边布局
	private LinearLayout ll_kline_left_k;// 父布局
	private TextView tv_time_k;// 时间
	private TextView tv_open_price_k;// 开盘价
	private TextView tv_close_price_k;// 收盘价
	private TextView tv_high_k;// 最高价
	private TextView tv_low_k;// 最低价
	private TextView tv_zhangdie_k;// 涨跌
	private TextView tv_zhangdiefu_k;// 涨跌幅
	private TextView tv_chengjiaoe_k;// 成交额
	private TextView tv_chengjiaoliang_k;// 成交量

	/** F10 */
	private WebView wv_f10;//
	private LinearLayout ll_loading;// loading

	/** 数据相关 */
	private String _code;
	private int cycle;// 纪录当前点击到日K、周K、月K
	double preclose;// 昨收价
	private boolean isShowNewsAndF10;// 是否显示新闻与F10
	private int height;// 屏幕高（不包含状态栏）
	private boolean isRefresh;// 是否定时刷新
	private boolean isLoadState;// webview是否加载过了
	private int index;// 分页计数器
	private String newsGongGao;// 新闻公告字段

	/** 进度条 */
	private ProgressDialog pd;
	/** 线程相关 */
	private RunGGXQRunnable runGGXQRunnable;
	private TimeThread timeThread;
	private boolean isStart;

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		this._code = code;
	}



	private void goToNews(int position) {
		String id = xwdata.get(position).get("id");
		String url = null;
		String title =null;
		//http://gphq.mytv365.com/Notice/002670/0000000000000gqzbx.html
		if (currentIndex == R.id.tv_news) {
			url = "http://gphq.mytv365.com/News/"+_code+"/"+id+".html";
			title = "新闻";
		} else if (currentIndex == R.id.tv_notice) {
			url = "http://gphq.mytv365.com/Notice/"+_code+"/"+id+".html";
			title = "公告";
		}
		Intent intent = new Intent(GgxqActivity.this, DetailActivity.class);
//		intent.putExtra("id",id);
//		intent.putExtra("index", currentIndex);
//		intent.putExtra("code",_code);
		intent.putExtra("url",url);
//		intent.putExtra("title", name.getText().toString());
		intent.putExtra("title",title);
		startActivity(intent);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0x2000:
					handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							initMAChart();
							findLayout();
							ivRefresh.clearAnimation();
							if (DataCenter.getInstance().autoupdate && isRefresh) {
								handler.postDelayed(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										ivRefresh.startAnimation(AnimationUtils
												.loadAnimation(
														getApplicationContext(),
														R.anim.my_rotate_360_reverse));
									}
								}, 5000);
								handler.removeCallbacks(runGGXQRunnable);
								handler.postDelayed(runGGXQRunnable, 6000);
							}
						}
					});
					break;
				case 0x4000:
					iv_add_clear.setTag(false);
					iv_add_clear.setImageResource(R.drawable.minus);
					break;
				case 0x5000:
					iv_add_clear.setTag(true);
					iv_add_clear.setImageResource(R.drawable.add);
					break;
				case 0x4001:
					MyUtils.getInstance().showToast(getApplicationContext(),
							"数据解析异常");
					break;

				case 0x4002:
					MyUtils.getInstance().showToast(getApplicationContext(),
							"网络链接异常");
					break;

				case 0x4003:
					Map<String, Object> map1 = Parse.getInstance()
							.parseMap(msg.obj);
					if ("100".equals(map1.get("resultCode"))) {
						iv_add_clear.setTag(false);
						iv_add_clear.setImageResource(R.drawable.minus);
						MyUtils.getInstance().showToast(getApplicationContext(),
								"添加自选成功");
					} else {
						MyUtils.getInstance().showToast(getApplicationContext(),
								Parse.getInstance().isNull(map1.get("resultMessage")));
					}
					break;

				case 0x4004:
					MyUtils.getInstance().showToast(getApplicationContext(),
							"数据解析异常");
					break;

				case 0x4005:
					MyUtils.getInstance().showToast(getApplicationContext(),
							"网络链接异常");
					break;

				case 0x4006:
					Map<String, Object> map2 = Parse.getInstance()
							.parseMap(msg.obj);
					if ("100".equals(map2.get("resultCode"))) {
						iv_add_clear.setTag(true);
						iv_add_clear.setImageResource(R.drawable.add);
						MyUtils.getInstance().showToast(getApplicationContext(),
								"删除自选成功");
					} else {
						MyUtils.getInstance().showToast(getApplicationContext(),
								Parse.getInstance().isNull(map2.get("resultMessage")));
					}
					break;

				case 0x4007:
					MyUtils.getInstance().showToast(getApplicationContext(),
							"数据解析异常");
					break;

				case 0x4008:
					MyUtils.getInstance().showToast(getApplicationContext(),
							"网络链接异常");
					break;

				case 0x9999:
					MyUtils.getInstance().showToast(getApplicationContext(),
							"网络链接异常");
					ivRefresh.clearAnimation();
					break;

				default:
					break;
			}
			if (pd.isShowing())
				pd.dismiss();
		}
	};

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isLoadState = false;
		int statusBarHeight = 0;
		runGGXQRunnable = new RunGGXQRunnable();
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			// loge("get status bar height fail");
			// e1.printStackTrace();
		}
		height = getWindowManager().getDefaultDisplay().getHeight()
				- statusBarHeight;
		Intent intent = this.getIntent();
		_code = intent.getStringExtra("code");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ggxq);
		index = 3;
		pd = new ProgressDialog(this);
		pd.setCancelable(false);
		pd.setMessage("加载中...");
		name = (TextView) findViewById(R.id.ggxqname);
		title = (TextView) findViewById(R.id.ggxqtitle);
		iv_add_clear = (ImageView) findViewById(R.id.iv_add_clear);
		back = (LinearLayout) findViewById(R.id.ll_back);
		ivRefresh = (ImageView) findViewById(R.id.ggxqtitle_refresh);
		content_view = (ListView) findViewById(R.id.content_view);
		layout_title_1 = (LinearLayout) findViewById(R.id.layout_title_1);
		tv_news_1 = (TextView) findViewById(R.id.tv_news_1);
		tv_notice_1 = (TextView) findViewById(R.id.tv_notice_1);
		tv_f10_1 = (TextView) findViewById(R.id.tv_f10_1);

		parent_h = (LinearLayout) findViewById(R.id.parent_h);
		rl_fs_h = (RelativeLayout) findViewById(R.id.rl_fs);
		kxianLayout_h = (LinearLayout) findViewById(R.id.klayoutid);
		k_line_h = (KLineChartView) findViewById(R.id.k_line);

		timeChartView_h = (TimeChartView) findViewById(R.id.timeChartView);

		rl_wd_mx_h = (RelativeLayout) findViewById(R.id.rl_wd_mx);
		tv_wd_h = (TextView) findViewById(R.id.tv_wd);
		tv_mx_h = (TextView) findViewById(R.id.tv_mx);
		ll_wd_h = (LinearLayout) findViewById(R.id.ll_wd);
		ll_mx_h = (LinearLayout) findViewById(R.id.ll_mx);
		code_h = (TextView) findViewById(R.id.ggxqcode);
		ggxqgm_h = (TextView) findViewById(R.id.ggxqgm);
		tv_sp5_h = (TextView) findViewById(R.id.tv_sp5);
		tv_sv5_h = (TextView) findViewById(R.id.tv_sv5);
		tv_sp4_h = (TextView) findViewById(R.id.tv_sp4);
		tv_sv4_h = (TextView) findViewById(R.id.tv_sv4);
		tv_sp3_h = (TextView) findViewById(R.id.tv_sp3);
		tv_sv3_h = (TextView) findViewById(R.id.tv_sv3);
		tv_sp2_h = (TextView) findViewById(R.id.tv_sp2);
		tv_sv2_h = (TextView) findViewById(R.id.tv_sv2);
		tv_sp1_h = (TextView) findViewById(R.id.tv_sp1);
		tv_sv1_h = (TextView) findViewById(R.id.tv_sv1);
		tv_bp5_h = (TextView) findViewById(R.id.tv_bp5);
		tv_bv5_h = (TextView) findViewById(R.id.tv_bv5);
		tv_bp4_h = (TextView) findViewById(R.id.tv_bp4);
		tv_bv4_h = (TextView) findViewById(R.id.tv_bv4);
		tv_bp3_h = (TextView) findViewById(R.id.tv_bp3);
		tv_bv3_h = (TextView) findViewById(R.id.tv_bv3);
		tv_bp2_h = (TextView) findViewById(R.id.tv_bp2);
		tv_bv2_h = (TextView) findViewById(R.id.tv_bv2);
		tv_bp1_h = (TextView) findViewById(R.id.tv_bp1);
		tv_bv1_h = (TextView) findViewById(R.id.tv_bv1);
		lv_mx_h = (MyListView) findViewById(R.id.lv_mx);
		rl_fuquan_h = (RelativeLayout) findViewById(R.id.rl_fuquan);
		tv_ma5_h = (TextView) findViewById(R.id.tv_ma5);
		tv_ma10_h = (TextView) findViewById(R.id.tv_ma10);
		tv_ma20_h = (TextView) findViewById(R.id.tv_ma20);
		tv_fuquan_h = (TextView) findViewById(R.id.tv_fuquan);

		ll_k_bottom_ma_h = (LinearLayout) findViewById(R.id.ll_k_bottom_ma);
		tv_ma5_bottom_h = (TextView) findViewById(R.id.tv_ma5_bottom);
		tv_ma10_bottom_h = (TextView) findViewById(R.id.tv_ma10_bottom);
		tv_ma20_bottom_h = (TextView) findViewById(R.id.tv_ma20_bottom);

		ll_time_left_h = (LinearLayout) findViewById(R.id.ll_time_left);
		tv_time_h_ = (TextView) findViewById(R.id.tv_time_);
		tv_price_h = (TextView) findViewById(R.id.tv_price);
		tv_zhangdie_h = (TextView) findViewById(R.id.tv_zhangdie);
		tv_zhangdiefu_h = (TextView) findViewById(R.id.tv_zhangdiefu);
		tv_chengjiao_h = (TextView) findViewById(R.id.tv_chengjiao);

		ll_kline_left_k_h = (LinearLayout) findViewById(R.id.ll_kline_left_k);
		tv_time_k_h = (TextView) findViewById(R.id.tv_time_k);
		tv_open_price_k_h = (TextView) findViewById(R.id.tv_open_price_k);
		tv_close_price_k_h = (TextView) findViewById(R.id.tv_close_price_k);
		tv_high_k_h = (TextView) findViewById(R.id.tv_high_k);
		tv_low_k_h = (TextView) findViewById(R.id.tv_low_k);
		tv_zhangdie_k_h = (TextView) findViewById(R.id.tv_zhangdie_k);
		tv_zhangdiefu_k_h = (TextView) findViewById(R.id.tv_zhangdiefu_k);
		tv_chengjiaoe_k_h = (TextView) findViewById(R.id.tv_chengjiaoe_k);
		tv_chengjiaoliang_k_h = (TextView) findViewById(R.id.tv_chengjiaoliang_k);

		title_close_h = (TextView) findViewById(R.id.ggxqtitle_close);
		change_h = (TextView) findViewById(R.id.ggxqchange);
		ggxqchangep_h = (TextView) findViewById(R.id.ggxqchangep);
		hprice_h = (TextView) findViewById(R.id.hprice);
		lprice_h = (TextView) findViewById(R.id.lprice);
		open_h = (TextView) findViewById(R.id.open);
		cje_h = (TextView) findViewById(R.id.cje);
		cjl_h = (TextView) findViewById(R.id.cjl);
		pe_h = (TextView) findViewById(R.id.pe);
		pb_h = (TextView) findViewById(R.id.pb);
		fla_h = (TextView) findViewById(R.id.fla);
		total_h = (TextView) findViewById(R.id.total);
		tv_time_h = (TextView) findViewById(R.id.tv_time);
		tv_day_h = (TextView) findViewById(R.id.tv_day);
		tv_week_h = (TextView) findViewById(R.id.tv_week);
		tv_month_h = (TextView) findViewById(R.id.tv_month);
		z_cje_h = (TextView) findViewById(R.id.z_cje);
		z_pb_h = (TextView) findViewById(R.id.z_pb);
		z_lprice_h = (TextView) findViewById(R.id.z_lprice);
		z_cjl_h = (TextView) findViewById(R.id.z_cjl);
		z_total_h = (TextView) findViewById(R.id.z_total);
		z_open_h = (TextView) findViewById(R.id.z_open);
		z_pe_h = (TextView) findViewById(R.id.z_pe);
		z_fla_h = (TextView) findViewById(R.id.z_fla);

		View view = getLayoutInflater().inflate(R.layout.layout_ggxq, null);
		rl_fs = (RelativeLayout) view.findViewById(R.id.rl_fs);
		kxianLayout = (LinearLayout) view.findViewById(R.id.klayoutid);
		k_line = (KLineChartView) view.findViewById(R.id.k_line);

		timeChartView = (TimeChartView) view.findViewById(R.id.timeChartView);

		rl_wd_mx = (RelativeLayout) view.findViewById(R.id.rl_wd_mx);
		tv_wd = (TextView) view.findViewById(R.id.tv_wd);
		tv_mx = (TextView) view.findViewById(R.id.tv_mx);
		ll_wd = (LinearLayout) view.findViewById(R.id.ll_wd);
		ll_mx = (LinearLayout) view.findViewById(R.id.ll_mx);
		tv_sp5 = (TextView) view.findViewById(R.id.tv_sp5);
		tv_sv5 = (TextView) view.findViewById(R.id.tv_sv5);
		tv_sp4 = (TextView) view.findViewById(R.id.tv_sp4);
		tv_sv4 = (TextView) view.findViewById(R.id.tv_sv4);
		tv_sp3 = (TextView) view.findViewById(R.id.tv_sp3);
		tv_sv3 = (TextView) view.findViewById(R.id.tv_sv3);
		tv_sp2 = (TextView) view.findViewById(R.id.tv_sp2);
		tv_sv2 = (TextView) view.findViewById(R.id.tv_sv2);
		tv_sp1 = (TextView) view.findViewById(R.id.tv_sp1);
		tv_sv1 = (TextView) view.findViewById(R.id.tv_sv1);
		tv_bp5 = (TextView) view.findViewById(R.id.tv_bp5);
		tv_bv5 = (TextView) view.findViewById(R.id.tv_bv5);
		tv_bp4 = (TextView) view.findViewById(R.id.tv_bp4);
		tv_bv4 = (TextView) view.findViewById(R.id.tv_bv4);
		tv_bp3 = (TextView) view.findViewById(R.id.tv_bp3);
		tv_bv3 = (TextView) view.findViewById(R.id.tv_bv3);
		tv_bp2 = (TextView) view.findViewById(R.id.tv_bp2);
		tv_bv2 = (TextView) view.findViewById(R.id.tv_bv2);
		tv_bp1 = (TextView) view.findViewById(R.id.tv_bp1);
		tv_bv1 = (TextView) view.findViewById(R.id.tv_bv1);
		lv_mx = (MyListView) view.findViewById(R.id.lv_mx);
		rl_fuquan = (RelativeLayout) view.findViewById(R.id.rl_fuquan);
		tv_ma5 = (TextView) view.findViewById(R.id.tv_ma5);
		tv_ma10 = (TextView) view.findViewById(R.id.tv_ma10);
		tv_ma20 = (TextView) view.findViewById(R.id.tv_ma20);
		tv_fuquan = (TextView) view.findViewById(R.id.tv_fuquan);

		ll_k_bottom_ma = (LinearLayout) view.findViewById(R.id.ll_k_bottom_ma);
		tv_ma5_bottom = (TextView) view.findViewById(R.id.tv_ma5_bottom);
		tv_ma10_bottom = (TextView) view.findViewById(R.id.tv_ma10_bottom);
		tv_ma20_bottom = (TextView) view.findViewById(R.id.tv_ma20_bottom);

		ll_time_left = (LinearLayout) view.findViewById(R.id.ll_time_left);
		tv_time_ = (TextView) view.findViewById(R.id.tv_time_);
		tv_price = (TextView) view.findViewById(R.id.tv_price);
		tv_zhangdie = (TextView) view.findViewById(R.id.tv_zhangdie);
		tv_zhangdiefu = (TextView) view.findViewById(R.id.tv_zhangdiefu);
		tv_chengjiao = (TextView) view.findViewById(R.id.tv_chengjiao);

		ll_kline_left_k = (LinearLayout) view
				.findViewById(R.id.ll_kline_left_k);
		tv_time_k = (TextView) view.findViewById(R.id.tv_time_k);
		tv_open_price_k = (TextView) view.findViewById(R.id.tv_open_price_k);
		tv_close_price_k = (TextView) view.findViewById(R.id.tv_close_price_k);
		tv_high_k = (TextView) view.findViewById(R.id.tv_high_k);
		tv_low_k = (TextView) view.findViewById(R.id.tv_low_k);
		tv_zhangdie_k = (TextView) view.findViewById(R.id.tv_zhangdie_k);
		tv_zhangdiefu_k = (TextView) view.findViewById(R.id.tv_zhangdiefu_k);
		tv_chengjiaoe_k = (TextView) view.findViewById(R.id.tv_chengjiaoe_k);
		tv_chengjiaoliang_k = (TextView) view
				.findViewById(R.id.tv_chengjiaoliang_k);

		title_close = (TextView) view.findViewById(R.id.ggxqtitle_close);
		change = (TextView) view.findViewById(R.id.ggxqchange);
		ggxqchangep = (TextView) view.findViewById(R.id.ggxqchangep);
		code = (TextView) view.findViewById(R.id.ggxqcode);
		ggxqgm = (TextView) view.findViewById(R.id.ggxqgm);
		hprice = (TextView) view.findViewById(R.id.hprice);
		lprice = (TextView) view.findViewById(R.id.lprice);
		open = (TextView) view.findViewById(R.id.open);
		cje = (TextView) view.findViewById(R.id.cje);
		cjl = (TextView) view.findViewById(R.id.cjl);
		pe = (TextView) view.findViewById(R.id.pe);
		pb = (TextView) view.findViewById(R.id.pb);
		fla = (TextView) view.findViewById(R.id.fla);
		total = (TextView) view.findViewById(R.id.total);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		tv_day = (TextView) view.findViewById(R.id.tv_day);
		tv_week = (TextView) view.findViewById(R.id.tv_week);
		tv_month = (TextView) view.findViewById(R.id.tv_month);
		z_cje = (TextView) view.findViewById(R.id.z_cje);
		z_pb = (TextView) view.findViewById(R.id.z_pb);
		z_lprice = (TextView) view.findViewById(R.id.z_lprice);
		z_cjl = (TextView) view.findViewById(R.id.z_cjl);
		z_total = (TextView) view.findViewById(R.id.z_total);
		z_open = (TextView) view.findViewById(R.id.z_open);
		z_pe = (TextView) view.findViewById(R.id.z_pe);
		z_fla = (TextView) view.findViewById(R.id.z_fla);
		k_line.setStrokeBottom(k_line.getTextSize());
		TextPaint tp = new TextPaint();
		tp.setTextSize(k_line.getTextSize());
		k_line.setStrokeLeft(k_line.getStrokeWidth() + 6
				+ tp.measureText("19990909"));
		k_line_h.setStrokeBottom(k_line_h.getTextSize());
		k_line_h.setStrokeLeft(k_line_h.getStrokeWidth() + 6
				+ tp.measureText("19990909"));

		View view1 = getLayoutInflater().inflate(R.layout.layout_webview, null);
		ll_loading = (LinearLayout) view1.findViewById(R.id.ll_loading);
		ll_loading.setVisibility(View.GONE);
		wv_f10 = (WebView) view1.findViewById(R.id.wv_f10);
		// wv_f10.setInitialScale(130);

		wv_f10.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				isLoadState = true;
				LayoutParams lp = view.getLayoutParams();
				lp.height = LayoutParams.WRAP_CONTENT;
				view.setLayoutParams(lp);
				view.setVisibility(View.VISIBLE);
				ll_loading.setVisibility(View.GONE);
			}
		});
		// wv_f10.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		wv_f10.getSettings().setJavaScriptEnabled(true);
		wv_f10.getSettings().setBuiltInZoomControls(false);
		wv_f10.setFocusable(false);
		wv_f10.setClickable(false);
		wv_f10.setVisibility(View.GONE);
		content_view.addHeaderView(view);
		content_view.addFooterView(view1);
		content_view.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem >= 1) {
					layout_title_1.setVisibility(View.VISIBLE);
				} else {
					layout_title_1.setVisibility(View.GONE);
				}
			}
		});
		if (("000001".equals(_code))
				|| ("399001".equals(_code))
				|| ("399006".equals(_code))) {
			isShowNewsAndF10 = false;
			currentIndex = R.id.tv_notice;
			tv_news_1.setVisibility(View.GONE);
			tv_f10_1.setVisibility(View.GONE);
			tv_notice_1.setBackgroundColor(getResources().getColor(
					R.color.z_colorblock));
			tv_notice_1.setTextColor(Color.WHITE);
			tv_notice_1.setOnClickListener(this);
			title_close.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
			title_close_h.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		} else {
			isShowNewsAndF10 = true;
			currentIndex = R.id.tv_news;
			tv_news_1.setBackgroundColor(getResources().getColor(
					R.color.z_colorblock));
			tv_news_1.setTextColor(Color.WHITE);
			tv_notice_1.setBackgroundColor(0xff1a2c43);
			tv_notice_1.setTextColor(getResources().getColor(
					R.color.z_list_title));
			tv_f10_1.setBackgroundColor(0xff1a2c43);
			tv_f10_1.setTextColor(getResources().getColor(R.color.z_list_title));

			tv_news_1.setOnClickListener(this);
			tv_notice_1.setOnClickListener(this);
			tv_f10_1.setOnClickListener(this);
			title_close.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
			title_close_h.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
		}
		xwdata = new LinkedList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("isRefresh", "true");
		xwdata.add(map1);
		datapaters = new DeviceAdapter(this, xwdata, 0, isShowNewsAndF10,
				height);
		content_view.setAdapter(datapaters);
		datapaters.setOnItemClickListener(new MyOnItemClickListener1());

		ll_time_left.setVisibility(View.GONE);
		ll_time_left_h.setVisibility(View.GONE);
		ViewTreeObserver vto = timeChartView.getViewTreeObserver();
		vto.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				LayoutParams lp = ll_time_left.getLayoutParams();
				lp.width = (int) timeChartView.getStrokeLeft();
				ll_time_left.setLayoutParams(lp);
				return true;
			}
		});
		ViewTreeObserver vto_h = timeChartView_h.getViewTreeObserver();
		vto_h.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				LayoutParams lp = ll_time_left_h.getLayoutParams();
				lp.width = (int) timeChartView_h.getStrokeLeft();
				ll_time_left_h.setLayoutParams(lp);
				return true;
			}
		});
		ViewTreeObserver vto1 = k_line.getViewTreeObserver();
		vto1.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				float textSize = k_line.getSpacing() - 5;
				tv_ma5.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma10.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma20.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma5_bottom.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma10_bottom
						.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma20_bottom
						.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

				LayoutParams lp = ll_kline_left_k.getLayoutParams();
				lp.width = (int) k_line.getStrokeLeft();
				ll_kline_left_k.setLayoutParams(lp);
				LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) tv_ma5
						.getLayoutParams();
				lp1.setMargins(
						(int) (k_line.getStrokeLeft() + k_line.getStrokeWidth()
								/ 2 + 2), 0, 0, 0);
				tv_ma5.setLayoutParams(lp1);

				android.widget.FrameLayout.LayoutParams lp2 = (android.widget.FrameLayout.LayoutParams) ll_k_bottom_ma
						.getLayoutParams();
				lp2.setMargins(
						(int) (k_line.getStrokeLeft() + k_line.getStrokeWidth()
								/ 2 + 2),
						(int) Math.abs(k_line.getUpChartBottom())
								+ rl_fuquan.getMeasuredHeight(), 0, 0);
				ll_k_bottom_ma.setLayoutParams(lp2);

				if (k_line.getUpChartBottom() > 0.0f) {
					rl_fuquan.setVisibility(View.VISIBLE);
					ll_k_bottom_ma.setVisibility(View.VISIBLE);
				}

				return true;
			}
		});
		ViewTreeObserver vto1_h = k_line_h.getViewTreeObserver();
		vto1_h.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// TODO Auto-generated method stub
				float textSize = k_line_h.getSpacing() - 5;
				tv_ma5_h.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma10_h.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma20_h.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
				tv_ma5_bottom_h.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						textSize);
				tv_ma10_bottom_h.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						textSize);
				tv_ma20_bottom_h.setTextSize(TypedValue.COMPLEX_UNIT_PX,
						textSize);

				LayoutParams lp = ll_kline_left_k_h.getLayoutParams();
				lp.width = (int) k_line_h.getStrokeLeft();
				ll_kline_left_k_h.setLayoutParams(lp);
				LinearLayout.LayoutParams lp1 = (LinearLayout.LayoutParams) tv_ma5_h
						.getLayoutParams();
				lp1.setMargins(
						(int) (k_line_h.getStrokeLeft()
								+ k_line_h.getStrokeWidth() / 2 + 2), 0, 0, 0);
				tv_ma5_h.setLayoutParams(lp1);

				android.widget.FrameLayout.LayoutParams lp2 = (android.widget.FrameLayout.LayoutParams) ll_k_bottom_ma_h
						.getLayoutParams();
				lp2.setMargins(
						(int) (k_line_h.getStrokeLeft()
								+ k_line_h.getStrokeWidth() / 2 + 2),
						(int) Math.abs(k_line_h.getUpChartBottom())
								+ rl_fuquan_h.getMeasuredHeight(), 0, 0);
				ll_k_bottom_ma_h.setLayoutParams(lp2);

				if (k_line_h.getUpChartBottom() > 0.0f) {
					rl_fuquan_h.setVisibility(View.VISIBLE);
					ll_k_bottom_ma_h.setVisibility(View.VISIBLE);
				}

				return true;
			}
		});
		timeChartView.setSpacing(0.0f);
		timeChartView_h.setSpacing(0.0f);
		// this.listOhlc = new ArrayList<OHLCEntity>();
		// initOHLC();
		mxAdapter = new MingXiAdapter(DataCenter.getInstance().mXList,
				getApplicationContext(), Parse.getInstance().parseFloat(
				DataCenter.getInstance().ggxqBean.changep));
		lv_mx.setAdapter(mxAdapter);
		lv_mx_h.setAdapter(mxAdapter);
		initMACandleStickChart();
		initMAStickChart();
		initMAChart();
		setMAValue();
		k_line.setData(DataCenter.listOhlc, DataCenter.vol);
		k_line_h.setData(DataCenter.listOhlc, DataCenter.vol);
		findLayout();

		// new asyncTaskLoadKLineData().execute(String.valueOf(1));
		if (("000001".equals(_code))
				|| ("399001".equals(_code))
				|| ("399006".equals(_code))) {
			rl_wd_mx.setVisibility(View.GONE);
			rl_wd_mx_h.setVisibility(View.GONE);
			newsGongGao = "noticelist";
			datapaters.setTitleBackground(R.id.tv_notice);
			new asyncTaskLoadLoadXW().execute(newsGongGao,
					String.valueOf(index));
			iv_add_clear.setVisibility(View.GONE);
			tv_fuquan.setVisibility(View.GONE);
			tv_fuquan_h.setVisibility(View.GONE);
		} else {
			newsGongGao = "newslist";
			datapaters.setTitleBackground(R.id.tv_news);
			new asyncTaskLoadLoadXW().execute(newsGongGao,
					String.valueOf(index));
//			new GetDataToJSONThread(
//					getApplicationContext(),
//					String.format(
//							"http://120.55.100.220/api/stock/checkSelfStockExist?temp_token=2&stock_id=%s&market=%s",
//							_code), handler, 0x4000, 0x4002, 0x4001,
//					false).start();
			iv_add_clear.setVisibility(View.VISIBLE);
			OkGo.get(String.format(UtilHttp.URL_GET_SELF))
					.headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
					.headers("header1", "headerValue1")
					.setCertificates()
					.execute(new StringCallback() {
						@Override
						public void onSuccess(String s, Call call, Response response) {
							JSONObject object = null;
							try {
								Log.i("test","--s--"+s);
								object = new JSONObject(s);
								int resultcode = object.getInt("resultCode");
								JSONArray resultData = object.optJSONArray("resultData");
								Log.i("test","--resultData--"+resultData);
								if (resultcode == 100) {
									if(resultData.length()>0){
										int length = resultData.length();
										for (int i = 0; i < length; i++) {
											JSONObject jsobs = resultData.optJSONObject(i);
											String code = jsobs.optString("stockNo");
											if(_code.equals(code)){
												Log.i("test","-0x4000-"+code);
												handler.sendEmptyMessage(0x4000);
												break;
											}else{
												Log.i("test","-0x5000-"+code);
												handler.sendEmptyMessage(0x5000);
											}
										}
									}else{
										handler.sendEmptyMessage(0x5000);
									}
								}
							}catch(JSONException e){
								e.printStackTrace();
							}

						}
						@Override
						public void onError(Call call, Response response, Exception e) {
							super.onError(call, response, e);
						}

					});
			tv_fuquan.setVisibility(View.VISIBLE);
			tv_fuquan_h.setVisibility(View.VISIBLE);
		}
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			parent_h.setVisibility(View.VISIBLE);
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			parent_h.setVisibility(View.GONE);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			parent_h.setVisibility(View.VISIBLE);
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			parent_h.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isRefresh = true;
		isStart = true;
		if (!isAlive(timeThread)) {
			timeThread = new TimeThread();
			timeThread.start();
		}
		LocalBroadcastManager manager = LocalBroadcastManager
				.getInstance(getApplicationContext());
		IntentFilter in = new IntentFilter(Consts.ggxqAction);
		manager.registerReceiver(broadcastReceiver, in);
		if (MyUtils.getInstance().isNetworkConnected(getApplicationContext())) {
			ivRefresh.startAnimation(AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.my_rotate_360_reverse));
			handler.removeCallbacks(runGGXQRunnable);
			handler.postDelayed(runGGXQRunnable, 1000);
		}
		wv_f10.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isRefresh = false;
		isStart = false;
		LocalBroadcastManager.getInstance(getApplicationContext())
				.unregisterReceiver(broadcastReceiver);
		wv_f10.onPause();
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		if (DataCenter.listOhlc != null)
			DataCenter.listOhlc.clear();
		if (DataCenter.vol != null)
			DataCenter.vol.clear();
		if (DataCenter.timeohlc != null)
			DataCenter.timeohlc.clear();
		if (DataCenter.timevol != null)
			DataCenter.timevol.clear();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		wv_f10.destroy();
	}

	/**  */
	public String mStrGghq;

	private List<Float> initVMA(int days) {
		if (days < 2) {
			return null;
		}

		List<Float> MA5Values = new ArrayList<Float>();

		float sum = 0;
		float avg = 0;
		for (int i = 0; i < DataCenter.vol.size(); i++) {
			float close = (float) DataCenter.vol.get(i).getHigh();
			if (i < days) {
				sum = sum + close;
				avg = sum / (i + 1f);
			} else {
				sum = sum + close
						- (float) DataCenter.vol.get(i - days).getHigh();
				avg = sum / days;
			}
			MA5Values.add(avg);
		}

		return MA5Values;
	}

	private void initMAStickChart() {

		// 以下计算VOL
		List<LineEntity> vlines = new ArrayList<LineEntity>();

		// 计算5日均线
		LineEntity VMA5 = new LineEntity();
		List<Float> vma5 = initVMA(5);
		VMA5.setTitle("MA5");
		VMA5.setLineColor(Color.WHITE);
		VMA5.setLineData(vma5);
		vlines.add(VMA5);

		// 计算10日均线
		LineEntity VMA10 = new LineEntity();
		List<Float> vma10 = initVMA(10);
		VMA10.setTitle("MA10");
		VMA10.setLineColor(Color.RED);
		VMA10.setLineData(vma10);
		vlines.add(VMA10);

		// 计算25日均线
		LineEntity VMA20 = new LineEntity();
		List<Float> vma20 = initVMA(20);
		VMA20.setTitle("MA20");
		VMA20.setLineColor(Color.GREEN);
		VMA20.setLineData(vma20);
		vlines.add(VMA20);

		for (int i = 0; i < DataCenter.vol.size(); i++) {
			DataCenter.vol.get(i).setVMA5(vma5.get(i));
			DataCenter.vol.get(i).setVMA10(vma10.get(i));
			DataCenter.vol.get(i).setVMA20(vma20.get(i));
		}
	}

	// 分时走势
	private void initMAChart() {

		List<LineEntity> lines = new ArrayList<LineEntity>();

		// 计算5日均线
		LineEntity MA5 = new LineEntity();
		MA5.setTitle("MA5");
		MA5.setLineColor(Color.WHITE);
		// MA5.setLineData(initMATimeTc());
		MA5.setTimeLineData(DataCenter.timeohlc);
		MA5.setDisplay(true);
		lines.add(MA5);

		// //计算10日均线
		// LineEntity MA10 = new LineEntity();
		// MA10.setTitle("MA10");
		// MA10.setLineColor(Color.RED);
		// MA10.setLineData(initMATime(10));
		// // lines.add(MA10);
		//
		// //计算25日均线
		// LineEntity MA25 = new LineEntity();
		// MA25.setTitle("MA25");
		// MA25.setLineColor(Color.GREEN);
		// MA25.setLineData(initMATimeTc());
		// // lines.add(MA25);

		double hprice = Parse.getInstance().parseDouble(
				(DataCenter.getInstance().ggxqBean.hprice));
		double lprice = Parse.getInstance().parseDouble(
				(DataCenter.getInstance().ggxqBean.lprice));
		preclose = Parse.getInstance().parseDouble(
				(DataCenter.getInstance().ggxqBean.preclose));
		// 设置5档数据
		setWuDang();
		setWuDangH();
		// 设置明细列表
		mxAdapter.setData(DataCenter.getInstance().mXList, Parse.getInstance()
				.parseFloat(DataCenter.getInstance().ggxqBean.changep));

		// 如果一开盘就涨停这种情况
		if (preclose < lprice) {
			lprice = preclose;
		}
		double tempprice = (hprice - lprice) / 3;

		List<String> ytitle = new ArrayList<String>();
		ytitle.add(String.format("%.3f", lprice));
		ytitle.add(String.format("%.3f", lprice + tempprice));
		ytitle.add(String.format("%.3f", lprice + tempprice * 2));
		ytitle.add(String.format("%.3f", hprice));
		// ytitle.add("280");
		List<String> xtitle = new ArrayList<String>();
		xtitle.add("9:30");
		xtitle.add("10:30");
		xtitle.add("11:30 13:00");
		xtitle.add("14:00");
		xtitle.add("15:00");

		timeChartView.setAutoLeftPadding(true);
		timeChartView.setTimesList(DataCenter.timeohlc, (float) hprice,
				(float) lprice, DataCenter.timevol, 0, (float) preclose);
		timeChartView_h.setAutoLeftPadding(true);
		timeChartView_h.setTimesList(DataCenter.timeohlc, (float) hprice,
				(float) lprice, DataCenter.timevol, 0, (float) preclose);

	}

	DeviceAdapter datapaters;
	LinkedList<HashMap<String, String>> xwdata;
	private RelativeLayout rl_fs;// 分时布局
	LinearLayout kxianLayout;

	private class asyncTaskLoadLoadXW extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			String pageSize = index+"";
			String waiteString = UtilHttp.getXW(_code,newsGongGao,pageSize,params[1]);

			return waiteString;
		}

		@Override
		protected void onPostExecute(String str) {
			// TODO Auto-generated method stub
			super.onPostExecute(str);
			if (str != null) {
				LinkedList<HashMap<String, String>> dataList = DataCenter
						.parseXW(str,newsGongGao);
				xwdata.clear();
				if (dataList != null && dataList.size() > 0) {
					xwdata.addAll(dataList);
				} else {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("isNo", "true");
					xwdata.add(map);
				}
				if (xwdata != null) {
					datapaters.setData(xwdata, isShowNewsAndF10);
				}
			} else {
				if (index > 5)
					index -= 5;
				xwdata.get(xwdata.size() - 1).put("isLoading", "false");
				datapaters.setData(xwdata, isShowNewsAndF10);
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private class asyncTaskLoadKLineData extends
			AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
//			// TODO Auto-generated method stub
			String waiteString = UtilHttp.getKLineByCodeAndKValue(_code,
					params[0]);
			DataCenter.parseKLine(waiteString);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result && DataCenter.listOhlc.size() > 0
					&& DataCenter.vol.size() > 0) {
				initMACandleStickChart();
				initMAStickChart();
				setMAValue();
				k_line.setData(DataCenter.listOhlc, DataCenter.vol);
				k_line_h.setData(DataCenter.listOhlc, DataCenter.vol);
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private List<Float> initMA(int days) {
		if (days < 2) {
			return null;
		}
		List<Float> MA5Values = new ArrayList<Float>();
		float sum = 0;
		float avg = 0;
		for (int i = 0; i < DataCenter.listOhlc.size(); i++) {
			float close = (float) DataCenter.listOhlc.get(i).getClose();
			if (i < days) {
				sum = sum + close;
				avg = sum / (i + 1f);
			} else {
				sum = sum + close
						- (float) DataCenter.listOhlc.get(i - days).getClose();
				avg = sum / days;
			}
			MA5Values.add(avg);
		}
		return MA5Values;
	}

	private List<Float> initMATime(int days) {
		if (days < 2) {
			return null;
		}
		// days=1;
		List<Float> MA5Values = new ArrayList<Float>();
		float sum = 0;
		float avg = 0;
		for (int i = 0; i < DataCenter.timeohlc.size(); i++) {
			float close = (float) DataCenter.timeohlc.get(i).getClose();
			if (i < days) {
				sum = sum + close;
				avg = sum / (i + 1f);
			} else {
				sum = sum + close
						- (float) DataCenter.timeohlc.get(i - days).getClose();
				avg = sum / days;
			}
			MA5Values.add(avg);
		}
		return MA5Values;
	}

	private List<Float> initMATimeTc() {

		List<Float> MA5Values = new ArrayList<Float>();

		// float avg = 0;
		for (int i = 0; i < DataCenter.timeohlc.size(); i++) {
			float close = (float) DataCenter.timeohlc.get(i).getClose();

			MA5Values.add(close);
		}
		return MA5Values;
	}

	private void initMACandleStickChart() {

		List<LineEntity> lines = new ArrayList<LineEntity>();
		// 计算5日均线
		LineEntity MA5 = new LineEntity();
		List<Float> ma5 = initMA(5);
		MA5.setTitle("MA5");
		MA5.setLineColor(Color.WHITE);
		MA5.setLineData(ma5);
		lines.add(MA5);

		// 计算10日均线
		LineEntity MA10 = new LineEntity();
		List<Float> ma10 = initMA(10);
		MA10.setTitle("MA10");
		MA10.setLineColor(Color.RED);
		MA10.setLineData(ma10);
		lines.add(MA10);

		// 计算25日均线
		LineEntity MA20 = new LineEntity();
		List<Float> ma20 = initMA(20);
		MA20.setTitle("MA20");
		MA20.setLineColor(Color.GREEN);
		MA20.setLineData(ma20);
		lines.add(MA20);

		// float max=0,min=100000;
		// int size = DataCenter.listOhlc.size();
		// for(int i=0;i<size;i++){
		// float tempmax = DataCenter.listOhlc.get(i).getHigh();
		// float tempmin = DataCenter.listOhlc.get(i).getLow();
		// if(max<tempmax)
		// {
		// max = tempmax;
		// }
		// if(min>tempmin)
		// {
		// min = tempmin;
		// }
		// }
		// 为chart2增加均线
		String oldDate = "";
		for (int i = 0; i < DataCenter.listOhlc.size(); i++) {
			DataCenter.listOhlc.get(i).setMA5(ma5.get(i));
			DataCenter.listOhlc.get(i).setMA10(ma10.get(i));
			DataCenter.listOhlc.get(i).setMA20(ma20.get(i));
			String date = Parse.getInstance().isNull(
					DataCenter.listOhlc.get(i).getDate());
			date = date.substring(0, 6);
			if (!oldDate.equals(date)) {
				DataCenter.listOhlc.get(i).setDrawLongitude(true);
			} else
				DataCenter.listOhlc.get(i).setDrawLongitude(false);
			oldDate = date;
		}
	}

	private void findLayout() {


		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);

		title.setText("更新 " + str);
		if(DataCenter.getInstance().ggxqBean.changep != null){
			name.setText(DataCenter.getInstance().ggxqBean.name);
			title_close.setText(UtilFont.setFontStyle(Parse.getInstance()
							.parse2String(DataCenter.getInstance().ggxqBean.close),
					UtilFont.getColor(DataCenter.getInstance().ggxqBean.change)));
			title_close_h.setText(UtilFont.setFontStyle(Parse.getInstance()
							.parse2String(DataCenter.getInstance().ggxqBean.close),
					UtilFont.getColor(DataCenter.getInstance().ggxqBean.change)));
			// change.setText(DataCenter.getInstance().ggxqBean.change+"\n"+DataCenter.getInstance().ggxqBean.changep);

			change.setText(UtilFont.setFontStyle(
					Parse.getInstance().parse2String(
							DataCenter.getInstance().ggxqBean.change),
					UtilFont.getColor(DataCenter.getInstance().ggxqBean.change)));
			change_h.setText(UtilFont.setFontStyle(Parse.getInstance()
							.parse2String(DataCenter.getInstance().ggxqBean.change),
					UtilFont.getColor(DataCenter.getInstance().ggxqBean.change)));

			ggxqchangep.setText(UtilFont.setFontStyle(UtilMath
							.getIncrease_(DataCenter.getInstance().ggxqBean.changep),
					UtilFont.getColor(DataCenter.getInstance().ggxqBean.changep)));
			ggxqchangep_h.setText(UtilFont.setFontStyle(UtilMath
							.getIncrease_(DataCenter.getInstance().ggxqBean.changep),
					UtilFont.getColor(DataCenter.getInstance().ggxqBean.changep)));

			code.setText(DataCenter.getInstance().ggxqBean.code);
			ggxqgm.setText(DataCenter.getInstance().ggxqBean.name);

			code_h.setText(DataCenter.getInstance().ggxqBean.code);
			ggxqgm_h.setText(DataCenter.getInstance().ggxqBean.name);

			hprice.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.hprice));
			hprice_h.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.hprice));

			lprice.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.lprice));
			lprice_h.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.lprice));

			open.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.open));
			open_h.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.open));

			// TextView close = (TextView) findViewById(R.id.close);
			// close.setText(DataCenter.getInstance().ggxqBean.close);
			cje.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cje, 2));
			cje_h.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cje, 2));

			cjl.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cjl, 2, true));
			cjl_h.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cjl, 2, true));

			pe.setText(DataCenter.getInstance().ggxqBean.pe);
			pe_h.setText(DataCenter.getInstance().ggxqBean.pe);

			pb.setText(DataCenter.getInstance().ggxqBean.pb);
			pb_h.setText(DataCenter.getInstance().ggxqBean.pb);

			fla.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.fla));
			fla_h.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.fla));

			total.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.total));
			total_h.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.total));
		}
		back.setOnClickListener(this);
		ivRefresh.setOnClickListener(this);
		// ggxqView=(GgxqView) findViewById(R.id.view_ggxq);

		tv_time.setOnClickListener(this);
		tv_day.setOnClickListener(this);
		tv_week.setOnClickListener(this);
		tv_month.setOnClickListener(this);
		tv_time_h.setOnClickListener(this);
		tv_day_h.setOnClickListener(this);
		tv_week_h.setOnClickListener(this);
		tv_month_h.setOnClickListener(this);

		// 指数时不同布局
		if (("000001".equals(_code))
				|| ("399001".equals(_code))
				|| ("399006".equals(_code))) {

			z_cje.setText("低");
			cje.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.lprice));
			z_cje_h.setText("低");
			cje_h.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.lprice));

			z_pb.setText("额");
			pb.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cje));
			z_pb_h.setText("额");
			pb_h.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cje));

			z_lprice.setText("开");
			lprice.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.open));
			z_lprice_h.setText("开");
			lprice_h.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.open));

			// 此处收为昨收
			z_cjl.setText("收");
			cjl.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.preclose));
			z_cjl_h.setText("收");
			cjl_h.setText(Parse.getInstance().parse2String(
					DataCenter.getInstance().ggxqBean.preclose));

			z_total.setText("量");
			total.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cjl, true));
			z_total_h.setText("量");
			total_h.setText(Parse.getInstance().parse2CNString(
					DataCenter.getInstance().ggxqBean.cjl, true));

			z_open.setText("涨");
			open.setText(DataCenter.getInstance().ggxqBean.countup + "家");
			open.setTextColor(getResources().getColor(
					R.color.z_red));
			z_open_h.setText("涨");
			open_h.setText(DataCenter.getInstance().ggxqBean.countup + "家");
			open_h.setTextColor(getResources().getColor(
					R.color.z_red));

			z_pe.setText("跌");
			pe.setText(DataCenter.getInstance().ggxqBean.countdown + "家");
			pe.setTextColor(getResources().getColor(
					R.color.z_green));
			z_pe_h.setText("跌");
			pe_h.setText(DataCenter.getInstance().ggxqBean.countdown + "家");
			pe_h.setTextColor(getResources().getColor(
					R.color.z_green));

			z_fla.setText("平");
			fla.setText(DataCenter.getInstance().ggxqBean.countmid + "家");
			z_fla_h.setText("平");
			fla_h.setText(DataCenter.getInstance().ggxqBean.countmid + "家");

		}

		// ggxqView.getData();
		tv_wd.setOnClickListener(this);
		tv_mx.setOnClickListener(this);
		ll_wd.setOnClickListener(this);
		ll_mx.setOnClickListener(this);
		tv_fuquan.setOnClickListener(this);
		iv_add_clear.setOnClickListener(this);

		tv_wd_h.setOnClickListener(this);
		tv_mx_h.setOnClickListener(this);
		ll_wd_h.setOnClickListener(this);
		ll_mx_h.setOnClickListener(this);
		tv_fuquan_h.setOnClickListener(this);
		// iv_narrow.setOnClickListener(this);
		// iv_enlarge.setOnClickListener(this);

		// iv_narrow.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// // TODO Auto-generated method stub
		// iv_enlarge.setAlpha(1.0f);
		// if (!k_line.narrowKLine()) {
		// iv_narrow.setAlpha(0.5f);
		// }
		// return true;
		// }
		// });
		// iv_enlarge.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// // TODO Auto-generated method stub
		// iv_narrow.setAlpha(1.0f);
		// if (!k_line.enlargeKline()) {
		// iv_enlarge.setAlpha(0.5f);
		// }
		// return true;
		// }
		// });
		lv_mx.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				setWdMx();
			}
		});
		lv_mx_h.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				setWdMx();
			}
		});
		timeChartView.setOnTimeListener(new OnTimeListener() {

			@Override
			public void listener(OHLCEntity data, StickEntity stickEntity) {
				// TODO Auto-generated method stub
//				String time = String.valueOf(stickEntity.getDate() - 10000000);
//				if (time.length() < 4) {
//					time = "0" + time;
//				}
				String time = String.valueOf(stickEntity.getDate());
				if(time.length()<6){
					time="0"+time;
				}
				String times = MyUtils.getInstance().string2Dates("HH:mm", "HHmm", time.substring(0, time.length()-2));
				tv_time_.setText(times);
				tv_price.setText(Parse.getInstance().parse2String(
						data.getClose()));
				String zhangDie = Parse.getInstance().parse2String(
						data.getClose() - preclose);
				tv_zhangdie.setText(zhangDie);
				String zdf = Parse.getInstance().parse2String(
						((data.getClose() - preclose) / preclose) * 100)
						+ "%";
				if ("-0.00%".equals(zdf))
					zdf = "0.00%";
				tv_zhangdiefu.setText(zdf);
				tv_chengjiao.setText(Parse.getInstance().parse2CNString(
						stickEntity.getHigh(), false));

				if (zhangDie.equals("0.00")) {
					tv_zhangdie.setTextColor(Color.WHITE);
					tv_price.setTextColor(Color.WHITE);
					tv_zhangdiefu.setTextColor(Color.WHITE);
				} else if (Parse.getInstance().parseFloat(zhangDie) > 0.00f) {
					tv_zhangdie.setTextColor(getResources().getColor(
							R.color.z_red));
					tv_price.setTextColor(getResources()
							.getColor(R.color.z_red));
					tv_zhangdiefu.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_zhangdie.setTextColor(getResources().getColor(
							R.color.z_green));
					tv_price.setTextColor(getResources().getColor(
							R.color.z_green));
					tv_zhangdiefu.setTextColor(getResources().getColor(
							R.color.z_green));
				}
			}

			@Override
			public void isShowDetails(boolean isShowDetails) {
				// TODO Auto-generated method stub
				if (isShowDetails) {
					ll_time_left.setVisibility(View.VISIBLE);
				} else {
					ll_time_left.setVisibility(View.GONE);
				}
			}

		});
		timeChartView_h.setOnTimeListener(new OnTimeListener() {

			@Override
			public void listener(OHLCEntity data, StickEntity stickEntity) {
				// TODO Auto-generated method stub
//				String time = String.valueOf(stickEntity.getDate() - 10000000);
//				if (time.length() < 4) {
//					time = "0" + time;
//				}
				String time = String.valueOf(stickEntity.getDate());
				if(time.length()<6){
					time="0"+time;
				}
				String times = MyUtils.getInstance().string2Dates("HH:mm", "HHmm", time.substring(0, time.length()-2));
				tv_time_h_.setText(times);
				tv_price_h.setText(Parse.getInstance().parse2String(
						data.getClose()));
				String zhangDie = Parse.getInstance().parse2String(
						data.getClose() - preclose);
				tv_zhangdie_h.setText(zhangDie);
				String zdf = Parse.getInstance().parse2String(
						((data.getClose() - preclose) / preclose) * 100)
						+ "%";
				if ("-0.00%".equals(zdf))
					zdf = "0.00%";
				tv_zhangdiefu_h.setText(zdf);
				tv_chengjiao_h.setText(Parse.getInstance().parse2CNString(
						stickEntity.getHigh(), false));

				if (zhangDie.equals("0.00")) {
					tv_zhangdie_h.setTextColor(Color.WHITE);
					tv_price_h.setTextColor(Color.WHITE);
					tv_zhangdiefu_h.setTextColor(Color.WHITE);
				} else if (Parse.getInstance().parseFloat(zhangDie) > 0.00f) {
					tv_zhangdie_h.setTextColor(getResources().getColor(
							R.color.z_red));
					tv_price_h.setTextColor(getResources().getColor(
							R.color.z_red));
					tv_zhangdiefu_h.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_zhangdie_h.setTextColor(getResources().getColor(
							R.color.z_green));
					tv_price_h.setTextColor(getResources().getColor(
							R.color.z_green));
					tv_zhangdiefu_h.setTextColor(getResources().getColor(
							R.color.z_green));
				}
			}

			@Override
			public void isShowDetails(boolean isShowDetails) {
				// TODO Auto-generated method stub
				if (isShowDetails) {
					ll_time_left_h.setVisibility(View.VISIBLE);
				} else {
					ll_time_left_h.setVisibility(View.GONE);
				}
			}

		});
		k_line.setKLineListener(new KLineListener() {

			@Override
			public void transferData(List<OHLCEntity> OHLCData,
									 List<StickEntity> stickEntityList, int position) {
				// TODO Auto-generated method stub
				OHLCEntity oHLCEntity = OHLCData.get(position);
				StickEntity stickEntity = stickEntityList.get(position);
				tv_ma5.setText("MA5:  "
						+ Parse.getInstance().parse2String(oHLCEntity.getMA5()));
				tv_ma10.setText("MA10:  "
						+ Parse.getInstance()
						.parse2String(oHLCEntity.getMA10()));
				tv_ma20.setText("MA20:  "
						+ Parse.getInstance()
						.parse2String(oHLCEntity.getMA20()));

				tv_ma5_bottom.setText("MA5:  "
						+ Parse.getInstance().parse2CNString(
						stickEntity.getVMA5(), false));
				tv_ma10_bottom.setText("MA10:  "
						+ Parse.getInstance().parse2CNString(
						stickEntity.getVMA10(), false));
				tv_ma20_bottom.setText("MA20:  "
						+ Parse.getInstance().parse2CNString(
						stickEntity.getVMA20(), false));

				tv_time_k.setText(Parse.getInstance().isNull(
						oHLCEntity.getDate()));
				tv_open_price_k.setText(Parse.getInstance().parse2String(
						oHLCEntity.getOpen()));
				tv_close_price_k.setText(Parse.getInstance().parse2String(
						oHLCEntity.getClose()));
				tv_high_k.setText(Parse.getInstance().parse2String(
						oHLCEntity.getHigh()));
				tv_low_k.setText(Parse.getInstance().parse2String(
						oHLCEntity.getLow()));
				tv_zhangdie_k.setText(Parse.getInstance().parse2String(
						oHLCEntity.getChange()));
				tv_zhangdiefu_k.setText(Parse.getInstance().parse2String(
						oHLCEntity.getChangep() * 100)
						+ "%");
				tv_chengjiaoe_k.setText(Parse.getInstance().parse2CNString(
						oHLCEntity.getCje()));
				tv_chengjiaoliang_k.setText(Parse.getInstance().parse2CNString(
						oHLCEntity.getCjl(), false));
				double zhD = Parse.getInstance().parseDouble(
						Parse.getInstance()
								.parse2String(oHLCEntity.getChange()));
				if (zhD > 0) {
					tv_zhangdie_k.setTextColor(getResources().getColor(
							R.color.z_red));
					tv_zhangdiefu_k.setTextColor(getResources().getColor(
							R.color.z_red));
				} else if (zhD < 0) {
					tv_zhangdie_k.setTextColor(getResources().getColor(
							R.color.z_green));
					tv_zhangdiefu_k.setTextColor(getResources().getColor(
							R.color.z_green));
				} else {
					tv_zhangdie_k.setTextColor(Color.WHITE);
					tv_zhangdiefu_k.setTextColor(Color.WHITE);
				}

				double x = Parse.getInstance().parseDouble(
						Parse.getInstance().parse2String(
								oHLCEntity.getOpen()
										- (position <= 0 ? oHLCEntity.getOpen()
										: OHLCData.get(position - 1)
										.getClose())));
				if (x < 0) {
					tv_open_price_k.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (x > 0) {
					tv_open_price_k.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_open_price_k.setTextColor(Color.WHITE);
				}

				double change = oHLCEntity.getChange();
				if (change < 0) {
					tv_close_price_k.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (change > 0) {
					tv_close_price_k.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_close_price_k.setTextColor(Color.WHITE);
				}

				double h = Parse.getInstance().parseDouble(
						Parse.getInstance().parse2String(
								oHLCEntity.getHigh()
										- (position <= 0 ? oHLCEntity.getOpen()
										: OHLCData.get(position - 1)
										.getClose())));
				if (h < 0) {
					tv_high_k.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (h > 0) {
					tv_high_k.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_high_k.setTextColor(Color.WHITE);
				}

				double l = Parse.getInstance().parseDouble(
						Parse.getInstance().parse2String(
								oHLCEntity.getLow()
										- (position <= 0 ? oHLCEntity.getOpen()
										: OHLCData.get(position - 1)
										.getClose())));
				if (l < 0) {
					tv_low_k.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (l > 0) {
					tv_low_k.setTextColor(getResources()
							.getColor(R.color.z_red));
				} else {
					tv_low_k.setTextColor(Color.WHITE);
				}
			}

			@Override
			public void isMove(boolean isMove) {
				// TODO Auto-generated method stub
				if (isMove) {
					ll_kline_left_k.setVisibility(View.VISIBLE);
				} else {
					ll_kline_left_k.setVisibility(View.GONE);
				}
			}
		});
		k_line_h.setKLineListener(new KLineListener() {

			@Override
			public void transferData(List<OHLCEntity> OHLCData,
									 List<StickEntity> stickEntityList, int position) {
				// TODO Auto-generated method stub
				OHLCEntity oHLCEntity = OHLCData.get(position);
				StickEntity stickEntity = stickEntityList.get(position);
				tv_ma5_h.setText("MA5:  "
						+ Parse.getInstance().parse2String(oHLCEntity.getMA5()));
				tv_ma10_h.setText("MA10:  "
						+ Parse.getInstance()
						.parse2String(oHLCEntity.getMA10()));
				tv_ma20_h.setText("MA20:  "
						+ Parse.getInstance()
						.parse2String(oHLCEntity.getMA20()));

				tv_ma5_bottom_h.setText("MA5:  "
						+ Parse.getInstance().parse2CNString(
						stickEntity.getVMA5(), false));
				tv_ma10_bottom_h.setText("MA10:  "
						+ Parse.getInstance().parse2CNString(
						stickEntity.getVMA10(), false));
				tv_ma20_bottom_h.setText("MA20:  "
						+ Parse.getInstance().parse2CNString(
						stickEntity.getVMA20(), false));

				tv_time_k_h.setText(Parse.getInstance().isNull(
						oHLCEntity.getDate()));
				tv_open_price_k_h.setText(Parse.getInstance().parse2String(
						oHLCEntity.getOpen()));
				tv_close_price_k_h.setText(Parse.getInstance().parse2String(
						oHLCEntity.getClose()));
				tv_high_k_h.setText(Parse.getInstance().parse2String(
						oHLCEntity.getHigh()));
				tv_low_k_h.setText(Parse.getInstance().parse2String(
						oHLCEntity.getLow()));
				tv_zhangdie_k_h.setText(Parse.getInstance().parse2String(
						oHLCEntity.getChange()));
				tv_zhangdiefu_k_h.setText(Parse.getInstance().parse2String(
						oHLCEntity.getChangep() * 100)
						+ "%");
				tv_chengjiaoe_k_h.setText(Parse.getInstance().parse2CNString(
						oHLCEntity.getCje()));
				tv_chengjiaoliang_k_h.setText(Parse.getInstance()
						.parse2CNString(oHLCEntity.getCjl(), false));
				double zhD = Parse.getInstance().parseDouble(
						Parse.getInstance()
								.parse2String(oHLCEntity.getChange()));
				if (zhD > 0) {
					tv_zhangdie_k_h.setTextColor(getResources().getColor(
							R.color.z_red));
					tv_zhangdiefu_k_h.setTextColor(getResources().getColor(
							R.color.z_red));
				} else if (zhD < 0) {
					tv_zhangdie_k_h.setTextColor(getResources().getColor(
							R.color.z_green));
					tv_zhangdiefu_k_h.setTextColor(getResources().getColor(
							R.color.z_green));
				} else {
					tv_zhangdie_k_h.setTextColor(Color.WHITE);
					tv_zhangdiefu_k_h.setTextColor(Color.WHITE);
				}

				double x = Parse.getInstance().parseDouble(
						Parse.getInstance().parse2String(
								oHLCEntity.getOpen()
										- (position <= 0 ? oHLCEntity.getOpen()
										: OHLCData.get(position - 1)
										.getClose())));
				if (x < 0) {
					tv_open_price_k_h.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (x > 0) {
					tv_open_price_k_h.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_open_price_k_h.setTextColor(Color.WHITE);
				}

				double change = oHLCEntity.getChange();
				if (change < 0) {
					tv_close_price_k_h.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (change > 0) {
					tv_close_price_k_h.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_close_price_k_h.setTextColor(Color.WHITE);
				}

				double h = Parse.getInstance().parseDouble(
						Parse.getInstance().parse2String(
								oHLCEntity.getHigh()
										- (position <= 0 ? oHLCEntity.getOpen()
										: OHLCData.get(position - 1)
										.getClose())));
				if (h < 0) {
					tv_high_k_h.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (h > 0) {
					tv_high_k_h.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_high_k_h.setTextColor(Color.WHITE);
				}

				double l = Parse.getInstance().parseDouble(
						Parse.getInstance().parse2String(
								oHLCEntity.getLow()
										- (position <= 0 ? oHLCEntity.getOpen()
										: OHLCData.get(position - 1)
										.getClose())));
				if (l < 0) {
					tv_low_k_h.setTextColor(getResources().getColor(
							R.color.z_green));
				} else if (l > 0) {
					tv_low_k_h.setTextColor(getResources().getColor(
							R.color.z_red));
				} else {
					tv_low_k_h.setTextColor(Color.WHITE);
				}
			}

			@Override
			public void isMove(boolean isMove) {
				// TODO Auto-generated method stub
				if (isMove) {
					ll_kline_left_k_h.setVisibility(View.VISIBLE);
				} else {
					ll_kline_left_k_h.setVisibility(View.GONE);
				}
			}
		});
	}

	private int currentIndex = R.id.tv_news;

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ll_back:
				Log.i("GgxqActivity", "===back");
				this.finish();
				break;
			case R.id.ggxqtitle_refresh:
				Log.i("GgxqActivity", "===title_refresh");
				ivRefresh.startAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.my_rotate_360_reverse));
				handler.removeCallbacks(runGGXQRunnable);
				handler.postDelayed(runGGXQRunnable, 1000);
				break;
			case R.id.tv_time:
				Log.i("GgxqActivity", "===tv_time");
				rl_fs.setVisibility(View.VISIBLE);
				rl_fs_h.setVisibility(View.VISIBLE);
				kxianLayout.setVisibility(View.GONE);
				kxianLayout_h.setVisibility(View.GONE);
				tv_time.setTextColor(getResources().getColor(
						R.color.z_click_text));
				tv_day.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_week.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_month.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_time_h.setTextColor(getResources().getColor(
						R.color.z_click_text));
				tv_day_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_week_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_month_h.setTextColor(getResources().getColor(
						R.color.z_list_title));

				break;

			case R.id.tv_day:
				Log.i("GgxqActivity", "===tv_day");
				rl_fs.setVisibility(View.GONE);
				kxianLayout.setVisibility(View.VISIBLE);
				rl_fs_h.setVisibility(View.GONE);
				kxianLayout_h.setVisibility(View.VISIBLE);

				tv_time.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_day.setTextColor(getResources().getColor(
						R.color.z_click_text));
				tv_week.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_month.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_time_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_day_h.setTextColor(getResources().getColor(
						R.color.z_click_text));
				tv_week_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_month_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				cycle = 1;
				new asyncTaskLoadKLineData().execute(String.valueOf(1));
				break;
			case R.id.tv_week:
				Log.i("GgxqActivity", "===tv_week");
				rl_fs.setVisibility(View.GONE);
				kxianLayout.setVisibility(View.VISIBLE);
				rl_fs_h.setVisibility(View.GONE);
				kxianLayout_h.setVisibility(View.VISIBLE);

				tv_time.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_day.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_week.setTextColor(getResources().getColor(
						R.color.z_click_text));
				tv_month.setTextColor(getResources().getColor(
						R.color.z_list_title));

				tv_time_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_day_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_week_h.setTextColor(getResources().getColor(
						R.color.z_click_text));
				tv_month_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				cycle = 5;
				new asyncTaskLoadKLineData().execute(String.valueOf(5));
				break;
			case R.id.tv_month:
				Log.i("GgxqActivity", "===tv_month");
				rl_fs.setVisibility(View.GONE);
				kxianLayout.setVisibility(View.VISIBLE);
				rl_fs_h.setVisibility(View.GONE);
				kxianLayout_h.setVisibility(View.VISIBLE);

				tv_time.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_day.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_week.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_month.setTextColor(getResources().getColor(
						R.color.z_click_text));

				tv_time_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_day_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_week_h.setTextColor(getResources().getColor(
						R.color.z_list_title));
				tv_month_h.setTextColor(getResources().getColor(
						R.color.z_click_text));

				cycle = 30;
				new asyncTaskLoadKLineData().execute(String.valueOf(30));
				break;

			case R.id.tv_wd:
				// 五档按钮
				if (ll_wd.getVisibility() == View.GONE
						|| ll_wd_h.getVisibility() == View.GONE) {
					setWdMx();
				}
				break;

			case R.id.tv_mx:
				// 明细按钮
				if (ll_mx.getVisibility() == View.GONE
						|| ll_mx_h.getVisibility() == View.GONE) {
					setWdMx();
				}
				break;

			case R.id.ll_wd:
			case R.id.ll_mx:
				setWdMx();
				break;
			case R.id.tv_fuquan:
				if (Parse.getInstance().isNull(tv_fuquan.getTag()).equals("0")) {
					tv_fuquan.setTag("1");
					tv_fuquan.setText("前复权");
					tv_fuquan_h.setTag("1");
					tv_fuquan_h.setText("前复权");
				} else if (Parse.getInstance().isNull(tv_fuquan.getTag())
						.equals("1")) {
					tv_fuquan.setTag("2");
					tv_fuquan.setText("后复权");
					tv_fuquan_h.setTag("2");
					tv_fuquan_h.setText("后复权");
				} else {
					tv_fuquan.setTag("0");
					tv_fuquan.setText("不复权");
					tv_fuquan_h.setTag("0");
					tv_fuquan_h.setText("不复权");
				}
				new asyncTaskLoadKLineData().execute(String.valueOf(cycle));
				break;

			case R.id.iv_add_clear:
				pd.show();
			if (Parse.getInstance().parseBool(iv_add_clear.getTag())) {
				new GetDataToJSONThread(
						getApplicationContext(),
						String.format(
								UtilHttp.URL_ADD_SELF+"stockNo=%s",
								DataCenter.getInstance().ggxqBean.code),
						handler, 0x4003, 0x4005, 0x4004, false).start();
			} else {
				new GetDataToJSONThread(
						getApplicationContext(),
						String.format(
								UtilHttp.URL_DEL_SELF+"stockNo=%s",
								DataCenter.getInstance().ggxqBean.code),
						handler, 0x4006, 0x4008, 0x4007, false).start();
			}
				break;

			case R.id.tv_news_1:
				setNews();
				break;
			case R.id.tv_notice_1:
				setNotice();
				break;
			case R.id.tv_f10_1:
				setF10();
				break;
			default:
				break;
		}
	}

	/**
	 * 设置五档数据
	 */
	private void setWuDang() {
		String sp1 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp1);
		tv_sp1.setText(sp1.equals("0.00") ? "--" : sp1);
		String sp2 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp2);
		tv_sp2.setText(sp2.equals("0.00") ? "--" : sp2);
		String sp3 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp3);
		tv_sp3.setText(sp3.equals("0.00") ? "--" : sp3);
		String sp4 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp4);
		tv_sp4.setText(sp4.equals("0.00") ? "--" : sp4);
		String sp5 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp5);
		tv_sp5.setText(sp5.equals("0.00") ? "--" : sp5);

		String sv1 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv1)
						/ 100 + "");
		tv_sv1.setText(sv1.equals("0") ? "--" : sv1);
		String sv2 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv2)
						/ 100 + "");
		tv_sv2.setText(sv2.equals("0") ? "--" : sv2);
		String sv3 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv3)
						/ 100 + "");
		tv_sv3.setText(sv3.equals("0") ? "--" : sv3);
		String sv4 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv4)
						/ 100 + "");
		tv_sv4.setText(sv4.equals("0") ? "--" : sv4);
		String sv5 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv5)
						/ 100 + "");
		tv_sv5.setText(sv5.equals("0") ? "--" : sv5);

		String bp1 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp1);
		tv_bp1.setText(bp1.equals("0.00") ? "--" : bp1);
		String bp2 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp2);
		tv_bp2.setText(bp2.equals("0.00") ? "--" : bp2);
		String bp3 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp3);
		tv_bp3.setText(bp3.equals("0.00") ? "--" : bp3);
		String bp4 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp4);
		tv_bp4.setText(bp4.equals("0.00") ? "--" : bp4);
		String bp5 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp5);
		tv_bp5.setText(bp5.equals("0.00") ? "--" : bp5);

		String bv1 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv1)
						/ 100 + "");
		tv_bv1.setText(bv1.equals("0") ? "--" : bv1);
		String bv2 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv2)
						/ 100 + "");
		tv_bv2.setText(bv2.equals("0") ? "--" : bv2);
		String bv3 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv3)
						/ 100 + "");
		tv_bv3.setText(bv3.equals("0") ? "--" : bv3);
		String bv4 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv4)
						/ 100 + "");
		tv_bv4.setText(bv4.equals("0") ? "--" : bv4);
		String bv5 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv5)
						/ 100 + "");
		tv_bv5.setText(bv5.equals("0") ? "--" : bv5);

		ArrayList<TextView> list = new ArrayList<TextView>();
		list.add(tv_sp1);
		list.add(tv_sp2);
		list.add(tv_sp3);
		list.add(tv_sp4);
		list.add(tv_sp5);
		list.add(tv_bp1);
		list.add(tv_bp2);
		list.add(tv_bp3);
		list.add(tv_bp4);
		list.add(tv_bp5);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().toString().equals("--")) {
				list.get(i).setTextColor(
						getResources().getColor(R.color.z_green));
			} else {
				if (Parse.getInstance().parseFloat(
						DataCenter.getInstance().ggxqBean.changep) > 0) {
					list.get(i).setTextColor(
							getResources().getColor(R.color.z_red));
				} else if (Parse.getInstance().parseFloat(
						DataCenter.getInstance().ggxqBean.changep) < 0) {
					list.get(i).setTextColor(
							getResources().getColor(R.color.z_green));
				} else {
					list.get(i).setTextColor(Color.WHITE);
				}
			}
		}
	}

	/**
	 * 设置横屏五档数据
	 */
	private void setWuDangH() {
		String sp1 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp1);
		tv_sp1_h.setText(sp1.equals("0.00") ? "--" : sp1);
		String sp2 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp2);
		tv_sp2_h.setText(sp2.equals("0.00") ? "--" : sp2);
		String sp3 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp3);
		tv_sp3_h.setText(sp3.equals("0.00") ? "--" : sp3);
		String sp4 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp4);
		tv_sp4_h.setText(sp4.equals("0.00") ? "--" : sp4);
		String sp5 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.sp5);
		tv_sp5_h.setText(sp5.equals("0.00") ? "--" : sp5);

		String sv1 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv1)
						/ 100 + "");
		tv_sv1_h.setText(sv1.equals("0") ? "--" : sv1);
		String sv2 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv2)
						/ 100 + "");
		tv_sv2_h.setText(sv2.equals("0") ? "--" : sv2);
		String sv3 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv3)
						/ 100 + "");
		tv_sv3_h.setText(sv3.equals("0") ? "--" : sv3);
		String sv4 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv4)
						/ 100 + "");
		tv_sv4_h.setText(sv4.equals("0") ? "--" : sv4);
		String sv5 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.sv5)
						/ 100 + "");
		tv_sv5_h.setText(sv5.equals("0") ? "--" : sv5);

		String bp1 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp1);
		tv_bp1_h.setText(bp1.equals("0.00") ? "--" : bp1);
		String bp2 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp2);
		tv_bp2_h.setText(bp2.equals("0.00") ? "--" : bp2);
		String bp3 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp3);
		tv_bp3_h.setText(bp3.equals("0.00") ? "--" : bp3);
		String bp4 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp4);
		tv_bp4_h.setText(bp4.equals("0.00") ? "--" : bp4);
		String bp5 = Parse.getInstance().parse2String(
				DataCenter.getInstance().ggxqBean.bp5);
		tv_bp5_h.setText(bp5.equals("0.00") ? "--" : bp5);

		String bv1 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv1)
						/ 100 + "");
		tv_bv1_h.setText(bv1.equals("0") ? "--" : bv1);
		String bv2 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv2)
						/ 100 + "");
		tv_bv2_h.setText(bv2.equals("0") ? "--" : bv2);
		String bv3 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv3)
						/ 100 + "");
		tv_bv3_h.setText(bv3.equals("0") ? "--" : bv3);
		String bv4 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv4)
						/ 100 + "");
		tv_bv4_h.setText(bv4.equals("0") ? "--" : bv4);
		String bv5 = Parse.getInstance().parseToCHString(
				Parse.getInstance().parseInt(
						DataCenter.getInstance().ggxqBean.bv5)
						/ 100 + "");
		tv_bv5_h.setText(bv5.equals("0") ? "--" : bv5);

		ArrayList<TextView> list = new ArrayList<TextView>();
		list.add(tv_sp1_h);
		list.add(tv_sp2_h);
		list.add(tv_sp3_h);
		list.add(tv_sp4_h);
		list.add(tv_sp5_h);
		list.add(tv_bp1_h);
		list.add(tv_bp2_h);
		list.add(tv_bp3_h);
		list.add(tv_bp4_h);
		list.add(tv_bp5_h);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getText().toString().equals("--")) {
				list.get(i).setTextColor(
						getResources().getColor(R.color.z_green));
			} else {
				if (Parse.getInstance().parseFloat(
						DataCenter.getInstance().ggxqBean.changep) > 0) {
					list.get(i).setTextColor(
							getResources().getColor(R.color.z_red));
				} else if (Parse.getInstance().parseFloat(
						DataCenter.getInstance().ggxqBean.changep) < 0) {
					list.get(i).setTextColor(
							getResources().getColor(R.color.z_green));
				} else {
					list.get(i).setTextColor(Color.WHITE);
				}
			}
		}
	}

	/**
	 * 设置五档与明细档切换
	 */
	private void setWdMx() {
		if (ll_wd.getVisibility() == View.GONE
				|| ll_wd_h.getVisibility() == View.GONE) {
			ll_wd.setVisibility(View.VISIBLE);
			ll_mx.setVisibility(View.GONE);
			tv_wd.setTextColor(Color.WHITE);
			tv_mx.setBackgroundColor(Color.TRANSPARENT);
			tv_mx.setTextColor(0xff7c8794);

			ll_wd_h.setVisibility(View.VISIBLE);
			ll_mx_h.setVisibility(View.GONE);
			tv_wd_h.setTextColor(Color.WHITE);
			tv_mx_h.setBackgroundColor(Color.TRANSPARENT);
			tv_mx_h.setTextColor(0xff7c8794);
		} else {
			ll_wd.setVisibility(View.GONE);
			ll_mx.setVisibility(View.VISIBLE);
			tv_mx.setTextColor(Color.WHITE);
			tv_wd.setBackgroundColor(Color.TRANSPARENT);
			tv_wd.setTextColor(0xff7c8794);

			ll_wd_h.setVisibility(View.GONE);
			ll_mx_h.setVisibility(View.VISIBLE);
			tv_mx_h.setTextColor(Color.WHITE);
			tv_wd_h.setBackgroundColor(Color.TRANSPARENT);
			tv_wd_h.setTextColor(0xff7c8794);
		}
	}

	/**
	 * 设置MA初始值
	 */
	private void setMAValue() {
		if (DataCenter.listOhlc != null && DataCenter.listOhlc.size() > 0) {
			tv_ma5.setText("MA5:  "
					+ Parse.getInstance().parse2String(
					DataCenter.listOhlc.get(
							DataCenter.listOhlc.size() - 1).getMA5()));
			tv_ma10.setText("MA10:  "
					+ Parse.getInstance().parse2String(
					DataCenter.listOhlc.get(
							DataCenter.listOhlc.size() - 1).getMA10()));
			tv_ma20.setText("MA20:  "
					+ Parse.getInstance().parse2String(
					DataCenter.listOhlc.get(
							DataCenter.listOhlc.size() - 1).getMA20()));
			tv_ma5_h.setText("MA5:  "
					+ Parse.getInstance().parse2String(
					DataCenter.listOhlc.get(
							DataCenter.listOhlc.size() - 1).getMA5()));
			tv_ma10_h.setText("MA10:  "
					+ Parse.getInstance().parse2String(
					DataCenter.listOhlc.get(
							DataCenter.listOhlc.size() - 1).getMA10()));
			tv_ma20_h.setText("MA20:  "
					+ Parse.getInstance().parse2String(
					DataCenter.listOhlc.get(
							DataCenter.listOhlc.size() - 1).getMA20()));
		} else {
			tv_ma5.setText("MA5:  -");
			tv_ma10.setText("MA10:  -");
			tv_ma20.setText("MA20:  -");
			tv_ma5_h.setText("MA5:  -");
			tv_ma10_h.setText("MA10:  -");
			tv_ma20_h.setText("MA20:  -");
		}
		if (DataCenter.vol != null && DataCenter.vol.size() > 0) {
			tv_ma5_bottom.setText("MA5:  "
					+ Parse.getInstance().parse2CNString(
					DataCenter.vol.get(DataCenter.vol.size() - 1)
							.getVMA5(), false));
			tv_ma10_bottom.setText("MA10:  "
					+ Parse.getInstance().parse2CNString(
					DataCenter.vol.get(DataCenter.vol.size() - 1)
							.getVMA10(), false));
			tv_ma20_bottom.setText("MA20:  "
					+ Parse.getInstance().parse2CNString(
					DataCenter.vol.get(DataCenter.vol.size() - 1)
							.getVMA20(), false));
			tv_ma5_bottom_h.setText("MA5:  "
					+ Parse.getInstance().parse2CNString(
					DataCenter.vol.get(DataCenter.vol.size() - 1)
							.getVMA5(), false));
			tv_ma10_bottom_h.setText("MA10:  "
					+ Parse.getInstance().parse2CNString(
					DataCenter.vol.get(DataCenter.vol.size() - 1)
							.getVMA10(), false));
			tv_ma20_bottom_h.setText("MA20:  "
					+ Parse.getInstance().parse2CNString(
					DataCenter.vol.get(DataCenter.vol.size() - 1)
							.getVMA20(), false));
		} else {
			tv_ma5_bottom.setText("MA5:  -");
			tv_ma10_bottom.setText("MA10:  -");
			tv_ma20_bottom.setText("MA20:  -");
			tv_ma5_bottom_h.setText("MA5:  -");
			tv_ma10_bottom_h.setText("MA10:  -");
			tv_ma20_bottom_h.setText("MA20:  -");
		}
	}

	/**
	 * 定时刷新
	 *
	 * @param code
	 */
	private void runGghq(final String code) {
		new Thread() {
			@Override
			public void run() {

				DataCenter.getInstance().mStrGghq = DataCenter.getInstance().http
						.getGghq(code);
				if (DataCenter.getInstance().mStrGghq != null
						&& DataCenter.getInstance().mStrGghq.length() > 0) {
					DataCenter.getInstance().parseAutoupdate(
							DataCenter.getInstance().mStrGghq);
					DataCenter.getInstance().parserGgxq();
					DataCenter.getInstance().parserGgxqTime();
					DataCenter.getInstance().parseMX();

					handler.sendEmptyMessage(0x2000);
				} else {
					handler.sendEmptyMessage(0x9999);
				}

//				 DataCenter.getInstance().mStrGghq =
//				 DataCenter.getInstance().http
//				 .getGghq(code);
//				 if (DataCenter.getInstance().mStrGghq.length() > 20) {
//				 handler.sendEmptyMessage(com.weitou.stock.Globals.HANDLE_GGHQ);
//				 }
			}
		}.start();
	}

	class MyOnItemClickListener1 implements OnItemClickListener1 {

		@Override
		public void onItemClickListener(View v, int position,
										List<HashMap<String, String>> list) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
				case R.id.tv_news:
					setNews();
					break;
				case R.id.tv_notice:
					setNotice();
					break;
				case R.id.tv_f10:
					setF10();
					break;
				case R.id.rl_item:
					// 内容
					goToNews(position);
					break;
				case R.id.tv_loadmore:
					// 加载更多
					list.get(list.size() - 1).put("isLoading", "true");
					datapaters.setData(list, isShowNewsAndF10);
					index += 10;
					new asyncTaskLoadLoadXW().execute(newsGongGao,
							String.valueOf(index));
					break;

				default:
					break;
			}
		}
	}

	/**
	 * 新闻
	 */
	private void setNews() {
		// 新闻
		if (datapaters != null) {
			datapaters.setTitleBackground(R.id.tv_news);
		}
		index = 3;
		newsGongGao = "newslist";
		tv_news_1.setBackgroundColor(getResources().getColor(
				R.color.z_colorblock));
		tv_news_1.setTextColor(Color.WHITE);
		tv_notice_1.setBackgroundColor(0xff1a2c43);
		tv_notice_1.setTextColor(getResources().getColor(R.color.z_list_title));
		tv_f10_1.setBackgroundColor(0xff1a2c43);
		tv_f10_1.setTextColor(getResources().getColor(R.color.z_list_title));

		currentIndex = R.id.tv_news;
		xwdata.clear();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("isRefresh", "true");
		xwdata.add(map);
		datapaters.setData(xwdata, isShowNewsAndF10);
		content_view.setSelection(1);
		new asyncTaskLoadLoadXW().execute(newsGongGao, String.valueOf(index));
		wv_f10.setVisibility(View.GONE);
	}

	/**
	 * 设置公告
	 */
	private void setNotice() {
		// 公告
		if (datapaters != null) {
			datapaters.setTitleBackground(R.id.tv_notice);
		}
		index = 3;
		newsGongGao = "noticelist";
		tv_notice_1.setBackgroundColor(getResources().getColor(
				R.color.z_colorblock));
		tv_notice_1.setTextColor(Color.WHITE);
		tv_news_1.setBackgroundColor(0xff1a2c43);
		tv_news_1.setTextColor(getResources().getColor(R.color.z_list_title));
		tv_f10_1.setBackgroundColor(0xff1a2c43);
		tv_f10_1.setTextColor(getResources().getColor(R.color.z_list_title));

		currentIndex = R.id.tv_notice;
		xwdata.clear();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("isRefresh", "true");
		xwdata.add(map1);
		datapaters.setData(xwdata, isShowNewsAndF10);
		content_view.setSelection(1);
		new asyncTaskLoadLoadXW().execute(newsGongGao, String.valueOf(index));
		wv_f10.setVisibility(View.GONE);
	}

	/**
	 * 设置F10
	 */
	private void setF10() {
		// F10
		if (datapaters != null) {
			datapaters.setTitleBackground(R.id.tv_f10);
		}
		tv_f10_1.setBackgroundColor(getResources().getColor(
				R.color.z_colorblock));
		tv_f10_1.setTextColor(Color.WHITE);
		tv_news_1.setBackgroundColor(0xff1a2c43);
		tv_news_1.setTextColor(getResources().getColor(R.color.z_list_title));
		tv_notice_1.setBackgroundColor(0xff1a2c43);
		tv_notice_1.setTextColor(getResources().getColor(R.color.z_list_title));

		xwdata.clear();
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("isShowF10", "true");
		xwdata.add(map2);
		datapaters.setData(xwdata, isShowNewsAndF10);
		content_view.setSelection(1);
		if (isLoadState) {
			wv_f10.setVisibility(View.VISIBLE);
			return;
		}
		ll_loading.setVisibility(View.VISIBLE);
		wv_f10.setVisibility(View.INVISIBLE);
		LayoutParams lp = wv_f10.getLayoutParams();
		lp.height = height;
		wv_f10.setLayoutParams(lp);
		// wv_f10.getSettings().setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		wv_f10.getSettings().setUseWideViewPort(true);
		wv_f10.getSettings().setJavaScriptEnabled(true);
		// wv_f10.setFocusable(true);

		// wv_f10.reload();
		// wv_f10.loadUrl(String.format(
		// "http://stock.microinvestment.cn/F10Index.aspx?code=%s", _code));
		//http://gphq.mytv365.com/staticHTML/F10_300059.HTML
		String URL = "http://gphq.mytv365.com/staticHTML/F10_"+_code+".html";
//		wv_f10.loadUrl(String
//				.format("http://gphq.mytv365.com/staticHTML/F10Index1.aspx?code=%s",
//						_code));
		wv_f10.loadUrl(URL);
	}

	/**
	 * 监听网络状态的广播
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (Consts.ggxqAction.equals(intent.getAction())) {
				ivRefresh.startAnimation(AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.my_rotate_360_reverse));
				handler.removeCallbacks(runGGXQRunnable);
				handler.postDelayed(runGGXQRunnable, 1000);
				wv_f10.onResume();
			}
		}
	};

	/**
	 * 获取数据的线程
	 *
	 * @author dingrui
	 *
	 */
	class RunGGXQRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			runGghq(_code);
		}

	}

	/**
	 * 定时器
	 */
	class TimeThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isStart) {
				try {
					// long t = System.currentTimeMillis();
					String time = MyUtils.getInstance().getTime24(
							"yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
					time = time.substring(11, 16);
					if ("09:30".equals(time) || "13:00".equals(time)) {
						ivRefresh.startAnimation(AnimationUtils.loadAnimation(
								getApplicationContext(),
								R.anim.my_rotate_360_reverse));
						handler.removeCallbacks(runGGXQRunnable);
						handler.postDelayed(runGGXQRunnable, 1000);
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 判断线程是否活动状态
	 *
	 * @param thread
	 * @return
	 */
	private boolean isAlive(Thread thread) {
		if (thread == null) {
			return false;
		} else {
			if (thread.isAlive())
				return true;
			else
				return false;
		}
	}
}
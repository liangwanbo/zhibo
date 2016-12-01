package com.mytv365.zb.views.activity.stock;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.adapters.HotPanelAdapter;
import com.mytv365.zb.adapters.ListStockAdapter;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.common.Globals;
import com.mytv365.zb.model.ItemStock;
import com.mytv365.zb.widget.webview.MyProgressDialog;


/**
 * 沪深行情
 */
public class HshqActivity extends Activity implements OnClickListener {
	LinearLayout back;
	ImageView ivSearch;
	RelativeLayout layout_hot;
	RelativeLayout layout_increase;
	// RelativeLayout layout_increase_low;

	TextView hot_1;
	TextView hot_2;
	TextView hot_3;
	TextView hot_4;
	TextView hot_5;
	TextView hot_6;

	ListStockAdapter listStockAdapter;
	ListView lvUp;

	/** Dialog */
	private MyProgressDialog pd;// 进度条
	private boolean isSend;// 是否发送消息到handler
	// ListView lvDown;

	private Handler handler = new Handler() {
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Globals.HANDLE_GGHQ:

				Bundle bundle = msg.getData();
				Intent intent = new Intent(HshqActivity.this,
						GgxqActivity.class);
				intent.putExtra("code", bundle.getString("code"));
				intent.putExtra("market", bundle.getString("market"));
				startActivity(intent);
				break;

			case 0x2000:
				if (listStockAdapter != null)
					listStockAdapter.notifyDataSetChanged();
				else
					listStockAdapter = new ListStockAdapter(HshqActivity.this,
							null);
				break;

			case 0x2001:
				runHshq();
				handler.sendEmptyMessageDelayed(0x2001,
						Integer.parseInt(getString(R.string.timing)));
				break;
			default:
				break;
			}
			if (pd.isShowing())
				pd.dismiss();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_hshq);
		pd = new MyProgressDialog(this, R.style.CustomProgressDialog);
		pd.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if (!pd.isShowing())
					isSend = false;
			}
		});
		initView();
		setViewData();
		initEvent();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		handler.removeMessages(0x2001);
		handler.sendEmptyMessage(0x2001);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		handler.removeMessages(0x2001);
	}

	private GridView hotGridView;
	private HotPanelAdapter hotAdapter;

	private void initView() {
		back = (LinearLayout) findViewById(R.id.ll_back);
		back.setOnClickListener(this);
		ivSearch = (ImageView) findViewById(R.id.title_search);
		ivSearch.setOnClickListener(this);
		layout_hot = (RelativeLayout) findViewById(R.id.layout_hot);
		layout_hot.setOnClickListener(this);
		layout_increase = (RelativeLayout) findViewById(R.id.layout_increase);
		layout_increase.setOnClickListener(this);
		hotGridView = (GridView) findViewById(R.id.hotlayoutid);
		lvUp = (ListView) findViewById(R.id.list_up);
	}

	private void initEvent() {
		hotAdapter = new HotPanelAdapter(this);
		hotGridView.setAdapter(hotAdapter);
		hotGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				gotoZyBack(arg2);
			}
		});
		lvUp.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				gotoZfOrDF(arg2);
			}
		});
		lvUp.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
					hotGridView.setVisibility(View.GONE);
				}
			}

		});
	}

	private void setViewData() {
		// layout_increase_low =
		// (RelativeLayout)findViewById(R.id.layout_increase_low);
		// layout_increase_low.setOnClickListener(this);
		// 热门板块

		/*
		 * ItemStock item; String name; hot_1 = (TextView)
		 * findViewById(R.id.hot_1); item =
		 * DataCenter.getInstance().listHot.get(0); name =
		 * item.name+"\n"+item.code+"\n"+item.cjl; hot_1.setText(name);
		 * hot_1.setOnClickListener(this); hot_2 = (TextView)
		 * findViewById(R.id.hot_2); item =
		 * DataCenter.getInstance().listHot.get(1); name =
		 * item.name+"\n"+item.code+"\n"+item.cjl; hot_2.setText(name);
		 * hot_2.setOnClickListener(this); hot_3 = (TextView)
		 * findViewById(R.id.hot_3); item =
		 * DataCenter.getInstance().listHot.get(2); name =
		 * item.name+"\n"+item.code+"\n"+item.cjl; hot_3.setText(name);
		 * hot_3.setOnClickListener(this); hot_4 = (TextView)
		 * findViewById(R.id.hot_4); item =
		 * DataCenter.getInstance().listHot.get(3); name =
		 * item.name+"\n"+item.code+"\n"+item.cjl; hot_4.setText(name);
		 * hot_4.setOnClickListener(this); hot_5 = (TextView)
		 * findViewById(R.id.hot_5); item =
		 * DataCenter.getInstance().listHot.get(4); name =
		 * item.name+"\n"+item.code+"\n"+item.cjl; hot_5.setText(name);
		 * hot_5.setOnClickListener(this); hot_6 = (TextView)
		 * findViewById(R.id.hot_6); item =
		 * DataCenter.getInstance().listHot.get(5); name =
		 * item.name+"\n"+item.code+"\n"+item.cjl; hot_6.setText(name);
		 * hot_6.setOnClickListener(this);
		 */
		// 涨幅榜
		// array = jsonObject.optJSONArray("stockup");
		// if (array != null) {
		// for (int i = 0; i < array.length(); i++) {
		// item = new ItemStock();
		// item.name = array.getJSONObject(i).getString("name");
		// item.code = array.getJSONObject(i).getString("code");
		// item.close = array.getJSONObject(i).getString("close");
		// item.change = array.getJSONObject(i).getString("change");
		// item.changep = array.getJSONObject(i).getString("changep");
		// item.cjl = array.getJSONObject(i).getString("cjl");
		// item.cje = array.getJSONObject(i).getString("cje");
		// item.market = array.getJSONObject(i).getString("market");
		// DataCenter.getInstance().listStockUp.add(item);
		// }
		// }

		listStockAdapter = new ListStockAdapter(this, null);
		lvUp.setAdapter(listStockAdapter);
		/*
		 * //跌幅榜 //listStockAdapter = new ListStockAdapter(this,
		 * DataCenter.getInstance().listStockDown); lvDown = (ListView)
		 * findViewById(R.id.list_down); //lvDown.setAdapter(listStockAdapter);
		 * lvDown.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) { Log.i("SelfActivity", "===onItemClick id:"+arg2);
		 * String code = DataCenter.getInstance().listStockDown.get(arg2).code;
		 * Log.i("SelfActivity", "===code:"+code); String market =
		 * DataCenter.getInstance().listStockDown.get(arg2).market;
		 * runGghq(code,market); } });
		 */
	}

	public void gotoZyBack(int position) {
		ItemStock itemstack = DataCenter.getInstance().listHot.get(position);
		Intent intent = new Intent(this, IncreaseActivity.class);
		intent.putExtra("index", 2);
		intent.putExtra("t", itemstack.name);
		intent.putExtra("code", itemstack.code);
		startActivity(intent);
	}

	private void gotoZfOrDF(int position) {
		Log.i("SelfActivity", "===onItemClick id:" + position);
		ItemStock itemStock = DataCenter.getInstance().listStockUp
				.get(position);
		if (itemStock.item == 1 && itemStock.index == 1) {
			// 涨幅

			Intent intent = new Intent(this, IncreaseActivity.class);
			intent.putExtra("index", 1);
			startActivity(intent);
			return;

		}
		if (itemStock.item == 1 && itemStock.index == -1) {
			// 跌幅
			Intent intent = new Intent(this, IncreaseActivity.class);
			intent.putExtra("index", -1);
			startActivity(intent);
			return;

		}
		if (itemStock.item == 1 && itemStock.index == 2) {
			// df
			Intent intent = new Intent(this, HotActivity.class);
			intent.putExtra("index", -1);
			startActivity(intent);
			return;

		}
		String code = itemStock.code;
		Log.i("SelfActivity", "===code:" + code);
		String market = itemStock.market;
		if (code == null || market == null) {
			return;
		}
		if (itemStock.item == 0
				&& (itemStock.index == 1 || itemStock.index == -1)) {
			// ->ggxq
			isSend = true;
			pd.show();
			runGghq(code, market);
			return;
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_back://
			Log.i("HshqActivity", "===back");
			this.finish();
			break;
		case R.id.title_search:
			Log.i("HshqActivity", "===title_search");
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			// this.finish();
			break;
		case R.id.layout_hot:
			Log.i("HshqActivity", "===layout_hot");
			intent = new Intent(this, HotActivity.class);
			startActivity(intent);
			// this.finish();
			break;
		/*
		 * case R.id.layout_increase: Log.i("HshqActivity",
		 * "===layout_increase"); intent = new Intent(this,
		 * IncreaseActivity.class); startActivity(intent); break; case
		 * R.id.layout_increase_low: Log.i("HshqActivity",
		 * "===layout_increase_low"); intent = new Intent(this,
		 * IncreaseActivity.class); startActivity(intent); break;
		 */

		default:
			break;
		}
	}

	/**
	 * 个股行情
	 */
	private void runGghq(final String code, final String market) {
		new Thread() {
			@Override
			public void run() {
				DataCenter.getInstance().mStrGghq = DataCenter.getInstance().http
						.getGghq(code);
				if (DataCenter.getInstance().mStrGghq != null
						&& DataCenter.getInstance().mStrGghq.length() > 20) {
					DataCenter.getInstance().parserGgxq();
					DataCenter.getInstance().parserGgxqTime();
					DataCenter.getInstance().parseMX();
					// DataCenter.parseKLine(DataCenter.getInstance().mStrGghq);

					Message muest = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("code", String.valueOf(code));
					bundle.putString("market", market);
					muest.setData(bundle);
					muest.what = Globals.HANDLE_GGHQ;
					if (isSend)
						handler.sendMessage(muest);
				}
			}
		}.start();
	}

	/**
	 * 沪深行情
	 */
	private void runHshq() {
		new Thread() {
			@Override
			public void run() {
//				DataCenter.getInstance().mStrHshq = DataCenter.getInstance().http
//						.getHshq();
//				if (DataCenter.getInstance().mStrHshq.length() > 20) {
//					parserHshq();
					handler.sendEmptyMessage(0x2000);
//				}
			}
		}.start();
	}
	/**
	 * 解析沪深行情
	 */
	private void parserHshq() {
		ItemStock item;
		String []name = {"传媒","机械设备","交通运输","商业运输","农林牧渔","招商轮船"};
		String []code = {"720000","640000","420000","450000","110000","601872"};
		String []close = {"7.880","9.090","23.000","23.680","17.320","11.080"};
		String []change = {"2.410","0.830","2.100","2.160","1.580","1.010"};
		String []changep = {"0.4406","0.1005","0.1005","0.1004","0.1004","0.1003"};
		String []cjl = {"25178500","11722900","1248171756","16650800","1116080","397200794"};;
		String []cje = {"1974826","1065612","14118833106","1971454","966526","42397200794"};
		String [] market = {"1","1","1","1","1","1"};
		for(int i =0; i< name.length; i++){
			item = new ItemStock();
			item.name = name[i];
			item.code = code[i];
			item.close= close[i];
			item.change = change[i];
			item.changep = changep[i];
			item.cjl = cjl[i];
			item.cje = cje[i];
			item.market = market[i];
			DataCenter.getInstance().listHot.add(item);
			
		}
		
		DataCenter.getInstance().listStockUp.clear();

		ItemStock itemUpHotm = new ItemStock();
		itemUpHotm.index = 2;
		itemUpHotm.item = 1;

		DataCenter.getInstance().listStockUp.add(itemUpHotm);

		ItemStock itemUpHot = new ItemStock();
		itemUpHot.index = 2;
		itemUpHot.item = 0;

		DataCenter.getInstance().listStockUp.add(itemUpHot);

		ItemStock itemUpHead = new ItemStock();
		itemUpHead.index = 1;
		itemUpHead.item = 1;
		DataCenter.getInstance().listStockUp.add(itemUpHead);
			// 涨幅榜
		String []nameZ = {"N有线","海伦哲","中海集运","茂业物流","湘潭电化"};
		String []codeZ = {"600959","300201","601866","000889","002125"};
		String []closeZ = {"7.880","9.090","11.500","11.840","15.460"};
		String []changeZ = {"2.410","0.830","1.050","1.080","1.410"};
		String []changepZ = {"0.4406","0.1005","0.1005","0.1004","0.1004"};
		String []cjlZ = {"251785","117229","624085878","83254","1133752"};;
		String []cjeZ = {"1974826","1065612","7059416553","985727","17527806"};
		String [] marketZ = {"1","1","1","1","1"};
		for(int i =0; i< name.length; i++){
			item = new ItemStock();
			item.name = name[i];
			item.code = code[i];
			item.close= close[i];
			item.change = change[i];
			item.changep = changep[i];
			item.cjl = cjl[i];
			item.cje = cje[i];
			item.market = market[i];
			item.item = 0;
			item.index = 1;
			DataCenter.getInstance().listStockUp.add(item);
		}

			// 跌幅榜
		DataCenter.getInstance().listStockDown.clear();
		ItemStock itemDownHead = new ItemStock();
		itemDownHead.index = -1;
		itemDownHead.item = 1;
		DataCenter.getInstance().listStockUp.add(itemDownHead);
			String []nameD = {"深大通","中润资源","绿景控股","四环生物","英特集团"};
			String []codeD = {"000038","000506","000502","000518","002125"};
			String []closeD = {"0.000","0.000","0.000","0.000","0.000"};
			String []changeD = {"-20.410","-10.830","-6.950","-13.080","-5.410"};
			String []changepD = {"-2.4406","1.1205","-0.1000","-1.1004","-1.1004"};
			String []cjlD = {"0","0","0","0","0"};;
			String []cjeD = {"0","0","0","0","0"};
			String [] marketD = {"2","2","2","2","2"};
			for(int i =0; i< name.length; i++){
				item = new ItemStock();
				item.name = name[i];
				item.code = code[i];
				item.close= close[i];
				item.change = change[i];
				item.changep = changep[i];
				item.cjl = cjl[i];
				item.cje = cje[i];
				item.market = market[i];
				item.index = -1;
				item.item = 0;
				DataCenter.getInstance().listStockUp.add(item);
			}

	}
	/**
	 * 解析沪深行情
	 */
//	private void parserHshq() {
//		ItemStock item;
//		try {
//			JSONObject jsondata = new JSONObject(
//					DataCenter.getInstance().mStrHshq);
//			JSONObject jsonObject = jsondata.getJSONObject("block");
//			// 热门板块
//			JSONArray array = jsonObject.optJSONArray("list");
//			if (array != null) {
//				DataCenter.getInstance().listHot.clear();
//				for (int i = 0; i < array.length(); i++) {
//					item = new ItemStock();
//					item.name = array.getJSONObject(i).getString("name");
//					item.code = array.getJSONObject(i).getString("code");
//					item.close = array.getJSONObject(i).getString("close");
//					item.change = array.getJSONObject(i).getString("change");
//					item.changep = array.getJSONObject(i).getString("changep");
//					item.cjl = array.getJSONObject(i).getString("cjl");
//					item.cje = array.getJSONObject(i).getString("cje");
//					item.market = array.getJSONObject(i).getString("market");
//
//					DataCenter.getInstance().listHot.add(item);
//					Log.i("SelfActivity", "=== hot item.name：" + item.name);
//				}
//			}
//			// 涨幅榜
//			jsonObject = jsondata.getJSONObject("stockup");
//			array = jsonObject.optJSONArray("list");
//			if (array != null && array.length() > 0) {
//				DataCenter.getInstance().listStockUp.clear();
//
//				ItemStock itemUpHotm = new ItemStock();
//				itemUpHotm.index = 2;
//				itemUpHotm.item = 1;
//
//				DataCenter.getInstance().listStockUp.add(itemUpHotm);
//
//				ItemStock itemUpHot = new ItemStock();
//				itemUpHot.index = 2;
//				itemUpHot.item = 0;
//
//				DataCenter.getInstance().listStockUp.add(itemUpHot);
//
//				ItemStock itemUpHead = new ItemStock();
//				itemUpHead.index = 1;
//				itemUpHead.item = 1;
//				DataCenter.getInstance().listStockUp.add(itemUpHead);
//				for (int i = 0; i < array.length(); i++) {
//					item = new ItemStock();
//					item.name = array.getJSONObject(i).getString("name");
//					item.code = array.getJSONObject(i).getString("code");
//					item.close = array.getJSONObject(i).getString("close");
//					item.change = array.getJSONObject(i).getString("change");
//					item.changep = array.getJSONObject(i).getString("changep");
//					item.cjl = array.getJSONObject(i).getString("cjl");
//					item.cje = array.getJSONObject(i).getString("cje");
//					item.market = array.getJSONObject(i).getString("market");
//					item.item = 0;
//					item.index = 1;
//					DataCenter.getInstance().listStockUp.add(item);
//					Log.i("SelfActivity", "=== stockup item.name：" + item.name);
//				}
//			}
//			// 跌幅榜
//			jsonObject = jsondata.getJSONObject("stockdown");
//			array = jsonObject.optJSONArray("list");
//			if (array != null && array.length() > 0) {
//				DataCenter.getInstance().listStockDown.clear();
//				ItemStock itemDownHead = new ItemStock();
//				itemDownHead.index = -1;
//				itemDownHead.item = 1;
//				DataCenter.getInstance().listStockUp.add(itemDownHead);
//				for (int i = 0; i < array.length(); i++) {
//					item = new ItemStock();
//					item.name = array.getJSONObject(i).getString("name");
//					item.code = array.getJSONObject(i).getString("code");
//					item.close = array.getJSONObject(i).getString("close");
//					item.change = array.getJSONObject(i).getString("change");
//					item.changep = array.getJSONObject(i).getString("changep");
//					item.cjl = array.getJSONObject(i).getString("cjl");
//					item.cje = array.getJSONObject(i).getString("cje");
//					item.market = array.getJSONObject(i).getString("market");
//					item.index = -1;
//					item.item = 0;
//					// DataCenter.getInstance().listStockDown.add(item);
//					DataCenter.getInstance().listStockUp.add(item);
//					Log.i("SelfActivity", "=== stockdown item.name："
//							+ item.name);
//				}
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
}

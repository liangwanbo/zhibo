package com.mytv365.zb.views.activity.stock;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.adapters.EmListSelfAdapter;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.common.Globals;
import com.mytv365.zb.pullableview.PullToRefreshLayout;
import com.mytv365.zb.pullableview.PullableListView;
import com.mytv365.zb.pullableview.PullToRefreshLayout.OnRefreshListener;
import com.mytv365.zb.utils.UtilHttp;
import com.mytv365.zb.widget.webview.MyProgressDialog;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * 涨幅榜/跌幅榜
 */
public class IncreaseActivity extends Activity implements OnClickListener,
		OnRefreshListener {
	// public ArrayList<Item> list;
	// private MyListAdapter mProcessTaskAdapter;
	// ListView listView;
	LinearLayout back;
	EmListSelfAdapter mSelfAdapter;
	PullToRefreshLayout refresh_view;// 上下拉
	PullableListView content_view;// 可上下拉哒listview
	public LinkedList<HashMap<String, String>> zfStock;
	private int currentindex;
	private ImageView searchButton;
	private TextView titleView;

	private MyProgressDialog pd;
	private boolean isSend;
	private int index;
	private String code;
	private boolean isStop;

	private Handler handler = new Handler() {
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Globals.HANDLE_GGHQ:

				Bundle bundle = msg.getData();
				Intent intent = new Intent(IncreaseActivity.this,
						GgxqActivity.class);
				intent.putExtra("code", bundle.getString("code"));
				intent.putExtra("market", bundle.getString("market"));
				startActivity(intent);
				break;

			case 0x4001:
				new asyncTaskLoadLoadXW().execute("uplist",
						String.valueOf(index));
				if (!isStop)
					sendEmptyMessageDelayed(0x4001, 6 * 1000);
				break;
			case 0x4002:
				new asyncTaskLoadLoadXW().execute("downlist",
						String.valueOf(index));
				if (!isStop)
					sendEmptyMessageDelayed(0x4002, 6 * 1000);
				break;
			case 0x4003:
				if (code != null) {
					new asyncTaskLoadPanelGp().execute(code);
					if (!isStop)
						sendEmptyMessageDelayed(0x4003, 6 * 1000);
				}
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
		setContentView(R.layout.layout_updownlist);
		pd = new MyProgressDialog(this, R.style.CustomProgressDialog);
		pd.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if (!pd.isShowing())
					isSend = false;
			}
		});
		currentindex = this.getIntent().getIntExtra("index", 0);
		if (currentindex == 0) {
			finish();
			return;
		}
		titleView = (TextView) findViewById(R.id.zfb_title);

		searchButton = (ImageView) findViewById(R.id.zfb_searchid);
		// getList();
		zfStock = new LinkedList<HashMap<String, String>>();
		mSelfAdapter = new EmListSelfAdapter(this, zfStock);
		refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		refresh_view.setOnRefreshListener(this);
		content_view = (PullableListView) findViewById(R.id.content_view);
		content_view.setRefresh(false);
		content_view.setLoadMore(true);
		content_view.setAdapter(mSelfAdapter);
		content_view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				selectLine(position);
			}

		});
		findLayout();
		index = 20;
		if (currentindex == 1) {
			titleView.setText(getString(R.string.uppanel));
		} else if (currentindex == -1) {
			titleView.setText(getString(R.string.downpanel));
		} else if (currentindex == 2) {
			String titlename = this.getIntent().getStringExtra("t");
			code = this.getIntent().getStringExtra("code");
			if (code == null || titlename == null) {
				finish();
				return;
			}
			titleView.setText(titlename);
			content_view.setLoadMore(false);
		}
		searchButton.setOnClickListener(this);
		isStop = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isStop = false;
		if (currentindex == 1) {
			handler.removeMessages(0x4001);
			handler.sendEmptyMessage(0x4001);
		} else if (currentindex == -1) {
			handler.removeMessages(0x4002);
			handler.sendEmptyMessage(0x4002);
		} else if (currentindex == 2) {
			handler.removeMessages(0x4003);
			handler.sendEmptyMessage(0x4003);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isStop = true;
		if (currentindex == 1) {
			handler.removeMessages(0x4001);
		} else if (currentindex == -1) {
			handler.removeMessages(0x4002);
		} else if (currentindex == 2) {
			handler.removeMessages(0x4003);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isStop = true;
		if (currentindex == 1) {
			handler.removeMessages(0x4001);
		} else if (currentindex == -1) {
			handler.removeMessages(0x4002);
		} else if (currentindex == 2) {
			handler.removeMessages(0x4003);
		}
	}

	private class asyncTaskLoadPanelGp extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			String waiteString = UtilHttp
					.getNetString(String
							.format("http://stock.microinvestment.cn/Quote.ashx?type=blockstock&code=%s",
									params[0]));

			LinkedList<HashMap<String, String>> dataList = DataCenter
					.parseBakuaiList(waiteString, "list");
			if (dataList != null && dataList.size() > 0) {
				zfStock.clear();
				zfStock.addAll(dataList);
				return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result && zfStock.size() > 0) {
				mSelfAdapter.notifyDataSetChanged();
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private void selectLine(int position) {
		HashMap<String, String> maps = zfStock.get(position);
		String code = maps.get("code");
		String market = maps.get("market");
		isSend = true;
		pd.show();
		runGghq(code, market);
	}

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

	private class asyncTaskLoadLoadXW extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			String index = params[1];
			String waiteString = UtilHttp.getZDF(params[0], index);
			LinkedList<HashMap<String, String>> dataList = DataCenter.parseZDF(
					waiteString, params[0]);
			if (dataList != null && dataList.size() > 0) {
				zfStock.clear();
				zfStock.addAll(dataList);
				return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result && zfStock.size() > 0) {
				mSelfAdapter.notifyDataSetChanged();
				refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				return;
			}
			refresh_view.loadmoreFinish(PullToRefreshLayout.FAIL);
			if (index > 20)
				index -= 20;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	// private void getList() {
	// list = new ArrayList<Item>();
	// Item item;
	// for(int i=0;i<50;i++){
	// item = new Item();
	// item.name ="name_"+i;
	// item.price ="price_"+i;
	// item.price2 ="price2_"+i;
	// item.increase ="10%";
	// Log.i("IncreaseActivity", "===name:"+item.name);
	// list.add(item);
	// }
	// }

	private void findLayout() {
		back = (LinearLayout) findViewById(R.id.ll_back);
		back.setOnClickListener(this);

		// listView = (ListView) findViewById(R.id.list);
		// mProcessTaskAdapter = new MyListAdapter(this,list);
		// listView.setAdapter(mProcessTaskAdapter);
		// listView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// Log.i("IncreaseActivity", "===list id:"+arg2);
		// }
		// });
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_back://
			Log.i("IncreaseActivity", "===back");
			this.finish();
			break;

		case R.id.zfb_searchid:
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		index += 20;
		if (currentindex == 1) {
			// new asyncTaskLoadLoadXW().execute("uplist",
			// String.valueOf(index));
			handler.removeMessages(0x4001);
			handler.sendEmptyMessage(0x4001);
		} else if (currentindex == -1) {
			// new asyncTaskLoadLoadXW()
			// .execute("downlist", String.valueOf(index));
			handler.removeMessages(0x4002);
			handler.sendEmptyMessage(0x4002);
		}
	}
}

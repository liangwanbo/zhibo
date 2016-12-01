package com.mytv365.zb.views.activity.stock;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mytv365.zb.R;
import com.mytv365.zb.adapters.HotListPanelAdapter;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.utils.UtilHttp;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * 热门板块
 */
public class HotActivity extends Activity implements OnClickListener {
	// ListView listView;
	LinearLayout back;
	private ImageView ivSearch;
	private HotListPanelAdapter mhotAdapter;
	ListView lvMyStockList;
	public LinkedList<HashMap<String, String>> hostList;
	private boolean isStop;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0x4000:
				new asyncTaskLoadLoadXW().execute();
				if (!isStop)
					sendEmptyMessageDelayed(0x4000, 6 * 1000);
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_hotblock);
		isStop = false;
		hostList = new LinkedList<HashMap<String, String>>();
		lvMyStockList = (ListView) findViewById(R.id.hotlist);
		mhotAdapter = new HotListPanelAdapter(this, hostList);
		lvMyStockList.setAdapter(mhotAdapter);
		lvMyStockList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				gotoZyBack(arg2);
			}
		});
		findLayout();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isStop = false;
		handler.removeMessages(0x4000);
		handler.sendEmptyMessage(0x4000);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isStop = true;
		handler.removeMessages(0x4000);
	}

	public void gotoZyBack(int position) {
		HashMap<String, String> itemstack = hostList.get(position);
		Intent intent = new Intent(this, IncreaseActivity.class);
		intent.putExtra("index", 2);
		intent.putExtra("t", itemstack.get("name"));
		intent.putExtra("code", itemstack.get("code"));
		startActivity(intent);
	}

	private class asyncTaskLoadLoadXW extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			String waiteString = UtilHttp.getHostList();

			LinkedList<HashMap<String, String>> dataList = DataCenter
					.parseHostList(waiteString);
			if (dataList != null && dataList.size() > 0) {
				hostList.clear();
				hostList.addAll(dataList);
				return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result && hostList.size() > 0) {
				mhotAdapter.notifyDataSetChanged();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private void findLayout() {
		back = (LinearLayout) findViewById(R.id.ll_back);
		ivSearch = (ImageView) findViewById(R.id.title_search);
		back.setOnClickListener(this);
		ivSearch.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_back://
			Log.i("HotActivity", "===back");
			this.finish();
			break;
		case R.id.title_search:
			Log.i("HshqActivity", "===title_search");
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		hostList.clear();
		isStop = true;
		handler.removeMessages(0x4000);
	}

}

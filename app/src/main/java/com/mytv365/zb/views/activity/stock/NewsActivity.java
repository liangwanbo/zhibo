package com.mytv365.zb.views.activity.stock;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.thread.GetFileThread;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Parse;
import com.mytv365.zb.utils.UtilHttp;

import java.io.File;
import java.util.HashMap;

public class NewsActivity extends Activity {
	private LinearLayout back;
	private TextView titleView;
	private TextView webView;

	private TextView newtitleView;
	private TextView titletime;
	private TextView tv_title;

	private TextView openFJ;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0x2000:
				String filePath = (String) msg.obj;
				if (filePath == null) {
					MyUtils.getInstance().showToast(getApplicationContext(),
							"未知错误");
					break;
				}
				File f = new File(filePath);
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// 设置intent的Action属性
				intent.setAction(Intent.ACTION_VIEW);
				// 获取文件file的MIME类型
				String type = MyUtils.getInstance().getMIMEType(f);
				// 设置intent的data和Type属性。
				intent.setDataAndType(/* uri */Uri.fromFile(f), type);
				// 跳转
				startActivity(intent);
				break;

			case 0x2001:
				MyUtils.getInstance().showToast(getApplicationContext(),
						getString(R.string.network_exception));
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	void exitActivity() {
		finish();
	}

	void openFJActivity() {
		String path = dataMap.get("path");
		// Intent intent = new Intent(NewsActivity.this, FJActivity.class);
		// intent.putExtra("path", path);
		// startActivity(intent);
		String url = "http://stock.microinvestment.cn/pdf/" + path;
		// Intent intent = new Intent();
		// intent.setAction("android.intent.action.VIEW");
		// intent.setData(Uri.parse(url));
		// startActivity(intent);
		new GetFileThread(this, url, handler, 0x2000, 0x2001).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.news_layout);
		back = (LinearLayout) findViewById(R.id.ll_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exitActivity();
			}
		});
		titleView = (TextView) findViewById(R.id.new_title);
		newtitleView = (TextView) findViewById(R.id.newtitleid);
		titletime = (TextView) findViewById(R.id.titletimeid);
		tv_title = (TextView) findViewById(R.id.tv_title);

		webView = (TextView) findViewById(R.id.contextid);

		webView.setMovementMethod(ScrollingMovementMethod.getInstance());

		openFJ = (TextView) findViewById(R.id.openpdfid);
		openFJ.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openFJActivity();
			}
		});

		currentIndex = this.getIntent().getIntExtra("index", 0);
		code = this.getIntent().getStringExtra("code");
		if (currentIndex == 0 || code == null || "".equals(code)) {
			finish();
			return;
		}

		if (currentIndex == R.id.tv_news) {
			// news
			// titleView.setText(R.string.news);
			new asyncTaskLoadNewContent().execute( code);
			titleView.setText("新闻");

		} else if (currentIndex == R.id.tv_notice) {
			// notice
			// titleView.setText(R.string.notice);
			new asyncTaskLoadNewContent().execute( code);
			titleView.setText("公告");
		}

	}

	private int currentIndex = 0;
	private String code = null;
	private HashMap<String, String> dataMap;

	private class asyncTaskLoadNewContent extends
			AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stubhttp:
			//http://gphq.mytv365.com/Notice/002670/0000000000000gqzbx.html
			//183.129.206.37:8080/Stock_WebService/stockNowInfo/getStockNewsInfoById?id=0000000000000g7yc9
		    //183.129.206.37:8080/Stock_WebService/stockNowInfo/getStockNoticeInfoById?id=0000000000000g7zdj
			String waiteStringNews = UtilHttp.getNetString(String.format(UtilHttp.URL_NEWS_ITEM+"id=%s",params[0]));
			String waiteStringInfo = UtilHttp.getNetString(String.format(UtilHttp.URL_INFO_ITEM+"id=%s",params[0]));
			if (currentIndex == R.id.tv_news) {
				dataMap = DataCenter.parseNewsContent(waiteStringNews);
			} else if (currentIndex == R.id.tv_notice) {
				dataMap = DataCenter.parseNoticeContent(waiteStringInfo);
			}
			if (dataMap != null && dataMap.size() > 0) {

				return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result && dataMap != null && dataMap.size() > 0) {
				newtitleView.setText(dataMap.get("title"));
				String srcs = dataMap.get("src");
				titletime.setText(Parse.getInstance().isNull(
						dataMap.get("date")));
				tv_title.setText(srcs == null ? "" : srcs);
				String str = dataMap.get("content");
				if (str == null)
					str = "";
				webView.setText(str);
				if (currentIndex == R.id.tv_notice) {

					String path = dataMap.get("path");
					if (path != null && path.length() > 0) {
						//
						openFJ.setVisibility(View.VISIBLE);
					}

				}

			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (dataMap != null) {
			dataMap.clear();
			dataMap = null;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

}

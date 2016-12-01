package com.mytv365.zb.views.activity.stock;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytv365.zb.R;

public class FJActivity extends Activity {

	WebView fjWebView;
	private LinearLayout back;
	private TextView titleView;

	void exitActivity() {
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_fj);
		back = (LinearLayout) findViewById(R.id.ll_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exitActivity();
			}
		});
		titleView = (TextView) findViewById(R.id.fj_title);
		fjWebView = (WebView) findViewById(R.id.fjwebid);
		String urls = this.getIntent().getStringExtra("path");
		if (urls == null || "".equals(urls)) {
			finish();
			return;
		}
		fjWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		WebSettings webSettings = fjWebView.getSettings();
		webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);

		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		String url = "http://stock.microinvestment.cn/pdf/" + urls;
		fjWebView.loadUrl(url);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		fjWebView.destroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		fjWebView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fjWebView.onResume();
	}

}

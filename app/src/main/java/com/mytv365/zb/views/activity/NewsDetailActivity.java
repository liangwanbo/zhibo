package com.mytv365.zb.views.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mytv365.zb.R;
import com.mytv365.zb.utils.BaseActivity;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description:
 */

public class NewsDetailActivity extends BaseActivity {
    private ProgressBar progressBar;
    private WebView webView;
    private String url;
    private String title;


    @Override
    public int bindLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initParms(Bundle parms) {
        url = parms.getString("url");
        title = parms.getString("title");
//        url = "http://192.168.0.47/staticHtml/96.html";
    }

    @Override
    public void initView(View view) {
        initTitle(title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.webView);
    }

    /***
     * 初始化标题
     *
     * @param title
     */
    private void initTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void doBusiness(Context mContext) {
        if (webView == null) {
            throw new NullPointerException("must init WebView before invoking initWebView.");
        }
        //关闭JS
        webView.getSettings().setJavaScriptEnabled(true);
        //设置是否支持变焦
        webView.getSettings().setSupportZoom(true);
        //启用缓存
        webView.getSettings().setAppCacheEnabled(true);
        //适配屏幕大小
        webView.getSettings().setUseWideViewPort(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.setWebChromeClient(new WebChromeClient() {
                                       public void onProgressChanged(WebView view, int progress) {
                                           if (progress >= 100) {
                                               progressBar.setVisibility(View.GONE);
                                           } else {
                                               if (progressBar.getVisibility() == View.GONE) {
                                                   progressBar.setVisibility(View.VISIBLE);
                                               }
                                               progressBar.setProgress(progress);
                                           }
                                           super.onProgressChanged(view, progress);
                                       }
                                   }
        );
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.loadUrl(url);
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        webView.stopLoading();
        super.onDestroy();
    }
}

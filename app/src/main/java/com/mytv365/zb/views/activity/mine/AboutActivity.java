package com.mytv365.zb.views.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mytv365.zb.R;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.utils.BaseActivity;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/30
 * Description:
 */

public class AboutActivity extends BaseActivity   {
//    private TextView tv_app_version;
//    private TextView tv_service_call;
    private WebView webView;

    @Override
    public int bindLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        initTitle("关于我们");
        webView= (WebView) findViewById(R.id.mywebView);
//        tv_app_version = (TextView) findViewById(tv_app_version);
//        tv_service_call = (TextView) findViewById(tv_service_call);
    }

    @Override
    public void doBusiness(Context mContext) {
//        String versionName = AppUtils.getVersionName(mContext);
//        tv_app_version.setText("炎黄大擂台v" + versionName);
//        SpannableString spanString = new SpannableString("客服电话：400-966-9898");
//        spanString.setSpan(new UnderlineSpan(), 5, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        spanString.setSpan(new URLSpan("tel:4009669898"), 5, spanString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        tv_service_call.setMovementMethod(new LinkMovementMethod());
//        tv_service_call.setText(spanString);
//
        WebSettings settings= webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if( url.startsWith("http:") || url.startsWith("https:") ) {
                    return false;
                }


                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return  true;
            }

        });



//
//
//
//        webView.setWebChromeClient(new WebChromeClient(){
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//            }
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
//            }
//
//
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
//                builder.setMessage(message)
//                        .setNeutralButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                arg0.dismiss();
//                            }
//                        }).show();
//                result.cancel();
//
//                return true;
//
////                return super.onJsAlert(view, url, message, result);
//            }
//
//
//            @Override
//            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
//                Toast.makeText(AboutActivity.this, "onJsConfirm", Toast.LENGTH_SHORT).show();
//
//                return true;
////                return super.onJsConfirm(view, url, message, result);

//            }

//        });


//        webView.addJavascriptInterface();
        webView.loadUrl(HttpUrl.PersontalAboutWeUrl);
//        webView.setDownloadListener(new MyWebViewDowloadListenter());


    }


//    public class JavaScriptInterface {
//        Context mContext;
//
//        JavaScriptInterface(Context c) {
//            mContext = c;
//        }
//
//        public void showToast(String webMessage){
//            final String msgeToast = webMessage;
//            myHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    // This gets executed on the UI thread so it can safely modify Views
//                    myTextView.setText(msgeToast);
//                }
//            });
//
//            Toast.makeText(mContext, webMessage, Toast.LENGTH_SHORT).show();
//        }
//    }







    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //改写物理返回键的逻辑
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                finish();
//                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }




    /***
     * 初始化标题
     * @param title
     */
    private void initTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }



    private  class  MyWebViewDowloadListenter implements DownloadListener{

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Toast.makeText(AboutActivity.this, " 打电话", Toast.LENGTH_SHORT).show();
            Uri uri=Uri.parse(url);
            startActivity(new Intent(Intent.ACTION_VIEW,uri));
        }
    }

}

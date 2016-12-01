package com.mytv365.zb.presenters;

import com.google.gson.Gson;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description:
 */

public class BasePresenter extends Presenter {
    private volatile static Gson mGson;

    public static Gson getGsonClient() {
        if (mGson == null) {
            synchronized (Gson.class) {
                if (mGson == null) {
                    mGson = new Gson();
                }
            }
        }
        return mGson;
    }

    protected String parseGetUrl(String url, HashMap<String, String> params) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : params.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            return String.format("%s?%s", url, tempParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDestory() {

    }
}

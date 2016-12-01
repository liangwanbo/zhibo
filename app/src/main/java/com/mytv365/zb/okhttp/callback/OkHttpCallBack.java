package com.mytv365.zb.okhttp.callback;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求回调
 * Created by Sunshine on 2016/5/12.
 */
public abstract class OkHttpCallBack<T> implements Callback {
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private OkBaseParser<T> mParser;

    public OkHttpCallBack(OkBaseParser<T> mParser) {
        if (mParser == null) {
            throw new IllegalArgumentException("Parser can't be null");
        }
        this.mParser = mParser;
    }

    @Override
    public void onFailure(Call call, IOException e) {
       onFailure(call.request(),e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        onResponse(response);
    }

    public void onFailure(Request request, IOException e) {

    }

    public void onResponse(Response response) throws IOException {
        final int code = mParser.getCode();
        try {
            final T t = mParser.parseResponse(response);
            if (response.isSuccessful() && t != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(code, t);
                    }
                });
            } else {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(new Exception(Integer.toString(code)));
                    }
                });
            }
        } catch (final Exception e) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(e);
                }
            });
        }
    }

    public abstract void onSuccess(int code, T t);
    public abstract void onFailure(Throwable e);
    public void onStart() {
    }
}

package com.mytv365.zb.okhttp.callback;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;

import okhttp3.Response;

/**
 * 解析JSON，转换为实体类
 * Created by Sunshine on 15/11/22.
 */
public abstract class OkJsonParser<T> extends OkBaseJsonParser<T> {

    protected Gson mGson;

    public OkJsonParser() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .excludeFieldsWithModifiers(
                            Modifier.FINAL,
                            Modifier.TRANSIENT,
                            Modifier.STATIC);
            mGson = gsonBuilder.create();
        } else {
            mGson = new Gson();
        }
    }

    @Override
    public T parse(Response response) throws IOException {
        String body = response.body().string();
        return mGson.fromJson(body, mType);
    }
}
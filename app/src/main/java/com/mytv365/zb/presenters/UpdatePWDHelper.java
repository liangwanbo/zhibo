package com.mytv365.zb.presenters;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.UpdatePWDBean;
import com.mytv365.zb.presenters.viewinface.CommonView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description:
 */

public class UpdatePWDHelper extends BasePresenter {
    private Context mContext;
    private CommonView mChangePWDView;

    public UpdatePWDHelper(Context context, CommonView changePWDView) {
        mContext = context;
        mChangePWDView = changePWDView;
    }

    public void updatePWD(String toKen, String oldPassword, String password) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("toKen", toKen);
        paramsMap.put("oldPassword", oldPassword);
        paramsMap.put("password", password);

        OkGo.post(HttpUrl.changePWD)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("oldPassword", oldPassword)
                .params("password", password)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        Log.i("pass", "onResponse: 请求放回" + response);
                        try {
                            JSONObject obj = new JSONObject(s);
                            String resultCode=obj.getString("resultCode");
                            String  resultMessage=obj.getString("resultMessage");
                            UpdatePWDBean updatePWDBean=new UpdatePWDBean();

//                    if (updatePWDBean == null) {
//                        mChangePWDView.showMessage("修改失败，请重试...");
//                        return;
//                    }

                            if(Integer.valueOf(resultCode) == 100) {
                                mChangePWDView.updateView(updatePWDBean);
                            } else {
                                mChangePWDView.showMessage(resultMessage);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mChangePWDView.showMessage("输入旧密码错误");
                    }
                });
   /*     OkHttpClientManager.getAsyn(parseGetUrl(HttpUrl.changePWD, paramsMap), new OkHttpClientManager.StringCallback() {

            @Override
            public void onFailure(Request request, IOException e) {
                mChangePWDView.showMessage("输入旧密码错误");
            }

            @Override
            public void onResponse(String response) {
                Log.i("pass", "onResponse: 请求放回" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String resultCode=obj.getString("resultCode");
                    String  resultMessage=obj.getString("resultMessage");
                    UpdatePWDBean updatePWDBean=new UpdatePWDBean();

//                    if (updatePWDBean == null) {
//                        mChangePWDView.showMessage("修改失败，请重试...");
//                        return;
//                    }

                    if(Integer.valueOf(resultCode) == 100) {
                        mChangePWDView.updateView(updatePWDBean);
                    } else {
                        mChangePWDView.showMessage(resultMessage);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    public void onDestory() {
        mContext = null;
        mChangePWDView = null;
    }
}

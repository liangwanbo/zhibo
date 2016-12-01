package com.mytv365.zb.presenters;

import android.content.Context;

import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.FeedbackBean;
import com.mytv365.zb.okhttp.http.OkHttpClientManager;
import com.mytv365.zb.presenters.viewinface.CommonView;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description:
 */

public class FeedbackHelper extends BasePresenter {
    private Context mContext;
    private CommonView mFeedbackView;

    public FeedbackHelper(Context context, CommonView feedbackView) {
        mContext = context;
        mFeedbackView = feedbackView;
    }

    public void feedback(String content, String contactway) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("content", content);
        paramsMap.put("contactway", contactway);
        OkHttpClientManager.getAsyn(parseGetUrl(HttpUrl.feedback, paramsMap), new OkHttpClientManager.StringCallback() {

            @Override
            public void onFailure(Request request, IOException e) {
                mFeedbackView.showMessage(e.toString());
            }

            @Override
            public void onResponse(String response) {
                FeedbackBean feedbackBean = getGsonClient().fromJson(response, FeedbackBean.class);
                if (feedbackBean == null) {
                    mFeedbackView.showMessage("提交失败，请稍后重试...");
                    return;
                }
                if (feedbackBean.getResultCode() == 100) {
                    mFeedbackView.updateView(feedbackBean);
                } else {
                    mFeedbackView.showMessage(feedbackBean.getResultMessage());
                }
            }
        });
    }

    @Override
    public void onDestory() {
        mContext = null;
        mFeedbackView = null;
    }
}

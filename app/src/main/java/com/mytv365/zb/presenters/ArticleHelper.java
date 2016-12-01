package com.mytv365.zb.presenters;

import android.content.Context;

import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.ArticleDetailBean;
import com.mytv365.zb.model.ArticleListBean;
import com.mytv365.zb.model.BaseBean;
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

public class ArticleHelper extends BasePresenter {
    private Context mContext;
    private CommonView mArticleView;

    public ArticleHelper(Context context, CommonView articleView) {
        mContext = context;
        mArticleView = articleView;
    }

    public void getArticleList(int articleTypeId, int currentPage, int pageSize) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("articleTypeId", articleTypeId + "");
        paramsMap.put("currentPage", currentPage + "");
        paramsMap.put("pageSize", pageSize + "");
        OkHttpClientManager.getAsyn(parseGetUrl(HttpUrl.articleList, paramsMap), new OkHttpClientManager.StringCallback() {

            @Override
            public void onFailure(Request request, IOException e) {
                mArticleView.showMessage(e.toString());
            }

            @Override
            public void onResponse(String response) {
                ArticleListBean articleListBean = getGsonClient().fromJson(response, ArticleListBean.class);
                if (articleListBean != null && articleListBean.getResultCode() == 100) {
                    mArticleView.updateView(articleListBean);
                } else {
                    if (articleListBean != null) {
                        mArticleView.showMessage(articleListBean.getResultMessage());
                    } else {
                        mArticleView.showMessage("结果为空");
                    }
                }
            }
        });
    }

    public void getArticleDetail(int articleId, int forReadPageSize, String toKen, int commentPageSize, int commentCurrentPage) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("articleId", articleId + "");
        paramsMap.put("forReadPageSize", forReadPageSize + "");
        paramsMap.put("toKen", toKen);
        paramsMap.put("commentPageSize", commentPageSize + "");
        paramsMap.put("commentCurrentPage", commentCurrentPage + "");
        OkHttpClientManager.getAsyn(parseGetUrl(HttpUrl.articleDetail, paramsMap), new OkHttpClientManager.StringCallback() {

            @Override
            public void onFailure(Request request, IOException e) {
                mArticleView.showMessage(e.toString());
            }

            @Override
            public void onResponse(String response) {
                ArticleDetailBean articleDetailBean = getGsonClient().fromJson(response, ArticleDetailBean.class);
                if (articleDetailBean != null && articleDetailBean.getResultCode() == 100) {
                    mArticleView.updateView(articleDetailBean);
                } else {
                    if (articleDetailBean != null) {
                        mArticleView.showMessage(articleDetailBean.getResultMessage());
                    } else {
                        mArticleView.showMessage("结果为空");
                    }
                }
            }
        });
    }

    public void articleComment(int articleId, String content, int pId, String toKen) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("articleId", articleId + "");
        paramsMap.put("content", content);
        paramsMap.put("toKen", toKen);
        paramsMap.put("pId", pId + "");
        OkHttpClientManager.getAsyn(parseGetUrl(HttpUrl.articleComment, paramsMap), new OkHttpClientManager.StringCallback() {

            @Override
            public void onFailure(Request request, IOException e) {
                mArticleView.showMessage(e.toString());
            }

            @Override
            public void onResponse(String response) {
                BaseBean baseBean = getGsonClient().fromJson(response, BaseBean.class);
                if (baseBean != null && baseBean.getResultCode() == 100) {
                    mArticleView.updateView(baseBean);
                } else {
                    if (baseBean != null) {
                        mArticleView.showMessage(baseBean.getResultMessage());
                    } else {
                        mArticleView.showMessage("结果为空");
                    }
                }
            }
        });
    }

    @Override
    public void onDestory() {
        mContext = null;
        mArticleView = null;
    }
}

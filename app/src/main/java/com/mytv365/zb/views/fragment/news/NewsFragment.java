package com.mytv365.zb.views.fragment.news;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.tools.ToolToast;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshListView;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.listview.NewsAdapter;
import com.mytv365.zb.model.ArticleListBean;
import com.mytv365.zb.presenters.ArticleHelper;
import com.mytv365.zb.presenters.viewinface.CommonView;

import java.util.List;

/**
 * 新闻
 * Created by zhangguohao on 16/9/12.
 */
public class NewsFragment extends BaseFragment implements CommonView {
    /*标题*/
    private TextView title;
    private ArticleHelper articleHelper;
    private static final int pageSize = 20;
    private int page = 1;
    private boolean hasMore = true;


    /**
     * 上拉刷新下拉加载
     */
    private ProgressActivity progressActivity;
    private ListView listView;
    private PullToRefreshListView mPullListView;

    private BaseMAdapter<ArticleListBean.Article> newsAdapter;

    @Override
    public int bindLayout() {
        return R.layout.news_fragment;

    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        articleHelper = new ArticleHelper(getActivity(), this);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("研究院");
        initListView();
    }

    /***
     * 初始化ListView
     */
    private void initListView() {

        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        progressActivity.showContent();
        mPullListView = (PullToRefreshListView) findViewById(R.id.scrollView);
        newsAdapter = new BasicDataAdapter<ArticleListBean.Article>(new NewsAdapter(mContext));
        listView = mPullListView.getRefreshableView();
        listView.setVerticalScrollBarEnabled(false);
        listView.setAdapter(newsAdapter);
        mPullListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        page = 1;
                        articleHelper.getArticleList(1, page, pageSize);
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                    }
                });
        mPullListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (hasMore) {
                    articleHelper.getArticleList(1, page, pageSize);
                } else {
                    ToolToast.showShort("没有更多了");
                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        articleHelper.getArticleList(1, page, pageSize);
        mPullListView.onRefreshComplete();
        mPullListView.setShowViewWhileRefreshing(true);
        mPullListView.setCurrentModeRefresh();
        mPullListView.setRefreshing(true);
    }

    @Override
    public void showMessage(String msg) {
        ToolToast.showShort(msg);
    }

    @Override
    public void updateView(Object result) {
        if (result instanceof ArticleListBean) {
            if (page == 1) {
                newsAdapter.clear();
            }
            List<ArticleListBean.Article> resultData = ((ArticleListBean) result).getResultData();
            if (resultData.size() < pageSize) {
                hasMore = false;
            } else {
                hasMore = true;
                page++;
            }

            newsAdapter.addItem(resultData);
            newsAdapter.notifyDataSetChanged();
            mPullListView.onRefreshComplete();
        }
    }
}

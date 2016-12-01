package com.mytv365.zb.views.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshListView;
import com.mytv365.zb.R;
import com.mytv365.zb.model.Fans;

/**
 * 问答
 * Created by zhangguohao on 16/9/11.
 */
public class QAFragment extends BaseFragment {
    /*上拉刷新下拉加载*/
    private ProgressActivity progressActivity;
    private ListView listView;
    private PullToRefreshListView mPullListView;

    private BaseMAdapter<Fans> fansAdapter;
    @Override
    public int bindLayout() {
        return R.layout.layout_listview;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        initListView();
    }

    @Override
    public void doBusiness(Context mContext) {
//        fansAdapter = new BasicDataAdapter<Fans>(new FansAdapter(mContext));
        getData();
    }

    /***
     * 初始化ListView
     */
    private void initListView() {
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        progressActivity.showContent();
        mPullListView = (PullToRefreshListView) findViewById(R.id.scrollView);
        listView = mPullListView.getRefreshableView();
        listView.setVerticalScrollBarEnabled(false);
        mPullListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                    }
                });
    }
    /***
     * 初始化数据
     */
    private void getData(){
        for (int i=0;i<30;i++){
            Fans fans=new Fans();
            fansAdapter.addItem(fans);
        }
        listView.setAdapter(fansAdapter);
        Tool.setListViewHeightBasedOnChildren(listView);
    }
}

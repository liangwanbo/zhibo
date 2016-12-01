package com.mytv365.zb.views.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.listview.ListView;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshScrollView;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.gridview.ImageTextAdapter;
import com.mytv365.zb.adapters.listview.DynamicAdapter;
import com.mytv365.zb.adapters.listview.StockAdapter;
import com.mytv365.zb.model.Dynamic;
import com.mytv365.zb.model.ImageText;
import com.mytv365.zb.model.Stock;

/**
 * Created by zhangguohao on 16/9/8.
 */
public class MainFragment01 extends BaseFragment {
    /*标题*/
    private TextView title;

    private ProgressActivity progressActivity;
    /*滚动条*/
    private ScrollView scrollView;
    /*下拉刷新*/
    private PullToRefreshScrollView mPullScrollView;
    /*内容界面*/
    private LinearLayout parent;

    /*功能*/
    private GridView gridView;
    private BaseMAdapter<ImageText> imageTextAdapter;

    /*推荐*/
    private ListView listViewTJ;
    private BaseMAdapter<Stock> adapterTJ;
    /*动态*/
    private ListView listViewDT;
    private BaseMAdapter<Dynamic> adapterDT;

    @Override
    public int bindLayout() {
        return R.layout.layout_scrollview;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("擂台");
        initScrollView();
        gridView = (GridView) findViewById(R.id.gridview);
        listViewTJ = (ListView) findViewById(R.id.listview_tj);
        listViewDT = (ListView) findViewById(R.id.listview_dt);
    }

    @Override
    public void doBusiness(Context mContext) {
        imageTextAdapter = new BasicDataAdapter<ImageText>(new ImageTextAdapter(mContext));
        adapterTJ = new BasicDataAdapter<Stock>(new StockAdapter(mContext));
        adapterDT = new BasicDataAdapter<Dynamic>(new DynamicAdapter(mContext));
        getDataFunction();
        getDataListViewTJ();
        getDataListViewDT();

    }

    /**
     * 加载数据
     */
    private void getDataFunction() {
//        String[] names = {"擂台排行", "粉丝排行", "已完成", "预售中", "问顾", "最新动态"};
//        int[] images = {R.mipmap.ioc_dlt, R.mipmap.ioc_lt, R.mipmap.ioc_wc, R.mipmap.ioc_ys, R.mipmap.ioc_wg, R.mipmap.ioc_ndt};
        String[] names = {"粉丝排行", "问顾"};
        int[] images = {R.mipmap.ioc_lt, R.mipmap.ioc_wg};
        for (int i = 0; i < names.length; i++) {
            ImageText imageText = new ImageText(images[i], names[i]);
            imageTextAdapter.addItem(imageText);
        }
        gridView.setAdapter(imageTextAdapter);
//        Tool.setGridViewHeightBasedOnChildren(gridView,0);
    }

    /****
     * 推荐数据
     */
    private void getDataListViewTJ() {
//        for (int i = 0; i < 5; i++) {
//            Stock stock = new Stock();
//            adapterTJ.addItem(stock);
//        }
//        listViewTJ.setAdapter(adapterTJ);
//        Tool.setListViewHeightBasedOnChildren(listViewTJ);
    }

    /****
     * 最新动态
     */
    private void getDataListViewDT() {
        for (int i = 0; i < 5; i++) {
            Dynamic dynamic = new Dynamic();
            adapterDT.addItem(dynamic);
        }
        listViewDT.setAdapter(adapterDT);
//        Tool.setListViewHeightBasedOnChildren(listViewDT);
    }

    /****
     * 动态数据
     */
    private void getDatalistViewDT() {

    }

    /***
     * 初始化下啦刷新
     */
    private void initScrollView() {
        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.main_fragment01, null);
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        progressActivity.showContent();
        mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
        mPullScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ScrollView> refreshView) {
                    }


                });
        /***
         * 状态发生改变时候调用
         */
        mPullScrollView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
            }
        });

        scrollView = mPullScrollView.getRefreshableView();
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(parent);
    }
}

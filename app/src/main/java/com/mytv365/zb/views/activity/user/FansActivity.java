package com.mytv365.zb.views.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.view.ProgressActivity;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.listview.FansAdapter;
import com.mytv365.zb.model.Concern;
import com.mytv365.zb.presenters.AudienceHelper;
import com.mytv365.zb.presenters.viewinface.Audience;
import com.mytv365.zb.presenters.viewinface.ConcernView;
import com.tencent.TIMUserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的粉丝
 * Created by zhangguohao on 16/9/18.
 */
public class FansActivity extends BaseActivity implements Audience,ConcernView {
    /*上拉刷新下拉加载*/
    private ProgressActivity progressActivity;
    private ListView listView;
    private SwipeRefreshLayout mPullListView;

    private BaseMAdapter<Concern> fansAdapter;
    private AudienceHelper audienceHelper;
    public static final String AUDIENCE="audience";
    public static final String FANCE="fance";
    private String type;

    private ImageView persontal_nodata_img_replay;
    private TextView persontal_nodata_text_replay;


    @Override
    public int bindLayout() {
        return R.layout.concern_listvie_item_layout;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        listView= (ListView) findViewById(R.id.concern_listview_item);
        mPullListView = (SwipeRefreshLayout) findViewById(R.id.scrollView);
        mPullListView.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        persontal_nodata_img_replay= (ImageView) findViewById(R.id.persontal_nodata_img_replay);
        persontal_nodata_text_replay= (TextView) findViewById(R.id.persontal_nodata_text_replay);


    }

    @Override
    public void doBusiness(Context mContext) {
        type=getIntent().getStringExtra("type");
        if (type.equals(AUDIENCE)){
            initTitle("关注");
            fansAdapter = new BasicDataAdapter<Concern>(new FansAdapter(mContext,AUDIENCE,this));
        }else if (type.equals(FANCE)){
            initTitle("粉丝");
            fansAdapter = new BasicDataAdapter<Concern>(new FansAdapter(mContext,FANCE,this));
        }

        initListView();
        audienceHelper=new AudienceHelper(this,this);
        getData();

    }

    /***
     * 初始化ListView
     */
    private void initListView() {
        mPullListView.setRefreshing(false);
        mPullListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullListView.setRefreshing(false);
            }
        });
    }

    /***
     * 初始化标题
     * @param title
     */
    private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    /***
     * 初始化数据
     */
    private void getData(){
        if (type.equals(AUDIENCE)){
            audienceHelper.getConcernlist(1+"",10+"");
        }else if (type.equals(FANCE)){
            audienceHelper.getByConcernlist(1+"",10+"");
        }
    }

    @Override
    public void addaudience(int type) {
    }

    @Override
    public void cancelaudien(int type) {
    }

    @Override
    public void audienguanx(int type) {
    }


    List<Concern> concerns=new ArrayList<>();
    @Override
    public void getConcernList(List<TIMUserProfile> concernList) {
        Log.i("fans", "getConcernList: 查询关注列表"+concernList.toString());

        if (concernList.size()==0){
            persontal_nodata_img_replay.setVisibility(View.VISIBLE);
            persontal_nodata_text_replay.setVisibility(View.VISIBLE);
        }



        for (int i = 0; i < concernList.size(); i++) {
            TIMUserProfile profile=concernList.get(i);
            Concern concern=new Concern();
            concern.setFollower(profile.getIdentifier());
            concern.setHeadImages(profile.getFaceUrl());
            concern.setNickName(profile.getNickName());
            concern.setSign(profile.getSelfSignature());
//            fansAdapter.addItem(concern);
            concerns.add(concern);
        }

        fansAdapter.addItem(concerns);
        listView.setAdapter(fansAdapter);
    }


    @Override
    public void getByConernList(List<TIMUserProfile> concernList) {
        if (concernList.size()==0){
            persontal_nodata_img_replay.setVisibility(View.VISIBLE);
            persontal_nodata_text_replay.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < concernList.size(); i++) {
            TIMUserProfile profile=concernList.get(i);
            Concern concern=new Concern();
            concern.setId(Long.valueOf(profile.getIdentifier()));
            concern.setHeadImages(profile.getFaceUrl());
            concern.setNickName(profile.getNickName());
            concern.setSign(profile.getSelfSignature());
            fansAdapter.addItem(concern);
        }
        listView.setAdapter(fansAdapter);
    }


    @Override
    public int getPosition(int position) {
        fansAdapter.removeItem(position);
        fansAdapter.notifyDataSetChanged();
        return 0;
    }
}

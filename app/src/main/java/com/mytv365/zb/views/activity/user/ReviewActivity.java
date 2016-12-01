package com.mytv365.zb.views.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.tools.ToolLog;
import com.fhrj.library.tools.ToolToast;
import com.mylhyl.crlayout.SwipeRefreshGridView;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.gridview.BaseReplayAdapter;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.WonderfulVideo;
import com.mytv365.zb.okhttp.http.OkHttpClientManager;
import com.mytv365.zb.views.activity.RememberVideoActivity;
import com.mytv365.zb.widget.BackDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

/**
 * 精彩回顾
 * Created by 郑士成 on 16/10/20.
 */
public class ReviewActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    /*全选*/
    private TextView choice_all;
    /*删除*/
    public TextView choice_delete, persontal_nodata_text_replay;

    private boolean isReview;
    private ArrayList<Boolean> isChoice = new ArrayList<>();
    private SwipeRefreshGridView swipeRefresh;
    /*编辑*/
    private Button bianji;
    /*全选或删除布局*/
    private LinearLayout bottomchoice;
    private int listPosition;
    /*精彩回放数据源*/
    private ArrayList<WonderfulVideo> lists = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();
    private BaseReplayAdapter replayAdapter;
    private String Videoid;
    private int pager = 1;
    private ImageView persontal_nodata_img_replay;
//    private boolean isfirst=false;
    private boolean refesh=false;


    @Override
    public int bindLayout() {
        return R.layout.activity_persontal_user_replay1;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        Videoid = getIntent().getStringExtra("userId");

        initTitle("精彩回放");
        bianji = (Button) findViewById(R.id.btn_done);
        bianji.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) bianji.getLayoutParams();
        layoutParams.setMargins(0, 0, 10, 0);
        bianji.setLayoutParams(layoutParams);
        bianji.setText("编辑");
        if (!Constant.isFor.equals("")) {
            bianji.setVisibility(View.INVISIBLE);
        }

        swipeRefresh = (SwipeRefreshGridView) findViewById(R.id.swipeRefresh);
//        swipeRefresh.setLoadLayoutResource(R.layout.swipe_refresh_footer);
        swipeRefresh.getSwipeRefreshLayout().setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        choice_all = (TextView) findViewById(R.id.replay_choice_all);
        choice_delete = (TextView) findViewById(R.id.replay_choice_delete);
        bottomchoice = (LinearLayout) findViewById(R.id.replay_choice_layout);

        initData();
        initPicture();
        Log.e("数据", lists.toString());
        replayAdapter = new BaseReplayAdapter(ReviewActivity.this, lists, isChoice);
        // swipeRefresh.setAdapter(replayAdapter);
        initListener();
        swipeRefresh.setOnRefreshListener(this);
        //swipeRefresh.setAdapter(replayAdapter);
        swipeRefresh.setAdapter(replayAdapter);
    }

    /**
     * 标题栏
     *
     * @param title
     */
    private void initTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();

    }

    /**
     * 事件监听
     */
    private void initListener() {
        persontal_nodata_text_replay = (TextView) findViewById(R.id.persontal_nodata_text_replay);
        persontal_nodata_img_replay = (ImageView) findViewById(R.id.persontal_nodata_img_replay);


        bottomchoice.setOnClickListener(this);
        bianji.setOnClickListener(this);
        choice_all.setOnClickListener(this);
        choice_delete.setOnClickListener(this);

        swipeRefresh.setOnItemClickListener(this);
        //swipeRefresh.setOnListLoadListener(this);
        swipeRefresh.setOnRefreshListener(this);



    }

    @Override
    public void doBusiness(Context mContext) {


    }

    private void initPicture() {

    }

    /**
     * 播放视屏
     */
    private void initPlayVideo(int position) {
        Intent intent = new Intent(ReviewActivity.this, RememberVideoActivity.class);
        intent.putExtra("urls", lists.get(position).getPayurl());
        intent.putExtra("name", lists.get(position).getFileName());
        intent.putExtra("id", lists.get(position).getId());
        startActivity(intent);
    }

    /**
     * 删除字体颜色变化
     */
    private void initDeleteColor() {
        if (isChoice.contains(true)) {
            Resources resource = (Resources) getBaseContext().getResources();
            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.orange);
            choice_delete.setTextColor(csl);
        } else {
            Resources resource = (Resources) getBaseContext().getResources();
            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.gray);
            choice_delete.setTextColor(csl);
        }
    }

    /**
     * 精彩回顾数据
     */
    private void initData() {

        if(refesh==true){
            lists.clear();
            isChoice.clear();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("currentPage", "1");
        map.put("pageSize", 1000000+"");
        map.put("userId", Videoid);
        OkHttpClientManager.postAsyn(HttpUrl.jingcaihuigu, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("精彩回放", e.toString());
            }

            @Override

            public void onResponse(String response) {
                Log.e("精彩回放", response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("100")) {
                        JSONArray array = jsonObject.getJSONArray("resultData");
                        Log.i("tag", array.toString() + "数据");
                        if (array.length()==0) {
                            Log.i("tag", array.toString() + "数据dsaffg");
                            persontal_nodata_text_replay.setVisibility(View.VISIBLE);
                            persontal_nodata_img_replay.setVisibility(View.VISIBLE);
                            return;
                        }

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String videoName = object.getString("videoName");
                            String id = object.getString("id");
                            String PLAYURL = object.getString("PLAYURL");
                            String image = object.getString("image");
                            WonderfulVideo video = new WonderfulVideo();
                            video.setId(id);
                            video.setCoverImage(image);
                            video.setFileName(videoName);
                            video.setPayurl(PLAYURL);
                            lists.add(video);
                            isChoice.add(i, false);
                        }

                          //replayAdapter = new BaseReplayAdapter(ReviewActivity.this, lists, isChoice);
                        replayAdapter.notifyDataSetChanged();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, map, "");


    }


    /**
     * 事件监听
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_done:

                if (isReview) {
                    isReview = false;
                    bianji.setText("编辑");
                    bottomchoice.setVisibility(View.INVISIBLE);
                    replayAdapter.isShowChoice = false;
                    replayAdapter.notifyDataSetChanged();

                } else {
                    if (lists.size() != 0) {
                        Resources resource = (Resources) getBaseContext().getResources();
                        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.gray);
                        choice_delete.setTextColor(csl);
                        bottomchoice.setVisibility(View.VISIBLE);
                    }
                    bianji.setText("取消");
                    choice_all.setTextColor(Color.BLACK);
                    isReview = true;
                    replayAdapter.isShowChoice = true;
                    replayAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.replay_choice_all:
                replayAdapter.chiceAll();
                initDeleteColor();
                break;
            case R.id.replay_choice_delete:

                if (isChoice.contains(true)) {
                    new BackDialog(this).builder().setTitle("你确定删除所选择的视频").setPositiveButton("确定删除", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DeleteChoiceData();
                            Resources resource = (Resources) getBaseContext().getResources();
                            ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.gray);
                            choice_delete.setTextColor(csl);
                            ToolToast.buildToast(ReviewActivity.this, "删除成功", Toast.LENGTH_SHORT, "#FF4500").show();
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();

                }

                break;
        }
    }

    /**
     * 删除所选的视屏
     */
    private void DeleteChoiceData() {

        StringBuffer sb = new StringBuffer();
        ids = replayAdapter.Ischiceid();
        if (ids.size() != 0) {
            for (int k = 0; k < ids.size(); k++) {
                sb.append(ids.get(k) + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("ids", sb.toString());
        ToolLog.e("删除的视屏id", sb.toString());
        OkHttpClientManager.postAsyn(HttpUrl.deleteVideo, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {
                ToolLog.e("删除的精彩回顾", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("100")) {
                        for (int i = 0; i < lists.size(); i++) {
                            for (int j = 0; j < ids.size(); j++) {
                                if (ids.get(j).equals(lists.get(i).getId())) {
                                    lists.remove(i);
                                    isChoice.remove(i);
                                }
                            }
                            ToolLog.e("精彩回放删除的状态", isChoice.size() + "");
                            ToolLog.e("删除回放的id", ids.toString());
                        }
                        replayAdapter.notifyDataSetChanged();
                        if (lists.size() == 0) {
                            bottomchoice.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, map, "");
    }

    /**
     * 返回键监听
     */
    @Override
    public void onBackPressed() {

        if (isReview) {
            isReview = false;
            bianji.setText("编辑");
            bottomchoice.setVisibility(View.INVISIBLE);
            replayAdapter.isShowChoice = false;
            replayAdapter.notifyDataSetChanged();

        } else {
            ReviewActivity.this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        lists.clear();
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        --pager;
        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refesh=true;
                initData();
                swipeRefresh.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        listPosition = position;
        if (isReview == false) {
            initPlayVideo(position);
        } else {
            replayAdapter.chiceState(position);
            initDeleteColor();

        }
    }

    /*@Override
    public void onListLoad() {
       // pager++;
        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
               *//* initData();
                swipeRefresh.setLoading(false);
                if (pager == 1) {
                    swipeRefresh.setLoadCompleted(true);
                }*//*
            }
        }, 2000);
    }*/
}

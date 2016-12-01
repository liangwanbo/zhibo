package com.mytv365.zb.views.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolOKHttp;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshListView;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.listview.LiveAdapter;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.CurLiveInfo;
import com.mytv365.zb.model.Live;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.utils.Constants;
import com.mytv365.zb.views.LiveActivity;
import com.mytv365.zb.views.WebPageActivity;
import com.mytv365.zb.views.activity.MyzhuyeActivity;
import com.mytv365.zb.views.activity.user.LoginActivity;
import com.mytv365.zb.views.zhibolive.livebase.PcLiveActivity;
import com.mytv365.zb.widget.LoadMoreListView;
import com.recker.flybanner.FlyBanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangguohao on 16/9/8.
 *
 */
public class MainFragment02 extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreListView.OnLoadMore {

    private View mHeader;
    /*标题*/
    private TextView title;
    /*上拉刷新下拉加载*/
    private ProgressActivity progressActivity;
    private PullToRefreshListView mPullListView;
    private List<Live> mylist = new ArrayList<Live>();
    private BaseMAdapter<Live> liveAdapter;
    private FlyBanner mFlyBanner;
    private ArrayList<String> topImagelist = new ArrayList<>();
    private ArrayList<String> urllink = new ArrayList<>();
    private LoadMoreListView listView;
    private SwipeRefreshLayout refreshlayout;
    private int i=1;
    private boolean zaiing=false;
    private boolean refesh;


    @Override
    public int bindLayout() {
        return R.layout.main_fragment02;
    }

    @Override
    public void initParams(Bundle params){
    }

    @Override
    public void initView(View view) {
        listView=(LoadMoreListView)this.findViewById(R.id.gv);
        refreshlayout = (SwipeRefreshLayout)this.findViewById(R.id.refreshlayout);
        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.zhuye_headview, null);
        mFlyBanner = (FlyBanner) mHeader.findViewById(R.id.banner_1);
        mFlyBanner.setPoinstPosition(Gravity.CENTER);

        //获取直播列表
        motorefesh();
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("直播");



    }

    @Override
    public void doBusiness(Context mContext) {
        getHeadImage();
        IntentLink();
        initListView();
        OnclickListview();


    }

    /***
     * 初始化ListView
     */
    private void initListView() {
        listView.addHeaderView(mHeader);
        listView.setLoadMoreListen(this);
        refreshlayout.setOnRefreshListener(this);
        refreshlayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        liveAdapter = new BasicDataAdapter<Live>(new LiveAdapter(getActivity()));
        //getData();
        /*progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
//        progressActivity.showLoading();

        progressActivity.showContent();
        mPullListView = (PullToRefreshListView) findViewById(R.id.scrollView);
        listView = mPullListView.getRefreshableView();
        listView.addHeaderView(mHeader);
        listView.setVerticalScrollBarEnabled(false);
        mPullListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                        liveAdapter.clear();
                        getData();
                        mPullListView.onRefreshComplete();

                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                        ToolAlert.toastShort("加载更多");
                    }
                });*/
    }


    @Override
    public void onRefresh() {

        listView.setFastScrollAlwaysVisible(false);
//        listView.stopNestedScroll();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refesh=true;
                liveAdapter.clear();
                getData();
                refreshlayout.setRefreshing(false);
//                listView.
//              topImagelist.clear();
//              getHeadImage();
            }
        }, 2000);
    }


    @Override
    public void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(zaiing==false){
                    getData();
                    listView.onLoadComplete();
                }else{
                    listView.onLoadComplete();
//                    Toast.makeText(getActivity(),"已加载全部",Toast.LENGTH_SHORT).show();

                }
            }
        }, 2000);
    }

    public void motorefesh(){
        refreshlayout.post(new Runnable() {
            @Override
            public void run() {
                refreshlayout.setRefreshing(true);
            }
        });
        onRefresh();
    }


    /***
     * 初始化数据
     */
    private void getData() {
        if(refesh=true){
            i=0;
        }else{
            i=++i;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startPage",  i+"");
        map.put("pageNum", "10");
        ToolOKHttp toolOKHttp = new ToolOKHttp();
        toolOKHttp.post(HttpUrl.liveList, map, new ToolOKHttp.HttpCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(String data) {
                try {
                    //ToolAlert.toastShort(data);
                    JSONObject object = new JSONObject(data);
                    if (object.getString("resultCode").equals("100")) {
                        JSONObject resultData = object.getJSONObject("resultData");
                        JSONArray room = resultData.getJSONArray("room");
                        for (int i = 0; i < room.length(); i++) {
                            Live live = new Live();
                            JSONObject jsonObject = room.getJSONObject(i);
                            live.setName(jsonObject.getString("NICKNAME"));
                            live.setIoc(jsonObject.getString("HEADIMAGES"));
                            live.setTitle(jsonObject.getString("ROOMNAME"));
                            live.setState(jsonObject.getString("ROOMSTATE"));
                            live.setRoomid(jsonObject.getString("LIVEURL"));
                            live.setUserid(jsonObject.getString("USERID"));
                            live.setPostionImage(jsonObject.getString("ROLEICON"));
                            live.setPosition(jsonObject.getString("POSITION"));
                            live.setType("");
                            if (jsonObject.getString("ROOMSTATE").equals("1")) {
                                if (jsonObject.has("PLAYADDRESS")) {
                                    live.setPayUrl(jsonObject.getString("PLAYADDRESS"));
                                } else {
                                    live.setPayUrl("");
                                }
                                if (jsonObject.has("ROOMIMAGES")) {
                                    if (!TextUtils.isEmpty(jsonObject.getString("ROOMIMAGES"))) {
                                        live.setRoomIoc(jsonObject.getString("ROOMIMAGES"));
                                    } else {
                                        live.setRoomIoc("");
                                    }
                                } else {
                                    live.setRoomIoc("");
                                }
                                live.setLiveUrl(jsonObject.getString("LIVEURL"));
                            } else if (jsonObject.getString("ROOMSTATE").equals("0")) {
                                live.setPayUrl("");
                                live.setRoomIoc("");
                                live.setRoomIoc("");
                            } else if (jsonObject.getString("ROOMSTATE").equals("2")) {
                                live.setPayUrl("");
                                if (jsonObject.has("ROOMIMAGES")) {
                                    if (jsonObject.has("PHONEIMAGES")) {
                                        if (TextUtils.isEmpty(jsonObject.getString("PHONEIMAGES"))) {
                                            live.setRoomIoc("");
                                        } else {
                                            live.setRoomIoc(jsonObject.getString("PHONEIMAGES"));
                                        }
                                    }
                                } else {
                                    live.setRoomIoc(jsonObject.getString("PHONEIMAGES"));
                                }
                            }
                            liveAdapter.addItem(live);
                        }
                        if(room.length()<10&&room!=null){
                            zaiing=true;
//                            ToolAlert.toastShort("已加载全部");
                        }else if(room.length()==10){
                            zaiing=false;
                        }else if(TextUtils.isEmpty(room.toString())){
                            zaiing=true;
//                            ToolAlert.toastShort("已加载全部");
                        }
                        liveAdapter.notifyDataSetChanged();
                        listView.setAdapter(liveAdapter);
                    } else {
                        ToolAlert.toastShort(object.getString("resultMessage"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                ToolAlert.toastShort("网络异常");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /***
     * listview监听进入直播间
     */
    public void OnclickListview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Live lives = liveAdapter.getItem(position-1);
                if (TextUtils.isEmpty(lives.toString())){
                    return;
                }

                if (Constant.getUser() == null) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }else if (lives.getState().equals("2")) {
                    Intent intent = new Intent(getActivity(), LiveActivity.class);
                    intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                    MySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                    MySelfInfo.getInstance().setJoinRoomWay(false);
                    //Toast.makeText(getActivity(), lives.get, Toast.LENGTH_LONG).show();
                    //hostid和RoomNum相同时作为粉丝进入直播间才是全屏
                    int ids = Integer.valueOf(lives.getUserid());  // 放入是的是userid 那边获取的roomid 是Userid
                    CurLiveInfo.setHostID(lives.getUserid());
                    CurLiveInfo.setHostName(lives.getName());
                    CurLiveInfo.setHostAvator(lives.getIoc());
                    CurLiveInfo.setRoomNum(ids);
                    CurLiveInfo.setImageUrl(lives.getRoomIoc());
                    CurLiveInfo.setTexts("");
                    CurLiveInfo.setTitles(lives.getTitle());
                    CurLiveInfo.setTitle(lives.getTitle());
                    CurLiveInfo.setUserid(Integer.valueOf(lives.getUserid()));
                    Log.i(TAG, "onClick:  getRoomNum"+CurLiveInfo.getRoomNum());
                    // CurLiveInfo.setMembers(items.getWatchCount() + 1); // 添加自己
                    // CurLiveInfo.setAdmires(items.getAdmireCount());
               /* CurLiveInfo.setAddress(items.getLbs().getAddress());*/
                    // CurLiveInfo.setRoomNum(ids);
                    mContext.startActivity(intent);

                } else if (lives.getState().equals("0")) {
                    Intent inte = new Intent(getContext(), MyzhuyeActivity.class);
                    inte.putExtra("liveteacher", lives.getUserid());
                    inte.putExtra("names",lives.getName());
                    mContext.startActivity(inte);
                } else if (lives.getState().equals("1")) {
                    Intent inte = new Intent(getContext(), PcLiveActivity.class);
                    inte.putExtra("live", lives);

                    Log.i(TAG, "onItemClick:  id   "+lives.getRoomid());

                    int ids = Integer.valueOf(lives.getUserid());
                    MySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                    MySelfInfo.getInstance().setJoinRoomWay(false);
                    CurLiveInfo.setHostID(lives.getRoomid());
                    CurLiveInfo.setHostName(lives.getName());
                    CurLiveInfo.setHostAvator(lives.getIoc());
                    CurLiveInfo.setLiveUrlroom(lives.getRoomid());
                    CurLiveInfo.setUserid(Integer.valueOf(lives.getUserid()));
                    mContext.startActivity(inte);
                }
            }
        });
    }



    public void getHeadImage(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startPage", "0");
        map.put("pageNum", "10");
        ToolOKHttp toolOKHttp = new ToolOKHttp();
        toolOKHttp.post(HttpUrl.liveList, map, new ToolOKHttp.HttpCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject json=new JSONObject(data);
                    if(json.getString("resultCode").equals("100")){
                        JSONObject resultData = json.getJSONObject("resultData");
                        JSONArray ImageArray = resultData.getJSONArray("advertise");
                        for(int i=0;i<ImageArray.length();i++){
                            JSONObject obj=ImageArray.getJSONObject(i);
                            String topimaheurl=obj.getString("imageUrl");
                            String url=obj.getString("url");
                            Log.e("urla",url);
                            topImagelist.add(topimaheurl);
                            urllink.add(url);
                        }
                        mFlyBanner.setImagesUrl(topImagelist);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }

        });
    }


    public void IntentLink(){
        mFlyBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Uri uri = Uri.parse(urllink.get(position));
                String url=urllink.get(position);
//                ((getActivity().getOperation()).addParameter(
//                        "isShowShare", false);
//                ((MainActivity) mContext).getOperation().addParameter(
//                        "url", rowData.getUrl());
//                ((MainActivity) mContext).getOperation().forward(
//                        WebPageActivity.class);

                Intent intent=new Intent(getActivity(), WebPageActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("isShowShare",false);
                startActivity(intent);

            }
        });
    }
}

package com.mytv365.zb.views.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.PersontalReplayAdapter;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.ReplayVideo;
import com.mytv365.zb.model.WonderfulVideo;
import com.mytv365.zb.views.activity.RememberVideoActivity;
import com.mytv365.zb.widget.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 精彩回顾
 * Created by zhangguohao on 16/9/11.
 */
public class ReviewFragment extends BaseFragment implements View.OnClickListener{

    private MyGridView gridView;
    private List<ReplayVideo> replayVideoList = new ArrayList<>();
    private List<Drawable> drawables = new ArrayList<>();
    private PersontalReplayAdapter replayAdapter;
    private LinearLayout replay_choice_layout;
    private ImageView replay_choice_bianji;
    private boolean isban;
    private TextView replay_choice_all,replay_choice_delete;
    private List<WonderfulVideo> wonderfularray=new ArrayList<>();
    private static final String TAG = "PersontalUserReplay";
    private ImageView yan_back;
    private boolean[] choiceId;

    @Override
    public int bindLayout() {
        return R.layout.main_fragment05;
    }

    @Override
    public void initParams(Bundle params) {

    }




    @Override
    public void initView(View view) {

        gridView = (MyGridView) findViewById(R.id.gv);
        replay_choice_layout = (LinearLayout) findViewById(R.id.replay_choice_layout);
        replay_choice_bianji = (ImageView) findViewById(R.id.replay_choice_bianji);
        replay_choice_all = (TextView) findViewById(R.id.replay_choice_all);
        replay_choice_delete= (TextView) findViewById(R.id.replay_choice_delete);
       // yan_back= (ImageView) findViewById(R.id.yan_back);
        //yan_back.setOnClickListener(this);
       /* for (int i = 0; i < 10; i++) {

            ReplayVideo video = new ReplayVideo();
            video.resId = R.drawable.replay_bg;
            video.replaynum = i;
            video.name = "2016.08.02直播回放";
            replayVideoList.add(video);

        }*/
        initdate();
        replay_choice_bianji.setOnClickListener(this);
        replay_choice_all.setOnClickListener(this);
        replay_choice_delete.setOnClickListener(this);

        Gridviewonclick();

    }

    public void Gridviewonclick(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                WonderfulVideo vider=wonderfularray.get(position);
                if (isban) {
                    replayAdapter.chiceState(position);
                }else{
                    Intent inte = new Intent(getContext(), RememberVideoActivity.class);
                    inte.putExtra("id",vider.getId());
                    inte.putExtra("urls",vider.getPayurl());
                    inte.putExtra("name",vider.getFileName());
                    getActivity().startActivity(inte);


                }



            }
        });
    }


    @Override
    public void doBusiness(Context mContext) {





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.replay_choice_bianji:
                if (isban) {
                    replay_choice_layout.setVisibility(View.INVISIBLE);
                    Log.i("tag","点击编辑显示isban 为true");
                    isban = false;
                    // 显示预选择图片
                    replayAdapter.isShowChice = false;
                    replayAdapter.notifyDataSetChanged();
                } else {
                    replay_choice_layout.setVisibility(View.VISIBLE);
                    Log.i("tag","点击编辑显示isban 为fasle");
                    isban = true;
                    // 隐藏预选图片
                    replayAdapter.isShowChice = true;
                    replayAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.replay_choice_all:
                replayAdapter.chiceAll();
                break;
            case R.id.replay_choice_delete:
                //DeleteChoice();
                break;


        }



    }

    public void initdate(){

      /*  for(int i=0;i<10;i++){
            WonderfulVideo wonderfulVideo=new WonderfulVideo();
            wonderfulVideo.setValueCount("");
            wonderfulVideo.setId("10");
            wonderfulVideo.setFileName("neaos");
            wonderfulVideo.setFileId("10");
            wonderfulVideo.setCoverImage("");
            wonderfularray.add(wonderfulVideo);


        }*/

        OkGo.post(HttpUrl.jingcaihuigu)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("currentPage", "1")
                .params("pageSize","10")
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json=new JSONObject(s);
                            if(json.getString("resultCode").equals("100")){

                                JSONArray obj=json.getJSONArray("resultData");
//                                for(int i=0;i<obj.length();i++){
//                                    JSONObject jsonObject=obj.getJSONObject(i);
//                                    WonderfulVideo wonderfulVideo=new WonderfulVideo();
//                                    wonderfulVideo.setCoverImage("");
//                                    wonderfulVideo.setFileId("");
//                                    wonderfulVideo.setFileName(jsonObject.getString("videoName"));
//                                    wonderfulVideo.setId(jsonObject.getString("id"));
//                                    wonderfulVideo.setPayurl(jsonObject.getString("PLAYURL"));
//                                    wonderfularray.add(wonderfulVideo);
//
//
//                                }

                            }

                            replayAdapter = new PersontalReplayAdapter(mContext, wonderfularray, drawables);
                            gridView.setAdapter(replayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

      /*  Map<String, String> map = new HashMap<String, String>();
        map.put("currentPage","0");
        map.put("pageSize","50");

        OkHttpClientManager.postAsyn(HttpUrl.jingcaihuigu, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json=new JSONObject(response);
                    if(json.getString("resultCode").equals("100")){

                        JSONArray obj=json.getJSONArray("resultData");
                        for(int i=0;i<obj.length();i++){
                            JSONObject jsonObject=obj.getJSONObject(i);
                            WonderfulVideo wonderfulVideo=new WonderfulVideo();
                            wonderfulVideo.setCoverImage("");
                            wonderfulVideo.setFileId("");
                            wonderfulVideo.setFileName(jsonObject.getString("videoName"));
                            wonderfulVideo.setId(jsonObject.getString("id"));
                            wonderfulVideo.setValueCount("");
                            wonderfularray.add(wonderfulVideo);


                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },map,"");*/

    }

}

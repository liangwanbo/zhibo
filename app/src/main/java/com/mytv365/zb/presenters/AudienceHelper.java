package com.mytv365.zb.presenters;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.Concern;
import com.mytv365.zb.presenters.viewinface.Audience;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/25 0025.
 *
 * yang  关注
 *
 */
public class AudienceHelper  extends Presenter {

    private Context mContext;
    private Audience audienceview;


    public AudienceHelper(Context context, Audience audienceview){
        this.mContext=context;
        this.audienceview=audienceview;
    }



    public void addaudience(int roomid){

        OkGo.post(HttpUrl.audienceUrl)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("follower", roomid)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("audio", "关注成功信息是"+response);
                        JSONObject obj= null;
                        try {
                            obj = new JSONObject(s);
                            int resultCode=obj.getInt("resultCode");
                            audienceview.addaudience(resultCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

//        Map<String,String> map=new HashMap<>();
//        map.put("follower",roomid+"");
//        OkHttpClientManager.postAsyn(HttpUrl.audienceUrl, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.i("audio", "关注失败");
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.i("audio", "关注成功信息是"+response);
//                JSONObject obj= null;
//                try {
//                    obj = new JSONObject(response);
//                    int resultCode=obj.getInt("resultCode");
//                        audienceview.addaudience(resultCode);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },map,"");
    }


    public void CancelAudience(int roomid){

        OkGo.post(HttpUrl.CancelaudienceUrl)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("follower", roomid)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("audio", "关注成功信息是"+response);
                        JSONObject obj= null;
                        try {
                            obj = new JSONObject(s);
                            int resultCode=obj.getInt("resultCode");
                            audienceview.cancelaudien(resultCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

//        Map<String,String> map=new HashMap<>();
//        map.put("follower",roomid+"");
//        OkHttpClientManager.postAsyn(HttpUrl.CancelaudienceUrl, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.i("audio", "关注失败");
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.i("audio", "关注成功信息是"+response);
//                JSONObject obj= null;
//                try {
//                obj = new JSONObject(response);
//                int resultCode=obj.getInt("resultCode");
//                audienceview.cancelaudien(resultCode);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        },map,"");
    }


    public void SelectAudience(int roomid){

        OkGo.post(HttpUrl.AcudienGuanxUrl)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("userId", roomid)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("audio", "关注成功信息是"+response);
                        JSONObject obj= null;
                        try {
                            obj = new JSONObject(s);
                            int resultCode=obj.getInt("resultCode");
                            audienceview.audienguanx(resultCode);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

//        Map<String,String> map=new HashMap<>();
//        map.put("userId",roomid+"");
//        OkHttpClientManager.postAsyn(HttpUrl.AcudienGuanxUrl, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.i("audio", "关注失败");
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.i("audio", "关注成功信息是"+response);
//                JSONObject obj= null;
//                try {
//                    obj = new JSONObject(response);
//                    int resultCode=obj.getInt("resultCode");
//                    audienceview.audienguanx(resultCode);
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//            }
//        },map,"");

    }



    List<Concern> concernlist=null;
    public List<Concern> getConcernlist(String page, String pagesize){
        Map<String,String> map=new HashMap<>();
        map.put("currentPage",page);
        map.put("pageSize",pagesize);
        OkGo.post(HttpUrl.SelectAudienceUrl)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("currentPage", page)
                .params("pageSize",pagesize)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("audio", "关注成功信息是"+response);
                        JSONObject obj= null;
                        try {
                            obj = new JSONObject(s);
                            int resultCode=obj.getInt("resultCode");
                            if (resultCode==100){
                                JSONArray jsonArray=obj.getJSONArray("resultData");
                                Type listType=new TypeToken<ArrayList<Concern>>(){}.getType();
                                concernlist=new Gson().fromJson(jsonArray.toString(),listType);
                                List<String> listid=new ArrayList<String>();
                                for (int i = 0; i < concernlist.size(); i++) {
                                    Concern concern=concernlist.get(i);
                                    listid.add(concern.getFollower()+"");
                                }

                                if (listid.size()==0){
                                    List<TIMUserProfile> timUserProfiles=new ArrayList<TIMUserProfile>();
                                    audienceview.getConcernList(timUserProfiles);
                                    return;
                                }


                                TIMFriendshipManager.getInstance().getUsersProfile(listid, new TIMValueCallBack<List<TIMUserProfile>>() {
                                    @Override
                                    public void onError(int i, String s) {
                                        Log.i("fans", "onError: "+s);
                                    }


                                    @Override
                                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                                        audienceview.getConcernList(timUserProfiles);
                                    }
                                });
                            }
//                    audienceview.audienguanx(resultCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

//        OkHttpClientManager.postAsyn(HttpUrl.SelectAudienceUrl, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.i("audio", "关注失败");
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.i("audio", "关注成功信息是"+response);
//                JSONObject obj= null;
//                try {
//                    obj = new JSONObject(response);
//                    int resultCode=obj.getInt("resultCode");
//                    if (resultCode==100){
//                        JSONArray jsonArray=obj.getJSONArray("resultData");
//                        Type listType=new TypeToken<ArrayList<Concern>>(){}.getType();
//                        concernlist=new Gson().fromJson(jsonArray.toString(),listType);
//                        List<String> listid=new ArrayList<String>();
//                        for (int i = 0; i < concernlist.size(); i++) {
//                            Concern concern=concernlist.get(i);
//                            listid.add(concern.getFollower()+"");
//                        }
//
//
//                        TIMFriendshipManager.getInstance().getUsersProfile(listid, new TIMValueCallBack<List<TIMUserProfile>>() {
//                            @Override
//                            public void onError(int i, String s) {
//                                Log.i("fans", "onError: "+s);
//                            }
//
//                            @Override
//                            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
//                                audienceview.getConcernList(timUserProfiles);
//                            }
//                        });
//                    }
////                    audienceview.audienguanx(resultCode);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },map,"");

        return concernlist;
    }


    public List<Concern> getByConcernlist(String page, String pagesize){

        OkGo.post(HttpUrl.SelectByAudienUrl)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("currentPage", page)
                .params("pageSize",pagesize)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.i("audio", "关注成功信息是"+response);
                        JSONObject obj= null;
                        try {
                            obj = new JSONObject(s);
                            int resultCode=obj.getInt("resultCode");
                            if (resultCode==100){
                                JSONArray jsonArray=obj.getJSONArray("resultData");
                                Type listType=new TypeToken<ArrayList<Concern>>(){}.getType();
                                concernlist=new Gson().fromJson(jsonArray.toString(),listType);
                                List<String> listid=new ArrayList<String>();
                                for (int i = 0; i < concernlist.size(); i++) {
                                    Concern concern=concernlist.get(i);
                                    listid.add(concern.getCreateUser()+"");
                                }

                                if (listid.size()==0){
                                    List<TIMUserProfile> timUserProfiles=new ArrayList<TIMUserProfile>();
                                    audienceview.getByConernList(timUserProfiles);
                                    return;
                                }




                                TIMFriendshipManager.getInstance().getUsersProfile(listid, new TIMValueCallBack<List<TIMUserProfile>>() {
                                    @Override
                                    public void onError(int i, String s) {
                                    }

                                    @Override
                                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                                        audienceview.getByConernList(timUserProfiles);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });


//        Map<String,String> map=new HashMap<>();
//        map.put("currentPage",page);
//        map.put("pageSize",pagesize);
//        OkHttpClientManager.postAsyn(HttpUrl.SelectByAudienUrl, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.i("audio", "关注失败");
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.i("audio", "关注成功信息是"+response);
//                JSONObject obj= null;
//                try {
//                    obj = new JSONObject(response);
//                    int resultCode=obj.getInt("resultCode");
//                    if (resultCode==100){
//                        JSONArray jsonArray=obj.getJSONArray("resultData");
//                        Type listType=new TypeToken<ArrayList<Concern>>(){}.getType();
//                        concernlist=new Gson().fromJson(jsonArray.toString(),listType);
//                        List<String> listid=new ArrayList<String>();
//                        for (int i = 0; i < concernlist.size(); i++) {
//                            Concern concern=concernlist.get(i);
//                            listid.add(concern.getCreateUser()+"");
//                        }
//
//                        TIMFriendshipManager.getInstance().getUsersProfile(listid, new TIMValueCallBack<List<TIMUserProfile>>() {
//                            @Override
//                            public void onError(int i, String s) {
//                            }
//
//                            @Override
//                            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
//                                audienceview.getByConernList(timUserProfiles);
//                            }
//                        });
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },map,"");
        return concernlist;
    }

    @Override
    public void onDestory() {
    }

}

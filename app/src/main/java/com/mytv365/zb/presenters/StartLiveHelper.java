package com.mytv365.zb.presenters;

import android.content.Context;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class StartLiveHelper extends  Presenter{


    private Context mContext;
    private StartLiveView mstartLiveView;


    public StartLiveHelper(Context context,StartLiveView startLiveView){
        this.mContext=context;
        this.mstartLiveView=startLiveView;
    }


    public void RestartLive(String roomName) {

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("liveUrl", Constant.getUser().getId());
//        //map.put("toKen", Constant.getUser().getToKen());
//        map.put("roomName",edit_title.getText().toString());

        OkGo.post(HttpUrl.statrtlive)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("liveUrl", Constant.getUser().getId())
//                .params("roomName", edit_title.getText().toString())
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json = null;
                            json = new JSONObject(s);
                            if (json.getString("resultCode").equals("100")) {
                                Toast.makeText(mContext, "继续直播", Toast.LENGTH_SHORT).show();
                                mstartLiveView.RestartSuccess();
                            } else {
                                Toast.makeText(mContext, "直播失败", Toast.LENGTH_SHORT).show();
                                mstartLiveView.RestartFail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
//                        Dismiss();
                        mstartLiveView.RestartFail();
                        super.onError(call, response, e);
                    }
                });


//        OkHttpClientManager.postAsyn(HttpUrl.statrtlive, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Dismiss();
//                Log.e(TAG+"error,error",request.body().toString()+e.getMessage());
//
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.e("TAGTAGTAGTAGTAGs",response);
//                try {
//                    JSONObject json= null;
//                        json = new JSONObject(response);
//                    if(json.getString("resultCode").equals("100")) {
//                        Toast.makeText(zhuboActivity.this,"开播成功",Toast.LENGTH_SHORT).show();
//                        Intent intents = new Intent(zhuboActivity.this, LiveActivity.class);
//                        intents.putExtra(Constants.ID_STATUS, Constants.HOST);
//                        MySelfInfo.getInstance().setIdStatus(Constants.HOST);
//                        MySelfInfo.getInstance().setJoinRoomWay(true);
//                        CurLiveInfo.setHostName(Constant.getUser().getNickName());
//                        CurLiveInfo.setTitle(edit_title.getText().toString());
//                        CurLiveInfo.setHostID(Constant.getUser().getId());
////                        CurLiveInfo.setRoomNum(MySelfInfo.getInstance().getMyRoomNum());
//                        CurLiveInfo.setRoomNum(Integer.valueOf(Constant.getUser().getId()) );
//                        startActivity(intents);
//                        Dismiss();
//                        zhuboActivity.this.finish();
//                    }else{
//                        Dismiss();
//                        Toast.makeText(zhuboActivity.this,"开播失败",Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },map,"");


//        OkGo.post(HttpUrl.statrtlive)
//                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
//                .headers("header1", "headerValue1")
//                .params("liveUrl",Constant.getUser().getId())
//                .params("roomName",edit_title.getText().toString())
//                .setCertificates()
//                .execute(new StringDialogCallback(this) {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        try {
//                            JSONObject json=new JSONObject(s);
//
//                            if(json.getString("resultCode").equals("100")) {
//                                Intent intents = new Intent(zhuboActivity.this, LiveActivity.class);
//                                intents.putExtra(Constants.ID_STATUS, Constants.HOST);
//                                MySelfInfo.getInstance().setIdStatus(Constants.HOST);
//                                MySelfInfo.getInstance().setJoinRoomWay(true);
//                                CurLiveInfo.setHostName(Constant.getUser().getNickName());
//                                CurLiveInfo.setTitle(edit_title.getText().toString());
//                                CurLiveInfo.setHostID(Constant.getUser().getId());
////                        CurLiveInfo.setRoomNum(MySelfInfo.getInstance().getMyRoomNum());
//                                CurLiveInfo.setRoomNum(Integer.valueOf(Constant.getUser().getId()) );
//                                startActivity(intents);
//                                zhuboActivity.this.finish();
//
//                            }else{
//                                Toast.makeText(zhuboActivity.this,"你未登录，请登录",Toast.LENGTH_LONG).show();
//                                Intent inten=new Intent(zhuboActivity.this, LoginActivity.class);
//                                startActivity(inten);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });

    }



    @Override
    public void onDestory() {

    }
}

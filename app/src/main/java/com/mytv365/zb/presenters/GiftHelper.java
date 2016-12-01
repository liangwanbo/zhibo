package com.mytv365.zb.presenters;

import android.content.Context;

import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.Mygift;
import com.mytv365.zb.okhttp.http.OkHttpClientManager;
import com.mytv365.zb.presenters.viewinface.GiftView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2016/10/12.
 */
public class GiftHelper extends Presenter {
    private List<Mygift> list=new ArrayList<>();

    public Context mContext;
    private GiftView giftView;

    public GiftHelper(Context context,GiftView mgiftView){
        mContext=context;
        giftView=mgiftView;

    }


    public void getgiftdata(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("currentPage","1");
        map.put("pageSize","20");

        OkHttpClientManager.postAsyn(HttpUrl.gifts, new OkHttpClientManager.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject json=new JSONObject(response);
                    String code=json.optString("resultCode");
                    if(code.equals("100")){
                        JSONArray jsonArray=json.getJSONArray("resultData");
                        for(int i=0;i<jsonArray.length();i++){
                            Mygift mygift=new Mygift();
                            JSONObject obj=jsonArray.optJSONObject(i);
                            mygift.setGiftEffect(obj.getString("giftEffect"));
                            mygift.setGiftLogo(obj.getString("giftLogo"));
                            mygift.setGiftName(obj.getString("giftName"));
                            mygift.setGiftPrice(obj.getString("giftPrice"));
                            mygift.setId(obj.getString("id"));
                            list.add(mygift);
                        }
                        giftView.sucess();
                        giftView.giftdata(list);

                    }
                    giftView.onfiled();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },map,"");
    }




    @Override
    public void onDestory() {
        mContext=null;
        giftView=null;
    }
}

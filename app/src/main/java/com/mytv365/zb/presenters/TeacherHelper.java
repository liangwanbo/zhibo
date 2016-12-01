package com.mytv365.zb.presenters;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.Teachers;
import com.mytv365.zb.presenters.viewinface.TeacherView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/26.
 * 老师主页信息帮助类
 */
public class TeacherHelper extends Presenter {
    public Context mContext;
    private TeacherView teacherView;


    public TeacherHelper(Context context,TeacherView mteacherView){
        mContext=context;
        teacherView=mteacherView;
    }



    public void getteacherdata(String id){

        OkGo.post(HttpUrl.taacherinfo)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("userId", id)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            if(jsonObject.getString("resultCode").equals("100")){
                                JSONObject obj=jsonObject.getJSONObject("resultData");
                                Teachers teachers=new Teachers();
                                teachers.setRoleIcon(obj.getString("roleIcon"));
                                teachers.setPosition(obj.getString("position"));
                                teachers.setFollowerNumber(obj.getString("followerNumber"));
                                teachers.setFollowStatus(obj.getString("followStatus"));
                                teachers.setHeadImages(obj.getString("headImages"));
                                teachers.setIntroduction(obj.getString("introduction"));
                                teachers.setTeacherId(obj.getString("teacherId"));
                                teachers.setUserId(obj.getString("userId"));
                                teacherView.callback(teachers);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        teacherView.show("获取老师信息失败");

                    }
                });

    }



    @Override
    public void onDestory() {
        mContext=null;
        teacherView=null;
    }
}

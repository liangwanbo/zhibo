package com.mytv365.zb.presenters.viewinface;

import com.mytv365.zb.model.Teachers;

/**
 * Created by Administrator on 2016/10/26.
 */
public interface TeacherView extends MvpView{

    void callback(Teachers teachers);

    void show(String imfo);


}

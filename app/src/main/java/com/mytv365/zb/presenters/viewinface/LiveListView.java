package com.mytv365.zb.presenters.viewinface;

import com.mytv365.zb.model.LiveInfoJson;

import java.util.ArrayList;


/**
 *  列表页面回调
 */
public interface LiveListView extends MvpView{

    void showFirstPage(ArrayList<LiveInfoJson> livelist);
}

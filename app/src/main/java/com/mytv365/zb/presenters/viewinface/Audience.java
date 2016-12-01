package com.mytv365.zb.presenters.viewinface;

import com.tencent.TIMUserProfile;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25 0025.
 *
 */
public interface Audience extends MvpView{

    void addaudience(int type);

    void cancelaudien(int type);

    void audienguanx(int type);

    void getConcernList(List<TIMUserProfile> concernList);

    void getByConernList(List<TIMUserProfile> concernList);




}

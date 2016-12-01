package com.mytv365.zb.presenters.viewinface;

import com.mytv365.zb.model.Mygift;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface GiftView extends MvpView{
    void giftdata(List<Mygift> list);
    void sucess();
    void onfiled();


}

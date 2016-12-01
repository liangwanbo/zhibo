package com.mytv365.zb.presenters.viewinface;


import com.mytv365.zb.model.LiveInfoJson;
import com.tencent.TIMUserProfile;

import java.util.List;

/**
 * 进出房间回调接口
 */
public interface EnterQuiteRoomView extends MvpView {


    void enterRoomComplete(int id_status, boolean succ);

    void quiteRoomComplete(int id_status, boolean succ, LiveInfoJson liveinfo);

    void memberQuiteLive(String[] list);

    void memberJoinLive(String[] list);

    void alreadyInLive(String[] list);

    void memberface(List<TIMUserProfile> timUserProfiles);


}

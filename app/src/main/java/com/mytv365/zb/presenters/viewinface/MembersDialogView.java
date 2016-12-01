package com.mytv365.zb.presenters.viewinface;

import com.mytv365.zb.model.MemberInfo;

import java.util.ArrayList;


/**
 * 成员列表回调
 */
public interface MembersDialogView extends MvpView {

    void showMembersList(ArrayList<MemberInfo> data);

}

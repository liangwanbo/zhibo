package com.mytv365.zb.presenters;

import android.content.Context;

import com.mytv365.zb.avcontrollers.QavsdkControl;
import com.mytv365.zb.model.MemberInfo;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.presenters.viewinface.MembersDialogView;
import com.mytv365.zb.utils.SxbLog;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/5/24.
 */
public class GetMemberListHelper extends Presenter {
    private Context mContext;
    private MembersDialogView mMembersDialogView;
    private static final String TAG = GetMemberListHelper.class.getSimpleName();
    private ArrayList<MemberInfo> mDialogMembers = new ArrayList<MemberInfo>();

    public GetMemberListHelper(Context context, MembersDialogView dialogView) {
        mContext = context;
        mMembersDialogView = dialogView;
    }

    public void getMemberList() {
        TIMGroupManager.getInstance().getGroupMembers("" + MySelfInfo.getInstance().getMyRoomNum(), new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
            @Override
            public void onError(int i, String s) {
                SxbLog.i(TAG, "get MemberList ");
            }

            @Override
            public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                SxbLog.i(TAG, "get MemberList ");
                getMemberListInfo(timGroupMemberInfos);

            }
        });
    }


    /**
     * 拉取成员列表信息
     *
     * @param timGroupMemberInfos
     */
    private void getMemberListInfo(List<TIMGroupMemberInfo> timGroupMemberInfos) {
        mDialogMembers.clear();
        for (TIMGroupMemberInfo item : timGroupMemberInfos) {
            if (item.getUser().equals(MySelfInfo.getInstance().getId())) {
                continue;
            }
            MemberInfo member = new MemberInfo();
            member.setUserId(item.getUser());
            if (QavsdkControl.getInstance().containIdView(item.getUser())) {
                member.setIsOnVideoChat(true);
            }
            mDialogMembers.add(member);

        }

        mMembersDialogView.showMembersList(mDialogMembers);

    }


    @Override
    public void onDestory() {
        mMembersDialogView =null;
        mContext = null;
    }
}

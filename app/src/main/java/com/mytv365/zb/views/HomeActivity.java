package com.mytv365.zb.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.mytv365.zb.R;
import com.mytv365.zb.avcontrollers.QavsdkControl;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.presenters.InitBusinessHelper;
import com.mytv365.zb.presenters.LoginHelper;
import com.mytv365.zb.presenters.ProfileInfoHelper;
import com.mytv365.zb.presenters.viewinface.ProfileView;
import com.mytv365.zb.utils.SxbLog;
import com.mytv365.zb.views.customviews.BaseFragmentActivity;
import com.tencent.TIMUserProfile;

import java.util.List;

/**
 * 主界面
 */
public class HomeActivity extends BaseFragmentActivity implements ProfileView {
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private ProfileInfoHelper infoHelper;
    private LoginHelper mLoginHelper;
    private final Class fragmentArray[] = {FragmentLiveList.class, FragmentPublish.class, FragmentProfile.class};
    private int mImageViewArray[] = {R.drawable.main_tab_01, R.drawable.icon_publish, R.drawable.tab_profile};
    private String mTextviewArray[] = {"live", "publish", "profile"};
    private static final String TAG = HomeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        SxbLog.i(TAG, "HomeActivity onStart");
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        layoutInflater = LayoutInflater.from(this);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.contentPanel);

        int fragmentCount = fragmentArray.length;
        for (int i = 0; i < fragmentCount; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().setDividerDrawable(null);

        }
        mTabHost.getTabWidget().getChildTabViewAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                DialogFragment newFragment = InputDialog.newInstance();
//                newFragment.show(ft, "dialog");

                startActivity(new Intent(HomeActivity.this, PublishLiveActivity.class));

            }
        });

        // 检测是否需要获取头像
        if (TextUtils.isEmpty(MySelfInfo.getInstance().getAvatar())) {
            infoHelper = new ProfileInfoHelper(this);
            infoHelper.getMyProfile();
        }
    }

    @Override
    protected void onStart() {
        SxbLog.i(TAG, "HomeActivity onStart");
        super.onStart();
        if (QavsdkControl.getInstance().getAVContext() == null) {//retry
            InitBusinessHelper.initApp(getApplicationContext());
            SxbLog.i(TAG, "HomeActivity retry login");
            mLoginHelper = new LoginHelper(this);
            mLoginHelper.imLogin(MySelfInfo.getInstance().getId(), MySelfInfo.getInstance().getUserSig());
        }
    }

    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        icon.setImageResource(mImageViewArray[index]);
        return view;
    }

    @Override
    protected void onDestroy() {
        if (mLoginHelper != null)
            mLoginHelper.onDestory();
        SxbLog.i(TAG, "HomeActivity onDestroy");
        //QavsdkControl.getInstance().stopContext();
        super.onDestroy();
    }

    @Override
    public void updateProfileInfo(TIMUserProfile profile) {
        SxbLog.i(TAG, "updateProfileInfo");
        if (null != profile) {
            MySelfInfo.getInstance().setAvatar(profile.getFaceUrl());
            if (!TextUtils.isEmpty(profile.getNickName())) {
                MySelfInfo.getInstance().setNickName(profile.getNickName());
            } else {
                MySelfInfo.getInstance().setNickName(profile.getIdentifier());
            }
        }
    }

    @Override
    public void updateUserInfo(int reqid, List<TIMUserProfile> profiles) {
    }
}

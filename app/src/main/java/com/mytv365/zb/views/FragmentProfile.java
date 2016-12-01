package com.mytv365.zb.views;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mytv365.zb.R;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.presenters.LoginHelper;
import com.mytv365.zb.presenters.ProfileInfoHelper;
import com.mytv365.zb.presenters.viewinface.LogoutView;
import com.mytv365.zb.presenters.viewinface.ProfileView;
import com.mytv365.zb.utils.GlideCircleTransform;
import com.mytv365.zb.utils.SxbLog;
import com.mytv365.zb.utils.UIUtils;
import com.mytv365.zb.views.customviews.LineControllerView;
import com.mytv365.zb.views.customviews.SpeedTestDialog;
import com.tencent.TIMManager;
import com.tencent.TIMUserProfile;
import com.tencent.av.sdk.AVContext;
import com.tencent.qalsdk.QALSDKManager;

import java.util.List;


/**
 * 视频和照片输入页面
 */
public class FragmentProfile extends Fragment implements View.OnClickListener, LogoutView, ProfileView {
    private static final String TAG = "FragmentLiveList";
    private ImageView mAvatar;
    private TextView mProfileName;
    private TextView mProfileId;
    private TextView mProfileInfo;
    private ImageView mEditProfile;
    private LoginHelper mLoginHeloper;
    private ProfileInfoHelper mProfileHelper;
    private LineControllerView mBtnLogout;
    private LineControllerView mBtnSet;
    private LineControllerView mVersion;
    private LineControllerView mSpeedTest;


    public FragmentProfile() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profileframent_layout, container, false);
        mAvatar = (ImageView) view.findViewById(R.id.profile_avatar);
        mProfileName = (TextView) view.findViewById(R.id.profile_name);
        mProfileId = (TextView) view.findViewById(R.id.profile_id);
        mEditProfile = (ImageView) view.findViewById(R.id.edit_profile);
        mProfileInfo = (TextView) view.findViewById(R.id.profile_info);
        mBtnSet = (LineControllerView) view.findViewById(R.id.profile_set);
        mBtnLogout = (LineControllerView) view.findViewById(R.id.logout);
        mSpeedTest = (LineControllerView) view.findViewById(R.id.profile_speed_test);
        mVersion = (LineControllerView) view.findViewById(R.id.version);
        mBtnSet.setOnClickListener(this);
        mBtnLogout.setOnClickListener(this);
        mEditProfile.setOnClickListener(this);
        mVersion.setOnClickListener(this);
        mSpeedTest.setOnClickListener(this);
        mLoginHeloper = new LoginHelper(getActivity().getApplicationContext(), this);
        mProfileHelper = new ProfileInfoHelper(this);
        return view;
    }

    @Override
    public void onDestroy() {
        mLoginHeloper.onDestory();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mProfileInfo) {
            mProfileHelper.getMyProfile();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void enterSetProfile() {
        Intent intent = new Intent(getContext(), SetActivity.class);
        startActivity(intent);
    }

    private void enterEditProfile() {
        Intent intent = new Intent(getContext(), EditProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_set:
                enterSetProfile();
                break;
            case R.id.edit_profile:
                enterEditProfile();
                break;
            case R.id.logout:
                mLoginHeloper.imLogout();
                break;
            case R.id.version:
                showSDKVersion();
                break;
            case R.id.profile_speed_test:
                new SpeedTestDialog(getContext()).start();
                break;
        }
    }


    private void showSDKVersion() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("APP : " + getAppVersion() + "\r\n" + "IM SDK: " + TIMManager.getInstance().getVersion() + "\r\n"
                + "QAL SDK: " + QALSDKManager.getInstance().getSdkVersion() + "\r\n"
                + "AV SDK: " + AVContext.getVersion());
        builder.show();
    }

    private String getAppVersion() {
        PackageManager packageManager = getActivity().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        String version = "";
        try {
            packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ;
        return version;
    }


    @Override
    public void logoutSucc() {
        Toast.makeText(getContext(), "Logout and quite", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void logoutFail() {

    }

    @Override
    public void updateProfileInfo(TIMUserProfile profile) {
        if (TextUtils.isEmpty(profile.getNickName())) {
            MySelfInfo.getInstance().setNickName(profile.getIdentifier());
        } else {
            MySelfInfo.getInstance().setNickName(profile.getNickName());
        }
        mProfileName.setText(MySelfInfo.getInstance().getNickName());
        mProfileId.setText("ID:" + MySelfInfo.getInstance().getId());
        if (TextUtils.isEmpty(profile.getRemark())) {
            MySelfInfo.getInstance().setSign(profile.getSelfSignature());
            mProfileInfo.setText(profile.getSelfSignature());
        }
        if (TextUtils.isEmpty(profile.getFaceUrl())) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            mAvatar.setImageBitmap(cirBitMap);
        } else {
            SxbLog.d(TAG, "profile avator: " + profile.getFaceUrl());
            MySelfInfo.getInstance().setAvatar(profile.getFaceUrl());
            RequestManager req = Glide.with(getActivity());
            req.load(profile.getFaceUrl()).transform(new GlideCircleTransform(getActivity())).into(mAvatar);
        }
        MySelfInfo.getInstance().writeToCache(getContext());
    }

    @Override
    public void updateUserInfo(int reqid, List<TIMUserProfile> profiles) {

    }
}

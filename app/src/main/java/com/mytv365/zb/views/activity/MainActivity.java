package com.mytv365.zb.views.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.tools.ToolAlert;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.avcontrollers.QavsdkControl;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.LoginTeacher;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.model.User;
import com.mytv365.zb.presenters.InitBusinessHelper;
import com.mytv365.zb.presenters.LoginExitHelper;
import com.mytv365.zb.presenters.LoginHelper;
import com.mytv365.zb.presenters.ProfileInfoHelper;
import com.mytv365.zb.presenters.viewinface.ExitView;
import com.mytv365.zb.presenters.viewinface.LoginView;
import com.mytv365.zb.presenters.viewinface.ProfileView;
import com.mytv365.zb.views.fragment.main.MainFragment02;
import com.mytv365.zb.views.fragment.main.MainFragment04;
import com.mytv365.zb.views.fragment.main.MainFragment05;
import com.mytv365.zb.views.fragment.main.SelfFragment;
import com.mytv365.zb.views.fragment.news.NewsFragment;
import com.tencent.TIMUserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements ProfileView, LoginView, ExitView {
    private Context context;
    /*选项卡*/
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private ProfileInfoHelper infoHelper;
    private LoginHelper mLoginHelper;
    /*页面*/
    //private final Class fragmentArray[] = {MainFragment01.class, MainFragment02.class, MainFragment05.class, NewsFragment.class, MainFragment04.class};
    private final Class fragmentArray[] = {NewsFragment.class,MainFragment02.class, MainFragment05.class, SelfFragment.class,MainFragment04.class};
    /*图片*/
    // private int mImageViewArray[] = {R.drawable.main_tab_01,R.drawable.main_tab_02, R.mipmap.main_tabstyle05_press, R.drawable.main_tab_03,R.drawable.main_tab_04};
    /*文字*/
    private int mImageViewArray[] = {R.drawable.main_tab_01,R.drawable.main_tab_02, R.mipmap.main_tabstyle05_press, R.drawable.main_tab_03,R.drawable.main_tab_04};
    //private String mTextviewArray[] = {"擂台","直播",  "","研究院", "我的"};
    private String mTextviewArray[] = {"研究院","直播","","自选股","我的"};
    private static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences LoginSharePrefere;
    private LoginHelper mLoginHeloper;
    private LoginExitHelper loginExitHelper;
    private long mExitTime;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        hiddeTitleBar();
    }

    @Override
    public void doBusiness(Context mContext) {
        this.context = mContext;
        initTab();
        init();
        onClick();
        mLoginHeloper = new LoginHelper(this, this);
        if (QavsdkControl.getInstance().getAVContext() == null) {//retry
            InitBusinessHelper.initApp(getApplicationContext());

            LoginSharePrefere = getSharedPreferences("VertificationLogin", MODE_PRIVATE);
            String username = LoginSharePrefere.getString("username", "");
            String password = LoginSharePrefere.getString("password", "");

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
//                getOperation().forward(LoginActivity.class);
            } else {
                AutomaticLogin(username, password);
            }



            loginExitHelper = new LoginExitHelper(this, this);


//            SxbLog.i(TAG, "HomeActivity retry login");
//            mLoginHelper = new LoginHelper(this);
//            mLoginHelper.imLogin(MySelfInfo.getInstance().getId(), MySelfInfo.getInstance().getUserSig());
//            Toast.makeText(mContext, MySelfInfo.getInstance().getId()+"  "+MySelfInfo.getInstance().getUserSig(), Toast.LENGTH_SHORT).show();

           /* PayTask payTask = new PayTask(this);
            String version = payTask.getVersion();*/
//            Log.i(TAG, "doBusiness:  支付当前版本"+version);
//            Toast.makeText(MainActivity.this, "版本号"+version, Toast.LENGTH_SHORT).show();

        }




//        AutoUpdate update=new AutoUpdate(this);
//        update.getCheck();


//        registerHomeListener();


//        VersionManager.AppVersion version=new VersionManager.AppVersion();
//        // 设置文件url
//        version.setApkUrl("http://yhzb.mytv365.com/apk/yhzb1.0.apk");
//        // 设置文件名
//        version.setFileName("手机QQ4");
//        // 设置文件在sd卡的目录
//        version.setFilePath("update");
//        // 设置app当前版本号
//        version.setVersionCode(2 + "");
//
//
//       final VersionManager manager=VersionManager.getInstance(this,version);
//        manager.setOnUpdateListener(new VersionManager.OnUpdateListener() {
//            @Override
//            public void hasNewVersion(boolean has) {
//                if (has) {
//                    Toast.makeText(MainActivity.this, "检测到有新版本",
//                            Toast.LENGTH_LONG).show();
//                    manager.downLoad();
//                }
//            }
//
//            @Override
//            public void onDownloading() {
//                Toast.makeText(MainActivity.this, "正在下载...", Toast.LENGTH_LONG)
//                        .show();
//            }
//
//            @Override
//            public void onSuccess() {
//                Toast.makeText(MainActivity.this, "下载成功等待安装", Toast.LENGTH_LONG)
//                        .show();
//            }
//
//            @Override
//            public void onError(String msg) {
//                Toast.makeText(MainActivity.this, "更新失败" + msg,
//                        Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//        manager.checkUpdateInfo();

    }

    @Override
    public void updateProfileInfo(TIMUserProfile profile) {
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
    public void updateUserInfo(int requestCode, List<TIMUserProfile> profiles) {
    }

    /***
     * 初始化Tab
     */
    private void initTab() {
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
    }

    /***
     * 点击事件
     */
    private void onClick() {
        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                DialogFragment newFragment = InputDialog.newInstance();
//                newFragment.show(ft, "dialog");
//                startActivity(new Intent(MainActivity.this, PublishLiveActivity.class));
                getOperation().forward(FunctionActivity.class);
            }
        });
    }



    /***
     * 初始化一些信心
     */
    private void init() {
        // 检测是否需要获取头像
        if (TextUtils.isEmpty(MySelfInfo.getInstance().getAvatar())) {
            infoHelper = new ProfileInfoHelper(this);
            infoHelper.getMyProfile();
        }
    }


    /***
     * 获得 View
     *
     * @param index
     * @return
     */
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        TextView name = (TextView) view.findViewById(R.id.name);
        if (index != 2) {
            name.setText(mTextviewArray[index]);
            name.setVisibility(View.VISIBLE);
        } else {
            name.setVisibility(View.GONE);
        }
        icon.setImageResource(mImageViewArray[index]);
        return view;
    }



    @Override
    protected void onDestroy() {
        if (mLoginHelper != null)
            mLoginHelper.onDestory();

        if (loginExitHelper != null)
            loginExitHelper.onDestory();

//        mHomeWatcher.stopWatch();

        super.onDestroy();
    }


    /***
     * 初始化标题
     */
    public void initTitle(String title) {
        setWindowTitle(title, Gravity.CENTER);
        showTitleBar();
    }


    /***
     * 发送登录
     */
    private void AutomaticLogin(String user_username, String user_password) {
        OkGo.post(HttpUrl.login)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("phone", user_username)
                .params("password", user_password)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {

                            JSONObject object = new JSONObject(s);
                            Log.i(TAG, "onSuccess: 登录返回" + object.toString());
                            JSONObject resultdata = object.getJSONObject("resultData");
                            JSONArray jsonarray = resultdata.getJSONArray("moudel");
                            String modelarray[] = new String[jsonarray.length()];
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject modelid = jsonarray.getJSONObject(i);
                                String modelid1 = modelid.getString("moudelId");
                                modelarray[i] = modelid1;
                            }

                            LoginTeacher loginTeacher = null;
                            for (String a :
                                    modelarray) {
                                if (a.equals("2")) {
                                    JSONObject teacherobj = resultdata.getJSONObject("teacher");
                                    loginTeacher = new LoginTeacher();
                                    loginTeacher.setId(teacherobj.getInt("id"));
                                    loginTeacher.setAptitude(teacherobj.getString("aptitude"));
                                    loginTeacher.setPlayUrl(teacherobj.getString("playUrl"));
                                    loginTeacher.setPosition(teacherobj.getString("position"));
                                    Constant.loginTeacher = loginTeacher;
                                }

                                if (a.equals("6")) {
                                    Constant.isvercationReplay = true;
                                }
                            }


                            if (object.getString("resultType").equals("success")) {
                                JSONObject resData = object.getJSONObject("resultData");
                                JSONObject uJson = resData.getJSONObject("user");
                                User user = new User();
                                user.setToKen("");
                                // Log.e("Mytoken",resData.getString("toKen"));
                                user.setSig(resData.getString("sig"));
                                //*地址*//*
                                user.setAddress(uJson.getString("address"));
                                //*昵称*//*
                                user.setNickName(uJson.getString("nickName"));
                                //*角色ID*//*
                                user.setRoleId(uJson.getString("roleId"));
                                //*性别*//*
                                user.setSex(uJson.getString("sex"));
                                //*个性签名*//*
                                user.setSign(uJson.getString("sign"));
                                //*来源*//*
                                user.setSource(uJson.getString("source"));
                                //*头像*//*
                                user.setHeadImages(uJson.getString("headImages"));
                                //*真实姓名*//*
                                user.setRealName(uJson.getString("realName"));
                                //*余额*//*
                                user.setBalance(uJson.getString("balance"));
                                //*手机*//*
                                user.setPhone(uJson.getString("phone"));
                                //*用户ID*//*
                                user.setId(uJson.getString("id"));

                                Log.e("IDS", uJson.getString("id"));
                                MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
                                //*邮箱*//*
                                user.setEmail(uJson.getString("email"));
                                //*简介*//*
                                user.setDate(uJson.getString("birthDate"));
                                user.setIntroduction(uJson.getString("introduction"));
                                Constant.setUser(user);
                                String sig = resData.getString("sig");
                                Constant.sig = sig;

//                                ToolAlert.toastShort("登录成功！");

                                Log.i(TAG, "onResponse: 用户信息：" + user.toString());
                                Log.i("tag", "onResponse: user" + user.toString());
                                MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
                                MySelfInfo.getInstance().setNickName(uJson.getString("nickName"));
                                MySelfInfo.getInstance().setId(resData.getString("sig"));
                                MySelfInfo.getInstance().setUserSig(uJson.getString("id"));
                                MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
                                MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
                                //ToolAlert.toastShort(uJson.getString("id"));
                                //ToolAlert.toastShort(resData.getString("sig"));
                                mLoginHeloper.imLogin(Constant.getUser().getId(), Constant.sig);

//                            finish();
                            } else {

                                ToolAlert.toastShort(object.getString("resultMessage"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(Call call, Response response, Exception e) {

                        super.onError(call, response, e);

                    }

                });

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("phone", user_username);//手机号
//        map.put("password", user_password); //密码
//            OkHttpClientManager.postAsyn(HttpUrl.login, new OkHttpClientManager.StringCallback() {
//                @Override
//                public void onFailure(Request request, IOException e) {
//                }
//
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        if (object.getString("resultType").equals("success")) {
//                            JSONObject resData = object.getJSONObject("resultData");
//                            JSONObject uJson = resData.getJSONObject("user");
//                            User user = new User();
//                            user.setToKen("");
//                            // Log.e("Mytoken",resData.getString("toKen"));
//                            user.setSig(resData.getString("sig"));
//                            //*地址*//*
//                            user.getAddress(uJson.getString("address"));
//                            //*昵称*//*
//                            user.setNickName(uJson.getString("nickName"));
//                            //*角色ID*//*
//                            user.setRoleId(uJson.getString("roleId"));
//                            //*性别*//*
//                            user.setSex(uJson.getString("sex"));
//                            //*个性签名*//*
//                            user.setSign(uJson.getString("sign"));
//                            //*来源*//*
//                            user.setSource(uJson.getString("source"));
//                            //*头像*//*
//                            user.setHeadImages(uJson.getString("headImages"));
//                            //*真实姓名*//*
//                            user.setRealName(uJson.getString("realName"));
//                            //*余额*//*
//                            user.setBalance(uJson.getString("balance"));
//                            //*手机*//*
//                            user.setPhone(uJson.getString("phone"));
//                            //*用户ID*//*
//                            user.setId(uJson.getString("id"));
//
//                            Log.e("IDS", uJson.getString("id"));
//                            MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
//                            //*邮箱*//*
//                            user.setEmail(uJson.getString("email"));
//                            //*简介*//*
//                            user.setIntroduction(uJson.getString("introduction"));
//                            Constant.setUser(user);
//                            String sig=resData.getString("sig");
//                            Constant.sig=sig;
//
//                            ToolAlert.toastShort("登录成功！");
//
//                            Log.i(TAG,  "onResponse: 用户信息："+user.toString());
//                            Log.i("tag", "onResponse: user"+user.toString());
//                            MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
//                            MySelfInfo.getInstance().setNickName(uJson.getString("nickName"));
//                            MySelfInfo.getInstance().setId(resData.getString("sig"));
//                            MySelfInfo.getInstance().setUserSig(uJson.getString("id"));
//                            MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
//                            MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
//                            //ToolAlert.toastShort(uJson.getString("id"));
//                            //ToolAlert.toastShort(resData.getString("sig"));
//                            mLoginHeloper.imLogin(Constant.getUser().getId(),Constant.sig);
////                            finish();
//                        } else {
//                            ToolAlert.toastShort(object.getString("resultMessage"));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, map, "");

    }

   /* HomeListener mHomeWatcher;
    *//**
     * 注册Home键的监听
     *//*
    private void registerHomeListener() {
        HomeListener mHomeWatcher = new HomeListener(this);
        mHomeWatcher.setOnHomePressedListener(new HomeListener.OnHomePressedListener() {

            @Override
            public void onHomePressed() {
                //TODO 进行点击Home键的处理
                Log.i("xsl", "0000000000000");
//                stopService(mIntentService);


            }

            @Override
            public void onHomeLongPressed() {
                //TODO 进行长按Home键的处理
                Log.i("xsl", "0000000000000");
//                ActivityManagerApplication.destoryActivity("LiveActivity");
            }
        });

        mHomeWatcher.startWatch();

    }*/




    @Override
    public void loginSucc() {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public void exit() {
    }

    @Override
    public void ExitBackView() {
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

package com.mytv365.zb.views.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.config.SysEnv;
import com.fhrj.library.tools.ToolImage;
import com.fhrj.library.tools.ToolUnit;
import com.fhrj.library.view.imageview.RoundImageView;
import com.fhrj.library.view.listview.ListViewScoll;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.listview.ListViewSetUpAdapter;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.Up;
import com.mytv365.zb.presenters.AudienceHelper;
import com.mytv365.zb.presenters.viewinface.Audience;
import com.mytv365.zb.service.VersionManager;
import com.mytv365.zb.utils.SingleDialog;
import com.mytv365.zb.views.activity.MyzhuyeActivity;
import com.mytv365.zb.views.activity.mine.AboutActivity;
import com.mytv365.zb.views.activity.mine.SettingActivity;
import com.mytv365.zb.views.activity.user.FansActivity;
import com.mytv365.zb.views.activity.user.LoginActivity;
import com.mytv365.zb.views.activity.user.ReviewActivity;
import com.mytv365.zb.views.activity.user.UserEditInfoActivity;
import com.mytv365.zb.wxapi.PayEntryActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.TIMUserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的
 * Created by zhangguohao on 16/9/8.
 */
public class MainFragment04 extends BaseFragment implements Audience ,SingleDialog.SingleDialogListener{
    private ImageLoader imageLoader = ToolImage.getImageLoader();
    /*我的主页*/
    private TextView home, persontal_concern_my_people_text, persontal_my_concern_people_text;
    /* 设置列表1*/
    private ListViewScoll mListView;
    /* 头部 */
    private View header;
    /* 头部背景图*/
    private ImageView mImageView;
    /* 标题*/
    private TextView title, persontal_change_notice;
    /*粉丝*/
    private LinearLayout fans;
    /*关注*/
    private LinearLayout focus;

    private String[] function;
    /* 设置数据*/
    private BaseMAdapter<Up> adapter;
    /*头部扩展*/
    private LinearLayout extend;
    /*头像*/
    private RoundImageView tou;
    /* 用户名*/
    private static TextView name;
    /* 个性签名*/
    private static TextView sign;
    /*等级图标*/
    private ImageView dj;
    /* 设置列表2*/
    private ListView listView1;
    /* 设置数据*/
    private BaseMAdapter<Up> adapter1;
    /*关注*/
    private AudienceHelper audienceHelper;
    private MyBrocastReceive myBrocastReceive;
    private String Action = "TUCHULOGIN";
    public static final String EXITLOGIN = "exitlogin";

    private SingleDialog singleDialog;
    private VersionManager manager;



    @Override
    public int bindLayout() {
        return R.layout.main_fragment04;
    }


    @Override
    public void initParams(Bundle params) {
    }

    /**
     * @param view
     */
    @Override
    public void initView(View view) {
        initView();
    }


    /***
     * 第一个ListView
     */
    private void initView() {
        //第一个
        mListView = (ListViewScoll) findViewById(R.id.layout_listview);
        // 分割线高度
        mListView.setDividerHeight(0);
        // 分割线颜色
        mListView.setDivider(new ColorDrawable(ContextCompat.getColor(mContext, R.color.theme_bg)));
        header = LayoutInflater.from(mContext).inflate(
                R.layout.listview_header, null);
        mImageView = (ImageView) header.findViewById(R.id.layout_header_image);
        tou = (RoundImageView) header.findViewById(R.id.vermiceils_user_img);
        persontal_my_concern_people_text = (TextView) header.findViewById(R.id.persontal_my_concern_people_text);
        persontal_concern_my_people_text = (TextView) header.findViewById(R.id.persontal_concern_my_people_text);
        tou.setType(RoundImageView.TYPE_ROUND);
        name = (TextView) header.findViewById(R.id.name);
        sign = (TextView) header.findViewById(R.id.sign);
        persontal_change_notice = (TextView) header.findViewById(R.id.persontal_change_notice);
        dj = (ImageView) header.findViewById(R.id.dj);
        home = (TextView) header.findViewById(R.id.home);
        //粉丝
        fans = (LinearLayout) header.findViewById(R.id.lay_fans);
        focus = (LinearLayout) header.findViewById(R.id.lay_focus);
        //第二个
        listView1 = (ListView) findViewById(R.id.listview1);
        audienceHelper = new AudienceHelper(mContext, this);

        zhuce();

    }


    @Override
    public void doBusiness(final Context mContext) {
        initListView();
        initListView1();
//        onResume();
        // 点击事件
        click();
    }

    /***
     * 初始化设置信息
     */
    public void initListView1() {
        // 分割线高度
        listView1.setDividerHeight(0);
        // 分割线颜色
        listView1.setDivider(new ColorDrawable(ContextCompat.getColor(mContext, R.color.theme_bg)));
        adapter1 = new BasicDataAdapter<Up>(new ListViewSetUpAdapter(mContext));
        // 名称
        function = getResources().getStringArray(R.array.my_up1);
        // 图标
        int[] ioc = new int[]{R.mipmap.my_up_wm,R.mipmap.persontal_version_check ,R.mipmap.my_up_sz};
        // 是否显示右边图标
        boolean[] right = new boolean[]{true, true, true};
        // 是否显示左边图标
        boolean[] left = new boolean[]{true, true, true};


        for (int i = 0; i < function.length; i++) {
            Up up = new Up(function[i], left[i], right[i], ioc[i]);
            adapter1.addItem(up);
        }

        listView1.setAdapter(adapter1);

    }

    /***
     * 初始化设置信息
     */
    public void initListView() {
        tou.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ioc_user));
        adapter = new BasicDataAdapter<Up>(new ListViewSetUpAdapter(mContext));
        mListView.setZoomRatio(ListViewScoll.ZOOM_X2);
        mImageView.setImageDrawable(ContextCompat.getDrawable(mContext,
                R.mipmap.my_bg));
        mImageView.setBackgroundColor(ContextCompat.getColor(mContext,
                R.color.theme_select));
        ViewGroup.LayoutParams params = mImageView.getLayoutParams();
        params.height = SysEnv.SCREEN_HEIGHT / 10 * 3;
        params.width = SysEnv.SCREEN_WIDTH;
        mImageView.setLayoutParams(params);
        // mListView.setDivider(getResources().getDrawable(R.color.theme_bg));
        // mListView.setDividerHeight(20);

        mListView.setParallaxImageView(mImageView);
        header.setPadding(0, 0, 0, ToolUnit.spTopx(10));
        mListView.addHeaderView(header);

        // 名称
//        function = getResources().getStringArray(R.array.my_up);
//        // 图标
//        int[] ioc = new int[]{R.mipmap.my_up_mn,
//                R.mipmap.my_up_dy, R.mipmap.my_up_hg,
//                R.mipmap.my_up_xx};
//        // 是否显示右边图标
//        boolean[] right = new boolean[]{true, true, true, true};
//        // 是否显示左边图标
//        boolean[] left = new boolean[]{true, true, true, true};

        if (Constant.isvercationReplay) {
            function = getResources().getStringArray(R.array.my_up2);
        } else {
            function = getResources().getStringArray(R.array.my_up);
        }

        // 图标
        int[] ioc = new int[]{R.mipmap.persontal_my_gold,
                R.mipmap.my_up_xx};
        // 是否显示右边图标
        boolean[] right = new boolean[]{true, true};
        // 是否显示左边图标
        boolean[] left = new boolean[]{true, true};
        for (int i = 0; i < function.length; i++) {
            Up up = new Up(function[i], left[i], right[i], ioc[i]);
            adapter.addItem(up);
        }


        mListView.setAdapter(adapter);

    }


    /***
     * 初始化设置信息
     */
    public void initListView3() {
        adapter = new BasicDataAdapter<Up>(new ListViewSetUpAdapter(mContext));
        mListView.setZoomRatio(ListViewScoll.ZOOM_X2);
        // mListView.setDivider(getResources().getDrawable(R.color.theme_bg));
        // mListView.setDividerHeight(20);
        // 名称
//        function = getResources().getStringArray(R.array.my_up);
//        // 图标
//        int[] ioc = new int[]{R.mipmap.my_up_mn,
//                R.mipmap.my_up_dy, R.mipmap.my_up_hg,
//                R.mipmap.my_up_xx};
//        // 是否显示右边图标
//        boolean[] right = new boolean[]{true, true, true, true};
//        // 是否显示左边图标
//        boolean[] left = new boolean[]{true, true, true, true};

        if (Constant.isvercationReplay) {
            function = getResources().getStringArray(R.array.my_up2);
        } else {
            function = getResources().getStringArray(R.array.my_up);
        }


        // 图标
        int[] ioc = new int[]{R.mipmap.persontal_my_gold,
                R.mipmap.my_up_xx};
        // 是否显示右边图标
        boolean[] right = new boolean[]{true, true};
        // 是否显示左边图标
        boolean[] left = new boolean[]{true, true};
        for (int i = 0; i < function.length; i++) {
            Up up = new Up(function[i], left[i], right[i], ioc[i]);
            adapter.addItem(up);
        }

        mListView.setAdapter(adapter);
    }

    /***
     * 点击事件
     */
    public void click() {
        name.setOnClickListener(new MyOnClick(1));
        home.setOnClickListener(new MyOnClick(2));
        fans.setOnClickListener(new MyOnClick(3));
        focus.setOnClickListener(new MyOnClick(4));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        if (Constant.getUser() == null) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            startActivity(new Intent(mContext, PayEntryActivity.class));
                        }

                        break;
                    case 2:
                        if (Constant.getUser() == null) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                        } else {
//                        startActivity(new Intent(getActivity(), ReviewActivity.class));
                            Intent intent = new Intent(getActivity(), ReviewActivity.class);
                            intent.putExtra("userId", Constant.getUser().getId());
                            Constant.isFor = "";
                            startActivity(intent);
                        }
                        break;

                    case 3:
                        Toast.makeText(getActivity(), "3", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://关于我们
                        if (Constant.getUser() == null) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            getOperation().forward(AboutActivity.class);
                        }
                        break;
                    case 1://版本检查
                        if (Constant.getUser() == null) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                        } else {
                        //获取当前的版本信息
//                            VersionManager manager=VersionManager.getInstance()
                            OkGo.get(HttpUrl.AppVersionCheckUrl)
                                    .headers("Connection", "close")
                                    .headers("header1", "headerValue1")
                                    .setCertificates()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(String s, Call call, Response response) {
                                            try {
                                                JSONObject obj=new JSONObject(s);
                                                String versionCode=obj.getString("apk_version");
                                                String  AppUrl=obj.getString("apk_url");
                                                Log.i("versiontext", "onSuccess: 后台返回当前版本"+versionCode+"   更新地址"+AppUrl);
                                                VersionManager.AppVersion version=new VersionManager.AppVersion();
                                                Log.i("versiontext", "onSuccess: 当前版本信息"+version.getVersionCode1(getActivity()));
                                                if (Integer.valueOf(versionCode)>version.getVersionCode1(getActivity())){
                                                    // 设置文件url
                                                    version.setApkUrl(AppUrl);
                                                    // 设置文件名
                                                    version.setFileName("炎黄直播");
                                                    // 设置文件在sd卡的目录
                                                    version.setFilePath("update");
                                                    // 设置app当前版本号
                                                    version.setVersionCode(versionCode);
                                                    manager=VersionManager.getInstance(getActivity(),version);
                                                    manager.setOnUpdateListener(new VersionManager.OnUpdateListener() {
                                                        @Override
                                                        public void hasNewVersion(boolean has) {
                                                            if (has) {
                                                                Toast.makeText(getActivity(), "检测到有新版本",
                                                                        Toast.LENGTH_LONG).show();
                                                                manager.downLoad();
                                                            }
                                                        }

                                                        @Override
                                                        public void onDownloading() {
                                                            Toast.makeText(getActivity(), "正在下载...", Toast.LENGTH_LONG)
                                                                    .show();
                                                        }

                                                        @Override
                                                        public void onSuccess() {
                                                            Toast.makeText(getActivity(), "下载成功等待安装", Toast.LENGTH_LONG)
                                                                    .show();
                                                        }

                                                        @Override
                                                        public void onError(String msg) {
                                                            Toast.makeText(getActivity(), "更新失败" + msg,
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    });



                                                    singleDialog=new SingleDialog(getActivity(),2,MainFragment04.this);
                                                    singleDialog.show();



                                                }else {
                                                    Toast.makeText(getContext(), "已是最新版本", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onError(Call call, Response response, Exception e) {
                                            super.onError(call, response, e);
                                        }
                                    });
                        }


                        break;
                    case 2:
                        //设置页面
                        if (Constant.getUser() == null) {
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            getOperation().forward(SettingActivity.class);
                        }
                        break;
                }
            }
        });


        tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.user == null) {
                    getOperation().forward(LoginActivity.class);
                } else {
                    getOperation().forward(UserEditInfoActivity.class);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("tag", "onResume: 个人中心onResume 方法调用");
        if (Constant.getUser() != null) {
            updateUser();
            audienceHelper.getConcernlist(Constant.CONCERNPAGE, Constant.CONCERNPAGESIZE);
            audienceHelper.getByConcernlist(Constant.CONCERNPAGE, Constant.CONCERNPAGESIZE);

            persontal_change_notice.setVisibility(View.VISIBLE);
            initListView3();

        } else {
            name.setText("立即登录");
            persontal_concern_my_people_text.setText("0");
            persontal_my_concern_people_text.setText("0");
            sign.setText("");
            tou.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ioc_user));
            persontal_change_notice.setVisibility(View.INVISIBLE);

        }
    }

    /***
     * 更新用户信息
     */
    private void updateUser() {
        Log.i("tag", "onResume: 个人中心onResume 更新用户信息");
        sign.setVisibility(View.VISIBLE);
        if (!Constant.getUser().getIntroduction().equals("")) {
            sign.setText(Constant.getUser().getIntroduction());
        }

        name.setText(Constant.getUser().getNickName());
        String roleId = Constant.getUser().getRoleId();
        imageLoader.displayImage(Constant.getUser().getHeadImages(), tou);

        switch (roleId) {
            case "1":
                dj.setVisibility(View.GONE);
                break;
            case "2":
                dj.setVisibility(View.VISIBLE);
                break;
            case "3":
                dj.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void addaudience(int type) {
    }

    @Override
    public void cancelaudien(int type) {
    }

    @Override
    public void audienguanx(int type) {
    }

    @Override
    public void getConcernList(List<TIMUserProfile> concernList) {
        Log.i(TAG, "getConcernList: 关注大小 " + concernList.size());
        persontal_my_concern_people_text.setText(String.valueOf(concernList.size()));
    }

    @Override
    public void getByConernList(List<TIMUserProfile> concernList) {
        Log.i(TAG, "getByConernList: 粉丝大小" + concernList.size());
        persontal_concern_my_people_text.setText(String.valueOf(concernList.size()));
    }

    @Override
    public void LiveSureComfirm() {
        manager.checkUpdateInfo();
        singleDialog.dismiss();
    }

    @Override
    public void LiveCancelBtn() {
        singleDialog.dismiss();
    }

    /****
     * 点击事件
     * @author ZhangGuoHao
     * @date 2016年6月8日 下午1:45:09
     */
    public class MyOnClick implements View.OnClickListener {
        int item;

        public MyOnClick() {
        }

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1://登录
                    if (Constant.getUser() == null) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        mContext.startActivityForResult(intent, 0);
                    }

                    break;
                case 2://主页
                    if (Constant.getUser() == null) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(intent);
                    } else {
                        getOperation().forward(MyzhuyeActivity.class);
                    }
                    break;

                case 3://粉丝
                    if (Constant.getUser() == null) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent1 = new Intent(getActivity(), FansActivity.class);
                        intent1.putExtra("type", FansActivity.FANCE);
                        startActivity(intent1);
                    }

                    break;

                case 4://关注
                    if (Constant.getUser() == null) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent2 = new Intent(getActivity(), FansActivity.class);
                        intent2.putExtra("type", FansActivity.AUDIENCE);
                        startActivity(intent2);
                    }
                    break;

                case 5://粉丝
                    getOperation().forward(FansActivity.class);
                    break;

                default:
                    break;
            }
        }
    }


    public void zhuce() {
        myBrocastReceive = new MyBrocastReceive();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action);
        getActivity().registerReceiver(myBrocastReceive, intentFilter);

    }



    private class MyBrocastReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Action)) {
                sign.setText("");
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(myBrocastReceive);
    }


}

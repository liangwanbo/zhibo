package com.mytv365.zb.views;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.fhrj.library.view.imageview.RoundImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.ChatMsgListAdapter;
import com.mytv365.zb.avcontrollers.QavsdkControl;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.common.tuiliuaddress;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.ChatEntity;
import com.mytv365.zb.model.CurLiveInfo;
import com.mytv365.zb.model.LiveInfoJson;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.model.Mygift;
import com.mytv365.zb.model.TIMProfie;
import com.mytv365.zb.model.User;
import com.mytv365.zb.model.liwu;
import com.mytv365.zb.presenters.AudienceHelper;
import com.mytv365.zb.presenters.EnterLiveHelper;
import com.mytv365.zb.presenters.LiveHelper;
import com.mytv365.zb.presenters.LiveListViewHelper;
import com.mytv365.zb.presenters.OKhttpHelper;
import com.mytv365.zb.presenters.StartLiveHelper;
import com.mytv365.zb.presenters.StartLiveView;
import com.mytv365.zb.presenters.viewinface.Audience;
import com.mytv365.zb.presenters.viewinface.EnterQuiteRoomView;
import com.mytv365.zb.presenters.viewinface.GiftHelper;
import com.mytv365.zb.presenters.viewinface.GiftView;
import com.mytv365.zb.presenters.viewinface.LiveView;
import com.mytv365.zb.presenters.viewinface.ProfileView;
import com.mytv365.zb.utils.Constants;
import com.mytv365.zb.utils.GlideCircleTransform;
import com.mytv365.zb.utils.LogConstants;
import com.mytv365.zb.utils.SxbLog;
import com.mytv365.zb.utils.UIUtils;
import com.mytv365.zb.views.activity.MyzhuyeActivity;
import com.mytv365.zb.views.customviews.HeartLayout;
import com.mytv365.zb.views.customviews.MembersDialog;
import com.mytv365.zb.views.zhibolive.livebase.BaseliveActivity;
import com.mytv365.zb.views.zhibolive.livebase.InputTextMsgDialog;
import com.mytv365.zb.widget.BackDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.av.TIMAvManager;
import com.tencent.av.sdk.AVView;
import com.tencent.av.utils.PhoneStatusTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Live直播类
 */
public class LiveActivity extends BaseliveActivity implements EnterQuiteRoomView, LiveView, View.OnClickListener, ProfileView, QavsdkControl.onSlideListener, GiftView, Audience, StartLiveView {
    private static final String TAG = LiveActivity.class.getSimpleName();
    //    private GiftHelper giftHelper;
    private static final int GETPROFILE_JOIN = 0x200;
    private EnterLiveHelper mEnterRoomHelper;
    private AudienceHelper audienceHelper;
    private LiveListViewHelper mLiveListViewHelper;
    private LiveHelper mLiveHelper;
    private RelativeLayout realay_chongzhi, Realitv;
    private ArrayList<ChatEntity> mArrayListChatEntity;
    private ChatMsgListAdapter mChatMsgListAdapter;
    private static final int MINFRESHINTERVAL = 500;
    private static final int UPDAT_WALL_TIME_TIMER_TASK = 1;
    private static final int TIMEOUT_INVITE = 2;
    private boolean mBoolRefreshLock = false;
    private boolean mBoolNeedRefresh = false;
    private final Timer mTimer = new Timer();
    private ArrayList<ChatEntity> mTmpChatList = new ArrayList<ChatEntity>();//缓冲队列
    private TimerTask mTimerTask = null;
    private static final int REFRESH_LISTVIEW = 5;
    private Dialog mMemberDg, closeCfmDg, inviteDg;
    private HeartLayout mHeartLayout;
    private ImageView mLikeTv;
    private HeartBeatTask mHeartBeatTask;//心跳
    private ImageView mHeadIcon;
    private TextView mHostNameTv;
    private LinearLayout mHostLeaveLayout;
    private User user;
    private RelativeLayout mHostLayout;
    private final int REQUEST_PHONE_PERMISSIONS = 0;
    private long mSecond = 0;
    private String formatTime;
    private Timer mHearBeatTimer, mVideoTimer;
    private VideoTimerTask mVideoTimerTask;//计时器
    private TextView mVideoTime;
    private ObjectAnimator mObjAnim;
    private ImageView mRecordBall;
    private int thumbUp = 0;
    private long admireTime = 0;
    private int watchCount = 0;
    private static boolean mBeatuy = false;
    private static boolean mWhite = true;
    private boolean bCleanMode = false;
    private boolean mProfile;
    private boolean bFirstRender = true;
    private boolean bInAvRoom = false, bSlideUp = false, bDelayQuit = false;
    private String backGroundId;
    private TextView audience;
    private TextView tvMembers;
    private TextView tvAdmires;
    private List<String> recodelist;
    private List<liwu> lists = new ArrayList<liwu>();
    private List<MyAdapter> mGridViewAdapters = new ArrayList<MyAdapter>();
    private Dialog mDetailDialog;
    private PopupWindow popupWindow;
    private View popview;
    private FrameLayout fragment_bg;
    private Context mContext;
    private List<Fragment> fragmentList;
    private ViewPager viewPagers;
    private FragmentManager fm = getSupportFragmentManager();
    //旁路直播
    private static boolean isPushed = false;
    private ArrayList<String> mRenderUserList = new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;
    private RelativeLayout Linear_gift;
    private boolean yincang = true;
    private List<GiftHelper> giftHelpers = new ArrayList<>();
    private static final int GRIDVIEW_COUNT = 8;
    private Button button;
    //private List<liwu> lists = new ArrayList<liwu>();
    // private List<MyAdapter> mGridViewAdapters = new ArrayList<MyAdapter>();
    private ViewPager mViewPager;
    private List<View> mAllViews = new ArrayList<View>();
    // private MyViewPagerAdapter myViewPagerAdapter;
    private boolean isVisility = false;
    private String[] giftprices = {"10", "20", "50", "80", "200", "500", "700", "888", "10000", "12000", "15000", "20000"};
    private String[] giftname = {"鲜花", "飞吻", "爱心", "干杯", "钻戒", "钻石", "iPhone7", "财神爷", "法拉利", "大黄蜂", "飞机", "游轮"};
    private Integer[] giftimage = {R.drawable.live_gift_flower, R.drawable.live_gift_kiss, R.drawable.live_gift_heart, R.drawable.live_gift_cheers, R.drawable.live_gift_ring, R.drawable.live_gift_diamond, R.drawable.live_gift_phone, R.drawable.live_gift_god, R.drawable.live_gift_falali, R.drawable.live_gift_wasp, R.drawable.live_gift_plane, R.drawable.live_gift_youlun};
    private ImageView live_gift_img;
    private int imglocationpositon;
    private ImageView anim_img;
    private int roomid;
    private liwu giftliwu;
    private boolean isAudience = false;
    private boolean isGiftBalance = false;
    private boolean booleant = false;

    private tuiliuaddress TLurl;
    private boolean isending = false;


    /**
     * 初始化UI
     */
    private View avView;
    private TextView BtnBack, Btnflash, BtnHeart, BtnNormal, mVideoChat, BtnCtrlVideo, BtnCtrlMic, BtnHungup, mBeautyConfirm;
    private TextView inviteView1, inviteView2, inviteView3;
    private ListView mListViewMsgItems;
    private LinearLayout mVideoMemberCtrlView, mBeautySettings;
    private RelativeLayout mHostCtrView;

    private FrameLayout mFullControllerUi, mBackgound;
    private SeekBar mBeautyBar;
    private int mBeautyRate, mWhiteRate;
    private TextView pushBtn, recordBtn, speedBtn, live_user_balance;

    private ImageView BtnSwitch, BtnMic, BtnInput, BtnScreen, pinlun, share_pt, send_good, send_good_two, BtnBeauty, BtnWhite;
    private RelativeLayout relat_guanzhu, mNomalMemberCtrView;
    private LinearLayout layout_dl, live_frofile_item_layout;
    private RoundImageView live_profile_item_face;
    private EditText assc;

    private ImageView share_pt_member;

    private boolean lubo = false;
    private StartLiveView startLiveView;
    private StartLiveHelper startLiveHelper;
    private String roomName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 不锁屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_live);
        mContext = getApplicationContext();
        TLurl = new tuiliuaddress();
        Realitv = (RelativeLayout) this.findViewById(R.id.Realitv);
        fragment_bg = (FrameLayout) this.findViewById(R.id.fragment_bg);
        realay_chongzhi = (RelativeLayout) this.findViewById(R.id.realay_chongzhi);
        Linear_gift = (RelativeLayout) this.findViewById(R.id.Linear_gift);
        live_gift_img = (ImageView) findViewById(R.id.live_gift_img);
        anim_img = (ImageView) findViewById(R.id.anim_img);
//        giftHelper = new GiftHelper(this, this);
        checkPermission();
        //进出房间的协助类
        mEnterRoomHelper = new EnterLiveHelper(this, this);
        //房间内的交互协助类
        mLiveHelper = new LiveHelper(this, this);
        audienceHelper = new AudienceHelper(this, this);

        // mLiveListViewHelper = new LiveListViewHelper(this);
        initView();
        registerReceiver();
        backGroundId = CurLiveInfo.getHostID();
        roomid = CurLiveInfo.getRoomNum();
        Constant.id = roomid;

        //进入房间流程
        mEnterRoomHelper.startEnterRoom();
        //QavsdkControl.getInstance().setCameraPreviewChangeCallback();
        mLiveHelper.setCameraPreviewChangeCallback();
        registerOrientationListener();
        startOrientationListener();
        //TouchEvent();

        Linear_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisility == true) {
                    mNomalMemberCtrView.setVisibility(View.VISIBLE);
                    Realitv.setVisibility(View.GONE);
                    Linear_gift.setVisibility(View.GONE);
                }
            }
        });


        MyliwuView();
        mEnterRoomHelper.facestep(roomid);
        audienceHelper.SelectAudience(roomid);


//        ActivityManagerApplication.addDestoryActivity(this, LiveActivity.TAG);
        startLiveHelper = new StartLiveHelper(this, this);
        roomName = getIntent().getStringExtra("roomName");

        Log.i(TAG, "onCreate: 直播名字" + roomName);


    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case UPDAT_WALL_TIME_TIMER_TASK:
                    updateWallTime();
                    break;
                case REFRESH_LISTVIEW:
                    doRefreshListView();
                    break;
                case TIMEOUT_INVITE:
                    String id = "" + msg.obj;
                    cancelInviteView(id);
                    mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_HOST_CANCELINVITE, id);
                    break;
                case 15:
                    getuplodeinfo();
                    break;
            }
            return false;
        }
    });

    /**
     * 时间格式化
     */
    private void updateWallTime() {
        String hs, ms, ss;

        long h, m, s;
        h = mSecond / 3600;
        m = (mSecond % 3600) / 60;
        s = (mSecond % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
        if (hs.equals("00")) {
            formatTime = ms + ":" + ss;
        } else {
            formatTime = hs + ":" + ms + ":" + ss;
        }

        if (Constants.HOST == MySelfInfo.getInstance().getIdStatus() && null != mVideoTime) {
            SxbLog.i(TAG, " refresh time ");
            mVideoTime.setText(formatTime);
        }

    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //AvSurfaceView 初始化成功
            if (action.equals(Constants.ACTION_SURFACE_CREATED)) {
                //打开摄像头
                if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
                    mLiveHelper.openCameraAndMic();
                }
            }

            if (action.equals(Constants.ACTION_CAMERA_OPEN_IN_LIVE)) {//有人打开摄像头
                ArrayList<String> ids = intent.getStringArrayListExtra("ids");

                //如果是自己本地直接渲染
                for (String id : ids) {
                    if (!mRenderUserList.contains(id)) {
                        mRenderUserList.add(id);
                    }
                    updateHostLeaveLayout();

                    if (id.equals(MySelfInfo.getInstance().getId())) {
                        showVideoView(true, id);
                        return;
//                        ids.remove(id);
                    }
                }
                //其他人一并获取
                SxbLog.d(TAG, LogConstants.ACTION_VIEWER_SHOW + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "somebody open camera,need req data"
                        + LogConstants.DIV + LogConstants.STATUS.SUCCEED + LogConstants.DIV + "ids " + ids.toString());
                int requestCount = CurLiveInfo.getCurrentRequestCount();
                mLiveHelper.requestViewList(ids);
                requestCount = requestCount + ids.size();
                CurLiveInfo.setCurrentRequestCount(requestCount);
//                }
            }

            if (action.equals(Constants.ACTION_CAMERA_CLOSE_IN_LIVE)) {//有人关闭摄像头
                ArrayList<String> ids = intent.getStringArrayListExtra("ids");
                //如果是自己本地直接渲染
                for (String id : ids) {
                    mRenderUserList.remove(id);
                }
                updateHostLeaveLayout();
            }

            if (action.equals(Constants.ACTION_SWITCH_VIDEO)) {//点击成员回调
                backGroundId = intent.getStringExtra(Constants.EXTRA_IDENTIFIER);
                SxbLog.v(TAG, "switch video enter with id:" + backGroundId);

                updateHostLeaveLayout();

                if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {//自己是主播
                    if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//背景是自己
                        // mHostCtrView.setVisibility(View.VISIBLE);
                        // mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                    } else {//背景是其他成员
                        // mHostCtrView.setVisibility(View.INVISIBLE);
                        //mVideoMemberCtrlView.setVisibility(View.VISIBLE);
                    }
                } else {//自己成员方式
                    if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//背景是自己
                        // mVideoMemberCtrlView.setVisibility(View.VISIBLE);
                        //mNomalMemberCtrView.setVisibility(View.INVISIBLE);
                    } else if (backGroundId.equals(CurLiveInfo.getHostID())) {//主播自己
                        //mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                        //mNomalMemberCtrView.setVisibility(View.VISIBLE);
                    } else {
                        // mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                        // mNomalMemberCtrView.setVisibility(View.INVISIBLE);
                    }
                }
            }
            if (action.equals(Constants.ACTION_HOST_LEAVE)) {//主播结束
                quiteLivePassively();
            }
        }
    };

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SURFACE_CREATED);
        intentFilter.addAction(Constants.ACTION_HOST_ENTER);
        intentFilter.addAction(Constants.ACTION_CAMERA_OPEN_IN_LIVE);
        intentFilter.addAction(Constants.ACTION_CAMERA_CLOSE_IN_LIVE);
        intentFilter.addAction(Constants.ACTION_SWITCH_VIDEO);
        intentFilter.addAction(Constants.ACTION_HOST_LEAVE);
        registerReceiver(mBroadcastReceiver, intentFilter);

    }

    private void unregisterReceiver() {
        unregisterReceiver(mBroadcastReceiver);
    }


    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            SxbLog.d(TAG, "load icon: " + avatar);
//            if (isDestroyed() == true) return;
            RequestManager req = Glide.with(this);
            req.load(avatar).transform(new GlideCircleTransform(this)).into(view);
        }
    }

    private void updateHostLeaveLayout() {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            return;
        } else {
            // 退出房间或主屏为主播且无主播画面显示主播已离开
            if (!bInAvRoom || (CurLiveInfo.getHostID().equals(backGroundId) && !mRenderUserList.contains(backGroundId))) {
                mHostLeaveLayout.setVisibility(View.VISIBLE);
            } else {
                mHostLeaveLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        share_pt_member = (ImageView) findViewById(R.id.share_pt_member);
        share_pt_member.setOnClickListener(this);
        layout_dl = (LinearLayout) findViewById(R.id.layout_dl);
        mHostCtrView = (RelativeLayout) findViewById(R.id.host_bottom_layout);
        mNomalMemberCtrView = (RelativeLayout) findViewById(R.id.member_bottom_layout);
        mVideoMemberCtrlView = (LinearLayout) findViewById(R.id.video_member_bottom_layout);
        mHostLeaveLayout = (LinearLayout) findViewById(R.id.ll_host_leave);
        // mVideoChat = (TextView) findViewById(R.id.video_interact);
        mHeartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        mVideoTime = (TextView) findViewById(R.id.broadcasting_time);
        mHeadIcon = (ImageView) findViewById(R.id.head_icon);
        relat_guanzhu = (RelativeLayout) findViewById(R.id.relat_guanzhu);
        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
        mHostNameTv = (TextView) findViewById(R.id.host_name);
        tvMembers = (TextView) findViewById(R.id.member_counts);
        tvAdmires = (TextView) findViewById(R.id.heart_counts);
        live_frofile_item_layout = (LinearLayout) findViewById(R.id.live_profile_item_face_layout);
        live_user_balance = (TextView) findViewById(R.id.live_user_balance);
        recordBtn = (TextView) findViewById(R.id.record_btn);
//        live_profile_item_face= (RoundImageView) findViewById(R.id.live_profile_item_face);


        speedBtn = (TextView) findViewById(R.id.speed_test_btn);
        speedBtn.setOnClickListener(this);

        BtnCtrlVideo = (TextView) findViewById(R.id.camera_controll);
        BtnCtrlMic = (TextView) findViewById(R.id.mic_controll);
        BtnHungup = (TextView) findViewById(R.id.close_member_video);
        BtnCtrlVideo.setOnClickListener(this);
        BtnCtrlMic.setOnClickListener(this);
        BtnHungup.setOnClickListener(this);
        //TextView roomId = (TextView) findViewById(R.id.room_id);
        //roomId.setText(CurLiveInfo.getChatRoomId());
        audience = (TextView) findViewById(R.id.audience);

        //for 测试用
        TextView paramVideo = (TextView) findViewById(R.id.param_video);
        paramVideo.setOnClickListener(this);
        tvTipsMsg = (TextView) findViewById(R.id.qav_tips_msg);
        tvTipsMsg.setTextColor(Color.GREEN);
        paramTimer.schedule(task, 1000, 1000);


        pushBtn = (TextView) findViewById(R.id.push_btn);
        pushBtn.setOnClickListener(this);
        pushBtn.setVisibility(View.GONE);


        mHeadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LiveActivity.this, MyzhuyeActivity.class);
                Log.i(TAG, "onClick:  getRoomNum"+CurLiveInfo.getRoomNum());
                intent.putExtra(MyzhuyeActivity.LIVETEACHERID, String.valueOf(CurLiveInfo.getRoomNum()));
                intent.putExtra("names", CurLiveInfo.getHostName());
                startActivity(intent);


            }
        });


        //判断用户是否是直播还是进入直播
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            mHostCtrView.setVisibility(View.VISIBLE);
            mNomalMemberCtrView.setVisibility(View.GONE);
            mRecordBall = (ImageView) findViewById(R.id.record_ball);
            Btnflash = (TextView) findViewById(R.id.flash_btn);
            BtnSwitch = (ImageView) findViewById(R.id.switch_cam);
            BtnBeauty = (ImageView) findViewById(R.id.beauty_btn);
            BtnWhite = (ImageView) findViewById(R.id.white_btn);
            BtnMic = (ImageView) findViewById(R.id.mic_btn);
            pinlun = (ImageView) findViewById(R.id.pinlun);
            pinlun.setOnClickListener(this);
            assc = (EditText) findViewById(R.id.assc);
            share_pt = (ImageView) findViewById(R.id.share_pt);
            share_pt.setOnClickListener(this);

            send_good = (ImageView) findViewById(R.id.send_good);
            send_good.setOnClickListener(this);

            send_good_two = (ImageView) findViewById(R.id.send_good_two);
            send_good_two.setOnClickListener(this);


            BtnScreen = (ImageView) findViewById(R.id.fullscreen_btn);
            // mVideoChat.setVisibility(View.VISIBLE);
            Btnflash.setOnClickListener(this);
            BtnSwitch.setOnClickListener(this);
            BtnBeauty.setOnClickListener(this);
            BtnWhite.setOnClickListener(this);
            BtnMic.setOnClickListener(this);
            BtnScreen.setOnClickListener(this);
            // mVideoChat.setOnClickListener(this);
            inviteView1 = (TextView) findViewById(R.id.invite_view1);
            inviteView2 = (TextView) findViewById(R.id.invite_view2);
            inviteView3 = (TextView) findViewById(R.id.invite_view3);
            inviteView1.setOnClickListener(this);
            inviteView2.setOnClickListener(this);
            inviteView3.setOnClickListener(this);
            relat_guanzhu.setVisibility(View.GONE);



            //daleitai

            recordBtn.setOnClickListener(this);
            mHostNameTv.setText(UIUtils.getLimitString(CurLiveInfo.getHostName(), 6));

            // initBackDialog();
            // initDetailDailog();
            initPushDialog();
            initRecordDialog();
            mMemberDg = new MembersDialog(this, R.style.floag_dialog, this);

            //TODO startRecordAnimation();
            showHeadIcon(mHeadIcon, MySelfInfo.getInstance().getAvatar());
            mBeautySettings = (LinearLayout) findViewById(R.id.qav_beauty_setting);
            mBeautyConfirm = (TextView) findViewById(R.id.qav_beauty_setting_finish);
            mBeautyConfirm.setOnClickListener(this);
            mBeautyBar = (SeekBar) (findViewById(R.id.qav_beauty_progress));
            mBeautyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    SxbLog.d("SeekBar", "onStopTrackingTouch");
                    if (mProfile == mBeatuy) {
                        Toast.makeText(LiveActivity.this, "beauty " + mBeautyRate + "%", Toast.LENGTH_SHORT).show();//美颜度
                    } else {
                        Toast.makeText(LiveActivity.this, "white " + mWhiteRate + "%", Toast.LENGTH_SHORT).show();//美白度
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    SxbLog.d("SeekBar", "onStartTrackingTouch");
                }


                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    Log.i(TAG, "onProgressChanged " + progress);
                    if (mProfile == mBeatuy) {
                        mBeautyRate = progress;
                        QavsdkControl.getInstance().getAVContext().getVideoCtrl().inputBeautyParam(getBeautyProgress(progress));//美颜
                    } else {
                        mWhiteRate = progress;
                        QavsdkControl.getInstance().getAVContext().getVideoCtrl().inputWhiteningParam(getBeautyProgress(progress));//美白
                    }
                }
            });

            //  lubo();


        } else {


            recordBtn.setVisibility(View.INVISIBLE);
            relat_guanzhu.setVisibility(View.VISIBLE);
//            LinearLayout llRecordTip = (LinearLayout) findViewById(R.id.record_tip);
            audience.setOnClickListener(this);
//            llRecordTip.setVisibility(View.GONE);
            mHostNameTv.setVisibility(View.VISIBLE);
            initInviteDialog();
            mNomalMemberCtrView.setVisibility(View.VISIBLE);
            mHostCtrView.setVisibility(View.GONE);
            BtnInput = (ImageView) findViewById(R.id.message_input);
            BtnInput.setOnClickListener(this);
            mLikeTv = (ImageView) findViewById(R.id.member_send_good);
            mLikeTv.setOnClickListener(this);
            //mVideoChat.setVisibility(View.GONE);
            BtnScreen = (ImageView) findViewById(R.id.clean_screen);
            BtnScreen.setOnClickListener(this);

            List<String> ids = new ArrayList<>();
            ids.add(CurLiveInfo.getHostID());
            showHeadIcon(mHeadIcon, CurLiveInfo.getHostAvator());
            mHostNameTv.setText(UIUtils.getLimitString(CurLiveInfo.getHostName(), 6));

            mHostLayout = (RelativeLayout) findViewById(R.id.head_up_layout);
            mHostLayout.setOnClickListener(this);
            //BtnScreen.setOnClickListener(this);

        }

        BtnNormal = (TextView) findViewById(R.id.normal_btn);
        BtnNormal.setOnClickListener(this);
        mFullControllerUi = (FrameLayout) findViewById(R.id.controll_ui);
        avView = findViewById(R.id.av_video_layer_ui);//surfaceView;
        BtnBack = (TextView) findViewById(R.id.btn_back);
        BtnBack.setOnClickListener(this);

        mListViewMsgItems = (ListView) findViewById(R.id.im_msg_listview);
        mArrayListChatEntity = new ArrayList<ChatEntity>();
        mChatMsgListAdapter = new ChatMsgListAdapter(this, mListViewMsgItems, mArrayListChatEntity);
        mListViewMsgItems.setAdapter(mChatMsgListAdapter);

//        tvMembers.setText("" + CurLiveInfo.getMembers());
        tvAdmires.setText("" + CurLiveInfo.getAdmires());

    }


    @Override
    protected void onResume() {
        super.onResume();


        QavsdkControl.getInstance().getAVContext().getAudioCtrl().enableSpeaker(true);
        mLiveHelper.resume();
        QavsdkControl.getInstance().onResume();


        if (isending) {
            Log.i(TAG, "onResume:  重新开始直播");
            startLiveHelper.RestartLive(roomName);
        }

    }


    @Override
    protected void onPause() {

        super.onPause();
        if (QavsdkControl.getInstance() != null)
            if (QavsdkControl.getInstance().getAVContext() != null)
                QavsdkControl.getInstance().getAVContext().getAudioCtrl().enableSpeaker(false);
        if (mLiveHelper != null)
            mLiveHelper.pause();
        if (QavsdkControl.getInstance() != null)
            QavsdkControl.getInstance().onPause();


        Log.i(TAG, "onPause: 结束直播");


        isending = true;


        EndLiving2();

    }

    @Override
    public void addaudience(int type) {

        if (type == 100) {
            Toast.makeText(mContext, "成功关注", Toast.LENGTH_SHORT).show();
            audience.setText("取消关注");
            isAudience = true;
        } else if (type == 301) {
            Toast.makeText(mContext, "已经关注", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void cancelaudien(int type) {
        if (type == 100) {
            Toast.makeText(mContext, "取消关注", Toast.LENGTH_SHORT).show();
            audience.setText("关注");
            isAudience = false;
        } else {
            Toast.makeText(mContext, "操作失败", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void audienguanx(int type) {
        if (type == 304) {
            isAudience = true;
            audience.setText("取消关注");
        } else if (type == 306) {
            isAudience = true;
            audience.setText("取消关注");
        } else if (type == 100) {
            isAudience = false;
            audience.setText("关注");
        }

//        if (type==304)
//            isAudience=true;
//            audience.setText("取消关注");
//        if (type==306)
//            isAudience=true;
//            audience.setText("取消关注");
//        if (type==100)
//            isAudience=false;
//            audience.setText("关注");

    }

    @Override
    public void getConcernList(List<TIMUserProfile> timUserProfiles) {
    }

    @Override
    public void getByConernList(List<TIMUserProfile> timUserProfiles) {
    }


    //阳
    @Override
    public void RestartSuccess() {
        Log.i(TAG, "onResume:  重新开始直播回调成功");
    }

    @Override
    public void RestartFail() {
        Log.i(TAG, "onResume:  重新开始直播回调失败");
    }


    /**
     * 直播心跳
     */
    private class HeartBeatTask extends TimerTask {
        @Override
        public void run() {
            String host = CurLiveInfo.getHostID();
            SxbLog.i(TAG, "HeartBeatTask " + host);
            OKhttpHelper.getInstance().sendHeartBeat(host, CurLiveInfo.getMembers(), CurLiveInfo.getAdmires(), 0);
        }
    }

    /**
     * 记时器
     */
    private class VideoTimerTask extends TimerTask {

        public void run() {
            SxbLog.i(TAG, "timeTask ");
            ++mSecond;
            if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST)
                mHandler.sendEmptyMessage(UPDAT_WALL_TIME_TIMER_TASK);
        }
    }

    @Override
    protected void onDestroy() {
        stopOrientationListener();

        watchCount = 0;
        super.onDestroy();
        if (null != mHearBeatTimer) {
            mHearBeatTimer.cancel();
            mHearBeatTimer = null;
        }
        if (null != mVideoTimer) {
            mVideoTimer.cancel();
            mVideoTimer = null;
        }
        if (null != paramTimer) {
            paramTimer.cancel();
            paramTimer = null;
        }

//        giftHelper.onDestory();
        Log.i(TAG, "onDestroy: Activity 销毁");
        inviteViewCount = 0;
        thumbUp = 0;
        CurLiveInfo.setMembers(0);
        CurLiveInfo.setAdmires(0);
        CurLiveInfo.setCurrentRequestCount(0);
        unregisterReceiver();
        EndLiving();


        mLiveHelper.onDestory();
//        mEnterRoomHelper.onDestory();
        QavsdkControl.getInstance().clearVideoMembers();
        QavsdkControl.getInstance().onDestroy();
        OkGo.getInstance().cancelTag(this);


    }


    /**
     * 点击Back键
     */
    @Override
    public void onBackPressed() {
        if (bInAvRoom) {
            bDelayQuit = false;
            quiteLiveByPurpose();
        } else {
            finish();
        }
    }

    /**
     * 主动退出直播
     */
    private void quiteLiveByPurpose() {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            initBackDialog();
        } else {
            mLiveHelper.perpareQuitRoom(true);
//            mEnterRoomHelper.quiteLive();
        }
    }


    private Dialog backDialog;

    private void initBackDialog() {

        new BackDialog(this).builder().setTitle("确定直播结束？").setNegativeButton("取消",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                }).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mLiveHelper) {
                    Log.i("back", "onResponse: " + "准备退出直播");
                    if (isPushed) {
                        mLiveHelper.stopPushAction();
                    }

                    if (mRecord) {
                        mLiveHelper.stopRecord();

                    }

                    mLiveHelper.perpareQuitRoom(true);
                    EndLiving();


                }
            }
        }).show();
    }

    /**
     * 被动退出直播
     */
    private void quiteLivePassively() {
        Toast.makeText(this, "Host leave Live ", Toast.LENGTH_SHORT).show();
        mLiveHelper.perpareQuitRoom(false);
//        mEnterRoomHelper.quiteLive();
    }

    @Override
    public void readyToQuit() {
        mEnterRoomHelper.quiteLive();
        //mEnterRoomHelper.justQuiteIMChatRoom(roomid + "");

        //不需要退出im

//        mEnterRoomHelper.justQuiteIMChatRoom(roomid + "");

    }

    /**
     * 完成进出房间流程
     *
     * @param id_status
     * @param isSucc
     */
    @Override
    public void enterRoomComplete(int id_status, boolean isSucc) {
//        Toast.makeText(LiveActivity.this, "EnterRoom  " + id_status + " isSucc " + isSucc, Toast.LENGTH_SHORT).show();
        //必须得进入房间之后才能初始化UI
        mEnterRoomHelper.initAvUILayer(avView);
        QavsdkControl.getInstance().setSlideListener(this);
        bInAvRoom = true;
        bDelayQuit = true;
        updateHostLeaveLayout();

        //设置预览回调，修正摄像头镜像
        mLiveHelper.setCameraPreviewChangeCallback();
        if (isSucc == true) {
            //IM初始化
            mLiveHelper.initTIMListener("" + CurLiveInfo.getRoomNum());

            if (id_status == Constants.HOST) {//主播方式加入房间成功
                //开启摄像头渲染画面
                mLiveHelper.openCameraAndMic();
                SxbLog.i(TAG, "createlive enterRoomComplete isSucc" + isSucc);
            } else {
                //发消息通知上线
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_EnterLive, "");
            }
        }
    }


    @Override
    public void quiteRoomComplete(int id_status, boolean succ, LiveInfoJson liveinfo) {
        bInAvRoom = false;
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            if ((getBaseContext() != null) && (null != mDetailDialog) && (mDetailDialog.isShowing() == false)) {
                SxbLog.d(TAG, LogConstants.ACTION_HOST_QUIT_ROOM + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "quite room callback"
                        + LogConstants.DIV + LogConstants.STATUS.SUCCEED + LogConstants.DIV + "id status " + id_status);
                mDetailTime.setText(formatTime);
                mDetailAdmires.setText("" + CurLiveInfo.getAdmires());
                mDetailWatchCount.setText("" + watchCount);
                mDetailDialog.show();
            }
        } else {
            //finish();
            if (bDelayQuit) {
                clearOldData();
                updateHostLeaveLayout();
            } else {
                finish();
            }
        }
    }

    public void EndLiving2() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("", "");
        //params.put("roomId", Constant.getUser().getId());//mingtiangai
        OkGo.post(HttpUrl.endlive)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int resultCode = object.getInt("resultCode");
                            if (resultCode == 100) {
                                Log.i(TAG, "onSuccess:EndLiving2 直播结束");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onError(Call call, Response response, Exception e) {
                    }
                });


    }

    public void EndLiving() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("", "");
        //params.put("roomId", Constant.getUser().getId());//mingtiangai
        OkGo.post(HttpUrl.endlive)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int resultCode = object.getInt("resultCode");
                            if (resultCode == 100) {
                                LiveActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onError(Call call, Response response, Exception e) {
                    }
                });


//        OkHttpClientManager.getAsyn(HttpUrl.endlive, new OkHttpClientManager.StringCallback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//            }
//
//            @Override
//            public void onResponse(String response) {
////                mLiveView.EndLiveCallback();
//                try {
//                    JSONObject object = new JSONObject(response);
//                    int resultCode = object.getInt("resultCode");
//                    if (resultCode == 100) {
//                        LiveActivity.this.finish();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


    private TextView mDetailTime, mDetailAdmires, mDetailWatchCount;

    private void initDetailDailog() {
        mDetailDialog = new Dialog(this, R.style.dialog);
        mDetailDialog.setContentView(R.layout.dialog_live_detail);
        mDetailTime = (TextView) mDetailDialog.findViewById(R.id.tv_time);
        mDetailAdmires = (TextView) mDetailDialog.findViewById(R.id.tv_admires);
        mDetailWatchCount = (TextView) mDetailDialog.findViewById(R.id.tv_members);
        mDetailDialog.setCancelable(false);
        TextView tvCancel = (TextView) mDetailDialog.findViewById(R.id.btn_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
                finish();
            }
        });
//        mDetailDialog.show();
    }

    /**
     * 成员状态变更
     *
     * @param id
     * @param name
     */
    @Override
    public void memberJoin(String id, String name) {
        Log.i(TAG, "成员加入时候的监听方法");
        SxbLog.d(TAG, LogConstants.ACTION_VIEWER_ENTER_ROOM + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "on member join" +
                LogConstants.DIV + "join room " + id);

        watchCount++;
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "进入了", Constants.MEMBER_ENTER);
        CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1);

//        tvMembers.setText("" + CurLiveInfo.getMembers());
    }

    @Override
    public void memberQuit(String id, String name) {

        Log.i(TAG, "成员退出时候的监听方法");
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "离开房间", Constants.MEMBER_EXIT);

        if (CurLiveInfo.getMembers() > 1) {
            CurLiveInfo.setMembers(CurLiveInfo.getMembers() - 1);
//            tvMembers.setText("" + CurLiveInfo.getMembers());
        }

        //如果存在视频互动，取消
        QavsdkControl.getInstance().closeMemberView(id);
    }

    @Override
    public void hostLeave(String id, String name) {
        //refreshTextListView(TextUtils.isEmpty(name) ? id : name, "主播离开", Constants.HOST_LEAVE);
    }

    @Override
    public void hostBack(String id, String name) {
        // refreshTextListView(TextUtils.isEmpty(name) ? id : name, "主播回来了", Constants.HOST_BACK);
    }

    @Override
    public void giftCallback(int resultCode) {

        Log.i(TAG, "giftCallback: 进入回调方法");
        Log.i(TAG, "giftCallback: 进入回调的resultCode是" + resultCode);


        //样子
        if (resultCode == 100) {
            isGiftBalance = true;
            Log.i(TAG, "giftCallback: 余额 " + Constant.getUser().getBalance());
            live_user_balance.setText(Constant.getUser().getBalance());
        } else if (resultCode == 303) {
            Toast.makeText(LiveActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LiveActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void EndLiveCallback() {
        this.finish();
    }

    /**
     * 有成员退群
     *
     * @param list 成员ID 列表
     */
    @Override
    public void memberQuiteLive(String[] list) {
        if (list == null) return;
        for (String id : list) {
            SxbLog.i(TAG, "memberQuiteLive id " + id);
            if (CurLiveInfo.getHostID().equals(id)) {
                if (MySelfInfo.getInstance().getIdStatus() == Constants.MEMBER)
                    quiteLivePassively();
            }
        }
    }


    /**
     * 有成员入群
     *
     * @param list 成员ID 列表
     */
    @Override
    public void memberJoinLive(final String[] list) {
    }

    @Override
    public void alreadyInLive(String[] list) {
        for (String id : list) {
            if (id.equals(MySelfInfo.getInstance().getId())) {
                QavsdkControl.getInstance().setSelfId(MySelfInfo.getInstance().getId());
                QavsdkControl.getInstance().setLocalHasVideo(true, MySelfInfo.getInstance().getId());
            } else {
                QavsdkControl.getInstance().setRemoteHasVideo(true, id, AVView.VIDEO_SRC_TYPE_CAMERA);
            }
        }
    }


    List<TIMProfie> profilelist = new ArrayList<>();

    @Override
    public void memberface(List<TIMUserProfile> timUserProfiles) {
        profilelist.clear();
        live_frofile_item_layout.removeAllViews();
        for (int i = 0; i < timUserProfiles.size(); i++) {
            TIMUserProfile userfile = timUserProfiles.get(i);
            TIMProfie profile = new TIMProfie();
            profile.setNickName(userfile.getNickName());
            profile.setFaceUrl(userfile.getFaceUrl());
            profile.setRemark(userfile.getRemark());
            profile.setIdentifier(userfile.getIdentifier());
            profilelist.add(profile);
        }

        Log.i("live", "profilelist 大小是" + profilelist.size());
        tvMembers.setText(profilelist.size() + "");
//        live_frofile_item_layout.removeAllViews();
        for (int i = 0; i < profilelist.size(); i++) {
            TIMProfie myprofile = profilelist.get(i);
            View view = LayoutInflater.from(this).inflate(R.layout.live_profile_item_face, null);
            RoundImageView img = (RoundImageView) view.findViewById(R.id.live_profile_item_face_img);
            String imgurl = myprofile.getFaceUrl();
            if (imgurl != null && !imgurl.equals("")) {
                Glide.with(this).load(imgurl).into(img);
                live_frofile_item_layout.addView(view);
            }
        }


       /* TIMUserProfile timUserProfil=timUserProfiles.get(0);*/

        for (int i = 0; i < timUserProfiles.size(); i++) {
            TIMUserProfile timUserProfil = timUserProfiles.get(i);
            if (timUserProfil.getIdentifier().equals(String.valueOf(roomid))) {

                String dizhi = timUserProfil.getLocation();
                TLurl.setUrl(dizhi);
                Log.e("ccurl", dizhi);

            }

        }


    }


    /**
     * 红点动画
     */
    private void startRecordAnimation() {
        mObjAnim = ObjectAnimator.ofFloat(mRecordBall, "alpha", 1f, 0f, 1f);
        mObjAnim.setDuration(1000);
        mObjAnim.setRepeatCount(-1);
        mObjAnim.start();
    }

    private static int index = 0;

    /**
     * 加载视频数据
     *
     * @param isLocal 是否是本地数据
     * @param id      身份
     */
    @Override
    public void showVideoView(boolean isLocal, String id) {
        SxbLog.i(TAG, "showVideoView " + id);

        //渲染本地Camera
        if (isLocal == true) {
            SxbLog.i(TAG, "showVideoView host :" + MySelfInfo.getInstance().getId());
            QavsdkControl.getInstance().setSelfId(MySelfInfo.getInstance().getId());
            QavsdkControl.getInstance().setLocalHasVideo(true, MySelfInfo.getInstance().getId());

            //主播通知用户服务器
            if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
                if (bFirstRender) {
                    mEnterRoomHelper.notifyServerCreateRoom();

                    //主播心跳
                    mHearBeatTimer = new Timer(true);
                    mHeartBeatTask = new HeartBeatTask();
                    mHearBeatTimer.schedule(mHeartBeatTask, 1000, 3 * 1000);

                    //直播时间
                    mVideoTimer = new Timer(true);
                    mVideoTimerTask = new VideoTimerTask();
                    mVideoTimer.schedule(mVideoTimerTask, 1000, 1000);
                    bFirstRender = false;
                }
            }
        } else {
            // QavsdkControl.getInstance().addRemoteVideoMembers(id);
            QavsdkControl.getInstance().setRemoteHasVideo(true, id, AVView.VIDEO_SRC_TYPE_CAMERA);
        }

    }


    private float getBeautyProgress(int progress) {
        SxbLog.d("shixu", "progress: " + progress);
        return (9.0f * progress / 100.0f);
    }


    @Override
    public void showInviteDialog() {
        if ((inviteDg != null) && (getBaseContext() != null) && (inviteDg.isShowing() != true)) {
            inviteDg.show();
        }
    }

    @Override
    public void hideInviteDialog() {
        if ((inviteDg != null) && (inviteDg.isShowing() == true)) {
            inviteDg.dismiss();
        }
    }


    @Override
    public void refreshText(String text, String name) {
        Log.i("git", "进入发送单个消息的回调listview 方法");
        if (text != null) {
            refreshTextListView(name, text, Constants.TEXT_TYPE);
        }
//        setimgAnimTion("11");
    }

    //阳
    @Override
    public void refreshgift(String giftname, String username, String id) {

        Log.i("git", "接收消息的回调" + giftname + "id是" + id);
        //只是启动动画没有更新listview 消息内容
        if (giftname != null) {
            refreshTextListView(username, giftname, Constants.TEXT_TYPE);
        }


        if (id.equals("18") || id.equals("19") || id.equals("20") || id.equals("21")) {
            setimgAnimTion(id, anim_img);
        } else {
            setimgAnimTion(id, live_gift_img);
        }

        Log.i("git", "启动动画完成");

    }

    @Override
    public void refreshThumbUp() {
        CurLiveInfo.setAdmires(CurLiveInfo.getAdmires() + 1);
        if (!bCleanMode) {      // 纯净模式下不播放飘星动画
            mHeartLayout.addFavor();
        }
        tvAdmires.setText("" + CurLiveInfo.getAdmires());
    }

    @Override
    public void refreshUI(String id) {
        //当主播选中这个人，而他主动退出时需要恢复到正常状态
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST)
            if (!backGroundId.equals(CurLiveInfo.getHostID()) && backGroundId.equals(id)) {
                backToNormalCtrlView();
            }
    }


    private int inviteViewCount = 0;

    @Override
    public boolean showInviteView(String id) {
        SxbLog.d(TAG, LogConstants.ACTION_VIEWER_SHOW + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "invite up show" +
                LogConstants.DIV + "id " + id);
        int index = QavsdkControl.getInstance().getAvailableViewIndex(1);
        if (index == -1) {
            Toast.makeText(LiveActivity.this, "the invitation's upper limit is 3", Toast.LENGTH_SHORT).show();
            return false;
        }
        int requetCount = index + inviteViewCount;
        if (requetCount > 3) {
            Toast.makeText(LiveActivity.this, "the invitation's upper limit is 3", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hasInvited(id)) {
            Toast.makeText(LiveActivity.this, "it has already invited", Toast.LENGTH_SHORT).show();
            return false;
        }
        switch (requetCount) {
            case 1:
                inviteView1.setText(id);
                inviteView1.setVisibility(View.VISIBLE);
                inviteView1.setTag(id);

                break;
            case 2:
                inviteView2.setText(id);
                inviteView2.setVisibility(View.VISIBLE);
                inviteView2.setTag(id);
                break;
            case 3:
                inviteView3.setText(id);
                inviteView3.setVisibility(View.VISIBLE);
                inviteView3.setTag(id);
                break;
        }
        mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MUlTI_HOST_INVITE, "", id);
        inviteViewCount++;
        //30s超时取消
        Message msg = new Message();
        msg.what = TIMEOUT_INVITE;
        msg.obj = id;
        mHandler.sendMessageDelayed(msg, 30 * 1000);
        return true;
    }


    /**
     * 判断是否邀请过同一个人
     *
     * @param id
     * @return
     */
    private boolean hasInvited(String id) {
        if (id.equals(inviteView1.getTag())) {
            return true;
        }
        if (id.equals(inviteView2.getTag())) {
            return true;
        }
        if (id.equals(inviteView3.getTag())) {
            return true;
        }
        return false;
    }

    @Override
    public void cancelInviteView(String id) {
        if ((inviteView1 != null) && (inviteView1.getTag() != null)) {
            if (inviteView1.getTag().equals(id)) {
            }
            if (inviteView1.getVisibility() == View.VISIBLE) {
                inviteView1.setVisibility(View.INVISIBLE);
                inviteView1.setTag("");
                inviteViewCount--;
            }
        }

        if (inviteView2 != null && inviteView2.getTag() != null) {
            if (inviteView2.getTag().equals(id)) {
                if (inviteView2.getVisibility() == View.VISIBLE) {
                    inviteView2.setVisibility(View.INVISIBLE);
                    inviteView2.setTag("");
                    inviteViewCount--;
                }
            } else {
                Log.i(TAG, "cancelInviteView inviteView2 is null");
            }
        } else {
            Log.i(TAG, "cancelInviteView inviteView2 is null");
        }

        if (inviteView3 != null && inviteView3.getTag() != null) {
            if (inviteView3.getTag().equals(id)) {
                if (inviteView3.getVisibility() == View.VISIBLE) {
                    inviteView3.setVisibility(View.INVISIBLE);
                    inviteView3.setTag("");
                    inviteViewCount--;
                }
            } else {
                Log.i(TAG, "cancelInviteView inviteView3 is null");
            }
        } else {
            Log.i(TAG, "cancelInviteView inviteView3 is null");
        }
    }

    @Override
    public void cancelMemberView(String id) {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
        } else {
            //TODO 主动下麦 下麦；
            SxbLog.d(TAG, LogConstants.ACTION_VIEWER_UNSHOW + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "start unShow" +
                    LogConstants.DIV + "id " + id);
            mLiveHelper.changeAuthandRole(false, Constants.NORMAL_MEMBER_AUTH, Constants.NORMAL_MEMBER_ROLE);
//            mLiveHelper.closeCameraAndMic();//是自己成员关闭
        }
        mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, id);
        QavsdkControl.getInstance().closeMemberView(id);
        backToNormalCtrlView();
    }


    private void showReportDialog() {
        final Dialog reportDialog = new Dialog(this, R.style.report_dlg);
        reportDialog.setContentView(R.layout.dialog_live_report);

        TextView tvReportDirty = (TextView) reportDialog.findViewById(R.id.btn_dirty);
        TextView tvReportFalse = (TextView) reportDialog.findViewById(R.id.btn_false);
        TextView tvReportVirus = (TextView) reportDialog.findViewById(R.id.btn_virus);
        TextView tvReportIllegal = (TextView) reportDialog.findViewById(R.id.btn_illegal);
        TextView tvReportYellow = (TextView) reportDialog.findViewById(R.id.btn_yellow);
        TextView tvReportCancel = (TextView) reportDialog.findViewById(R.id.btn_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    default:
                        reportDialog.cancel();
                        break;
                }
            }
        };

        tvReportDirty.setOnClickListener(listener);
        tvReportFalse.setOnClickListener(listener);
        tvReportVirus.setOnClickListener(listener);
        tvReportIllegal.setOnClickListener(listener);
        tvReportYellow.setOnClickListener(listener);
        tvReportCancel.setOnClickListener(listener);

        reportDialog.setCanceledOnTouchOutside(true);
        reportDialog.show();
    }

    private void showHostDetail() {
        Dialog hostDlg = new Dialog(this, R.style.host_info_dlg);
        hostDlg.setContentView(R.layout.host_info_layout);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window dlgwin = hostDlg.getWindow();
        WindowManager.LayoutParams lp = dlgwin.getAttributes();
        dlgwin.setGravity(Gravity.TOP);
        lp.width = (int) (display.getWidth()); //设置宽度

        hostDlg.getWindow().setAttributes(lp);
        hostDlg.show();

        TextView tvHost = (TextView) hostDlg.findViewById(R.id.tv_host_name);
        tvHost.setText(CurLiveInfo.getHostName());
        ImageView ivHostIcon = (ImageView) hostDlg.findViewById(R.id.iv_host_icon);
        showHeadIcon(ivHostIcon, CurLiveInfo.getHostAvator());
        TextView tvLbs = (TextView) hostDlg.findViewById(R.id.tv_host_lbs);
        tvLbs.setText(UIUtils.getLimitString(CurLiveInfo.getAddress(), 6));
        ImageView ivReport = (ImageView) hostDlg.findViewById(R.id.iv_report);
        ivReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportDialog();
            }
        });
    }

    private boolean checkInterval() {
        if (0 == admireTime) {
            admireTime = System.currentTimeMillis();
            return true;
        }
        long newTime = System.currentTimeMillis();
        if (newTime >= admireTime + 1000) {
            admireTime = newTime;
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
//                quiteLiveByPurpose();
                onBackPressed();
                break;

            case R.id.message_input:
                //发送消息
                inputMsgDialog();
                break;

            case R.id.send_good_two:
                layout_dl.setVisibility(View.VISIBLE);
                send_good_two.setVisibility(View.GONE);
                break;

            //关注
            case R.id.relat_guanzhu:
                //关注调借口
                //guangzhu();
                relat_guanzhu.setVisibility(View.GONE);
                break;
            case R.id.member_send_good:
                mHeartLayout.addFavor();
                if (checkInterval()) {
                    mLiveHelper.sendC2CMessage(Constants.AVIMCMD_Praise, "", CurLiveInfo.getHostID());
                    CurLiveInfo.setAdmires(CurLiveInfo.getAdmires() + 1);
                    tvAdmires.setText("" + CurLiveInfo.getAdmires());

                } else {
                    //Toast.makeText(this, getString(R.string.text_live_admire_limit), Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.flash_btn:
                if (mLiveHelper.isFrontCamera() == true) {
                    Toast.makeText(LiveActivity.this, "this is front cam", Toast.LENGTH_SHORT).show();
                } else {
                    mLiveHelper.toggleFlashLight();
                }
                break;
            case R.id.switch_cam:
                mLiveHelper.switchCamera();
                break;
            case R.id.mic_btn:
                if (mLiveHelper.isMicOpen() == true) {
                    BtnMic.setImageResource(R.drawable.prohibit_mic);
                    mLiveHelper.muteMic();
                } else {
                    BtnMic.setImageResource(R.drawable.prohibit2);
                    mLiveHelper.openMic();
                }
                break;

            case R.id.pinlun:
                inputMsgDialog();
                break;
            case R.id.share_pt:
                // tuiliu();

              /*  if(!TextUtils.isEmpty(TLurl.getUrl())){
                    getfenxiangdata();
                }*/

                if (!mRecord) {
                    if (recordDialog != null)
                        recordDialog.show();
                } else {
                    mLiveHelper.stopRecord();
                }


                break;
            case R.id.send_good:
                //显示美白美颜
                layout_dl.setVisibility(View.GONE);
                send_good_two.setVisibility(View.VISIBLE);

                // MyliwuView();
                break;
            case R.id.head_up_layout:
                //showHostDetail();
                break;
            case R.id.clean_screen:
                isVisility = true;
                mNomalMemberCtrView.setVisibility(View.GONE);
                Linear_gift.setVisibility(View.VISIBLE);
                Realitv.setVisibility(View.VISIBLE);
                Log.i(TAG, "设置余额" + Constant.getUser().getBalance());
                live_user_balance.setText(Constant.getUser().getBalance());
                //new ShareDialoy(LiveActivity.this).builder().show();
               /* mHeartLayout.addFavor();
                if (checkInterval()) {
                    mLiveHelper.sendC2CMessage(Constants.AVIMCMD_Praise, "", CurLiveInfo.getHostID());
                    CurLiveInfo.setAdmires(CurLiveInfo.getAdmires() + 1);
                    tvAdmires.setText("" + CurLiveInfo.getAdmires());
                } else {
                    //Toast.makeText(this, getString(R.string.text_live_admire_limit), Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.fullscreen_btn:
                bCleanMode = true;
                mFullControllerUi.setVisibility(View.INVISIBLE);
                BtnNormal.setVisibility(View.VISIBLE);
                break;
            case R.id.normal_btn:
                bCleanMode = false;
                mFullControllerUi.setVisibility(View.VISIBLE);
                BtnNormal.setVisibility(View.GONE);
                break;

           /* case R.id.video_interact:
                mMemberDg.setCanceledOnTouchOutside(true);
                mMemberDg.show();
                break;*/
            case R.id.camera_controll:
                Toast.makeText(LiveActivity.this, "切换" + backGroundId + "camrea 状态", Toast.LENGTH_SHORT).show();
                if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//自己关闭自己
                    mLiveHelper.toggleCamera();
                } else {
                    mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MULTI_HOST_CONTROLL_CAMERA, backGroundId, backGroundId);//主播关闭自己
                }
                break;
            case R.id.mic_controll:
                Toast.makeText(LiveActivity.this, "切换" + backGroundId + "mic 状态", Toast.LENGTH_SHORT).show();
                if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//自己关闭自己
                    mLiveHelper.toggleMic();
                } else {
                    mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MULTI_HOST_CONTROLL_MIC, backGroundId, backGroundId);//主播关闭自己
                }
                break;
            case R.id.close_member_video://主动关闭成员摄像头
                cancelMemberView(backGroundId);
                break;
            case R.id.beauty_btn:
                Log.i(TAG, "onClick " + mBeautyRate);
                mProfile = mBeatuy;
                if (mBeautySettings != null) {
                    if (mBeautySettings.getVisibility() == View.GONE) {
                        mBeautySettings.setVisibility(View.VISIBLE);
                        mFullControllerUi.setVisibility(View.INVISIBLE);
                        mBeautyBar.setProgress(mBeautyRate);
                    } else {
                        mBeautySettings.setVisibility(View.GONE);
                        mFullControllerUi.setVisibility(View.VISIBLE);
                    }
                } else {
                    SxbLog.i(TAG, "beauty_btn mTopBar  is null ");
                }
                break;

            case R.id.white_btn:
                Log.i(TAG, "onClick " + mWhiteRate);
                mProfile = mWhite;
                if (mBeautySettings != null) {
                    if (mBeautySettings.getVisibility() == View.GONE) {
                        mBeautySettings.setVisibility(View.VISIBLE);
                        mFullControllerUi.setVisibility(View.INVISIBLE);
                        mBeautyBar.setProgress(mWhiteRate);
                    } else {
                        mBeautySettings.setVisibility(View.GONE);
                        mFullControllerUi.setVisibility(View.VISIBLE);
                    }
                } else {
                    SxbLog.i(TAG, "beauty_btn mTopBar  is null ");
                }
                break;
            case R.id.qav_beauty_setting_finish:
                mBeautySettings.setVisibility(View.GONE);
                mFullControllerUi.setVisibility(View.VISIBLE);
                break;
            case R.id.invite_view1:
                inviteView1.setVisibility(View.INVISIBLE);
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView1.getTag());
                break;
            case R.id.invite_view2:
                inviteView2.setVisibility(View.INVISIBLE);
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView2.getTag());
                break;
            case R.id.invite_view3:
                inviteView3.setVisibility(View.INVISIBLE);
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView3.getTag());
                break;
            case R.id.param_video:
                showTips = !showTips;
                break;
            case R.id.push_btn:
                pushStream();
                break;
            case R.id.record_btn:
                if (!mRecord) {
                    if (recordDialog != null)
                        recordDialog.show();
                } else {
                    mLiveHelper.stopRecord();
                }
                break;
            case R.id.speed_test_btn:
                // new SpeedTestDialog(this).start();
                break;
            case R.id.audience:
                //阳
                if (!isAudience) {
                    audienceHelper.addaudience(roomid);
                } else {
                    audienceHelper.CancelAudience(roomid);
                }

                break;

            case R.id.share_pt_member:

                getfenxiangdata();
                break;
        }
    }


    //for 测试获取测试参数
    private boolean showTips = false;
    private TextView tvTipsMsg;
    Timer paramTimer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (showTips) {
                        if (tvTipsMsg != null) {
                            String strTips = QavsdkControl.getInstance().getQualityTips();
                            strTips = praseString(strTips);
                            if (!TextUtils.isEmpty(strTips)) {
                                tvTipsMsg.setText(strTips);
                            }
                        }
                    } else {
                        tvTipsMsg.setText("");
                    }
                }
            });
        }
    };


    //for 测试 解析参数
    private String praseString(String video) {
        if (video.length() == 0) {
            return "";
        }
        String result = "";
        String splitItems[];
        String tokens[];
        splitItems = video.split("\\n");
        for (int i = 0; i < splitItems.length; ++i) {
            if (splitItems[i].length() < 2)
                continue;
            tokens = splitItems[i].split(":");
            if (tokens[0].length() == "mainVideoSendSmallViewQua".length()) {
                continue;
            }
            if (tokens[0].endsWith("BigViewQua")) {
                tokens[0] = "mainVideoSendViewQua";
            }
            if (tokens[0].endsWith("BigViewQos")) {
                tokens[0] = "mainVideoSendViewQos";
            }
            result += tokens[0] + ":\n" + "\t\t";
            for (int j = 1; j < tokens.length; ++j)
                result += tokens[j];
            result += "\n\n";
            //Log.d(TAG, "test:" + result);
        }
        //Log.d(TAG, "test:" + result);

        return result;

    }

    private void backToNormalCtrlView() {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            backGroundId = CurLiveInfo.getHostID();
            mHostCtrView.setVisibility(View.VISIBLE);
            mVideoMemberCtrlView.setVisibility(View.GONE);
        } else {
            backGroundId = CurLiveInfo.getHostID();
            mVideoMemberCtrlView.setVisibility(View.GONE);
        }
    }

    /**
     * 发消息弹出框
     */
    private void inputMsgDialog() {
        InputTextMsgDialog inputMsgDialog = new InputTextMsgDialog(this, R.style.inputdialog, mLiveHelper);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = inputMsgDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        inputMsgDialog.getWindow().setAttributes(lp);
        inputMsgDialog.setCancelable(true);
        inputMsgDialog.show();

    }


    /**
     * 主播邀请应答框
     */
    private void initInviteDialog() {
        inviteDg = new Dialog(this, R.style.dialog);
        inviteDg.setContentView(R.layout.invite_dialog);
        TextView hostId = (TextView) inviteDg.findViewById(R.id.host_id);
        hostId.setText(CurLiveInfo.getHostID());
        TextView agreeBtn = (TextView) inviteDg.findViewById(R.id.invite_agree);
        TextView refusebtn = (TextView) inviteDg.findViewById(R.id.invite_refuse);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mVideoMemberCtrlView.setVisibility(View.VISIBLE);
                SxbLog.d(TAG, LogConstants.ACTION_VIEWER_SHOW + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "accept invite" +
                        LogConstants.DIV + "host id " + CurLiveInfo.getHostID());
                //上麦 ；TODO 上麦 上麦 上麦 ！！！！！；
                mLiveHelper.changeAuthandRole(true, Constants.VIDEO_MEMBER_AUTH, Constants.VIDEO_MEMBER_ROLE);
                inviteDg.dismiss();
            }
        });

        refusebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MUlTI_REFUSE, "", CurLiveInfo.getHostID());
                inviteDg.dismiss();
            }
        });

        Window dialogWindow = inviteDg.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }


    /**
     * 消息刷新显示
     *
     * @param name    发送者
     * @param context 内容
     * @param type    类型 （上线线消息和 聊天消息）
     */
    public void refreshTextListView(String name, String context, int type) {

        ChatEntity entity = new ChatEntity();
        entity.setSenderName(name);
        entity.setContext(context);
        entity.setType(type);
        //mArrayListChatEntity.add(entity);
        notifyRefreshListView(entity);
        //mChatMsgListAdapter.notifyDataSetChanged();
        mListViewMsgItems.setVisibility(View.VISIBLE);
        SxbLog.d(TAG, "refreshTextListView height " + mListViewMsgItems.getHeight());

        if (mListViewMsgItems.getCount() > 1) {
            if (true)
                mListViewMsgItems.setSelection(0);
            else
                mListViewMsgItems.setSelection(mListViewMsgItems.getCount() - 1);
        }
    }


    /**
     * 通知刷新消息ListView
     */
    private void notifyRefreshListView(ChatEntity entity) {
        mBoolNeedRefresh = true;
        mTmpChatList.add(entity);
        if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }
    }


    /**
     * 刷新ListView并重置状态
     */
    private void doRefreshListView() {
        if (mBoolNeedRefresh) {
            mBoolRefreshLock = true;
            mBoolNeedRefresh = false;
            mArrayListChatEntity.addAll(mTmpChatList);
            mTmpChatList.clear();
            mChatMsgListAdapter.notifyDataSetChanged();

            if (null != mTimerTask) {
                mTimerTask.cancel();
            }
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    SxbLog.v(TAG, "doRefreshListView->task enter with need:" + mBoolNeedRefresh);
                    mHandler.sendEmptyMessage(REFRESH_LISTVIEW);
                }
            };
            //mTimer.cancel();
            mTimer.schedule(mTimerTask, MINFRESHINTERVAL);
        } else {
            mBoolRefreshLock = false;
        }
    }

    @Override
    public void updateProfileInfo(TIMUserProfile profile) {
    }

    @Override
    public void updateUserInfo(int requestCode, List<TIMUserProfile> profiles) {
        if (null != profiles) {
            switch (requestCode) {
                case GETPROFILE_JOIN:
                    for (TIMUserProfile user : profiles) {
//                        tvMembers.setText("" + CurLiveInfo.getMembers());
                        SxbLog.w(TAG, "get nick name:" + user.getNickName());
                        SxbLog.w(TAG, "get remark name:" + user.getRemark());
                        SxbLog.w(TAG, "get avatar:" + user.getFaceUrl());
                        if (!TextUtils.isEmpty(user.getNickName())) {
                            //TODO 修改名称显示
                            refreshTextListView(user.getNickName(), "join live", Constants.MEMBER_ENTER);
                        } else {
                            refreshTextListView(user.getIdentifier(), "join live", Constants.MEMBER_ENTER);
                        }
                    }
                    break;
            }
        }
    }


    /**
     * 旁路直播 退出房间时必须退出推流。否则会占用后台channel。
     */
    public void pushStream() {
        if (!isPushed) {
            if (mPushDialog != null)
                mPushDialog.show();
        } else {
            mLiveHelper.stopPushAction();
        }
    }

    private Dialog mPushDialog;

    private void initPushDialog() {
        mPushDialog = new Dialog(this, R.style.dialog);
        mPushDialog.setContentView(R.layout.push_dialog_layout);
        final TIMAvManager.StreamParam mStreamParam = TIMAvManager.getInstance().new StreamParam();
        final EditText pushfileNameInput = (EditText) mPushDialog.findViewById(R.id.push_filename);
        final RadioGroup radgroup = (RadioGroup) mPushDialog.findViewById(R.id.push_type);


        Button recordOk = (Button) mPushDialog.findViewById(R.id.btn_record_ok);
        recordOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (pushfileNameInput.getText().toString().equals("")) {
                    Toast.makeText(LiveActivity.this, "name can't be empty", Toast.LENGTH_SHORT);
                    return;
                } else {

                }*/

                mStreamParam.setChannelName(CurLiveInfo.getHostName());


                if (radgroup.getCheckedRadioButtonId() == R.id.hls) {
                    mStreamParam.setEncode(TIMAvManager.StreamEncode.HLS);
                } else {
                    mStreamParam.setEncode(TIMAvManager.StreamEncode.RTMP);
                }
//                mStreamParam.setEncode(TIMAvManager.StreamEncode.HLS);

                mPushDialog.dismiss();
            }
        });


        Button recordCancel = (Button) mPushDialog.findViewById(R.id.btn_record_cancel);
        recordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPushDialog.dismiss();
            }
        });

        Window dialogWindow = mPushDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        mPushDialog.setCanceledOnTouchOutside(false);


    }

    /*
    * 开启推流
    * */

    public void tuiliu() {
        final TIMAvManager.StreamParam mStreamParam = TIMAvManager.getInstance().new StreamParam();
        mStreamParam.setChannelName(CurLiveInfo.getHostName());
        mStreamParam.setEncode(TIMAvManager.StreamEncode.RTMP);
        mLiveHelper.pushAction(mStreamParam);
    }


    public void tuiliuchenggong() {
        final TIMAvManager.StreamParam mStreamParam = TIMAvManager.getInstance().new StreamParam();
        mStreamParam.setChannelName("yanghuangcaijing");
        mStreamParam.setEncode(TIMAvManager.StreamEncode.RTMP);
        mLiveHelper.pushAction(mStreamParam);

    }


    /**
     * 推流成功
     *
     * @param streamRes
     */
    @Override
    public void pushStreamSucc(TIMAvManager.StreamRes streamRes) {
        List<TIMAvManager.LiveUrl> liveUrls = streamRes.getUrls();
        isPushed = true;
        pushBtn.setText(R.string.live_btn_stop_push);
        Toast.makeText(this, "推流成功", Toast.LENGTH_LONG).show();
        int length = liveUrls.size();
        String url = null;
        String url2 = null;
        if (length == 1) {
            TIMAvManager.LiveUrl avUrl = liveUrls.get(0);
            url = avUrl.getUrl();
        } else if (length == 2) {
            TIMAvManager.LiveUrl avUrl = liveUrls.get(0);
            url = avUrl.getUrl();
            TIMAvManager.LiveUrl avUrl2 = liveUrls.get(1);
            url2 = avUrl2.getUrl();
        }

        TLurl.setUrl(url);

        TIMFriendshipManager.getInstance().setLocation(url, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {
                Toast.makeText(LiveActivity.this, "推流地址设置成功", Toast.LENGTH_SHORT).show();

            }
        });


        Log.e("url1", url);
        Log.e("url2", url2);

        //ClipToBoard(url, url2);
    }


    private Dialog recordDialog;
    private TIMAvManager.RecordParam mRecordParam;
    private String filename = "";
    private String tags = "";
    private String classId = "";
    private boolean mRecord = false;
    private EditText filenameEditText, tagEditText, classEditText;
    private CheckBox trancodeCheckBox, screenshotCheckBox, watermarkCheckBox;

    private void initRecordDialog() {
        recordDialog = new Dialog(this, R.style.dialog);
        recordDialog.setContentView(R.layout.record_param);
        mRecordParam = TIMAvManager.getInstance().new RecordParam();

        filenameEditText = (EditText) recordDialog.findViewById(R.id.record_filename);
        tagEditText = (EditText) recordDialog.findViewById(R.id.record_tag);
        classEditText = (EditText) recordDialog.findViewById(R.id.record_class);
        trancodeCheckBox = (CheckBox) recordDialog.findViewById(R.id.record_tran_code);
        screenshotCheckBox = (CheckBox) recordDialog.findViewById(R.id.record_screen_shot);
        watermarkCheckBox = (CheckBox) recordDialog.findViewById(R.id.record_water_mark);

        if (filename.length() > 0) {
            filenameEditText.setText(filename);
        }
        filenameEditText.setText("" + CurLiveInfo.getTitle());

        if (tags.length() > 0) {
            tagEditText.setText(tags);
        }

        if (classId.length() > 0) {
            classEditText.setText(classId);
        }

        Button recordOk = (Button) recordDialog.findViewById(R.id.btn_record_ok);
        recordOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SxbLog.d(TAG, LogConstants.ACTION_HOST_CREATE_ROOM + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "start record"
                        + LogConstants.DIV + "room id " + MySelfInfo.getInstance().getMyRoomNum());
                filename = filenameEditText.getText().toString();
                mRecordParam.setFilename(filename);
                tags = tagEditText.getText().toString();
                classId = classEditText.getText().toString();
                Log.d(TAG, "onClick classId " + classId);
                if (classId.equals("")) {
                    Toast.makeText(getApplicationContext(), "classID can not be empty", Toast.LENGTH_LONG).show();
                    return;
                }


                mRecordParam.setClassId(Integer.parseInt(classId));
                mRecordParam.setTransCode(trancodeCheckBox.isChecked());
                mRecordParam.setSreenShot(screenshotCheckBox.isChecked());
                mRecordParam.setWaterMark(watermarkCheckBox.isChecked());
                mLiveHelper.startRecord(mRecordParam);
                recordDialog.dismiss();
            }
        });
        Button recordCancel = (Button) recordDialog.findViewById(R.id.btn_record_cancel);
        recordCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordDialog.dismiss();
            }
        });
        Window dialogWindow = recordDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        recordDialog.setCanceledOnTouchOutside(false);
    }

    public void lubo() {
        mRecordParam = TIMAvManager.getInstance().new RecordParam();
        mRecordParam.setFilename(CurLiveInfo.getTitle());
        mLiveHelper.startRecord(mRecordParam);

    }

    /**
     * 停止推流成功
     */
    @Override
    public void stopStreamSucc() {
        isPushed = false;
        pushBtn.setText(R.string.live_btn_push);
    }

    @Override
    public void startRecordCallback(boolean isSucc) {
        mRecord = true;
        recordBtn.setText(R.string.live_btn_stop_record);
    }

    boolean fans = false;


    @Override
    public void stopRecordCallback(boolean isSucc, List<String> files) {

        /*new Thread(new Runnable(){

            public void run(){
                try {
                    Thread.sleep(12000);
                    Message msg=new Message();
                    msg.what=15;
                    mHandler.sendMessage(msg); //告诉主线程执行任务
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();*/

        if (isSucc == true) {
            mRecord = false;
            recordBtn.setText(R.string.live_btn_record);
            String name = filenameEditText.getText().toString();
            recodelist = files;

            for (int i = 0; i < files.size(); i++) {
                uploadeVideo(files.get(i), name);


            }
        }
    }


    public void getuplodeinfo() {
        String name = filenameEditText.getText().toString();
        for (int i = 0; i < recodelist.size(); i++) {
            uploadeVideo(recodelist.get(i), name);

        }
    }


    //上传录制信息
    public void uploadeVideo(String ID, String filename) {

        SharedPreferences share = getSharedPreferences("photo", 0);
        String url = share.getString("url", "");

        Map<String, String> map = new HashMap<>();
        map.put("vid", ID);
        map.put("videoName", filename);
        Log.e("TAGIDIDID", ID);
        OkGo.post(HttpUrl.RememberInfo)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("vid", ID)
                .params("imageUrl", url)
                .params("videoName", filename)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(LiveActivity.this, "录播成功", Toast.LENGTH_SHORT).show();

                        booleant = true;
                        // EndLiving();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(LiveActivity.this, "录播失败", Toast.LENGTH_SHORT).show();
                    }
                });


      /*OkHttpClientManager.postAsyn(HttpUrl.RememberInfo, new OkHttpClientManager.StringCallback() {
          @Override
          public void onFailure(Request request, IOException e) {

          }

          @Override
          public void onResponse(String response) {
              Toast.makeText(LiveActivity.this,"录播"+response,Toast.LENGTH_LONG).show();

          }
      },map,"");*/

    }


    private VideoOrientationEventListener mOrientationEventListener;

    void registerOrientationListener() {
        if (mOrientationEventListener == null) {
            mOrientationEventListener = new VideoOrientationEventListener(super.getApplicationContext(), SensorManager.SENSOR_DELAY_UI);
        }
    }

    void startOrientationListener() {
        if (mOrientationEventListener != null) {
            mOrientationEventListener.enable();
        }
    }

    void stopOrientationListener() {
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
        }
    }


    class VideoOrientationEventListener extends OrientationEventListener {
        boolean mbIsTablet = false;
        int mRotationAngle = 0;

        public VideoOrientationEventListener(Context context, int rate) {
            super(context, rate);
            mbIsTablet = PhoneStatusTools.isTablet(context);
        }

        int mLastOrientation = -25;

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                mLastOrientation = orientation;
                return;
            }

            if (mLastOrientation < 0) {
                mLastOrientation = 0;
            }

            if (((orientation - mLastOrientation) < 20)
                    && ((orientation - mLastOrientation) > -20)) {
                return;
            }

            if (mbIsTablet) {
                orientation -= 90;
                if (orientation < 0) {
                    orientation += 360;
                }
            }
            mLastOrientation = orientation;

            if (orientation > 314 || orientation < 45) {
                if (QavsdkControl.getInstance() != null) {
                    QavsdkControl.getInstance().setRotation(0);
                }
                mRotationAngle = 0;
            } else if (orientation > 44 && orientation < 135) {
                if (QavsdkControl.getInstance() != null) {
                    QavsdkControl.getInstance().setRotation(90);
                }
                mRotationAngle = 90;
            } else if (orientation > 134 && orientation < 225) {
                if (QavsdkControl.getInstance() != null) {
                    QavsdkControl.getInstance().setRotation(180);
                }
                mRotationAngle = 180;
            } else {
                if (QavsdkControl.getInstance() != null) {
                    QavsdkControl.getInstance().setRotation(270);
                }
                mRotationAngle = 270;
            }
        }
    }


    void checkPermission() {
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.CAMERA);
            if ((checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.RECORD_AUDIO);
            if ((checkSelfPermission(Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WAKE_LOCK);
            if ((checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            if (permissionsList.size() != 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_PHONE_PERMISSIONS);
            }
        }
    }

    // 清除老房间数据
    private void clearOldData() {
        mArrayListChatEntity.clear();
        mBoolNeedRefresh = true;
        if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }
        QavsdkControl.getInstance().clearVideoData();
    }

    @Override
    public void onSlideUp() {
        if (MySelfInfo.getInstance().getIdStatus() != Constants.HOST) {
            SxbLog.v(TAG, "ILVB-DBG|onSlideUp->enter");
            // quiteLiveByPurpose();
            // mLiveListViewHelper.getPageData();
            bSlideUp = true;
        }
    }

    @Override
    public void onSlideDown() {
        if (MySelfInfo.getInstance().getIdStatus() != Constants.HOST) {
            SxbLog.v(TAG, "ILVB-DBG|onSlideDown->enter");
            // quiteLiveByPurpose();
            // mLiveListViewHelper.getPageData();
            bSlideUp = false;
        }
    }


    static class ViewHolder {
        RelativeLayout layout;
        ImageView icon;
        TextView name, gift_money;
    }


    /**
     * 获取礼物数据
     */
    @Override
    public void giftdata(List<Mygift> list) {
    }

    /**
     * 获取礼物成功
     */

    @Override
    public void sucess() {
    }

    /**
     * 获取礼物失败
     */

    @Override
    public void onfiled() {
    }


    public void MyliwuView() {
        realay_chongzhi = (RelativeLayout) this.findViewById(R.id.realay_chongzhi);
        //getGift();
        for (int i = 0; i < giftimage.length; i++) {
            liwu gift = new liwu();
            gift.setId(i + 1 + "");
            gift.setMoney(giftprices[i]);
            gift.setPhotourl("");
            gift.setGiftname(giftname[i]);
            gift.setResimgid(giftimage[i]);
            lists.add(gift);
        }

        //放置礼物数据
        /* for (int i = 0; i < 23; i++) {
            lists.add(i);
        }*/

        mViewPager = (ViewPager) findViewById(R.id.langsi_popup_viewpager);
        loadViews();

    }


    private void loadViews() {
        int pageCount = (lists.size() + GRIDVIEW_COUNT - 1) / GRIDVIEW_COUNT;
        Log.d(TAG, "pageCount: " + pageCount);
        mGridViewAdapters.clear();
        mAllViews.clear();
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < pageCount; i++) {
            View mView = inflater.inflate(R.layout.langsi_popup_gridview, null);
            GridView mGridView = (GridView) mView
                    .findViewById(R.id.langsi_popup_gridview);
            MyAdapter adapter = new MyAdapter(mContext, i);
            mGridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            mGridViewAdapters.add(adapter);
            mAllViews.add(mView);
        }

        myViewPagerAdapter = new MyViewPagerAdapter();
        mViewPager.setAdapter(myViewPagerAdapter);
        myViewPagerAdapter.notifyDataSetChanged();

    }


    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mAllViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mViewPager.removeView((View) object);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager) container).addView(mAllViews.get(position),
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return mAllViews.get(position);
        }
    }


    private class MyAdapter extends BaseAdapter {

        private Context mContext;
        private int pagePosition;
        private LayoutInflater mInflater;

        private MyAdapter(Context context, int pagePosition) {
            this.mContext = context;
            this.pagePosition = pagePosition;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            int size = (lists == null ? 0 : lists.size() - GRIDVIEW_COUNT
                    * pagePosition);
            Log.d(TAG, "size: " + size);
            if (size > GRIDVIEW_COUNT) {
                return GRIDVIEW_COUNT;
            } else {
                return size > 0 ? size : 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int nowPosition = GRIDVIEW_COUNT * pagePosition + position;
            final liwu surprise = lists.get(nowPosition);

            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = mInflater.inflate(R.layout.langsi_popup_item, null);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.langsi_popup_app_name);
                holder.icon = (ImageView) convertView.findViewById(R.id.langsi_popup_app_icon);
                holder.layout = (RelativeLayout) convertView.findViewById(R.id.langsi_popup_item_layout);
                holder.gift_money = (TextView) convertView.findViewById(R.id.gift_money);
                convertView.setTag(holder);
            }

            holder.layout.setTag(nowPosition);
            holder.layout.setOnClickListener(LiveActivity.this);
            holder.name.setText(lists.get(nowPosition).getGiftname());
            holder.gift_money.setText(lists.get(nowPosition).getMoney() + "金币");
            holder.icon.setImageResource(surprise.getResimgid());


            //阳sir
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "onClick: 礼物id" + surprise.getId() + "主播id" + roomid);
//                    Toast.makeText(LiveActivity.this, "礼物发送中", Toast.LENGTH_SHORT).show();
                    Linear_gift.setVisibility(View.GONE);
                    Realitv.setVisibility(View.GONE);
                    mNomalMemberCtrView.setVisibility(View.VISIBLE);
                    mLiveHelper.SendgiftGoods(surprise.getId(), String.valueOf(roomid));
                    String balance = surprise.getMoney();
                    if (Double.valueOf(Constant.getUser().getBalance()) > Double.valueOf(balance)) {
                        if (surprise.getGiftname().equals("鲜花")) {
                            Log.i("git", "点击发送消息");
                            sendGiftmessage("10", "鲜花");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("飞吻")) {
                            sendGiftmessage("11", "飞吻");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("爱心")) {
                            sendGiftmessage("12", "爱心");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("干杯")) {
                            sendGiftmessage("13", "干杯");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("钻戒")) {
                            sendGiftmessage("14", "钻戒");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("钻石")) {
                            isGiftBalance = false;
                            sendGiftmessage("15", "钻石");
                        } else if (surprise.getGiftname().equals("iPhone7")) {
                            sendGiftmessage("16", "iPhone7");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("财神爷")) {
                            sendGiftmessage("17", "财神爷");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("法拉利")) {
                            sendGiftmessage("18", "法拉利");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("大黄蜂")) {
                            sendGiftmessage("19", "大黄蜂");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("飞机")) {
                            sendGiftmessage("20", "飞机");
                            isGiftBalance = false;
                        } else if (surprise.getGiftname().equals("游轮")) {
                            sendGiftmessage("21", "游轮");
                            isGiftBalance = false;
                        } else {

                        }
                    }
                }
            });
            return convertView;
        }

        public class ViewHolder {
            RelativeLayout layout;
            ImageView icon;
            TextView name, gift_money;
        }
    }


    public void setimgAnimTion(String animtype, ImageView img) {
        GiftHelper gift = new GiftHelper(LiveActivity.this, img);
        gift.setAnimImageviewResourceGetName(animtype);
        gift.setimgAnimtion(Integer.valueOf(animtype));
    }


    public void sendGiftmessage(String id, String gift) {
        TIMMessage msg = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        TIMTextElem elem2 = new TIMTextElem();
        elem2.setText(id);
        elem.setText("发送了" + gift);   //设置消息内容
        msg.addElement(elem);
        msg.addElement(elem2);
        mLiveHelper.sendGroupText(msg);
        Log.i("git", "发送消息中。。。");
    }



  /*  public void sharesvodio(String url){

        ShareSDK.initSDK(LiveActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        oks.setTitle("炎黄直播");
        // tleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setText("["+CurLiveInfo.getTitle()+"]"+CurLiveInfo.getHostName()+"正在直播快来围观");
        oks.setImageUrl(CurLiveInfo.getImageUrl());
        oks.setTitleUrl(url);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        oks.show(this);
    }*/

   /* public void Share(String url){
        ShareHelper shareHelper = new ShareHelper(LiveActivity.this);
        shareHelper.setTitle(CurLiveInfo.getTitles());
        shareHelper.setText(url);
        shareHelper.setImageUrl(CurLiveInfo.getImageUrl());
        shareHelper.setUrl(url);
        shareHelper.setSite("来自");
        shareHelper.setSiteUrl(url);
        shareHelper.setTitleUrl(url);
        shareHelper.show();
    }*/


    public void getfenxiangdata() {

        SharedPreferences share = getSharedPreferences("photo", 0);
        String url = share.getString("url", "");
        OkGo.post(HttpUrl.fenxiang)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("teacherName", CurLiveInfo.getHostName())
                .params("teacherPhoto", CurLiveInfo.getHostAvator())
                .params("playAddress", TLurl.getUrl())
                .params("roomId", CurLiveInfo.getRoomNum() + "")
                .params("roomPhoto", CurLiveInfo.getImageUrl())
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject json = new JSONObject(s);
                            if (json.getString("resultCode").equals("100")) {
                                String url = json.getString("resultData");
                                Log.e("casdaf", url);
                                Log.e("cctt", s);
                                // Share(url);
                                assc.setText(url);
//                                sharesvodio(url);

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
}

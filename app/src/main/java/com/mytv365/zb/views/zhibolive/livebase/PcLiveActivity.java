package com.mytv365.zb.views.zhibolive.livebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.fhrj.library.tools.ToolToast;
import com.fhrj.library.view.imageview.RoundImageView;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.ChatMsgListAdapter;
import com.mytv365.zb.avcontrollers.QavsdkControl;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.model.ChatEntity;
import com.mytv365.zb.model.CurLiveInfo;
import com.mytv365.zb.model.Live;
import com.mytv365.zb.model.LiveInfoJson;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.model.TIMProfie;
import com.mytv365.zb.model.liwu;
import com.mytv365.zb.presenters.AudienceHelper;
import com.mytv365.zb.presenters.EnterLiveHelper;
import com.mytv365.zb.presenters.LiveHelper;
import com.mytv365.zb.presenters.viewinface.Audience;
import com.mytv365.zb.presenters.viewinface.EnterQuiteRoomView;
import com.mytv365.zb.presenters.viewinface.ExitView;
import com.mytv365.zb.presenters.viewinface.GiftHelper;
import com.mytv365.zb.presenters.viewinface.LiveView;
import com.mytv365.zb.utils.Constants;
import com.mytv365.zb.utils.GlideCircleTransform;
import com.mytv365.zb.utils.LogConstants;
import com.mytv365.zb.utils.SxbLog;
import com.mytv365.zb.utils.UIUtils;
import com.mytv365.zb.views.activity.MyzhuyeActivity;
import com.mytv365.zb.views.activity.PersonPayMoneyActivity;
import com.mytv365.zb.views.customviews.HeartLayout;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMCallBack;
import com.tencent.TIMDelFriendType;
import com.tencent.TIMFriendCheckParam;
import com.tencent.TIMFriendCheckResult;
import com.tencent.TIMFriendRelationType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.av.TIMAvManager;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/10/12
 * Description:
 */

public class PcLiveActivity extends AppCompatActivity implements EnterQuiteRoomView, View.OnClickListener, LiveView, Audience , ExitView {
    private Live live;
    private EnterLiveHelper mEnterRoomHelper;
    private LiveHelper mLiveHelper;
    private ArrayList<ChatEntity> mArrayListChatEntity;
    private static ArrayList<ChatEntity> mTmpChatList = new ArrayList<ChatEntity>();//缓冲队列
    private ChatMsgListAdapter mChatMsgListAdapter;
    private ListView mListViewMsgItems;
    private boolean mBoolRefreshLock = false;
    private boolean mBoolNeedRefresh = false;
    private TimerTask mTimerTask = null;
    private long watchCount;
    private String IMRoomId = "47";
    private JCVideoPlayerStandard jcVideoPlayerStandard;

    //view
    private ImageView head_icon;//主播头像
    private TextView host_name, live_user_balance;//主播名字
    private TextView room_id;
    private TextView member_counts;//房间人数
    private RelativeLayout relat_guanzhu;//关注
    private TextView audience;
    private TextView heart_counts;//魅力值
    private TextView btn_back;//关闭按钮
    private HeartLayout mHeartLayout;
    private long admireTime = 0;
    private LinearLayout live_frofile_item_layout, rl_inputdlg_view;

    //text
    private String rtmp = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private TextView tvMembers;
    private ImageView message_input;
    private ImageView clean_screen;
    private ImageView member_send_good;
    private TextView tvAdmires;

    //gift
    private RelativeLayout Linear_gift, mNomalMemberCtrView, Realitv, ittm_input_message_editext;
    private boolean isVisility = false;

    private String[] giftprices = {"10", "20", "50", "80", "200", "500", "700", "888", "10000", "12000", "15000", "20000"};
    private String[] giftname = {"鲜花", "飞吻", "爱心", "干杯", "钻戒", "钻石", "iPhone7", "财神爷", "法拉利", "大黄蜂", "飞机", "游轮"};
    private Integer[] giftimage = {R.drawable.live_gift_flower, R.drawable.live_gift_kiss, R.drawable.live_gift_heart, R.drawable.live_gift_cheers, R.drawable.live_gift_ring, R.drawable.live_gift_diamond, R.drawable.live_gift_phone, R.drawable.live_gift_god, R.drawable.live_gift_falali, R.drawable.live_gift_wasp, R.drawable.live_gift_plane, R.drawable.live_gift_youlun};
    private List<View> mAllViews = new ArrayList<View>();
    private ViewPager mViewPager;
    private List<MyAdapter> mGridViewAdapters = new ArrayList<MyAdapter>();
    private List<liwu> lists = new ArrayList<liwu>();
    private static final int GRIDVIEW_COUNT = 8;
    private ImageView live_gift_img;
    private Context mContext;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ImageView anim_img;
    private boolean isGiftBalance = false;
    private boolean isAudience = false;
    private static final String TAG = "PcLiveActivity";
    private AudienceHelper audienceHelper;
    private EditText input_message;
    private InputMethodManager imm;
    private Button chongzhi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 不锁屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.acitivty_pclive);
        IMRoomId = CurLiveInfo.getHostID();

        tvMembers = (TextView) findViewById(R.id.member_counts);
        live_user_balance = (TextView) findViewById(R.id.live_user_balance);
        chongzhi= (Button) findViewById(R.id.chongzhi);


        mContext = getApplicationContext();
        init();
        MyliwuView();


        audienceHelper.SelectAudience(Integer.valueOf(CurLiveInfo.getUserid()));




        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Constants.BD_EXIT_APP);
        receiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                finish();

            }
        };
        registerReceiver(receiver, intentFilter);


    }

    public void init() {
        MySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
        MySelfInfo.getInstance().setJoinRoomWay(false);
        live = (Live) getIntent().getSerializableExtra("live");

        Log.i(TAG, "init: live.getRoomid  "+live.getRoomid()+"getUserid   "+live.getUserid());



        if (live == null) {
            ToolToast.showLong("进入直播失败，请退出重试");
            return;
        }

        //进出房间的协助类
        mEnterRoomHelper = new EnterLiveHelper(this, this);
        mLiveHelper = new LiveHelper(this, this);
        audienceHelper = new AudienceHelper(this, this);
        mListViewMsgItems = (ListView) findViewById(R.id.im_msg_listview);
        mArrayListChatEntity = new ArrayList<ChatEntity>();
        mChatMsgListAdapter = new ChatMsgListAdapter(this, mListViewMsgItems, mArrayListChatEntity);
        mListViewMsgItems.setAdapter(mChatMsgListAdapter);

        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
        head_icon = (ImageView) findViewById(R.id.head_icon);
        live_frofile_item_layout = (LinearLayout) findViewById(R.id.live_profile_item_face_layout);
        host_name = (TextView) findViewById(R.id.host_name);
        room_id = (TextView) findViewById(R.id.room_id);
        member_counts = (TextView) findViewById(R.id.member_counts);
        relat_guanzhu = (RelativeLayout) findViewById(R.id.relat_guanzhu);
        audience = (TextView) findViewById(R.id.audience);
        anim_img = (ImageView) findViewById(R.id.anim_img);
        heart_counts = (TextView) findViewById(R.id.heart_counts);
        btn_back = (TextView) findViewById(R.id.btn_back);
        mHeartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        tvAdmires = (TextView) findViewById(R.id.heart_counts);
        input_message = (EditText) findViewById(R.id.input_message);
        rl_inputdlg_view = (LinearLayout) findViewById(R.id.rl_inputdlg_view);


        Linear_gift = (RelativeLayout) this.findViewById(R.id.Linear_gift);
        mNomalMemberCtrView = (RelativeLayout) findViewById(R.id.member_bottom_layout);
        Realitv = (RelativeLayout) this.findViewById(R.id.Realitv);
        live_gift_img = (ImageView) findViewById(R.id.live_gift_img);
        ittm_input_message_editext = (RelativeLayout) findViewById(R.id.ittm_input_message_editext);


        message_input = (ImageView) findViewById(R.id.message_input);
        clean_screen = (ImageView) findViewById(R.id.clean_screen);
        member_send_good = (ImageView) findViewById(R.id.member_send_good);
        if (!TextUtils.isEmpty(live.getPayUrl())) {
            jcVideoPlayerStandard.setUp(live.getPayUrl()
                    , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, live.getTitle());
        }
        jcVideoPlayerStandard.bottomProgressBar.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.currentTimeTextView.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.totalTimeTextView.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.progressBar.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.fullscreenButton.setVisibility(View.VISIBLE);

        jcVideoPlayerStandard.bottomContainer.setVisibility(View.INVISIBLE);
//        live.getLiveUrl() 为聊天室id，暂时该返回的聊天室ID无效，请跟后台联系，暂时用50主播聊天室id代替
        mEnterRoomHelper.justJoinIMChatRoom(live.getRoomid());

        btn_back.setOnClickListener(this);
        relat_guanzhu.setOnClickListener(this);
        message_input.setOnClickListener(this);
        clean_screen.setOnClickListener(this);
        member_send_good.setOnClickListener(this);
        host_name.setText(live.getName());
        Linear_gift.setOnClickListener(this);

        showHeadIcon(head_icon, live.getIoc());
        audience.setOnClickListener(this);

//        mEnterRoomHelper.facestep2(String.valueOf(CurLiveInfo.getRoomNum()));


        mEnterRoomHelper.facestep2(CurLiveInfo.getLiveUrlroom());

        imm = (InputMethodManager) input_message.getContext()
                .getSystemService(INPUT_METHOD_SERVICE);


        head_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PcLiveActivity.this, MyzhuyeActivity.class);
                intent.putExtra(MyzhuyeActivity.LIVETEACHERID, live.getUserid());
                intent.putExtra("names", CurLiveInfo.getHostName());
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (jcVideoPlayerStandard != null) {
            jcVideoPlayerStandard.startPlayLogic();
        }
    }

    @Override
    public void onBackPressed() {

        if (JCVideoPlayer.backPress()) {
            return;
        }

        if (mEnterRoomHelper != null) {
            mEnterRoomHelper.justQuiteIMChatRoom(IMRoomId);
        }

        //阳
//        Log.i("formy", "隐藏输入法。");
        ittm_input_message_editext.setVisibility(View.INVISIBLE);
        ittm_input_message_editext.setVisibility(View.GONE);
//        Log.i("formy", "隐藏完毕。");
        imm.hideSoftInputFromWindow(input_message.getWindowToken(), 0);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
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
        SxbLog.d("refreshTextListView", "refreshTextListView height " + mListViewMsgItems.getHeight());

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
        mArrayListChatEntity.addAll(mTmpChatList);
        mTmpChatList.clear();
        mChatMsgListAdapter.notifyDataSetChanged();

    }

    @Override
    public void enterRoomComplete(int id_status, boolean succ) {

        if (succ) {
            //IM初始化
            mLiveHelper.initTIMListener(IMRoomId);
//            mLiveHelper.initTIMListener("" + CurLiveInfo.getRoomNum());

            mLiveHelper.sendGroupMessage(Constants.AVIMCMD_EnterLive, "");
//            chenckFollow();
        }
    }

    @Override
    public void quiteRoomComplete(int id_status, boolean succ, LiveInfoJson liveinfo) {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.MEMBER) {
            //IM初始化
            mLiveHelper.sendGroupMessage(Constants.AVIMCMD_ExitLive, "");
        }
    }

    @Override
    public void memberQuiteLive(String[] list) {
        ToolToast.showLong("memberQuiteLive:->" + "list:" + list.length);
//        Log.i(TAG, "成员退出时候的监听方法");
//        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "quite live", Constants.MEMBER_EXIT);
    }

    @Override
    public void memberJoinLive(String[] list) {
        ToolToast.showLong("memberJoinLive:->" + "list:" + list.length);
    }

    @Override
    public void alreadyInLive(String[] list) {
        ToolToast.showLong("alreadyInLive:->" + "list:" + list.length);
        member_counts.setText(list.length);
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
        //live_frofile_item_layout.removeAllViews();
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

        Log.i("live", "获取到成员的信息是" + profilelist.toString());
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_back) {
            //onBackPressed();
            PcLiveActivity.this.finish();



        } else if (v.getId() == R.id.message_input) {
            inputMsgDialog();

            //阳
//            ittm_input_message_editext.setVisibility(View.VISIBLE);
//            input_message.setFocusableInTouchMode(true);
//            input_message.setFocusable(true);
//            input_message.requestFocus();
//            imm.showSoftInput(input_message, 0);
//

        } else if (v.getId() == R.id.clean_screen) {
            //点击礼物显示出
            isVisility = true;
            mNomalMemberCtrView.setVisibility(View.GONE);
            Linear_gift.setVisibility(View.VISIBLE);
            Realitv.setVisibility(View.VISIBLE);
            live_user_balance.setText(Constant.getUser().getBalance());

            //阳
            chongzhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(PcLiveActivity.this, PersonPayMoneyActivity.class));
                }
            });


        } else if (v.getId() == R.id.member_send_good) {
            //ToolToast.showLong("member_send_good");
            mHeartLayout.addFavor();
            if (checkInterval()) {

                mLiveHelper.sendC2CMessage(Constants.AVIMCMD_Praise, "", live.getRoomid());
                CurLiveInfo.setAdmires(CurLiveInfo.getAdmires() + 1);
                // tvAdmires.setText("" + CurLiveInfo.getAdmires());

            }else{

                //Toast.makeText(this, getString(R.string.text_live_admire_limit), Toast.LENGTH_SHORT).show();

            }

        }

        switch (v.getId()) {
            case R.id.Linear_gift:

                if (isVisility == true) {
                    mNomalMemberCtrView.setVisibility(View.VISIBLE);
                    Realitv.setVisibility(View.GONE);
                    Linear_gift.setVisibility(View.GONE);
                }

                break;

            case R.id.audience:
                Log.i(TAG, "onClick: 点击关注");
                if (!isAudience) {
                    audienceHelper.addaudience(Integer.valueOf(CurLiveInfo.getUserid()));
                } else {
                    audienceHelper.CancelAudience(Integer.valueOf(CurLiveInfo.getUserid()));
                }
                break;
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if (mEnterRoomHelper != null) {
            Log.i(TAG, "onDestroy:  退出房间id"+CurLiveInfo.getUserid());
            mEnterRoomHelper.justQuiteIMChatRoom(String.valueOf(CurLiveInfo.getUserid()));
            mEnterRoomHelper.onDestory();
        }

        if (mLiveHelper != null) {
            mLiveHelper.onDestory();
        }
        unregisterReceiver(receiver);
    }


    /**
     * 发消息弹出框
     */
    private void inputMsgDialog() {

//        rl_inputdlg_view.setVisibility(View.VISIBLE);
//        InputMethodManager imm = (InputMethodManager) input_message.getContext()
//                .getSystemService(INPUT_METHOD_SERVICE);
//        rl_inputdlg_view.setFocusableInTouchMode(true);
//        rl_inputdlg_view.setFocusable(true);
//        rl_inputdlg_view.requestFocus();
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        InputTextMsgDialog inputTextMsgDialog = new InputTextMsgDialog(this, R.style.inputdialog, mLiveHelper);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = inputTextMsgDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        inputTextMsgDialog.getWindow().setAttributes(lp);
        inputTextMsgDialog.setCancelable(true);
        inputTextMsgDialog.show();

    }

    @Override
    public void showVideoView(boolean isHost, String id) {

    }

    @Override
    public void showInviteDialog() {

    }

    @Override
    public void refreshText(String text, String name) {
//        ToolToast.showLong("text=" + text + "|name=" + name);
        refreshTextListView(name, text, Constants.TEXT_TYPE);
    }

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
    }

    @Override
    public void refreshUI(String id) {
    }

    @Override
    public boolean showInviteView(String id) {
        return false;
    }

    @Override
    public void cancelInviteView(String id) {
    }

    @Override
    public void cancelMemberView(String id) {
    }

    @Override
    public void memberJoin(String id, String name) {
        SxbLog.d("memberJoin", LogConstants.ACTION_VIEWER_ENTER_ROOM + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "on member join" +
                LogConstants.DIV + "join room " + id);
        watchCount++;
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "加入房间", Constants.MEMBER_ENTER);
        CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1);
//        tvMembers.setText("" + CurLiveInfo.getMembers());

    }

    @Override
    public void memberQuit(String id, String name) {

        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "离开房间", Constants.MEMBER_EXIT);
        if (CurLiveInfo.getMembers() > 1) {
            CurLiveInfo.setMembers(CurLiveInfo.getMembers() - 1);
//            tvMembers.setText("" + CurLiveInfo.getMembers());
        }
        //如果存在视频互动，取消
        QavsdkControl.getInstance().closeMemberView(id);
    }

    @Override
    public void readyToQuit() {
        mEnterRoomHelper.quiteLive();
    }

    @Override
    public void hideInviteDialog() {
    }

    @Override
    public void pushStreamSucc(TIMAvManager.StreamRes streamRes) {
    }

    @Override
    public void stopStreamSucc() {
    }

    @Override
    public void startRecordCallback(boolean isSucc) {
    }

    @Override
    public void stopRecordCallback(boolean isSucc, List<String> files) {
    }

    // TODO: 2016/10/14 直播间直播关闭（由于聊天室关闭），取消直播拉流，显示一些提示信息
    @Override
    public void hostLeave(String id, String name) {
        ToolToast.showLong("直播间关闭");
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "leave for a while", Constants.HOST_LEAVE);
    }

    // TODO: 2016/10/14 主播重新回来，敢看直播，初始化一些房间信息等
    @Override
    public void hostBack(String id, String name) {
        ToolToast.showLong("主播重新回来");
        mEnterRoomHelper.justJoinIMChatRoom(IMRoomId);
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "is back", Constants.HOST_BACK);
    }

    @Override
    public void giftCallback(int resultCode) {
        //样子
        if (resultCode == 100) {
//            Toast.makeText(PcLiveActivity.this, "礼物发送中", Toast.LENGTH_SHORT).show();
            isGiftBalance = true;
            Log.i("git", "giftCallback: 余额 " + Constant.getUser().getBalance());
            live_user_balance.setText(Constant.getUser().getBalance());

        } else if (resultCode == 303) {
            Toast.makeText(PcLiveActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(PcLiveActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void EndLiveCallback() {
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


    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            RequestManager req = Glide.with(this);
            req.load(avatar).transform(new GlideCircleTransform(this)).into(view);
        }
    }


    public void MyliwuView() {

        for (int i = 0; i < giftimage.length; i++) {
            liwu gift = new liwu();
            gift.setId(i + 1 + "");
            gift.setMoney(giftprices[i]);
            gift.setPhotourl("");
            gift.setGiftname(giftname[i]);
            gift.setResimgid(giftimage[i]);
            lists.add(gift);
        }

        mViewPager = (ViewPager) findViewById(R.id.langsi_popup_viewpager);
        loadViews();
    }


    private BroadcastReceiver receiver;

    private void loadViews() {
        int pageCount = (lists.size() + GRIDVIEW_COUNT - 1) / GRIDVIEW_COUNT;
//        Log.d(TAG, "pageCount: " + pageCount);
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

    @Override
    public void addaudience(int type) {
        //阳
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
    }

    @Override
    public void getConcernList(List<TIMUserProfile> concernList) {
    }

    @Override
    public void getByConernList(List<TIMUserProfile> concernList) {
    }

    @Override
    public void exit() {

    }

    @Override
    public void ExitBackView() {

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
//            Log.d(TAG, "size: " + size);
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
//            imglocationpositon = nowPosition;
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
            holder.layout.setOnClickListener(PcLiveActivity.this);
            holder.name.setText(lists.get(nowPosition).getGiftname());
            holder.gift_money.setText(lists.get(nowPosition).getMoney() + "金币");
            holder.icon.setImageResource(giftimage[nowPosition]);


            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(PcLiveActivity.this, "礼物发送中", Toast.LENGTH_SHORT).show();
                    mLiveHelper.SendgiftGoods(surprise.getId(), String.valueOf(CurLiveInfo.getUserid()));
                    String balance = surprise.getMoney();
                    Linear_gift.setVisibility(View.GONE);
                    Realitv.setVisibility(View.GONE);
                    mNomalMemberCtrView.setVisibility(View.VISIBLE);
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


    public void setimgAnimTion(String animtype, ImageView img) {
        GiftHelper gift = new GiftHelper(PcLiveActivity.this, img);
        gift.setAnimImageviewResourceGetName(animtype);
        gift.setimgAnimtion(Integer.valueOf(animtype));
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

    private void chenckFollow() {
        List<String> hostid = new ArrayList<>();
//            hostid.add(live.getRoomid());
        hostid.add(IMRoomId);

        TIMFriendshipManager.getInstance().getFriendsProfile(hostid, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                TIMUserProfile profile = timUserProfiles.get(0);
                if (profile.getRemark().equals("add")) {
                    audience.setText("取消关注");
                } else {
                    audience.setText("关注");
                }
            }
        });

    }

    private void CheckFocus() {

        TIMFriendCheckParam params = new TIMFriendCheckParam();
        List<String> fiers = new ArrayList<>();
        fiers.add(IMRoomId);
        params.setBidirection(false);
        params.setIdentifiers(fiers);

        List<TIMFriendResult> result = new ArrayList<>();
        TIMFriendshipManager.getInstance().checkFriends(params, new TIMValueCallBack<List<TIMFriendCheckResult>>() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(List<TIMFriendCheckResult> timFriendCheckResults) {

                if (timFriendCheckResults != null) {
                    TIMFriendCheckResult checkResult = timFriendCheckResults.get(0);
                    if (checkResult.getRelationType() == TIMFriendRelationType.TIM_FRIEND_RELATION_TYPE_SELF) {
//                       audience.setText("取消关注");
                        deleteFriends();
                    } else {
                        addFriends();
                    }
                }
            }
        });
    }

    private void addFriends() {

        TIMAddFriendRequest que = new TIMAddFriendRequest();
        que.setIdentifier(IMRoomId);
        que.setRemark("add");
        List<TIMAddFriendRequest> ques = new ArrayList<>();
        ques.add(que);


        TIMFriendshipManager.getInstance().addFriend(ques, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {
                Toast.makeText(PcLiveActivity.this, "关注失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
//                Toast.makeText(PcLiveActivity.this, "添加关注", Toast.LENGTH_SHORT).show();
                audience.setText("取消关注");
            }
        });


    }

    private void modifyadd() {

        TIMFriendshipManager.getInstance().setFriendRemark(IMRoomId, "add", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess() {
                audience.setText("取消关注");
            }
        });

    }


    private void deleteFriends() {

        TIMAddFriendRequest que = new TIMAddFriendRequest();
        que.setIdentifier(IMRoomId);
        List<TIMAddFriendRequest> ques = new ArrayList<>();
        ques.add(que);

        TIMFriendshipManager.getInstance().delFriend(TIMDelFriendType.TIM_FRIEND_DEL_SINGLE, ques, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {
                Toast.makeText(PcLiveActivity.this, "取消关注好友失败", Toast.LENGTH_SHORT).show();
//                audience.setText("取消关注");
            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                Toast.makeText(PcLiveActivity.this, "取消关注好友成功", Toast.LENGTH_SHORT).show();
                audience.setText("关注");
            }
        });

    }


}

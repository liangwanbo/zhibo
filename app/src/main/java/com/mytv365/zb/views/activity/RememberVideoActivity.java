package com.mytv365.zb.views.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fhrj.library.base.SystemBarTintManager;
import com.mytv365.zb.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2016/10/25.
 */
public class RememberVideoActivity extends AppCompatActivity {

    /*播放地址*/
    private String url;
    private String name;
    private String ID;

    private ImageView backs;

    private JCVideoPlayerStandard jcVideoPlayerStandard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getchenjing();
        setContentView(R.layout.remember_video_view);

        getinkongjian();


    }



    public void getinkongjian(){
        url=getIntent().getStringExtra("urls");
        name=getIntent().getStringExtra("name");
        ID=getIntent().getStringExtra("id");

        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
        if (!TextUtils.isEmpty(url)) {
            jcVideoPlayerStandard.setUp(url
                    , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, name);
        }
        jcVideoPlayerStandard.bottomProgressBar.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.currentTimeTextView.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.totalTimeTextView.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.progressBar.setVisibility(View.INVISIBLE);
        jcVideoPlayerStandard.fullscreenButton.setVisibility(View.VISIBLE);
        jcVideoPlayerStandard.bottomContainer.setVisibility(View.INVISIBLE);
        backs=(ImageView)this.findViewById(R.id.backs);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RememberVideoActivity.this.finish();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(jcVideoPlayerStandard!=null){
            jcVideoPlayerStandard.startPlayLogic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }


    public void getchenjing(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //为状态栏着色
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.quantouming);
    }


    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}

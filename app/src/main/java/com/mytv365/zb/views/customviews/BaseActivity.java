package com.mytv365.zb.views.customviews;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.fhrj.library.third.tvlive.utils.Constants;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.utils.LogConstants;
import com.mytv365.zb.utils.SxbLog;

/**
 *  Created by admin on 2016/5/20.
 *
 */
public class BaseActivity extends Activity{
    private BroadcastReceiver recv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recv = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BD_EXIT_APP)){
                    SxbLog.d("BaseActivity", LogConstants.ACTION_HOST_KICK + LogConstants.DIV + MySelfInfo.getInstance().getId() + LogConstants.DIV + "on force off line");
                    finish();
                }
            }
        };

        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BD_EXIT_APP);
        registerReceiver(recv, filter);

    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(recv);
        }catch (Exception e){
        }
        super.onDestroy();
    }
}

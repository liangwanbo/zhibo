package com.mytv365.zb.views.zhibolive.livebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.utils.Constants;
import com.mytv365.zb.utils.LogConstants;
import com.mytv365.zb.utils.SxbLog;


/**
 * Created by Administrator on 2016/9/1.
 */
public class BaseliveActivity extends FragmentActivity {



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

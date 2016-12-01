package com.mytv365.zb.views.activity.user;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.zb.R;

/**
 * 消息
 */
public class PersontalNotice extends BaseActivity {

    private TextView title;
    private ListView notice_listview;

    @Override
    public int bindLayout() {
        return R.layout.activity_persontal_notice;
    }

    @Override
    public void initParms(Bundle parms) {
    }


    @Override
    public void initView(View view) {
    }


    @Override
    public void doBusiness(Context mContext) {
        setTintManager(R.color.touming);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("我的消息");


    }




}

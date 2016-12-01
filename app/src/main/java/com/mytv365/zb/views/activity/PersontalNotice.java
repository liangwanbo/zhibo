package com.mytv365.zb.views.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.zb.R;

public class PersontalNotice extends BaseActivity {


    private ImageView persontal_nodata_img_replay;
    private TextView persontal_nodata_text_replay;



    @Override
    public int bindLayout() {
        return R.layout.activity_persontal_notice2;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        initTitle("投资笔记");
    }

    @Override
    public void doBusiness(Context mContext) {
    }

    /**
     * 标题栏
     * @param title
     */
    private void initTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();

    }
}

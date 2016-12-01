package com.mytv365.zb.views.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.zb.R;


/**
 * Created by Administrator on 2016/10/9.
 * 发布动态
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout relat_back;

    /*标题*/
    private TextView title;
    @Override
    public int bindLayout() {
        return R.layout.publish_activity;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.theme_select);
        hiddeTitleBar();
        relat_back=(RelativeLayout)findViewById(R.id.relat_back);
        title = (TextView) findViewById(R.id.tv_titles);
        title.setText("发布动态");

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relat_back:
                PublishActivity.this.finish();
                break;

        }

    }
}

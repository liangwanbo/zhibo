package com.mytv365.zb.views.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.presenters.LoginHelper;
import com.mytv365.zb.presenters.viewinface.LogoutView;
import com.mytv365.zb.utils.BaseActivity;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/30
 * Description:
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener  ,LogoutView {
    private RelativeLayout rl_change_password;
    private RelativeLayout rl_feedback;
    private TextView tv_logout;
    /*automatic*/
    private SharedPreferences LoginSharePrefere;
    private LoginHelper mloginHelper;

    private static String action="TUCHULOGIN";


    @Override
    public int bindLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        mloginHelper=new LoginHelper(this,this);
        initTitle("设置");
        rl_change_password = (RelativeLayout) findViewById(R.id.rl_change_password);
        rl_feedback = (RelativeLayout) findViewById(R.id.rl_feedback);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

    }

    @Override
    public void doBusiness(Context mContext) {
        rl_change_password.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        LoginSharePrefere = getSharedPreferences("VertificationLogin", MODE_PRIVATE);
    }


    /***
     * 初始化标题
     * @param title
     */
    private void initTitle(String title){
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.tv_logout:
                Constant.setUser(null);
//                Constant.
//                Intent intent=new Intent();
//                intent.putExtra(MainFragment04.EXITLOGIN,MainFragment04.EXITLOGIN);
//                setResult(1,intent);

                SharedPreferences.Editor editor=LoginSharePrefere.edit();
                editor.clear().commit();
                SharedPreferences sharedPreferences=getSharedPreferences("TYPE",0);
                SharedPreferences.Editor edits=sharedPreferences.edit();
                edits.putString("type","tuichu");
                edits.commit();
                Intent inten=new Intent();
                SettingActivity.this.sendBroadcast(inten,action);
                mloginHelper.imLogout();
                Constant.isvercationReplay=false;
                this.finish();
                break;
        }
    }


    @Override
    public void logoutSucc() {
//        Toast.makeText(SettingActivity.this,"退出啊sdk",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void logoutFail() {
    }

}
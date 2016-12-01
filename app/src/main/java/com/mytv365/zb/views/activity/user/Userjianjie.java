package com.mytv365.zb.views.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.common.BaseActivity;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Userjianjie extends BaseActivity {

    private RelativeLayout relat_back;
    private EditText text_jianjie;
    private String indection;
    private TextView title;


    @Override
    public int getLayout() {
        return R.layout.user_jianjie;
    }

    @Override
    public int getcolor() {
        return R.color.title;
    }

    @Override
    public void getinit() {
        SharedPreferences userInfof =getSharedPreferences("content", 1);
        String content = userInfof.getString("content", "");
        title=(TextView)this.findViewById(R.id.tv_titles);
        title.setText("个人简介");
        text_jianjie=(EditText)this.findViewById(R.id.text_jianjie);
        text_jianjie.setText(content);
        relat_back=(RelativeLayout)this.findViewById(R.id.relat_back);
        relat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                indection=text_jianjie.getText().toString();
                if (indection != null) {
                    Intent intent = new Intent();
                    intent.putExtra("persontal_indection", indection);
                    setResult(UserEditInfoActivity.GERENJIANJIE, intent);
                }

                Userjianjie.this.finish();
            }
        });

    }







    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            indection=text_jianjie.getText().toString();
            if (indection != null) {
                Intent intent = new Intent();
                intent.putExtra("persontal_indection", indection);
                setResult(UserEditInfoActivity.GERENJIANJIE, intent);
            }
            SharedPreferences userInfos = getSharedPreferences("Myjianjie", 1);
            SharedPreferences.Editor edit = userInfos.edit();
            edit.putString("content", indection);
            edit.commit();
            Userjianjie.this.finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

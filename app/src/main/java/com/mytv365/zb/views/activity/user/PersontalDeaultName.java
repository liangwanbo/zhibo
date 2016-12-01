package com.mytv365.zb.views.activity.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.common.BaseActivity;

public class PersontalDeaultName extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private EditText persontal_name;
    private String name;
    private LinearLayout persontal_sex;
    private RelativeLayout relat_back;


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.relat_back:
                name = persontal_name.getText().toString().trim();
                if (name != null) {
                    Intent intent = new Intent();
                    intent.putExtra("persontal_name", name);
                    setResult(UserEditInfoActivity.USERCHANGENAME, intent);
                }
                PersontalDeaultName.this.finish();
                break;
        }
    }


    @Override
    public int getLayout() {
        return R.layout.activity_persontal_deault_name;
    }

    @Override
    public int getcolor() {
        return R.color.touming;
    }

    @Override
    public void getinit() {


        title = (TextView) findViewById(R.id.tv_titles);
        title.setText("修改名称");
        relat_back = (RelativeLayout) this.findViewById(R.id.relat_back);
        relat_back.setOnClickListener(this);
        persontal_name = (EditText) findViewById(R.id.persontal_deault_name);
        SharedPreferences userInfo = getSharedPreferences("content", 1);
        String content = userInfo.getString("content", "");
        persontal_name.setText(content);
        name = persontal_name.getText().toString().trim();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            name = persontal_name.getText().toString().trim();
            if (name != null) {
                Intent intent = new Intent();
                intent.putExtra("persontal_name", name);
                setResult(UserEditInfoActivity.USERCHANGENAME, intent);
            }
            SharedPreferences userInfos = getSharedPreferences("Myname", 1);
            SharedPreferences.Editor edit = userInfos.edit();
            edit.putString("content", name);
            edit.commit();
            PersontalDeaultName.this.finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

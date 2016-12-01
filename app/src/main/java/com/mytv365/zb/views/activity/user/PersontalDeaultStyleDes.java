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

public class PersontalDeaultStyleDes extends BaseActivity implements View.OnClickListener{

    private TextView title;
    private EditText persontal_styledes;
    private String style;
    private RelativeLayout relat_back;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relat_back:

                style=persontal_styledes.getText().toString().trim();
                if (style!=null){
                    Intent intent =new Intent();
                    intent.putExtra("persontal_style",style);
                    setResult(UserEditInfoActivity.USERCHANGEQING,intent);
                }

                PersontalDeaultStyleDes.this.finish();


                break;

        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_persontal_deault_style_des;
    }

    @Override
    public int getcolor() {
        return R.color.touming;
    }

    @Override
    public void getinit() {
        title = (TextView) findViewById(R.id.tv_titles);
        title.setText("个性签名");
        relat_back=(RelativeLayout)this.findViewById(R.id.relat_back);
        relat_back.setOnClickListener(this);
        persontal_styledes= (EditText) findViewById(R.id.persontal_deault_style);
        SharedPreferences userInfoc =getSharedPreferences("content", 1);
        String content=userInfoc.getString("content","");
        persontal_styledes.setText(content);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            style=persontal_styledes.getText().toString().trim();
            if (style!=null){
                Intent intent =new Intent();
                intent.putExtra("persontal_style",style);
                setResult(UserEditInfoActivity.USERCHANGEQING,intent);
            }

            SharedPreferences userInfo =getSharedPreferences("MySign", 1);
            SharedPreferences.Editor edit=userInfo.edit();
            edit.putString("content",style);
            PersontalDeaultStyleDes.this.finish();


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

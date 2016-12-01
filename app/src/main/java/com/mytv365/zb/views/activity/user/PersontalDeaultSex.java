package com.mytv365.zb.views.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.zb.R;


/**
 *
 * Created by yang on 16/10/18.
 */
public class PersontalDeaultSex extends BaseActivity {

    private TextView title;
    private  String sex;
    private EditText persontal_sex;

    @Override
    public int bindLayout() {
        return R.layout.activity_persontal_deault_sex;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        title = (TextView) findViewById(R.id.tv_title);
        title.setText("修改性别");
        persontal_sex= (EditText) findViewById(R.id.persontal_deault_sex);

    }


    @Override
    public void doBusiness(Context mContext) {

    }


    @Override
    public void onBackPressed() {

        sex=persontal_sex.getText().toString().trim();
        if (sex!=null){
            if (sex.equals("男")||sex.equals("女"))
            Toast.makeText(PersontalDeaultSex.this, "backname"+sex, Toast.LENGTH_SHORT).show();
            Intent intent =new Intent();
            intent.putExtra("persontal_sex",sex);
            setResult(UserEditInfoActivity.USERCHANGESEX,intent);
        }

        super.onBackPressed();
    }

}

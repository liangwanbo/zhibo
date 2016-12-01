package com.mytv365.zb.views;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mytv365.zb.R;
import com.mytv365.zb.views.activity.PersonPayActivity;
import com.mytv365.zb.views.zhibolive.livebase.BaseActivity;

/**
 * Created by Administrator on 2016/10/14.
 * <p/>
 * 充值界面
 */
public class ChongzhiActiviry extends BaseActivity implements View.OnClickListener {

    /*标题*/
    private TextView title;
    private Button bnt_one, bnt_two, bnt_thiee, bnt_four, bnt_five, bnt_six;

    @Override
    public int getLayout() {
        return R.layout.activity_persontal_user_money;
    }

    @Override
    public int getcolor() {
        return 0;
    }


    @Override
    public void getinit() {
        title = (TextView) findViewById(R.id.tv_titles);
        title.setText("充值");
        init();
    }


    public void init() {

        bnt_one = (Button) findViewById(R.id.bnt_one);
        bnt_two = (Button) findViewById(R.id.bnt_two);
        bnt_thiee = (Button) findViewById(R.id.bnt_thiee);
        bnt_four = (Button) findViewById(R.id.bnt_four);
        bnt_five = (Button) findViewById(R.id.bnt_five);
        bnt_six = (Button) findViewById(R.id.bnt_six);



        bnt_one.setOnClickListener(this);
        bnt_two.setOnClickListener(this);
        bnt_thiee.setOnClickListener(this);
        bnt_four.setOnClickListener(this);
        bnt_five.setOnClickListener(this);
        bnt_six.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bnt_one:
                //TODO
                Toast.makeText(ChongzhiActiviry.this, "   6666", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PersonPayActivity.class));
                break;
            case R.id.bnt_two:
                break;
            case R.id.bnt_thiee:
                break;
            case R.id.bnt_four:
                break;
            case R.id.bnt_five:
                break;
            case R.id.bnt_six:
                break;

        }

    }
}

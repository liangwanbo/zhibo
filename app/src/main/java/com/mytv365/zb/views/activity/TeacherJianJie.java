package com.mytv365.zb.views.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.common.BaseActivity;

/**
 * Created by Administrator on 2016/10/26.
 */
public class TeacherJianJie extends BaseActivity {
    private RelativeLayout relat_back;
    private TextView text_jianjie;
    private String indection;
    private TextView title,persontal_nodata_text_replay;
    private ImageView persontal_nodata_img_replay;
    private RelativeLayout tearch_introuce;


    @Override
    public int getLayout() {
        return R.layout.teachaerjianjie_layout;
    }

    @Override
    public int getcolor() {
        return R.color.title;
    }

    @Override
    public void getinit() {
        title=(TextView)this.findViewById(R.id.tv_titles);
        title.setText("名师简介");
        indection=getIntent().getStringExtra("jianjie");
        text_jianjie=(TextView)this.findViewById(R.id.text_jianjie);
        text_jianjie.setText(indection);
        relat_back=(RelativeLayout)this.findViewById(R.id.relat_back);
        tearch_introuce= (RelativeLayout) findViewById(R.id.tearch_introuce);
        persontal_nodata_text_replay= (TextView) findViewById(R.id.persontal_nodata_text_replay);
        persontal_nodata_img_replay= (ImageView) findViewById(R.id.persontal_nodata_img_replay);


        if (indection.equals("")){
            tearch_introuce.setVisibility(View.INVISIBLE);
            persontal_nodata_text_replay.setVisibility(View.VISIBLE);
            persontal_nodata_img_replay.setVisibility(View.VISIBLE);
        }




        relat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeacherJianJie.this.finish();
            }
        });

    }
}

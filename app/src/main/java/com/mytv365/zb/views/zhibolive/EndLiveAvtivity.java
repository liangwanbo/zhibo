package com.mytv365.zb.views.zhibolive;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.utils.Blur;
import com.mytv365.zb.utils.GlideUtils;
import com.mytv365.zb.views.zhibolive.livebase.BaseActivity;
import com.mytv365.zb.widget.CircleImageView;


/**
 * Created by Administrator on 2016/9/20.
 *
 * 梁万波
 */
public class EndLiveAvtivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout  reality;
    private CircleImageView head_icon;
    private ImageView image_cha,image_bg,imageback;
    private TextView host_name,member_counts;
    private String name,headimage,backCov;
    @Override
    public int getLayout() {
        return R.layout.endlive_activity;
    }

    @Override
    public void getinit() {
        Intent inten=this.getIntent();
        name=inten.getStringExtra("name");
        headimage=inten.getStringExtra("headimage");
        backCov=inten.getStringExtra("backcov");

        init();

    }

    @Override
    public int getcolor() {
        return R.color.quantouming;
    }



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void init(){
        reality=(RelativeLayout)this.findViewById(R.id.reality);
        head_icon=(CircleImageView)this.findViewById(R.id.head_icon);

        image_bg=(ImageView)this.findViewById(R.id.image_bg);
        image_cha=(ImageView)this.findViewById(R.id.image_cha);
        host_name=(TextView)this.findViewById(R.id.host_name);
        host_name.setText(name);
        member_counts=(TextView)this.findViewById(R.id.member_counts);
        image_cha.setOnClickListener(this);
        //GlideUtils.loadBlurImage(EndLiveAvtivity.this,"",imageback);

        if(!TextUtils.isEmpty(headimage)){

            GlideUtils.loadImage(EndLiveAvtivity.this,headimage,head_icon);

        }else{
            head_icon.setImageResource(R.drawable.ccd);
        }



        if(!TextUtils.isEmpty(backCov)){

            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int a=wm.getDefaultDisplay().getWidth();
            int b=wm.getDefaultDisplay().getHeight();


            GlideUtils.loadBlurImage(EndLiveAvtivity.this,backCov,image_bg);

        }else{
            //image_bg.setImageResource(R.drawable.live_bg);
            Resources r = this.getResources();
            Bitmap bitmap=BitmapFactory.decodeResource(r, R.drawable.errou);
            image_bg.setImageBitmap(Blur.apply(bitmap));


        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.image_cha:

                EndLiveAvtivity.this.finish();

                break;

            default:
                break;

        }

    }
}

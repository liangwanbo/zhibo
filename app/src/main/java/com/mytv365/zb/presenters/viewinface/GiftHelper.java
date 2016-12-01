package com.mytv365.zb.presenters.viewinface;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mytv365.zb.R;
import com.mytv365.zb.presenters.Presenter;

/**
 * Created by yang on 2016/10/17 0017.
 *
 * gift
 */
public class GiftHelper extends Presenter {


    private String[] getGiftids = {"10","11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
    private Integer[] giftimags={R.drawable.live_gift_flower,R.drawable.live_gift_kiss,R.drawable.live_gift_heart,R.drawable.live_gift_cheers,R.drawable.live_gift_ring,R.drawable.live_gift_diamond,R.drawable.live_gift_phone,R.drawable.live_gift_god,R.drawable.live_gift_falali,R.drawable.live_gift_wasp,R.drawable.live_gift_plane,R.drawable.live_gift_youlun};

    private ImageView animImageview;
    private Animation simpleanim;
    private Animation myanim;
    private Animation simple_pingyi;
    private Context mcontext;


    public GiftHelper(Context context,  ImageView imageView) {
        this.mcontext = context;
        this.animImageview = imageView;
        simpleanim = AnimationUtils.loadAnimation(mcontext, R.anim.simple_bug);
        myanim = AnimationUtils.loadAnimation(mcontext, R.anim.mysimple_anim);
        simple_pingyi = AnimationUtils.loadAnimation(mcontext, R.anim.simple_pingyi);
    }


    public String setAnimImageviewResourceGetName(String mgiftid) {
//        animImageview.setImageResource(giftimags[0]);
        for (int i = 0; i < getGiftids.length; i++) {
            if (getGiftids[i].equals(mgiftid)) {
                Log.i("tag", "setAnimImageviewResourceGetName: "+getGiftids[i]+"位置"+i);
                animImageview.setImageResource(giftimags[i]);
            }
        }
        return null;
    }


    public void setimgAnimtion(int id) {
        String type=String.valueOf(id);
        if (type.equals("18")||type.equals("19")||type.equals("20")){
            animImageview.startAnimation(myanim);
            setAnimImageviewLister(myanim);
        }else if (type.equals("21")){
            animImageview.startAnimation(simple_pingyi);
            setAnimImageviewLister(simple_pingyi);
        }else{
            animImageview.startAnimation(simpleanim);
            setAnimImageviewLister(simpleanim);
        }
    }


    public void setAnimImageviewLister( Animation animtion) {
        animtion.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation){
                animImageview.clearAnimation();
                animImageview.invalidate();
                animImageview.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation){
            }
        });
    }


    @Override
    public void onDestory() {
    }

}

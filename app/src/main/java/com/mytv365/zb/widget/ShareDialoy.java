package com.mytv365.zb.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mytv365.zb.R;


/**
 * Created by Administrator on 2016/8/26.
 */
public class ShareDialoy {


    private Context context;
    private Dialog dialog;
    private Display display;
    private RelativeLayout share_re_wx,share_re_qq,share_re_qqk,share_re_wb,share_re_te,realay_share;


    public ShareDialoy(Context context){

        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

    }



    public ShareDialoy  builder(){

        View view= LayoutInflater.from(context).inflate(R.layout.share_dialog,null);
        view.setMinimumWidth(display.getWidth());
        share_re_wx=(RelativeLayout)view.findViewById(R.id.share_re_wx);
        share_re_qq=(RelativeLayout)view.findViewById(R.id.share_re_qq);
        share_re_qqk=(RelativeLayout)view.findViewById(R.id.share_re_qqk);
        share_re_wb=(RelativeLayout)view.findViewById(R.id.share_re_wb);
        share_re_te=(RelativeLayout)view.findViewById(R.id.share_re_te);
        realay_share=(RelativeLayout)view.findViewById(R.id.realay_share);


        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);


        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public ShareDialoy getWX(final View.OnClickListener listener){

        share_re_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });

        return this;
    }


    public ShareDialoy getqq(final View.OnClickListener listener){

        share_re_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);

            }
        });

        return this;
    }


    public ShareDialoy getqqk(final View.OnClickListener listener){

        share_re_qqk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);

            }
        });

        return this;
    }

    public ShareDialoy getwb(final View.OnClickListener listener){

        share_re_wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);

            }
        });

        return this;
    }

    public ShareDialoy gettwter(final View.OnClickListener listener){

        share_re_te.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });

        return this;
    }


    public ShareDialoy concel(final View.OnClickListener listener){

        realay_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });

        return this;
    }



    public ShareDialoy setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public void show() {

        dialog.show();
    }
}

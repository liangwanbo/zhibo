package com.mytv365.zb.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mytv365.zb.R;


/**
 * Created by Administrator on 2016/11/1.
 */
public class MyDialogs {

    private Display display;
    private Context context;
    private Dialog dialog;
    private RelativeLayout lLayout_bg;


    public MyDialogs(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();



    }


    public MyDialogs builder() {

        View view = LayoutInflater.from(context).inflate(
                R.layout.mydialog, null);
        lLayout_bg = (RelativeLayout) view.findViewById(R.id.lLayout_bg);

        return this;
    }



 public void Dismiss(){
     dialog.dismiss();
 }

    public void show() {
        //setLayout();
        dialog.show();
    }


}

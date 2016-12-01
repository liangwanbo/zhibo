package com.mytv365.zb.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mytv365.zb.R;


/**
 * Created by Administrator on 2016/11/3 0003.
 * yang
 */
public class SingleDialog extends AlertDialog{

    private Context mcontext;
    private int dialogtype;
    private RelativeLayout dialogview;
    private TextView live_dialog_right_btn_back, live_dialog_left_btn_replay,live_single_notice;
    private SingleDialogListener msingleDialogListener;
    private Dialog dialog;
    private TextView single_dialog_title;



    public SingleDialog(Context context,  int Dialog, SingleDialogListener singleDialogListener) {
        super(context);
        this.mcontext = context;
        this.dialogtype = Dialog;
        this.dialogview = (RelativeLayout) View.inflate(context, R.layout.live_single_dialog_layout, null);
        this.msingleDialogListener = singleDialogListener;
        dialog= new Dialog(mcontext,R.style.Mydialog);
        dialog.setCancelable(false);

        live_single_notice= (TextView) dialogview.findViewById(R.id.live_single_notice);
        live_dialog_right_btn_back = (TextView) dialogview.findViewById(R.id.live_dialog_right_btn_back);
        live_dialog_left_btn_replay = (TextView) dialogview.findViewById(R.id.live_dialog_left_btn_replay);
        single_dialog_title= (TextView) dialogview.findViewById(R.id.single_dialog_title);


        if (dialogtype==1){
            live_single_notice.setText("你的帐号在其他地方登录。");
        }

        if (dialogtype==2){
            single_dialog_title.setText("版本更新");
            live_single_notice.setText("发现新版本是否立即更新？");
            live_dialog_right_btn_back.setText("取消");
            live_dialog_left_btn_replay.setText("立即更新");
        }


        live_dialog_right_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msingleDialogListener.LiveCancelBtn();
            }
        });


        live_dialog_left_btn_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msingleDialogListener.LiveSureComfirm();
            }
        });

    }


    public void show(){
        dialog.setContentView(dialogview);
        dialog.show();
    }


    public void dismiss(){
        dialog.dismiss();
    }



    public interface SingleDialogListener {

        void LiveSureComfirm();

        void LiveCancelBtn();
    }


}

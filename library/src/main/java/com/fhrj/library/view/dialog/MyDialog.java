package com.fhrj.library.view.dialog;

/**
 * Created by zhangguohao on 16/8/15.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhrj.library.R;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class MyDialog extends AlertDialog{
    private AlertDialog dialog;
    private View viewDialog;
    /*内容*/
    private LinearLayout content;
    /*图标*/
    private ImageView ioc;
    /*关闭*/
    private ImageView x;
    /*标题*/
    private TextView titles;
    /*下边按钮区域*/
    private LinearLayout bottom;
    /*左边*/
    private TextView lefts;
    /*右边*/
    private TextView rights;
    public MyDialog(Context context, View view) {
        super(context);
        init(context,view);
    }

    private void init(Context context, View view){
        viewDialog = View.inflate(context, R.layout.public_dialog, null);
        content=(LinearLayout) viewDialog.findViewById(R.id.view);
        content.addView(view);
        titles=(TextView)viewDialog.findViewById(R.id.title);
        ioc=(ImageView) viewDialog.findViewById(R.id.ioc);
        x=(ImageView) viewDialog.findViewById(R.id.x);
        bottom=(LinearLayout)viewDialog.findViewById(R.id.bottom);
        lefts=(TextView)viewDialog.findViewById(R.id.left);
        rights=(TextView)viewDialog.findViewById(R.id.right);
        dialog = new AlertDialog.Builder(context).create();
        x.setOnClickListener(new MyOnClick(1));

    }
    /***
     * 只有标题内容的Dialog
     * @param title
     */
    public void initTile(String title){
        ioc.setVisibility(View.GONE);
        bottom.setVisibility(View.GONE);
        titles.setText(title);
    }

    /**
     * 没有图标 自定义按钮的Dialog
     * @param title
     * @param leftName 左边按钮名称
     * @param rightName 右边按钮名称
     * @param left 左边按钮点击事件
     * @param right 右边按钮点击事件
     */
    public void initTile(String title,String leftName,String rightName, View.OnClickListener left, View.OnClickListener right){
        ioc.setVisibility(View.GONE);
        titles.setText(title);
        lefts.setText(leftName);
        rights.setText(rightName);
        lefts.setOnClickListener(left);
        rights.setOnClickListener(right);
        lefts.setOnClickListener(left);

    }
    /**
     * 没有图标 自定义按钮的Dialog
     * @param title
     * @param rightName 右边按钮名称
     * @param right 右边按钮点击事件
     */
    public void initTile(String title,String rightName,  View.OnClickListener right){
        ioc.setVisibility(View.GONE);
        titles.setText(title);
        lefts.setVisibility(View.GONE);
        rights.setText(rightName);
        rights.setOnClickListener(right);
    }

    /***
     * 点击空白不消失
     * @param flag
     */
    public void setIsCancelable(boolean flag) {
        if (!flag) {
            dialog.setCancelable(false);
        }
    }

    /***
     * 显示对话框
     */
    public void showDialog() {
        dialog.setView(viewDialog, 0, 0, 0, 0);
        dialog.show();
        x.setOnClickListener(new MyOnClick(1));
    }
    public void dismissDialog() {
        dialog.dismiss();

    }


    /***
     * 点击事件
     */
    private class MyOnClick implements View.OnClickListener{
        private int item;
        public MyOnClick(int item){
            this.item=item;
        }
        @Override
        public void onClick(View v) {
            switch (item){
                case 1:
                    dismissDialog();
                    break;
            }
        }
    }


}


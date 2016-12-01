package com.mytv365.zb.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.gridview.ImageTextAdapter;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.model.ImageText;
import com.mytv365.zb.views.activity.user.LoginActivity;
import com.mytv365.zb.views.zhibolive.zhuboActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 主页功能
 */
public class FunctionActivity extends BaseActivity {
    private ImageView breaks;
    private GridView gridView;
    private BaseMAdapter<ImageText> imageTextAdapter;
    private String[] names;
    private TextView datatime;
    private int day;
    private TextView haoshu,xiangqiji;
    private String mWay;

    @Override
    public int bindLayout() {
        return R.layout.activity_function;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.theme_select);
        hiddeTitleBar();
        breaks = (ImageView) findViewById(R.id.breaks);
        gridView = (GridView) findViewById(R.id.gridview);
        datatime=(TextView)findViewById(R.id.datatime);
        haoshu=(TextView)findViewById(R.id.haoshu);
        xiangqiji=(TextView)findViewById(R.id.xiangqiji);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM");
        String data=sdf.format(new Date());
        datatime.setText(data);
        getDate();
        haoshu.setText(String.valueOf(day));
        xiangqiji.setText(riqi());
        Log.e("riqi",riqi());

    }

    @Override
    public void doBusiness(Context mContext) {
        imageTextAdapter = new BasicDataAdapter<ImageText>(new ImageTextAdapter(mContext));
        getData();
        onClick();
    }


    /**
     * 加载数据
     */
    private void getData() {

//        names = new String[]{"发布动态", "开始直播"};
//        int[] images = {R.mipmap.ioc_dt, R.mipmap.ioc_zb};
//        for (int i = 0; i < names.length; i++) {
//            ImageText imageText = new ImageText(images[i], names[i]);
//            imageTextAdapter.addItem(imageText);
//        }
//        gridView.setAdapter(imageTextAdapter);
//        onclickGRidView();

        names = new String[]{ "开始直播"};
        int[] images = { R.mipmap.ioc_zb};
        for (int i = 0; i < names.length; i++) {
            ImageText imageText = new ImageText(images[i], names[i]);
            imageTextAdapter.addItem(imageText);
        }
        gridView.setAdapter(imageTextAdapter);
        onclickGRidView();



    }

    /***
     * 初始化标题
     */
    public void initTitle(String title) {
        setWindowTitle(title, Gravity.CENTER);
        showTitleBar();
    }

    /**
     * 点击事件
     */
    private void onClick() {
        breaks.setOnClickListener(new MyOnClick(1));
    }

    /***
     * 点击事件
     */
    private class MyOnClick implements View.OnClickListener {
        private int item;

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1:
                    finish();
                    break;
            }
        }
    }

    /***
     * GRidView点击事件
     */
    public void onclickGRidView(){

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(Constant.getUser()!=null){

                    if(Constant.getUser().getRoleId().equals("2")){
                        if(names[position].equals("开始直播")){
                            Intent inten=new Intent(FunctionActivity.this, zhuboActivity.class);
                            startActivity(inten);
                            finish();
                        }

                    }else {
                        Toast.makeText(FunctionActivity.this,"你不是老师没有开播权限",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Intent inten=new Intent(FunctionActivity.this, LoginActivity.class);
                    startActivity(inten);
                    finish();
                }



            }
        });


    }


    public void getDate(){
        Calendar c = Calendar.getInstance();
        //year = c.get(Calendar.YEAR);
        //month = c.grt(Calendar.MONTH);
       day = c.get(Calendar.DAY_OF_MONTH);

    }

    public String riqi(){

        Calendar c = Calendar.getInstance();
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return "星期"+mWay;

    }




}

package com.mytv365.zb.views.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.gridview.ImageTextAdapter;
import com.mytv365.zb.model.ImageText;
import com.mytv365.zb.views.activity.PublishActivity;
import com.mytv365.zb.views.zhibolive.zhuboActivity;

/**
 * 主页功能
 */
public class FunctionActivity extends BaseActivity {
    private ImageView breaks;
    private GridView gridView;
    private BaseMAdapter<ImageText> imageTextAdapter;
    private String[] names;

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
        names = new String[]{"发布动态", "开始直播"};
        int[] images = {R.mipmap.ioc_dt, R.mipmap.ioc_zb};
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

                if(names[position].equals("开始直播")){
                    Intent inten=new Intent(FunctionActivity.this, zhuboActivity.class);
                    startActivity(inten);

                }else if(names[position].equals("发布动态")){
                    Intent inten=new Intent(FunctionActivity.this, PublishActivity.class);
                    startActivity(inten);


                }

            }
        });


    }
}

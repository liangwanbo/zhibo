package com.mytv365.zb.adapters.listview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseViewHolder;
import com.mytv365.zb.R;
import com.mytv365.zb.model.ArticleListBean;
import com.mytv365.zb.utils.ToolImage;
import com.mytv365.zb.views.WebPageActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 新闻
 * Created by zhangguohao on 16/9/13.
 */
public class NewsAdapter extends BaseViewHolder<ArticleListBean.Article> {
    private ImageView iv_img;
    private TextView tv_title;
    private TextView tv_source;
    private LinearLayout ll_article;
    private ImageLoader imageLoader;

    public NewsAdapter(Context mContext) {
        super(mContext);
        imageLoader = ToolImage.init(mContext);
    }

    @Override
    public BaseViewHolder<ArticleListBean.Article> createViewHolder() {
        return new NewsAdapter(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_news;
    }

    @Override
    public void initView(View view) {
        iv_img = (ImageView) findViewById(R.id.iv_img);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_source = (TextView) findViewById(R.id.tv_source);
        ll_article = (LinearLayout) findViewById(R.id.ll_article);
    }

    @Override
    public void fillData(final ArticleListBean.Article rowData, int position) {
        if(!rowData.getImage().equals("")){
            iv_img.setVisibility(View.VISIBLE);
            imageLoader.displayImage(rowData.getImage(), iv_img);
        }else{
            iv_img.setVisibility(View.GONE);
            Log.i("test","rowData.getImage()--"+rowData.getImage());
        }


        imageLoader.displayImage(rowData.getImage(), iv_img);
        tv_title.setText(rowData.getTitle());
        tv_source.setText(rowData.getSource());
        ll_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, WebPageActivity.class);
                intent.putExtra("url", rowData.getInfoUrl());
                intent.putExtra("isShowShare",false);
                mContext. startActivity(intent);

//                Intent intent = new Intent(mContext, NewsDetailActivity.class);
//                intent.putExtra("url", rowData.getInfoUrl());
//                intent.putExtra("title", rowData.getTitle());
//                mContext.startActivity(intent);



            }
        });
    }
}

package com.mytv365.zb.adapters.gridview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseViewHolder;
import com.mytv365.zb.R;
import com.mytv365.zb.model.ImageText;

/**
 * 图文
 * Created by zhangguohao on 16/9/12.
 */
public class ImageTextAdapter extends BaseViewHolder<ImageText> {
    private ImageView image;
    private TextView name;

    public ImageTextAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseViewHolder<ImageText> createViewHolder() {
        return new ImageTextAdapter(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_image_view;
    }

    @Override
    public void initView(View view) {
        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
    }

    @Override
    public void fillData(ImageText rowData, int position) {
        name.setText(rowData.getName());
        image.setImageDrawable(ContextCompat.getDrawable(mContext, rowData.getImage()));
    }
}

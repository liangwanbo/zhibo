package com.mytv365.zb.adapters.listview;

import android.content.Context;
import android.view.View;

import com.fhrj.library.base.impl.BaseViewHolder;
import com.mytv365.zb.R;
import com.mytv365.zb.model.Dynamic;

/**
 * 动态
 * Created by zhangguohao on 16/9/18.
 */
public class DynamicAdapter extends BaseViewHolder<Dynamic>{
    public DynamicAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseViewHolder<Dynamic> createViewHolder() {
        return new DynamicAdapter(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_dynamic;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void fillData(Dynamic rowData, int position) {

    }
}

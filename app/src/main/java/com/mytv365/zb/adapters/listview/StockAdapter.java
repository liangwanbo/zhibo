package com.mytv365.zb.adapters.listview;

import android.content.Context;
import android.view.View;

import com.fhrj.library.base.impl.BaseViewHolder;
import com.mytv365.zb.R;
import com.mytv365.zb.model.Stock;

/**
 * 股票
 * Created by zhangguohao on 16/9/18.
 */
public class StockAdapter extends BaseViewHolder<Stock> {

    public StockAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public BaseViewHolder<Stock> createViewHolder() {
        return new StockAdapter(mContext);
    }

    @Override
    public int bindLayout() {
        return R.layout.item_stock;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void fillData(Stock rowData, int position) {

    }
}

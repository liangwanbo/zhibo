package com.mytv365.zb.adapters.listview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseViewHolder;
import com.mytv365.zb.R;
import com.mytv365.zb.model.MoneyGold;

/**
 * Created by Administrator on 2016/11/10 0010.
 *  yang
 */
public class GoldAdapter  extends BaseViewHolder<MoneyGold>{


    private TextView pay_item_moneyNum,pay_item_glodNum;
    private ImageView pay_item_layout_img_golds;
    private Context context;


    public GoldAdapter(Context mContext) {
        super(mContext);
        this.context=mContext;
    }

    @Override
    public BaseViewHolder<MoneyGold> createViewHolder() {
        return new GoldAdapter(context);
    }


    @Override
    public int bindLayout() {
        return R.layout.persontal_item_layout_gold_money;
    }


    @Override
    public void initView(View view) {
        pay_item_moneyNum= (TextView) view.findViewById(R.id.pay_item_moneyNum);
        pay_item_glodNum= (TextView) view.findViewById(R.id.pay_item_glodNum);
        pay_item_layout_img_golds= (ImageView) view.findViewById(R.id.pay_item_layout_img_golds);

    }


    @Override
    public void fillData(MoneyGold rowData, int position) {
        pay_item_moneyNum.setText(rowData.getMoneyNum());
        pay_item_glodNum.setText(rowData.getGoldNum());
        pay_item_layout_img_golds.setImageResource(rowData.getResicon());
    }


}

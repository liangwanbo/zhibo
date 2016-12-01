package com.mytv365.zb.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.listview.GoldAdapter;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.model.MoneyGold;

import java.util.ArrayList;
import java.util.List;

public class PersonPayMoneyActivity extends BaseActivity{

    private BaseMAdapter<MoneyGold> GoldAdapter;
    private ListView pay_money_listview;
    private List<MoneyGold> moneyGoldList = new ArrayList<>();
    private TextView pay_money_last;


    @Override
    public int bindLayout() {
        return R.layout.activity_person_pay_money;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        initTitle("充值金币");
        pay_money_listview = (ListView) findViewById(R.id.pay_money_listview);
        pay_money_last= (TextView) findViewById(R.id.pay_money_last);
        Log.i(TAG, "initView: 用户余额"+Constant.getUser().getBalance());
        pay_money_last.setText(Constant.getUser().getBalance());

    }

    @Override
    public void doBusiness(Context mContext) {
        GoldAdapter = new BasicDataAdapter<>(new GoldAdapter(this));
        initMoenyList();
        GoldadapterListener();

    }


    private void initMoenyList() {
        MoneyGold moneyGold = new MoneyGold();
        moneyGold.setResicon(R.drawable.golds);
        moneyGold.setGoldNum("60金币");
        moneyGold.setMoneyNum("6元");
        moneyGold.setMoney("60");
        moneyGoldList.add(moneyGold);

        MoneyGold moneyGold1 = new MoneyGold();
        moneyGold1.setResicon(R.drawable.golds);
        moneyGold1.setGoldNum("680金币");
        moneyGold1.setMoneyNum("68元");
        moneyGold1.setMoney("680");
        moneyGoldList.add(moneyGold1);

        MoneyGold moneyGold2 = new MoneyGold();
        moneyGold2.setResicon(R.drawable.golds);
        moneyGold2.setGoldNum("1280金币");
        moneyGold2.setMoneyNum("128元");
        moneyGold2.setMoney("1280");

        moneyGoldList.add(moneyGold2);

        MoneyGold moneyGold3 = new MoneyGold();
        moneyGold3.setResicon(R.drawable.golds);
        moneyGold3.setGoldNum("2680金币");
        moneyGold3.setMoneyNum("268元");
        moneyGold3.setMoney("2680");
        moneyGoldList.add(moneyGold3);

        MoneyGold moneyGold4 = new MoneyGold();
        moneyGold4.setResicon(R.drawable.golds);
        moneyGold4.setGoldNum("5180金币");
        moneyGold4.setMoneyNum("518元");
        moneyGold4.setMoney("5180");
        moneyGoldList.add(moneyGold4);

        MoneyGold moneyGold5 = new MoneyGold();
        moneyGold5.setResicon(R.drawable.golds);
        moneyGold5.setGoldNum("9980金币");
        moneyGold5.setMoneyNum("998元");
        moneyGold5.setMoney("9880");
        moneyGoldList.add(moneyGold5);


        for (MoneyGold mo : moneyGoldList) {
            GoldAdapter.addItem(mo);
        }

        pay_money_listview.setAdapter(GoldAdapter);

    }


    private void GoldadapterListener() {
        pay_money_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MoneyGold moneyGold =moneyGoldList.get(i);
                Intent intent=new Intent(PersonPayMoneyActivity.this,PersonPayActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("money",moneyGold);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }



    /**
     * 标题栏
     *
     * @param title
     */
    private void initTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }






    @Override
    protected void onResume() {
        super.onResume();
        pay_money_last.setText(Constant.getUser().getBalance());
    }





}

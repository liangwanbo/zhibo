package com.mytv365.zb.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/10 0010.
 * yang
 */
public class MoneyGold  implements Serializable{

    private int resicon;
    private String goldNum;
    private String moneyNum;
    private String money;



    public int getResicon() {
        return resicon;
    }

    public void setResicon(int resicon) {
        this.resicon = resicon;
    }

    public String getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(String goldNum) {
        this.goldNum = goldNum;
    }

    public String getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(String moneyNum) {
        this.moneyNum = moneyNum;
    }
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


}


package com.mytv365.zb.wxapi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.mytv365.zb.R;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.model.MoneyGold;
import com.mytv365.zb.presenters.PayHelper;
import com.mytv365.zb.presenters.viewinface.PayView;
import com.mytv365.zb.utils.ToolNetwork;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class WXPayEntryActivity extends BaseActivity implements View.OnClickListener, PayView,IWXAPIEventHandler {

    private boolean isChooseWX = false;
    private boolean isChooseZFB = false;
    private ImageView pay_choose_zfb;
    private ImageView pay_choose_wx;
    private RelativeLayout pay_choose_layout_zfb, pay_choose_layout_wx;
    private Button pay_sure_btn;
    private String NetWorkType;
    private String phoneIP;
    private PayHelper payHelper;
    private TextView pay_show_money, pay_show_golds;
    private MoneyGold moneyGold;
    private IWXAPI msgApi;
    // 将该app注册到微信
    private boolean registerboolean;


    @Override
    public int bindLayout() {
        return R.layout.activity_person_pay;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        initTitle("购买金币");
        pay_choose_zfb = (ImageView) findViewById(R.id.pay_choose_zfb);
        pay_choose_wx = (ImageView) findViewById(R.id.pay_choose_wx);
        pay_sure_btn = (Button) findViewById(R.id.pay_sure_btn);
        pay_choose_layout_wx = (RelativeLayout) findViewById(R.id.pay_choose_layout_wx);
        pay_choose_layout_zfb = (RelativeLayout) findViewById(R.id.pay_choose_layout_zfb);
        pay_show_golds = (TextView) findViewById(R.id.pay_shwo_golds);
        pay_show_money = (TextView) findViewById(R.id.pay_show_money);


        pay_sure_btn.setOnClickListener(this);
        pay_choose_layout_wx.setOnClickListener(this);
        pay_choose_layout_zfb.setOnClickListener(this);
        payHelper = new PayHelper(this, this, this);


    }


    @Override
    public void doBusiness(Context mContext) {
        ToolNetwork network = new ToolNetwork(mContext);
        NetWorkType = network.getNetworkType();
        if (NetWorkType.equals(ToolNetwork.NETWORK_WIFI)) {
            phoneIP = getWifiIpNetwork();
        } else {
            phoneIP = GetHostIp();
        }


        pay_choose_wx.setImageResource(R.drawable.person_select_pressed);
//        pay_choose_zfb.setImageResource(R.drawable.person_select_pressed);

        isChooseWX = true;
        moneyGold = (MoneyGold) getIntent().getExtras().getSerializable("money");
//        Log.i(TAG, "doBusiness: moneyGold" + moneyGold.toString());

        if (moneyGold != null) {
            pay_show_money.setText(moneyGold.getMoneyNum());
            pay_show_golds.setText(moneyGold.getGoldNum());
        }



        msgApi = WXAPIFactory.createWXAPI(this, "wxa0d5abb059674e4c");
        msgApi.registerApp("wxa0d5abb059674e4c");
        msgApi.handleIntent(getIntent(), this);

//        msgApi.registerApp("wxb4ba3c02aa476ea1");


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_choose_layout_wx:
                pay_choose_wx.setImageResource(R.drawable.person_select_pressed);
                pay_choose_zfb.setImageResource(R.drawable.person_select_default);
                isChooseWX = true;
                isChooseZFB=false;
                Toast.makeText(WXPayEntryActivity.this, "微信支付", Toast.LENGTH_SHORT).show();
                break;

            case R.id.pay_choose_layout_zfb:

                isChooseWX=false;
                isChooseZFB=true;
                pay_choose_zfb.setImageResource(R.drawable.person_select_pressed);
                pay_choose_wx.setImageResource(R.drawable.person_select_default);


                break;

            case R.id.pay_sure_btn:

                if (isChooseWX){
//                    Toast.makeText(WXPayEntryActivity.this, "微信支付", Toast.LENGTH_LONG).show();
                    payHelper. PayByWX(phoneIP, moneyGold.getMoney(), msgApi);
                }

                if (isChooseZFB){
//                    Toast.makeText(WXPayEntryActivity.this, "支付宝支付", Toast.LENGTH_LONG).show();
                    payHelper.PayByZhi(phoneIP, moneyGold.getMoney());
                }
                break;
        }
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


    private String getWifiIpNetwork() {
        // 获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);

        return ip;

    }


    private String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }


    public static String GetHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
        } catch (Exception e) {
        }
        return null;
    }


    @Override
    public void PaySuccess(String goldNum) {
        Log.i(TAG, "PaySuccess: 支付金币成功PersonPayActivity");
        Log.i(TAG, "PaySuccess: 支付金币成功PersonPayActivity充值金币数" + goldNum);
        String balnace = String.valueOf(Double.valueOf(Constant.getUser().getBalance()) + Double.valueOf(goldNum));
        Constant.getUser().setBalance(balnace);
        Log.i(TAG, "PaySuccess: 支付金币成功PersonPayActivity账户余额" + Constant.getUser().getBalance());

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "PaySuccess: 设置的支付结果是"+Constant.wxPaySuccess);
        if (Constant.wxPrice != null&&Constant.wxPaySuccess) {
            Log.i(TAG, "PaySuccess: 支付金币成功PersonPayActivity");
            Log.i(TAG, "PaySuccess: 支付金币成功PersonPayActivity充值金币数" + Constant.wxPrice);
            String balnace = String.valueOf(Double.valueOf(Constant.getUser().getBalance()) + Double.valueOf(Constant.wxPrice));
            Constant.getUser().setBalance(Constant.wxPrice);
            Log.i(TAG, "PaySuccess: 支付金币成功PersonPayActivity账户余额" + Constant.getUser().getBalance());
            Constant.wxPaySuccess=false;

        }

    }


    @Override
    public void PayFail() {
    }


    public String getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }


    @Override
    public void onReq(BaseReq req) {

        Toast.makeText(this, "openid = " + req.openId, Toast.LENGTH_SHORT).show();
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                Toast.makeText(this, "微信支付失败", Toast.LENGTH_SHORT).show();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                Toast.makeText(this, "微信支付失败", Toast.LENGTH_SHORT).show();
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                Toast.makeText(this, "微信支付失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }

    }


    @Override
    public void onResp(BaseResp resp) {

        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        int result = 0;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                Constant.wxPaySuccess=true;
                Log.i(TAG, "PaySuccess: 设置的支付结果为支付成功"+Constant.wxPaySuccess);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Constant.wxPaySuccess=false;
                Log.i(TAG, "PaySuccess: 设置的支付结果为支付成功"+Constant.wxPaySuccess);
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Constant.wxPaySuccess=false;
                Log.i(TAG, "PaySuccess: 设置的支付结果为支付成功"+Constant.wxPaySuccess);
                result = R.string.errcode_deny;
                break;

            default:
                Constant.wxPaySuccess=false;
                Log.i(TAG, "PaySuccess: 设置的支付结果为支付成功"+Constant.wxPaySuccess);
                result = R.string.errcode_unknown;
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        msgApi.handleIntent(intent, this);
    }



}


package com.mytv365.zb.presenters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.PayResult;
import com.mytv365.zb.presenters.viewinface.PayView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2016/11/9 0009.
 * <p>
 * 阳志 :支付处理Presenter
 */
public class PayHelper extends Presenter {

    private Context mcontext;
    private Activity mactivity;
    private Handler mhandler;
    private static final int SDK_PAY_FLAG = 1;  //支付宝支付
    private static final int SDK_AUTH_FLAG = 2;
    private PayView mPayview;
    private String mPrice;


    public PayHelper(Context context, Activity activity, PayView payview) {

        this.mactivity = activity;
        this.mcontext = context;
        this.mPayview = payview;

    }


    public void PayByZhi(String ip, String price) {
        mPrice = null;
        mPrice = price;
        OkGo.post(HttpUrl.CreateOrderUrl)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("userTokenId", "1")
                .params("commodityid", "100")
                .params("commodityprice", "0.1")
                .params("commoditynumber", price)
                .params("commodityname", "金币")
                .params("commoditydescribe", "又香又甜")
                .params("commoditytype", "水果")
                .params("ip", ip)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            int resultCode = object.getInt("resultCode");
                            if (resultCode == 100) {
                                String resultData = object.getString("resultData");
                                if (!resultData.equals("")) {
                                    OkGo.post(HttpUrl.ZhiFuBaoPayUrl)
                                            .headers("Connection", "close")
                                            .headers("header1", "headerValue1")
                                            .params("userTokenId", "1")
                                            .params("orderNumber", resultData)
                                            .setCertificates()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(String s, Call call, Response response) {
                                                    try {
                                                        JSONObject object1 = new JSONObject(s);
                                                        int resultCode = object1.getInt("resultCode");
                                                        if (resultCode == 100) {
                                                            final String myorder = object1.getString("resultData");
                                                            Log.i("tags", "支付宝订单信息" + myorder.toString());
                                                            //生成订单后请求支付操作
                                                            Runnable payRunnable = new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    PayTask alipay = new PayTask(mactivity);
                                                                    Map<String, String> result = alipay.payV2(myorder, true);
                                                                    Log.i("tags", "支付结果" + result.toString());
                                                                    Message msg = new Message();
                                                                    msg.what = SDK_PAY_FLAG;
                                                                    msg.obj = result;
                                                                    handle.sendMessage(msg);
                                                                }
                                                            };
                                                            Thread payThread = new Thread(payRunnable);
                                                            payThread.start();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onError(Call call, Response response, Exception e) {
                                                    super.onError(call, response, e);
                                                    Log.i("WXPayEntryActivity", "onError: " + "生成支付订单失败" + e);
                                                }
                                            });
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Log.i("WXPayEntryActivity", "onError: " + "创建订单失败" + e);
                        Toast.makeText(mcontext, "创建订单失败", Toast.LENGTH_SHORT).show();
                        super.onError(call, response, e);
                    }
                });
    }


    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(mcontext, "支付成功", Toast.LENGTH_SHORT).show();
                        Log.i("WXPayEntryActivity", "支付成功");

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(mcontext, "支付失败", Toast.LENGTH_SHORT).show();
                        Log.i("WXPayEntryActivity", "支付失败");
                    }

                    SendZhiFubaoPayCallback(payResult.toString());
                    break;
            }
        }

    };


    public void SendZhiFubaoPayCallback(String resultDate) {
        Log.i("WXPayEntryActivity", resultDate);
        OkGo.post(HttpUrl.ZhuFuBaoPayCallback)
                .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                .headers("header1", "headerValue1")
                .params("userTokenId", "1")
                .params("resultDate", resultDate)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            int resultCode = obj.getInt("resultCode");
                            Log.i("WXPayEntryActivity", "支付后台返回码" + resultCode);
                            if (resultCode == 9000) {
                                Toast.makeText(mcontext, "充值成功", Toast.LENGTH_SHORT).show();
                                mPayview.PaySuccess(mPrice);
                            } else if (resultCode == 6001) {
                                Toast.makeText(mcontext, "取消操作", Toast.LENGTH_SHORT).show();
                                mPayview.PayFail();
                            } else {

                                Toast.makeText(mcontext, "操作失败", Toast.LENGTH_SHORT).show();
                                mPayview.PayFail();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }



                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(mcontext, "操作失败", Toast.LENGTH_SHORT).show();
                        mPayview.PayFail();
                        Log.i("WXPayEntryActivity", "支付失败" + e);
                    }
                });


    }


    public void PayByWX(String ip, String price,final IWXAPI msgApi) {
        mPrice = null;
        mPrice = price;
        OkGo.post(HttpUrl.CreateOrderUrl)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("userTokenId", "1")
                .params("commodityid", "100")
                .params("commodityprice", "0.1")
                .params("commoditynumber",price)
                .params("commodityname", "金币")
                .params("commoditydescribe", "金币")
                .params("commoditytype", "水果")
                .params("ip", ip)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {

                            JSONObject object = new JSONObject(s);
                            int resultCode = object.getInt("resultCode");
                            if (resultCode == 100) {
                                String resultData = object.getString("resultData");
                                Log.i("WX", "onSuccess:  创建的订单号是：" + resultData);
                                if (!resultData.equals("")) {
                                    OkGo.post(HttpUrl.WeiXinPayUrl)
                                            .headers("Connection", "close")
                                            .headers("header1", "headerValue1")
                                            .params("userTokenId", "1")
                                            .params("orderNumber", resultData)
                                            .setCertificates()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(String s, Call call, Response response) {
                                                    Log.i("WX", "微信订单提交返回" + s);
                                                    try {
                                                        JSONObject object1 = new JSONObject(s);
                                                        int resultCode = object1.getInt("resultCode");
                                                        if (resultCode == 100) {
                                                            JSONObject objectorder = object1.getJSONObject("resultData");
//                                                            final String myorder = object1.getString("resultData");
                                                            Log.i("WX", "微信订单信息" + objectorder.toString());
                                                            //启动微信
//                                                            msgApi.openWXApp();
//                                                          //生成订单后请求支付操作
                                                            PayReq request = new PayReq();
                                                            request.appId = objectorder.getString("appid");
                                                            request.partnerId = objectorder.getString("partnerid");
                                                            request.prepayId = objectorder.getString("prepayid");
                                                            request.packageValue = "Sign=WXPay";
                                                            request.nonceStr = objectorder.getString("noncestr");
                                                            request.timeStamp = objectorder.getString("timestamp");
                                                            request.sign = objectorder.getString("sign");
                                                            msgApi.sendReq(request);
//                                                            mPayview.PaySuccess(mPrice);
                                                            Constant.wxPrice=mPrice;

                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onError(Call call, Response response, Exception e) {
                                                    super.onError(call, response, e);
                                                    Log.i("WX", "onError: " + "生成支付订单失败" + e);
                                                }
                                            });

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        Log.i("WX", "onError: " + "创建订单失败" + e);
                        super.onError(call, response, e);
                    }
                });
    }


    @Override
    public void onDestory() {
        mcontext = null;
    }

}

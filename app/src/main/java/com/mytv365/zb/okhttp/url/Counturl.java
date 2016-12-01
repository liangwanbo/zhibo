package com.mytv365.zb.okhttp.url;

/**
 * Created by Administrator on 2016/9/5.
 */
public class Counturl {

    public static final String LOGIN_URL="http://192.168.0.42:8080/showlive-appserver/userVerify/userLogin";//登录url
    public static final String YZM_URL="http://192.168.0.42:8080/showlive-appserver/userVerify/sendVerificationCode";//验证码地址
    public static final String REGISTER_URL="http://192.168.0.42:8080/showlive-appserver/userVerify/registered";
    public static final String EDIT_PASSWORD_URL="http://192.168.0.42:8080/showlive-appserver/userVerify/updatePassWord";
    public static final String GETSIG_URL="http://192.168.0.42:8080/showlive-appserver/liveYun/generateSig";//获取信令
    public static final String SZ_ZHUBO_URL="http://192.168.0.42:8080/showlive-appserver/liveYun/startLive";//开播时主播设置直播间名称，直播流
    public static final String ZB_IMAGEVIEW="http://192.168.0.42:8080/showlive-appserver/liveYun/upload";//上传直播间图片
    public static final String LIE_BIAO_LIST="http://192.168.0.42:8080/showlive-appserver/room/list";//首页列表
    public static final String APPLY_ZB="http://192.168.0.42:8080/showlive-appserver/doUpdateUserType";//申请主播
    public static final String GUANZHU="http://192.168.0.42:8080/showlive-appserver/concerns/doAddConcern";//关注
    public static final String GIFT_LIST="http://192.168.0.42:8080/showlive-appserver/giftListApp";//礼物列表
    public static final String CREAT_ORDER="http://192.168.0.42:8080/showlive-appserver/addOrder";//创建订单页面
    public static final String H5="http://192.168.0.42:8080/showlive-appserver/rechargeWXH5";//通过订单ID获取h5地址
    public static final String SEND_GIFT="http://192.168.0.42:8080/showlive-appserver/sendGift";//发送礼物
}

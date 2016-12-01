package com.mytv365.zb.http;

/**
 * 请求地址
 * Created by zhangguohao on 16/9/19.
 */
public class HttpUrl {

    //http://192.168.0.47/Arena_App/follow/getFollowListByFollower?currentPage=0&pageSize=10000
//    private static String url = "http://yhzb.mytv365.com/Arena_App/";
//    private static String url = "http://192.168.0.69/Arena_App/";
    private static String url = "http://yhzb.mytv365.com/Arena_App/";
//    private static String url = "http://192.168.0.47/Arena_App/";

    public static String liveList = url + "liveRoom/list";
    /*登录*/
    public static String login = url + "login";
    /*注册*/
    public static String register = url + "/user/registered";
    /*发送验证码*/
    public static String registerCode = url + "/user/sendSecurityCode";
    /*上传直播图片*/
    public static String updateImage = url + "liveRoom/uploadPhoto";
    /*开启直播*/
    public static String statrtlive = url + "liveRoom/startLive";
    /*结束直播*/
    public static String endlive = url + "liveRoom/overLive";
    /*录制信息*/
    public static String RememberInfo = url + "video/add";
    /*直播礼物列表*/
    public static String gifts = url + "gift/getGiftList";
    /*文章列表*/
    public static final String articleList = url + "article/getArticleListByTypeId";
    /*文章详情*/
    public static final String articleDetail = url + "article/getArticleInfoById";
    /*文章评论*/
    public static final String articleComment = url + "articleComment/insertArticleComment";
    /*修改密码*/
    public static final String changePWD = url + "user/updatePwd";
    /*意见反馈*/
    public static final String feedback = url + "feedback/add";

    public static String userSendHeadfaceUrl = url + "user/uploadHead";

    public static String bianjiinfo = url + "user/uploadUser";




   /* *//*取消关注*//*
    public static String CancelaudienceUrl=url+"follow/cancelFollow";
    *//*添加关注*//*
    public static String audienceUrl=url+"follow/insertFollowComment";
    *//*我的关注*//*
    public static String myAudienceUrl=url+"message/list";
    *//*查询关注列表*//*
    public static String SelectAudienceUrl=url+"follow/getFollowListByCreateUser";
    *//*查询粉丝列表*//*
    public static String SelectByAudienUrl=url+"follow/getFollowListByFollower";
    *//*关注关系*//*
    public static String AcudienGuanxUrl=url+"follow/getFollowStatus";*/



    /*精彩回顾*/
    public static String jingcaihuigu = url + "video/getVideoList";
    /*个人主页*/
    public static String PersontalIndexInfoUrl = url + "user/personIndex";
    /*取消关注*/
    public static String CancelaudienceUrl = url + "follow/cancelFollow";
    /*添加关注*/
    public static String audienceUrl = url + "follow/insertFollowComment";
    /*我的关注*/
    public static String myAudienceUrl = url + "message/list";
    /*查询关注列表*/
    public static String SelectAudienceUrl = url + "follow/getFollowListByCreateUser";
    /*查询粉丝列表*/
    public static String SelectByAudienUrl = url + "follow/getFollowListByFollower";
    /*关注关系*/
    public static String AcudienGuanxUrl = url + "follow/getFollowStatus";
    /*老师信息*/
    public static String taacherinfo = url + "teacher/getTeacherIndex";
    /*修改密码*/
    public static String XGpassweor = url + "user/updatePwd";
    /*忘记密码*/
    public static String Wpassword = url + "/user/forgot";
    /*发送礼物*/
    public static String SendGiftUrl = url + "giftRecord/insertGiftRecord";
    /*精彩回顾*/
    public static String deleteVideo =url + "video/deleteVideo";
    /*分享*/
    public static String fenxiang=url + "liveRoom/ShareHtml";
    /*关于我们*/
    public static String PersontalAboutWeUrl=url+"about.html";




    /*创建订单*/
    public static String CreateOrderUrl="http://test2.mytv365.com:8082/Arena_App/rechanger/create";
    /*支付宝支付*/
    public static String ZhiFuBaoPayUrl="http://test2.mytv365.com:8082/Arena_App/rechanger/appOrderInformationa";
    /*支付宝同步回调*/
    public static String ZhuFuBaoPayCallback="http://test2.mytv365.com:8082/Arena_App/rechanger/appVerificationa";
    /*微信支付*/
    public static String WeiXinPayUrl="http://test2.mytv365.com:8082/Arena_App/rechanger/appOrderInformationw";
//        public static String WeiXinPayUrl="http://192.168.0.169:8080/Arena_App/rechanger/appOrderInformationw";
    /*版本检查*/
    public static String AppVersionCheckUrl="http://yhzb.mytv365.com/apk/apk.json";



}

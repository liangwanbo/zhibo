package com.mytv365.zb.okhttp.url;

/**
 * 用户接口
 * Created by Sunshine on 2016/5/12.
 */
public enum UserMethod {
    /**
     * 登陆
     */
    LOGIN("/user/login"),
    /**
     * 退出
     */
    LOGOUT("/user/loginout"),
    /**
     * 注册
     */
    REGISTER("/user/register"),
    /**
     * 手机修改密码
     */
    P_PASSWORD("/findpwd/updatepwdphone"),
    /**
     * 邮箱修改密码
     */
    E_PASSWORD("/findpwd/updatepwdemail"),
    /**
     * 发送邮件
     */
    SEND_EMAIL("/findpwd/sendemail"),
    /**
     * 用户习惯
     */
    HABIT("/user/habit");

    private String value;

    private UserMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

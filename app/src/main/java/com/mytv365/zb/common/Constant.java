package com.mytv365.zb.common;

import android.net.Uri;

import com.mytv365.zb.model.LoginTeacher;
import com.mytv365.zb.model.User;

/**
 * Created by zhangguohao on 16/9/20.
 */
public class Constant {
    /*用户信息*/
    public static User user;
    public static int id;
    public static String liveroom="47";
    public static boolean IsExitLogin=false;
    public static String CONCERNPAGE="1";
    public static String CONCERNPAGESIZE="10000";
    public static String sig=null;
    public static Uri CopyURI=null;
    public static String isFor="";
    public static boolean isvercationReplay=false;
    public static String userGold;

    public static boolean isfirstJoin=false;
    public static String wxPrice;
    public static boolean wxPaySuccess=false;


    public static LoginTeacher loginTeacher=null;



    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Constant.user = user;
    }

    public static String getToken() {
        if (user != null) {
            return user.getToKen();
        } else {
            return "";
        }
    }

    public static String Userurl=null;

}

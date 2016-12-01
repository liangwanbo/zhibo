package com.mytv365.zb.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/30
 * Description:
 */

public class AppUtils {
    private static final String unknowVersion = "????";

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return unknowVersion;
    }
}

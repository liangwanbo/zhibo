package com.fhrj.library.tools;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.fhrj.library.R;
import com.fhrj.library.config.SysEnv;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 工具
 *
 * @author ZhangGuoHao
 * @date 2016年5月11日 下午7:47:10
 */
public class Tool {
    /**
     * 　　* 从服务器取图片
     * 　　*http://bbs.3gstdy.com
     * 　　* @param url
     * 　　* @return
     *
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String timeToString(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(new Date(Long.parseLong(time)));
    }


    public static String timeToString(String time, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);

        return sdf.format(new Date(Long.parseLong(time)));
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过第二个参数(文件名)来判断，是否本地有此图片
            drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), null);
        } catch (IOException e) {
        }
        if (drawable == null) {
        } else {
        }

        return drawable;
    }

    /***
     * 倒计时
     *
     * @param textView
     * @param re
     */
    public static void sendCode(final Context context, final Button textView,
                                final int re) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int recLen = re;

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void run() {
                recLen--;
                if (recLen > 0) {
                    textView.setClickable(false);
                    textView.setText("剩余" + recLen + "秒");
                    if (SysEnv.SDK_API > 15) {
                        textView.setBackground(context.getResources()
                                .getDrawable(R.drawable.button));
                    } else {
                        textView.setBackgroundDrawable(context.getResources()
                                .getDrawable(R.drawable.button));
                    }
                    handler.postDelayed(this, 1000);

                } else {
                    if (SysEnv.SDK_API > 15) {
                        textView.setBackground(context.getResources()
                                .getDrawable(R.drawable.button));
                    } else {
                        textView.setBackgroundDrawable(context.getResources()
                                .getDrawable(R.drawable.button));
                    }
                    textView.setText("获取验证码");

                    textView.setClickable(true);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    /***
     * 调用系统信息
     *
     * @param cls 系统信息 com.android.settings.AccessibilitySettings 辅助功能设置
     *            com.android.settings.ActivityPicker 选择活动
     *            com.android.settings.ApnSettings APN设置
     *            com.android.settings.ApplicationSettings 应用程序设置
     *            com.android.settings.BandMode 设置GSM/UMTS波段
     *            com.android.settings.BatteryInfo 电池信息
     *            com.android.settings.DateTimeSettings 日期和时间设置
     *            com.android.settings.DateTimeSettingsSetupWizard 日期和时间设置
     *            com.android.settings.DevelopmentSettings 应用程序设置=》开发设置
     *            com.android.settings.DeviceAdminSettings 设备管理器
     *            com.android.settings.DeviceInfoSettings 关于手机
     *            com.android.settings.Display 显示——设置显示字体大小及预览
     *            com.android.settings.DisplaySettings 显示设置
     *            com.android.settings.DockSettings 底座设置
     *            com.android.settings.IccLockSettings SIM卡锁定设置
     *            com.android.settings.InstalledAppDetails 语言和键盘设置
     *            com.android.settings.LanguageSettings 语言和键盘设置
     *            com.android.settings.LocalePicker 选择手机语言
     *            com.android.settings.LocalePickerInSetupWizard 选择手机语言
     *            com.android.settings.ManageApplications 已下载（安装）软件列表
     *            com.android.settings.MasterClear 恢复出厂设置
     *            com.android.settings.MediaFormat 格式化手机闪存
     *            com.android.settings.PhysicalKeyboardSettings 设置键盘
     *            com.android.settings.PrivacySettings 隐私设置
     *            com.android.settings.ProxySelector 代理设置
     *            com.android.settings.RadioInfo 手机信息
     *            com.android.settings.RunningServices 正在运行的程序（服务）
     *            com.android.settings.SecuritySettings 位置和安全设置
     *            com.android.settings.Settings 系统设置
     *            com.android.settings.SettingsSafetyLegalActivity 安全信息
     *            com.android.settings.SoundSettings 声音设置
     *            com.android.settings.TestingSettings
     *            测试——显示手机信息、电池信息、使用情况统计、Wifi information、服务信息
     *            com.android.settings.TetherSettings 绑定与便携式热点
     *            com.android.settings.TextToSpeechSettings 文字转语音设置
     *            com.android.settings.UsageStats 使用情况统计
     *            com.android.settings.UserDictionarySettings 用户词典
     *            com.android.settings.VoiceInputOutputSettings 语音输入与输出设置
     *            com.android.settings.WirelessSettings 无线和网络设置
     * @return
     */
    public static Intent getIntent(String cls) {
        Intent mIntent = new Intent();
        ComponentName comp = new ComponentName("com.android.settings",
                cls);
        mIntent.setComponent(comp);
        mIntent.setAction("android.intent.action.VIEW");
        return mIntent;
    }

    /****
     * 设置EditText图片大小
     *
     * @param mContext 上下文
     * @param w        图片宽度
     * @param h        图片高度
     * @return
     */
    public static Drawable setDrawable(Context mContext, float w, float h,
                                       int image) {
        Drawable drawable = mContext.getResources().getDrawable(image);
        drawable.setBounds(0, 0, ToolUnit.sp2px(mContext, w),
                ToolUnit.sp2px(mContext, h));// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
        return drawable;

    }

    /***
     * * 设置EditText图片大小
     *
     * @param mContext 上下文
     * @param w        图片宽度
     * @param h        图片高度
     * @param mContext
     * @param l        左边的距离
     * @param t        上边的距离
     * @return
     */
    public static Drawable setDrawable(Context mContext, float w, float h,
                                       int image, int l, int t) {
        Drawable drawable = mContext.getResources().getDrawable(image);
        drawable.setBounds(0, 0, ToolUnit.sp2px(mContext, w),
                ToolUnit.sp2px(mContext, h));// 第一0是距左边距离，第二0是距上边距离，40分别是长宽
        return drawable;

    }

    /***
     * 访问QQ 需要开通临时会话 http://shang.qq.com/v3/index.html
     *
     * @param context
     * @param qq
     */
    public static void qq(Context context, String qq) {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /***
     * 设置listView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /***
     * 设置listView的高度
     *
     * @param listView
     * @param attHeight
     */
    public static void setListViewHeightBasedOnChildren(ListView listView,
                                                        int attHeight) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);

            totalHeight += listItem.getMeasuredHeight();
            ToolToast.showShort(totalHeight + "");
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // params.height = totalHeight + (listView.getDividerHeight() *
        // (listAdapter.getCount() - 1)) + attHeight;
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + attHeight;
        listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView listView,
                                                        int attHeight) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int col = 2;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加

        for (int i = 0; i < listAdapter.getCount(); i++) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
            col = listItem.getMeasuredHeight();
        }
// 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (listAdapter.getCount() % 2 != 0) {
            params.height = (totalHeight / 2) + attHeight;
        } else {
            totalHeight += col;
            // 设置高度
            params.height = (totalHeight / 2) + attHeight;

        }


        // 设置margin
        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    /***
     * 设置text任意位置颜色
     *
     * @param content 内容
     * @param begin   开始位置
     * @param end     结束位置
     * @param color   颜色
     * @return
     */
    public static SpannableStringBuilder textViewColor(String content,
                                                       int begin, int end, int color) {
        ForegroundColorSpan cl = new ForegroundColorSpan(color);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(cl, begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;

    }

}

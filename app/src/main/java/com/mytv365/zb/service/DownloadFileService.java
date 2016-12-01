package com.mytv365.zb.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.model.CurLiveInfo;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/14.
 */

public class DownloadFileService extends Service{


    /**
     * 目标文件存储的文件夹路径
     */
    private String  destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
            .separator + "YHZB";
    /**
     * 目标文件存储的文件名
     */
    private String destFileName = "yanhuangzhibo.apk";

    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }



    /**
     * 下载文件
     */
    private void loadFile() {
        initNotification();

        OkGo.post(CurLiveInfo.getDownloadeurl())
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .setCertificates()
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // 安装软件
                        cancelNotification();
                        installApk(file);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        cancelNotification();
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                        updateNotification((long) (progress * 100 / totalSize));
                    }
                });
        /*if (retrofit == null) {
            retrofit = new Retrofit.Builder();
        }*/
       /* retrofit.baseUrl("http://112.124.9.133:8080/parking-app-admin-1.0/android/manager/adminVersion/")
                .client(initOkHttpClient())
                .build()
                .create(IFileLoad.class)
                .loadFile()
                .enqueue(new FileCallback(destFileDir, destFileName) {

                    @Override
                    public void onSuccess(File file) {
                        Log.e("zs", "请求成功");
                        // 安装软件
                        cancelNotification();
                        installApk(file);
                    }

                    @Override
                    public void onLoading(long progress, long total) {
                        Log.e("zs", progress + "----" + total);
                        updateNotification(progress * 100 / total);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("zs", "请求失败");
                        cancelNotification();
                    }
                });*/
    }


    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        // 执行意图进行安装
        mContext.startActivity(install);
    }


    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("0%")
                .setContentTitle("炎黄直播更新")
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void   cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }
}

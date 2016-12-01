package com.mytv365.zb.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.support.v4.content.LocalBroadcastManager;

import com.mytv365.zb.utils.Consts;

/**
 * 监听网络状态的广播
 * 
 * @author dingrui
 * 
 */
public class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo[] networkInfos = connectivityManager
					.getAllNetworkInfo();
			for (int i = 0; i < networkInfos.length; i++) {
				State state = networkInfos[i].getState();
				if (State.CONNECTED == state) {
					System.out.println("------------> Network is ok");
					Intent intent1 = new Intent(Consts.ggxqAction);
					LocalBroadcastManager.getInstance(context).sendBroadcast(
							intent1);
					return;
				}
			}
		}
		// 没有执行return,则说明当前无网络连接
		System.out.println("------------> Network is validate");
	}

}

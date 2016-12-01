package com.mytv365.zb.common;

import com.fhrj.library.common.NetworkStateService;
import com.fhrj.library.tools.ToolAlert;
import com.mytv365.zb.views.GlobalApplication;


/***
 * 网络监听Service
 * @author ZhangGuoHao
 * @date 2016年4月21日 上午11:30:51
 */
public class MyNetworkListener extends NetworkStateService {
	@Override
	public void onNoNetwork() {
		ToolAlert.toastShort(GlobalApplication.gainContext(), "木有网络了");

	}

	@Override
	public void onNetworkChange(String networkType) {
		ToolAlert.toastShort(GlobalApplication.gainContext(), "当前网络："+networkType);
	}

}

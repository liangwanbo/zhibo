package com.mytv365.zb.okhttp.config;


import android.util.Log;

/**
 * 枚举类
 * @author Sunshine
 *
 */
public final class CommonConstant {

	private static final String TAG = CommonConstant.class.getSimpleName();

	public enum AppType{
		ios,android
	}
	
	public enum Sex{
		f,//女
		m;//男
	}
	
	public enum heightUnits{
		cm,ft
	}
	
	public enum weightUnits{
		kg,lb
	}
	
	public enum LoginType {
		phone, email;
	}
	
	public enum YesNo {
		Y, N;
	}

    public static void  main(String[] args){
		Log.v(TAG, heightUnits.cm+"-----"+ AppType.android);

    }


}

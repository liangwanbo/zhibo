package com.mytv365.zb.views;

import android.content.Context;
import android.content.Intent;

import com.fhrj.library.MApplication;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.mytv365.zb.common.MyNetworkListener;
import com.mytv365.zb.presenters.InitBusinessHelper;
import com.mytv365.zb.utils.SxbLogImpl;


/***
 * AjavaSample全局的Application
 * @author Administrator
 */
public class GlobalApplication extends MApplication {

	private static GlobalApplication app;
	private static Context context;

	private static volatile GlobalApplication instance;
	private static final Object syncObj = new Object();


	public static GlobalApplication getInstance() {
		if (instance == null) {
			synchronized (syncObj) {
				if (instance == null) {
					instance = new GlobalApplication();
				}
			}
		}
		return instance;
	}


	@Override
	public void onCreate() {
		super.onCreate();

		HttpHeaders headers = new HttpHeaders();
		headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
		headers.put("commonHeaderKey2", "commonHeaderValue2");
		HttpParams params = new HttpParams();
		params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
		params.put("commonParamsKey2", "这里支持中文参数");

		OkGo.init(this);

		try {
			//以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
			OkGo.getInstance()

					//打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
					.debug("OkGo")

					//如果使用默认的 60秒,以下三行也不需要传
					.setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
					.setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
					.setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

					//可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy0216/
					.setCacheMode(CacheMode.NO_CACHE)

					//可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
					.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

					//如果不想让框架管理cookie,以下不需要
//                .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）
					.setCookieStore(new PersistentCookieStore())          //cookie持久化存储，如果cookie不过期，则一直有效

					//可以设置https的证书,以下几种方案根据需要自己设置
//                    .setCertificates()                                  //方法一：信任所有证书（选一种即可）
//                    .setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书（选一种即可）
//                    .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密

					//可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

					//这两行同上,不需要就不要传
					.addCommonHeaders(headers)                                         //设置全局公共头
					.addCommonParams(params);                                          //设置全局公共参数
		} catch (Exception e) {
			e.printStackTrace();
		}
		//启动Service

		/*ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

		HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				.addInterceptor(new LoggerInterceptor("TAG"))
				.cookieJar(cookieJar1)
				.hostnameVerifier(new HostnameVerifier()
				{
					@Override
					public boolean verify(String hostname, SSLSession session)
					{
						return true;
					}
				})
				.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
				.build();
		OkHttpUtils.initClient(okHttpClient);*/
		Intent mIntent = new Intent(this, MyNetworkListener.class);
		startService(mIntent);

		app = this;
		context = getApplicationContext();

		SxbLogImpl.init(getApplicationContext());

		//初始化APP
		InitBusinessHelper.initApp(getApplicationContext());


	}

	public static Context getcontext(){
		return context;
	}

	/**
	 * 退出APP时手动调用
	 */
	@Override
	public void exit() {
		try {
			//停止网络监听
			Intent mIntent = new Intent(this, MyNetworkListener.class);
			stopService(mIntent);
			//取消所有请求

			//关闭所有Activity
			removeAll();
			//退出进程
			System.exit(0);
		} catch (Exception ignored) {
		}
	}


}
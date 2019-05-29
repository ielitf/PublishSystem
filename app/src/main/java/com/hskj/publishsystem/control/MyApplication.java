package com.hskj.publishsystem.control;

import android.app.Application;
import android.widget.Toast;

import com.hskj.publishsystem.utils.SharedPreferenceManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {
	private static final String SHARED_PREFERENCE_NAME = "PublishSystem_sp";
	private static MyApplication application;
	public static MyApplication getMyApplication() {
		return application;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		SharedPreferenceManager.init(getApplicationContext(), SHARED_PREFERENCE_NAME);

		//使用OkGo的拦截器
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("meee");
		//日志的打印范围
		loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
		//在logcat中的颜色
		loggingInterceptor.setColorLevel(Level.INFO);
		//默认是Debug日志类型
		builder.addInterceptor(loggingInterceptor);

		//设置请求超时时间,默认60秒
		builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //读取超时时间
		builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //写入超时时间
		builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //连接超时时间

		//okhttp默认不保存cookes/session信息,需要自己的设置
		//builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
		builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保存cookie,退出后失效

		//全局初始化
		OkGo.getInstance().init(this);
		OkGo.getInstance()
				.setOkHttpClient(builder.build())
//				.setConnectTimeout(3000)
//				.setReadTimeOut(3000)
//				.setWriteTimeOut(3000)
//				.setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
//				.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
				.setRetryCount(3);
	}
	public void showToast(String msg) {
		Toast.makeText(application, msg + "", Toast.LENGTH_SHORT).show();
	}
}

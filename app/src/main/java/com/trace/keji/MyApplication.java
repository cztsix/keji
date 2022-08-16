package com.trace.keji;

import android.app.Application;
import android.webkit.WebView;
import cn.jpush.android.api.JPushInterface;
/**
 * 
 * @author user
 * 
 */
public class MyApplication extends Application {

	private static MyApplication instance;
	
	public WebView myWebview;

	public void onCreate() {
		super.onCreate();
		instance = this;
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
	}
	
}

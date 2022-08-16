package com.trace.keji.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 
 * @author user
 *
 */
public class NetworkUtil {


    
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
    
    
    
	public static boolean isNetworkAvailable(Activity mActivity) {
		Context context = mActivity.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}


	/**
	 * 监测网络是否链接
	 * @param c
	 * @return
	 */
	public static boolean checkNetWork(Context c){
		boolean wifiConnected  = isWifiConnection(c);
		boolean mobileConnected = isMobileConnected(c);
		
		if (wifiConnected == false && mobileConnected == false){
			return false;
		}
		return true;
	}
	
	/**
	 * 是否是wifi网络
	 * @param c
	 * @return
	 */
	public static boolean isWifiConnection(Context c){
		ConnectivityManager manager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		if (networkInfo != null && networkInfo.isConnected()){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是手机网络
	 * @param c
	 * @return
	 */
	public static boolean isMobileConnected(Context c){
		ConnectivityManager manager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		
		if (networkInfo != null && networkInfo.isConnected()){
			return true;
		}
		return false;
	}
	
}

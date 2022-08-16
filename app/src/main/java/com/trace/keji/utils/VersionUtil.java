package com.trace.keji.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 
 * 
 * @author yinmenglei
 * 
 */
public class VersionUtil {
	
	
	/**
	 * get app name
	 * @param c
	 * @return
	 */
	public static String getAppName(Context c) {
		try {
			PackageManager packageManager = c.getPackageManager();
			
			PackageInfo packageInfo = packageManager.getPackageInfo( c.getPackageName(), 0);
			
			int labelRes = packageInfo.applicationInfo.labelRes;
			
			return c.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get Version Name
	 * 
	 * @param c
	 * @return
	 */
	public static String getVersionName(Context c) {
		try {
			PackageManager packageManager = c.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(c.getPackageName(), 0);
			return packInfo.versionName;			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get Version Code
	 * 
	 * @param c
	 * @return
	 */
	public static int getVersionCode(Context c) {
		try {
			PackageManager packageManager = c.getPackageManager();
			
			PackageInfo packInfo = packageManager.getPackageInfo(c.getPackageName(), 0);
			
			return packInfo.versionCode;
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

}

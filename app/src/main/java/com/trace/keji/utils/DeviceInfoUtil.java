package com.trace.keji.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 
 * @author yinmenglei
 * 
 */
@SuppressWarnings("deprecation")
public class DeviceInfoUtil {
	
	
	
    /**
     * 获取网络IP
     * @param a
     * @return
     */
	public static String getNetworkIp(Activity a) {
		// TODO Auto-generated method stub
		// 获取wifi服务
		WifiManager wifiManager = (WifiManager) a.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		// 判断wifi是否开启
		if (!wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		return intToIp(ipAddress);
	}

	private static String intToIp(int i) {
		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
	}
    
	/**
	 * 手机分辨率
	 */
	public static String getPhoneDPI(Activity a) {
		// TODO Auto-generated method stub
		return DensityUtil.getScreenHeight(a)+"*"+DensityUtil.getScreenWidth(a) ;
	}
	
	/**
	 * 手机型号
	 * @return
	 */
	public static String getPhoneModel() {
		// TODO Auto-generated method stub
		return android.os.Build.MODEL;
	}
	
	/**
	 * SDK版本
	 * @return
	 */
	public static  String getSdkVersion() {
		// TODO Auto-generated method stub
		return android.os.Build.VERSION.SDK;
	}
	
	/**
	 * 系统版本
	 * @return
	 */
	public static  String getVersionRelease() {
		// TODO Auto-generated method stub
		return android.os.Build.VERSION.RELEASE;
	}
	
	/**
	 * 获取手机唯一标识imei
	 * @param mContext
	 * @return
	 */
	public static String getImeiStatus(Context c) {
		TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	/**
	 * 获取wifi的Mac地址
	 * @param c
	 * @return
	 */
	public static String getDeviceId(Context c) {
		String deviceId = "";
		WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		try {
			deviceId = info.getMacAddress().toString().trim();
			deviceId = deviceId.toLowerCase();// Mac地址转换为小写
		} catch (Exception e) {
			deviceId = getMac();
			if (deviceId == null || deviceId.equals("")) {
				deviceId = "";
			}
			e.printStackTrace();
		}

		if (deviceId != null && !"".equals(deviceId)) {
			return deviceId.replaceAll("[\\p{Punct}\\p{Space}]+", "");
		} else {
			return "";
		}
	}

	/**
	 * 获取设备唯一码
	 * 
	 * @return macSerial Mac地址
	 */
	public static String getMac() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return macSerial;
	}

}

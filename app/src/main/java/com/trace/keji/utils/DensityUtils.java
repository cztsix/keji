package com.trace.keji.utils;


import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;

/**
 * 分辨率转换类
 * 
 * @author yinmenglei_
 *
 */
public class DensityUtils {
	
	public static int getScreenHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	public static int getScreenWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	/**
	 * 根据手机的屏幕密度从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的屏幕密度从 px(像素) 的单位 转成为 dp
	 */
	public int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(Context context, float spVal){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal, context.getResources().getDisplayMetrics());
	}

	public static float px2sp(Context context, float pxVal){
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}
}
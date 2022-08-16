package com.trace.keji.utils;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup.MarginLayoutParams;

/**
 * 分辨率转换类
 * 
 * @author yinmenglei_
 *
 */
public class DensityUtil {
	
	public static int getScreenHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

	public static int getScreenWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	public int getTopMagin(Activity activity) {
		return (int) (getScreenHeight(activity) * 0.1);
	}

	public int getLeftMagin(Activity activity) {
		return (int) (getScreenWidth(activity) * 0.1);
	}

	@SuppressWarnings("unused")
	public MarginLayoutParams getMagins(Activity activity,
			MarginLayoutParams params) {

		params.height = params.MATCH_PARENT;
		params.width = params.MATCH_PARENT;
		params.leftMargin = getLeftMagin(activity);
		params.bottomMargin = getTopMagin(activity);
		params.topMargin = getTopMagin(activity);
		params.rightMargin = 0;
		DisplayMetrics dm = new DisplayMetrics();
		dm = activity.getResources().getDisplayMetrics();
		return params;

	}

	/**
	 * 根据手机的屏幕密度从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的屏幕密度从 px(像素) 的单位 转成为 dp
	 */
	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
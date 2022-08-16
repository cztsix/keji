package com.trace.keji.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtil {

  private static String oldMsg;
  protected static Toast toast = null;

  public static void showToast(Context mContext, String s) {
    if (TextUtils.isEmpty(s) || mContext == null) {
      return;
    }
    if (toast == null) {
      toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
      toast.show();
    } else {
      if (s.equals(oldMsg)) {
        toast.show();
      } else {
        oldMsg = s;
        toast.setText(s);
        toast.show();
      }
    }
  }

  /**
   * 显示短Toast
   *
   * @param mContext
   * @param s
   * @param
   */
  public static void showShortToast(Context mContext, String s) {
    if (TextUtils.isEmpty(s) || mContext == null) {
      return;
    }
    if (toast == null) {
      toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
      toast.show();
    } else {
      if (s.equals(oldMsg)) {
        toast.show();
      } else {
        oldMsg = s;
        toast.setText(s);
        toast.show();
      }
    }
  }

  /**
   * 显示长Toast
   *
   * @param mContext
   * @param s
   * @param
   */
  public static void showLongToast(Context mContext, String s) {
    if (TextUtils.isEmpty(s) || mContext == null) {
      return;
    }
    if (toast == null) {
      toast = Toast.makeText(mContext, s, Toast.LENGTH_LONG);
      toast.show();
    } else {
      if (s.equals(oldMsg)) {
        toast.show();
      } else {
        oldMsg = s;
        toast.setText(s);
        toast.show();
      }
    }
  }

  public static void showToast(Context mContext, String s, String defautStr) {
    if (!TextUtils.isEmpty(s)) {
      showToast(mContext, s);
    } else if (!TextUtils.isEmpty(defautStr)) {
      showToast(mContext, defautStr);
    }
  }

  public static void showToast(Context mContext, int resId) {
    if (mContext == null) {
      return;
    }
    showToast(mContext, mContext.getString(resId));
  }
}

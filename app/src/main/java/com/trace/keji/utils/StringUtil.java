package com.trace.keji.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * String 工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtil {

	/**
	 * 判断是否为null
	 * 
	 * @param str
	 * @return
	 */
	public String isNullStr(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}
	
	public static String isNullStr2(String str, String defaultStr) {
		return (str == null || str.equals("")) ? defaultStr : str;
	}


    
    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
    
	 public static boolean isEmptyStr(String s) {
	        if (null == s)
	            return true;
	        if (s.length() == 0)
	            return true;
	        if (s.trim().length() == 0)
	            return true;
	        return false;
	    }
	/**
	 * 替换电话号码中间4位 为 ****
	 * 
	 * @param num
	 * @return
	 */
	public static String ReplaceXing(String num) {
		if (num.startsWith("+86")) {
			num = num.substring("+86".length(), num.length());
		}
		StringBuffer buffer = new StringBuffer();
		for (int index = 0; index < num.length(); index++) {
			if (index >= 3 && index <= 6) {
				buffer.append("*");
			} else {
				buffer.append(num.charAt(index));
			}
		}
		return buffer.toString();
	}
	
	
	/**
	 * 截取小数点后两位
	 * @param valueStr
	 * @return
	 */
	public static String getMontyCount(String valueStr){
		if(valueStr == null || valueStr.equals("")){
			return "0.00";
		}
		String split;
		int pointPos = valueStr.indexOf('.');
		if (pointPos < 0 || pointPos + 3 > valueStr.length()) {
			split = valueStr;
		} else {
			split = valueStr.substring(0, valueStr.indexOf('.') + 3);
		}
		return split;
	}
	
	/**
	 * 判断手机号
	 * @param str
	 * @return
	 */
	public static boolean checkPhone(String str) {
		if (!TextUtils.isEmpty(str)) {
//			^0?(13[0-9]|15[012356789]|18[012356789]|17[0-9]|14[57])[0-9]{8}$
			String regex = "^0?(13[0-9]|15[012356789]|18[012356789]|14[57]|17[012356789])[0-9]{8}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断是否为邮箱
	 * @param str
	 * @return
	 */
	public int checkEmail(String str) {
		if (!TextUtils.isEmpty(str)) {
			String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			if (matcher.matches()) {
				return 1;
			}
		} else {
			return -1;
		}
		return 0;
	}

	/**
	 * 判断是否为字符串长度
	 * @param str
	 * @param min
	 * @param max
	 * @return
	 */
	public int checkLength(String str, int min, int max) {
		if (!TextUtils.isEmpty(str)) {
			if (str.length() > max || str.length() < min) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return -1;
		}
	}

	/**
	 * 解析Struts返回的结果
	 * @param str
	 * @return
	 */
	public String parseStr(String str) {
		return str.replaceAll("[\\p{Punct}\\p{Space}]+", "");
	}

}

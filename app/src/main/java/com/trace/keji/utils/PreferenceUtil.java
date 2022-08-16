package com.trace.keji.utils;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * 存储配置信息的工具类
 * 
 * 注：可读取的数据类型有 boolean、int、float、long、String 
 * 
 * @author yinmenglei
 *
 */
public class PreferenceUtil {

	// 文件内容存储模式
	// private final int MODE = Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE;

	private SharedPreferences sharedpreferences;

	public PreferenceUtil(String filename, Context c) {
		sharedpreferences = c.getSharedPreferences(filename, 0);
	}

	public boolean saveStrPreference(String key, String value) {
		SharedPreferences.Editor editor = sharedpreferences.edit();
		try {
			editor.putString(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return editor.commit();
	}

	/**
	 * 
	 * @param key
	 * @param defaultStr 默认值
	 * @return
	 */
	public String getStrPreference(String key, String defaultStr) {
		String str = null;
		try {
			str = sharedpreferences.getString(key, defaultStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public boolean saveIntPreference(String key, int value) {
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putInt(key, value);
		return editor.commit();
	}

	public int getIntPreference(String key) {
		return sharedpreferences.getInt(key, 0);
	}

	public boolean saveFloatPreference(String key, float value) {
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putFloat(key, value);
		return editor.commit();
	}

	public float getFloatPreference(String key) {
		return sharedpreferences.getFloat(key, 0f);
	}

	public boolean saveLongPreference(String key, Long value) {
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public long getLongPreference(String key) {
		return sharedpreferences.getLong(key, 0l);
	}

	/**
	 * 保存boolean值
	 * @param key 
	 * @param value 值
	 * @return
	 */
	public boolean saveBoolPreference(String key, Boolean value) {
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	/**
	 * 默认为false
	 * @param key
	 * @param flag 默认返回值
	 * @return
	 */
	public boolean getBoolPreference(String key,boolean flag) {
		return sharedpreferences.getBoolean(key, flag);
	}

	public boolean saveAllPreference(List<?> list, String... keyName) {
		int size = list.size();
		if (size < 1) {
			return false;
		}
		SharedPreferences.Editor editor = sharedpreferences.edit();
		if (list.get(0) instanceof String) {
			for (int i = 0; i < size; i++) {
				editor.putString(keyName[i], (String) list.get(i));
			}
		} else if (list.get(0) instanceof Long) {
			for (int i = 0; i < size; i++) {
				editor.putLong(keyName[i], (Long) list.get(i));
			}
		} else if (list.get(0) instanceof Float) {
			for (int i = 0; i < size; i++) {
				editor.putFloat(keyName[i], (Float) list.get(i));
			}
		} else if (list.get(0) instanceof Integer) {
			for (int i = 0; i < size; i++) {
				editor.putLong(keyName[i], (Integer) list.get(i));
			}
		} else if (list.get(0) instanceof Boolean) {
			for (int i = 0; i < size; i++) {
				editor.putBoolean(keyName[i], (Boolean) list.get(i));
			}
		}
		return editor.commit();
	}

	public Map<String, ?> getAllPreference() {
		return sharedpreferences.getAll();
	}

	public boolean removeKey(String key) {
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.remove(key);
		return editor.commit();
	}

	public boolean removeAllKey() {
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.clear();
		return editor.commit();
	}

}

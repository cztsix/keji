package com.trace.keji.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.trace.keji.R;


/**
 * 
 * 激光推送帮助类
 * 
 * @author user
 *
 */
public class JPushUtil {

    private static final String TAG = "JPush";
    
	public static JPushUtil mJPushUtil;
	Context mContext;
	
	public JPushUtil(Context mContext) {
		this.mContext = mContext;
	}
	
	public static JPushUtil getJPushUtil(Context mContext){
		if(mJPushUtil == null){
			mJPushUtil = new JPushUtil(mContext);
		}
		return mJPushUtil;
	}

	
	/**
	 * 
	 * @param tag
	 */
	public void setTag(String tag){
        // 检查 tag 的有效性
		if (TextUtils.isEmpty(tag)) {
			Toast.makeText(mContext, "tag不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// ","隔开的多个 转换成 Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!StringUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(mContext, "Tag不合法", Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//调用JPush API设置Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
	} 
	
	/**
	 * 
	 * @param alias
	 */
	public void setAlias(String alias){
//		if (TextUtils.isEmpty(alias)) {
//			Toast.makeText(mContext, "alias不能为空", Toast.LENGTH_SHORT).show();
//			return;
//		}
		if (!StringUtil.isValidTagAndAlias(alias)) {
			Toast.makeText(mContext,"alias不合法", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}
	
	
	/**
	 * 设置通知提示方式 - 基础属性
	 */
	public void setStyleBasic(){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(mContext);
		builder.statusBarDrawable = R.drawable.icon_30;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）  
		JPushInterface.setPushNotificationBuilder(1, builder);
		Toast.makeText(mContext, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
	}
	
	
	/**
	 *设置通知栏样式 - 定义通知栏Layout
	 */
	private void setStyleCustom(){
//		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
//				mContext, R.layout.customer_notitfication_layout, 
//				R.id.icon, R.id.title, R.id.text);
//		builder.layoutIconDrawable = R.drawable.ic_launcher;
//		builder.developerArg0 = "developerArg2";
//		JPushInterface.setPushNotificationBuilder(2, builder);
//		Toast.makeText(mContext,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
	}
	

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (NetworkUtil.isConnected(mContext)) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
            }
            
            
            /**
            *6001   无效的设置，tag/alias 不应参数都为 null 
            *6002   设置超时    建议重试
            *6003   alias 字符串不合法    有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
            *6004   alias超长。最多 40个字节    中文 UTF-8 是 3 个字节
            *6005   某一个 tag 字符串不合法  有效的别名、标签组成：字母（区分大小写）、数字、下划线、汉字。
            *6006   某一个 tag 超长。一个 tag 最多 40个字节  中文 UTF-8 是 3 个字节
            *6007   tags 数量超出限制。最多 100个 这是一台设备的限制。一个应用全局的标签数量无限制。
            *6008   tag/alias 超出总长度限制。总长度最多 1K 字节   
            *6011   10s内设置tag或alias大于3次 短时间内操作过于频繁
            **/
//            MyToast.showToast(logs, mContext);
        }
	};
	
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (NetworkUtil.isConnected(mContext)) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
            }
            
//            MyToast.showToast(logs, mContext);
            ShowUtil.showCenterToast(mContext, logs, 0);
        }
    };
    
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	
	

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MSG_SET_ALIAS:
                Log.d(TAG, "Set alias in handler.");
                JPushInterface.setAliasAndTags(mContext, (String) msg.obj, null, mAliasCallback);
                break;
            case MSG_SET_TAGS:
                Log.d(TAG, "Set tags in handler.");
                JPushInterface.setAliasAndTags(mContext, null, (Set<String>) msg.obj, mTagsCallback);
                break;
            default:
                Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
	
	
	
	
	
	
}

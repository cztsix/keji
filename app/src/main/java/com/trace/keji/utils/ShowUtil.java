package com.trace.keji.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * Toast工具
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("unused")
public class ShowUtil {

	private Context context;

	private static Toast mToast;
	
	private Dialog pg_dlg;
	private static ShowUtil show;

	public ShowUtil() {
		
	}
	
	public ShowUtil(Context c) {
		this.context = c;
	}

	public static ShowUtil getShowInit() {
		if (show == null) {
			show = new ShowUtil();
		}
		return show;
	}

	/** 设置对话框的大小 */
	public void setWindow(Dialog dia, double width, double height, boolean flag) {
		dia.setCancelable(flag); // 点击空白地方是否隐藏
		Window w = dia.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		w.setGravity(Gravity.CENTER);
		// 1024 * 720 1024 * 768 1280 * 752
		lp.width = (int) (w.getWindowManager().getDefaultDisplay().getWidth() * width);
		lp.height = (int) (w.getWindowManager().getDefaultDisplay().getHeight() * height);
		w.setAttributes(lp);
	}
	
	public Dialog createDlg(int style, int dlgview, boolean isTouchCancel,
			int gravity, Context context) {
		Dialog dlg = new Dialog(context, style);
		dlg.setContentView(dlgview);
		dlg.setCanceledOnTouchOutside(isTouchCancel);
		return dlg;
	}
	
	private static Handler mHandler = new Handler();
	
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};
	
	/**
	 * Toast居中显示
	 * @param c
	 * @param msg
	 * @param flag
	 */
	public static void showCenterToast(Context c, String msg, int flag) {
		mHandler.removeCallbacks(r);
		if (mToast != null) {
			mToast.setText(msg);
		} else {
			if(c == null){
				return;
			}
			mToast = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
		}
		mToast.setGravity(Gravity.CENTER, 0, 0);
		if(flag == 1){
			mHandler.postDelayed(r, 1000);
		} else if(flag == 2){
			mHandler.postDelayed(r, 2000);
		} else if(flag == 3){
			mHandler.postDelayed(r, 3000);
		} else {
			mHandler.postDelayed(r, 5000);
		}
		mToast.show();
	}
	
	
	/**
	 * 带图片的Toast提示
	 * @param c
	 * @param msg
	 * @param flag
	 */
	public static void showToastIV(Context c, String msg, int image, int flag) {
		Toast toast = null;
		if (flag == 0) {
			toast = Toast.makeText(c.getApplicationContext(), msg, Toast.LENGTH_SHORT);
		} else {
			toast = Toast.makeText(c.getApplicationContext(), msg, Toast.LENGTH_LONG);
		}
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(c.getApplicationContext());
		imageCodeProject.setImageResource(image);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}

	
	public void toast(int flag, String msg) {
		if (flag == 0) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}
	
	
}

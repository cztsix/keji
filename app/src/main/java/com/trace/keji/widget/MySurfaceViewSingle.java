package com.trace.keji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;


/**
 * 单个SurfaceView
 * 
 * @author user
 *
 */
public class MySurfaceViewSingle extends SurfaceView implements OnTouchListener, OnGestureListener {

	private GestureDetector gd;
	private Context context;

	private OnFlingListener listener_onfling;
	private int MIN_DISTANCE = 100;
	
	float x_1 = 0;
	float y_1 = 0;
	float x_2 = 0;
	float y_2 = 0;

	public MySurfaceViewSingle(Context context, AttributeSet ats) {
		super(context,ats);
		this.context = context;
		setFocusable(true);
		requestFocus();
		this.setLongClickable(true);
		this.setOnTouchListener(this);
		setFocusable(true);
		gd = new GestureDetector(context, this);
	}

	
	/**
	 * 当发行屏幕触控事件的时候，首先触发此方法，再通过此方法，监听具体的整件在onTouch()方法中，
	 * 我们调用GestureDetector的onTouchEvent()方法，将捕捉到的MotionEvent交给GestureDetector 
	 * 来分析是否有合适的callback函数来处理用户的手势
	 */
	public boolean onTouch(View v, MotionEvent event) {
		return gd.onTouchEvent(event);
	}

	public static abstract class OnFlingListener {
		public abstract void OnFlingLeft();
		public abstract void OnFlingRight();
		public abstract void OnFlingUp();
		public abstract void OnFlingDown();
		public abstract void OnFlingLeftUp();
		public abstract void OnFlingRightUp();
		public abstract void OnFlingLeftDown();
		public abstract void OnFlingRightDown();
		public abstract void OnFlingStop();
	}

	public void setOnFlingListener(OnFlingListener listener) {
		listener_onfling = listener;
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:// 按下
			x_1 = event.getX();
			y_1 = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:// 移动
			x_2 = event.getX();
			y_2 = event.getY();
			float x1_x2 = x_1 - x_2;
			float x2_x1 = x_2 - x_1;
			float y1_y2 = y_1 - y_2;
			float y2_y1 = y_2 - y_1;
			if(listener_onfling != null){
				if (x1_x2 > MIN_DISTANCE && y1_y2 < MIN_DISTANCE && y2_y1 < MIN_DISTANCE) {
					listener_onfling.OnFlingLeft();
				} else if (x2_x1 > MIN_DISTANCE && y1_y2 < MIN_DISTANCE && y2_y1 < MIN_DISTANCE) {
					listener_onfling.OnFlingRight();
				} else if (y1_y2 > MIN_DISTANCE && x1_x2 < MIN_DISTANCE && x2_x1 < MIN_DISTANCE) {
					listener_onfling.OnFlingUp();
				} else if (y2_y1 > MIN_DISTANCE && x1_x2 < MIN_DISTANCE && x2_x1 < MIN_DISTANCE) {
					listener_onfling.OnFlingDown();
				} else if (y2_y1 > MIN_DISTANCE && x2_x1 > MIN_DISTANCE && x1_x2 < MIN_DISTANCE) {
					listener_onfling.OnFlingRightDown();
				} else if (y2_y1 > MIN_DISTANCE && x1_x2 > MIN_DISTANCE && x2_x1 < MIN_DISTANCE) {
					listener_onfling.OnFlingLeftDown();
				} else if (y1_y2 > MIN_DISTANCE && x1_x2 < MIN_DISTANCE && x2_x1 > MIN_DISTANCE) {
					listener_onfling.OnFlingRightUp();
				} else if (y1_y2 > MIN_DISTANCE && x1_x2 > MIN_DISTANCE && x2_x1 < MIN_DISTANCE) {
					listener_onfling.OnFlingLeftUp();
				}
			}
			break;
		case MotionEvent.ACTION_UP:// 抬起
			x_1 = 0;
			y_1 = 0;
			x_2 = 0;
			y_2 = 0;
			if(listener_onfling != null){
				listener_onfling.OnFlingStop();
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:// 多点触摸
			break;
		case MotionEvent.ACTION_POINTER_UP:// 多点抬起
			if(listener_onfling != null){
				listener_onfling.OnFlingStop();
			}
			break;
			
		}
		return true;
	}
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// 触发条件 ：
		// X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
//		final int FLING_MIN_DISTANCE = (int) (screen_width / 3.0f), FLING_MIN_VELOCITY = 200;
//		x_1 = e1.getX();
//		y_1 = e1.getY();
//		x_2 = e2.getX();
//		y_2 = e2.getY();
//		if (e1.getX() - e2.getX() > 100) {
//			listener_onfling.OnFlingLeft();
//		} else if (e2.getX() - e1.getX() > 20) {
//			listener_onfling.OnFlingRight();
//		}
		return true;
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}


	public boolean onDown(MotionEvent e) {
		return false;
	}

	public void onLongPress(MotionEvent e) {
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

}

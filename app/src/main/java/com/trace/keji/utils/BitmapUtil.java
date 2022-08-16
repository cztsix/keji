package com.trace.keji.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Build;

/**
 * 
 * @author yinmenglei_
 *
 */
public class BitmapUtil {

	/**
	 * 根据一个网络连接(URL)获取bitmap图像
	 * @param imageUri
	 * @return
	 */
	public static Bitmap getBitmapByUrl(URL imageUri) {
		URL myFileUrl = imageUri;
		Bitmap bitmap = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 读取图片的旋转的角度
	 * @param path  图片绝对路径
	 */
	public int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}
	

	/** 
	 * Bitmap 改变大小
	 * @param bitmap
	 * @param f
	 * @return
	 */
	public Bitmap big(Bitmap bitmap, float f) {
		Matrix matrix = new Matrix();
		matrix.postScale(f, f); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}
	
	
	/**
	 * 获取bitmap对象的大小
	 * @param bitmap
	 * @return
	 */
	@SuppressLint("NewApi")
	public static long getBitmapsize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}

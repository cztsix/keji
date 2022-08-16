package com.trace.keji.utils;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

/**
 * 文件存储
 * 
 * @author user
 *
 */
public class FileUtil {

	private static String sdPath = Environment.getExternalStorageDirectory().toString();
	public static String folder0 = sdPath + "/shagri/";
	public static String folder1 = folder0 + "WnPlatform/";// 项目文件夹分类
	public static String folder_image = folder1 + "image/";//图片分类
	public static String folder_file = folder1 + "file/";//文件分类
	
	// 图片文件后缀
	public static String fileSuffix = ".jpg";
	
	/**
	 * 保存图片
	 * @param bitmap
	 * @return
	 */
	public static String savaBitmap(Bitmap bitmap) {
		isFileExist(folder0, folder1, folder_image);

		String photoPath = folder_image + "12316_"+System.currentTimeMillis() + fileSuffix;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(photoPath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photoPath;
	}
	
	
	/**
	 * 判断文件夹是否存在
	 * 
	 * @param filePath
	 */
	public static void isFileExist(String...filePath){
		int size = filePath.length;
		if(size >=1){
			File file1 = new File(filePath[0]);
			if (!file1.exists()) {
				file1.mkdir();
			}
		}
		if(size >= 2){
			File file2 = new File(filePath[1]);
			if (!file2.exists()) {
				file2.mkdir();
			}
		}
		if(size >= 3){
			File file3 = new File(filePath[2]);
			if (!file3.exists()) {
				file3.mkdir();
			}
		}
		if(size >= 4){
			File file4 = new File(filePath[4]);
			if (!file4.exists()) {
				file4.mkdir();
			}
		}
	}
	
}

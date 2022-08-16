package com.trace.keji.model;

import java.io.Serializable;


/**
 * 图片拍照上传
 * 
 * @author Administrator
 *
 */
public class SavePhotoCondition implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	public String proName;
	public String ID;
	public String PointID;
	public String CameraID;
	public String PhotoTime;
	public String ImgNameS;
	public String ImgNameL;
	public String ImgPath;
	public String CreateUser;
	public String Tag;
	public String fileBytes;

}
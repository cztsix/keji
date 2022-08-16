package com.trace.keji.model;

import java.io.Serializable;

/**
 * 视屏播放
 * 
 * @author Administrator
 *
 */
public class VideoModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public VideoCallBack data;

	public class VideoCallBack implements Serializable {
		private static final long serialVersionUID = 1L;

		public String callbackMethodName;
		public String callbackData;
	}


}
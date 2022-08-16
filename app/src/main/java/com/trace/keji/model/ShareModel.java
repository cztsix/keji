package com.trace.keji.model;

import java.io.Serializable;

/**
 * 第三方分享
 * 
 * @author user
 * 
 */
public class ShareModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public Share data;

	public class Share implements Serializable {
		private static final long serialVersionUID = 1L;

		public String proName;
		public String title;
		public String description;
		public String imageUrl;
		public String url;

	}

}
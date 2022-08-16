package com.trace.keji.model;

import java.io.Serializable;

public class DownLoadModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public DownLoadUrlModel data;

	public class DownLoadUrlModel implements Serializable {
		private static final long serialVersionUID = 1L;

		public String url;
		public String fileName;
		public String callbackData;
	}

	
}


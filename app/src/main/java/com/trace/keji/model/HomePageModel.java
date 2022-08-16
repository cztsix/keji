package com.trace.keji.model;

import java.io.Serializable;

public class HomePageModel implements Serializable{
	private static final long serialVersionUID = 1L;

	public String method;
	public HomePage data;

	public class HomePage implements Serializable {
		private static final long serialVersionUID = 1L;
		public String url;
		public String callbackData;
		public String callbackMethodName;
	}

}

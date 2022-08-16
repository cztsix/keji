package com.trace.keji.model;

import java.io.Serializable;

public class CallBackModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public CallBack data;

	public class CallBack implements Serializable {
		private static final long serialVersionUID = 1L;

		public String callbackData;
		public String callbackMethodName;
	}

}
package com.trace.keji.model;

import java.io.Serializable;

public class GetDataModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public GetData data;

	public class GetData implements Serializable {
		private static final long serialVersionUID = 1L;

		public String Key;
		public String callbackData;
		public String callbackMethodName;

	}

}
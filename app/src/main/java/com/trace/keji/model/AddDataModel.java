package com.trace.keji.model;

import java.io.Serializable;

public class AddDataModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public AddData data;

	public class AddData implements Serializable {
		private static final long serialVersionUID = 1L;

		public String Key;
		public String Value;
	}

}
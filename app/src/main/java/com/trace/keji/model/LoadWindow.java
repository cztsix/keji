package com.trace.keji.model;

import java.io.Serializable;

public class LoadWindow implements Serializable {
	private static final long serialVersionUID = 1L;

	public String method;
	public Load data;

	public class Load implements Serializable {
		private static final long serialVersionUID = 1L;

		public String htmlFileName;
		public String callbackMethodName;

	}

}
package com.trace.keji.model;

import java.io.Serializable;

public class CallNumberModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public CallNumber data;

	public class CallNumber implements Serializable {
		private static final long serialVersionUID = 1L;

		public String callNumber;
	}

}
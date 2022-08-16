package com.trace.keji.model;

import java.io.Serializable;

public class PushTagModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public PushTag data;

	public class PushTag implements Serializable {
		private static final long serialVersionUID = 1L;

		public String Tag;
	}
}
package com.trace.keji.model;

import java.io.Serializable;

public class WebUrlModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public WebUrl data;

	public class WebUrl implements Serializable {
		private static final long serialVersionUID = 1L;

		public String url;
	}

}
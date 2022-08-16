package com.trace.keji.model;

import java.io.Serializable;

/**
 * 版本名称
 * 
 * @author Administrator
 *
 */
public class VersionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public VersionCallBack data;

	public class VersionCallBack implements Serializable {
		private static final long serialVersionUID = 1L;

		public String callbackMethodName;
		public String callbackData;
	}


}
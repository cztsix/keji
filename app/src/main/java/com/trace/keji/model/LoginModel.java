package com.trace.keji.model;

import java.io.Serializable;

/**
 * 第三方登录
 * 
 * @author user
 *
 */
public class LoginModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public Login data;

	public class Login implements Serializable {
		private static final long serialVersionUID = 1L;

		public int type;
		public String callbackData;
		public String callbackMethodName;

	}

}
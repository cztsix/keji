package com.trace.keji.model;

import java.io.Serializable;

/**
 * 扫描二维码回调
 * 
 * @author yinmenglei
 *
 */
public class ScanCallback implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public ScanCall data;

	public class ScanCall implements Serializable {
		private static final long serialVersionUID = 1L;

		public String callbackData;
		public String callbackMethodName;
		public String titleName;
		public String scanHint;
		
	}

}
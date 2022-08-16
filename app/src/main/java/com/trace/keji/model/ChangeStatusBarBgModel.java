package com.trace.keji.model;

import java.io.Serializable;

/**
 * 修改状态栏背景和文字颜色
 * @author Administrator
 * 
 */
public class ChangeStatusBarBgModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public StatusBarColor data;

	public class StatusBarColor implements Serializable {
		private static final long serialVersionUID = 1L;

		public String bgcolor;
		public String color;
	}

}
package com.trace.keji.model;

import java.io.Serializable;



public class HelpTipModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public String method;
	public HelpTip data;

	public class HelpTip implements Serializable {
		private static final long serialVersionUID = 1L;
	
		public String mssage;
		public String type;
		public String callbackData;
		public String callbackMethodName;
	}
	
}
package com.trace.keji.model;

import java.io.Serializable;

public class SendMsgModel {
	private static final long serialVersionUID = 1L;

	public String method;
	public SendMegData data;

	public class SendMegData implements Serializable {
		private static final long serialVersionUID = 1L;
		public String callNumber;
		public String content;
	}

}

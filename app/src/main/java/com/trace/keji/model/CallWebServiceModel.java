package com.trace.keji.model;

import java.io.Serializable;
import java.util.List;

public class CallWebServiceModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public WebServiceModel data;

	public class WebServiceModel implements Serializable {
		private static final long serialVersionUID = 1L;

		public String url;
		public String method;
		
		public List<Model> pars;

		public String callbackData;
		public String callbackMethodName;
	}

	public class Model implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public String name;
		public Object value;
	}
	
}


package com.trace.keji.model;

import java.io.Serializable;

public class ImageListModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public ImageList data;

	public class ImageList implements Serializable {
		private static final long serialVersionUID = 1L;

		public String postion;
		public String[] lists;
	}


}
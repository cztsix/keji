package com.trace.keji.model;

import java.io.Serializable;


public class ResponseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public String result;
	public int code;
	public boolean isSuccess;

}
package com.trace.keji.model;

import java.io.Serializable;

public class TagModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public TagModel(String id, String tagName) {
		this.id = id;
		this.tagName = tagName;
	}

	public String id;
	public String tagName;

}
package com.trace.keji.model;

import java.io.Serializable;

public class PlayModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public String method;
	public Play data;

	public class Play implements Serializable {
		private static final long serialVersionUID = 1L;

		public String _n;
		public String PointID;
		public String CameraID;
		public String CreateUser;

		public boolean autoPlay;// 是否自动播放 true/false
		public int type;
		public int tag;// tag:0.控制加拍照（默认）;1.控制无拍照;2.拍照无控制;3.无拍照，无控制
		public String url;

		public String name;
		public int channel;
		public int phoneChannel;
		public String strIP;
		public int nPort;
		public int phonePoint;
		public String strUser;
		public String strPsd;
		public String title;
		
	}
}

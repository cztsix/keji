package com.trace.keji.model;

import java.io.Serializable;


/**
 * 第三方登录返回值解析
 * 
 * @author Administrator
 *
 */
public class OtherLoginModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public String Portrait;// 头像
	public String Sex;// 性别
	public String Age;// 年龄
	public String UserName;//昵称
	public String ThirdIdCode;// 唯一表示token
	public int ThirdType;// 第三方类型
	
	
	public OtherLoginModel(int ThirdType, String ThirdIdCode, String UserName, String Portrait, String Sex) {
		// TODO Auto-generated constructor stub
		this.ThirdType = ThirdType;
		this.ThirdIdCode = ThirdIdCode;
		this.UserName = UserName;
		this.Portrait = Portrait;
		this.Sex = Sex;
		this. Age = null;
	}

}
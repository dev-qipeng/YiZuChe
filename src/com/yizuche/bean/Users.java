package com.yizuche.bean;

import cn.bmob.v3.BmobObject;

/**
 * 
 * @author DELL001
 * 
 */
public class Users extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

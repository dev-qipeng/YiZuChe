package com.yizuche.bean;

import cn.bmob.v3.BmobObject;

public class BikeInfo extends BmobObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;//标题
	private String describe;//描述
	private String phone;// 联系手机

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}

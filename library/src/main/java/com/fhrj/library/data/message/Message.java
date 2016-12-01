package com.fhrj.library.data.message;

import com.fhrj.library.data.DTB;


/***
 *  消息实体Bean
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午7:32:54
 */
public class Message extends DTB<Object, Object> {

	private static final long serialVersionUID = 7491152915368949244L;
	
	/**
	 * 消息ID
	 */
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

package com.mytv365.zb.model;

/**
 * TAB分类栏目属性
 * */
public class TabView {
	/** id */
	public Integer id;
	public String type;
	/** 标题 */
	public String title;
	/** 是否感兴趣 */
	public Boolean is_myinterest;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIs_myinterest() {
		return is_myinterest;
	}

	public void setIs_myinterest(Boolean is_myinterest) {
		this.is_myinterest = is_myinterest;
	}

}

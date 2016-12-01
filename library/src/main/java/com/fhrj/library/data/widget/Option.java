package com.fhrj.library.data.widget;

import com.fhrj.library.data.DTB;

/***
 * 下拉框、单选框、复选框选项Bean
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:30:06
 */
public class Option extends DTB<Object, Object>{

	private static final long serialVersionUID = -724868344947644938L;

	private String label = "请选择...";
	
	private String value = "";
	
	public Option() {
		
	}

	public Option(String value,String label) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return label;
	}
	
	
}

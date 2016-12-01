package com.fhrj.library.data;

/***
 *  数据传输Bean
 * @author ZhangGuoHao
 * @date 2016年4月7日 下午8:29:33
 */
public class DTB<K, V> extends DTO<K, V>{
	private static final long serialVersionUID = 1L;

	private String name;

	private String nameCN;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameCN() {
		return nameCN;
	}

	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}
}

package com.mytv365.zb.model;
/***
 * 设置
 * 
 * @author ZhangGuoHao
 * @date 2016年6月7日 下午7:04:43
 */
public class Up {
	/*名称 */
	private String name;
	/*是否显示左边图标 */
	private boolean isLeft=false;
	/*是否显示右边图标 */
	private boolean isRight=false;
	/*左边图片 */
	private int ioc;
	/*右边图片*/
	private String nameRight;

	public Up(){}
	/***
	 * 
	 * @param name 名称
	 * @param isLeft 是否显示左边图标
	 * @param isRight 是否显示右边图标
	 * @param ioc 左边图片
	 */
	public Up(String name, boolean isLeft, boolean isRight, int ioc) {
		super();
		this.name = name;
		this.isLeft = isLeft;
		this.isRight = isRight;
		this.ioc = ioc;
	}


	/***
	 *
	 * @param name 名称
	 * @param isRight 是否显示右边图标
	 * @param ioc 左边图片
	 */
	public Up(String name, String nameRight,boolean isRight, int ioc) {
		super();
		this.name = name;
		this.nameRight = nameRight;
		this.isRight = isRight;
		this.ioc = ioc;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public int getIoc() {
		return ioc;
	}

	public void setIoc(int ioc) {
		this.ioc = ioc;
	}

	public String getNameRight() {
		return nameRight;
	}

	public void setNameRight(String nameRight) {
		this.nameRight = nameRight;
	}
}

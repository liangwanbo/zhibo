package com.mytv365.zb.widget.stock;

public class StickEntity {

	/** Highest Value */
	private double high;

	/** Lowest Value */
	private double low;

	/** Date */
	private int date;

	/** 5均线 */
	private float VMA5;

	/** 10均线 */
	private float VMA10;

	/** 25均线 */
	private float VMA20;

	/** 柱子颜色 */
	private int columnColor;

	public int getColumnColor() {
		return columnColor;
	}

	public void setColumnColor(int columnColor) {
		this.columnColor = columnColor;
	}

	public float getVMA5() {
		return VMA5;
	}

	public void setVMA5(float vMA5) {
		VMA5 = vMA5;
	}

	public float getVMA10() {
		return VMA10;
	}

	public void setVMA10(float vMA10) {
		VMA10 = vMA10;
	}

	public float getVMA20() {
		return VMA20;
	}

	public void setVMA20(float vMA20) {
		VMA20 = vMA20;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public StickEntity(double high, double low, int date) {
		super();
		this.high = high;
		this.low = low;
		this.date = date;
	}

	public StickEntity() {
		super();
	}
}

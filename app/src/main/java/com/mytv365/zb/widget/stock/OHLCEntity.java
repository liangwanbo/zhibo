package com.mytv365.zb.widget.stock;

public class OHLCEntity {

	/** Open Session Value */
	private double open;

	/** Highest Value */
	private float high;

	/** Lowest Value */
	private float low;

	/** Close Session Value */
	private double close;

	/** Date */
	private int date;

	/** 涨跌 */
	private double change;

	/** 涨跌幅 */
	private double changep;

	/** 成交额 */
	private double cje;

	/** 成交量 */
	private double cjl;

	/** 股价均量线 */
	private double gjjlx;

	public double getGjjlx() {
		return gjjlx;
	}

	public void setGjjlx(double gjjlx) {
		this.gjjlx = gjjlx;
	}

	/** 是否绘制经线（代表时间段） */
	private boolean isDrawLongitude;

	/** 5日均线 */
	private float MA5;

	/** 10日均线 */
	private float MA10;

	/** 25日均线 */
	private float MA20;

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getChangep() {
		return changep;
	}

	public void setChangep(double changep) {
		this.changep = changep;
	}

	public double getCje() {
		return cje;
	}

	public void setCje(double cje) {
		this.cje = cje;
	}

	public double getCjl() {
		return cjl;
	}

	public void setCjl(double cjl) {
		this.cjl = cjl;
	}

	public boolean isDrawLongitude() {
		return isDrawLongitude;
	}

	public void setDrawLongitude(boolean isDrawLongitude) {
		this.isDrawLongitude = isDrawLongitude;
	}

	public float getMA5() {
		return MA5;
	}

	public float getMA10() {
		return MA10;
	}

	public void setMA10(float mA10) {
		MA10 = mA10;
	}

	public float getMA20() {
		return MA20;
	}

	public void setMA20(float mA20) {
		MA20 = mA20;
	}

	public void setMA5(float mA5) {
		MA5 = mA5;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public OHLCEntity(double open, float high, float low, double close, int date) {
		super();
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.date = date;
	}

	public OHLCEntity() {
		super();
	}
}

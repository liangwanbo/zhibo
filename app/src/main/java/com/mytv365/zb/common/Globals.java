package com.mytv365.zb.common;

import android.annotation.SuppressLint;


/**
 * ������
 */
public class Globals {
	public static final String TAG 				= "TEST";


	//handlerMyLocation
	/** 搜索接口 通过传入股票名称关键字，股票代码关键字，股票名称拼音首字母关键字，搜索出要的股票列表 */
	public final static int HANDLE_SEARCH = 1;
	/** 获取自选股 */
	public final static int HANDLE_GET_SELF = 2;
	/** 添加自选股 */
	public final static int HANDLE_ADD_SELF = 3;
	/** 沪深行情 */
	public final static int HANDLE_HSHQ = 4;
	/** 个股行情 */
	public final static int HANDLE_GGHQ = 5;
	/** 板块列表 一级 */
	public final static int HANDLE_BLOCK_LIST = 6;
	/** 取得板块中所有股票 */
	public final static int HANDLE_BLOCK_STOCK = 7;
	/** 涨幅榜 */
	public final static int HANDLE_UP_LIST = 8;
	/** 跌幅榜 */
	public final static int HANDLE_DOWN_LIST = 9;
	/** 个股新闻列表 */
	public final static int HANDLE_NEWS_LIST = 10;
	/** 个股公告列表 */
	public final static int HANDLE_NOTICE_LIST = 11;
	/** 新闻内容详细 */
	public final static int HANDLE_NEWS_ID = 12;
	/** 公告内容详细 */
	public final static int HANDLE_NOTICE_ID = 13;
	

	/************ path ****************/
	/** log����ļ� */
	public static final String FILE_LOG 	= "log.txt";
	/** ��Ŀ¼ */
	// public static final String PATH_ROOT =
	// Environment.getExternalStorageDirectory().getPath() + "/";
	@SuppressLint("SdCardPath")
	public static final String PATH_ROOT 	= "/sdcard/";
	/** ��Ŀ�ļ�·�� */
	public static final String PATH_CZW 	= PATH_ROOT + "found/";
	/** log��Ϣ·�� */
	public static final String PATH_LOG 	= PATH_CZW + "log/";
	/** ͼƬ·�� */
	public static final String PATH_IMG 	= PATH_CZW + "image/";
}

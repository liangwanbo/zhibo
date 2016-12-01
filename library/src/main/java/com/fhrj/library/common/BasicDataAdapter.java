package com.fhrj.library.common;

import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.base.impl.BaseViewHolder;

import android.view.View;
import android.view.ViewGroup;



/***
 *  * 基本的列表数据展示Adapter，只需要传入BaseViewHolder的实现类即可
 * 具体示例写法见ImageListviewActivity.java
 * 
 * @see http://git.oschina.net/zftlive/AjavaAndroidSample/blob/master/src/com/zftlive/android/sample/image/ImageListviewActivity.java
 * @author ZhangGuoHao
 * @date 2016年4月18日 上午10:37:02
 */
public class BasicDataAdapter<T> extends BaseMAdapter<T> {

	/**
	 * Item控件缓存ViewHolder
	 */
	private BaseViewHolder<T> mViewHolder;
	
	public BasicDataAdapter(BaseViewHolder<T> mViewHolder){
		this.mViewHolder = mViewHolder;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(null == convertView){
			mViewHolder = mViewHolder.createViewHolder();
			convertView = mViewHolder.inflateLayout();
			mViewHolder.initView(convertView);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (BaseViewHolder<T>)convertView.getTag();
		}
		//设置数据
		T rowData = getItem(position);
		mViewHolder.fillData(rowData, position);
		return convertView;
	}


	@Override
	public boolean removeItem(Object object) {
		return super.removeItem(object);
	}

	@Override
	public Object removeItem(int location) {
		return super.removeItem(location);
	}
}
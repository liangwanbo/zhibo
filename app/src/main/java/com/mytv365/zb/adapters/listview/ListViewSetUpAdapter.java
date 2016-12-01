package com.mytv365.zb.adapters.listview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseViewHolder;
import com.mytv365.zb.R;
import com.mytv365.zb.model.Up;


/****
 * listview项绑定控件数据
 * 
 * @author ZhangGuoHao
 * @date 2016年4月12日 下午9:48:05
 */
public class ListViewSetUpAdapter extends BaseViewHolder<Up> {
	/**当前行*/
	private LinearLayout item;
	/** 图片背景 */
	private LinearLayout lay;
	/** 名称 */
	private TextView name;
	/** 图标 */
	private ImageView ioc;
	/**右边图标*/
	private ImageView right;
	
	/** 图片处理 */
	private Context mContext;

	public ListViewSetUpAdapter(Context mContext) {
		super(mContext);
		this.mContext = mContext;
	}

	@Override
	public BaseViewHolder<Up> createViewHolder() {
		return new ListViewSetUpAdapter(mContext);
	}

	@Override
	public int bindLayout() {
		return R.layout.item_listview02;
	}

	@Override
	public void initView(View view) {
		name = (TextView) mItemView.findViewById(R.id.name);
		ioc = (ImageView) mItemView.findViewById(R.id.ioc);
		right=(ImageView)mItemView.findViewById(R.id.right);
		lay = (LinearLayout) mItemView.findViewById(R.id.lay);
		item=(LinearLayout)mItemView.findViewById(R.id.item);
		item.setVisibility(View.VISIBLE);
//		item.setPadding(0, ToolUnit.pxTosp(20), 0, ToolUnit.pxTosp(20));
	}

	@Override
	public void fillData(Up rowData, int position) {
		name.setText(rowData.getName());
		if(rowData.isLeft()){
			lay.setVisibility(View.VISIBLE);
			ioc.setImageResource(rowData.getIoc());
		}else{
			lay.setVisibility(View.GONE);
		}
		if(rowData.isRight()){
			right.setVisibility(View.VISIBLE);
		}else{
			right.setVisibility(View.GONE);
			
		}

	}

}

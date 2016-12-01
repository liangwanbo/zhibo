package com.mytv365.zb.widget.stock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.model.ItemGgxqTime;
import com.mytv365.zb.model.ItemGgxqTime2;
import com.mytv365.zb.utils.UtilFont;


public class GgxqView extends View {
	float viewWidth, viewHeight;

	/**
	 * 最大涨/跌价格,单位0.01元
	 */
	public float getMaxPrice() {
		float maxPrice = 0;// 最大涨/跌价格,单位0.01元
		for (int i = 0; i < DataCenter.getInstance().listGgxqTime.size(); i++) {
			ItemGgxqTime item = DataCenter.getInstance().listGgxqTime.get(i);
			float preclose = Float.parseFloat(DataCenter.getInstance().ggxqBean.preclose);
			float fClose = Float.parseFloat(item.close);
			float tmp = (Math.abs(fClose - preclose));
//			Log.i("GgxqView", "===getMaxPrice():preclose" + preclose+" fClose："+fClose+" tmp:"+tmp);
			if (maxPrice < tmp) {
				maxPrice = tmp;
			}
		}
//		Log.i("GgxqView", "===maxPrice:" + maxPrice);
		return maxPrice;
	}
	/**
	 * 最大成交额
	 */
	public float getMaxCjl() {
		float maxCjl = 0;// 最大涨/跌价格,单位0.01元
		for (int i = 0; i < DataCenter.getInstance().listGgxqTime.size(); i++) {
			ItemGgxqTime item = DataCenter.getInstance().listGgxqTime.get(i);
			float cjl = Float.parseFloat(item.cjl);
			if (maxCjl < cjl) {
				maxCjl = cjl;
			}
		}
//		Log.i("GgxqView", "===maxCjl:" + maxCjl);
		return maxCjl;
	}

	public GgxqView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		try {
			Log.i("GgxqView", "===viewWidth:"+viewWidth+" viewHeight:"+viewHeight);
			Rect rect = new Rect(0, 0, (int)viewWidth, (int)viewHeight);
			Paint paint = new Paint();
			// 画背景
			paint.setColor(0xFF000000);
			canvas.drawRect(rect, paint);
			// 线
			paint.setColor(0xFFff0000);
			//横
			for (int i = 0; i <= 4; i++) {
				canvas.drawLine(viewWidth * i / 4, 0, viewWidth * i / 4, viewHeight, paint);
			}
			//竖
			for (int i = 0; i <= 6; i++) {
				canvas.drawLine(0, viewHeight * i / 6, viewWidth, viewHeight * i / 6, paint);
			}
			 
			paint.setColor(0xFFFFFFFF);
			paint.setTextSize(20);
			//价格
			canvas.drawText(DataCenter.getInstance().ggxqBean.hprice, 0, viewHeight*0/6+20, paint);//最高
			canvas.drawText(UtilFont.getMean(DataCenter.getInstance().ggxqBean.preclose, DataCenter.getInstance().ggxqBean.hprice), 0, viewHeight*1/6, paint);//半高
			canvas.drawText(DataCenter.getInstance().ggxqBean.preclose, 0, viewHeight*2/6, paint);//昨收
			canvas.drawText(UtilFont.getMean(DataCenter.getInstance().ggxqBean.lprice, DataCenter.getInstance().ggxqBean.preclose), 0, viewHeight*3/6, paint);//半低
			canvas.drawText(DataCenter.getInstance().ggxqBean.lprice, 0, viewHeight*4/6, paint);//最低
//			成交额
			canvas.drawText(String.valueOf(getMaxCjl()), 0, viewHeight*4/6+20, paint);//最高
			canvas.drawText(String.valueOf(getMaxCjl()/2), 0, viewHeight*5/6+20, paint);//半高
			//时间
			canvas.drawText("9:30", 0, viewHeight+20, paint);
			canvas.drawText("11:30/13:00", (viewWidth-110)/2, viewHeight+20, paint);
			canvas.drawText("15:00", viewWidth-50, viewHeight+20, paint);
			//五档
			int h = (int) (viewHeight/10);
			canvas.drawText("5 "+DataCenter.getInstance().ggxqBean.bp5+"  "+DataCenter.getInstance().ggxqBean.bv5, viewWidth, h*1, paint);
			canvas.drawText("4 "+DataCenter.getInstance().ggxqBean.bp4+"  "+DataCenter.getInstance().ggxqBean.bv4, viewWidth, h*2, paint);
			canvas.drawText("3 "+DataCenter.getInstance().ggxqBean.bp3+"  "+DataCenter.getInstance().ggxqBean.bv3, viewWidth, h*3, paint);
			canvas.drawText("2 "+DataCenter.getInstance().ggxqBean.bp2+"  "+DataCenter.getInstance().ggxqBean.bv2, viewWidth, h*4, paint);
			canvas.drawText("1 "+DataCenter.getInstance().ggxqBean.bp1+"  "+DataCenter.getInstance().ggxqBean.bv1, viewWidth, h*5, paint);
			canvas.drawText("1 "+DataCenter.getInstance().ggxqBean.sp1+"  "+DataCenter.getInstance().ggxqBean.sv1, viewWidth, h*6, paint);
			canvas.drawText("2 "+DataCenter.getInstance().ggxqBean.sp2+"  "+DataCenter.getInstance().ggxqBean.sv2, viewWidth, h*7, paint);
			canvas.drawText("3 "+DataCenter.getInstance().ggxqBean.sp3+"  "+DataCenter.getInstance().ggxqBean.sv3, viewWidth, h*8, paint);
			canvas.drawText("4 "+DataCenter.getInstance().ggxqBean.sp4+"  "+DataCenter.getInstance().ggxqBean.sv4, viewWidth, h*9, paint);
			canvas.drawText("5 "+DataCenter.getInstance().ggxqBean.sp5+"  "+DataCenter.getInstance().ggxqBean.sv5, viewWidth, h*10, paint);
			
			DataCenter.getInstance().listGgxqTime2.clear();
			for (int i = 0; i < DataCenter.getInstance().listGgxqTime.size(); i++) {
				ItemGgxqTime item = DataCenter.getInstance().listGgxqTime.get(i);
				float time = UtilFont.getTime(item.time);
				float proceMove1 = UtilFont.getMoveY(item.close);
				proceMove1 = proceMove1*((viewHeight*4/6-0)/2/(getMaxPrice()*100));
				
				ItemGgxqTime2 item2 = new ItemGgxqTime2();
				item2.x = time*(viewWidth/240);
				item2.y = viewHeight*4/6/2-proceMove1;
//				Log.i("GgxqView", "===item.close:"+item.close+" item2.y:"+item2.y);
				float cjl = Float.parseFloat(item.cjl);
				float mxH = (viewHeight/6*2);
				
				item2.cy = viewHeight-cjl*mxH/getMaxCjl();
//				Log.i("GgxqView", "===mxH:"+mxH+" max:"+getMaxCjl()+" cjl:"+cjl+" item2.cy:"+item2.cy);
				
				DataCenter.getInstance().listGgxqTime2.add(item2);
			}
			//画分时线,成交额
			for (int i = 0; i < DataCenter.getInstance().listGgxqTime2.size()-1; i++) {
				ItemGgxqTime2 item11 = DataCenter.getInstance().listGgxqTime2.get(i);
				ItemGgxqTime2 item12 = DataCenter.getInstance().listGgxqTime2.get(i+1);
				if(item12.x<=viewWidth){
					canvas.drawLine(item11.x, item11.y, item12.x, item12.y, paint);//分时线
				}
				canvas.drawLine(item11.x, item11.cy, item11.x, viewHeight, paint);//成交额
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		viewWidth = w-120;
		viewHeight = h-20;
		Log.i("GgxqView", "===onSizeChanged()w:"+w+" h:"+h+" viewWidth:"+viewWidth+" viewHeight:"+viewHeight);
		super.onSizeChanged(w, h, oldw, oldh);
	}

}
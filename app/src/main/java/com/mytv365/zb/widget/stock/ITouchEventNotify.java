package com.mytv365.zb.widget.stock;


public interface ITouchEventNotify {

	public void notifyEventAll(GridChart chart);
	
	public void addNotify(ITouchEventResponse notify);
	
	public void removeNotify(int i);
	
	public void removeAllNotify();
}

package com.mytv365.zb.receiver;

import android.view.View;

import java.util.HashMap;
import java.util.LinkedList;

public interface OnItemClickListener {

	public void onItemClickListener(View v, int position,
									LinkedList<HashMap<String, String>> list);
}

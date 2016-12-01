package com.mytv365.zb.views.activity.stock;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.adapters.SearchAdapter;
import com.mytv365.zb.common.DataCenter;
import com.mytv365.zb.receiver.OnItemClickListener;
import com.mytv365.zb.thread.GetDataToJSONThread;
import com.mytv365.zb.utils.MyUtils;
import com.mytv365.zb.utils.Parse;
import com.mytv365.zb.utils.SaveListObject;
import com.mytv365.zb.utils.UtilHttp;
import com.mytv365.zb.widget.webview.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 个股搜索
 */
public class SearchActivity extends Activity implements OnClickListener {
	LinkedList<HashMap<String, String>> dataList;
	private SearchAdapter mProcessTaskAdapter;
	private static final String TAG = SearchActivity.class.getSimpleName();
	ListView listView;
	LinearLayout back;
	EditText etInput;
	/** Dialog */
	private MyProgressDialog pd;// 进度条
	/** 数据相关 */
	private boolean isSend;// 是否发送到handler
	private int heightDifference;// 键盘高度
	private boolean isShowPop;// 是否显示自定义键盘

	private Map<String, HashMap<String, String>> historyMap;// 历史纪录
	private final String fileName = "history";// 历史纪录文件名

	private ImageView iv_cancel;// 清除按钮

	private int position;// 纪录当前位置

	private PopupWindow pw;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x4000:
				Map<String, Object> map = Parse.getInstance().parseMap(msg.obj);
				if ("100".equals(map.get("resultCode"))) {
					MyUtils.getInstance().showToast(getApplicationContext(),
							"添加自选成功");
				}else {
					dataList.get(position).put("isAdd", "false");
					mProcessTaskAdapter.notifyDataSetChanged();
					MyUtils.getInstance().showToast(getApplicationContext(),
							Parse.getInstance().isNull(map.get("resultMessage")));
				}
				break;
			case 0x4001:
				dataList.get(position).put("isAdd", "false");
				mProcessTaskAdapter.notifyDataSetChanged();
				MyUtils.getInstance().showToast(getApplicationContext(),
						"数据解析异常");
				break;

			case 0x4002:
				dataList.get(position).put("isAdd", "false");
				mProcessTaskAdapter.notifyDataSetChanged();
				MyUtils.getInstance().showToast(getApplicationContext(),
						"网络链接异常");
				break;

			case 0x4003:
				Map<String, Object> map1 = Parse.getInstance()
						.parseMap(msg.obj);
				if ("100".equals(map1.get("resultCode"))) {
					MyUtils.getInstance().showToast(getApplicationContext(),
							"删除自选成功");
				} else {
					dataList.get(position).put("isAdd", "false");
					mProcessTaskAdapter.notifyDataSetChanged();
					MyUtils.getInstance().showToast(getApplicationContext(),
							Parse.getInstance().isNull(map1.get("resultMessage")));
				}
				break;

			case 0x4004:
				dataList.get(position).put("isAdd", "true");
				mProcessTaskAdapter.notifyDataSetChanged();
				MyUtils.getInstance().showToast(getApplicationContext(),
						"数据解析异常");
				break;

			case 0x4005:
				dataList.get(position).put("isAdd", "true");
				mProcessTaskAdapter.notifyDataSetChanged();
				MyUtils.getInstance().showToast(getApplicationContext(),
						"网络链接异常");
				break;

			case com.mytv365.zb.common.Globals.HANDLE_GGHQ:
				// 跳转个股详情
				Bundle bundle = msg.getData();
				Intent intent = new Intent(getApplicationContext(),
						GgxqActivity.class);
				intent.putExtra("code", bundle.getString("code"));

				if (historyMap == null)
					historyMap = (Map<String, HashMap<String, String>>) SaveListObject
							.getInstance()
							.openObject(
									MyUtils.getInstance().getCache(
											getApplicationContext(),
											"USER/" + fileName, fileName, true));
				if (historyMap == null)
					historyMap = new HashMap<String, HashMap<String, String>>();
				HashMap<String, String> hm = new HashMap<String, String>();
				hm.put("code", bundle.getString("code"));
				hm.put("name", bundle.getString("name"));
				hm.put("time", String.valueOf(System.currentTimeMillis()));
				historyMap.put(
						bundle.getString("code") + bundle.getString("market"),
						hm);

				SaveListObject.getInstance()
						.saveObject(
								MyUtils.getInstance().getCache(
										getApplicationContext(),
										"USER/" + fileName, fileName, true),
								historyMap);
				startActivity(intent);
				break;

			default:
				break;
			}
			if (pd.isShowing())
				pd.dismiss();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_search);
		isSend = true;
		pd = new MyProgressDialog(this, R.style.CustomProgressDialog);
		pd.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if (!pd.isShowing()) {
					isSend = false;
				}
			}
		});
		// getList();
		initPopupWindow();
		findLayout();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (etInput.getText() != null
				&& etInput.getText().toString().length() > 0)
			search();
		else
			new asyncLoadSearchResult()
					.execute(UtilHttp.URL_GET_SELF);
	}

	private void findLayout() {
		back = (LinearLayout) findViewById(R.id.ll_back);
		back.setOnClickListener(this);
		etInput = (EditText) findViewById(R.id.ss_et_input);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
		iv_cancel.setOnClickListener(this);
		// etInput.setOnClickListener(this);
		etInput.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				isShowPop = true;
				if (heightDifference > 0) {
					if (!pw.isShowing()) {
						pw.showAtLocation(findViewById(R.id.ll_parent),
								Gravity.BOTTOM, 0, 0);
					}
				}
				return false;
			}
		});
		ViewTreeObserver vto = getWindow().getDecorView().getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				Rect r = new Rect();
				getWindow().getDecorView().getWindowVisibleDisplayFrame(r);

				int screenHeight = getWindow().getDecorView().getRootView()
						.getHeight();
				heightDifference = screenHeight - r.bottom;// r.top（状态栏）
				Log.d("Keyboard Size", "Size: " + heightDifference);
				if (heightDifference > 0) {
					pw.setHeight(heightDifference);
					if (!pw.isShowing() && isShowPop) {
						pw.showAtLocation(findViewById(R.id.ll_parent),
								Gravity.BOTTOM, 0, 0);
					}
				} else {
					if (pw.isShowing()) {
						handler.postDelayed(new Runnable() {
							public void run() {
								pw.dismiss();
							}
						}, 300);
					}
				}

			}
		});
		etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEND
						|| actionId == EditorInfo.IME_ACTION_SEARCH
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					search();
					return true;
				}
				return false;
			}
		});
		etInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					iv_cancel.setVisibility(View.VISIBLE);
					search();
				} else {
//					 if (dataList == null)
//					 dataList = new LinkedList<HashMap<String, String>>();
//					 dataList.clear();
//					 if (mProcessTaskAdapter != null)
//					 mProcessTaskAdapter.notifyDataSetChanged();
					new asyncLoadSearchResult()
							.execute(UtilHttp.URL_GET_SELF);
					iv_cancel.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		dataList = new LinkedList<HashMap<String, String>>();
		listView = (ListView) findViewById(R.id.sslist);
		Log.i("test","dataList--"+dataList);
		mProcessTaskAdapter = new SearchAdapter(getApplicationContext(),
				dataList);
		mProcessTaskAdapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClickListener(View v, int position,
					LinkedList<HashMap<String, String>> list) {
				// TODO Auto-generated method stub
				SearchActivity.this.position = position;
				switch (v.getId()) {
				case R.id.addbuttonid:
					list.get(position).put("isAdd", "true");
					mProcessTaskAdapter.notifyDataSetChanged();
					new GetDataToJSONThread(
							getApplicationContext(),
							String.format(
									UtilHttp.URL_ADD_SELF+"stockNo=%s",
									list.get(position).get("code")), handler,
							0x4000, 0x4002, 0x4001, false).start();
					break;

				case R.id.cleebuttonid:
					list.get(position).put("isAdd", "false");
					mProcessTaskAdapter.notifyDataSetChanged();
					new GetDataToJSONThread(
							getApplicationContext(),
							String.format(
									UtilHttp.URL_DEL_SELF+"stockNo=%s",
									list.get(position).get("code")), handler,
							0x4003, 0x4005, 0x4004, false).start();
					break;

				case R.id.rl_parent:
					isSend = true;
					pd.show();
					MyUtils.getInstance().setKeyBoardGone(
							getApplicationContext(), etInput);
					if (pw.isShowing()) {
						handler.postDelayed(new Runnable() {
							public void run() {
								pw.dismiss();
							}
						}, 300);
					}
					runGghq(Parse.getInstance().isNull(
							list.get(position).get("code")),
							Parse.getInstance().isNull(
									list.get(position).get("name")));
					break;

				default:
					break;
				}
				dataList = list;
			}
		});
		listView.setAdapter(mProcessTaskAdapter);

	}

	private void search() {
		String inputkey = etInput.getEditableText().toString().trim();
		if ("".equals(inputkey)) {
			Toast.makeText(SearchActivity.this, "请输入关键词", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		String urlstr = null;
		String optionalUnit = UtilHttp.URL_GET_SELF;
		try {
			urlstr = String
					.format(UtilHttp.URL_SEARCH+"codeOrName=%s",
							java.net.URLEncoder.encode(inputkey, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (urlstr != null) {
			new asyncLoadSearchResult().execute(urlstr, optionalUnit);
		}

	}
	LinkedList<HashMap<String, String>> results = null;// 搜索列表
	List<Map<String, String>> list = null;// 自选列表
	private class asyncLoadSearchResult extends
			AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			historyMap = (Map<String, HashMap<String, String>>) SaveListObject
					.getInstance().openObject(
							MyUtils.getInstance().getCache(
									getApplicationContext(),
									"USER/" + fileName, fileName, true));
			LinkedList<HashMap<String, String>> historyList = new LinkedList<HashMap<String, String>>();
			if (historyMap != null) {
				for (String key : historyMap.keySet()) {
					historyMap.get(key).put("isShowTitle", "false");
					historyList.add(historyMap.get(key));
				}
				for (int i = 0; i < historyList.size(); i++) {
					for (int j = i; j < historyList.size(); j++) {
						if (Parse.getInstance().parseLong(
								historyList.get(i).get("time")) < Parse
								.getInstance().parseLong(
										historyList.get(j).get("time"))) {

							HashMap<String, String> hm = historyList.get(i);
							historyList.set(i, historyList.get(j));
							historyList.set(j, hm);
						}
					}
				}
				if (historyList.size() > 0) {
					historyList.get(0).put("isShowTitle", "true");
				}
			}
			if (params.length == 2) {
				String restults = UtilHttp.getNetString(params[0]);// 搜索列表
				results = DataCenter.parseSearchList(restults);
//				String optionalUnit = UtilHttp.getNetString(params[1]);// 自选列表
				getselfList(params[1]);

			} else {
				results = new LinkedList<HashMap<String, String>>();
//				String optionalUnit = UtilHttp.getNetString(params[0]);// 自选列表
				getselfList(params[0]);
			}
			if (results == null || list == null)
				return false;
			if (params.length == 1) {
				results.addAll(historyList);
			}else{
				results.addAll(historyList);
			}
			for (int i = 0; i < results.size(); i++) {
				for (int j = 0; j < list.size(); j++) {
					if (results.get(i).get("code")
							.equals(list.get(j).get("stock_id"))) {
						results.get(i).put("isAdd", "true");
					}
				}
			}
			if (results != null) {
				dataList.clear();
				dataList.addAll(results);
				return true;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				mProcessTaskAdapter.notifyDataSetChanged();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_back://
			Log.i("SearchActivity", "===back");
			this.finish();
			break;

		case R.id.iv_cancel:
			etInput.setText("");
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化PopupWindow
	 */
	private void initPopupWindow() {
		KeyboardOnClickListener clickListener = new KeyboardOnClickListener();
		View view = getLayoutInflater().inflate(R.layout.layout_keyboard, null);
		view.findViewById(R.id.tv_600).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_1).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_2).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_3).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_backspace).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_601).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_4).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_5).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_6).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_002).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_000).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_7).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_8).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_9).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_300).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_abc).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_0).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_dian).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_gone).setOnClickListener(clickListener);
		view.findViewById(R.id.tv_determine).setOnClickListener(clickListener);
		pw = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setTouchable(true);

		// pw.setContentView(view);
	}

	/**
	 * 自定义键盘点击时间
	 * 
	 * @author dingrui
	 * 
	 */
	class KeyboardOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_backspace:
				// 退格
				if (etInput.getText() == null
						|| etInput.getText().toString().length() == 0)
					return;
				String str = etInput.getText().toString();
				str = str.substring(0, str.length() - 1);
				etInput.setText(str);
				etInput.setSelection(str.length());
				break;

			case R.id.tv_determine:
				// 确定
				MyUtils.getInstance().setKeyBoardGone(getApplicationContext(),
						etInput);
				if (pw.isShowing()) {
					handler.postDelayed(new Runnable() {
						public void run() {
							search();
						}
					}, 300);
				}
				break;

			case R.id.tv_gone:
				// 隐藏
				MyUtils.getInstance().setKeyBoardGone(getApplicationContext(),
						etInput);
				if (pw.isShowing()) {
					handler.postDelayed(new Runnable() {
						public void run() {
							pw.dismiss();
						}
					}, 300);
				}
				break;

			case R.id.tv_abc:
				// 切换系统键盘
				isShowPop = false;
				pw.dismiss();
				break;

			default:
				if (etInput.getText() == null)
					etInput.setText("");
				String str1 = etInput.getText().toString();
				str1 += ((TextView) v).getText().toString();
				etInput.setText(str1);
				etInput.setSelection(str1.length());
				break;
			}
		}

	}

	/**
	 * 个股行情
	 */
	private void runGghq(final String code,
			final String name) {
		new Thread() {
			@Override
			public void run() {

				DataCenter.getInstance().mStrGghq = DataCenter.getInstance().http
						.getGghq(code);
				if (DataCenter.getInstance().mStrGghq != null
						&& DataCenter.getInstance().mStrGghq.length() > 20) {
					DataCenter.getInstance().parserGgxq();
					DataCenter.getInstance().parserGgxqTime();
					DataCenter.getInstance().parseMX();
					// DataCenter.parseKLine(DataCenter.getInstance().mStrGghq);
					Message muest = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("code", String.valueOf(code));
					bundle.putString("name", name);
					muest.setData(bundle);
					muest.what = com.mytv365.zb.common.Globals.HANDLE_GGHQ;
					if (isSend)
						handler.sendMessage(muest);
				}

				// DataCenter.getInstance().mStrGghq =
				// DataCenter.getInstance().http
				// .getGghq(code, market);
				// if (DataCenter.getInstance().mStrGghq.length() > 20) {
				// handler.sendEmptyMessage(com.weitou.stock.Globals.HANDLE_GGHQ);
				// }
			}
		}.start();
	}

	public void getselfList(String url){
		OkGo.get(url)
				.headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
				.headers("header1", "headerValue1")
				.setCertificates()
				.execute(new StringCallback() {
					@Override
					public void onSuccess(String s, Call call, Response response) {
						JSONObject object = null;
						try {
							object = new JSONObject(s);
							Log.i("test","s--"+s);
							int resultcode = object.getInt("resultCode");
							JSONArray resultData = object.optJSONArray("resultData");
							if (resultcode == 100) {
									list = DataCenter.getInstance().parseOptionalUnit(s);
							}
						}catch(JSONException e){
							e.printStackTrace();
						}

					}
					@Override
					public void onError(Call call, Response response, Exception e) {
						super.onError(call, response, e);
					}

				});
	}
}

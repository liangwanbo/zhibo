package com.mytv365.zb.thread;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mytv365.zb.R;
import com.mytv365.zb.utils.GetApiData;
import com.mytv365.zb.utils.MyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;


public class GetFileThread extends Thread {
	private Context context;
	private String uri;
	private Handler handler;
	private int msgWhat_OK;
	private int msgWhat_TIME_OUT;
	private AlertDialog.Builder ab;
	private DialogInterface di;

	private int length;// 文件大小

	/** 控件相关 */
	private TextView tv_progress;// 进度值
	private ProgressBar pb_progressbar;// 进度条

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x1999:
				di = ab.show();
				tv_progress.setText("0 / 100%");
				pb_progressbar.setProgress(0);
				break;
			case 0x2000:
				int cont = (Integer) msg.obj;
				// tv_progress.setText(((float)cont / 100 * length) + " / " +
				// length
				// + "   " + cont + " / 100%");
				tv_progress.setText(cont + " / 100%");
				pb_progressbar.setProgress(cont);
				break;

			case 0x2001:
				if (di == null)
					break;
				di.dismiss();
				break;

			default:
				break;
			}
		}
	};

	public GetFileThread(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT) {
		this.context = context;
		this.uri = uri;
		this.handler = handler;
		this.msgWhat_OK = msgWhat_OK;
		this.msgWhat_TIME_OUT = msgWhat_TIME_OUT;
		if (ab == null)
			ab = new AlertDialog.Builder(context);
		ab.setCancelable(false);
		ab.setTitle("下载中...");
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_progressbar, null);
		tv_progress = (TextView) view.findViewById(R.id.tv_progress);
		pb_progressbar = (ProgressBar) view.findViewById(R.id.pb_progressbar);
		ab.setView(view);
		mHandler.sendEmptyMessage(0x1999);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		getFile(context, uri, handler, msgWhat_OK, msgWhat_TIME_OUT);
	}

	private void getFile(Context context, String uri, Handler handler,
			int msgWhat_OK, int msgWhat_TIME_OUT) {
		File file = MyUtils.getInstance().getCache(context, "download", uri,
				true);
		if (uri.contains(".")) {
			String[] uris = uri.split("\\.");
			file = new File(file.getPath() + "." + uris[uris.length - 1]);
		}
		if (file.exists()) {
			Message msg = mHandler.obtainMessage();
			msg.obj = 100;
			msg.what = 0x2000;
			mHandler.sendMessage(msg);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			mHandler.sendEmptyMessage(0x2001);
			sendEmptyMessage(msgWhat_OK, file.getPath());
			return;
		}
		HttpURLConnection con = GetApiData.getInstance().getFile(uri);
		if (con == null) {
			if (file.exists())
				file.delete();
			mHandler.sendEmptyMessage(0x2001);
			sendEmptyMessage(msgWhat_TIME_OUT, null);
			return;
		}
		try {
			InputStream in = con.getInputStream();
			length = con.getContentLength();
			OutputStream out = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int a = 0;
			int leng = 0;
			while ((a = in.read(b)) != -1) {
				out.write(b, 0, a);
				leng += a;
				Message msg = mHandler.obtainMessage();
				msg.obj = (int) ((float) leng / length * 100);
				msg.what = 0x2000;
				mHandler.sendMessage(msg);
			}
			out.flush();
			out.close();
			in.close();
			Message msg = mHandler.obtainMessage();
			msg.obj = 100;
			msg.what = 0x2000;
			mHandler.sendMessage(msg);
			Thread.sleep(500);
			mHandler.sendEmptyMessage(0x2001);
			sendEmptyMessage(msgWhat_OK, file.getPath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			if (file.exists())
				file.delete();
			mHandler.sendEmptyMessage(0x2001);
			sendEmptyMessage(msgWhat_TIME_OUT, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			if (file.exists())
				file.delete();
			mHandler.sendEmptyMessage(0x2001);
			sendEmptyMessage(msgWhat_TIME_OUT, null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	/**
	 * 发消息
	 */
	private void sendEmptyMessage(int emptyMessage, Object obj) {
		if (emptyMessage != -1) {
			if (handler != null) {
				Message msg = handler.obtainMessage();
				msg.what = emptyMessage;
				msg.obj = obj;
				handler.sendMessage(msg);
			}
		}
	}
}

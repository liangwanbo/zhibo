package com.mytv365.zb.views.zhibolive.livebase;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mytv365.zb.R;
import com.mytv365.zb.presenters.LiveHelper;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;


/**
 * 文本输入框
 */
public class InputTextMsgDialog extends Dialog {
    private TextView confirmBtn;
    private EditText messageTextView;
    private static final String TAG = InputTextMsgDialog.class.getSimpleName();
    private Context mContext;
    private LiveHelper mLiveControlHelper;
    private InputMethodManager imm;
    private RelativeLayout rlDlg;
    private int mLastDiff = 0;
    private final String reg = "[`~@#$%^&*()-_+=|{}':;,/.<>￥…（）—【】‘；：”“’。，、]";
    private Pattern pattern = Pattern.compile(reg);


    public InputTextMsgDialog(Context context, int theme, LiveHelper presenter){
        super(context, theme);
        mContext = context;
        mLiveControlHelper = presenter;
        setContentView(R.layout.input_text_dialog);
        messageTextView = (EditText) findViewById(R.id.input_message);
        confirmBtn = (TextView) findViewById(R.id.confrim_btn);
//        rlDlg = (RelativeLayout) findViewById(R.id.rl_dlg);
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messageTextView.getText().length() > 0) {
                    sendText("" + messageTextView.getText());
                    imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                    imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    dismiss();
                } else {
                    Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
                }
            }
        });

//        messageTextView.setFocusableInTouchMode(true);
        messageTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_UP) {   // 忽略其它事件
                    return false;
                }

                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        if (messageTextView.getText().length() > 0) {
                            sendText("" + messageTextView.getText());
                            imm.showSoftInput(messageTextView, InputMethodManager.SHOW_FORCED);
                            imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                            dismiss();
                        } else {
                            Toast.makeText(mContext, "input can not be empty!", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });


        final LinearLayout rldlgview = (LinearLayout) findViewById(R.id.rl_inputdlg_view);
        rldlgview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                if (heightDifference <= 0 && mLastDiff > 0) {
                    imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                    dismiss();
                }
                mLastDiff = heightDifference;

            }
        });

        rldlgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(messageTextView.getWindowToken(), 0);
                dismiss();
            }
        });


        messageTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d("null", "afterTextChanged");
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
                Log.d("null", "beforeTextChanged:" + s + "-" + start + "-" + count + "-" + after);

                if (count == 0) {
                    confirmBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.send_btn));
                } else {
                    confirmBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.send_btn_pressed));
                }
            }

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                Log.d("null", "onTextChanged:" + s + "-" + "-" + start + "-" + before + "-" + count);
                if (count == 0) {
                    confirmBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.send_btn));
                } else {
                    confirmBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.send_btn_pressed));
                }
            }
        });

    }

    /**
     * add message text
     */
    public void setMessageText(String strInfo) {
        messageTextView.setText(strInfo);
        messageTextView.setSelection(strInfo.length());
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        mVideoPlayActivity.refreshViewAfterDialog();
    }

    @Override
    public void cancel() {
        super.cancel();
    }


    private void sendText(String msg) {
        if (msg.length() == 0)
            return;
        try {
            byte[] byte_num = msg.getBytes("utf8");
            if (byte_num.length > 160) {
                Toast.makeText(mContext, "input message too long", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        TIMMessage Nmsg = new TIMMessage();
        TIMTextElem elem = new TIMTextElem();
        elem.setText(msg);
        if (Nmsg.addElement(elem) != 0) {
            return;
        }
        mLiveControlHelper.sendGroupText(Nmsg);

    }

    @Override
    public void show() {
        super.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) messageTextView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(messageTextView, 0);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }, 300);
    }
}

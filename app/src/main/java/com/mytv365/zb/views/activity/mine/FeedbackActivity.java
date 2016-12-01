package com.mytv365.zb.views.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fhrj.library.tools.ToolToast;
import com.mytv365.zb.R;
import com.mytv365.zb.model.FeedbackBean;
import com.mytv365.zb.presenters.FeedbackHelper;
import com.mytv365.zb.presenters.viewinface.CommonView;
import com.mytv365.zb.utils.BaseActivity;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/30
 * Description:
 */

public class FeedbackActivity extends BaseActivity implements CommonView, View.OnClickListener {
    private EditText et_content;
    private EditText et_contact;
    private Button btn_ok;
    private FeedbackHelper feedbackHelper;

    @Override
    public int bindLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        initTitle("意见反馈");
        et_content = (EditText) findViewById(R.id.et_content);
        et_contact = (EditText) findViewById(R.id.et_contact);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        feedbackHelper = new FeedbackHelper(this, this);
    }

    @Override
    public void doBusiness(Context mContext) {
    }

    /***
     * 初始化标题
     *
     * @param title
     */
    private void initTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    @Override
    public void showMessage(String msg) {
        ToolToast.showLong(msg);
    }

    @Override
    public void updateView(Object result) {
        //ToolToast.showShort("修改成功");
        if (result instanceof FeedbackBean) {
            //ToolToast.showShort("修改成功");
            Toast.makeText(FeedbackActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok) {
            if (checkInput()) {
                feedbackHelper.feedback(et_content.getText().toString(), et_contact.getText().toString());
            }
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(et_content.getText())) {
            ToolToast.showLong("请输入您的意见...");
            return false;
        }
        return true;
    }
}

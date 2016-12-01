package com.mytv365.zb.views.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fhrj.library.tools.ToolToast;
import com.mytv365.zb.R;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.presenters.UpdatePWDHelper;
import com.mytv365.zb.presenters.viewinface.CommonView;
import com.mytv365.zb.utils.BaseActivity;
import com.mytv365.zb.views.activity.user.LoginActivity;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/30
 * Description:
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener, CommonView {
    private EditText et_old_pwd;
    private EditText et_new_pwd;
    private EditText et_new_pwd2;
    private TextView tv_have_account;
    private Button btn_ok;
    private UpdatePWDHelper updatePWDHelper;

    @Override
    public int bindLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        initTitle("修改密码");
        et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_new_pwd2 = (EditText) findViewById(R.id.et_new_pwd2);
        tv_have_account = (TextView) findViewById(R.id.tv_have_account);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        updatePWDHelper = new UpdatePWDHelper(this, this);
    }

    @Override
    public void doBusiness(Context mContext) {
        btn_ok.setOnClickListener(this);
        SpannableString spanString = new SpannableString("已有账号？去登录");
        spanString.setSpan(new ForegroundColorSpan(Color.RED), 6, spanString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new ClickableSpan() {
                               @Override
                               public void onClick(View widget) {
                                   startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                               }
                           }, 6, spanString.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_have_account.setMovementMethod(LinkMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT == 24) {
            tv_have_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                }
            });
        }
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
    public void onClick(View v) {
        if (v.getId() == R.id.btn_ok && checkInput()) {
            updatePWDHelper.updatePWD(Constant.getToken(), et_old_pwd.getText().toString(), et_new_pwd.getText().toString());
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(et_old_pwd.getText()) || TextUtils.isEmpty(et_new_pwd.getText()) || TextUtils.isEmpty(et_new_pwd2.getText())) {
            ToolToast.showShort("请输入密码");
            return false;
        } else if (!et_new_pwd.getText().toString().equals(et_new_pwd2.getText().toString())) {
            ToolToast.showShort("两次确认密码不一致");
            return false;
        }
        return true;
    }

    @Override
    public void showMessage(String msg) {
        ToolToast.showLong(msg);
    }

    @Override
    public void updateView(Object result) {
            ToolToast.showShort("修改成功");
            finish();
    }
}

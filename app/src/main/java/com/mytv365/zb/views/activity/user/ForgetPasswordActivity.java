package com.mytv365.zb.views.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolOKHttp;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.common.MyOnFocusChange;
import com.mytv365.zb.http.HttpUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/***
 * 忘了密码
 *
 * @author ZhangGuoHao
 * @date 2016年6月13日 上午9:25:16
 */
public class ForgetPasswordActivity extends BaseActivity {
    private Context mContext;
    private String forget_password = "forget_password";
    /*手机号 */
    private EditText username;
    /*密码 */
    private EditText password;
    /*验证码 */
    private EditText code;
    /*发送验证码 */
    private Button send;
    /*找回密码 */
    private Button forget;

    /*手机号*/
    private String phs;
    /* 密码*/
    private String passwords;
    /* 验证码*/
    private String codes;
    private boolean isCode = false;
    private AlertDialog dialog;


    @Override
    public int bindLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle("找回密码");



    }

    @Override
    public void doBusiness(final Context mContext) {
        this.mContext = mContext;


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwprd);
        code = (EditText) findViewById(R.id.code);
        send = (Button) findViewById(R.id.send);
        forget = (Button) findViewById(R.id.forget);
        focusChange();
        // 点击事件
        onClick();
    }


    /***
     * 初始化标题
     */
    private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    /***
     * 验证信息
     *
     * @return
     */
    private boolean verification() {
        if (isCode == false) {
            ToolAlert.toastShort("未发送手机验证码");
            return false;
        } else if (phs.equals("")) {
            ToolAlert.toastShort("手机号不能为空");
            return false;
        } else if (phs.length() != 11) {
            ToolAlert.toastShort("手机号格式不正确");
            return false;
        } else if (codes.equals("")) {
            ToolAlert.toastShort("验证码不能为空");
            return false;
        } else if (passwords.equals("")) {
            ToolAlert.toastShort("密码不能为空");
            return false;
        } else if (passwords.length() < 6) {
            ToolAlert.toastShort("密码不能少于6位");
            return false;
        } else {
            return true;
        }

    }

    /***
     * 焦点事件
     */
    private void focusChange() {
        username.setOnFocusChangeListener(new MyOnFocusChange(username));
        password.setOnFocusChangeListener(new MyOnFocusChange(password));
        code.setOnFocusChangeListener(new MyOnFocusChange(code));
    }

    /***
     * 点击事件
     */
    private void onClick() {
        send.setOnClickListener(new MyOnClick(2));
        forget.setOnClickListener(new MyOnClick(1));
    }


    /***
     * 发送验证码
     */
    private void sendCode() {
        phs = username.getText().toString().trim();
        //dialog = ToolAlert.dialog(mContext, R.layout.public_dialog_load);

        if (!phs.equals("") && phs != null) {
            if (phs.length() == 11) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("phone", phs);
                ToolOKHttp toolOKHttp = new ToolOKHttp();
                toolOKHttp.post(HttpUrl.registerCode, map, new ToolOKHttp.HttpCallback() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        dialog = ToolAlert.dialog(mContext, R.layout.public_dialog_load);
                        dialog.show();

                    }

                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject object = new JSONObject(data);
                            if (object.getString("resultCode").equals("100")) {
                                Tool.sendCode(mContext, send, 90);
                                isCode = true;
                            } else {
                                ToolAlert.toastShort(object.getString("resultMessage"));
                                isCode = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.hide();
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        ToolAlert.toastShort(msg);
                        dialog.hide();
                    }
                });
            } else {
                ToolAlert.toastShort("手机号格式不正确");
            }

        } else {
            ToolAlert.toastShort("手机号不能为空");
        }
       // dialog.show();
       // dialog.hide();
    }

    /****
     * 点击事件
     *
     * @author: 张国浩
     * @date: 2016年6月30日 下午6:51:18
     */
    public class MyOnClick implements OnClickListener {
        private int item = 0;

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1:// 找回密码
                    sendForgetPassword();
                    break;
                case 2:// 发送验证码
                    sendCode();
                    break;
                default:
                    break;
            }
        }
    }


    /***
     * 登录
     */
    private void sendForgetPassword() {
        phs = username.getText().toString().trim();
        codes = code.getText().toString().trim();
        passwords = password.getText().toString().trim();

        OkGo.post(HttpUrl.Wpassword)
                .headers("Connection", "close")
                .headers("header1", "headerValue1")
                .params("phone",phs)
                .params("password",passwords)
                .params("code",codes)
                .setCertificates()
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject json=new JSONObject(s);
                            if(json.getString("resultCode").equals("100")){
                                ToolAlert.toastShort("密码修改成功");
                                ForgetPasswordActivity.this.finish();

                            }else{
                                ToolAlert.toastShort("密码修改失败");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog.dismiss();
        }
    }

}

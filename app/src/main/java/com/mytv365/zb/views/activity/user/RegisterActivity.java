package com.mytv365.zb.views.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolOKHttp;
import com.mytv365.zb.R;
import com.mytv365.zb.common.MyOnFocusChange;
import com.mytv365.zb.http.HttpUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/***
 * 注册
 *
 * @author: 张国浩
 * @date: 2016年6月30日 下午7:12:53
 */
public class RegisterActivity extends BaseActivity {
    private String register = "register";


    private Context mContext;
    /* 手机号 */
    private EditText ph;
    /* 密码 */
    private EditText password;
    /* 密码 */
    private EditText password1;
    /* 验证码 */
    private EditText code;
    /* 发送验证码 */
    private Button send;
    /* 注册密码 */
    private Button registerBut;
    /* 登录 */
    private TextView login;
    /*协议*/
    private CheckBox agreement;
    /*协议内容*/
    private TextView agreementText;

    /*手机号*/
    private String phs;
    /* 密码*/
    private String passwords;
    /* 密码 */
    private String passwords1;
    /* 验证码*/
    private String codes;

    private boolean isCode = false;


    private AlertDialog dialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle("用户注册");
    }

    @Override
    public void doBusiness(final Context mContext) {
        this.mContext = mContext;


        ph = (EditText) findViewById(R.id.ph);
        code = (EditText) findViewById(R.id.code);
        password = (EditText) findViewById(R.id.passwprd);
        password1 = (EditText) findViewById(R.id.passwprd1);

        registerBut = (Button) findViewById(R.id.register);
        send = (Button) findViewById(R.id.send);
        login = (TextView) findViewById(R.id.login);

        agreement = (CheckBox) findViewById(R.id.agreement);
        agreementText = (TextView) findViewById(R.id.agreement_text);


        SpannableStringBuilder builder = Tool.textViewColor(login.getText()
                .toString(), 5, 9, ContextCompat
                .getColor(mContext, R.color.theme_select));
        login.setText(builder);

        focusChange();
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
     * 焦点事件
     */
    private void focusChange() {
        ph.setOnFocusChangeListener(new MyOnFocusChange(ph));
        password.setOnFocusChangeListener(new MyOnFocusChange(password));
        password1.setOnFocusChangeListener(new MyOnFocusChange(password1));
        code.setOnFocusChangeListener(new MyOnFocusChange(code));
    }

    /***
     * 点击事件
     */
    private void onClick() {
        registerBut.setOnClickListener(new MyOnClick(1));
        send.setOnClickListener(new MyOnClick(2));
        login.setOnClickListener(new MyOnClick(3));
        agreementText.setOnClickListener(new MyOnClick(4));

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
                case 1:// 注册
                    sendRegister();
                    break;
                case 2:// 发送验证码
                    sendCode();
                    break;
                case 3:// 登录
                    finish();
                    break;
                case 4:// 协议内容
                    break;
                default:
                    break;
            }
        }
    }


    /***
     * 验证注册信息
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
        } else if (!passwords.equals(passwords1)) {
            ToolAlert.toastShort("两次密码不一致");
            return false;
        } else if (agreement.isChecked() == true) {
            ToolAlert.toastShort("您未同意炎黄用户协议");
            return false;
        } else {
            return true;
        }

    }

    /***
     * 发送验证码
     */
    private void sendCode() {
        phs = ph.getText().toString().trim();
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
    }

    /***
     * 登录
     */
    private void sendRegister() {
        codes = code.getText().toString().trim();
        passwords = password.getText().toString().trim();
        passwords1 = password1.getText().toString().trim();
        if (verification()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("phone", phs);//手机号
            map.put("securityCode", codes);//验证码
            map.put("passWord", passwords); //密码
            map.put("source", "1"); //来源

            ToolOKHttp toolOKHttp = new ToolOKHttp();
            toolOKHttp.post(HttpUrl.register, map, new ToolOKHttp.HttpCallback() {
                @Override
                public void onStart() {
                    super.onStart();
                    dialog = ToolAlert.dialog(mContext, R.layout.public_dialog_load);
                    dialog.show();

                }

                @Override
                public void onSuccess(String data) {
//                    ToolAlert.toastShort(data);
                    try {
                        JSONObject object = new JSONObject(data);
                        String resultMessage = object.getString("resultMessage");
                        if (object.getInt("resultCode") == 100) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, resultMessage, Toast.LENGTH_SHORT).show();
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
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}

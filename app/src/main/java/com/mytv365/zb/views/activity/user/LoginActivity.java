package com.mytv365.zb.views.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.mytv365.zb.R;
import com.mytv365.zb.common.BaseActivity;
import com.mytv365.zb.common.Constant;
import com.mytv365.zb.common.MyOnFocusChange;
import com.mytv365.zb.http.HttpUrl;
import com.mytv365.zb.model.LoginTeacher;
import com.mytv365.zb.model.MySelfInfo;
import com.mytv365.zb.model.User;
import com.mytv365.zb.presenters.LoginHelper;
import com.mytv365.zb.presenters.viewinface.LoginView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 登陆
 * Created by zhangguohao on 16/8/1.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    private LoginHelper mLoginHeloper;
    private Context mContext;

    private String login = "login";
    /**
     * 用户名
     */
    private EditText username;
    /**
     * 密码
     */
    private EditText password;
    /**
     * 立即注册
     */
    private TextView register;
    /**
     * 忘了密码
     */
    private TextView back;
    /**
     * 登录
     */
    private Button loginBut;
    /**
     * 用户登录输入name
     */
    private String user_username;
    /**
     * 用户登录输入password
     */
    private String user_password;
    /**
     * SharePreferences记录用户登录信息
     */
    private SharedPreferences mSettings = null;
    /**
     * 数据库名
     */
    private String user_key = "USERID_KEY";

    private static final String TAG = "LoginActivity";

    /*加载提示*/
    private AlertDialog dialog;


    /*automatic*/
    private SharedPreferences LoginSharePrefere;


    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public int getcolor() {
        return R.color.touming;
    }

    @Override
    public void getinit() {
        mLoginHeloper = new LoginHelper(this, this);
        // setTintManager(R.color.touming);
        // initTitle("登录");
        LoginSharePrefere = getSharedPreferences("VertificationLogin", MODE_PRIVATE);
        initc();
    }

/*    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }*/

  /*  @Override
    public void initParms(Bundle parms) {
    }
*/
  /*  @Override
    public void initView(View view) {
        mLoginHeloper = new LoginHelper(this, this);
        setTintManager(R.color.touming);
        initTitle("登录");
        LoginSharePrefere = getSharedPreferences("VertificationLogin", MODE_PRIVATE);

    }*/

    public void initc() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwprd);
        register = (TextView) findViewById(R.id.register);
        back = (TextView) findViewById(R.id.back);
        loginBut = (Button) findViewById(R.id.login);


        SpannableStringBuilder builder = Tool.textViewColor(register.getText()
                .toString(), 5, 9, ContextCompat
                .getColor(getApplicationContext(), R.color.white));
        register.setText(builder);
        this.mContext = mContext;
        focusChange();
        mSettings = getSharedPreferences(user_key, Context.MODE_PRIVATE);

        String usernameContent = mSettings.getString("username", "");
        String passwordContent = mSettings.getString("userpassword", "");
        if (usernameContent != null && !"".equals(usernameContent)) {
            username.setText(usernameContent);
        }
        if (passwordContent != null && !"".equals(passwordContent)) {
            password.setText(passwordContent);
        }

        onclick();

    }

  /*  @Override
    public void doBusiness(final Context mContext) {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwprd);
        register = (TextView) findViewById(R.id.register);
        back = (TextView) findViewById(R.id.back);
        loginBut = (Button) findViewById(R.id.login);


        SpannableStringBuilder builder = Tool.textViewColor(register.getText()
                .toString(), 5, 9, ContextCompat
                .getColor(mContext, R.color.theme_select));
        register.setText(builder);
        this.mContext = mContext;
        focusChange();
        mSettings = getSharedPreferences(user_key, Context.MODE_PRIVATE);

        String usernameContent = mSettings.getString("username", "");
        String passwordContent = mSettings.getString("userpassword", "");
        if (usernameContent != null && !"".equals(usernameContent)) {
            username.setText(usernameContent);
        }
        if (passwordContent != null && !"".equals(passwordContent)) {
            password.setText(passwordContent);
        }
        onclick();
    }*/

    /***
     * 初始化标题
     *
     * @param title
     */
   /* private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }*/

    /***
     * 焦点事件
     */
    private void focusChange() {
        username.setOnFocusChangeListener(new MyOnFocusChange(username));
        password.setOnFocusChangeListener(new MyOnFocusChange(password));
    }

    /**
     * 点击事件
     */
    private void onclick() {
        register.setOnClickListener(new MyOnclick(1));
        back.setOnClickListener(new MyOnclick(2));
        loginBut.setOnClickListener(new MyOnclick(3));
    }

    @Override
    public void loginSucc() {
    }

    @Override
    public void loginFail() {

    }

    /**
     * 点击事件
     */
    private class MyOnclick implements View.OnClickListener {
        public int item = 0;

        public MyOnclick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1://注册
                    //getOperation().forward(RegisterActivity.class);
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);

                    break;
                case 2://忘了密码
                    // getOperation().forward(ForgetPasswordActivity.class);
                    Intent inten = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                    startActivity(inten);
                    break;
                case 3://登录
                    sendLogin();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /***
     * 验证注册信息
     *
     * @return
     */
    private boolean verification() {
        if (user_username.equals("")) {
            ToolAlert.toastShort("账号不能为空");
            return false;
        } else if (user_password.equals("")) {
            ToolAlert.toastShort("密码不能为空");
            return false;
        } else if (user_password.length() < 6) {
            ToolAlert.toastShort("密码不能少于6位");
            return false;
        } else {
            return true;
        }
    }

    /***
     * 发送登录
     */
    private void sendLogin() {
        user_username = username.getText().toString();
        user_password = password.getText().toString();
        if (verification()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("phone", user_username);//手机号
            map.put("password", user_password); //密码
            //ToolOKHttp toolOKHttp = new ToolOKHttp();
            OkGo.post(HttpUrl.login)
                    .headers("Connection", "close")           //如果对于部分自签名的https访问不成功，需要加上该控制头
                    .headers("header1", "headerValue1")
                    .params("phone", user_username)
                    .params("password", user_password)
                    .setCertificates()
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject object = new JSONObject(s);
                                int resultCode=object.getInt("resultCode");
                                Log.i(TAG, "onSuccess: 登录返回" + object.toString());
                                String resultMessage=object.getString("resultMessage");
                                Log.i(TAG, "onSuccess: 登录返回resultMessage" + resultMessage+"resultCode  "+resultCode);
                                if (resultCode!=100){
                                    ToolAlert.toastShort(resultMessage);
                                    return;
                                }

                                JSONObject resultdata = object.getJSONObject("resultData");
                                JSONArray jsonarray = resultdata.getJSONArray("moudel");


                                long modelarray[] = new long[jsonarray.length()];
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject modelid=jsonarray.getJSONObject(i);
                                    long modelid1= modelid.getLong("moudelId");
                                    modelarray[i] = modelid1;
                                }



                                if (object.getString("resultType").equals("success")) {
                                    JSONObject resData = object.getJSONObject("resultData");
                                    JSONObject uJson = resData.getJSONObject("user");
                                    User user = new User();
                                    user.setToKen("");
                                    user.setSig(resData.getString("sig"));
                                    user.setAddress(uJson.getString("address"));
                                    user.setNickName(uJson.getString("nickName"));
                                    user.setRoleId(uJson.getString("roleId"));
                                    user.setSex(uJson.getString("sex"));
                                    user.setSign(uJson.getString("sign"));
                                    user.setSource(uJson.getString("source"));
                                    user.setHeadImages(uJson.getString("headImages"));
                                    user.setRealName(uJson.getString("realName"));
                                    user.setBalance(uJson.getString("balance"));
                                    user.setPhone(uJson.getString("phone"));
                                    user.setId(uJson.getString("id"));
                                    user.setDate(uJson.getString("birthDate"));
                                    MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
                                    user.setEmail(uJson.getString("email"));
                                    user.setIntroduction(uJson.getString("introduction"));


                                    if(uJson.getString("roleId").equals("2")){
                                        LoginTeacher loginTeacher=null;
                                        for (long a :
                                                modelarray) {
                                            if (a==2) {
                                                JSONObject  teacherobj=resultdata.getJSONObject("teacher");
                                                loginTeacher=new LoginTeacher();
                                                loginTeacher.setId(teacherobj.getInt("id"));
                                                loginTeacher.setAptitude(teacherobj.getString("aptitude"));
                                                loginTeacher.setPlayUrl(teacherobj.getString("playUrl"));
                                                loginTeacher.setPosition(teacherobj.getString("position"));
                                                Constant.loginTeacher=loginTeacher;
                                            }

                                            if (a==6){
                                                Constant.isvercationReplay=true;
                                            }
                                        }
                                    }

                                    Constant.setUser(user);
                                    Log.e("userid", Constant.getUser().toString());
                                    //ToolAlert.toastShort("登录成功！");
                                    MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
                                    MySelfInfo.getInstance().setNickName(uJson.getString("nickName"));
                                    MySelfInfo.getInstance().setId(resData.getString("sig"));
                                    MySelfInfo.getInstance().setUserSig(uJson.getString("id"));
                                    MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
                                    MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
                                    //ToolAlert.toastShort(uJson.getString("id"));
                                    //ToolAlert.toastShort(resData.getString("sig"));


                                    mLoginHeloper.imLogin(uJson.getString("id"), resData.getString("sig"));
                                    SharedPreferences.Editor editor = LoginSharePrefere.edit();
                                    editor.clear();
                                    editor.putString("username", user_username);
                                    editor.putString("password", user_password);
                                    editor.commit();
                                    finish();
                                } else {

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();


                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ToolAlert.toastShort("请求失败");
                        }
                    });

          /*  OkHttpClientManager.postAsyn(HttpUrl.login, new OkHttpClientManager.StringCallback() {
                @Override
                public void onFailure(Request request, IOException e) {
                }

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        if (object.getString("resultType").equals("success")) {
                            JSONObject resData = object.getJSONObject("resultData");
                            JSONObject uJson = resData.getJSONObject("user");
                            User user = new User();
                            user.setToKen("");
                            // Log.e("Mytoken",resData.getString("toKen"));
                            user.setSig(resData.getString("sig"));
                            /*//*地址*//**//*
                            user.getAddress(uJson.getString("address"));
                            /*//*昵称*//**//*
                            user.setNickName(uJson.getString("nickName"));
                            /*//*角色ID*//**//*
                            user.setRoleId(uJson.getString("roleId"));
                            /*//*性别*//**//*
                            user.setSex(uJson.getString("sex"));
                            /*//*个性签名*//**//*
                            user.setSign(uJson.getString("sign"));
                            /*//*来源*//**//*
                            user.setSource(uJson.getString("source"));
                            /*//*头像*//**//*
                            user.setHeadImages(uJson.getString("headImages"));
                            /*//*真实姓名*//**//*
                            user.setRealName(uJson.getString("realName"));
                            /*//*余额*//**//*
                            user.setBalance(uJson.getString("balance"));
                            /*//*手机*//**//*
                            user.setPhone(uJson.getString("phone"));
                            /*//*用户ID*//**//*
                            user.setId(uJson.getString("id"));

                            Log.e("IDS", uJson.getString("id"));
                            MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
                            /*//*邮箱*//**//*
                            user.setEmail(uJson.getString("email"));
                            /*//*简介*//**//*
                            user.setIntroduction(uJson.getString("introduction"));
                            Constant.setUser(user);
                            ToolAlert.toastShort("登录成功！");

                            Log.i("tag", "onResponse: user"+user.toString());
                            MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
                            MySelfInfo.getInstance().setNickName(uJson.getString("nickName"));
                            MySelfInfo.getInstance().setId(resData.getString("sig"));
                            MySelfInfo.getInstance().setUserSig(uJson.getString("id"));
                            MySelfInfo.getInstance().setAvatar(uJson.getString("headImages"));
                            MySelfInfo.getInstance().setMyRoomNum(Integer.valueOf(uJson.getString("id")));
                            //ToolAlert.toastShort(uJson.getString("id"));
                            //ToolAlert.toastShort(resData.getString("sig"));
                            mLoginHeloper.imLogin(uJson.getString("id"), resData.getString("sig"));


                            SharedPreferences.Editor editor = LoginSharePrefere.edit();
                            editor.clear();
                            editor.putString("username", user_username);
                            editor.putString("password", user_password);
                            editor.commit();
                            finish();
                        } else {
                            ToolAlert.toastShort(object.getString("resultMessage"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, map, "");*/
        }
    }

}

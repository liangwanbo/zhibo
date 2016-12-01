package com.mytv365.zb.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.mytv365.zb.common.Constant;
import com.mytv365.zb.presenters.viewinface.ExitView;
import com.mytv365.zb.presenters.viewinface.LoginView;
import com.mytv365.zb.utils.Constants;
import com.mytv365.zb.utils.SingleDialog;

/**
 * Created by Administrator on 2016/10/30 0030.
 *
 */
public class LoginExitHelper extends Presenter implements LoginView, SingleDialog.SingleDialogListener {
    private LoginHelper mLoginHeloper;
    private BroadcastReceiver receiver;
    private Context mcontext;
    private ExitView exitView;
    private LoginExitHelper loginExitHelper;
    private SingleDialog singleDialog;


    public LoginExitHelper(Context context, final ExitView exitView){
        this.mcontext=context;
        this.exitView=exitView;
        mLoginHeloper=new LoginHelper(mcontext,this);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Constants.BD_EXIT_APP);
        receiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                singleDialog = new SingleDialog(mcontext, 1, LoginExitHelper.this);
                singleDialog.show();
            }
        };
        mcontext.registerReceiver(receiver, intentFilter);
    }


    @Override
    public void onDestory(){
        mcontext.unregisterReceiver(receiver);
    }

    @Override
    public void loginSucc(){
        Toast.makeText(mcontext, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail() {
    }

    @Override
    public void LiveSureComfirm() {
        mLoginHeloper.imLogin(Constant.getUser().getId(), Constant.sig);
        singleDialog.dismiss();
    }

    @Override
    public void LiveCancelBtn() {
        exitView.ExitBackView();
        singleDialog.dismiss();
    }
}

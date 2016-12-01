package com.mytv365.zb.presenters.viewinface;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/28
 * Description:
 */

public interface CommonView extends MvpView {
    void showMessage(String msg);

    void updateView(Object result);
}

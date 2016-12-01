package com.mytv365.zb.model;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/10/8
 * Description:
 */

public class UpdatePWDBean extends BaseBean<Boolean> {
    private String resultMsg;

    public String getResultMessage() {
        return resultMsg;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMsg = resultMessage;
    }

    @Override
    public String toString() {
        return "UpdatePWDBean{" +
                "resultMsg='" + resultMsg + '\'' +
                '}';
    }

}

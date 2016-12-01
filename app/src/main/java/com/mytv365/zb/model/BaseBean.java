package com.mytv365.zb.model;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/10/8
 * Description:
 */

public class BaseBean<T> {
    protected int resultCode;
    protected String resultMessage;
    protected String resultType;
    protected T resultData;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

}

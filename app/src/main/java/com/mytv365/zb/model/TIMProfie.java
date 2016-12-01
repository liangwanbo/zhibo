package com.mytv365.zb.model;

/**
 * Created by Administrator on 2016/10/20 0020.
 * yang
 */
public class TIMProfie {
    private String identifier = "";
    private String nickName = "";
    private String remark = "";
    private String faceUrl = "";


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }


    @Override
    public String toString() {
        return "TIMProfie{" +
                "identifier='" + identifier + '\'' +
                ", nickName='" + nickName + '\'' +
                ", remark='" + remark + '\'' +
                ", faceUrl='" + faceUrl + '\'' +
                '}';
    }

}

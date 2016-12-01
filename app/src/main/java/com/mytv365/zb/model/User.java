package com.mytv365.zb.model;

/**
 * 用户信息
 * Created by zhangguohao on 16/9/20.
 */
public class User {
    private String sig;
    /*地址*/
    private String address;
    /*昵称*/
    private String nickName;
    /*角色ID 1:普通用户2:老师3:主播*/
    private String roleId;
    /*性别*/
    private String sex;
    /*个性签名*/
    private String sign;
    /*来源*/
    private String source;
    /*头像*/
    private String headImages;
    /*真实姓名*/
    private String realName;
    /*余额*/
    private String balance;
    /*手机*/
    private String phone;
    /*用户ID*/
    private String id;
    /*邮箱*/
    private String email;
    /*简介*/
    private String introduction;
    /*用户凭证*/
    private String toKen;
    /*老师*/
    private String aptitude;
    /*老师id*/
    private String teacherid;
    /*老师位置*/
    private String position;
    /*老师播放地址*/
    private String playUrl;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAptitude() {
        return aptitude;
    }

    public void setAptitude(String aptitude) {
        this.aptitude = aptitude;
    }

    public String getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(String teacherid) {
        this.teacherid = teacherid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getAddress(String address) {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public void setHeadImages(String headImages) {
        this.headImages = headImages;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getToKen() {
        return toKen;
    }

    public void setToKen(String toKen) {
        this.toKen = toKen;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getAddress() {
        return address;
    }

    public String getHeadImages() {
        return headImages;
    }


    @Override
    public String toString() {
        return "User{" +
                "sig='" + sig + '\'' +
                ", address='" + address + '\'' +
                ", nickName='" + nickName + '\'' +
                ", roleId='" + roleId + '\'' +
                ", sex='" + sex + '\'' +
                ", sign='" + sign + '\'' +
                ", source='" + source + '\'' +
                ", headImages='" + headImages + '\'' +
                ", realName='" + realName + '\'' +
                ", balance='" + balance + '\'' +
                ", phone='" + phone + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", introduction='" + introduction + '\'' +
                ", toKen='" + toKen + '\'' +
                '}';
    }
}

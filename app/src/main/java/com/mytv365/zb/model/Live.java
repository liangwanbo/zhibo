package com.mytv365.zb.model;

import java.io.Serializable;

/**
 * 直播
 * Created by zhangguohao on 16/9/12.
 */
public class Live implements Serializable{
    /*用户昵称*/
    private String name;
    /*头像*/
    private String ioc;
    /*直播主题 标题*/
    private String title;
    /*封面图片*/
    private String roomIoc;
    /*播放地址*/
    private String payUrl;
    /*用户类型*/
    private String type;
    /*房间状态*/
    private String state;
    /*房间id*/
    private String roomid;
    //直播间聊天室id
    private String liveUrl;
    //用户id
    private String userid;
    //职称图标
    private String postionImage;
    //职称
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPostionImage() {
        return postionImage;
    }

    public void setPostionImage(String postionImage) {
        this.postionImage = postionImage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIoc() {
        return ioc;
    }

    public void setIoc(String ioc) {
        this.ioc = ioc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomIoc() {
        return roomIoc;
    }

    public void setRoomIoc(String roomIoc) {
        this.roomIoc = roomIoc;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

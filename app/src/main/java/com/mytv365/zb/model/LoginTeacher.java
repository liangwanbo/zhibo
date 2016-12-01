package com.mytv365.zb.model;

/**
 * Created by Administrator on 2016/11/7 0007.
 *  yang
 */
public class LoginTeacher  {

    private  String playUrl;
    private  String position;
    private int id;
    private  String aptitude;


    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAptitude() {
        return aptitude;
    }

    public void setAptitude(String aptitude) {
        this.aptitude = aptitude;
    }

    @Override
    public String toString() {
        return "LoginTeacher{" +
                "playUrl='" + playUrl + '\'' +
                ", position='" + position + '\'' +
                ", id=" + id +
                ", aptitude='" + aptitude + '\'' +
                '}';
    }
}

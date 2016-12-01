package com.mytv365.zb.model;

/**
 * Created by Administrator on 2016/10/25 0025.
 * yang  关注
 */
public class Concern {

    private long id;
    private long createUser;
    private String nickName;
    private String follower;
    private String headImages;
    private String sign;
    private String followerStatus ;


    public Concern(){

    }

    public Concern(long id, long createUser, String nickName, String follower, String headImages, String sign, String followerStatus) {
        this.id = id;
        this.createUser = createUser;
        this.nickName = nickName;
        this.follower = follower;
        this.headImages = headImages;
        this.sign = sign;
        this.followerStatus = followerStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(long createUser) {
        this.createUser = createUser;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String position) {
        this.follower = position;
    }

    public String getHeadImages() {
        return headImages;
    }

    public void setHeadImages(String headImages) {
        this.headImages = headImages;
    }

    public String getSign(){
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFollowerStatus() {
        return followerStatus;
    }

    public void setFollowerStatus(String followerStatus) {
        this.followerStatus = followerStatus;
    }

    @Override
    public String toString() {
        return "Concern{" +
                "id=" + id +
                ", createUser=" + createUser +
                ", nickName='" + nickName + '\'' +
                ", position='" + follower + '\'' +
                ", headImages='" + headImages + '\'' +
                ", sign='" + sign + '\'' +
                ", followerStatus='" + followerStatus + '\'' +
                '}';
    }
}

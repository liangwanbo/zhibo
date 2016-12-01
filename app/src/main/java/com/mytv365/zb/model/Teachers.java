package com.mytv365.zb.model;

/**
 * Created by Administrator on 2016/10/26.
 *
 */
public class Teachers {


    /*老师图标*/
    private String roleIcon;
    /*老师关注状态*/
    private String followStatus;
    /*关注数量*/
    private String followerNumber;
    /*老师职位*/
    private String position;
    /*老师头像*/
    private String headImages;
    /*简介*/
    private String introduction;

    private String userId;

    private String teacherId;


    public String getRoleIcon() {
        return roleIcon;
    }

    public void setRoleIcon(String roleIcon) {
        this.roleIcon = roleIcon;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getFollowerNumber() {
        return followerNumber;
    }

    public void setFollowerNumber(String followerNumber) {
        this.followerNumber = followerNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHeadImages() {
        return headImages;
    }

    public void setHeadImages(String headImages) {
        this.headImages = headImages;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}

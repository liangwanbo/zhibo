package com.mytv365.zb.model;

/**
 * Created by Administrator on 2016/10/26 0026.
 * yang
 */
public class Teacher {

    private String nickName;
    private long id;
    private  int  fCount ;
    private int sCount;
    private String headImages;
    private String introduction;
    private int gCount;


    public Teacher() {
    }

    public Teacher(String nickName, long id, int fCount, int sCount, String headImages, String introduction, int gCount) {
        this.nickName = nickName;
        this.id = id;
        this.fCount = fCount;
        this.sCount = sCount;
        this.headImages = headImages;
        this.introduction = introduction;
        this.gCount = gCount;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getfCount() {
        return fCount;
    }

    public void setfCount(int fCount) {
        this.fCount = fCount;
    }

    public int getsCount() {
        return sCount;
    }

    public void setsCount(int sCount) {
        this.sCount = sCount;
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

    public int getgCount() {
        return gCount;
    }

    public void setgCount(int gCount) {
        this.gCount = gCount;
    }


    @Override
    public String toString() {
        return "Teacher{" +
                "nickName='" + nickName + '\'' +
                ", id=" + id +
                ", fCount=" + fCount +
                ", sCount=" + sCount +
                ", headImages='" + headImages + '\'' +
                ", introduction='" + introduction + '\'' +
                ", gCount=" + gCount +
                '}';
    }
}

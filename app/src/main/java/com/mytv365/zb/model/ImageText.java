package com.mytv365.zb.model;

/**
 * 图文
 * Created by zhangguohao on 16/9/12.
 */
public class ImageText {
    private int image;
    private String name;
    public ImageText(int image,String name){
        this.image=image;
        this.name=name;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

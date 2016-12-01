package com.mytv365.zb.model;

/**
 * Created by 阳志 on 2016/9/7 0007.
 * 精彩回复实体
 */
public class WonderfulVideo {

    private String coverImage;//视频封面图
    private String fileId;//文件ID
    private String fileName;
    private String id;
    private String valueCount;
    private  String Payurl;

    public WonderfulVideo(){

    }

    public WonderfulVideo(String coverImage, String fileId, String fileName, String id, String valueCount,String Payurl) {
        this.coverImage = coverImage;
        this.fileId = fileId;
        this.fileName = fileName;
        this.id = id;
        this.valueCount = valueCount;
        this.Payurl =Payurl;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueCount() {
        return valueCount;
    }

    public void setValueCount(String valueCount) {
        this.valueCount = valueCount;
    }

    public String getPayurl() {
        return Payurl;
    }

    public void setPayurl(String payurl) {
        Payurl = payurl;
    }

    @Override
    public String toString() {
        return "WonderfulVideo{" +
                "coverImage='" + coverImage + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", id='" + id + '\'' +
                ", valueCount='" + valueCount + '\'' +
                '}';
    }


}

package com.mytv365.zb.model;


/**
 * 当前直播信息页面
 *
 */
public class CurLiveInfo {
    private static int members;
    private static int admires;
    private static String title;
    private static double lat1;
    private static double long1;
    private static String address = "";
    private static String coverurl = "";

    public static int roomNum;
    private static int userid;

    private static String LiveUrlroom;
    private static String downloadeurl;

    public static String getDownloadeurl() {
        return downloadeurl;
    }

    public static void setDownloadeurl(String downloadeurl) {
        CurLiveInfo.downloadeurl = downloadeurl;
    }

    public static String getLiveUrlroom() {
        return LiveUrlroom;
    }

    public static void setLiveUrlroom(String liveUrlroom) {
        LiveUrlroom = liveUrlroom;
    }

    public static int getUserid() {
        return userid;
    }

    public static void setUserid(int userid) {
        CurLiveInfo.userid = userid;
    }

    public static String hostID;
    public static String hostName;
    public static String hostAvator;

    public static String ImageUrl;

    public static String titles;

    public static String texts;

    public static String getImageUrl() {
        return ImageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public static String getTitles() {
        return titles;
    }

    public static void setTitles(String titles) {
        CurLiveInfo.titles = titles;
    }

    public static String getTexts() {
        return texts;
    }

    public static void setTexts(String texts) {
        CurLiveInfo.texts = texts;
    }

    public static int currentRequestCount = 0;

    public static int getCurrentRequestCount() {
        return currentRequestCount;
    }

    public static int getIndexView() {
        return indexView;
    }

    public static void setIndexView(int indexView) {
        CurLiveInfo.indexView = indexView;
    }

    public static int indexView = 0;

    public static void setCurrentRequestCount(int currentRequestCount) {
        CurLiveInfo.currentRequestCount = currentRequestCount;
    }

    public static String getHostID() {
        return hostID;
    }

    public static void setHostID(String hostID) {
        CurLiveInfo.hostID = hostID;
    }

    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String hostName) {
        CurLiveInfo.hostName = hostName;
    }

    public static String getHostAvator() {
        return hostAvator;
    }

    public static void setHostAvator(String hostAvator) {
        CurLiveInfo.hostAvator = hostAvator;
    }

    public static int getMembers() {
        return members;
    }

    public static void setMembers(int members) {
        CurLiveInfo.members = members;
    }

    public static int getAdmires() {
        return admires;
    }

    public static void setAdmires(int admires) {
        CurLiveInfo.admires = admires;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        CurLiveInfo.title = title;
    }

    public static double getLat1() {
        return lat1;
    }

    public static void setLat1(double lat1) {
        CurLiveInfo.lat1 = lat1;
    }

    public static double getLong1() {
        return long1;
    }

    public static void setLong1(double long1) {
        CurLiveInfo.long1 = long1;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        CurLiveInfo.address = address;
    }

    public static int getRoomNum() {
        return roomNum;
    }

    public static void setRoomNum(int roomNum) {
        CurLiveInfo.roomNum = roomNum;
    }

    public static String getCoverurl() {
        return coverurl;
    }

    public static void setCoverurl(String coverurl) {
        CurLiveInfo.coverurl = coverurl;
    }

    public static String getChatRoomId() {
        return "" + roomNum;
    }
}

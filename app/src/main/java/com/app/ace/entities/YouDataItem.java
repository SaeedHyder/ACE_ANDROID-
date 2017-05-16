package com.app.ace.entities;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class YouDataItem {
    private String userImage;
    private String userName;
    private String userDetail;
    private String youImage;



    public YouDataItem(String userImage, String userName, String userDetail, String youImage){
        setUserImage(userImage);
        setUserName(userName);
        setUserMessage(userDetail);
        setYouImage(youImage);

    }
    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMessage() {
        return userDetail;
    }

    public void setUserMessage(String userMessage) {
        this.userDetail = userMessage;
    }

    public String getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(String userDetail) {
        this.userDetail = userDetail;
    }

    public String getYouImage() {
        return youImage;
    }

    public void setYouImage(String youImage) {
        this.youImage = youImage;
    }
}

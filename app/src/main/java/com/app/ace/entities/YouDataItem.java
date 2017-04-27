package com.app.ace.entities;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class YouDataItem {
    private String userImage;
    private String userName;
    private String userDetail;
    private Integer youImage;



    public YouDataItem(String userImage, String userName, String userDetail, Integer youImage){
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

    public Integer getYouImage() {
        return youImage;
    }

    public void setYouImage(Integer youImage) {
        this.youImage = youImage;
    }


}

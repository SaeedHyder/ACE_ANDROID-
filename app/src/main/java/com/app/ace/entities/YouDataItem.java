package com.app.ace.entities;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class YouDataItem {
    private String userImage;
    private String userName;
    private String userDetail;
    private String youImage;
    private String time;
    private String SenderId;
    private String Isfollowing;
    private String post_thumb_image;




    public YouDataItem(String userImage, String userName, String userDetail, String youImage, String time, int sender_id, String is_following, String post_thumb_image){
        setUserImage(userImage);
        setUserName(userName);
        setUserMessage(userDetail);
        setYouImage(youImage);
        setTime(time);
        setSenderId(String.valueOf(sender_id));
        setIsfollowing(is_following);
        this.post_thumb_image=post_thumb_image;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getIsfollowing() {
        return Isfollowing;
    }

    public void setIsfollowing(String isfollowing) {
        Isfollowing = isfollowing;
    }

    public String getPost_thumb_image() {
        return post_thumb_image;
    }

    public void setPost_thumb_image(String post_thumb_image) {
        this.post_thumb_image = post_thumb_image;
    }
}

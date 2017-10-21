package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class FollowDataItem {
    private String userImage;
    private String userName;
    private String userDetail;
    private String created_at;
    private int senderId;
    private String post_thumb_image;
    //private Integer imgChildGrid;

    private ArrayList<String> imgChildGrid;

    public FollowDataItem(String userImage, String userName, String userDetail, ArrayList<String> imgChildGrid, String created_at, int sender_id, String post_thumb_image){
        setUserImage(userImage);
        setUserName(userName);
        setUserMessage(userDetail);
        setImgChildGrid(imgChildGrid);
        setCreated_at(created_at);
        setSenderId(sender_id);
        setPost_thumb_image(post_thumb_image);

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

    public ArrayList<String> getImgChildGrid() {
        return imgChildGrid;
    }

    public void setImgChildGrid(ArrayList<String> imgChildGrid) {
        this.imgChildGrid = imgChildGrid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getPost_thumb_image() {
        return post_thumb_image;
    }

    public void setPost_thumb_image(String post_thumb_image) {
        this.post_thumb_image = post_thumb_image;
    }

    /*  public Integer getImgChildGrid() {
        return imgChildGrid;
    }

    public void setImgChildGrid(Integer imgChildGrid) {
        this.imgChildGrid = imgChildGrid;*/
    // }


}

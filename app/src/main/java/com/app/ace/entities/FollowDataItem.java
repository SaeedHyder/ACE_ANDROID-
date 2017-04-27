package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by muniyemiftikhar on 4/4/2017.
 */

public class FollowDataItem {
    private String userImage;
    private String userName;
    private String userDetail;

    //private Integer imgChildGrid;

    private ArrayList<Integer> imgChildGrid;

    public FollowDataItem(String userImage, String userName, String userDetail , ArrayList<Integer> imgChildGrid){
        setUserImage(userImage);
        setUserName(userName);
        setUserMessage(userDetail);
        setImgChildGrid(imgChildGrid);
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

    public ArrayList<Integer> getImgChildGrid() {
        return imgChildGrid;
    }

    public void setImgChildGrid(ArrayList<Integer> imgChildGrid) {
        this.imgChildGrid = imgChildGrid;
    }

/*  public Integer getImgChildGrid() {
        return imgChildGrid;
    }

    public void setImgChildGrid(Integer imgChildGrid) {
        this.imgChildGrid = imgChildGrid;*/
    // }


}

package com.app.ace.entities;

/**
 * Created by muniyemiftikhar on 5/2/2017.
 */

public class FollowingCountDataItem {

    private String userImage;
    private String userName;

    int id;

    public FollowingCountDataItem(String userImage, String userName,int id) {
        this.userImage = userImage;
        this.userName = userName;
        setId(id);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

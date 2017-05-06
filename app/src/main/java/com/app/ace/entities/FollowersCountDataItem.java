package com.app.ace.entities;

/**
 * Created by muniyemiftikhar on 5/2/2017.
 */

public class FollowersCountDataItem {
    private String userImage;
    private String userName;
    int id;

    public FollowersCountDataItem(String userImage, String userName,int id) {
        setUserImage(userImage);
        setUserName(userName);
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

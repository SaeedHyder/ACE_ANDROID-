package com.app.ace.entities;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class NewMessageDataItem {

    private String Image;
    private String userName;

    public NewMessageDataItem(String image, String userName) {
        setImage(image);
        setUserName(userName);
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

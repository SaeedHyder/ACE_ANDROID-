package com.app.ace.entities;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class MapScreenItem {

    private String lat;
    private String lng;
    private String image;
    private int userId;

    public MapScreenItem(String lat, String lng, String image,int userId)
    {
        setLat(lat);
        setLng(lng);
        setImage(image);
        this.userId=userId;

    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

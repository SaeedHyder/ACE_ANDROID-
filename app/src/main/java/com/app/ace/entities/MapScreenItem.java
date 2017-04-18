package com.app.ace.entities;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class MapScreenItem {

    private String lat;
    private String lng;
    private String image;

    public MapScreenItem(String lat, String lng, String image)
    {
        setLat(lat);
        setLng(lng);
        setImage(image);

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
}

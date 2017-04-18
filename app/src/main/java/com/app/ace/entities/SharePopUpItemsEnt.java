package com.app.ace.entities;

/**
 * Created by ahmedsyed on 4/6/2017.
 */

public class SharePopUpItemsEnt {
    public String images;
    public String tv_name;

    public SharePopUpItemsEnt(String images, String tv_name) {
        this.images = images;
        this.tv_name = tv_name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }
}

package com.app.ace.entities;

/**
 * Created by khan_muhammad on 3/24/2017.
 */

public class CreatePostEnt {

    String caption;
    String user_id;
    String post_image;


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }
}

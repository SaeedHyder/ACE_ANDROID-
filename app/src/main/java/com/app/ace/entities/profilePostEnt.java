package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 5/31/2017.
 */

public class profilePostEnt {

    String post_thumb_image;
    String post_image;

    public profilePostEnt(String post_image,String post_thumb_image)
    {
        setPost_image(post_image);
        setPost_thumb_image(post_thumb_image);
    }



        public String getPost_thumb_image() {
        return post_thumb_image;
    }

    public void setPost_thumb_image(String post_thumb_image) {
        this.post_thumb_image = post_thumb_image;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }
}

package com.app.ace.entities;

/**
 * Created by ahmedsyed on 4/6/2017.
 */

public class CommentsSectionItemsEnt {
    public String imageCommentor;
    public String nameCommentor;
    public String comment;
    public String time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public CommentsSectionItemsEnt(String imageCommentor, String nameCommentor, String comment, String time, String user_id) {
        this.imageCommentor = imageCommentor;
        this.nameCommentor = nameCommentor;
        this.comment = comment;
        this.time = time;
        this.user_id = user_id;
    }

    private String user_id;


    public String getImageCommentor() {
        return imageCommentor;
    }

    public void setImageCommentor(String imageCommentor) {
        this.imageCommentor = imageCommentor;
    }

    public String getNameCommentor() {
        return nameCommentor;
    }

    public void setNameCommentor(String nameCommentor) {
        this.nameCommentor = nameCommentor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

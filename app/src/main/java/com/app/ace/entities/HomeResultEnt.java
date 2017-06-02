package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by khan_muhammad on 3/22/2017.
 */

public class HomeResultEnt {


    ArrayList<PostsEnt> Posts;

    int is_approved_user;
    int total_records;

    public ArrayList<PostsEnt> getPosts() {
        return Posts;
    }

    public void setPosts(ArrayList<PostsEnt> posts) {
        this.Posts = posts;
    }

    public int getIs_approved_user() {
        return is_approved_user;
    }

    public void setIs_approved_user(int is_approved_user) {
        this.is_approved_user = is_approved_user;
    }

    public int getTotal_records() {
        return total_records;
    }

    public void setTotal_records(int total_records) {
        this.total_records = total_records;
    }
}

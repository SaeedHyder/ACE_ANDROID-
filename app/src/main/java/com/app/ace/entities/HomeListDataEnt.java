package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class HomeListDataEnt {

    int totoal_likes, total_comments;

    String profile_pic_path, profile_name, profile_post_pic_path, friend_name,friend_comment;

    public HomeListDataEnt(int totoal_likes, int total_comments, String profile_pic_path, String profile_name, String profile_post_pic_path, String friend_name, String friend_comment){

        setTotoal_likes(totoal_likes);
        setTotal_comments(total_comments);
        setProfile_pic_path(profile_pic_path);
        setProfile_name(profile_name);
        setProfile_post_pic_path(profile_post_pic_path);
        setFriend_name(friend_name);
        setFriend_comment(friend_comment);

    }

    public int getTotoal_likes() {
        return totoal_likes;
    }

    public void setTotoal_likes(int totoal_likes) {
        this.totoal_likes = totoal_likes;
    }

    public int getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(int total_comments) {
        this.total_comments = total_comments;
    }

    public String getProfile_pic_path() {
        return profile_pic_path;
    }

    public void setProfile_pic_path(String profile_pic_path) {
        this.profile_pic_path = profile_pic_path;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getProfile_post_pic_path() {
        return profile_post_pic_path;
    }

    public void setProfile_post_pic_path(String profile_post_pic_path) {
        this.profile_post_pic_path = profile_post_pic_path;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_comment() {
        return friend_comment;
    }

    public void setFriend_comment(String friend_comment) {
        this.friend_comment = friend_comment;
    }
}

package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by khan_muhammad on 3/18/2017.
 */

public class HomeListDataEnt {

    int totoal_likes, total_comments;

    String profile_pic_path, profile_name, profile_post_pic_path, friend_name, friend_comment;

    int user_id;
    int id;
    String is_liked;
    String videoThumbnail;
    ArrayList<PostComment> commentsArray;
    int is_approved_user;
    int follower_post;
    public HomeListDataEnt(int totoal_likes, int total_comments, String profile_pic_path, String profile_name, String profile_post_pic_path, String friend_name, String friend_comment, int user_id, int id, ArrayList<PostComment> commentsArray, String is_liked, String videoThumb, int is_approved_user,int FollowerPost) {

        setTotoal_likes(totoal_likes);
        setTotal_comments(total_comments);
        setProfile_pic_path(profile_pic_path);
        setProfile_name(profile_name);
        setProfile_post_pic_path(profile_post_pic_path);
        setFriend_name(friend_name);
        setFriend_comment(friend_comment);
        setUser_id(user_id);
        setId(id);
        setCommentsArray(commentsArray);
        setIs_liked(is_liked);
        setVideoThumbnail(videoThumb);
        setIs_approved_user(is_approved_user);
        setFollower_post(FollowerPost);


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollower_post() {
        return follower_post;
    }

    public void setFollower_post(int follower_post) {
        this.follower_post = follower_post;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public int getTotoal_likes() {
        return totoal_likes;
    }

    public void setTotoal_likes(int totoal_likes) {
        this.totoal_likes = totoal_likes;
    }

    public int getIs_approved_user() {
        return is_approved_user;
    }

    public void setIs_approved_user(int is_approved_user) {
        this.is_approved_user = is_approved_user;
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

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ArrayList<PostComment> getCommentsArray() {
        return commentsArray;
    }

    public void setCommentsArray(ArrayList<PostComment> commentsArray) {
        this.commentsArray = commentsArray;
    }


}

package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by khan_muhammad on 3/22/2017.
 */

public class PostsEnt {

    int user_id;
    int totoal_likes;
    int total_comments;
    String post_image = "";
    String friend_name;
    String friend_comment;
    ;
    CreaterEnt creator;

    //Adding Fileds for User Posts
    String image;
    String caption;
    String status;
    String created_at;
    String updated_at;

    int id;
    String like_count;
    String comment_count;
    String is_liked;
    String post_thumb_image;
    int follower_post;
    ArrayList<PostComment> comment;

    public int getFollower_post() {
        return follower_post;
    }

    public void setFollower_post(int follower_post) {
        this.follower_post = follower_post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPost_image() {
        return post_image;
    }

    public void setPost_image(String post_image) {
        this.post_image = post_image;
    }

    public CreaterEnt getCreator() {
        return creator;
    }

    public void setCreator(CreaterEnt creator) {
        this.creator = creator;
    }

    public ArrayList<PostComment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<PostComment> comment) {
        this.comment = comment;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getPost_thumb_image() {
        return post_thumb_image;
    }

    public void setPost_thumb_image(String post_thumb_image) {
        this.post_thumb_image = post_thumb_image;
    }


    /* "id": 3,

            "user_id": 150,
            "image": "pSZmNgGaHuVu.jpg",
            "caption": "swagger",
            "status": "1",
            "created_at": "2017-03-21 18:23:15",
            "updated_at": "2017-03-21 18:23:15",
            "post_image": "http://10.1.18.234/ace/public/images/posts/pSZmNgGaHuVu.jpg",
            */


}

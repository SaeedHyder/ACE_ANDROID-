package com.app.ace.entities;

/**
 * Created by khan_muhammad on 3/22/2017.
 */

public class PostsEnt {

    int user_id;
    int totoal_likes, total_comments;
    String post_image,friend_name,friend_comment;;
    CreaterEnt creator;

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

package com.app.ace.entities;

/**
 * Created by saeedhyder on 4/25/2017.
 */

public class FollowUser {


    int id;
    int status;
    String following_id;
    String follower_id;
    String updated_at;
    String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollowing_id() {
        return following_id;
    }

    public void setFollowing_id(String following_id) {
        this.following_id = following_id;
    }

    public String getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(String follower_id) {
        this.follower_id = follower_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

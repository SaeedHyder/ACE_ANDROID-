package com.app.ace.interfaces;

/**
 * Created by saeedhyder on 5/16/2017.
 */

public interface FollowService {

    void followUser(String senderId, int position, int i);

    void UnFollowUser(String senderId, int position, int i);
}

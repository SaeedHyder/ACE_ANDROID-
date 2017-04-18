package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by khan_muhammad on 3/22/2017.
 */

public class HomeResultEnt {


    ArrayList<PostsEnt> Posts;

    public ArrayList<PostsEnt> getPosts() {
        return Posts;
    }

    public void setPosts(ArrayList<PostsEnt> posts) {
        this.Posts = posts;
    }
}

package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by khan_muhammad on 3/17/2017.
 */

public class ProfileEnt {

    int no_of_posts,not_of_followers,no_of_followings;

    String user_name,education_and_certification,pref_training_loc,avaliability,tagline;

    boolean is_trainer;

    ArrayList<String> pics;

    public ProfileEnt(int no_of_posts,int not_of_followers,int no_of_followings,String user_name,String education_and_certification,String pref_training_loc,String avaliability,String tagline,boolean is_trainer, ArrayList<String> pics){

        setNo_of_posts(no_of_posts);
        setNot_of_followers(not_of_followers);
        setNo_of_followings(no_of_followings);
        setUser_name(user_name);
        setEducation_and_certification(education_and_certification);
        setPref_training_loc(pref_training_loc);
        setAvaliability(avaliability);
        setTagline(tagline);
        setIs_trainer(is_trainer);
        setPics(pics);
    }

    public int getNo_of_posts() {
        return no_of_posts;
    }

    public void setNo_of_posts(int no_of_posts) {
        this.no_of_posts = no_of_posts;
    }

    public int getNot_of_followers() {
        return not_of_followers;
    }

    public void setNot_of_followers(int not_of_followers) {
        this.not_of_followers = not_of_followers;
    }

    public int getNo_of_followings() {
        return no_of_followings;
    }

    public void setNo_of_followings(int no_of_followings) {
        this.no_of_followings = no_of_followings;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEducation_and_certification() {
        return education_and_certification;
    }

    public void setEducation_and_certification(String education_and_certification) {
        this.education_and_certification = education_and_certification;
    }

    public String getPref_training_loc() {
        return pref_training_loc;
    }

    public void setPref_training_loc(String pref_training_loc) {
        this.pref_training_loc = pref_training_loc;
    }

    public String getAvaliability() {
        return avaliability;
    }

    public void setAvaliability(String avaliability) {
        this.avaliability = avaliability;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public boolean is_trainer() {
        return is_trainer;
    }

    public void setIs_trainer(boolean is_trainer) {
        this.is_trainer = is_trainer;
    }

    public ArrayList<String> getPics() {
        return pics;
    }

    public void setPics(ArrayList<String> pics) {
        this.pics = pics;
    }

}

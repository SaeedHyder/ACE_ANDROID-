package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 4/25/2017.
 */

public class UserProfile {

    int id;
    int role_id;
    String userType;
    String user_name;
    String first_name;
    String last_name;
    String full_name;
    String email;
    String status;
    String dob;
    String gender;
    String address;
    String city;
    String state;
    String country;
    String phone_number;
    String company_name;
    String profile_picture;
    String education;
    String university;
    String gym_name;
    String gym_latitude;
    String gym_longitude;
    String speciality;
    String experience;
    String primary_reason;
    String gym_days;
    String gym_timing_from;
    String gym_timing_to;
    String resume;
    String user_type;
    String social_media_id;
    String social_media_platform;
    String notification_status;
    String device_type;
    String device_token;
    String profile_image;
    String verification_code;
    String is_verified;
    String created_at;
    String updated_at;
    String deleted_at;
    String last_login;
    String user_status;
    String followers_count;
    String following_count;
    String posts_count;
    int is_following;




    ArrayList<post> posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getGym_name() {
        return gym_name;
    }

    public void setGym_name(String gym_name) {
        this.gym_name = gym_name;
    }

    public String getGym_latitude() {
        return gym_latitude;
    }

    public void setGym_latitude(String gym_latitude) {
        this.gym_latitude = gym_latitude;
    }

    public String getGym_longitude() {
        return gym_longitude;
    }

    public void setGym_longitude(String gym_longitude) {
        this.gym_longitude = gym_longitude;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPrimary_reason() {
        return primary_reason;
    }

    public void setPrimary_reason(String primary_reason) {
        this.primary_reason = primary_reason;
    }

    public String getGym_days() {
        return gym_days;
    }

    public void setGym_days(String gym_days) {
        this.gym_days = gym_days;
    }

    public String getGym_timing_from() {
        return gym_timing_from;
    }

    public void setGym_timing_from(String gym_timing_from) {
        this.gym_timing_from = gym_timing_from;
    }

    public String getGym_timing_to() {
        return gym_timing_to;
    }

    public void setGym_timing_to(String gym_timing_to) {
        this.gym_timing_to = gym_timing_to;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getSocial_media_id() {
        return social_media_id;
    }

    public void setSocial_media_id(String social_media_id) {
        this.social_media_id = social_media_id;
    }

    public String getSocial_media_platform() {
        return social_media_platform;
    }

    public void setSocial_media_platform(String social_media_platform) {
        this.social_media_platform = social_media_platform;
    }

    public String getNotification_status() {
        return notification_status;
    }

    public void setNotification_status(String notification_status) {
        this.notification_status = notification_status;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
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

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }

    public String getPosts_count() {
        return posts_count;
    }

    public void setPosts_count(String posts_count) {
        this.posts_count = posts_count;
    }

    public int getIs_following() {
        return is_following;
    }

    public void setIs_following(int is_following) {
        this.is_following = is_following;
    }

    public ArrayList<post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<post> posts) {
        this.posts = posts;
    }


}

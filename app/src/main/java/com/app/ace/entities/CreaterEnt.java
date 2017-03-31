package com.app.ace.entities;

/**
 * Created by khan_muhammad on 3/22/2017.
 */

public class CreaterEnt {

    int status;

    String user_name;
    String first_name;
    String last_name;
    String full_name;
    String email;
    String dob;
    String gender;
    String address;
    String city;
    String state;
    String country;
    String phone_number;
    String company_name;
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

    String social_media_id;
    String social_media_platform;
    String notification_status;
    String device_type;
    String device_token;
    String last_login;
    String profile_image;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    /* "creator": {
        "id": 150,
                "role_id": 2,
                "userType": "user",
                "rights": "category,product,user,cms,noti",
                "user_name": null,
                "first_name": "first",
                "last_name": "name",
                "full_name": "",
                "email": "email1@email.com",
                "status": 1,
                "dob": null,
                "gender": null,
                "address": "",
                "city": "",
                "state": "",
                "country": null,
                "phone_number": "123456",
                "company_name": "",
                "profile_picture": "KREZKy5kjQ6F.jpg",
                "education": "NASM,ACSM",
                "university": "yale university",
                "gym_name": "ace",
                "gym_latitude": 22.35,
                "gym_longitude": 15.2,
                "speciality": "aerobic,body",
                "experience": "1 to 5 years",
                "primary_reason": "i love it",
                "gym_days": "sun,mon,tue,wed",
                "gym_timing_from": "10 A.M.",
                "gym_timing_to": "10 P.M.",
                "resume": "",
                "user_type": "trainer",
                "social_media_id": "",
                "social_media_platform": "",
                "notification_status": 1,
                "device_type": "ios",
                "device_token": "321sda354sd3a",
                "verification_code": "1234",
                "is_verified": 1,
                "created_at": "2017-03-15 18:18:00",
                "updated_at": "2017-03-15 18:18:00",
                "deleted_at": null,
                "last_login": "2017-03-15 23:18:00",
                "profile_image": "http://10.1.18.234/ace/public/images/users/KREZKy5kjQ6F.jpg"*/

}

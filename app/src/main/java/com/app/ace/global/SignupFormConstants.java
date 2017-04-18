package com.app.ace.global;


import java.io.File;

public class SignupFormConstants {

    public String fName;
    public String lName;
    public String email;
    public String password;
    public File profilePic;
    public String mobileNumber;

    public String social_user_id;
    public String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSocial_user_id() {
        return social_user_id;
    }

    public void setSocial_user_id(String social_user_id) {
        this.social_user_id = social_user_id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(File profilePic) {
        this.profilePic = profilePic;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}

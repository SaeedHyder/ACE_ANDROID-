package com.app.ace.entities;


public class TwitterUser {

    private String userPic;
    private String userId;

    public TwitterUser() {
    }

    public TwitterUser(String userPic, String userId, String userName, String userEmail, String token, String secret) {
        this.userPic = userPic;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.token = token;
        this.secret = secret;
    }

    private String userName;
    private String userEmail;


    private String token;
    private String secret;

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}

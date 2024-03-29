package com.app.ace.entities;

public class InboxDataItem {

    private String userImage;
    private String userName;
    private String userMessage;




    private int ConversationId;

    public InboxDataItem(String userImage, String userName, String userMessage, int ConversationId){
        setUserImage(userImage);
        setUserName(userName);
        setUserMessage(userMessage);
        setConversationId(ConversationId);
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public int getConversationId() {
        return ConversationId;
    }

    public void setConversationId(int conversationId) {
        ConversationId = conversationId;
    }



}

package com.app.ace.entities;

public class ChatDataItem {

    private String senderImage;
    private String senderMessage;
    private String senderMessageTime;

    private String receiverImage;
    private String receiverMessage;
    private String receiverMessageTime;

    private boolean isSender;

    public ChatDataItem(String senderImage, String senderMessage, String senderMessageTime,String receiverImage, String receiverMessage, String receiverMessageTime, boolean isSender){
        setSenderImage(senderImage);
        setSenderMessage(senderMessage);
        setSenderMessageTime(senderMessageTime);
        setReceiverImage(receiverImage);
        setReceiverMessage(receiverMessage);
        setReceiverMessageTime(receiverMessageTime);
        setSender(isSender);
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(String senderMessage) {
        this.senderMessage = senderMessage;
    }

    public String getSenderMessageTime() {
        return senderMessageTime;
    }

    public void setSenderMessageTime(String senderMessageTime) {
        this.senderMessageTime = senderMessageTime;
    }

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public void setReceiverMessage(String receiverMessage) {
        this.receiverMessage = receiverMessage;
    }

    public String getReceiverMessageTime() {
        return receiverMessageTime;
    }

    public void setReceiverMessageTime(String receiverMessageTime) {
        this.receiverMessageTime = receiverMessageTime;
    }

    public boolean isSender() {
        return isSender;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }
}

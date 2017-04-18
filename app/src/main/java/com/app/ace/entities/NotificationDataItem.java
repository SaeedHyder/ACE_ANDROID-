package com.app.ace.entities;

public class NotificationDataItem {

    private String notificationText;
    private String notificationDate;

    public NotificationDataItem(String notificationText, String notificationDate){
        setNotificationText(notificationText);
        setNotificationDate(notificationDate);
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }
}

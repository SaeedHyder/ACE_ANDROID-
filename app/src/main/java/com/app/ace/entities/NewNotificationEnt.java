package com.app.ace.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 10/17/2017.
 */

public class NewNotificationEnt {

    @SerializedName("Notification")
    @Expose
    private ArrayList<Notification> notification = null;
    @SerializedName("total_records")
    @Expose
    private Integer totalRecords;

    public ArrayList<Notification> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<Notification> notification) {
        this.notification = notification;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
}

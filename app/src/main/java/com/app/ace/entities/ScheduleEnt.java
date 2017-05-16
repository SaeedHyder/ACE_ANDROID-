package com.app.ace.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created on 5/15/2017.
 */

public class ScheduleEnt {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time_start_1")
    @Expose
    private String timeStart1;
    @SerializedName("time_end_1")
    @Expose
    private String timeEnd1;
    @SerializedName("time_start_2")
    @Expose
    private String timeStart2;
    @SerializedName("time_end_2")
    @Expose
    private String timeEnd2;
    @SerializedName("time_start_3")
    @Expose
    private String timeStart3;
    @SerializedName("time_end_3")
    @Expose
    private String timeEnd3;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("trainer")
    @Expose
    private UserProfile trainer;
    @SerializedName("bookings")
    @Expose
    private ArrayList<Booking> bookings = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStart1() {
        return timeStart1;
    }

    public void setTimeStart1(String timeStart1) {
        this.timeStart1 = timeStart1;
    }

    public String getTimeEnd1() {
        return timeEnd1;
    }

    public void setTimeEnd1(String timeEnd1) {
        this.timeEnd1 = timeEnd1;
    }

    public String getTimeStart2() {
        return timeStart2;
    }

    public void setTimeStart2(String timeStart2) {
        this.timeStart2 = timeStart2;
    }

    public String getTimeEnd2() {
        return timeEnd2;
    }

    public void setTimeEnd2(String timeEnd2) {
        this.timeEnd2 = timeEnd2;
    }

    public String getTimeStart3() {
        return timeStart3;
    }

    public void setTimeStart3(String timeStart3) {
        this.timeStart3 = timeStart3;
    }

    public String getTimeEnd3() {
        return timeEnd3;
    }

    public void setTimeEnd3(String timeEnd3) {
        this.timeEnd3 = timeEnd3;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserProfile getTrainer() {
        return trainer;
    }

    public void setTrainer(UserProfile trainer) {
        this.trainer = trainer;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

}

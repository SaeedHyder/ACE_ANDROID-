package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 4/29/2017.
 */

public class TrainerBookingCalendarJson {

    int trainer_id;

    String month;
    String date;

    ArrayList<ScheduleTime> time;

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ScheduleTime> getTime() {
        return time;
    }

    public void setTime(ArrayList<ScheduleTime> time) {
        this.time = time;
    }




}

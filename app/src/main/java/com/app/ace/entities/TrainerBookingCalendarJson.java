package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 4/29/2017.
 */

public class TrainerBookingCalendarJson {

    int trainer_id;

    String month;
    String start_date;
    String end_date;

    String time_start_1;
    String time_end_1;
    String time_start_2;
    String time_end_2;

    String time_start_3;
    String time_end_3;


    ArrayList<ScheduleTime> time;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTime_start_1() {
        return time_start_1;
    }

    public void setTime_start_1(String time_start_1) {
        this.time_start_1 = time_start_1;
    }

    public String getTime_end_1() {
        return time_end_1;
    }

    public void setTime_end_1(String time_end_1) {
        this.time_end_1 = time_end_1;
    }

    public String getTime_start_2() {
        return time_start_2;
    }

    public void setTime_start_2(String time_start_2) {
        this.time_start_2 = time_start_2;
    }

    public String getTime_end_2() {
        return time_end_2;
    }

    public void setTime_end_2(String time_end_2) {
        this.time_end_2 = time_end_2;
    }

    public String getTime_start_3() {
        return time_start_3;
    }

    public void setTime_start_3(String time_start_3) {
        this.time_start_3 = time_start_3;
    }

    public String getTime_end_3() {
        return time_end_3;
    }

    public void setTime_end_3(String time_end_3) {
        this.time_end_3 = time_end_3;
    }


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


    public ArrayList<ScheduleTime> getTime() {
        return time;
    }

    public void setTime(ArrayList<ScheduleTime> time) {
        this.time = time;
    }


}

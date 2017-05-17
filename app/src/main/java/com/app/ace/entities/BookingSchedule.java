package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created on 5/17/2017.
 */

public class BookingSchedule {
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTraining_type() {
        return training_type;
    }

    public void setTraining_type(String training_type) {
        this.training_type = training_type;
    }

    public ArrayList<CalenderEnt> getAll_ids() {
        return all_ids;
    }

    public void setAll_ids(ArrayList<CalenderEnt> all_ids) {
        this.all_ids = all_ids;
    }

    Integer user_id;
    String training_type;
    ArrayList<CalenderEnt> all_ids = null;
}
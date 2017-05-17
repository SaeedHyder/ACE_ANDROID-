package com.app.ace.entities;

/**
 * Created on 5/17/2017.
 */

public class CalenderEnt {
    public Integer getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(Integer calendar_id) {
        this.calendar_id = calendar_id;
    }

    public CalenderEnt(Integer calendar_id) {
        this.calendar_id = calendar_id;
    }

    Integer calendar_id;
}

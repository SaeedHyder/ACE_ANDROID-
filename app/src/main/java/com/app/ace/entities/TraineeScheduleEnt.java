package com.app.ace.entities;

import java.util.ArrayList;

/**
 * Created on 5/18/2017.
 */

public class TraineeScheduleEnt {
    UserProfile user;
    ArrayList<Slot> slots;



    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }
}

package com.app.ace.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created on 5/16/2017.
 */

public class TrainerBooking {

    @SerializedName("trainer")
    @Expose
    private UserProfile trainer = null;
    @SerializedName("slots")
    @Expose
    private ArrayList<Slot> slots = null;
    @SerializedName("available_slots")
    @Expose
    ArrayList<AvailableSlot> available_slots;
    public ArrayList<AvailableSlot> getAvailable_slots() {
        return available_slots;
    }

    public void setAvailable_slots(ArrayList<AvailableSlot> available_slots) {
        this.available_slots = available_slots;
    }


    public UserProfile getTrainer() {
        return trainer;
    }

    public void setTrainer(UserProfile trainer) {
        this.trainer = trainer;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }
}

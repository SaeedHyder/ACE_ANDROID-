package com.app.ace.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by saeedhyder on 11/15/2017.
 */

public class SpecialityResultEnt {

    @SerializedName("Specialities")
    @Expose
    private List<SpecialityEnt> specialities = null;

    public List<SpecialityEnt> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<SpecialityEnt> specialities) {
        this.specialities = specialities;
    }


}

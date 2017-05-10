package com.app.ace.interfaces;

import com.app.ace.entities.SpinnerDataItem;

import java.util.ArrayList;

/**
 * Created by saeedhyder on 5/6/2017.
 */

public interface EditTrainerData {

    void updateEducationData(ArrayList<SpinnerDataItem> listState);
    void updateSpecialtyData(ArrayList<SpinnerDataItem> listState);

}

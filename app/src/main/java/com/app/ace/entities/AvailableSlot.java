package com.app.ace.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 5/29/2017.
 */

public class AvailableSlot {
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;

    public String getStartTime() {

      /*  String[] array=startTime.split(":");
        String sTime=array[0]+":"+array[1];

        return sTime;*/
      return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {

     /*   String[] array=endTime.split(":");
        String eTime=array[0]+":"+array[1];

        return eTime;*/
     return endTime;

    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}

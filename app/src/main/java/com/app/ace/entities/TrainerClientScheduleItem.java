package com.app.ace.entities;

/**
 * Created by saeedhyder on 4/6/2017.
 */

public class TrainerClientScheduleItem {

    private String txtTime;
    private String btnName;

   /* private String showdate;*/

    public TrainerClientScheduleItem(String txtTime, String btnName /*, String showdate*/)
    {
        settxtTime(txtTime);
        setbtnName(btnName);
     /*   setshowdate(showdate);*/

    }

    public String gettxtTime() {
        return txtTime;
    }

    public void settxtTime(String txtTime) {
        this.txtTime = txtTime;
    }

    public String gebtnName() {
        return btnName;
    }

    public void setbtnName(String btnName) {
        this.btnName = btnName;
    }

    /*public String getshowdate() {
        return showdate;
    }

    public void setshowdate(String showdate) {
        this.showdate = showdate;
    }*/


}
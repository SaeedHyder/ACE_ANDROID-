package com.app.ace.entities;

/**
 * Created by muniyemiftikhar on 4/7/2017.
 */

public class TrainingBookingCalenderItem {
    private String txtDayDate;
    private  String txtTo;
    private String txtFrom;
    private String txtIncre;

    private String txtback;
    private String txtSecTo;
    private String txtSecFrom;

/*    public TrainingBookingCalenderItem( String txtTo, String txtFrom){
        setTxtTo(txtTo);
        setTxtFrom(txtFrom);
        setTxtback(txtback);
    }*/

    public TrainingBookingCalenderItem(String txtDayDate, String txtSecTo, String txtTo, String txtFrom, String txtSecFrom)
    {
        setTxtDayDate(txtDayDate);
        setTxtTo(txtTo);
        setTxtFrom(txtFrom);
        setTxtIncre(txtIncre);
        setTxtSecTo(txtSecTo);
        setTxtSecFrom(txtSecFrom);

    }


    public String getTxtFrom() {
        return txtFrom;
    }

    public void setTxtFrom(String txtFrom) {
        this.txtFrom = txtFrom;
    }

    public String getTxtDayDate() {
        return txtDayDate;
    }

    public void setTxtDayDate(String txtDayDate) {
        this.txtDayDate = txtDayDate;
    }

    public String getTxtTo() {
        return txtTo;
    }

    public void setTxtTo(String txtTo) {
        this.txtTo = txtTo;
    }

    public String getTxtIncre() {
        return txtIncre;
    }

    public void setTxtIncre(String txtIncre) {
        this.txtIncre = txtIncre;
    }

    public String getTxtback() {
        return txtback;
    }

    public void setTxtback(String txtback) {
        this.txtback = txtback;
    }

    public String getTxtSecTo() {
        return txtSecTo;
    }

    public void setTxtSecTo(String txtSecTo) {
        this.txtSecTo = txtSecTo;
    }

    public String getTxtSecFrom() {
        return txtSecFrom;
    }

    public void setTxtSecFrom(String txtSecFrom) {
        this.txtSecFrom = txtSecFrom;
    }
}

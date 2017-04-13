package com.app.ace.entities;

/**
 * Created by saeedhyder on 4/7/2017.
 */

public class TraineeScheduleItem {

    private String bookingDetailCol1;
    private String bookingDetailCol2;

    public TraineeScheduleItem(String bookingDetailCol1, String bookingDetailCol2)
    {
        setBookingDetailCol1(bookingDetailCol1);
        setBookingDetailCol2(bookingDetailCol2);

    }

    public String getBookingDetailCol1() {
        return bookingDetailCol1;
    }

    public void setBookingDetailCol1(String bookingDetailCol1) {
        this.bookingDetailCol1 = bookingDetailCol1;
    }

    public String getBookingDetailCol2() {
        return bookingDetailCol2;
    }

    public void setBookingDetailCol2(String bookingDetailCol2) {
        this.bookingDetailCol2 = bookingDetailCol2;
    }
}

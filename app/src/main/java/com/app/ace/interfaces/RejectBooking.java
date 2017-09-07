package com.app.ace.interfaces;

import com.app.ace.entities.NotificationEnt;

/**
 * Created by saeedhyder on 8/30/2017.
 */

public interface RejectBooking {

    void deleteBooking(int Position,Object entity);
    void acceptBooking(int Position,Object entity);


}

package com.app.ace.interfaces;

/**
 * Created by saeedhyder on 5/9/2017.
 */

import com.google.android.gms.maps.model.LatLng;

public interface IGetLocation {

    public void onLocationSet(LatLng location, String formattedAddress);
}

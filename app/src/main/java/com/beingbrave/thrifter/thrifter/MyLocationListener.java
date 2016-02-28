package com.beingbrave.thrifter.thrifter;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by ryant on 28/02/2016.
 */
public class MyLocationListener implements LocationListener {

    public static Location curentLocation;

    @Override
    public void onLocationChanged(Location location) {
        curentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

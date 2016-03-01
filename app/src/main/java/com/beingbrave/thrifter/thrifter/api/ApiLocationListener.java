package com.beingbrave.thrifter.thrifter.api;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ryant on 28/02/2016.
 */
public class ApiLocationListener implements LocationListener {

    private Location curentLocation;

    public Location getCurentLocation() {
        return curentLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LocationListener", "Received location");
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

package com.beingbrave.thrifter.data;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.beingbrave.thrifter.ThrifterApplication;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;

public class LocationApi {

    private static final int REQUEST_LOCATION = 1;
    private static Location currentLocation;
    private static LocationManager locationManager;
    private static boolean isInitialized = false;

    // SHAMEFUL CODE, SHAME, SHAME, SHAME, SHAME......
    public static GeoQuery activeQuery = null;

    public static void initialize(final Activity activity) {
        locationManager = (LocationManager) ThrifterApplication.getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(ThrifterApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ThrifterApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

            // TODO: Wait for allow access
        } else {
            Log.d("LocationApi", "Setup location listener");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    Log.d("LocationApi", "Received location");
                    currentLocation = location;
                    if(activeQuery != null) {
                        activeQuery.setCenter(new GeoLocation(location.getLatitude(), location.getLongitude()));
                    }
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
            });

            isInitialized = true;
        }
    }

    public static Location getCurrentLocation() {
        if (!isInitialized) return null;
        return currentLocation;
    }


}

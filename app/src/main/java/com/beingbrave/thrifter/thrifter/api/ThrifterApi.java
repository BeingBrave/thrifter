package com.beingbrave.thrifter.thrifter.api;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ImageView;

import com.beingbrave.thrifter.thrifter.MainActivity;
import com.beingbrave.thrifter.thrifter.UploadActivity;
import com.beingbrave.thrifter.thrifter.model.ItemModel;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;

public class ThrifterApi {

    private static final String TAG = "ThrifterAPI";

    private String appToken;
    private Context context;
    private LocationManager locationManager;
    private boolean isInitialized = false;

    private final String endPoint = "http://178.62.117.169:3333";
    private ApiLocationListener locationListener;

    public ThrifterApi(Context context) {
        this.context = context;
    }

    public ThrifterApi(Context context, String appToken) {
        this.context = context;
        setAppToken(appToken);
    }

    public void initialize(Activity activity) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new ApiLocationListener();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    2);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }

        isInitialized = true;
    }

    public void setAppToken(String appToken) {
        Log.d(TAG, "App token: " + appToken);
        this.appToken = appToken;
    }

    public void requestFacebookLogin(String token, FutureCallback<JsonArray> callback) {
        Log.d(TAG, "Facebook token: " + token);
        Ion.with(context)
                .load(endPoint + "/auth/facebook?access_token=" + token)
                .asJsonArray()
                .setCallback(callback);
    }

    public void requestUpload(File file, FutureCallback<JsonArray> callback) {
        Ion.with(context)
                .load(endPoint + "/item?access_token=" + appToken)
                .setMultipartFile("upload", file)
                .setMultipartParameter("lat", String.valueOf(locationListener.getCurentLocation().getLatitude()))
                .setMultipartParameter("lon", String.valueOf(locationListener.getCurentLocation().getLongitude()))
                .setMultipartParameter("name", "lol")
                .asJsonArray()
                .setCallback(callback);
    }

    public void requestSearch(FutureCallback<JsonArray> callback) {
        StringBuilder url = new StringBuilder();
        url.append(endPoint + "/search?access_token=" + appToken);
        url.append("&distance=" + String.valueOf(100));
        if(locationListener != null && locationListener.getCurentLocation() != null) {
            url.append("&lat=" + String.valueOf(locationListener.getCurentLocation().getLatitude())
                    + "&lon=" + String.valueOf(locationListener.getCurentLocation().getLongitude()));
        }

        Ion.with(context)
                .load(url.toString())
                .asJsonArray()
                .setCallback(callback);
    }

    public void requestItem(String itemUuid, FutureCallback<JsonArray> callback) {
        Ion.with(context)
                .load(endPoint + "/item/" + itemUuid + "?access_token=" + appToken)
                .asJsonArray()
                .setCallback(callback);
    }

    public void loadImage(String imageHash, ImageView view) {
        Ion.with(view)
                .load(endPoint + "/static/" + imageHash);
    }
}

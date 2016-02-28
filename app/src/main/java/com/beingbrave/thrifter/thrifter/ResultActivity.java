package com.beingbrave.thrifter.thrifter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Ion.with(getApplicationContext())
                .load("http://178.62.117.169:3333/search?access_token=" + MainActivity.appToken)
                .setBodyParameter("lat", String.valueOf(MyLocationListener.curentLocation.getLatitude()))
                .setBodyParameter("lon", String.valueOf(MyLocationListener.curentLocation.getLongitude()))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        System.out.println(result.toString());
                    }
                });
    }

}

package com.beingbrave.thrifter.thrifter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;

public class UploadActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.app_bar_layout, null);

        actionBar.setCustomView(v);
    }

    public void onClick(View v) {
        Ion.with(getApplicationContext())
                .load("http://178.62.117.169:3333/search?access_token=" + MainActivity.appToken)
                .setMultipartFile("upload", new File(selectedImagePath))
                .setMultipartParameter("lat", String.valueOf(MyLocationListener.curentLocation.getLatitude()))
                .setMultipartParameter("lon", String.valueOf(MyLocationListener.curentLocation.getLongitude()))
                .setMultipartParameter("name", "lol")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
    }


    public void onImageClick(View v) {
        // in onCreate or any event where your want the user to
        // select a file
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        if (resultCode == RESULT_OK) {
            System.out.println("Result Ok");
            if (requestCode == SELECT_PICTURE) {
                System.out.println("Select Picture");
                Uri selectedImageUri = data.getData();
                selectedImagePath = selectedImageUri.getPath();
            }
        }
    }

}

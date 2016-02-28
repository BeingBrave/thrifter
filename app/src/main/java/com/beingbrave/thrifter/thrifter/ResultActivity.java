package com.beingbrave.thrifter.thrifter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONObject;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<ItemModel> al;
    private CardAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        al = new ArrayList<ItemModel>();


//        Ion.with(getApplicationContext())
//                .load("http://178.62.117.169:3333/search?access_token=" + MainActivity.appToken)
//                .setBodyParameter("lat", MyLocationListener.curentLocation != null ? String.valueOf(MyLocationListener.curentLocation.getLatitude()) : "0")
//                .setBodyParameter("lon", MyLocationListener.curentLocation != null ? String.valueOf(MyLocationListener.curentLocation.getLongitude()) : "0")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonArray result) {
//                        //System.out.println(result.toString());
//                    }
//                });

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.cardFrame);

        //choose your favorite adapter
        arrayAdapter = new CardAdapter(al);

        //set the listener and the adapter
        //flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(ResultActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(ResultActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                //al.add("XML ".concat(String.valueOf(i)));
                //arrayAdapter.notifyDataSetChanged();
                //Log.d("LIST", "notified");
            }

            @Override
            public void onScroll(float v) {

            }
        });

//        // Optionally add an OnItemClickListener
//        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClicked(int itemPosition, Object dataObject) {
//                makeToast(MyActivity.this, "Clicked!");
//            }
//        });

    }

}

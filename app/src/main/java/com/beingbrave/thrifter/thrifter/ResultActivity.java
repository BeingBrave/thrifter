package com.beingbrave.thrifter.thrifter;

import android.content.ClipData;
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

import com.beingbrave.thrifter.thrifter.adapters.CardAdapter;
import com.beingbrave.thrifter.thrifter.model.ItemModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    private ArrayList<ItemModel> al;
    private CardAdapter cardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        al = new ArrayList<>();

        ((ThrifterApplication) getApplicationContext()).api.requestSearch(
                new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(result != null) {
                            Log.d(TAG, result.toString());
                        }
                    }
                }
        );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.card_frame);

        // Test data
        al.add(new ItemModel("Test", "hi"));

        //choose your favorite adapter
        cardAdapter = new CardAdapter(this, al);

        flingContainer.setAdapter(cardAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                cardAdapter.notifyDataSetChanged();
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

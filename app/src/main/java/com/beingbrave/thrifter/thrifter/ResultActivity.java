package com.beingbrave.thrifter.thrifter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beingbrave.thrifter.thrifter.adapters.CardAdapter;
import com.beingbrave.thrifter.thrifter.model.ItemModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    private ArrayList<ItemModel> al;
    private CardAdapter cardAdapter;
    private SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        al = new ArrayList<>();

        ((ThrifterApplication) getApplicationContext()).api.requestSearch(
                new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null) {
                            e.printStackTrace();
                            return;
                        }

                        if(result.get("error") != null) {
                            return;
                        }

                        JsonArray resultObject = result.getAsJsonArray("data");
                        Log.d(TAG, resultObject.toString());
                        for(JsonElement element : resultObject) {
                            JsonObject obj = (JsonObject) element;

                            al.add(new ItemModel(obj.get("name").getAsString(), obj.get("imageHash").getAsString()));
                        }

                        cardAdapter.notifyDataSetChanged();
                    }
                }
        );

        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.card_frame);

        cardAdapter = new CardAdapter(this, al);

        flingContainer.setAdapter(cardAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                al.remove(0);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //ItemModel item = (ItemModel) dataObject;
                Toast.makeText(ResultActivity.this, "Disliked", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content), "Disliked", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //ItemModel item = (ItemModel) dataObject;
                Toast.makeText(ResultActivity.this, "Liked", Toast.LENGTH_SHORT).show();
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
    }

    public void onLikeClick(View v) {
        flingContainer.getTopCardListener().selectRight();
    }

    public void onDislikeClick(View v) {
        flingContainer.getTopCardListener().selectLeft();
    }

}

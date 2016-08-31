package com.beingbrave.thrifter.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.beingbrave.thrifter.R;
import com.beingbrave.thrifter.ThrifterApplication;
import com.beingbrave.thrifter.adapters.CardAdapter;
import com.beingbrave.thrifter.data.ThrifterApi;
import com.beingbrave.thrifter.models.Post;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    @BindView(R.id.toolbar) Toolbar toolbar;

    private ArrayList<Post> al;
    private CardAdapter cardAdapter;
    private SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        // Bind views
        ButterKnife.bind(this);

        // Use support library action bar instead of default
        setSupportActionBar(toolbar);

        al = new ArrayList<>();

        ThrifterApplication.getApi().searchPosts(1, new ThrifterApi.SearchCallback() {
            @Override
            public void onNewPost(Post post) {
                al.add(post);
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });

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
                //Post item = (Post) dataObject;
                Toast.makeText(ResultActivity.this, "Disliked", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content), "Disliked", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //Post item = (Post) dataObject;
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

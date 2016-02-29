package com.beingbrave.thrifter.thrifter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.beingbrave.thrifter.thrifter.R;
import com.beingbrave.thrifter.thrifter.ResultActivity;


public class SearchFragment extends Fragment{

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.content_main, container, false);

        EditText searchBar = (EditText) view.findViewById(R.id.search);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {

                if ((actionId == EditorInfo.IME_ACTION_SEARCH)
                        || (actionId == EditorInfo.IME_ACTION_DONE)
                        || (event.getAction() == KeyEvent.ACTION_DOWN)
                        && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                        &&(!event.isShiftPressed())) {

                        // the user is done typing.
                        Intent intent = new Intent(getActivity(), ResultActivity.class);
                        intent.putExtra("SEARCH", "test");
                        startActivity(intent);

                        return true; // consume.
                    }
                else
                {
                return false; // pass on to other listeners.
                }
            }
        });

        return view;
    }



}

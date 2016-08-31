package com.beingbrave.thrifter.fragments;

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

import com.beingbrave.thrifter.R;
import com.beingbrave.thrifter.activities.ResultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.Unbinder;


public class SearchFragment extends Fragment{

    @BindView(R.id.search) EditText searchText;
    private Unbinder unbinder;

    public SearchFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    public void doSearch() {
        Intent intent = new Intent(getActivity(), ResultActivity.class);
        intent.putExtra("SEARCH", searchText.getText().toString());
        startActivity(intent);
    }

//    @OnClick(R.id.search)
//    public void onClick() {
//        doSearch();
//    }

    @OnEditorAction(R.id.search)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId == EditorInfo.IME_ACTION_SEARCH)
                || (actionId == EditorInfo.IME_ACTION_DONE)
                || (event.getAction() == KeyEvent.ACTION_DOWN)
                && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                &&(!event.isShiftPressed())) {

            doSearch();

            return true;
        }

        return false;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}

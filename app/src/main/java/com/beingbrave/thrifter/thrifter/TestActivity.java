package com.beingbrave.thrifter.thrifter;

import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.widget.ArrayAdapter;

public class TestActivity extends ListActivity {
    private IdentifiersDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.);

        datasource = new IdentifiersDataSource(this);
        datasource.open();

        List<Identifier> values = datasource.getAllIdentifiers();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Identifier> adapter = new ArrayAdapter<Identifier>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Identifier> adapter = (ArrayAdapter<Identifier>) getListAdapter();
        Identifier identifier = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new identifier to the database
                identifier = datasource.createIdentifier(comments[nextInt]);
                adapter.add(identifier);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    identifier = (Identifier) getListAdapter().getItem(0);
                    datasource.deleteIdentifier(identifier);
                    adapter.remove(identifier);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}


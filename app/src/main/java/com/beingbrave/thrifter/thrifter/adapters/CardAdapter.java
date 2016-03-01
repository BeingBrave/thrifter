package com.beingbrave.thrifter.thrifter.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beingbrave.thrifter.thrifter.R;
import com.beingbrave.thrifter.thrifter.ThrifterApplication;
import com.beingbrave.thrifter.thrifter.model.ItemModel;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class CardAdapter extends BaseAdapter {
    private static final String TAG = "CardAdapter";

    private Context context;

    private ArrayList<ItemModel> mDataSet;

    public CardAdapter(Context context, ArrayList<ItemModel> data) {
        this.context = context;
        mDataSet = data;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public ItemModel getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if(convertView == null) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_card, parent, false);
        } else {
            v = convertView;
        }

        ItemModel item = getItem(position);
        TextView title = ((TextView) v.findViewById(R.id.card_title));
        title.setText(item.getTitle());

        ImageView imageView = ((ImageView) v.findViewById(R.id.card_image));

        ((ThrifterApplication) context.getApplicationContext()).api.loadImage(item.getImageHash(), imageView);

        return v;
    }
}

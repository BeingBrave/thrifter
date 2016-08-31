package com.beingbrave.thrifter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beingbrave.thrifter.R;
import com.beingbrave.thrifter.models.Post;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CardAdapter extends BaseAdapter {
    private static final String TAG = "CardAdapter";

    private Context context;

    private ArrayList<Post> mDataSet;

    public CardAdapter(Context context, ArrayList<Post> data) {
        this.context = context;
        mDataSet = data;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Post getItem(int position) {
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

        Post item = getItem(position);
        TextView title = ((TextView) v.findViewById(R.id.card_title));
        title.setText(item.getTitle());

        ImageView imageView = ((ImageView) v.findViewById(R.id.card_image));

        System.out.println("URI: " + item.getImageUri().toString());
        Glide.with(context)
                .load(item.getImageUri().toString())
                .into(imageView);

        return v;
    }
}

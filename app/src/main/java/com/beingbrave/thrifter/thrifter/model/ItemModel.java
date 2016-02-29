package com.beingbrave.thrifter.thrifter.model;

import android.content.ClipData;
import android.widget.ImageView;

public class ItemModel {

    private String title;
    private String imageHash;

    public ItemModel(String title, String imageHash) {
        this.title = title;
        this.imageHash = imageHash;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImageHash(String hash) {
        imageHash = hash;
    }

    public String getImageHash() {
        return imageHash;
    }
}

package com.beingbrave.thrifter.models;

import android.net.Uri;

import com.google.firebase.database.Exclude;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("title")
    private String title;

    @Exclude
    private String postId;

    @Exclude
    private transient Uri imageUri;

    @SerializedName("userId")
    private String userId;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public Post(String title, Uri imageUri) {
        this.title = title;
        this.imageUri = imageUri;
    }

    public Post() {}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Exclude
    public void setImageUri(Uri uri) {
        this.imageUri = uri;
    }

    @Exclude
    public Uri getImageUri() {
        return imageUri;
    }
}

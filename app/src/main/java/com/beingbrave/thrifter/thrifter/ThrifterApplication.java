package com.beingbrave.thrifter.thrifter;

import android.app.Application;

import com.beingbrave.thrifter.thrifter.api.ThrifterApi;

public class ThrifterApplication extends Application {
    public ThrifterApi api;

    @Override
    public void onCreate() {
        api = new ThrifterApi(this);
    }
}

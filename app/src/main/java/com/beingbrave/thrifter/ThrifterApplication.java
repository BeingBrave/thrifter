package com.beingbrave.thrifter;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import com.beingbrave.thrifter.data.LocationApi;
import com.beingbrave.thrifter.data.ThrifterApiFirebaseImpl;
import com.beingbrave.thrifter.data.ThrifterApi;

public class ThrifterApplication extends Application {

    private static ThrifterApi api;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //LocationApi.initialize();
        api = new ThrifterApiFirebaseImpl();
    }

    public static ThrifterApi getApi() {
        return api;
    }

    public static Context getContext() {
        return mContext;
    }
}

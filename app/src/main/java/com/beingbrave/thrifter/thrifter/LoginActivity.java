package com.beingbrave.thrifter.thrifter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends FragmentActivity {

    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
    }

    public void onToken(String token) {
        Ion.with(getApplicationContext())
                .load("http://178.62.117.169:3333/auth/facebook?access_token=" + token)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        String appToken = ((JsonObject) result.get(0)).get("access_token").toString();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("TOKEN", appToken);
                        startActivity(intent);
                    }
                });
    }
}


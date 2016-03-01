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
 * A login screen that offers login via facebook
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
        ((ThrifterApplication) getApplicationContext()).api.requestFacebookLogin(token, new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if(e != null) {
                    e.printStackTrace();
                    return;
                }

                if(result.get("error") != null) {
                    return;
                }

                JsonObject resultObject = result.getAsJsonArray("data").get(0).getAsJsonObject();
                String appToken = resultObject.get("access_token").getAsString();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("TOKEN", appToken);
                startActivity(intent);
            }
        });
    }
}


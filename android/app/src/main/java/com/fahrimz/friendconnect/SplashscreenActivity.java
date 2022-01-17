package com.fahrimz.friendconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashscreenActivity extends AppCompatActivity {

    PrefManager pref;
    private int loadingTime = 3000; // 3s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        pref = new PrefManager(this);

        new Handler().postDelayed(() -> {
            boolean isLoggedIn = pref.isLoggedIn();
            Log.d("debug", isLoggedIn ? "isLoggedIn" : "notLoggedIn");

            Class nextActivityClass = isLoggedIn ? ListPostActivity.class : MainActivity.class;
            Intent nextActivity = new Intent(SplashscreenActivity.this, nextActivityClass);
            startActivity(nextActivity);
            finish();
        }, loadingTime);
    }
}

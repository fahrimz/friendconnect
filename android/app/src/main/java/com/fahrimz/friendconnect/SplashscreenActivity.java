package com.fahrimz.friendconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

public class SplashscreenActivity extends AppCompatActivity {

    PrefManager pref;
    private int loadingTime = 3000; // 3s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pref = new PrefManager(this);

        new Handler().postDelayed(() -> {
            boolean isLoggedIn = pref.isLoggedIn();

            Class nextActivityClass = isLoggedIn ? ListPostActivity.class : LoginActivity.class;
            Intent nextActivity = new Intent(SplashscreenActivity.this, nextActivityClass);
            startActivity(nextActivity);
            finish();
        }, loadingTime);
    }
}

package com.fahrimz.friendconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ListPostActivity extends AppCompatActivity {

    TextView txtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);

        PrefManager pref = new PrefManager(this);
        String username = pref.getUsername();

        txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText(username);
    }
}

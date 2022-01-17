package com.fahrimz.friendconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fahrimz.friendconnect.model.ErrorResponse;
import com.fahrimz.friendconnect.model.LoginResponse;
import com.fahrimz.friendconnect.model.LoginRequest;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    Button btnLogin;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        userService = ApiUtils.getUserService();

        btnLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();

            // validate form
            if (validateLogin(username, password)) {
                doLogin(username, password);
            }
        });
    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username,final String password){
        LoginRequest body = new LoginRequest(username, password);

        Call<LoginResponse> call = userService.login(body);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, ListPostActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Gson gson = new Gson();
                    ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(MainActivity.this, error.getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("response", t.getLocalizedMessage());
            }
        });
    }
}
package com.fahrimz.friendconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fahrimz.friendconnect.model.AddFriendRequest;
import com.fahrimz.friendconnect.model.AddFriendResponse;
import com.fahrimz.friendconnect.model.ErrorResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendActivity extends AppCompatActivity {

    private EditText txtInviteCode;
    private Button btnAddFriend;

    private PrefManager pref;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        pref = new PrefManager(this);
        userService = ApiUtils.getUserService();

        txtInviteCode = findViewById(R.id.txtInviteCode);
        btnAddFriend = findViewById(R.id.btnAddFriend);

        btnAddFriend.setOnClickListener(v -> {
            String inviteCode = txtInviteCode.getText().toString();
            addFriend(inviteCode);
        });
    }

    private void addFriend(String inviteCode) {
        AddFriendRequest body = new AddFriendRequest(inviteCode);
        String token = "Bearer " + pref.getAccessToken();
        Call<AddFriendResponse> call = userService.addFriend(body, token);
        call.enqueue(new Callback<AddFriendResponse>() {
            @Override
            public void onResponse(Call<AddFriendResponse> call, Response<AddFriendResponse> response) {
                if (response.isSuccessful()) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Gson gson = new Gson();
                    ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(AddFriendActivity.this, error.getError(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<AddFriendResponse> call, Throwable t) {
                Log.d("response", t.getLocalizedMessage());
                Toast.makeText(AddFriendActivity.this, "Cannot add friend. Please try again later", Toast.LENGTH_SHORT);
            }
        });
    }
}

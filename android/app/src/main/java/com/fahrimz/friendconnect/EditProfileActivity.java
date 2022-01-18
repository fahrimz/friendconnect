package com.fahrimz.friendconnect;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.fahrimz.friendconnect.model.EditProfileResponse;
import com.fahrimz.friendconnect.model.ErrorResponse;
import com.fahrimz.friendconnect.model.MyProfileResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.UserService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private TextView txtUsername;
    private EditText txtBio, txtInviteCode;
    private Button btnSave;
    private ImageView imgAvatar;

    private PrefManager pref;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = new PrefManager(this);
        userService = ApiUtils.getUserService();

        txtUsername = findViewById(R.id.txtUsernameEdit);
        txtBio = findViewById(R.id.txtBioEdit);
        txtInviteCode = findViewById(R.id.txtInviteCodeEdit);
        btnSave = findViewById(R.id.btnSave);

        imgAvatar = findViewById(R.id.imgAvatarEdit);
        Glide.with(this).load(imgAvatar.getDrawable()).circleCrop().into(imgAvatar);

        getData();

        btnSave.setOnClickListener(v -> {
            String bio = txtBio.getText().toString();
            String inviteCode = txtInviteCode.getText().toString();
            int idUser = pref.getIdUser();

            updateProfile(new EditProfileRequest(idUser, bio, inviteCode));
        });
    }

    private void updateProfile(EditProfileRequest profile) {
        String token = "Bearer " + pref.getAccessToken();
        Call<EditProfileResponse> call = userService.updateProfile(profile, token);
        call.enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                if (response.isSuccessful()) {
                    finish();
                } else {
                    Gson gson = new Gson();
                    ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    if (error.getError().contains("duplicate")) {
                        Toast.makeText(EditProfileActivity.this, "invite code cannot be changed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData() {
        String token = "Bearer " + pref.getAccessToken();
        Call<MyProfileResponse> call = userService.getMyProfile(token);
        call.enqueue(new Callback<MyProfileResponse>() {
            @Override
            public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                if (response.isSuccessful()) {
                    MyProfileResponse profile = response.body();
                    txtUsername.setText(profile.getUsername());
                    txtBio.setText(profile.getBio());
                    txtInviteCode.setText(profile.getInviteCode());

                    // avatar
                    // problem with via placeholder: https://stackoverflow.com/q/62425649
                    try {
                        String url = profile.getAvatarUrl();
                        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                                .addHeader("User-Agent", "okhttp/4.2.1")
                                .build());

                        Glide.with(EditProfileActivity.this)
                                .load(glideUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .circleCrop()
                                .into(imgAvatar);
                    } catch (Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Cannot show image", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

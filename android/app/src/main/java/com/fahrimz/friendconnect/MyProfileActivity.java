package com.fahrimz.friendconnect;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.fahrimz.friendconnect.model.MyProfileResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private TextView txtUsername, txtBio, txtTotalFriend, txtTotalPost, txtInviteCode;
    private FloatingActionButton btnEdit;

    private PrefManager pref;
    private UserService userService;
    private MyProfileResponse profile;

    ActivityResultLauncher<Intent> activityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = new PrefManager(this);
        userService = ApiUtils.getUserService();

        txtUsername = findViewById(R.id.txtUsername);
        txtBio = findViewById(R.id.txtBio);
        txtTotalFriend = findViewById(R.id.txtTotalFriend);
        txtTotalPost = findViewById(R.id.txtTotalPost);
        txtInviteCode = findViewById(R.id.txtInviteCode);
        btnEdit = findViewById(R.id.btnEdit);

        imgAvatar = findViewById(R.id.imgAvatar);
        Glide.with(this).load(imgAvatar.getDrawable()).circleCrop().into(imgAvatar);

        getData();


        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    getData();
                }
        );

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            activityLauncher.launch(intent);
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
                    String inviteCode = "Invite Code: " + profile.getInviteCode();
                    String totalFriend = profile.getFriends() + " friends";
                    String totalPost = profile.getPosts() + " posts";

                    txtUsername.setText(profile.getUsername());
                    txtBio.setText("\"" + profile.getBio() + "\"");
                    txtInviteCode.setText(inviteCode);
                    txtTotalFriend.setText(totalFriend);
                    txtTotalPost.setText(totalPost);

                    // avatar
                    // problem with via placeholder: https://stackoverflow.com/q/62425649
                    try {
                        String url = profile.getAvatarUrl();
                        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                                .addHeader("User-Agent", "okhttp/4.2.1")
                                .build());

                        Glide.with(MyProfileActivity.this)
                                .load(glideUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .circleCrop()
                                .into(imgAvatar);
                    } catch (Exception e) {
                        Toast.makeText(MyProfileActivity.this, "Cannot show image", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                Toast.makeText(MyProfileActivity.this, "Cannot get profile. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

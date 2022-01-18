package com.fahrimz.friendconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fahrimz.friendconnect.model.AddPostRequest;
import com.fahrimz.friendconnect.model.AddPostResponse;
import com.fahrimz.friendconnect.model.ErrorResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.PostService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddPostActivity extends AppCompatActivity {

    private EditText txtPostBody;
    private Button btnPost;

    PrefManager pref;
    PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        setTitle("Add Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = new PrefManager(this);
        postService = ApiUtils.getPostService();

        txtPostBody = findViewById(R.id.txtPostBody);
        btnPost = findViewById(R.id.btnPost);

        btnPost.setOnClickListener(v -> {
            int idUser = pref.getIdUser();
            String postBody = txtPostBody.getText().toString();

            addPost(idUser, postBody);
        });
    }

    private void addPost(int idUser, String postBody) {
        AddPostRequest body = new AddPostRequest(idUser, postBody);
        String token = "Bearer " + pref.getAccessToken();
        Log.d("debug", token);
        Call<AddPostResponse> call = postService.addPost(body, token);
        call.enqueue(new Callback<AddPostResponse>() {
            @Override
            public void onResponse(Call<AddPostResponse> call, Response<AddPostResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Gson gson = new Gson();
                    ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(AddPostActivity.this, error.getError(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<AddPostResponse> call, Throwable t) {
                Log.d("response", t.getLocalizedMessage());
                Toast.makeText(AddPostActivity.this, "Cannot add post. Please try again later", Toast.LENGTH_SHORT);
            }
        });
    }
}

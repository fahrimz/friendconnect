package com.fahrimz.friendconnect;

import static com.fahrimz.friendconnect.DetailPostActivity.EXTRA_KEY_ID_POST;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fahrimz.friendconnect.model.AddCommentRequest;
import com.fahrimz.friendconnect.model.AddCommentResponse;
import com.fahrimz.friendconnect.model.ErrorResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.PostService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentActivity extends AppCompatActivity {

    private EditText txtBody;
    private Button btnComment;

    PrefManager pref;
    PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        setTitle("Add Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = new PrefManager(this);
        postService = ApiUtils.getPostService();

        txtBody = findViewById(R.id.txtAddCommentBody);
        btnComment = findViewById(R.id.btnComment);

        // get idPost from previous activity
        Bundle extras = getIntent().getExtras();
        try {
            int idPost = extras.getInt(EXTRA_KEY_ID_POST);
            int idUser = pref.getIdUser();

            btnComment.setOnClickListener(v -> {
                String comment = txtBody.getText().toString();
                addComment(idPost, idUser, comment);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addComment(int idPost, int idUser, String comment) {
        String token = "Bearer " + pref.getAccessToken();
        AddCommentRequest body = new AddCommentRequest(idPost, idUser, comment);
        Call<AddCommentResponse> call = postService.addComment(body, token);
        call.enqueue(new Callback<AddCommentResponse>() {
            @Override
            public void onResponse(Call<AddCommentResponse> call, Response<AddCommentResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Gson gson = new Gson();
                    ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(AddCommentActivity.this, error.getError(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<AddCommentResponse> call, Throwable t) {
                Log.d("response", t.getLocalizedMessage());
                Toast.makeText(AddCommentActivity.this, "Cannot add comment. Please try again later", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }
}

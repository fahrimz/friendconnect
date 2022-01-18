package com.fahrimz.friendconnect;

import static com.fahrimz.friendconnect.DetailPostActivity.EXTRA_KEY_ID_POST;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fahrimz.friendconnect.model.ErrorResponse;
import com.fahrimz.friendconnect.model.PostsItem;
import com.fahrimz.friendconnect.model.PostsResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.PostService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPostActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private ListPostAdapter listPostAdapter;
    private ArrayList<PostsItem> listPost = null;
    private FloatingActionButton btnAdd;

    private PrefManager pref;
    private PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);
        pref = new PrefManager(this);
        postService = ApiUtils.getPostService();

        ActivityResultLauncher<Intent> addPostActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    getData();
                }
            }
        );

        recyclerView = findViewById(R.id.recycleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPostActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        listPostAdapter = new ListPostAdapter(listPost);
        listPostAdapter.clickListener = this;

        recyclerView.setAdapter(listPostAdapter);
        getData();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddPostActivity.class);
            addPostActivityLauncher.launch(intent);
        });
    }

    public void getData() {
        String token = "Bearer " + pref.getAccessToken();
        Call<PostsResponse> call = postService.getPosts(token);
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    ArrayList<PostsItem> list = new ArrayList<>();
                    list.addAll(response.body().getPosts());
                    listPostAdapter.setData(list);
                } else {
                    Gson gson = new Gson();
                    ErrorResponse error = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(ListPostActivity.this, error.getError(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.d("debug", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onItemClicked(View view, PostsItem item) {
        Intent intent = new Intent(this, DetailPostActivity.class);
        intent.putExtra(EXTRA_KEY_ID_POST, item.getIdPost());
        startActivity(intent);
    }
}

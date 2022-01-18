package com.fahrimz.friendconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fahrimz.friendconnect.model.PostsItem;
import com.fahrimz.friendconnect.model.PostsResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.PostService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPostActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListPostAdapter listPostAdapter;
    private ArrayList<PostsItem> listPost;

    private PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);
        postService = ApiUtils.getPostService();

        recyclerView = findViewById(R.id.recycleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPostActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        listPostAdapter = new ListPostAdapter(listPost);
        recyclerView.setAdapter(listPostAdapter);
        getData();
    }

    public void getData() {
        Call<PostsResponse> call = postService.getPosts();
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                ArrayList<PostsItem> list = new ArrayList<>();
                list.addAll(response.body().getPosts());

                if (response.body() != null && response.isSuccessful()) {
                    listPostAdapter.setData(list);
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.d("debug: getData() onFailure()", t.getLocalizedMessage());
            }
        });
    }
}

package com.fahrimz.friendconnect;

import static com.fahrimz.friendconnect.DetailPostActivity.EXTRA_KEY_ID_POST;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private SwipeRefreshLayout swipeRefresh;

    private PrefManager pref;
    private PostService postService;
    ActivityResultLauncher<Intent> activityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post);
        setTitle("Posts");

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        pref = new PrefManager(this);
        postService = ApiUtils.getPostService();

        activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                getData();
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
            activityLauncher.launch(intent);
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
                    Toast.makeText(ListPostActivity.this, error.getError(), Toast.LENGTH_SHORT).show();
                }

                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                swipeRefresh.setRefreshing(false);
                Toast.makeText(ListPostActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClicked(View view, PostsItem item) {
        Intent intent = new Intent(this, DetailPostActivity.class);
        intent.putExtra(EXTRA_KEY_ID_POST, item.getIdPost());
        activityLauncher.launch(intent);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.add_friend:
                intent = new Intent(this, AddFriendActivity.class);
                activityLauncher.launch(intent);
                return true;
            case R.id.my_profile:
                intent = new Intent(this, MyProfileActivity.class);
                activityLauncher.launch(intent);
                return true;
            case R.id.logout:
                pref.clearSession();
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

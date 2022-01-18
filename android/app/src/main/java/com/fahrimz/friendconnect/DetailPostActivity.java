package com.fahrimz.friendconnect;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.fahrimz.friendconnect.model.CommentsItem;
import com.fahrimz.friendconnect.model.DetailPostResponse;
import com.fahrimz.friendconnect.remote.ApiUtils;
import com.fahrimz.friendconnect.remote.PostService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostActivity extends AppCompatActivity {

    private RecyclerView commentRecyclerView;
    private ListCommentAdapter listCommentAdapter;
    private ArrayList<CommentsItem> listComment = null;

    private TextView txtUsername, txtBody, txtLikes, txtDate, txtCommentTitle;
    private ImageView imgAvatar;
    public static final String EXTRA_KEY_ID_POST = "ID_POST";

    PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        postService = ApiUtils.getPostService();
        commentRecyclerView = findViewById(R.id.commentRecycleView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DetailPostActivity.this);
        commentRecyclerView.setLayoutManager(layoutManager);
        listCommentAdapter = new ListCommentAdapter(listComment);
        commentRecyclerView.setAdapter(listCommentAdapter);


        txtUsername = findViewById(R.id.txtDetailUsername);
        txtBody = findViewById(R.id.txtDetailBody);
        txtLikes = findViewById(R.id.txtDetailLikes);
        txtDate = findViewById(R.id.txtDetailDate);
        txtCommentTitle = findViewById(R.id.txtCommentTitle);
        imgAvatar = findViewById(R.id.imgDetailAvatar);

        // get idPost from previous activity
        Bundle extras = getIntent().getExtras();
        try {
            int idPost = extras.getInt(EXTRA_KEY_ID_POST);
            getData(idPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getData(int idPost) {
        Call<DetailPostResponse> call = postService.getPost(idPost);
        call.enqueue(new Callback<DetailPostResponse>() {
            @Override
            public void onResponse(Call<DetailPostResponse> call, Response<DetailPostResponse> response) {
                if (response.isSuccessful()) {
                    DetailPostResponse post = response.body();
                    txtUsername.setText(post.getUsername());
                    txtBody.setText(post.getBody());
                    txtLikes.setText(post.getLikes().size() + " likes");

                    String date = Utils.formatDateFromDatabaseString(post.getCreatedAt());
                    txtDate.setText(date);

                    ArrayList<CommentsItem> list = new ArrayList<>();
                    list.addAll(post.getComments());
                    listCommentAdapter.setData(list);

                    txtCommentTitle.setText("Comments (" + list.size() + ")");

                    // problem with via placeholder: https://stackoverflow.com/q/62425649
                    try {
                        String url = post.getAvatarUrl();
                        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                                .addHeader("User-Agent", "okhttp/4.2.1")
                                .build());

                        Glide.with(DetailPostActivity.this)
                                .load(glideUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .centerCrop()
                                .into(imgAvatar);
                    } catch (Exception e) {
                        Toast.makeText(DetailPostActivity.this, "Cannot show image", Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailPostResponse> call, Throwable t) {
                Toast.makeText(DetailPostActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        });
    }
}

package com.fahrimz.friendconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.fahrimz.friendconnect.model.CommentsItem;
import com.fahrimz.friendconnect.model.DetailPostResponse;
import com.fahrimz.friendconnect.model.ToggleLikeRequest;
import com.fahrimz.friendconnect.model.ToggleLikeResponse;
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

    private TextView txtUsername, txtBody, txtLikes, txtDate, txtCommentTitle, txtAddComment;
    private ImageView imgAvatar;
    private ImageButton imgLike;
    public static final String EXTRA_KEY_ID_POST = "ID_POST";

    PrefManager pref;
    PostService postService;

    ActivityResultLauncher<Intent> addCommentActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        pref = new PrefManager(this);
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
        imgLike = findViewById(R.id.imgLike);
        txtAddComment = findViewById(R.id.txtAddComment);

        // get idPost from previous activity
        Bundle extras = getIntent().getExtras();
        try {
            int idPost = extras.getInt(EXTRA_KEY_ID_POST);
            getData(idPost);

            addCommentActivityLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(), result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            getData(idPost);
                        }
                    }
            );

            txtAddComment.setOnClickListener(v -> {
                Intent intent = new Intent(this, AddCommentActivity.class);
                intent.putExtra(EXTRA_KEY_ID_POST, idPost);
                addCommentActivityLauncher.launch(intent);
            });

            imgLike.setOnClickListener(v -> {
                toggleLikeOnPost(idPost, pref.getIdUser());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleLikeOnPost(int idPost, int idUser) {
        ToggleLikeRequest body = new ToggleLikeRequest(idPost, idUser);
        String token = "Bearer " + pref.getAccessToken();
        Call<ToggleLikeResponse> call = postService.toggleLike(body, token);
        call.enqueue(new Callback<ToggleLikeResponse>() {
            @Override
            public void onResponse(Call<ToggleLikeResponse> call, Response<ToggleLikeResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    int likes = Integer.parseInt(txtLikes.getText().toString());

                    switch (message) {
                        case "post liked.":
                            txtLikes.setText(String.valueOf(likes + 1));
                            imgLike.setColorFilter(getResources().getColor(R.color.green));
                            break;
                        case "post unliked.":
                            txtLikes.setText(String.valueOf(likes - 1));
                            imgLike.setColorFilter(getResources().getColor(R.color.gray2));
                            break;
                    }
                } else {
                    Toast.makeText(DetailPostActivity.this, "Cannot like post. try again later.", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ToggleLikeResponse> call, Throwable t) {
                Toast.makeText(DetailPostActivity.this, "Cannot like post. try again later.", Toast.LENGTH_SHORT);
            }
        });
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
                    txtLikes.setText(String.valueOf(post.getLikes().size()));

                    // date
                    String date = Utils.formatDateFromDatabaseString(post.getCreatedAt());
                    txtDate.setText(date);

                    // like
                    // if the current user has liked the post, change the color of like button
                    int idUser = pref.getIdUser();
                    boolean userLiked = post.userLikedThisPost(idUser);
                    if (userLiked) {
                        imgLike.setColorFilter(getResources().getColor(R.color.green2));
                    }

                    // comments
                    ArrayList<CommentsItem> list = new ArrayList<>();
                    list.addAll(post.getComments());
                    listCommentAdapter.setData(list);
                    txtCommentTitle.setText("Comments (" + list.size() + ")");

                    // avatar
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

    @Override
    public void onBackPressed()
    {
        setResult(Activity.RESULT_OK);
        super.onBackPressed();  // optional depending on your needs
    }
}

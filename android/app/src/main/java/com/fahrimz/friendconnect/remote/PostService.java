package com.fahrimz.friendconnect.remote;

import com.fahrimz.friendconnect.model.PostsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {
    @GET("api/posts")
    Call<PostsResponse> getPosts();
}

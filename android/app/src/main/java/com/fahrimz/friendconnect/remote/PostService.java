package com.fahrimz.friendconnect.remote;

import com.fahrimz.friendconnect.model.AddPostRequest;
import com.fahrimz.friendconnect.model.AddPostResponse;
import com.fahrimz.friendconnect.model.PostsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PostService {
    @GET("api/posts")
    Call<PostsResponse> getPosts();

    @POST("/api/posts")
    Call<AddPostResponse> addPost(@Body AddPostRequest body, @Header("Authorization") String auth);
}

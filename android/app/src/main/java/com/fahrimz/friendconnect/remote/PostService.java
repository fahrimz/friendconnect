package com.fahrimz.friendconnect.remote;

import com.fahrimz.friendconnect.model.AddCommentRequest;
import com.fahrimz.friendconnect.model.AddCommentResponse;
import com.fahrimz.friendconnect.model.AddPostRequest;
import com.fahrimz.friendconnect.model.AddPostResponse;
import com.fahrimz.friendconnect.model.DetailPostResponse;
import com.fahrimz.friendconnect.model.PostsResponse;
import com.fahrimz.friendconnect.model.ToggleLikeRequest;
import com.fahrimz.friendconnect.model.ToggleLikeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostService {
    @GET("api/posts")
    Call<PostsResponse> getPosts(@Header("Authorization") String auth);

    @GET("api/posts/{id}")
    Call<DetailPostResponse> getPost(@Path("id") int idPost);

    @POST("api/posts")
    Call<AddPostResponse> addPost(@Body AddPostRequest body, @Header("Authorization") String auth);

    @POST("api/likes/toggle")
    Call<ToggleLikeResponse> toggleLike(@Body ToggleLikeRequest body, @Header("Authorization") String auth);

    @POST("api/comments")
    Call<AddCommentResponse> addComment(@Body AddCommentRequest body, @Header("Authorization") String auth);
}

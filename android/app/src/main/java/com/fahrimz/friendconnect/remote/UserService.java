package com.fahrimz.friendconnect.remote;

import com.fahrimz.friendconnect.model.AddFriendRequest;
import com.fahrimz.friendconnect.model.AddFriendResponse;
import com.fahrimz.friendconnect.model.LoginResponse;
import com.fahrimz.friendconnect.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest body);

    @POST("api/users/add-friend")
    Call<AddFriendResponse> addFriend(@Body AddFriendRequest body, @Header("Authorization") String auth);
}

package com.fahrimz.friendconnect.remote;

import com.fahrimz.friendconnect.model.LoginResponse;
import com.fahrimz.friendconnect.model.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest body);
}

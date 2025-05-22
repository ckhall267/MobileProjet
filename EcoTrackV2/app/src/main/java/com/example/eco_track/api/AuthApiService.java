package com.example.eco_track.api;

import com.example.eco_track.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("/api/auth/register")
    Call<User> register(@Body User user);

    @POST("/api/auth/login")
    Call<User> login(@Body User user);
}

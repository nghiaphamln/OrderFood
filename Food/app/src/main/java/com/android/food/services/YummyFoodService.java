package com.android.food.services;

import com.android.food.models.LoginRequest;
import com.android.food.models.LoginResponse;
import com.android.food.models.RegisterRequest;
import com.android.food.models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface YummyFoodService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest data);

    @POST("/register")
    Call<RegisterResponse> register(@Body RegisterRequest data);
}

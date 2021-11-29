package com.android.food.services;

import com.android.food.models.ForgotPasswordRequest;
import com.android.food.models.ForgotPasswordResponse;
import com.android.food.models.GetInformationRequest;
import com.android.food.models.GetInformationResponse;
import com.android.food.models.LoginRequest;
import com.android.food.models.LoginResponse;
import com.android.food.models.OtpRequest;
import com.android.food.models.OtpResponse;
import com.android.food.models.RegisterRequest;
import com.android.food.models.RegisterResponse;
import com.android.food.models.ResetPasswordRequest;
import com.android.food.models.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface YummyFoodService {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginRequest data);

    @POST("/register")
    Call<RegisterResponse> register(@Body RegisterRequest data);

    @POST("/send-otp")
    Call<ForgotPasswordResponse> sendOtp(@Body ForgotPasswordRequest data);

    @POST("/check-otp")
    Call<OtpResponse> checkOtp(@Body OtpRequest data);

    @POST("/reset-password")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest data);

    @POST("/get-info")
    Call<GetInformationResponse> getInformation(@Body GetInformationRequest data);
}

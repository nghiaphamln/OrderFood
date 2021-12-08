package com.android.food.services;

import com.android.food.models.AddOrderRequest;
import com.android.food.models.AddOrderResponse;
import com.android.food.models.CategoriesResponse;
import com.android.food.models.ChangePasswordRequest;
import com.android.food.models.ChangePasswordResponse;
import com.android.food.models.ForgotPasswordRequest;
import com.android.food.models.ForgotPasswordResponse;
import com.android.food.models.GetInformationRequest;
import com.android.food.models.GetInformationResponse;
import com.android.food.models.LoginRequest;
import com.android.food.models.LoginResponse;
import com.android.food.models.OtpRequest;
import com.android.food.models.OtpResponse;
import com.android.food.models.ProductsRequest;
import com.android.food.models.ProductsRespone;
import com.android.food.models.RegisterRequest;
import com.android.food.models.RegisterResponse;
import com.android.food.models.ResetPasswordRequest;
import com.android.food.models.ResetPasswordResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

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

    @POST("/change-password")
    Call<ChangePasswordResponse> changePassword(@Body ChangePasswordRequest data);

    @GET("/get-categories")
    Call<List<CategoriesResponse>> getCategories();

    @POST("/get-products")
    Call<List<ProductsRespone>> getProducts(@Body ProductsRequest data);

    @POST("add-order")
    Call<AddOrderResponse> addOrder(@Body AddOrderRequest data);
}

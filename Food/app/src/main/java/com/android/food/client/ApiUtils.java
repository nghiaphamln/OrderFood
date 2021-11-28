package com.android.food.client;

import com.android.food.services.YummyFoodService;

public class ApiUtils {
    public static final String BASE_URL = "http://10.0.2.2:5000";

    public static YummyFoodService getFoodService() {
        return RetrofitClient.getClient(BASE_URL).create(YummyFoodService.class);
    }
}

package com.android.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.food.adapter.CategoryAdapter;
import com.android.food.adapter.PopularAdapter;
import com.android.food.client.ApiUtils;
import com.android.food.domain.CategoryDomain;
import com.android.food.models.CategoriesResponse;
import com.android.food.models.ProductsRespone;
import com.android.food.services.YummyFoodService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private CategoryAdapter categoryAdapter;
    private PopularAdapter popularAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewCategoryList = v.findViewById(R.id.recyclerView);
        recyclerViewPopularList = v.findViewById(R.id.recyclerView2);
        recyclerViewCategory(v);

        return v;
    }

    private void recyclerViewCategory(View v) {
        YummyFoodService mService = ApiUtils.getFoodService();
        Call<List<CategoriesResponse>> callback = mService.getCategories();
        callback.enqueue(new Callback<List<CategoriesResponse>>() {
            @Override
            public void onResponse(Call<List<CategoriesResponse>> call,
                                   Response<List<CategoriesResponse>> response) {
                ArrayList<CategoriesResponse> categoryDomainArrayList =
                        (ArrayList<CategoriesResponse>) response.body();

                categoryAdapter = new CategoryAdapter(getActivity(), categoryDomainArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
                recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
                recyclerViewCategoryList.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoriesResponse>> call, Throwable t) {

            }
        });

        Call<List<ProductsRespone>> callbackPopular = mService.getProducts();
        callbackPopular.enqueue(new Callback<List<ProductsRespone>>() {
            @Override
            public void onResponse(Call<List<ProductsRespone>> call,
                                   Response<List<ProductsRespone>> response) {

                ArrayList<ProductsRespone> productsResponeArrayList =
                        (ArrayList<ProductsRespone>) response.body();

                popularAdapter = new PopularAdapter(getActivity(), productsResponeArrayList);

                LinearLayoutManager linearLayoutManagerPopular = new
                        LinearLayoutManager(getActivity());

                linearLayoutManagerPopular.setOrientation(linearLayoutManagerPopular.HORIZONTAL);
                recyclerViewPopularList.setLayoutManager(linearLayoutManagerPopular);
                recyclerViewPopularList.setAdapter(popularAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductsRespone>> call, Throwable t) {

            }
        });
    }
}
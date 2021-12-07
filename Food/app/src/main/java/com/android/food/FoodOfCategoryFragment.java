package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.food.adapter.FoodOfCategoryAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.food.adapter.CartAdapter;
import com.android.food.manager.CartManager;
import com.android.food.manager.ProductManager;
import com.android.food.models.ProductsRespone;

import java.util.ArrayList;

public class FoodOfCategoryFragment extends Fragment {
    private RecyclerView recyclerViewProductList;
    private FoodOfCategoryAdapter foodOfCategoryAdapter;

    public FoodOfCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food_of_category, container, false);

        recyclerViewProductList = v.findViewById(R.id.recyclerview);
        recyclerViewProduct(v);

        return v;
    }

    private void recyclerViewProduct(View v) {
        ArrayList<ProductsRespone> productsList = ProductManager.getInstance().getProductsByCategory();
        foodOfCategoryAdapter = new FoodOfCategoryAdapter(getActivity(), productsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerViewProductList.setLayoutManager(linearLayoutManager);
        recyclerViewProductList.setAdapter(foodOfCategoryAdapter);
    }
}
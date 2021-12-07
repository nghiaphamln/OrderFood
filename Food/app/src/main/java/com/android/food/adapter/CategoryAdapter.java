package com.android.food.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.food.FoodOfCategoryFragment;
import com.android.food.client.ApiUtils;
import com.android.food.manager.ProductManager;
import com.android.food.models.CategoriesResponse;
import com.android.food.models.ProductsRequest;
import com.android.food.models.ProductsRespone;
import com.android.food.services.YummyFoodService;
import com.bumptech.glide.Glide;
import com.android.food.domain.CategoryDomain;
import com.android.food.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoriesResponse> categoriesResponseArrayList;
    YummyFoodService mService = ApiUtils.getFoodService();

    public CategoryAdapter(Context context,
                           ArrayList<CategoriesResponse> categoriesResponseArrayList) {
        this.context = context;
        this.categoriesResponseArrayList = categoriesResponseArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_categories, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesResponse categoryDomain = categoriesResponseArrayList.get(position);
        holder.categoryName.setText(categoryDomain.getName());
        Picasso.get().load(categoryDomain.getImage()).into(holder.categoryPic);

        // nhấn vào hình ảnh show ra danh sách thể loại
        holder.categoryPic.setOnClickListener(v -> {
            ProductsRequest data = new ProductsRequest();
            data.setCategory(categoryDomain.getId().toString());

            mService.getProducts(data).enqueue(new Callback<List<ProductsRespone>>() {
                @Override
                public void onResponse(Call<List<ProductsRespone>> call,
                                       Response<List<ProductsRespone>> response) {
                    ArrayList<ProductsRespone> productsResponeArrayList =
                            (ArrayList<ProductsRespone>) response.body();

                    ProductManager.getInstance().setProductsByCategory(productsResponeArrayList);

                    FoodOfCategoryFragment foodOfCategoryFragment = new FoodOfCategoryFragment();
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.container, foodOfCategoryFragment).addToBackStack(null).commit();
                }

                @Override
                public void onFailure(Call<List<ProductsRespone>> call, Throwable t) {

                }
            });
        });
    }


    @Override
    public int getItemCount() {
        if (categoriesResponseArrayList != null) {
            return categoriesResponseArrayList.size();
        }
        else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
        }
    }
}
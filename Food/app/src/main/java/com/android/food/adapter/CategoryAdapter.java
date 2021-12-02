package com.android.food.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.food.models.CategoriesResponse;
import com.bumptech.glide.Glide;
import com.android.food.domain.CategoryDomain;
import com.android.food.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    ArrayList<CategoriesResponse> categoriesResponseArrayList;

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
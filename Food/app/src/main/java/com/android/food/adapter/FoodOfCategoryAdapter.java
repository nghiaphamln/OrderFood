package com.android.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.food.R;
import com.android.food.models.ProductsRespone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodOfCategoryAdapter extends RecyclerView.Adapter<FoodOfCategoryAdapter.ViewHolder>{
    Context context;
    ArrayList<ProductsRespone> productsList;

    public FoodOfCategoryAdapter(Context context, ArrayList<ProductsRespone> productsList) {
        this.context = context;
        this.productsList = productsList;
    }


    @NonNull
    @Override
    public FoodOfCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_food_of_category, parent, false);

        return new FoodOfCategoryAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodOfCategoryAdapter.ViewHolder holder, int position) {
        ProductsRespone productsRespone = productsList.get(position);

        holder.title.setText(productsRespone.getName());
        holder.totalEachItem.setText(productsRespone.getPrice().toString());
        Picasso.get().load(productsRespone.getImage()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        if (productsList == null) {
            return 0;
        }
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;
        TextView totalEachItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2Txt);
            pic = itemView.findViewById(R.id.picCard);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
        }
    }
}

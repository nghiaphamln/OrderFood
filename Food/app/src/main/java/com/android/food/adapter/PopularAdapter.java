package com.android.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.food.R;
import com.android.food.manager.CartManager;
import com.android.food.models.ProductsRespone;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>{

    Context context;
    ArrayList<ProductsRespone> productsResponeArrayList;

    public PopularAdapter(Context context, ArrayList<ProductsRespone> productsResponeArrayList) {
        this.context = context;
        this.productsResponeArrayList = productsResponeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_food, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsRespone productsRespone = productsResponeArrayList.get(position);
        holder.txtTitle.setText(productsRespone.getName());
        holder.txtFee.setText(productsRespone.getPrice().toString());
        Picasso.get().load(productsRespone.getImage()).into(holder.imgPic);

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ProductsRespone> a = new ArrayList<>();
                a.add(productsResponeArrayList.get(position));
                CartManager.getInstance().setCartList(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsResponeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtFee, btnAdd;
        ImageView imgPic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            txtFee = itemView.findViewById(R.id.fee);
            btnAdd = itemView.findViewById(R.id.addBtn);
            imgPic = itemView.findViewById(R.id.pic);
        }
    }
}

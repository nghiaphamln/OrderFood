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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.food.AccountManagerFragment;
import com.android.food.R;
import com.android.food.ShowDetailFragment;
import com.android.food.manager.AccountManager;
import com.android.food.manager.CartManager;
import com.android.food.manager.ProductManager;
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

        holder.btnAdd.setOnClickListener(v -> {
            if (AccountManager.getInstance().isLogin()) {
                ArrayList<ProductsRespone> a = new ArrayList<>();
                a.add(productsResponeArrayList.get(position));
                CartManager.getInstance().setCartList(a);
                Toast.makeText(v.getContext(), "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(v.getContext(), "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            }

        });

        holder.imgPic.setOnClickListener(v -> {
            ProductManager.getInstance().setSelectedProduct(productsRespone);
            ShowDetailFragment showDetailFragment = new ShowDetailFragment();
            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.container, showDetailFragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        if (productsResponeArrayList != null) {
            return productsResponeArrayList.size();
        }
        else {
            return 0;
        }
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
